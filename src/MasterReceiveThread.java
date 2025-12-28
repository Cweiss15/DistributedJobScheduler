import java.io.BufferedReader;
import java.util.Queue;

public class MasterReceiveThread extends Thread {
    private Queue<Job> jobs;
    private BufferedReader clientIn;

    public MasterReceiveThread(Queue<Job> jobs, BufferedReader clientIn) {
        this.jobs = jobs;
        this.clientIn = clientIn;
    }

    public void run() {
        while (true) {
            try {
                String line = clientIn.readLine();
                if (line != null) {
                    System.out.println("Master received job from client: " + line);
                    Job job = new Job(line);
                    jobs.add(job);
                }
            } catch (Exception e) {
                System.err.println("Error reading from client: " + e.getMessage());
            }
        }
    }
}
