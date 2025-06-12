package Game.database;

import Game.core.Speler;
import Game.item.Item;
import Game.item.ItemFactory;
import Game.joker.Joker;
import Game.joker.JokerFactory;

import java.sql.*;
import java.util.List;

public class DatabaseVoortgang {

    private static final String URL = "jdbc:mysql://schoolopd.duckdns.org:3308/scrum_escape_game?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "1234";

    public static void slaOp(Speler speler, int kamerId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            int gebruikerId = getGebruikerId(conn, speler.getNaam());
            if (gebruikerId == -1) {
                System.out.println("❌ Gebruiker niet gevonden: " + speler.getNaam());
                return;
            }

            slaStatusOp(conn, speler, gebruikerId);
            slaInventoryOp(conn, speler, gebruikerId);
            slaMonstersOp(conn, speler, gebruikerId);
            slaJokersOp(conn, speler, gebruikerId);
            slaKamerVoortgangOp(conn, gebruikerId, kamerId);

            System.out.println("💾 Spelvoortgang succesvol opgeslagen!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getGebruikerId(Connection conn, String gebruikersnaam) throws SQLException {
        String sql = "SELECT id FROM gebruikers WHERE gebruikersnaam = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, gebruikersnaam);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("id") : -1;
        }
    }

    private static void slaStatusOp(Connection conn, Speler speler, int gebruikerId) throws SQLException {
        String sql = """
                INSERT INTO speler_status (gebruiker_id, score, streak, sleutels, levens, hints_gebruikt, hints_over, eerste_kamer_betreden, joker_gekozen)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    score = VALUES(score),
                    streak = VALUES(streak),
                    sleutels = VALUES(sleutels),
                    levens = VALUES(levens),
                    hints_gebruikt = VALUES(hints_gebruikt),
                    hints_over = VALUES(hints_over),
                    eerste_kamer_betreden = VALUES(eerste_kamer_betreden),
                    joker_gekozen = VALUES(joker_gekozen)
                """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gebruikerId);
            stmt.setInt(2, speler.getScore());
            stmt.setInt(3, speler.getStreak());
            stmt.setInt(4, speler.getSleutels());
            stmt.setInt(5, speler.getLevens());
            stmt.setInt(6, speler.getHintCounter());
            stmt.setInt(7, speler.getHintsLeft());
            stmt.setBoolean(8, speler.isEersteKamerBetreden());
            stmt.setBoolean(9, speler.isJokerGekozen());
            stmt.executeUpdate();
        }
    }

    private static void slaInventoryOp(Connection conn, Speler speler, int gebruikerId) throws SQLException {
        try (PreparedStatement delete = conn.prepareStatement("DELETE FROM speler_inventory WHERE gebruiker_id = ?")) {
            delete.setInt(1, gebruikerId);
            delete.executeUpdate();
        }

        String insert = "INSERT INTO speler_inventory (gebruiker_id, item_naam) VALUES (?, ?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(insert)) {
            for (Item item : speler.getInventory()) {
                insertStmt.setInt(1, gebruikerId);
                insertStmt.setString(2, item.getNaam());
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();
        }
    }

    private static void slaMonstersOp(Connection conn, Speler speler, int gebruikerId) throws SQLException {
        try (PreparedStatement delete = conn.prepareStatement("DELETE FROM speler_monsters WHERE gebruiker_id = ?")) {
            delete.setInt(1, gebruikerId);
            delete.executeUpdate();
        }

        String insert = "INSERT INTO speler_monsters (gebruiker_id, monster_naam) VALUES (?, ?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(insert)) {
            for (String monster : speler.getMonsters()) {
                insertStmt.setInt(1, gebruikerId);
                insertStmt.setString(2, monster);
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();
        }
    }

    private static void slaJokersOp(Connection conn, Speler speler, int gebruikerId) throws SQLException {
        try (PreparedStatement delete = conn.prepareStatement("DELETE FROM speler_jokers WHERE gebruiker_id = ?")) {
            delete.setInt(1, gebruikerId);
            delete.executeUpdate();
        }

        String insert = "INSERT INTO speler_jokers (gebruiker_id, joker_naam) VALUES (?, ?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(insert)) {
            for (Joker joker : speler.getJokers()) {
                insertStmt.setInt(1, gebruikerId);
                insertStmt.setString(2, joker.getNaam()); // zorg dat Joker.getNaam() bestaat
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();
        }
    }

    private static void slaKamerVoortgangOp(Connection conn, int gebruikerId, int kamerId) throws SQLException {
        String sql = """
                INSERT INTO voortgang (gebruiker_id, kamer_id)
                VALUES (?, ?)
                ON DUPLICATE KEY UPDATE voltooid_op = CURRENT_TIMESTAMP
                """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gebruikerId);
            stmt.setInt(2, kamerId);
            stmt.executeUpdate();
        }
    }

    public static boolean spelerBestaat(String gebruikersnaam) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String sql = "SELECT id FROM gebruikers WHERE gebruikersnaam = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, gebruikersnaam);
                ResultSet rs = stmt.executeQuery();
                return rs.next(); // true als gebruiker al bestaat
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Speler laadtSpeler(String gebruikersnaam) {
        Speler speler = new Speler();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            int gebruikerId = getGebruikerId(conn, gebruikersnaam);
            if (gebruikerId == -1) return null;

            speler.setNaam(gebruikersnaam);

            // Status
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM speler_status WHERE gebruiker_id = ?")) {
                stmt.setInt(1, gebruikerId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    speler.setStreak(rs.getInt("streak"));
                    speler.verhoogScore(rs.getInt("score")); // voeg toe aan default 0
                    speler.setLevens(rs.getInt("levens"));
                    for (int i = 0; i < rs.getInt("sleutels") - 1; i++) speler.voegSleutelToe(); // begint met 1
                    speler.markeerEersteKamerBetreden();
                    speler.setJokerGekozen(rs.getBoolean("joker_gekozen"));
                    // Hint setup
                    int gebruikt = rs.getInt("hints_gebruikt");
                    for (int i = 0; i < gebruikt; i++) speler.verhoogHintCounter();
                    while (speler.getHintsLeft() > rs.getInt("hints_over")) speler.verlaagHintCounter();
                }
            }

            // Inventory
            try (PreparedStatement stmt = conn.prepareStatement("SELECT item_naam FROM speler_inventory WHERE gebruiker_id = ?")) {
                stmt.setInt(1, gebruikerId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Item item = ItemFactory.maakItem(rs.getString("item_naam"));
                    if (item != null) speler.voegItemToe(item);
                }
            }

            // Monsters
            try (PreparedStatement stmt = conn.prepareStatement("SELECT monster_naam FROM speler_monsters WHERE gebruiker_id = ?")) {
                stmt.setInt(1, gebruikerId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    speler.voegMonsterToe(rs.getString("monster_naam"));
                }
            }

            // Jokers
            try (PreparedStatement stmt = conn.prepareStatement("SELECT joker_naam FROM speler_jokers WHERE gebruiker_id = ?")) {
                stmt.setInt(1, gebruikerId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Joker joker = JokerFactory.maakJoker(rs.getString("joker_naam"));
                    if (joker != null) speler.voegJokerToe(joker);
                }
            }

            // Kamers
            try (PreparedStatement stmt = conn.prepareStatement("SELECT kamer_id FROM voortgang WHERE gebruiker_id = ?")) {
                stmt.setInt(1, gebruikerId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    speler.voegVoltooideKamerToe(rs.getInt("kamer_id"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return speler;
    }
}