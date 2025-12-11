import java.io.*;
import java.net.*;

public class Slave {

    private static String slaveType;
    private static String masterHost;
    private static int masterPort;

    public static void main(String[] args) {
        // Program startup
        System.out.println("This is Slave.");
        //checking to see slavetype, master hostname, and masterport
        if (args.length != 3) {
            System.out.println("Usage: Slave A or B, MasterHost, MasterPort");
            return;
        }
        // Read command line to determine if this should be slave A or B
        slaveType = args[0].toUpperCase();
        // also get the port number
        masterHost = args[1];
        masterPort = Integer.parseInt(args[2]);

        //validation for slave type
        if (!slaveType.equals("A") && !slaveType.equals("B")) {
            System.out.println("Slave must be type A or type B.");
            return;
        }

        // display to console what type of slave has started

        //connecting to Master

        // socket connection to master

        // input and output streams for communication

        // inform master that this slave is ready to receive jobs


        // Main processing loop
        // loop

        // wait for a job message from the master

        // parse the job data

        //display to console, "job received"

        //simulating job work, either sleep for 2 seconds or sleep for 10 seconds

        //display to console that job is being processed

        // handle job completion

        // put error handling here


    }
}

