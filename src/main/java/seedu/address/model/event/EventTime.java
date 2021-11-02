package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * This is an EventTime class that represents the Time of an Event.
 */
public class EventTime implements Comparable<EventTime> {

    public static final String MESSAGE_CONSTRAINTS = "Time should be in 24hr format. "
            + "Input time should be from 0000 to 2359 inclusive.";
    private static final String TIME_FORMAT = "HHmm";
    private static final String TIME_DISPLAY_FORMAT = "HH:mm";

    private final LocalTime time;
    private boolean hasTime;

    /**
     * Constructors an {@code EventTime}
     *
     * @param time    A valid time.
     */
    public EventTime(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        this.time = LocalTime.parse(time, DateTimeFormatter.ofPattern(TIME_FORMAT));
        this.hasTime = true;
    }

    /**
     * This is an overloaded Constructor for Event Time with no time.
     */
    public EventTime() {
        this.time = LocalTime.MIN;
        this.hasTime = false;
    }

    /**
     * Returns true if a given string is a valid time.
     *
     * @param test A String to be tested if it is a valid time.
     * @return A boolean indicating if the string is a valid time.
     */
    public static boolean isValidTime(String test) {
        if (test.length() != 4) {
            return false;
        }
        DateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        sdf.setLenient(false);
        try {
            sdf.parse(test);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns the String format of time using {@code TIME_DISPLAY_FORMAT}.
     */
    public String getTimeDisplayString() {
        return hasTime ? time.format(DateTimeFormatter.ofPattern(TIME_DISPLAY_FORMAT)) : "";
    }

    @Override
    public String toString() {
        return this.hasTime ? this.time.format(DateTimeFormatter.ofPattern(TIME_FORMAT)) : "";
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof EventTime
                && hasTime == ((EventTime) other).hasTime && time.equals(((EventTime) other).time));
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }

    @Override
    public int compareTo(EventTime o) {
        if (hasTime && o.hasTime) {
            return this.time.compareTo(o.time);
        } else if (hasTime) {
            return 1;
        } else if (o.hasTime) {
            return -1;
        } else {
            return 0;
        }
    }
}
