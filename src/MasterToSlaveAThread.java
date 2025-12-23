import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MasterToSlaveAThread implements Runnable {
    private final PrintWriter slaveAOut;
    private BufferedReader slaveAIn;
    private ArrayList<Job> AJobs;
    private ArrayList<Job> doneJobs;

    //constructor
    public MasterToSlaveAThread(BufferedReader slaveAIn, PrintWriter slaveAOut, ArrayList<Job> AJobs,ArrayList<Job> doneJobs) {
        this.slaveAIn = slaveAIn;
        this.slaveAOut = slaveAOut;
        this.AJobs = AJobs;
        this.doneJobs = doneJobs;
    }

    @Override
    public void run() {
        int cntr = 0;
        try {
            //it will go forever
            while (true) {
                while (!AJobs.isEmpty()) {
                    Job job = AJobs.get(0);
                    slaveAOut.println(job);
                    slaveAOut.flush();
                    while (!slaveAIn.readLine().equals(job.toString())) ;
                    doneJobs.add(job);
                    AJobs.remove(0);
                }
            }
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}