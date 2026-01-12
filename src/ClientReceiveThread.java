import java.io.BufferedReader;

public class ClientReceiveThread implements Runnable {
    private SynchronizedJobQueue doneJobs;
    private BufferedReader masterIn;
    private boolean forever = true;
    private char clientType;

    public ClientReceiveThread(SynchronizedJobQueue doneJobs, BufferedReader masterIn, char clientType) {
        this.doneJobs = doneJobs;
        this.masterIn = masterIn;
        this.clientType = clientType;
    }

    public void run() {
        System.out.println("ClientReceiveThread started and waiting for done jobs from master");
        System.out.flush();
        String doneJob;
        while (forever) {
            try {
                while ((doneJob = masterIn.readLine()) != null) {
                    Job job = new Job(doneJob);
                    System.out.println("completed job: " + job.toPrint());

                }
            } catch (Exception e) {
                System.out.println("Error reading from client: " + e.getMessage());
            }
        }
    }
}