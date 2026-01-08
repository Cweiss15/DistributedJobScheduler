import java.io.*;
import java.net.*;

public class SlaveB {
    private static String masterHost;
    private static int masterPort;

    public static void main(String[] args) {
        System.out.println("This is Slave B.");

        // Default to localhost:31222 if no args provided
        args = new String[]{"127.0.0.1", "31222"};
        
        //get the master host and port number
        masterHost = args[0];
        masterPort = Integer.parseInt(args[1]);

        // display to console that slave has started
        System.out.println("Slave B starting...");
        System.out.println("Connecting to Master at " + masterHost + ":" + masterPort);

        //connecting to Master, socket connection to master
        //creating input and output streams for communication
        try (Socket masterSocket = new Socket(masterHost ,masterPort);
             PrintWriter out = new PrintWriter(masterSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader( new InputStreamReader(masterSocket.getInputStream()))) {

            // inform master that this slave is ready to receive jobs
            //out.println("READY:B");
            System.out.println("Slave B connected and ready.");

            String jobMessage;
            while ((jobMessage = in.readLine()) != null) {
                System.out.println("Slave B received job: " + jobMessage);

                // Parse the job data
                try {
                    Job job = new Job(jobMessage);
                    char jobType = job.getType();
                    String jobId = job.getId();

                    System.out.println("Slave B processing job type:" + jobType);

                    // optimization for type B jobs
                    if (jobType == 'B') {
                        System.out.println("Optimal job for Slave B");
                        Thread.sleep(2000);
                    } else if (jobType == 'A') {
                        System.out.println("Non-Optimal job for Slave B");
                        Thread.sleep(10000);
                    } else {
                        System.err.println("Unknown job type: " + jobType);
                        continue;
                    }

                    System.out.println("Slave B completed job " + jobId);
                    // Send back the completed job in the same format
                    out.println(job.toString());
                } catch (InterruptedException e) {
                    System.err.println("Job processing interrupted: " + e.getMessage());
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    System.err.println("Error processing job: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            // TODO another println for testing

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



