import java.io.PrintWriter;

public class MasterToSlaveAThread implements Runnable {
    private final PrintWriter slaveAOut;
    private final Job job;

    //constructor
    public MasterToSlaveAThread(PrintWriter slaveAOut, Job job) {
        this.slaveAOut = slaveAOut;
        this.job =job;
    }
    public void run() {
        slaveAOut.println(job);
        slaveAOut.flush();
    }
}