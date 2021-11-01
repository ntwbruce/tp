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

    public static final String MESSAGE_CONSTRAINTS = "Dates should be in YYYY-MM-DD format!";
    public static final String MESSAGE_PAST_DATE = "Date of new Event cannot be in the past!";
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

    /**
     * Returns true if a given LocalDate instance is in the present or future.
     * This is used to ensure the Event date is not in the past.
     *
     * @param test     A String representation of a LocalDate instance.
     * @return         A boolean representing if the date is in the present or future.
     */
    public static boolean isPresentOrFuture(String test) {
        LocalDate date = LocalDate.parse(test, DateTimeFormatter.ofPattern(DATE_FORMAT));
        return (LocalDate.now().isEqual(date) || LocalDate.now().isBefore(date));
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
