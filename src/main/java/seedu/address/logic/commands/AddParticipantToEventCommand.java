package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.participant.Participant;

public class AddParticipantToEventCommand extends Command {

    public static final String COMMAND_WORD = "enroll";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds Participant with specified index to an event with another specified index.\n"
            + "Indexes should be positive integers.\n"
            + "Parameters: "
            + "PARTICIPANT_INDEX "
            + "EVENT_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_ADD_PARTICIPANT_TO_EVENT_SUCCESS = "Added %1$s to %2$s successfully";

    private final Index participantIndex;
    private final Index eventIndex;

    /**
     * Creates an AddParticipantToEventCommandByIndex to add {@code Participant} at
     * specified {@code participantIndex} to {@code event} at specified {@code eventIndex}
     */
    public AddParticipantToEventCommand(Index participantIndex, Index eventIndex) {
        this.participantIndex = participantIndex;
        this.eventIndex = eventIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Participant> lastShownParticipantList = model.getFilteredParticipantList();
        List<Event> lastShownEventList = model.getFilteredEventList();

        Participant participantToAdd;

        try {
            participantToAdd = lastShownParticipantList.get(participantIndex.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX);
        }

        Event selectedEvent;

        try {
            selectedEvent = lastShownEventList.get(eventIndex.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        if (selectedEvent.hasParticipant(participantToAdd)) {
            throw new CommandException(Messages.showParticipantAlreadyEnrolled(
                    participantToAdd.getFullName(), selectedEvent.getNameString()));
        }

        // add participant
        selectedEvent.addParticipant(participantToAdd);

        return new CommandResult(String.format(MESSAGE_ADD_PARTICIPANT_TO_EVENT_SUCCESS,
                participantToAdd.getFullName(), selectedEvent.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddParticipantToEventCommand // instanceof handles nulls
                && participantIndex.equals(((AddParticipantToEventCommand) other).participantIndex))
                && eventIndex.equals(((AddParticipantToEventCommand) other).eventIndex); //state check
    }
}
