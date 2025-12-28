import java.io.PrintWriter;
import java.util.*;

public class MasterToClientThread implements Runnable {
    private PrintWriter clientOut;
    private Queue<Job> doneJobs = new LinkedList<>();

    public MasterToClientThread(PrintWriter clientOut, Queue<Job> doneJobs) {
        this.clientOut = clientOut;
        this.doneJobs = doneJobs;
    }

    @Override
    public void run() {
        while (true) {
            if (!doneJobs.isEmpty()) {
                Job job = doneJobs.poll();
                if (job != null) {
                    System.out.println("Master sending done job to client: " + job);
                    clientOut.println(job);
                    clientOut.flush();
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
