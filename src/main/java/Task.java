import java.util.LinkedList;
import java.util.ListIterator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    protected String description;
    protected boolean isDone;
    protected static LinkedList<Task> taskList = new LinkedList<>();

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        if (isDone) {
            return "\u2713";
        } else {
            return "\u2718";
        }
    }

    public static void addTask(Task task) {
        taskList.add(task);

        printLine();

        printIndent();
        System.out.println("Got it. I've added this task:");
        printIndent();
        System.out.println("  " + task.toString());
        printIndent();
        if (taskList.size() == 1) {
            System.out.println("Now you have 1 task in the list.");
        } else {
            System.out.println("Now you have " + taskList.size() + " tasks in the list.");
        }
        printLine();
    }

    public void done() {
        this.isDone = true;
    }

    public static void doTask(int num) {
        if (taskList.get(num - 1).isDone) {
            printLine();

            printIndent();
            System.out.println("This task has already been done.");
            printIndent();
            System.out.println("  " + taskList.get(num - 1).toString());

            printLine();
        } else {
            taskList.get(num - 1).done();

            printLine();

            printIndent();
            System.out.println("Nice! I've marked this task as done:");
            printIndent();
            System.out.println("  " + taskList.get(num - 1).toString());

            printLine();
        }
    }

    public static void printTaskList() {
        if (taskList.size() == 0) {
            printLine();

            printIndent();
            System.out.println("There are no tasks in the list currently.");

            printLine();
        } else {
            ListIterator<Task> iter = taskList.listIterator();
            int count = 1;

            printLine();

            printIndent();
            System.out.println("Here are the tasks in your list:");

            while (iter.hasNext()) {
                String currentTask = iter.next().toString();
                printIndent();
                System.out.println(count + "." + currentTask);
                count++;
            }

            printLine();
        }
    }

    public static void deleteTask(int num) {
        printLine();

        printIndent();
        System.out.println("Noted. I've removed this task:");
        printIndent();
        System.out.println("  " + taskList.get(num - 1).toString());

        printIndent();
        if (taskList.size() == 2) {
            System.out.println("Now you have 1 task in your list.");
        } else if (taskList.size() == 1) {
            System.out.println("Now you have no tasks in your list.");
        } else {
            System.out.println("Now you have " + (taskList.size() - 1) + " tasks in your list.");
        }

        printLine();

        taskList.remove(num - 1);
    }

    public static void printLine() {
        StringBuilder line = new StringBuilder();

        for (int i = 0; i < 60; i++) {
            line.append("_");
        }

        String stringLine = line.toString();
        printIndent();
        System.out.println(stringLine);
    }

    public static void printIndent() {
        System.out.print("    ");
    }

    protected LocalDateTime storeAsDateTime(String date) {
        StringBuilder sb = new StringBuilder();
        String[] temp = date.split(" ");


        int timeInt = Integer.parseInt(temp[2]);
        int hour = timeInt / 100;
        int minutes = timeInt % 100;

        String dateString = temp[1];
        String[] dateStringArr = dateString.split("/");

        //If day of the week is single digit pad with 0
        if (dateStringArr[0].length() == 1) {
            dateStringArr[0] = "0" + dateStringArr[0];
        }

        //If month is single digit pad with 0
        if (dateStringArr[1].length() == 1) {
            dateStringArr[1] = "0" + dateStringArr[1];
        }

        //Formatting for LocalDateTime to parse
        sb.append(dateStringArr[2]);
        sb.append("-");
        sb.append(dateStringArr[1]);
        sb.append("-");
        sb.append(dateStringArr[0]);
        sb.append("T");

        //If minutes is 0 pad with 0
        if (minutes == 0) {
            sb.append(hour + ":00:00");
        } else {
            sb.append(hour + ":" + minutes + ":00");
        }

        LocalDateTime dateTime = LocalDateTime.parse(sb.toString());

        return dateTime;
    }

    @Override
    public String toString() {
        String task = "[" + this.getStatusIcon() + "] " + description;
        return task;
    }
}