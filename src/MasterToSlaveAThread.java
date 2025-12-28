import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Queue;

public class MasterToSlaveAThread implements Runnable {
    private final PrintWriter slaveAOut;
    private BufferedReader slaveAIn;
    private Queue<Job> AJobs;
    private Queue<Job> doneJobs;

    //constructor
    public MasterToSlaveAThread(BufferedReader slaveAIn, PrintWriter slaveAOut, Queue<Job> AJobs,Queue<Job> doneJobs) {
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
                    Job job = AJobs.poll();
                    //send slave A a job
                    //test
                    System.out.println(job.toString()+" given to slave a");
                    slaveAOut.println(job);
                    slaveAOut.flush();
                    while (!slaveAIn.readLine().equals(job.toString())) ;
                    doneJobs.add(job);
                    //test
                    System.out.println("done job list"+doneJobs);
                }
            }
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}