import java.io.*;
import java.net.*;
import java.util.Properties;

public class SlaveB {
    // Configuration constants
    private static final long TYPE_B_PROCESSING_TIME_MS = 2000;
    private static final long TYPE_A_PROCESSING_TIME_MS = 10000;
    private static final String SLAVE_ID = "B";

    private static String masterHost;
    private static int masterPort;

    public static void main(String[] args) {
        System.out.println("This is Slave B.");

        // Load configuration from file if exists, otherwise use args
        Properties config = loadConfiguration("properties");

        if (config != null && config.containsKey("master.host") && config.containsKey("master.port")) {
            masterHost = config.getProperty("master.host");
            masterPort = Integer.parseInt(config.getProperty("master.port"));
        } else if (args.length >= 2) {
            masterHost = args[0];
            masterPort = Integer.parseInt(args[1]);
        } else {
            // Default to localhost:31223 (slave port)
            masterHost = "127.0.0.1";
            masterPort = 31223;
        }

        // display that slave has started
        System.out.println("Slave B starting...");
        System.out.println("Connecting to Master at " + masterHost + ":" + masterPort);

        // Connecting to Master, socket connection to master
        try (Socket masterSocket = new Socket(masterHost, masterPort);
             PrintWriter out = new PrintWriter(masterSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(masterSocket.getInputStream()))) {

            System.out.println("Slave B connected and ready.");

            String jobMessage;
            while ((jobMessage = in.readLine()) != null) {
                System.out.println("Slave B received job: " + jobMessage);

                // Parse the job data
                try {
                    Job job = new Job(jobMessage);
                    processJob(job, out);
                } catch (InterruptedException e) {
                    System.err.println("Job processing interrupted: " + e.getMessage());
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    System.err.println("Error processing job: " + e.getMessage());
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

    // Process a job based on its type
    private static void processJob(Job job, PrintWriter out) throws InterruptedException {
        char jobType = job.getType();
        String jobId = job.getId();

        System.out.println("Slave B processing job type: " + jobType);

        // configuration processing times (non-hard coded)
        switch (jobType) {
            case 'B':
                System.out.println("Optimal job for Slave B");
                Thread.sleep(TYPE_B_PROCESSING_TIME_MS);
                break;
            case 'A':
                System.out.println("Non-Optimal job for Slave B");
                Thread.sleep(TYPE_A_PROCESSING_TIME_MS);
                break;
            default:
                System.err.println("Unknown job type: " + jobType);
                return;
        }

        System.out.println("Slave B completed job " + jobId);
        out.println(job.toString());
    }

    // Load configuration from properties
    private static Properties loadConfiguration(String filename) {
        Properties properties = new Properties();

        // Try to load from current directory first
        File configFile = new File(filename);
        if (configFile.exists()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                properties.load(fis);
                return properties;
            } catch (IOException e) {
                System.err.println("Warning: Could not load configuration file: " + e.getMessage());
            }
        }
        return null;
    }
}