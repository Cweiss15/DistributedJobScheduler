import java.io.PrintWriter;
import java.util.List;

public class MasterToClientThread implements Runnable {
    private List<PrintWriter> clientWriters;
    private SynchronizedJobQueue doneJobs;

    public MasterToClientThread(List<PrintWriter> clientWriters, SynchronizedJobQueue doneJobs) {
        this.clientWriters = clientWriters;
        this.doneJobs = doneJobs;
    }

    @Override
    public void run() {
        while (true) {
            // get the next done job from the doneJobs queue
            Job job = doneJobs.poll();
            System.out.println("Master sending done job to all clients: " + job);
            
            // Send to all connected clients
            synchronized (clientWriters) {
                for (PrintWriter clientOut : clientWriters) {
                    clientOut.println(job);
                    clientOut.flush();
                }
            }
        }
    }
}
