import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Master {
    private static int countA = 0;
    private static int countB = 0;

    public static void main(String[] args) {
        System.out.println("This is Master");
        args = new String[]{"31222", ""};

        int portNumber = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(portNumber);
             Socket clientSocket = serverSocket.accept();
             PrintWriter clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader clientIn = new BufferedReader(
                     new InputStreamReader(clientSocket.getInputStream()));

             Socket slaveASocket = serverSocket.accept();
             PrintWriter slaveAOut = new PrintWriter(slaveASocket.getOutputStream(), true);
             BufferedReader slaveAIn = new BufferedReader(
                     new InputStreamReader(slaveASocket.getInputStream()));

             Socket slaveBSocket = serverSocket.accept();
             PrintWriter slaveBOut = new PrintWriter(slaveBSocket.getOutputStream(), true);
             BufferedReader slaveBIn = new BufferedReader(
                     new InputStreamReader(slaveBSocket.getInputStream()))) {

            ArrayList<Job> jobList = new ArrayList<>();

            BlockingDeque<Job> readyQueue = new LinkedBlockingDeque<>();
            BlockingDeque<Job> doneQueue = new LinkedBlockingDeque<>();

            String jobString;

            //the thread that reads from the client should put the jobs on the readyQueue
            while ((jobString = clientIn.readLine()) != null) {
                Job job = new Job(jobString);
                // adds each job to the job list
                jobList.add(job);
                assignJob(job, slaveAOut, slaveBOut);
            }
            String doneJobAString;
            while ((doneJobAString = slaveAIn.readLine()) != null) {
                sendDoneJobToClient(clientOut, doneJobAString, jobList);
            }
            String doneJobBString;
            while ((doneJobBString = slaveBIn.readLine()) != null) {
                sendDoneJobToClient(clientOut, doneJobBString, jobList);
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    //sends finished jobs back to the client, removes finished jobs from the job list
    private static void sendDoneJobToClient(PrintWriter clientOut, String doneJob, ArrayList<Job> jobList) {
        Job job = new Job(doneJob);
        Thread clientOutThread = new Thread(new MasterToClientThread(clientOut, job));
        clientOutThread.start();
        jobList.remove(job);
    }

    // assigns jobs to the correct slave
    private static void assignJob(Job job, PrintWriter slaveAOut, PrintWriter slaveBOut) {
        PrintWriter masterToSlaveOut = null;
        // if Slave B already has more than 5 more jobs than Slave A then assign the B
        // job to A
        if (countB > (countA + 5) && job.getType() == 'B') {
            System.out.println("Assign to Slave A");
            //  assign a job to slave A
            masterToSlaveOut = slaveAOut;
            // B job increases A's count by 5
            countA += 5;
            // if Slave A already has more than 5 more jobs than B
        } else if (countA > (countB + 5) && job.getType() == 'A') {
            System.out.println("Assign to Slave B"); // assign the A job to B
            //  assign a job to slave B
            masterToSlaveOut = slaveBOut;
            // A job increases B's count by 5
            countB += 5;
        }
        // otherwise assign normally
        else {
            if (job.getType() == 'A') {
                System.out.println("Assign to Slave A");
                //  assign a job to slave A
                masterToSlaveOut = slaveAOut;
                countA++;
            } else {
                System.out.println("Assign to Slave B");
                // assign a job to slave B
                masterToSlaveOut = slaveBOut;
                countB++;
            }
        }
        // Use MasterToSlaveThread for the master to assign to the proper slave
        Thread masterToSlaveThread = new Thread(new MasterToSlaveThread(masterToSlaveOut, job));
        masterToSlaveThread.start();
    }
}