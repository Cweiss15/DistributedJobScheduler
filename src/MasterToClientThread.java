import java.io.PrintWriter;

public class MasterToClientThread implements Runnable {
    private final PrintWriter clientOut;
    private final Job job;

    public MasterToClientThread(PrintWriter clientOut, Job job) {
        this.clientOut = clientOut;
        this.job =job;
    }
    public void run() {
        clientOut.println(job);
        clientOut.flush();
    }
}
