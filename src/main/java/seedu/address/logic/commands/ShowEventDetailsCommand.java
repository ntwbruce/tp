package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Shows the details of an existing Event in the Managera Event list.
 */
public class ShowEventDetailsCommand extends Command {

    public static final String COMMAND_WORD = "showDetails";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the details of the event at the specified index.\n"
            + "Index should be positive integer.\n"
            + "Parameters: INDEX \n"
            + "Example: " + COMMAND_WORD + " 1 ";

    private final Index index;

    /**
     * Creates a ShowEventDetailsCommand for the Event with the specified {@code eventName}
     */
    public ShowEventDetailsCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownEventList = model.getFilteredEventList();

        if (lastShownEventList.size() == 0 || lastShownEventList.size() < index.getOneBased()) {
            throw new CommandException(Messages.MESSAGE_EVENT_NOT_FOUND);
        }

        Event desiredEvent = lastShownEventList.get(index.getZeroBased());
        return new CommandResult(desiredEvent.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowEventDetailsCommand // instanceof handles nulls
                && index.equals(((ShowEventDetailsCommand) other).index)); // state check
    }
}
