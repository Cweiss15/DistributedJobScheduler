import java.io.PrintWriter;
import java.util.Map;

public class MasterToClientThread implements Runnable {
    private Map<PrintWriter, Character> clientWriters;
    private SynchronizedJobQueue doneJobs;
    //This thread is used by master to send done jobs to the client
    public MasterToClientThread(Map<PrintWriter, Character> clientWriters, SynchronizedJobQueue doneJobs) {
        this.clientWriters = clientWriters;
        this.doneJobs = doneJobs;
    }

    @Override
    public void run() {
        while (true) {
            // get the next done job from the doneJobs queue
            Job job = doneJobs.poll();
            //Get which client is sending to
            char[] splitID = job.getId().toCharArray();
            char clientToSend = splitID[1];
            System.out.println("Master sending done job to client " + clientToSend +": " + job.toPrint());
            
            //Loop through connected clients
                for (Map.Entry<PrintWriter, Character> entry : clientWriters.entrySet()) {
                    //If client name matches job ID then send to the client
                    if(entry.getValue() == clientToSend) {
                        entry.getKey().println(job);
                        entry.getKey().flush();
                    }

                }

        }
    }
}
