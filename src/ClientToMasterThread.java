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
        for (Job job : jobs) {
            masterOut.println(job.toString());
        }
    }
}
