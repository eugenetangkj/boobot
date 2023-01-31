package duke.command;

/**
 * Represents a command that is entered by the user
 */
public abstract class Command {
    /** Whether the command should cause the bot to exit. */
    protected boolean isExit = false;

    /**
     * Constructs a <code>Command</code> instance.
     */
    public Command() {
        //Empty constructor
    }

    /**
     * Runs the given command.
     *
     * @return the string output of running a given command
     */
    public abstract String runCommand();

    /**
     * Checks if the command should cause the bot to exit.
     *
     * @return true if the command should cause the bot to exit.
     */
    public boolean isExit() {
        return isExit;
    }
}
