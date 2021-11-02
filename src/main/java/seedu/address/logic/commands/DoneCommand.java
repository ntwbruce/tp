package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Marks an Event identified using its displayed index as done.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the event identified by the "
            + "index number used in the displayed event list as done.\n"
            + "Index should be positive integer.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_EVENT_SUCCESS = "Marked event as done:\n%1$s";
    public static final String MESSAGE_EVENT_ALREADY_DONE = "This event is already marked as done!";

    private final Index targetIndex;

    /**
     * This is a constructor for DoneCommand.
     *
     * @param targetIndex The index of the event to be marked as done, as seen in the displayed event list.
     */
    public DoneCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }
        Event eventToMarkDone = lastShownList.get(targetIndex.getZeroBased());

        if (eventToMarkDone.getDoneValue()) {
            throw new CommandException(MESSAGE_EVENT_ALREADY_DONE);
        }
        model.markEventAsDone(eventToMarkDone);
        return new CommandResult(String.format(MESSAGE_DONE_EVENT_SUCCESS, eventToMarkDone));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DoneCommand
                && targetIndex.equals(((DoneCommand) other).targetIndex));
    }
}
