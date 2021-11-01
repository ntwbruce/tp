package seedu.address.testutil;

import static seedu.address.testutil.TypicalParticipants.ALEX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.participant.Participant;

/**
 * An utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {
    public static final Participant SAMPLE_PARTICIPANT = new ParticipantBuilder().build();

    public static final Event SAMPLE_EVENT = new Event(new EventName("Sleep"), new EventDate("2022-09-18"),
            new EventTime("1000"));
    public static final Event ANOTHER_EVENT = new Event(new EventName("Sleep2"), new EventDate("2022-09-18"),
            new EventTime("1001"));
    public static final Event SAMPLE_EVENT_2 = new Event(new EventName("Random Event 1"),
            new EventDate("1-1-1"), new EventTime("0001"));
    public static final Event SAMPLE_EVENT_3 = new Event(new EventName("Sleep"), new EventDate("2022-09-18"),
            new EventTime("1002"), false, Collections.singletonList(new ParticipantBuilder().build()));
    public static final Event MARATHON_NO_TIME = new Event(new EventName("24km Marathon"),
            new EventDate("2022-10-5"), new EventTime());
    public static final Event MARATHON_HAS_TIME = new Event(new EventName("24Km Marathon with Time"),
            new EventDate("2022-10-5"), new EventTime("0800"));
    public static final Event CODE_FOR_SANITY = new Event(new EventName("Code For Sanity"),
            new EventDate("2022-8-10"), new EventTime("0000"));
    public static final Event SAMPLE_EVENT_SPECIFIED_TIME_AND_COMPLETION = new Event(new EventName("Sleep"),
            new EventDate("2022-09-18"), new EventTime("1002"), true,
            Collections.singletonList(SAMPLE_PARTICIPANT));
    public static final Event SAMPLE_EVENT_SPECIFIED_TIME_AND_COMPLETION_COPY = new Event(new EventName("Sleep"),
            new EventDate("2022-09-18"), new EventTime("1002"), true,
            Collections.singletonList(SAMPLE_PARTICIPANT));
    public static final Event SAMPLE_EVENT_DEFAULT_TIME_AND_COMPLETION = new Event(new EventName("Sleep again"),
            new EventDate("2022-09-18"));
    public static final Event SAMPLE_EVENT_COPY_DIFFERENT_NAME = new Event(new EventName("Sleeps"),
            new EventDate("2022-09-18"), new EventTime("1000"));
    public static final Event SAMPLE_EVENT_COPY_DIFFERENT_DATE = new Event(new EventName("Sleep"),
            new EventDate("2022-09-19"), new EventTime("1000"));
    public static final Event SAMPLE_EVENT_COPY_DIFFERENT_TIME = new Event(new EventName("Sleep"),
            new EventDate("2022-09-18"), new EventTime("1001"));
    public static final Event SAMPLE_EVENT_COPY_DIFFERENT_COMPLETION = new Event(new EventName("Sleep"),
            new EventDate("2022-09-18"), new EventTime("1000"), true, new ArrayList<>());
    public static final Event SAMPLE_EVENT_COPY_DIFFERENT_PARTICIPANTS = new Event(new EventName("Sleep"),
            new EventDate("2022-09-18"), new EventTime("1000"), false,
            Collections.singletonList(ALEX));
    public static final Event SAMPLE_EVENT_COPY_NO_TIME = new Event(new EventName("Slept"),
            new EventDate("2022-09-18"));

    private TypicalEvents() {}

    /**
     * Returns an {@code AddressBook} with all the typical events.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with a copy of all the typical events.
     * Used when events in the addressbook are mutated.
     */
    public static AddressBook getTypicalAddressBookCopy() {
        AddressBook ab = new AddressBook();
        for (Event event : getTypicalEvents()) {
            ab.addEvent(new EventBuilder(event).build());
        }
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(SAMPLE_EVENT, SAMPLE_EVENT_COPY_NO_TIME, SAMPLE_EVENT_COPY_DIFFERENT_NAME,
                ANOTHER_EVENT, SAMPLE_EVENT_2));
    }
}
