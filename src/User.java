import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class User {
    public static void main(String[] args) {
        System.out.println("This is User");
        args = new String[]{"31223", ""};

        int portNumber = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(portNumber);
             Socket clientSocket = serverSocket.accept();
             PrintWriter clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader clientIn = new BufferedReader(
                     new InputStreamReader(clientSocket.getInputStream()))) {
            final Object lock = new Object();
            Thread clientA = new UserToClientThread(clientOut, lock, 'A');
            Thread clientB =  new UserToClientThread(clientOut, lock, 'B');
            clientA.start();
            clientB.start();

            String doneJob;
            while (true) {
                doneJob = clientIn.readLine();
                if (doneJob != null) {
                    System.out.println("completed job: " + doneJob);
                }
            }

        } catch(Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
