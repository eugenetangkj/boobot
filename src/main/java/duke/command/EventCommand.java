package duke.command;

import java.lang.StringBuilder;

import duke.datetime.DateTime;
import duke.storage.Storage;
import duke.task.Event;
import duke.tasklist.TaskList;

/**
 * Represents an event command that is entered by the user to create a task with a start and end date.
 */
public class EventCommand extends Command {

    /** Task name for the event task to be created. */
    private String taskName;

    /** Start date for the given event task. */
    private String startDate;

    /** End date for the given event task. */
    private String endDate;

    /** Task list containing all the tasks. */
    private TaskList tasks;

    /** Storage that allows updating after creating the event task. */
    private Storage storage;

    /**
     * Constructs an EventCommand.
     *
     * @param taskName The name of the event task to be created.
     * @param startDate The start date of the task.
     * @param endDate The end date of the task.
     * @param tasks The <code>TaskList</code>> of all available tasks.
     * @param storage The <code>Storage</code> object to allow local saving after adding a new event task.
     */
    public EventCommand(String taskName, String startDate, String endDate, TaskList tasks, Storage storage) {
        super();
        this.taskName = taskName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tasks = tasks;
        this.storage = storage;
    }

    /**
     * Creates an <code>Event</code> task and updates the local data file.
     *
     * @return a string that informs the user that the task has been created.
     */
    @Override
    public String runCommand() {
        //Creates task and saves it
        Event newEventTask = new Event(taskName, startDate, endDate, DateTime.getDateTimeObject(startDate),
                DateTime.getDateTimeObject(endDate));
        tasks.addTask(newEventTask);
        storage.saveTasks(tasks);

        StringBuilder sb = new StringBuilder();
        sb.append("Added task to list:\n");
        sb.append(newEventTask.getStatusOfTaskInString() + "\n");
        if (tasks.getSizeOfTaskList() == 1) {
            sb.append("\nCurrently, there is 1 task in your list.");
        } else {
            sb.append("\nCurrently, there are " + tasks.getSizeOfTaskList() + " tasks in your list.");
        }
        return sb.toString();
    }

}

