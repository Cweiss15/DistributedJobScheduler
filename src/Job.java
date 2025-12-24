public class Job {
    private char type;
    private final int id;

    public Job(char type, int id, String name) {
        this.type = type;
        this.id = id;
    }
    public Job(String jobString) {
        String[] parts = jobString.split("\\|");
        this.type = parts[0].charAt(0);
        this.id = Integer.parseInt(parts[1]);
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public int getId() {
        return id;
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
        return type == job.type && id == job.id;
    }
}