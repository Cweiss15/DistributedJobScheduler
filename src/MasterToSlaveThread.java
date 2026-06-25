import java.io.BufferedReader;
import java.io.PrintWriter;

public class MasterToSlaveThread implements Runnable {
    private final PrintWriter slaveOut;
    private BufferedReader slaveIn;
    private SynchronizedJobQueue jobQueue;
    private SynchronizedJobQueue doneJobs;
    private boolean forever = true;

    // This thread is used by the master to send jobs to the slaves
    public MasterToSlaveThread(BufferedReader slaveIn, PrintWriter slaveOut, SynchronizedJobQueue jobQueue,
                               SynchronizedJobQueue doneJobs) {
        this.slaveIn = slaveIn;
        this.slaveOut = slaveOut;
        this.jobQueue = jobQueue;
        this.doneJobs = doneJobs;
    }

    @Override
    public void run() {
        try {
            while (forever) {
                // sends job to slave
                Job job = jobQueue.poll();
                System.out.println(job.toPrint() + " given to slave");
                slaveOut.println(job);
                slaveOut.flush();

                // Wait for slave to respond with a done job
                String doneJob = slaveIn.readLine();
                Job done = new Job(doneJob);
                System.out.println("Slave returned a done job: " + done.toPrint());

                doneJobs.add(done);
            }
        } catch (Exception e) {
            System.err.println("Error in MasterToSlaveThread: " + e.getMessage());
            e.printStackTrace();
        }
    }
}