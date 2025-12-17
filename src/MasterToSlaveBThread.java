import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MasterToSlaveBThread implements Runnable{
    private final PrintWriter slaveBOut;
    private BufferedReader slaveBIn;
    ArrayList<Job> BJobs = new ArrayList<>();
    ArrayList<Job> doneJobs = new ArrayList<>();

    //constructor
    public MasterToSlaveBThread(BufferedReader slaveBIn, PrintWriter slaveBOut, ArrayList<Job> BJobs, ArrayList<Job> doneJobs) {
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
                    Job job = BJobs.get(0);
                    slaveBOut.println(job);
                    slaveBOut.flush();
                    while (!slaveBIn.readLine().equals(job.toString())) ;
                    doneJobs.add(job);
                    BJobs.remove(0);
                }
            }
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}