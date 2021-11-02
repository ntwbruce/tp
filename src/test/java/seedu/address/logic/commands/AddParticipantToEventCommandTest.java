package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX;
import static seedu.address.logic.commands.AddParticipantToEventCommand.MESSAGE_ADD_PARTICIPANT_TO_EVENT_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.SAMPLE_EVENT;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.participant.Participant;
import seedu.address.testutil.DefaultModelStub;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.ParticipantBuilder;

class AddParticipantToEventCommandTest {

    @Test
    public void execute_addParticipantToEventSuccessful() throws Exception {
        Participant validParticipant = new ParticipantBuilder().build();
        EventName eventName = SAMPLE_EVENT.getName();
        ModelStubWithEventAndParticipant modelStub =
                new ModelStubWithEventAndParticipant(validParticipant, new EventBuilder().build());

        CommandResult commandResult =
                new AddParticipantToEventCommand(Index.fromOneBased(1), Index.fromOneBased(1)).execute(modelStub);

        assertEquals(String.format(MESSAGE_ADD_PARTICIPANT_TO_EVENT_SUCCESS,
                validParticipant.getFullName(), eventName), commandResult.getFeedbackToUser());
        assertTrue(modelStub.event.hasParticipant(validParticipant));
    }

    @Test
    public void execute_participantNotInModel_throwsCommandException() {
        ModelStubWithEvent modelStub = new ModelStubWithEvent(new EventBuilder().build());

        AddParticipantToEventCommand addParticipantToEventCommand =
                new AddParticipantToEventCommand(Index.fromOneBased(1), Index.fromOneBased(1));

        assertThrows(CommandException.class,
                MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX, () -> addParticipantToEventCommand.execute(modelStub));
    }

    @Test
    public void execute_eventNotInModel_throwsCommandException() {
        Participant validParticipant = new ParticipantBuilder().build();
        ModelStubWithParticipant modelStub = new ModelStubWithParticipant(validParticipant);

        AddParticipantToEventCommand addParticipantToEventCommand =
                new AddParticipantToEventCommand(Index.fromOneBased(1), Index.fromOneBased(1));

        assertThrows(CommandException.class,
                MESSAGE_INVALID_EVENT_DISPLAYED_INDEX, () -> addParticipantToEventCommand.execute(modelStub));
    }

    @Test
    public void execute_eventContainsParticipant_throwsCommandException() {
        Participant validParticipant = new ParticipantBuilder().build();
        EventBuilder eventBuilder = new EventBuilder();
        eventBuilder.addParticipant(validParticipant);
        Event event = eventBuilder.build();

        ModelStubWithEventAndParticipant modelStub =
                new ModelStubWithEventAndParticipant(validParticipant, event);

        AddParticipantToEventCommand addParticipantToEventCommand =
                new AddParticipantToEventCommand(Index.fromOneBased(1), Index.fromOneBased(1));

        assertThrows(CommandException.class,
                Messages.showParticipantAlreadyEnrolled(validParticipant.getFullName(), event.getNameString()), () ->
                        addParticipantToEventCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AddParticipantToEventCommand addFirstParticipantFirstEvent =
                new AddParticipantToEventCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        AddParticipantToEventCommand addSecondParticipantFirstEvent =
                new AddParticipantToEventCommand(Index.fromOneBased(2), Index.fromOneBased(1));
        AddParticipantToEventCommand addFirstParticipantSecondEvent =
                new AddParticipantToEventCommand(Index.fromOneBased(1), Index.fromOneBased(2));

        // same object -> returns true
        assertTrue(addFirstParticipantFirstEvent.equals(addFirstParticipantFirstEvent));

        // same values -> returns true
        AddParticipantToEventCommand addFirstParticipantFirstEventCopy =
                new AddParticipantToEventCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        assertTrue(addFirstParticipantFirstEvent.equals(addFirstParticipantFirstEventCopy));

        // different types -> returns false
        assertFalse(addFirstParticipantFirstEvent.equals(1));

        // null -> returns false
        assertFalse(addFirstParticipantFirstEvent.equals(null));

        // different participant -> returns false
        assertFalse(addFirstParticipantFirstEvent.equals(addSecondParticipantFirstEvent));

        // different event -> returns false
        assertFalse(addFirstParticipantFirstEvent.equals(addFirstParticipantSecondEvent));
    }

    /**
     * A Model stub that contains a single Event and single Participant.
     */
    private class ModelStubWithEventAndParticipant extends DefaultModelStub {
        private final Participant participant;
        private final Event event;

        ModelStubWithEventAndParticipant(Participant participant, Event event) {
            requireNonNull(event);
            this.participant = participant;
            this.event = event;
        }

        @Override
        public ObservableList<Participant> getFilteredParticipantList() {
            ObservableList<Participant> participants = FXCollections.observableArrayList();
            participants.add(participant);
            return participants;
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            ObservableList<Event> events = FXCollections.observableArrayList();
            events.add(event);
            return events;
        }
    }

    /**
     * A Model stub that contains a single Participant.
     */
    private class ModelStubWithParticipant extends DefaultModelStub {
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

        @Override
        public ObservableList<Event> getFilteredEventList() {
            return FXCollections.observableArrayList();
        }
    }

    /**
     * A Model stub that contains a single Event.
     */
    private class ModelStubWithEvent extends DefaultModelStub {
        private final Event event;

        ModelStubWithEvent(Event event) {
            requireNonNull(event);
            this.event = event;
        }

        @Override
        public ObservableList<Participant> getFilteredParticipantList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            ObservableList<Event> events = FXCollections.observableArrayList();
            events.add(event);
            return events;
        }
    }
}
