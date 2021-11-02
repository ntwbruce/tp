package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This is an EventDate class representing the Date of an Event.
 */
public class EventDate implements Comparable<EventDate> {

    public static final String MESSAGE_CONSTRAINTS = "Date does not exist!";
    public static final String MESSAGE_DATE_FORMAT_ERROR = "Dates should be in YYYY-MM-DD format!";
    public static final String DATE_FORMAT = "y-M-d";

    private final LocalDate date;

    /**
     * Constructs an {@code EventDate}
     *
     * @param date of the Event.
     */
    public EventDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    /**
     * Returns true if a given string is a valid date.
     *
     * @param test A String representing a date to be tested.
     * @return A boolean to indicate if a string is a valid date.
     */
    public static boolean isValidDate(String test) {
        DateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);
        try {
            sdf.parse(test);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean checkDateComponents(String date) {
        return date.split("-").length == 3;
    }

    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof EventDate
                && date.equals(((EventDate) other).date));
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    @Override
    public int compareTo(EventDate o) {
        return date.compareTo(o.date);
    }
}
