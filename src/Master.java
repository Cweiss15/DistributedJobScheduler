import java.io.*;
import java.net.*;
import java.util.*;

public class Master {
    public static void main(String[] args) {
        System.out.println("Master");

        // default argument used for quick local testing;
        args = new String[] { "31222", "" };

        if (args.length < 2) {
            System.err.println("Usage: java message <port number> <message>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        // Test mode - comment out to use real socket connection
        String jobString = "A|B|A|A|B|A|B|B|A|B"; // pretend string for testing

        // The server uses try-with-resources so sockets/streams are closed
        // automatically.

        /*
         * try (
         * Socket msgSocket = new Socket("",portNumber);
         * PrintWriter out =
         * new PrintWriter(msgSocket.getOutputStream(), true);
         * BufferedReader in =
         * new BufferedReader(
         * new InputStreamReader(msgSocket.getInputStream()));
         * ) {
         */

        ArrayList<String> jobList = new ArrayList<>();

        // Read jobs from client (format: "A|B|A|B|...")
        // String jobString = in.readLine(); // uncomment for real connection
        if (jobString != null) {
            // splits the job string into individual jobs
            String[] jobs = jobString.split("\\|");
            // adds each job to the job list
            for (String job : jobs) {
                jobList.add(job.trim());
            }
        }

        System.out.println("Total jobs " + jobList.size());
        System.out.println("Jobs: " + jobList);

        assignJobs(jobList);

        /*
         * } catch (IOException e) {
         * System.err.println("Error: " + e.getMessage());
         * }
         */

    }

    private static void assignJobs(ArrayList<String> jobList) {
        for (int i = 0; i < jobList.size(); i++) {
            String job = jobList.get(i);
            if(job.equals("A")){
                System.out.println("Assign to worker A");
            } else if (job.equals("B")){
                System.out.println("Assign to worker B");
            }
            
        }
    }
}
