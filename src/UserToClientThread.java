import java.io.PrintWriter;
import java.util.*;
//Make it socket and connect to the client and send it the jobs
public class UserToClientThread extends Thread {
    private Object lock;
    private PrintWriter clientOut;
    private int client;
    public UserToClientThread(PrintWriter clientOut, Object lock, int client) {
        this.clientOut = clientOut;
        this.lock = lock;
        this.client = client;
    }
    public void run() {
        String jobName = "";
        int ctr = 0;
        while (!jobName.equals("done")) {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter a job (or done to exit): ");
            jobName = in.nextLine().toLowerCase();
            System.out.println("Enter job type (A/B): ");
            char jobType = in.nextLine().toLowerCase().charAt(0);
            Job job = new Job(jobType, ctr + client, jobName);
            synchronized (lock) {
                ctr++;
            }
            clientOut.println(job.toString());
        }
    }
}
