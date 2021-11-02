package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_EVENTS_LISTED_OVERVIEW = "%1$d events listed!";
    public static final String MESSAGE_EVENT_NOT_FOUND = "The Event you are looking for does not exist!";
    public static final String MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX =
            "The participant index provided is invalid!";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX =
            "The event index provided is invalid!";
    public static final String MESSAGE_PARTICIPANTS_LISTED_OVERVIEW = "%1$d participants listed";
    public static final String MESSAGE_INVALID_ZERO_BASED_INDEX =
            "The index provided cannot be less than or equal to 0";
    public static final String MESSAGE_PARTICIPANT_NOT_FOUND = "Participant of id: %1$s not found, consider "
            + "relisting the participants using '%2$s'";
    public static final String MESSAGE_EVENT_NOT_FOUND_IN_FILTERED_LIST =
            "Event %1$s Not Found, consider relisting events using '%2$s'";

    /**
     * Forms the message that states that a Participant does not exist in an Event in Managera.
     *
     * @param participantName The name of the Participant.
     * @param eventName       The name of the Event.
     * @return a message that states that the Participant does not exist in an Event.
     */
    public static String showParticipantDoesNotExist(String participantName, String eventName) {
        return String.format("%s is not taking part in %s!", participantName, eventName);
    }

    /**
     * Forms the message that indicates a participant is already enrolled in an Event in Managera.
     *
     * @param participantName  The name of the Participant.
     * @param eventName        The name of the Event.
     * @return a message that states that the participant is already in the given event.
     */
    public static String showParticipantAlreadyEnrolled(String participantName, String eventName) {
        return String.format("%s is already enrolled into %s!", participantName, eventName);
    }

    /**
     * Forms the message that states that a next of kin is already assigned to a participant.
     *
     * @param nextOfKinName The name of the next of kin.
     * @return a message that states that the next of kin is already assigned to a participant.
     */
    public static String showNextOfKinExists(String nextOfKinName, String participantName) {
        return nextOfKinName + " is already next of kin of " + participantName + "!";
    }

}
