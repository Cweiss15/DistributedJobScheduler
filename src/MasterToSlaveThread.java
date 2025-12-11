import java.io.PrintWriter;

public class MasterToSlaveThread implements Runnable {
    private final PrintWriter slaveAOut;
    private final Job job;

    //constructor
    public MasterToSlaveThread(PrintWriter slaveAOut, Job job) {
        this.slaveAOut = slaveAOut;
        this.job =job;
    }
    public void run() {
        slaveAOut.println(job);
        slaveAOut.flush();
    }
}