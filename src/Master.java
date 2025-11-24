import java.io.*;
import java.net.*;
import java.util.*;

public class Master {
    private static int countA = 0;
    private static int countB = 0;

    public static void main(String[] args) {
        System.out.println("This is Master");
        // default argument used for quick local testing;
        args = new String[] { "31222", "" };

        if (args.length < 2) {
            System.err.println("Usage: java message <port number> <message>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        // The server uses try-with-resources so sockets/streams are closed
        // automatically.
        try (ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader clientIn = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                Socket slaveASocket = new Socket("", portNumber);
                PrintWriter slaveAOut = new PrintWriter(slaveASocket.getOutputStream(), true);
                BufferedReader slaveAIn = new BufferedReader(
                        new InputStreamReader(slaveASocket.getInputStream()));

                Socket slaveBSocket = new Socket("", portNumber);
                PrintWriter slaveBOut = new PrintWriter(slaveBSocket.getOutputStream(), true);
                BufferedReader slaveBIn = new BufferedReader(
                        new InputStreamReader(slaveBSocket.getInputStream()));) {

            ArrayList<Job> jobList = new ArrayList<>();
            String jobString;
            while ((jobString = clientIn.readLine()) != null) {
                // splits the job string into individual jobs
                String[] onejob = jobString.split("\\|");
                Job job = new Job(onejob[0].charAt(0), Integer.parseInt(onejob[1]));
                // adds each job to the job list
                jobList.add(job);
                assignJobs(job, slaveAOut, slaveBOut);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void assignJobs(Job job, PrintWriter slaveAOut, PrintWriter slaveBOut) {
        // if Slave B already has more then 5 more jobs then Slave A then assign the B
        // job to A
        if (countB > (countA + 5) && job.getType() == 'B') {
            System.out.println("Assign to Slave A");
            slaveAOut.println(job);
            // B job increases A's count by 5
            countA += 5;
        } else if (countA > (countB + 5) && job.getType() == 'A') { // if Slave A already has more then 5 more jobs then
                                                                    // B
            System.out.println("Assign to Slave B"); // assign the A job to B
            slaveBOut.println(job);
            // A job increases B's count by 5
            countB += 5;
        }
        // otherwise assign normally
        else {
            if (job.getType() == 'A') {
                System.out.println("Assign to Slave A");
                slaveAOut.println(job);
                countA++;
            }

            else {
                System.out.println("Assign to Slave B");
                slaveBOut.println(job);
                countB++;
            }
        }
    }
}