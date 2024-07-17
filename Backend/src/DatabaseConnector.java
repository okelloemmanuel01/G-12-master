import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




    public class DatabaseConnector {
        public static void main(String[] args) {
            String url = "jdbc:mysql://localhost:8000/tonly";

            try (Connection conn = DriverManager.getConnection(url)) {
                if (conn != null) {
                    System.out.println("Connected to the database successfully.");
                } else {
                    System.out.println("Failed to make connection!");
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }


