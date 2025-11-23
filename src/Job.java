public class Job {
    private char type;
    private int id;

    public Job(char type, int id) {
        this.type = type;
        this.id = id;
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
}