//package Game.database;
//
//import java.sql.*;
//
////Deze database is LOKAAL als backup voor het geval.
//public class DatabaseBackup {
//    public static void main(String[] args) {
//        String url = "jdbc:mysql://localhost:3306/scrum_escape_game?useSSL=false&serverTimezone=UTC";
//        String user = "root";
//        String password = "";
//
//        System.out.println("🔍 Proberen te verbinden met database...");
//
//        try (Connection conn = DriverManager.getConnection(url, user, password)) {
//            System.out.println("✅ Verbonden met de database!");
//
//            try (Statement tableStmt = conn.createStatement();
//                 ResultSet tables = tableStmt.executeQuery("SHOW TABLES")) {
//
//                System.out.println("📦 Tabellen en hun inhoud:");
//                while (tables.next()) {
//                    String tableName = tables.getString(1);
//                    System.out.println("\n📁 Tabel: " + tableName);
//
//                    try (Statement dataStmt = conn.createStatement();
//                         ResultSet rows = dataStmt.executeQuery("SELECT * FROM " + tableName)) {
//
//                        ResultSetMetaData meta = rows.getMetaData();
//                        int columnCount = meta.getColumnCount();
//
//                        // Kolomnamen
//                        System.out.print("│ ");
//                        for (int i = 1; i <= columnCount; i++) {
//                            System.out.print(meta.getColumnName(i) + " │ ");
//                        }
//                        System.out.println();
//
//                        // Rijen
//                        while (rows.next()) {
//                            System.out.print("│ ");
//                            for (int i = 1; i <= columnCount; i++) {
//                                System.out.print(rows.getString(i) + " │ ");
//                            }
//                            System.out.println();
//                        }
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            System.out.println("❌ Verbinden mislukt:");
//            e.printStackTrace();
//        }
//    }
//}
