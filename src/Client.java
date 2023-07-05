//Client.java
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static void main(String[] args) {
        List<Worker> workers = DatabaseConnector.getWorkersFromDatabase();
        LoadBalancer loadBalancer = new LoadBalancer(workers);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // Simulate multiple client requests
        for (int i = 0; i < 10; i++) {
            final int requestId = i;
            executorService.execute(() -> {
                String request = "Request " + requestId;
                String response = loadBalancer.processRequest(request);
                System.out.println("Response: " + response);
            });
        }

        // Simulate dynamic worker addition and removal
        executorService.execute(() -> {
            try {
                Thread.sleep(5000); // Wait for 5 seconds

                // Add a new worker
                Worker newWorker = new Worker(4, "Worker 4", "Active", 0);
                loadBalancer.addWorker(newWorker);
                System.out.println("Added Worker 4");

                Thread.sleep(5000); // Wait for 5 seconds

                // Remove a worker
                Worker workerToRemove = workers.get(0);
                loadBalancer.removeWorker(workerToRemove);
                System.out.println("Removed Worker 1");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }
}
