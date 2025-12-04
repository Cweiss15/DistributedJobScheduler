import java.io.PrintWriter;

public class MasterToSlaveBThread implements Runnable {
    private final PrintWriter slaveBOut;
    private final Job job;

    public MasterToSlaveBThread(PrintWriter slaveBOut, Job job) {
        this.slaveBOut = slaveBOut;
        this.job =job;
    }
    public void run() {
        slaveBOut.println(job);
        slaveBOut.flush();
    }
}