import java.io.PrintWriter;

public class ClientToUserThread implements Runnable {
    private PrintWriter userOut;
    private SynchronizedJobQueue doneJobs;
    private char client;

    public ClientToUserThread(SynchronizedJobQueue doneJobs, PrintWriter userOut, char client) {
        this.userOut = userOut;
        this.doneJobs = doneJobs;
        this.client = client;

    }

    public void run() {
        while (true) {
            Job job = doneJobs.poll();
            if (job != null) {
                System.out.println("Client sending done job to user: " + job);
                userOut.println(job);
                userOut.flush();
            }
        }
    }
}
