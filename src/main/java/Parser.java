/**
 * Parser is a class that aids in parsing through the user input
 * and understanding the commands and executing them accordingly.
 * Commands are passed into a method and appropriate actions are
 * taken to create tasks, remove tasks, or add tasks etc.
 */
public class Parser {
    private Ui ui;

    /**
     * Constructs a Parser object.
     * Parser object is constructed and passed a Ui object to
     * help with printing the outputs back to the user.
     * @param ui ui object for user interaction
     */
    public Parser(Ui ui) {
        this.ui = ui;
    }

    /**
     * Parses through the user's input and makes changes to the
     * task list accordingly.
     * First token of the string is checked to see if it is a valid
     * instruction. If invalid an IncorrectInputException is thrown.
     * If a task is given without description a NoDescriptionException
     * is thrown. Otherwise the task is processed accordingly.
     * @param input String input from the user.
     * @param taskList The current list of tasks
     * @throws DukeException
     */
    public String parseCommand(String input, TaskList taskList) throws DukeException {
        assert input != null;

        String[] inputArr = input.split(" ");

        //Extracting task type and description from input
        String taskType = inputArr[0];
        String taskDesc = getDesc(inputArr);

        if (!correctInput(taskType)) {
            throw new IncorrectInputException(":( OOPS!!! I'm sorry, but I don't know what that means :-(");
        }

        if (taskType.equals("bye")) {

            return ui.showGoodbye();

        } else if (taskType.equals("list")) {

            return ui.printTaskList(taskList);

        } else if (taskDesc.isEmpty()) {

            throw new NoDescriptionException(":( OOPS!!! The description of " + inputArr[0] + " cannot be empty.");

        } else if (taskType.equals("done")) {

            return doneCommand(taskDesc, taskList);

        } else if (taskType.equals("delete")) {

            return deleteCommand(taskDesc, taskList);

        } else if (taskType.equals("find")) {

            return ui.showFoundTasks(taskList.searchFor(taskDesc));

        } else if (taskType.equals("todo")) {

            return todoCommand(taskDesc, taskList);

        } else if (taskType.equals("deadline")) {

            return deadlineCommand(taskDesc, taskList);

        } else  if (taskType.equals("event")) {

            return eventCommand(taskDesc, taskList);

        } else {
            assert false;
            return "assert testing"; //Returning a string since method must return string
        }
    }

    private String doneCommand(String taskDesc, TaskList taskList) {
        int taskNum = Integer.parseInt(taskDesc);
        Task task = taskList.getTask(taskNum);

        if (task.isDone()) {
            return ui.showTaskAlreadyDone(task);
        } else {
            task.doTask();
            return ui.showTaskDone(task);
        }
    }

    private String deleteCommand(String taskDesc, TaskList taskList) {
        int taskNum = Integer.parseInt(taskDesc);
        Task task = taskList.getTask(taskNum);
        taskList.deleteTask(taskNum);

        return ui.showTaskDeleted(task, taskList);
    }

    private String todoCommand(String taskDesc, TaskList taskList) {
        Task newTodo = new Todo(taskDesc);
        taskList.addTask(newTodo);

        return ui.showTaskAdded(newTodo, taskList);
    }

    private String deadlineCommand(String taskDesc, TaskList taskList) {
        String[] taskDescArr = taskDesc.split(" /");

        Task newDeadline = new Deadline(taskDescArr[0], taskDescArr[1]);

        taskList.addTask(newDeadline);

        return ui.showTaskAdded(newDeadline, taskList);
    }

    private String eventCommand(String taskDesc, TaskList taskList) {
        String[] taskDescArr = taskDesc.split(" /");

        Task newEvent = new Event(taskDescArr[0], taskDescArr[1]);

        taskList.addTask(newEvent);

        return ui.showTaskAdded(newEvent, taskList);
    }

    /**
     * Extracts the description and date of the task
     * leaving the command out.
     * @param inputArr String[] of the whole input by the user
     * @return String String representing the description and date.
     */
    public static String getDesc(String[] inputArr) {
        StringBuilder builder = new StringBuilder();

        for (int i = 1; i < inputArr.length; i++) {
            if (i != inputArr.length - 1) {
                builder.append(inputArr[i]);
                builder.append(" ");
            } else {
                builder.append(inputArr[i]);
            }
        }

        return builder.toString();
    }

    public static boolean correctInput(String input) {
        return input.equals("todo") ||
                input.equals("event") ||
                input.equals("deadline") ||
                input.equals("list") ||
                input.equals("done") ||
                input.equals("bye") ||
                input.equals("delete") ||
                input.equals("find");
    }
}