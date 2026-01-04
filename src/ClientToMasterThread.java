import java.io.PrintWriter;

public class ClientToMasterThread extends Thread {
    private SynchronizedJobQueue jobs;
    private PrintWriter masterOut;

    public ClientToMasterThread(SynchronizedJobQueue jobs, PrintWriter masterOut) {
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
