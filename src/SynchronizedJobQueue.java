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

    @Override
    public String toString() {
        return queue.toString();
    }

    public synchronized Job peek() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        return queue.peek();
    }
}