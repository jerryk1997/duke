import java.io.IOException;

/**
 * Duke is the main body of the program. It takes in the
 * user's input and processes it accordingly.
 */
public class Duke {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Duke() {

    }

    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public String run(String input) {
        Parser parser = new Parser(ui);

        try {
            String output = parser.parseCommand(input, tasks);
            storage.update(tasks);

            return output;

        } catch (DukeException e) {

            return ui.showException(e);

        } catch (IOException e) {
            return e.getMessage();
        }
    }


    /**
     * You should have your own function to generate a response to user input.
     * Replace this stub with your completed method.
     */
    public String getResponse(String input) {
        String output = run(input);
        assert output != null;
        return output;
    }

}