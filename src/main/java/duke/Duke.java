package duke;

import duke.command.Command;
import duke.task.TaskList;

/**
 * The Duke class represents the main controller of the Duke chatbot application.
 *
 * @author selwyn
 */
public class Duke {
    private static TaskList taskList;

    private Storage storage;

    private Parser parser;

    /**
     * Constructs a Duke instance with the specified file path for data storage.
     *
     * @param filePath The file path where Duke stores task data.
     */
    public Duke(String filePath) {
        parser = new Parser();

        String[] dirAndFilePathArr = filePath.split("/");
        String dirPath = "";
        for (int i = 0; i < dirAndFilePathArr.length - 1; i++) {
            dirPath += dirAndFilePathArr[i];
            dirPath += "/";
        }

        System.out.println(dirPath);
        storage = new Storage(dirPath, dirAndFilePathArr[dirAndFilePathArr.length - 1]);
        try {
            taskList = new TaskList(storage.retrieveTasks());
        } catch (DukeException e) {
            Ui.printError(e.getMessage());
        }
    }

    /**
     * Gets a response from Duke based on user input.
     *
     * @param userInput The user's input.
     * @return The response generated by Duke.
     */
    public String getResponse(String userInput) {
        String responseStr;
        try {
            Command c = this.parser.parseCommand(userInput);
            responseStr = c.execute(taskList, storage);
        } catch (DukeException e) {
            responseStr = Ui.printError(e.getMessage());
        }
        return responseStr;
    }
}
