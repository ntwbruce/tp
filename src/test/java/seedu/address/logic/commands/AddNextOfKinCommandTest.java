package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX;
import static seedu.address.logic.commands.AddNextOfKinCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalNextOfKins.KEN;
import static seedu.address.testutil.TypicalNextOfKins.SARAH;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.participant.Participant;
import seedu.address.testutil.DefaultModelStub;
import seedu.address.testutil.ParticipantBuilder;

public class AddNextOfKinCommandTest {

    @Test
    public void execute_addNextOfKinSuccessful() throws Exception {
        Participant validParticipant = new ParticipantBuilder().build();
        ModelStubWithParticipant modelStub =
                new ModelStubWithParticipant(validParticipant);

        CommandResult commandResult =
                new AddNextOfKinCommand(Index.fromOneBased(1), SARAH).execute(modelStub);

        assertEquals(String.format(MESSAGE_SUCCESS, SARAH.getFullName(),
                validParticipant.getFullName()), commandResult.getFeedbackToUser());

        assertTrue(validParticipant.hasNextOfKin(SARAH));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Participant validParticipant = new ParticipantBuilder().build();
        ModelStubWithParticipant modelStub =
                new ModelStubWithParticipant(validParticipant);

        assertThrows(CommandException.class,
                String.format(MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX,
                        AddNextOfKinCommand.COMMAND_WORD), () ->
                        new AddNextOfKinCommand(Index.fromOneBased(2), SARAH).execute(modelStub));
    }

    @Test
    public void execute_participantContainsNextOfKin_throwsCommandException() throws Exception {
        Participant validParticipant = new ParticipantBuilder().withName("Johnny Tester").build();
        ModelStubWithParticipant modelStub =
                new ModelStubWithParticipant(validParticipant);

        CommandResult addKenFirst = new AddNextOfKinCommand(Index.fromOneBased(1), KEN).execute(modelStub);

        AddNextOfKinCommand tryAddingKenAgain = new AddNextOfKinCommand(Index.fromOneBased(1), KEN);

        assertThrows(CommandException.class,
                Messages.showNextOfKinExists(KEN.getFullName(), validParticipant.getFullName()), () ->
                        tryAddingKenAgain.execute(modelStub));
    }

    @Test
    public void equals() {
        Index firstIndex = Index.fromOneBased(1);
        Index secondIndex = Index.fromOneBased(2);

        AddNextOfKinCommand addSarahSample =
                new AddNextOfKinCommand(firstIndex, SARAH);
        AddNextOfKinCommand addSarahFirstIndex =
                new AddNextOfKinCommand(firstIndex, SARAH);
        AddNextOfKinCommand addSarahSecondIndex =
                new AddNextOfKinCommand(secondIndex, SARAH);
        AddNextOfKinCommand addKenSecondIndex =
                new AddNextOfKinCommand(secondIndex, KEN);

        // same object -> returns true
        assertTrue(addSarahSample.equals(addSarahSample));

        // same values -> returns true
        assertTrue(addSarahSample.equals(addSarahFirstIndex));

        // different types -> returns false
        assertFalse(addSarahSample.equals(1));

        // null -> returns false
        assertFalse(addSarahSample.equals(null));

        // different index -> returns false
        assertFalse(addSarahFirstIndex.equals(addSarahSecondIndex));

        // different next of kin -> returns false
        assertFalse(addSarahSecondIndex.equals(addKenSecondIndex));
    }

    /**
     * A Model stub that contains a single Event and single Participant.
     */
    private static class ModelStubWithParticipant extends DefaultModelStub {
        private final Participant participant;

        ModelStubWithParticipant(Participant participant) {
            requireNonNull(participant);
            this.participant = participant;
        }

        @Override
        public ObservableList<Participant> getFilteredParticipantList() {
            ObservableList<Participant> participants = FXCollections.observableArrayList();
            participants.add(participant);
            return participants;
        }
    }
}
