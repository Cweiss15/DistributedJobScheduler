public class AssignJobsThread implements Runnable {
    private  int countA = 0;
    private   int countB = 0;
    private SynchronizedJobQueue jobList;
    private SynchronizedJobQueue AJobs;
    private SynchronizedJobQueue BJobs;
    private boolean forever = true;

    public AssignJobsThread(SynchronizedJobQueue jobList, SynchronizedJobQueue AJobs, SynchronizedJobQueue BJobs) {
        this.jobList = jobList;
        this.AJobs = AJobs;
        this.BJobs = BJobs;
    }

    public void run() {
        //test
        System.out.println("AssignJobsThread started");
        System.out.flush();
        while (forever) {
            Job job = jobList.poll();
            System.out.println("AssignJobsThread got job: " + job);
            // if Slave B already has more than 5 more jobs than Slave A then assign the B
            // job to A
            if (countB > (countA + 5) && job.getType() == 'B') {
                System.out.println("Assign to Slave A (job type B)");
                //  assign a job to slave A by adding it to the AJobs ArrayList
                AJobs.add(job);
                // B job increases A's count by 5
                countA += 5;
                // if Slave A already has more than 5 more jobs than B+
            } else if (countA > (countB + 5) && job.getType() == 'A') {
                System.out.println("Assign to Slave B (job type A)"); // assign the A job to B
                //  assign a job to slave B by adding it to the BJobs ArrayList
                BJobs.add(job);
                // A job increases B's count by 5
                countB += 5;
            }
            // otherwise assign normally
            else {
                if (job.getType() == 'A') {
                    System.out.println("Assign to Slave A job type A");
                    //  assign a job to slave A by adding it to the AJobs ArrayList
                    AJobs.add(job);
                    countA++;
                } else {
                    System.out.println("Assign to Slave B job type B");
                    // assign a job to slave B by adding it to the BJobs ArrayList
                    BJobs.add(job);
                    countB++;
                }
            }
        }
    }
}