package duke;

import duke.command.Command;
import duke.parser.CommandType;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.tasklist.TaskList;

/**
 * Represents a chatbot that one can interact with to keep track of tasks.
 */
public class Duke {
    //Bot elements
    private Storage storage;
    private TaskList taskList;
    private Parser parser;


    /**
     * Constructs a chatbot instance.
     */
    public Duke() {
        //Initialise components
        storage = new Storage("data", "tasks.txt");
        taskList = new TaskList();
        parser = new Parser(taskList);

        //Prepare data file
        if (!storage.prepareFile()) {
            System.exit(1);
        }

        if (!storage.loadTasksFromFile(taskList)) {
            //Cannot read from data file. Start with new empty task list.
            taskList = new TaskList();
        }
    }


    /**
     * Gets the response of the bot in accordance to what the user types in
     *
     * @param input The user's input to be responded to.
     * @return the string containing the bot's response.
     */
    public String getResponse(String input) {
        CommandType commandType = parser.parseRawCommand(input);
        Command command = parser.parseCommandType(commandType, taskList, storage);

        return command.runCommand();

    }
}



