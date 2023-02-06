package boo.parser;

import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;

import boo.command.ByeCommand;
import boo.command.Command;
import boo.command.DeadlineCommand;
import boo.command.DeleteCommand;
import boo.command.EventCommand;
import boo.command.ExceptionCommand;
import boo.command.FindCommand;
import boo.command.HelpCommand;
import boo.command.InvalidCommand;
import boo.command.ListCommand;
import boo.command.MarkCommand;
import boo.command.OnCommand;
import boo.command.ReminderCommand;
import boo.command.ToDoCommand;
import boo.command.UnmarkCommand;
import boo.datetime.DateTime;
import boo.exception.BooException;
import boo.storage.Storage;
import boo.tasklist.TaskList;

/**
 * Represents a parser that will parse and process commands entered by the user into the chatbot.
 */
public class Parser {
    /** The task list required to check whether command is valid. */
    private TaskList tasks;

    /**
     * Constructs a {@code Parser} instance.
     *
     * @param tasks Task list of the user
     */
    public Parser(TaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Processes command to determine if it is in the format of any valid commands,
     * and checks for invalid input.
     *
     * @return the type of command given by the user.
     */
    public CommandType parseRawCommand(String rawCommand) {
        //Single word commands
        switch (rawCommand) {
        case "bye":
            return CommandType.BYE;
        case "list":
            return CommandType.LIST;
        case "help":
            return CommandType.HELP;
        default:
            //Do nothing
        }

        //Multiple word commands
        String[] inputArray = rawCommand.split(" ");
        String firstWord = inputArray[0];

        //Check cases and verify whether they are valid formats or not
        switch (firstWord) {
        case "mark":
            return validateMark(inputArray);
        case "unmark":
            return validateUnmark(inputArray);
        case "delete":
            return validateDelete(inputArray);
        case "todo":
            return validateTodo(rawCommand, inputArray);
        case "deadline":
            return validateDeadline(rawCommand);
        case "event":
            return validateEvent(rawCommand);
        case "on":
            return validateOn(rawCommand);
        case "find":
            return validateFind(rawCommand);
        case "reminder":
            return validateReminder(inputArray);
        default:
            return CommandType.INVALID;
        }
    }

    /**
     * Returns the appropriate Command object associated with the CommandType.
     *
     * @return the Command object for the given CommandType.
     */
    public Command parseCommandType(CommandType command, TaskList tasks, Storage storage) {
        switch (command) {
        case BYE:
            return new ByeCommand();
        case LIST:
            return new ListCommand(tasks);
        case MARK:
            return new MarkCommand(command.getIndex(), tasks, storage);
        case UNMARK:
            return new UnmarkCommand(command.getIndex(), tasks, storage);
        case TODO:
            return new ToDoCommand(command.getTaskName(), tasks, storage);
        case DEADLINE:
            return new DeadlineCommand(command.getTaskName(), command.getDeadline(), tasks, storage);
        case EVENT:
            return new EventCommand(command.getTaskName(), command.getStartDate(), command.getEndDate(),
                    tasks, storage);
        case DELETE:
            return new DeleteCommand(command.getIndex(), tasks, storage);
        case ON:
            return new OnCommand(command.getOnDate(), tasks);
        case HELP:
            return new HelpCommand();
        case FIND:
            return new FindCommand(command.getKeyPhrase(), tasks);
        case REMINDER:
            return new ReminderCommand(command.getReminderDuration(), tasks);
        case INVALID:
            return new InvalidCommand();
        default:
            return new ExceptionCommand(command.getExceptionMessage());
        }
    }


    /**
     * Validates the 'mark' input of the user.
     *
     * @param inputArray The array containing the words from the user's raw command.
     * @return a Mark command type if 'mark' input is valid, else return an Exception command type.
     */
    private CommandType validateMark(String[] inputArray) {
        try {
            if (inputArray.length != 2) {
                throw new BooException("The mark command must be followed by a single number.");
            }
            if (!isInteger(inputArray[1])) {
                throw new BooException("The mark command must be followed by a single integer.");
            }
            int indexOfTask = Integer.parseInt(inputArray[1]) - 1;
            if (!(indexOfTask <= tasks.getSizeOfTaskList() - 1 && indexOfTask >= 0)) {
                throw new BooException("Please enter a valid task number. You currently have "
                        + tasks.getSizeOfTaskList() + " tasks.");
            }
            CommandType ctMark = CommandType.MARK;
            ctMark.setIndex(indexOfTask);
            return ctMark;
        } catch (BooException booException) {
            CommandType ctException = CommandType.EXCEPTION;
            ctException.setExceptionMessage(booException.getMessage());
            return ctException;
        }
    }


    /**
     * Validates the 'unmark' input of the user.
     *
     * @param inputArray The array containing the words from the user's raw command.
     * @return an Unmark command type if 'unmark' input is valid, else return an Exception command type.
     */
    private CommandType validateUnmark(String[] inputArray) {
        try {
            if (inputArray.length != 2) {
                throw new BooException("The unmark command must be followed by a single number.");
            }
            if (!isInteger(inputArray[1])) {
                throw new BooException("The unmark command must be followed by a single integer.");
            }
            int indexOfTask = Integer.parseInt(inputArray[1]) - 1;
            if (!(indexOfTask <= tasks.getSizeOfTaskList() - 1 && indexOfTask >= 0)) {
                throw new BooException("Please enter a valid task number. You currently have "
                        + tasks.getSizeOfTaskList() + " tasks.");
            }
            CommandType ctUnmark = CommandType.UNMARK;
            ctUnmark.setIndex(indexOfTask);
            return ctUnmark;
        } catch (BooException booException) {
            CommandType ctException = CommandType.EXCEPTION;
            ctException.setExceptionMessage(booException.getMessage());
            return ctException;
        }
    }

    /**
     * Validates the 'delete' input of the user.
     *
     * @param inputArray The array containing the words from the user's raw command.
     * @return a Delete command type if 'delete' input is valid, else return an Exception command type.
     */
    private CommandType validateDelete(String[] inputArray) {
        try {
            if (inputArray.length != 2) {
                throw new BooException("The delete command must be followed by a single number.");
            }
            if (!isInteger(inputArray[1])) {
                throw new BooException("The delete command must be followed by a single integer.");
            }
            int indexOfTask = Integer.parseInt(inputArray[1]) - 1;
            if (!(indexOfTask <= tasks.getSizeOfTaskList() - 1 && indexOfTask >= 0)) {
                throw new BooException("Please enter a valid task number. You currently have "
                        + tasks.getSizeOfTaskList() + " tasks.");
            }
            CommandType ctDelete = CommandType.DELETE;
            ctDelete.setIndex((indexOfTask));
            return ctDelete;
        } catch (BooException booException) {
            CommandType ctException = CommandType.EXCEPTION;
            ctException.setExceptionMessage(booException.getMessage());
            return ctException;
        }
    }


    /**
     * Validates the 'to-do' input of the user.
     *
     * @param rawCommand The user's raw command.
     * @param inputArray The array containing the words from the user's raw command.
     * @return a to-do command type if 'to-do' input is valid, else return an Exception command type.
     */
    private CommandType validateTodo(String rawCommand, String[] inputArray) {
        try {
            if (inputArray.length == 1) {
                throw new BooException("The todo command cannot be left blank.");
            }
            int indexOfType = rawCommand.indexOf("todo");
            String taskName = rawCommand.substring(indexOfType + 5);
            CommandType ctToDo = CommandType.TODO;
            ctToDo.setTaskName(taskName);
            return ctToDo;
        } catch (BooException booException) {
            CommandType ctException = CommandType.EXCEPTION;
            ctException.setExceptionMessage(booException.getMessage());
            return ctException;
        }
    }

    /**
     * Validates the 'deadline' input of the user.
     *
     * @param rawCommand The user's raw command.
     * @return a deadline command type if 'deadline' input is valid, else return an Exception command type.
     */
    private CommandType validateDeadline(String rawCommand) {
        try {
            int indexOfType = rawCommand.indexOf("deadline");
            if (indexOfType + 8 > rawCommand.length() - 1) {
                throw new BooException("The deadline command cannot be left blank.");
            }
            int indexOfBy = rawCommand.indexOf("/by");
            if (indexOfBy == -1) {
                throw new BooException("The deadline cannot be left blank.");
            }
            //deadline/by
            if (indexOfType + 8 == indexOfBy) {
                throw new BooException("There seems to be a missing task name.");
            }
            if (indexOfBy + 4 > rawCommand.length() - 1) {
                throw new BooException("The deadline cannot be left blank.");
            }
            if (indexOfType + 9 > indexOfBy - 1) {
                throw new BooException("There seems to be a missing task name.");
            }
            String taskName = rawCommand.substring(indexOfType + 9, indexOfBy - 1);
            String deadlineOfTask;
            if (rawCommand.charAt(indexOfBy + 3) == ' ') {
                deadlineOfTask = rawCommand.substring(indexOfBy + 4);
            } else {
                deadlineOfTask = rawCommand.substring(indexOfBy + 3);
            }
            if (taskName.isBlank()) {
                throw new BooException("The task name cannot be left blank.");
            }
            if (deadlineOfTask.isBlank()) {
                throw new BooException("The deadline cannot be left blank.");
            }
            DateTime.getDateTimeObject(deadlineOfTask);
            CommandType ctDeadline = CommandType.DEADLINE;
            ctDeadline.setTaskName(taskName);
            ctDeadline.setDeadline(deadlineOfTask);
            return ctDeadline;
        } catch (BooException booException) {
            CommandType ctException = CommandType.EXCEPTION;
            ctException.setExceptionMessage(booException.getMessage());
            return ctException;
        } catch (DateTimeParseException dateTimeException) {
            CommandType ctException = CommandType.EXCEPTION;
            ctException.setExceptionMessage(
                    "Please check that you entered a valid date, and that the date should be in "
                            + "the format of\nyyyy-MM-dd hh:mm or yyyy-MM-dd.");
            return ctException;
        }
    }


    /**
     * Validates the 'event' input of the user.
     *
     * @param rawCommand The user's raw command.
     * @return an event command type if 'event' input is valid, else return an Exception command type.
     */
    private CommandType validateEvent(String rawCommand) {
        try {
            int indexOfType = rawCommand.indexOf("event");
            if (indexOfType + 5 > rawCommand.length() - 1) {
                throw new BooException("The event command cannot be left blank.");
            }

            int indexOfFrom = rawCommand.indexOf("/from");
            if (indexOfFrom == -1) {
                throw new BooException("There seems to be a missing from date.");
            }

            int indexOfTo = rawCommand.indexOf("/to");
            if (indexOfTo == -1) {
                throw new BooException("There seems to be a missing to date.");
            }

            //Check taskName
            if ((indexOfType + 6 > indexOfFrom - 1)) {
                throw new BooException("There seems to be a missing task name.");
            }

            String taskName = rawCommand.substring(indexOfType + 6, indexOfFrom - 1);
            if (taskName.isBlank()) {
                throw new BooException("The task name cannot be left blank.");
            }

            //Check startDate
            if (indexOfFrom + 6 > indexOfTo - 1) {
                throw new BooException("There seems to be a missing start date.");
            }

            String startDate = rawCommand.substring(indexOfFrom + 6, indexOfTo - 1);
            if (startDate.isBlank()) {
                throw new BooException("The start date cannot be left blank.");
            }

            //Check endDate
            if (indexOfTo + 4 > rawCommand.length() - 1) {
                throw new BooException("There seems to be a missing end date.");
            }

            String endDate = rawCommand.substring(indexOfTo + 4);
            if (endDate.isBlank()) {
                throw new BooException("The end date cannot be left blank.");
            }

            //Create new event task
            Temporal start = DateTime.getDateTimeObject(startDate);
            Temporal end = DateTime.getDateTimeObject(endDate);

            if (!DateTime.isValidDuration(start, end)) {
                throw new BooException("Start date must be before end date.");
            }

            CommandType ctEvent = CommandType.EVENT;
            ctEvent.setTaskName(taskName);
            ctEvent.setStartDate(startDate);
            ctEvent.setEndDate(endDate);
            return ctEvent;
        } catch (BooException booException) {
            CommandType ctException = CommandType.EXCEPTION;
            ctException.setExceptionMessage(booException.getMessage());
            return ctException;
        } catch (DateTimeParseException dateTimeException) {
            CommandType ctException = CommandType.EXCEPTION;
            ctException.setExceptionMessage(
                    "Please check that you entered a valid date, and that the date should be in "
                            + "the format of\nyyyy-MM-dd hh:mm or yyyy-MM-dd.");
            return ctException;
        }
    }


    /**
     * Validates the 'on' input of the user.
     *
     * @param rawCommand The user's raw command.
     * @return an on command type if 'on' input is valid, else return an Exception command type.
     */
    private CommandType validateOn(String rawCommand) {
        try {
            String dateString = rawCommand.substring(3);
            if (dateString.equals("")) {
                throw new BooException("The date cannot be left blank.");
            }
            DateTime.getDateTimeObject(dateString);
            CommandType ctOn = CommandType.ON;
            ctOn.setOnDate(dateString);
            return ctOn;
        } catch (BooException booException) {
            CommandType ctException = CommandType.EXCEPTION;
            ctException.setExceptionMessage(booException.getMessage());
            return ctException;
        } catch (DateTimeParseException dateTimeException) {
            CommandType ctException = CommandType.EXCEPTION;
            ctException.setExceptionMessage(
                    "Please check that you entered a valid date, and that the date should be in "
                            + "the format of\nyyyy-MM-dd hh:mm or yyyy-MM-dd.");
            return ctException;
        }
    }

    /**
     * Validates the 'find' input of the user.
     *
     * @param rawCommand The user's raw command.
     * @return a find command type.
     */
    private CommandType validateFind(String rawCommand) {
        CommandType ctFind = CommandType.FIND;
        ctFind.setKeyPhrase(rawCommand.substring(5));
        return ctFind;
    }


    /**
     * Validates the 'reminder' input of the user.
     *
     * @param inputArray The array containing the words from the user's raw command.
     */
    private CommandType validateReminder(String[] inputArray) {
        try {
            boolean hasValidNumberOfSubcommands = inputArray.length == 2;
            if (!hasValidNumberOfSubcommands) {
                throw new BooException("Please enter one of the following after reminder: day, week or month.");
            }
            boolean isValidKeyword = inputArray[1].equals("day") || inputArray[1].equals("week")
                    || inputArray[1].equals("month");
            if (!isValidKeyword) {
                throw new BooException("Please enter one of the following after reminder: day, week or month.");
            }
            CommandType ctReminder = CommandType.REMINDER;
            ctReminder.setReminderDuration(inputArray[1]);
            return ctReminder;
        } catch (BooException booException) {
            CommandType ctException = CommandType.EXCEPTION;
            ctException.setExceptionMessage(booException.getMessage());
            return ctException;
        }
    }

    /**
     * Checks if a string can be converted into an {@code Integer}.
     *
     * @param stringToCheck {@code String} to check whether the conversion is possible.
     * @return true if it can be converted, else return false.
     */
    public boolean isInteger(String stringToCheck) {
        try {
            Integer.parseInt(stringToCheck);
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
        return true;
    }

}
