import java.time.LocalDateTime;

/**
 * Event is a subclass of Task.
 * Event describes something that is happening
 * at a certain time.
 */
public class Event extends Task {
    private String eventDateString;
    private LocalDateTime eventDate;

    /**
     * Constructs a Event object.
     * @param description Description of the event.
     * @param eventDate Date of the event.
     */
    public Event(String description, String eventDate) {
        super(description);
        this.eventDateString = makeEventDate(eventDate);
        this.eventDate = storeAsDateTime(eventDateString);
    }

    /**
     * Constructs a Event object.
     * This constructor is for when the Event is being loaded
     * from memory and hence can be already completed and the
     * status of completion needs to be a parameter as well.
     * @param description Description of the task
     * @param deadline Date of the deadline
     * @param status Status of completion
     */
    public Event(String description, String eventDate, boolean status) {
        super(description);
        this.eventDateString = makeEventDate(eventDate);
        this.eventDate = storeAsDateTime(eventDateString);
        this.isDone = status;
    }

    /**
     * Formats the String date.
     * @param eventDate Unformatted String date
     * @return String Formatted String date
     */
    public String makeEventDate(String eventDate) {
        StringBuilder temp = new StringBuilder();
        String[] eventDateArr = eventDate.split(" ");

        temp.append(eventDateArr[0]);

        if (!eventDateArr[0].contains(":")) {
            temp.append(":");
        }

        for (int i = 1; i < eventDateArr.length; i++) {
            temp.append(" ");
            temp.append(eventDateArr[i]);
        }

        return temp.toString();
    }

    /**
     * Converting to a format to be stored on file.
     * Task is converted to a string that is stored on the
     * hard disk, and can be read easily when loaded so that
     * the information can be loaded onto the Task List when the
     * program first starts.
     * @return String Formatted string to be stored.
     */
    @Override
    public String toFileFormat() {
        StringBuilder fileFormat = new StringBuilder();

        fileFormat.append("E~");

        if (this.isDone) {
            fileFormat.append("1~");
        } else {
            fileFormat.append("0~");
        }

        fileFormat.append(this.description);
        fileFormat.append("~");
        fileFormat.append(this.eventDateString);

        return fileFormat.toString();
    }

    @Override
    public String toString() {
        String task = "[E][" + this.getStatusIcon() + "] " + this.description + " (" + eventDateString + ")";
        return task;
    }
}