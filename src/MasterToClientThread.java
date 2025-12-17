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
            while (doneJobs.isEmpty()) {
                clientOut.println(doneJobs.get(cnt));
                clientOut.flush();
                cnt++;
            }
        }
    }
}
