import java.io.*;
import java.net.*;
import java.util.*;

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
            String jobString;
            while ((jobString = clientIn.readLine()) != null) {
                Job job = new Job(jobString);
                // adds each job to the job list
                jobList.add(job);
                assignJobs(job, slaveAOut, slaveBOut);
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
    private static void assignJobs(Job job, PrintWriter slaveAOut, PrintWriter slaveBOut) {
        // if Slave B already has more then 5 more jobs then Slave A then assign the B
        // job to A
        if (countB > (countA + 5) && job.getType() == 'B') {
            System.out.println("Assign to Slave A");
            // Use MasterToSlaveAThread for the master to assign a job to slave A
            Thread threadA1 = new Thread(new MasterToSlaveAThread(slaveAOut, job));
            threadA1.start();
            // B job increases A's count by 5
            countA += 5;
            // if Slave A already has more then 5 more jobs then B
        } else if (countA > (countB + 5) && job.getType() == 'A') {
            System.out.println("Assign to Slave B"); // assign the A job to B
            // Use MasterToSlaveBThread for the master to assign a job to slave B
            Thread threadB1 = new Thread(new MasterToSlaveBThread(slaveBOut, job));
            threadB1.start();
            // A job increases B's count by 5
            countB += 5;
        }
        // otherwise assign normally
        else {
            if (job.getType() == 'A') {
                System.out.println("Assign to Slave A");
                // Use MasterToSlaveAThread for the master to assign a job to slave A
                Thread threadA2 = new Thread(new MasterToSlaveAThread(slaveAOut, job));
                threadA2.start();
                countA++;
            } else {
                System.out.println("Assign to Slave B");
                // Use MasterToSlaveBThread for the master to assign a job to slave B
                Thread threadB2 = new Thread(new MasterToSlaveBThread(slaveBOut, job));
                threadB2.start();
                countB++;
            }
        }
    }
}