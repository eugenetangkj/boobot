package duke.parser;

/**
 * Represents the type of command entered by the user.
 */
public enum CommandType {
    /** Possible command types. */
    BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, ON, HELP, INVALID, NOTHING;

    /** Name of task associated with to-do, deadline and event commands. */
    private String taskName;
    /** Index of task to process, for mark, unmark and delete commands. */
    private int index;
    /** Deadline of task associated with deadline command. */
    private String deadline;
    /** Start date of task associated with event command. */
    private String startDate;
    /** End date of task associated with event command. */
    private String endDate;
    /** Date to check, associated with on command. */
    private String onDate;


    /**
     * Gets the name of the task associated with this command. The command is to-do, deadline or event.
     *
     * @return the name of the task associated with this command.
     */
    public String getTaskName() {
        return this.taskName;
    }

    /**
     * Gets the index of the task to be processed by this command. The command is mark, unmark or delete.
     *
     * @return the index of the task to be processed by this command.
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Gets the deadline of the task to be processed by this command. The command is deadline.
     *
     * @return the deadline of the deadline task associated with this command.
     */
    public String getDeadline() {
        return this.deadline;
    }

    /**
     * Gets the start date of the task to be processed by this command. The command is event.
     *
     * @return the start date of the event task associated with this command.
     */
    public String getStartDate() {
        return this.startDate;
    }

    /**
     * Gets the end date of the task to be processed by this command. The command is event.
     *
     * @return the end date of the event task associated with this command.
     */
    public String getEndDate() {
        return this.endDate;
    }

    /**
     * Gets the date to be checked by this command The command is on.
     *
     * @return the date to be checked by this on command.
     */
    public String getOnDate() { return this.onDate;}



    /**
     * Sets the name of the task associated with this command. The command is to-do, deadline or event.
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }


    /**
     * Sets the index of the task to be processed by this command. The command is mark, unmark or delete.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Sets the deadline of the task to be processed by this command. The command is deadline.
     */
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    /**
     * Sets the start date of the task to be processed by this command. The command is event.
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Sets the end date of the task to be processed by this command. The command is event.
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Sets the date to be checked by this command The command is on.
     */
    public void setOnDate(String onDate) {
        this.onDate = onDate;
    }
}
