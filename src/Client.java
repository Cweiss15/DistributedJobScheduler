import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws IOException {
        //This is to distinguish Client and Master in console
        System.out.println("This is Client");
        //Setup port
        args = new String[]{"127.0.0.1", "31222"};
        if (args.length != 2) {
            System.err.println("Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        //Setup client server communication
        try
                (Socket masterSocket = new Socket(hostName, portNumber);
                 PrintWriter masterOut = new PrintWriter(masterSocket.getOutputStream(), true);
                 BufferedReader masterIn = new BufferedReader(new InputStreamReader(masterSocket.getInputStream()));

                Socket clientSocket = new Socket(hostName, 31223);
                PrintWriter userOut = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader userIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            //Should job have a title?
            //Should id be a string?
            //Read in from client (should this also be a thread)
            //Send to master (is a thread)
            //Read from master when done, tell user, user prints done (is one or two threads)?
            String sendJob;
            SynchronizedJobQueue jobQueue = new SynchronizedJobQueue();
            Thread sendToMaster = new ClientToMasterThread(jobQueue, masterOut);
            sendToMaster.start();
            while ((sendJob = userIn.readLine()) != null) {
                Job job = new Job(sendJob);
                jobQueue.add(job);
            }
            String doneJob;
            while ((doneJob = masterIn.readLine()) != null) {
                userOut.println(doneJob);
            }

        } catch(UnknownHostException var50){
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch(IOException var51){
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}
