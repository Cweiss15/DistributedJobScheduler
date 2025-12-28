import java.io.PrintWriter;
import java.util.Queue;

public class ClientToMasterThread extends Thread {
    private Queue<Job> jobs;
    private PrintWriter masterOut;

    public ClientToMasterThread(Queue<Job> jobs, PrintWriter masterOut) {
        this.jobs = jobs;
        this.masterOut = masterOut;
    }

    public void run() {
        while (true) {
            if (!jobs.isEmpty()) {
                Job job = jobs.poll();
                if (job != null) {
                    System.out.println("Client sending job to master: " + job);
                    masterOut.println(job.toString());
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
