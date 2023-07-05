//DatabaseConnector.java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector {
    private static final String DB_URL = "jdbc:mysql://localhost/load_balancer_db?useSSL=false";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    public static List<Worker> getWorkersFromDatabase() {
        List<Worker> workers = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            // Execute the SQL query to retrieve workers
            ResultSet resultSet = statement.executeQuery("SELECT * FROM workers");

            // Iterate over the result set and create Worker objects
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String status = resultSet.getString("status");
                int connectionCount = resultSet.getInt("connection_count");

                Worker worker = new Worker(id, name, status, connectionCount);
                workers.add(worker);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workers;
    }

    public static void updateConnectionCount(int workerId, int connectionCount) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE workers SET connection_count = ? WHERE id = ?")) {
            statement.setInt(1, connectionCount);
            statement.setInt(2, workerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
