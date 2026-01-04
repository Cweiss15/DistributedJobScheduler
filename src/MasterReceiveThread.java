import java.io.BufferedReader;

public class MasterReceiveThread extends Thread {
    private SynchronizedJobQueue jobs;
    private BufferedReader clientIn;
    private boolean forever = true;

    public MasterReceiveThread(SynchronizedJobQueue jobs, BufferedReader clientIn) {
        this.jobs = jobs;
        this.clientIn = clientIn;
    }

    public void run() {
        while (forever) {
            try {
                String line = clientIn.readLine();
                if (line != null) {
                    System.out.println("Master received job from client: " + line);
                    Job job = new Job(line);
                    jobs.add(job);
                }
            } catch (Exception e) {
                System.out.println("Error reading from client: " + e.getMessage());
            }
        }
    }
}
