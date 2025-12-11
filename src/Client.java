import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

public class Client {
    public static void main(String[] args) throws IOException {
        //This is to destinguish Client and Master in console
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
                (Socket serverSocket = new Socket(hostName, portNumber);
                 PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                 BufferedReader input = new BufferedReader(new InputStreamReader(System.in))) {
            Random random = new Random();
            ArrayList<Job> jobs = new ArrayList<>();
            char type;
            for (int i = 0; i < 50; i++) {
                double probability = random.nextDouble();
                if (probability <= 0.5)
                    type = 'A';
                else
                    type = 'B';
                Job job = new Job(type, i);
                jobs.add(job);
            }

            for (Job job : jobs) {
                out.println(job);
            }

            String jobString;
            while ((jobString = in.readLine()) != null) {
                // splits the job string into individual jobs
                String[] onejob = jobString.split("\\|");
                Job job = new Job(onejob[0].charAt(0), Integer.parseInt(onejob[1]));
               System.out.println("Job" + job.getId() + "has been completed.");
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
