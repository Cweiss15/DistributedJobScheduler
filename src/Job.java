public class Job {
    private enum JobType {
        A,
        B,
    }
    private JobType type;


    public JobType getType() {
        return type;
    }
    public void setType(JobType type) {
        this.type = type;
    }

}
