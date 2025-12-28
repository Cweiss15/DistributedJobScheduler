import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.*;

public class MasterToSlaveBThread implements Runnable{
    private final PrintWriter slaveBOut;
    private BufferedReader slaveBIn;
    private Queue<Job> BJobs = new LinkedList<>();
    private Queue<Job> doneJobs = new LinkedList<>();

    //constructor
    public MasterToSlaveBThread(BufferedReader slaveBIn, PrintWriter slaveBOut, Queue<Job> BJobs, Queue<Job> doneJobs) {
        this.slaveBIn = slaveBIn;
        this.slaveBOut = slaveBOut;
        this.BJobs = BJobs;
        this.doneJobs = doneJobs;
    }

    @Override
    public void run() {
        int cntr = 0;
        try {
            //it will go forever
            while (true) {
                while (!BJobs.isEmpty()) {
                    Job job = BJobs.poll();
                    slaveBOut.println(job);
                    slaveBOut.flush();
                    while (!slaveBIn.readLine().equals(job.toString())) ;
                    doneJobs.add(job);
                }
            }
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}