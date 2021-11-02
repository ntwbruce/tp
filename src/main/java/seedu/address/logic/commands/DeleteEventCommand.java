package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Deletes an Event identified using its displayed index.
 */
public class DeleteEventCommand extends Command {

    public static final String COMMAND_WORD = "deleteEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the event identified by the "
            + "index number used in the displayed event list as done.\n"
            + "Index should be positive integer.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted event:\n%1$s";

    private final Index targetIndex;

    /**
     * This is a constructor for DeleteEventCommand.
     *
     * @param targetIndex The index of the event to be deleted, as seen in the displayed event list.
     */
    public DeleteEventCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteEvent(eventToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteEventCommand
                && targetIndex.equals(((DeleteEventCommand) other).targetIndex));
    }
}
