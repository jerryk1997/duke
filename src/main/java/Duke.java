import java.util.Scanner;
import java.io.IOException;

public class Duke {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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

    public void run() {
        Scanner sc = new Scanner(System.in);
        Parser parser = new Parser(ui);

        ui.showWelcome();
        String input = sc.nextLine();

        while (true) {
            try {

                parser.parseCommand(input, tasks);
                storage.update(tasks);

                if (parser.isExit()) {
                    break;
                }
                input = sc.nextLine();

            } catch (DukeException e) {

                ui.showException(e);
                input = sc.nextLine();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        ui.showGoodbye();
    }


    public static void main(String[] args) {
        new Duke("tasks.txt").run();
    }
}