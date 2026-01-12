import java.io.PrintWriter;

public class ClientToMasterThread extends Thread {
    private SynchronizedJobQueue jobs;
    private PrintWriter masterOut;
    private char clientType;

    public ClientToMasterThread(SynchronizedJobQueue jobs, PrintWriter masterOut, char clientType) {
        this.jobs = jobs;
        this.masterOut = masterOut;
        this.clientType = clientType;
    }

    public void run() {
        while (true) {
            Job job = jobs.poll();
            System.out.println("Client sending job to master: " + job.toPrint());
            masterOut.println(job.toString());
            masterOut.flush();
        }
    }
}
