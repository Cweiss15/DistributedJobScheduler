import java.io.*;
import java.net.*;

public class SlaveB {
    private static String masterHost;
    private static int masterPort;

    public static void main(String[] args) {
        System.out.println("This is Slave B.");

        // Default to localhost:31223 (slave)
        args = new String[]{"127.0.0.1", "31223"};

        // Parse connection of masterHost and masterPort
        masterHost = args[0];
        masterPort = Integer.parseInt(args[1]);

        System.out.println("Slave B starting...");
        System.out.println("Connecting to Master at " + masterHost + ":" + masterPort);

        // Establish connection to Master
        //creating input and output streams for communication
        try (Socket masterSocket = new Socket(masterHost ,masterPort);
             PrintWriter out = new PrintWriter(masterSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader( new InputStreamReader(masterSocket.getInputStream()))) {

            System.out.println("Slave B connected and ready.");

            // Main processing loop, receives and processes jobs from Master
            String jobMessage;
            while ((jobMessage = in.readLine()) != null) {
                Job job = new Job(jobMessage);
                System.out.println("Slave B received job: " + job.toPrint());

                try {
                    char jobType = job.getType();
                    String jobId = job.getId();

                    System.out.println("Slave B processing job type:" + jobType);

                    // Optimization for type A jobs
                    if (jobType == 'A') {
                        System.out.println("Non-Optimal job for Slave B");
                        Thread.sleep(10000);
                    } else if (jobType == 'B') {
                        System.out.println("Optimal job for Slave B");
                        Thread.sleep(2000);
                    } else {
                        System.err.println("Unknown job type: " + jobType);
                        continue;
                    }
                    // Send back the completed job to Master
                    System.out.println("Slave B completed job " + jobId);
                    out.println(job.toString());

                } catch (InterruptedException e) {
                    System.err.println("Job processing interrupted: " + e.getMessage());
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    System.err.println("Error processing job: " + e.getMessage());
                    e.printStackTrace();
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + masterHost);
            System.err.println("Error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error connecting to Master at " + masterHost + ":" + masterPort);
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }

    }
}


