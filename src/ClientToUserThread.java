import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ClientToUserThread {
    private PrintWriter userOut;
    private SynchronizedJobQueue doneJobs;
    private char client;

    public ClientToUserThread(SynchronizedJobQueue doneJobs, PrintWriter userOut, char client) {
        this.userOut = userOut;
        this.doneJobs = doneJobs;
        this.client = client;

    }

    public void run() {
        while (true) {
            Job job = doneJobs.poll();
            System.out.println("Client sending done job to user: " + job);
            userOut.println(job);
            userOut.flush();
        }
    }
    }
