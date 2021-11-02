package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.participant.Participant;

/**
 * Shows a list of participants of an existing Event in the Managera Event list.
 */
public class ShowEventParticipantsCommand extends Command {

    public static final String COMMAND_WORD = "showParticipants";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays a list of participants of the event at the specified index.\n"
            + "Index should be positive integer.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index index;

    /**
     * Creates a ShowEventParticipantsCommand for the Event with the specified {@code eventName}
     */
    public ShowEventParticipantsCommand(Index index) {
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
        List<Participant> participantList = desiredEvent.getParticipants();
        participantList.sort(Comparator.comparing(Participant::getFullName));
        String displayedMessage = String.format("Event Name: %s\nNumber of participants: %d\nParticipant list:\n%s",
                desiredEvent.getName(),
                participantList.size(),
                Stream.iterate(0, i -> i + 1)
                        .limit(participantList.size())
                        .map(i -> (i + 1) + ". " + participantList.get(i).getFullName() + "\n")
                        .collect(Collectors.joining())
                );

        return new CommandResult(displayedMessage);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowEventParticipantsCommand // instanceof handles nulls
                && index.equals(((ShowEventParticipantsCommand) other).index)); // state check
    }
}

