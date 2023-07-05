//Worker.java
public class Worker {
    private int id;
    private String name;
    private String status;
    private int connectionCount;

    public Worker(int id, String name, String status, int connectionCount) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.connectionCount = connectionCount;
    }

    public synchronized String processRequest(String request) {
        // Process the request
        String response = "Worker " + id + " (" + name + ") processed request: " + request;

        // Update the connection count
        connectionCount++;

        // Update the connection count in the database
        DatabaseConnector.updateConnectionCount(id, connectionCount);

        return response;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public int getConnectionCount() {
        return connectionCount;
    }
}
