import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Queue;

public class MasterToSlaveAThread implements Runnable {
    private final PrintWriter slaveAOut;
    private BufferedReader slaveAIn;
    private Queue<Job> AJobs;
    private Queue<Job> doneJobs;
    private boolean forever = true;

    //constructor
    public MasterToSlaveAThread(BufferedReader slaveAIn, PrintWriter slaveAOut, Queue<Job> AJobs,Queue<Job> doneJobs) {
        this.slaveAIn = slaveAIn;
        this.slaveAOut = slaveAOut;
        this.AJobs = AJobs;
        this.doneJobs = doneJobs;
    }

    @Override
    public void run() {
        try {
            while (forever) {
                if (!AJobs.isEmpty()) {
                    Job job = AJobs.poll();
                    if (job != null) {
                        System.out.println(job.toString()+" given to slave a");
                        slaveAOut.println(job);
                        slaveAOut.flush();
                        
                        // Wait for slave to respond with a done job
                        String doneJob = slaveAIn.readLine();
                        System.out.println("Slave A returned: " + doneJob);
                        
                        doneJobs.add(job);
                        System.out.println("done job list"+doneJobs);
                    }
                }
            }
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}