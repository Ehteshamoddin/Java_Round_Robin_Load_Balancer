import java.util.ArrayList;
import java.util.List;

//LoadBalancer.java
public class LoadBalancer {
    private List<Worker> workers;
    private int currentIndex;

    public LoadBalancer(List<Worker> workers) {
        this.workers = new ArrayList<>(workers);
        this.currentIndex = 0;
    }

    public synchronized Worker getNextWorker() {
        // Get the next worker in a strict round-robin manner
        Worker worker = workers.get(currentIndex);
        currentIndex = (currentIndex + 1) % workers.size();
        return worker;
    }

    public synchronized String processRequest(String request) {
        Worker worker = getNextWorker();
        return worker.processRequest(request);
    }

    public synchronized void addWorker(Worker worker) {
        workers.add(worker);
    }

    public synchronized void removeWorker(Worker worker) {
        workers.remove(worker);
    }
}
