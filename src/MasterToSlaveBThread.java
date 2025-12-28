import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.*;

public class MasterToSlaveBThread implements Runnable{
    private final PrintWriter slaveBOut;
    private BufferedReader slaveBIn;
    private Queue<Job> BJobs = new LinkedList<>();
    private Queue<Job> doneJobs = new LinkedList<>();
    private boolean forever = true;

    //constructor
    public MasterToSlaveBThread(BufferedReader slaveBIn, PrintWriter slaveBOut, Queue<Job> BJobs, Queue<Job> doneJobs) {
        this.slaveBIn = slaveBIn;
        this.slaveBOut = slaveBOut;
        this.BJobs = BJobs;
        this.doneJobs = doneJobs;
    }

    @Override
    public void run() {
        try {
            while (forever) {
                if (!BJobs.isEmpty()) {
                    Job job = BJobs.poll();
                    if (job != null) {
                        System.out.println(job.toString()+" given to slave b");
                        slaveBOut.println(job);
                        slaveBOut.flush();
                        
                        // Wait for slave to respond with a done job
                        String doneJob = slaveBIn.readLine();
                        System.out.println("Slave B returned a done job: " + doneJob);
                        
                        doneJobs.add(job);
                    }
                }
            }
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}