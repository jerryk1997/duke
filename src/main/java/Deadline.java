import java.time.LocalDateTime;

public class Deadline extends Task {
    private String deadlineString;
    private LocalDateTime deadlineDate;

    public Deadline(String description, String deadline) {
        super(description);

        this.deadlineString = makeDeadline(deadline);
        this.deadlineDate = storeAsDateTime(deadlineString);
    }

    public Deadline(String description, String deadline, boolean status) {
        super(description);
        this.deadlineString = makeDeadline(deadline);
        this.deadlineDate = storeAsDateTime(deadlineString);
        this.isDone = status;
    }

    public String makeDeadline(String deadline) {
        StringBuilder temp = new StringBuilder();
        String[] deadlineArr = deadline.split(" ");

        temp.append(deadlineArr[0]);
        if (!deadlineArr[0].contains(":")) {
            temp.append(":");
        }

        for (int i = 1; i < deadlineArr.length; i++) {
            temp.append(" ");
            temp.append(deadlineArr[i]);
        }

        return temp.toString();
    }

    @Override
    public String toFileFormat() {
        StringBuilder fileFormat = new StringBuilder();

        fileFormat.append("D~");

        if (this.isDone) {
            fileFormat.append("1~");
        } else {
            fileFormat.append("0~");
        }

        fileFormat.append(this.description);
        fileFormat.append("~");
        fileFormat.append(this.deadlineString);

        return fileFormat.toString();
    }

    @Override
    public String toString() {
        String task = "[D][" + this.getStatusIcon() + "] " + description + " (" + deadlineString + ")";
        return task;
    }
}