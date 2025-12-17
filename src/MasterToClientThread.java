import java.io.PrintWriter;
import java.util.ArrayList;

public class MasterToClientThread implements Runnable {
    private PrintWriter clientOut;
    private ArrayList<Job> doneJobs = new ArrayList<>();

    public MasterToClientThread(PrintWriter clientOut, ArrayList<Job> doneJobs) {
        this.clientOut = clientOut;
        this.doneJobs = doneJobs;
    }

    @Override
    public void run() {
        int cnt = 0;
        while (true) {
            Job job = null;
            
            // Synchronize to safely get a job from doneJobs ArrayList
            synchronized (doneJobs) {
                while (!doneJobs.isEmpty()) {
                job = doneJobs.get(cnt);
            }
            
            // Send done job to client
            clientOut.println(job);
            clientOut.flush();
            cnt++;
        }
    }
}
