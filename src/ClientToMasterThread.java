//Devora Sokol - T00554071
//Sonya Ginzburg - T00543210
//Chana Weiss - T00564695

import java.io.PrintWriter;

public class ClientToMasterThread extends Thread {
    private SynchronizedJobQueue jobs;
    private PrintWriter masterOut;
    private char clientType;

    //This thread sends user's jobs from client to master
    public ClientToMasterThread(SynchronizedJobQueue jobs, PrintWriter masterOut, char clientType) {
        this.jobs = jobs;
        this.masterOut = masterOut;
        this.clientType = clientType;
    }

    public void run() {
        while (true) {
            //take first job off queue
            Job job = jobs.poll();
            //Send it to the master
            masterOut.println(job.toString());
            masterOut.flush();
            System.out.println("Client sending job to master: " + job.toPrint());

        }
    }
}
