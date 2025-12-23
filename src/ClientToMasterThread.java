import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class ClientToMasterThread implements Runnable{
    private BufferedReader clientIn = null;
    private ArrayList<Job> jobList = new ArrayList<>();

    public ClientToMasterThread(BufferedReader clientIn, ArrayList<Job> jobList) {
        this.clientIn = clientIn;
        this.jobList = jobList;
    }

    public void run() {
        String jobString;
        try {
            while ((jobString = clientIn.readLine()) != null) {
                Job job = new Job(jobString);
                // adds each job to the job list
                jobList.add(job);
            }
        }catch (IOException e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}
