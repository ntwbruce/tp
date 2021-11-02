package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.participant.Participant;

public class RemoveParticipantFromEventCommand extends Command {

    public static final String COMMAND_WORD = "expel";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the Participant with specified index from an event with another specified index.\n"
            + "Index should be positive integer.\n"
            + "Parameters: "
            + "PARTICIPANT_INDEX "
            + "EVENT_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_REMOVE_PARTICIPANT_FROM_EVENT_SUCCESS =
            "Removed %1$s from %2$s successfully";

    private final Index participantIndex;
    private final Index eventIndex;

    /**
     * Creates an RemoveParticipantFromEventByIndexCommand to remove {@code Participant} at
     * specified {@code participantIndex} from {@code event} at specified {@code eventIndex}
     */
    public RemoveParticipantFromEventCommand(Index participantIndex, Index eventIndex) {
        this.participantIndex = participantIndex;
        this.eventIndex = eventIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Participant> lastShownParticipantList = model.getFilteredParticipantList();
        List<Event> lastShownEventList = model.getFilteredEventList();

        Participant participantToRemove;

        try {
            participantToRemove = lastShownParticipantList.get(participantIndex.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX);
        }

        Event selectedEvent;

        try {
            selectedEvent = lastShownEventList.get(eventIndex.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        if (!selectedEvent.hasParticipant(participantToRemove)) {
            throw new CommandException(Messages.showParticipantDoesNotExist(
                    participantToRemove.getFullName(), selectedEvent.getNameString()));
        }

        // remove participant
        selectedEvent.removeParticipant(participantToRemove);

        return new CommandResult(String.format(MESSAGE_REMOVE_PARTICIPANT_FROM_EVENT_SUCCESS,
                participantToRemove.getFullName(), selectedEvent.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveParticipantFromEventCommand // instanceof handles nulls
                && participantIndex.equals(((RemoveParticipantFromEventCommand) other).participantIndex))
                && eventIndex.equals(((RemoveParticipantFromEventCommand) other).eventIndex); //state check
    }
}
