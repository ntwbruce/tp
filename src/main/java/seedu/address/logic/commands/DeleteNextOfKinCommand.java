package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.participant.NextOfKin;
import seedu.address.model.participant.Participant;

public class DeleteNextOfKinCommand extends Command {

    public static final String COMMAND_WORD = "deleteNok";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the next-of-kin with specified index from a "
            + "participant with another specified index.\n"
            + "Indexes should be positive integers.\n"
            + "Parameters: \n"
            + "NOK_INDEX "
            + "PARTICIPANT_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_SUCCESS = "Removed \n%1$s \nfrom %2$s successfully";
    public static final String MESSAGE_INVALID_NOK_INDEX = "The next-of-kin index provided is invalid!";

    private final Index nextOfKinIndex;
    private final Index participantIndex;

    /**
     * This is a constructor for DeleteNextOfKinCommand.
     *
     * @param nextOfKinIndex index of next-of-kin to be deleted.
     * @param participantIndex index of participant to delete next-of-kin.
     */
    public DeleteNextOfKinCommand(Index nextOfKinIndex, Index participantIndex) {
        requireNonNull(participantIndex);
        requireNonNull(nextOfKinIndex);
        this.nextOfKinIndex = nextOfKinIndex;
        this.participantIndex = participantIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Participant> lastShownParticipantList = model.getFilteredParticipantList();

        Participant selectedParticipant;

        try {
            selectedParticipant = lastShownParticipantList.get(participantIndex.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX);
        }

        NextOfKin selectedNextOfKin;

        try {
            selectedNextOfKin = selectedParticipant.getNextOfKin(nextOfKinIndex.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_INVALID_NOK_INDEX);
        }

        selectedParticipant.removeNextOfKin(selectedNextOfKin);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                selectedNextOfKin.getFullName(), selectedParticipant.getFullName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteNextOfKinCommand
                && participantIndex.equals(((DeleteNextOfKinCommand) other).participantIndex))
                && nextOfKinIndex.equals(((DeleteNextOfKinCommand) other).nextOfKinIndex);
    }
}
