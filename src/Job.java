public class Job {
    private char type;
    private final String id;

    public Job(char type, String id) {
        this.type = type;
        this.id = id;
    }

    public Job(String jobString) {
        String[] parts = jobString.split("\\|");
        this.type = parts[0].charAt(0);
        this.id = parts[1];
    }

    public char getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String toPrint() {
        return ("Job ID: " + id + " Type: " + type);
    }

    @Override
    public String toString() {
        return type + "|" + id + "|";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Job job = (Job) obj;
        return type == job.type && id.equals(job.id);
    }
}