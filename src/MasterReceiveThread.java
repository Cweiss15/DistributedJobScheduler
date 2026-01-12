import java.io.BufferedReader;

public class MasterReceiveThread implements Runnable {
    private SynchronizedJobQueue jobs;
    private BufferedReader clientIn;
    private boolean forever = true;
    private char clientType;

    public MasterReceiveThread(SynchronizedJobQueue jobs, BufferedReader clientIn, char clientType) {
        this.jobs = jobs;
        this.clientIn = clientIn;
        this.clientType = clientType;
    }

    public void run() {
        System.out.println("MasterReceiveThread started and waiting for jobs from client " + clientType);
        System.out.flush();
        while (forever) {
            try {
                // read in a line from the client to put on the job queue
                String line = clientIn.readLine();
                if (line != null) {
                    Job job = new Job(line);
                    System.out.println("Master received job from client: " + job.toPrint());
                    System.out.flush();
                    jobs.add(job);
                }
            } catch (Exception e) {
                System.out.println("Error reading from client: " + e.getMessage());
            }
        }
    }
}
