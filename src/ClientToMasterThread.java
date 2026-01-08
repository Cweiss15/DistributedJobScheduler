import java.io.PrintWriter;

public class ClientToMasterThread extends Thread {
    private SynchronizedJobQueue jobs;
    private PrintWriter masterOut;

    public ClientToMasterThread(SynchronizedJobQueue jobs, PrintWriter masterOut, char clientName) {
        this.jobs = jobs;
        this.masterOut = masterOut;
    }

    public void run() {
        while (true) {
            Job job = jobs.poll();
            System.out.println("Client sending job to master: " + job);
            masterOut.println(job.toString());
        }
    }
}
