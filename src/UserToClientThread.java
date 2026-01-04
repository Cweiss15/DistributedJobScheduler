import java.io.PrintWriter;
import java.util.*;
//Make it socket and connect to the client and send it the jobs
public class UserToClientThread extends Thread {
    private Object lock;
    private PrintWriter clientOut;
    private char client = 'A';
    public UserToClientThread(PrintWriter clientOut, Object lock, char client) {
        this.clientOut = clientOut;
        this.lock = lock;
        this.client = client;
    }
    public void run() {
        int ctr = 1;
        char jobType = 'X';
        while (jobType!='D') {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter job type, A or B, for job " + ctr +" (or done to exit): ");
            jobType = in.nextLine().toUpperCase().charAt(0);
            Job job = new Job(jobType, (""+ ctr + client));
            synchronized (lock) {
                ctr++;
            }
            clientOut.println(job.toString());
        }
    }
}
