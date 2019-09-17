import java.io.File;
import java.io.FileWriter;

import java.util.ListIterator;
import java.util.LinkedList;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Scanner;

/**
 * Storage is a class that handles operations to store user input
 * of the tasks and write it onto the computer's memory so that it is
 * preserved after the application terminates.
 */
public class Storage {
    File taskListFile;

    /**
     *
     * Constructs a Storage object.
     * Takes in a file path so that it can be used later
     * to direct where to store the information.
     * @param filePath File path to the storage location
     */
    public Storage(String filePath) {
        taskListFile = new File(filePath);
    }

    /**
     * Generates a LinkedList of tasks based on what the user has
     * saved from the last use of this program.
     * The tasks are stored in a pre determined format. All this
     * method does is read the tasks line by line and create new
     * Task objects based on the strings and adds them to a
     * LinkedList to be returned.
     * @return LinkedList<Task> List representing the tasks saved
     * @throws DukeException Exception thrown if no existing list is found
     */
    public LinkedList<Task> load() throws DukeException {
        try {
            if (!taskListFile.exists()) {
                throw new NoExistingListException("No saved List found.");
            }

            Scanner sc = new Scanner(taskListFile);
            LinkedList<Task> taskList = new LinkedList<>();


            while (sc.hasNext()) {
                String nextTask = sc.nextLine();

                String[] nextTaskArr = nextTask.split("~");
                boolean status = nextTaskArr[1].equals("1");

                if (nextTaskArr[0].equals("T")) {
                    taskList.add(
                            new Todo(nextTaskArr[2], status));
                } else if (nextTaskArr[0].equals("D")) {
                    taskList.add(
                            new Deadline(nextTaskArr[2], nextTaskArr[3], status));
                } else if (nextTaskArr[0].equals("E")) {
                    taskList.add(
                            new Event(nextTaskArr[2], nextTaskArr[3], status));
                }
            }

            return taskList;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return new LinkedList<Task>();
        }
    }

    /**
     * Updates the file that saves the tasks based on the
     * LinkedList of tasks stored in the TaskList file.
     * The method takes in a TaskList object, extracts the Linked
     * List stored and goes through every task stored, converting
     * them into strings and appending the string to a stringbuilder
     * before converting the stringbuilder into a string to be written
     * onto the file storing the information. Every change is not an
     * addition but rather the whole file is overwritten again.
     * @param taskList TaskList object representing the current list of tasks
     * @throws IOException
     */
    public void update(TaskList taskList) throws IOException {
        FileWriter fw = new FileWriter(taskListFile);
        StringBuilder sb = new StringBuilder();
        LinkedList<Task> list = taskList.getList();

        ListIterator<Task> iter = list.listIterator();


        while (iter.hasNext()) {
            Task current = iter.next();
            sb.append(current.toFileFormat());
            sb.append("\n");
        }

        String taskListString = sb.toString();

        fw.write(taskListString);
        fw.close();
    }
}