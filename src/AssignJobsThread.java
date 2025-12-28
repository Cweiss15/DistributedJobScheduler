import java.util.*;

public class AssignJobsThread implements Runnable {
    private static int countA = 0;
    private static int countB = 0;
    private Queue<Job> jobList;
    private Queue<Job> AJobs = new LinkedList<>();
    private Queue<Job> BJobs = new LinkedList<>();
    private boolean forever = true;

    public AssignJobsThread(Queue<Job> jobList, Queue<Job> AJobs, Queue<Job> BJobs) {
        this.jobList = jobList;
        this.AJobs = AJobs;
        this.BJobs = BJobs;
    }

    public void run() {
        //test
        System.out.println("Assigning Jobs");
        while (forever) {
            while (jobList.isEmpty()){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
            Job job = jobList.poll();
            System.out.println("AssignJobsThread got job: " + job);
            // if Slave B already has more than 5 more jobs than Slave A then assign the B
            // job to A
            if (countB > (countA + 5) && job.getType() == 'B') {
                System.out.println("Assign to Slave A");
                //  assign a job to slave A by adding it to the AJobs ArrayList
                AJobs.add(job);
                // B job increases A's count by 5
                countA += 5;
                // if Slave A already has more than 5 more jobs than B+
            } else if (countA > (countB + 5) && job.getType() == 'A') {
                System.out.println("Assign to Slave B"); // assign the A job to B
                //  assign a job to slave B by adding it to the BJobs ArrayList
                BJobs.add(job);
                // A job increases B's count by 5
                countB += 5;
            }
            // otherwise assign normally
            else {
                if (job.getType() == 'A') {
                    System.out.println("Assign to Slave A");
                    //  assign a job to slave A by adding it to the AJobs ArrayList
                    AJobs.add(job);
                    countA++;
                } else {
                    System.out.println("Assign to Slave B");
                    // assign a job to slave B by adding it to the BJobs ArrayList
                    BJobs.add(job);
                    countB++;
                }
            }
        }
    }
}