import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {

        // Setup port
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String clientName = args[2];
        args = new String[]{"127.0.0.1", "31222", clientName};

        if (args.length != 3) {
            System.err.println("Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }
        // This is to distinguish Client and Master in console
        System.out.println("This is Client " + clientName);

        // Setup client server communication
        try (Socket masterSocket = new Socket(hostName, portNumber);
             PrintWriter masterOut = new PrintWriter(masterSocket.getOutputStream(), true);
             BufferedReader masterIn = new BufferedReader(new InputStreamReader(masterSocket.getInputStream()))) {

            //Tell master which client this is
            masterOut.println(clientName);
            masterOut.flush();

            String sendJob;
            char client = clientName.charAt(0);
            SynchronizedJobQueue jobQueue = new SynchronizedJobQueue();

            //Start thread to send jobs to master
            Thread sendToMaster = new ClientToMasterThread(jobQueue, masterOut, client);
            sendToMaster.start();
            //Start thread to receive back done jobs
            Thread clientReceiveThread = new Thread(new ClientReceiveThread(jobQueue, masterIn, client));
            clientReceiveThread.start();
            int ctr = 1;
            char jobType = 'X';
            //while client doesnt want to end
            while (jobType != 'D') {
                //Keep asking for jobs
                Scanner in = new Scanner(System.in);
                System.out.println("Enter job type, A or B, for job " + ctr + " (or done to exit): ");
                jobType = in.next().toUpperCase().charAt(0);
                //input validation
                while (jobType != 'A' && jobType != 'B' && jobType != 'D') {
                    System.out.println("Enter job type, A or B, for job " + ctr + ": ");
                    jobType = in.next().toUpperCase().charAt(0);
                }
                if (jobType == 'D') {
                    continue;
                }
                //Create job with type and id made of number and client name
                Job job = new Job(jobType, ("" + ctr + client));
                jobQueue.add(job);
                ctr++;
            }

        } catch (UnknownHostException var50) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException var51) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}
