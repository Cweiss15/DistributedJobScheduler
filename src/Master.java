import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Master {
    private static SynchronizedJobQueue jobQueue = new SynchronizedJobQueue();
    private static SynchronizedJobQueue AJobs = new SynchronizedJobQueue();
    private static SynchronizedJobQueue BJobs = new SynchronizedJobQueue();
    private static SynchronizedJobQueue doneJobs = new SynchronizedJobQueue();
    private static Map<PrintWriter, Character> allClientWriters = new HashMap<>();


    public static void main(String[] args) {
        System.out.println("This is Master");
        args = new String[]{"31222", "31223"};

        int clientPort = Integer.parseInt(args[0]);
        int slavePort = Integer.parseInt(args[1]);

        try {
            ServerSocket clientServerSocket = new ServerSocket(clientPort);
            ServerSocket slaveServerSocket = new ServerSocket(slavePort);
            
            Socket slaveASocket = slaveServerSocket.accept();
            PrintWriter slaveAOut = new PrintWriter(slaveASocket.getOutputStream(), true);
            BufferedReader slaveAIn = new BufferedReader(
                    new InputStreamReader(slaveASocket.getInputStream()));
            System.out.println("Slave A connected");

            Socket slaveBSocket = slaveServerSocket.accept();
            PrintWriter slaveBOut = new PrintWriter(slaveBSocket.getOutputStream(), true);
            BufferedReader slaveBIn = new BufferedReader(
                    new InputStreamReader(slaveBSocket.getInputStream()));
            System.out.println("Slave B connected");

            //the thread takes the jobs off the Queue and assigns them to the correct slave
            Thread assignJobsThread = new Thread(new AssignJobsThread(jobQueue, AJobs, BJobs));
            assignJobsThread.start();

            //thread that takes jobs from the AJob list, waits for a done signal from the slave and sends the finished jobs back to the client
            Thread masterTOSlaveAThread = new Thread(new MasterToSlaveThread(slaveAIn, slaveAOut, AJobs, doneJobs));
            masterTOSlaveAThread.start();

            //thread that takes jobs from the BJob list, waits for a done signal from the slave and sends the finished jobs back to the client
            Thread masterTOSlaveBThread = new Thread(new MasterToSlaveThread(slaveBIn, slaveBOut, BJobs, doneJobs));
            masterTOSlaveBThread.start();

            //sends finished jobs back to all clients, takes jobs from donelist and sends to clients
            Thread masterToClientThread = new Thread(new MasterToClientThread(allClientWriters, doneJobs));
            masterToClientThread.start();

            System.out.println("Master ready for client connections on port " + clientPort);

            // Accept clients while the server socket is open (supports many clients)
            while (true) {
                Socket clientSocket = clientServerSocket.accept();
                PrintWriter clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader clientIn = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                System.out.println("Client connected");

                char clientType = clientIn.readLine().charAt(0);

                synchronized (allClientWriters) {
                    allClientWriters.put(clientOut, clientType);
                    System.out.println("Total connected clients: " + allClientWriters.size());
                }



                // The thread reads from the client and puts jobs on the queue
                Thread clientThread = new Thread(new MasterReceiveThread(jobQueue, clientIn, clientType));
                clientThread.setDaemon(true);
                clientThread.start();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}