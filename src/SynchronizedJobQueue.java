import java.util.*;

public class SynchronizedJobQueue {

    private final Queue<Job> queue = new LinkedList<>();

    public synchronized void add(Job job) {
        queue.add(job);
        notifyAll();
    }

    public synchronized Job poll() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                Thread.currentThread().interrupt();
            }

        }
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
    
    @Override
    public String toString() {
        return queue.toString();
    }
}