package Game.database;

import java.sql.*;

public class DatabaseResetter {
    public static void main(String[] args) {
        String url = "jdbc:mysql://schoolopd.duckdns.org:3308/scrum_escape_game?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "1234";

        System.out.println("🧹 Proberen gegevens te verwijderen...");

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement deleteStmt = conn.createStatement()) {

            // Zet foreign key checks tijdelijk uit
            deleteStmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            // Gebruik apart Statement voor ResultSet
            try (Statement showStmt = conn.createStatement();
                 ResultSet rs = showStmt.executeQuery("SHOW TABLES")) {

                while (rs.next()) {
                    String tableName = rs.getString(1);
                    System.out.println("❌ Leegmaken van tabel: " + tableName);
                    deleteStmt.executeUpdate("DELETE FROM " + tableName);
                }
            }

            deleteStmt.execute("SET FOREIGN_KEY_CHECKS = 1");

            System.out.println("✅ Alle gegevens zijn verwijderd (structuur behouden).");

        } catch (Exception e) {
            System.out.println("❌ Fout bij verwijderen:");
            e.printStackTrace();
        }
    }
}
