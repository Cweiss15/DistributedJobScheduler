import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class MasterToClientThread implements Runnable {
    private Map<PrintWriter, Character> clientWriters;
    private SynchronizedJobQueue doneJobs;

    public MasterToClientThread(Map<PrintWriter, Character> clientWriters, SynchronizedJobQueue doneJobs) {
        this.clientWriters = clientWriters;
        this.doneJobs = doneJobs;
    }

    @Override
    public void run() {
        while (true) {
            // get the next done job from the doneJobs queue
            Job job = doneJobs.poll();
            char[] splitID = job.getId().toCharArray();
            char clientToSend = splitID[1];
            System.out.println("Master sending done job to client " + clientToSend +": " + job.toPrint());
            
            // Send to all connected clients
            synchronized (clientWriters) {
                for (Map.Entry<PrintWriter, Character> entry : clientWriters.entrySet()) {
                    entry.getKey().println(job);
                    entry.getKey().flush();
                }
            }
        }
    }
}
