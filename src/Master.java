import java.io.*;
import java.net.*;
import java.util.*;

public class Master {
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
                assignJobs(jobList, slaveAOut, slaveBOut);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void assignJobs(ArrayList<Job> jobList, PrintWriter slaveAOut, PrintWriter slaveBOut) {
        for (int i = 0; i < jobList.size(); i++) {
            Job job = jobList.get(i);
            if (job.getType() == 'A') {
                System.out.println("Assign to Slave A");
                slaveAOut.println(job);

            } else if (job.getType() == 'B') {
                System.out.println("Assign to Slave B");
                slaveBOut.println(job);
            }

        }
    }
}
