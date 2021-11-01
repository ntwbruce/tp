package seedu.address.model.participant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalEvents.ANOTHER_EVENT;
import static seedu.address.testutil.TypicalEvents.SAMPLE_EVENT;
import static seedu.address.testutil.TypicalParticipants.ALEX;
import static seedu.address.testutil.TypicalParticipants.ALEX_DIFFERENT_ADDRESS;
import static seedu.address.testutil.TypicalParticipants.ALEX_DIFFERENT_BIRTHDATE;
import static seedu.address.testutil.TypicalParticipants.ALEX_DIFFERENT_EMAIL;
import static seedu.address.testutil.TypicalParticipants.ALEX_DIFFERENT_NOK;
import static seedu.address.testutil.TypicalParticipants.ALEX_DIFFERENT_PHONE;
import static seedu.address.testutil.TypicalParticipants.BERNICE;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.Event;
import seedu.address.testutil.ParticipantBuilder;

public class ParticipantTest {

    @Test
    public void isSameParticipantTest() {
        assertTrue(ALEX.isSameParticipant(ALEX));
        assertTrue(BERNICE.isSameParticipant(BERNICE));
        assertFalse(ALEX.isSameParticipant(BERNICE));
        assertFalse(BERNICE.isSameParticipant(ALEX));
        assertFalse(ALEX.isSameParticipant(ALEX_DIFFERENT_BIRTHDATE));
        assertFalse(ALEX.isSameParticipant(null));
    }

    @Test
    public void withBirthDateTest() {
        Participant alexP = new ParticipantBuilder().withBirthDate(2000, 8, 4).build();
        Participant bobP = new ParticipantBuilder().build();
        assertEquals("2000-08-04", alexP.getBirthDateString());
        assertEquals("N/A", bobP.getBirthDateString());
    }

    @Test
    public void addEventTest() {
        Participant alexP = new ParticipantBuilder().build();
        alexP.addEvent(SAMPLE_EVENT);
        alexP.addEvent(ANOTHER_EVENT);
        assertFalse(alexP.getEvents().isEmpty());
        ArrayList<Event> expectedEvents = new ArrayList<>(List.of(SAMPLE_EVENT, ANOTHER_EVENT));
        assertEquals(alexP.getEvents(), expectedEvents);
    }

    @Test
    public void deleteEventTest() {
        Participant alexP = new ParticipantBuilder().build();
        assertTrue(alexP.getEvents().isEmpty());
        alexP.addEvent(SAMPLE_EVENT);
        alexP.addEvent(ANOTHER_EVENT);
        alexP.deleteEvent(ANOTHER_EVENT);
        ArrayList<Event> expectedEvents = new ArrayList<>(List.of(SAMPLE_EVENT));
        assertEquals(alexP.getEvents(), expectedEvents);
        alexP.deleteEvent(SAMPLE_EVENT);
        assertTrue(alexP.getEvents().isEmpty());
    }


    @Test
    public void equals() {
        Participant alexCopy = new ParticipantBuilder(ALEX).build();

        // same object -> returns true
        assertEquals(ALEX, ALEX);

        // same details, different object -> returns false
        assertNotEquals(alexCopy, ALEX);

        // null -> returns false
        assertNotEquals(ALEX, null);

        // different type -> returns false
        assertNotEquals(ALEX, 5);

        // different person -> returns false
        assertNotEquals(BERNICE, ALEX);

        // different phone number -> return false
        assertNotEquals(ALEX, ALEX_DIFFERENT_PHONE);

        // different email -> return false
        assertNotEquals(ALEX, ALEX_DIFFERENT_EMAIL);

        // different address -> return false
        assertNotEquals(ALEX, ALEX_DIFFERENT_ADDRESS);

        // different birthdate -> return false
        assertNotEquals(ALEX, ALEX_DIFFERENT_BIRTHDATE);

        // different next of kin -> return false
        assertNotEquals(ALEX, ALEX_DIFFERENT_NOK);
    }
}
