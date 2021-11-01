package seedu.address.model.participant;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.IntStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Event;

/**
 * Represents a Participant in an event.
 * Guarantees: name, phone, email, address, birthDate, nextOfKins are present and not null, field values
 * are validated, immutable.
 */
public class Participant {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final ParticipantId id;
    private final BirthDate birthDate;
    private final ObservableList<NextOfKin> nextOfKins = FXCollections.observableArrayList();

    private final ArrayList<Event> events = new ArrayList<>();


    /**
     * Every field must be present and not null.
     * Called upon creation of new Participant.
     *
     * @param name       Name object of the participant.
     * @param phone      Phone object of the participant.
     * @param email      Email object of the participant.
     * @param address    Address object of the participant.
     * @param birthDate  birthdate of the participant.
     * @param nextOfKin  nextOfKin of the participant.
     */
    public Participant(Name name, Phone phone, Email email, Address address, BirthDate birthDate,
                       Collection<NextOfKin> nextOfKin) {
        requireAllNonNull(name, phone, email, address, birthDate);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.birthDate = birthDate;
        this.nextOfKins.addAll(nextOfKin);
        this.id = ParticipantId.of(this);
    }

    /**
     * Overloaded constructor with provided Participant ID. Every field must be present and not null.
     * Called when converting existing Participants in memory (JSONAdaptedParticipant) to Participant.
     *
     * @param name       Name object of the participant.
     * @param phone      Phone object of the participant.
     * @param email      Email object of the participant.
     * @param address    Address object of the participant.
     * @param birthDate  birthdate of the participant.
     * @param nextOfKin  nextOfKin of the participant.
     * @param id         ParticipantId object of the participant.
     */
    public Participant(Name name, Phone phone, Email email, Address address, BirthDate birthDate,
                       Collection<NextOfKin> nextOfKin, ParticipantId id) {
        requireAllNonNull(name, phone, email, address, birthDate);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.birthDate = birthDate;
        this.nextOfKins.addAll(nextOfKin);
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public String getFullName() {
        return name.toString();
    }

    public Phone getPhone() {
        return phone;
    }

    public String getPhoneValue() {
        return phone.toString();
    }

    public Email getEmail() {
        return email;
    }

    public String getEmailValue() {
        return email.toString();
    }

    public Address getAddress() {
        return address;
    }

    public String getAddressValue() {
        return address.toString();
    }

    /**
     * @return this object's birthDate.
     */
    public BirthDate getBirthDate() {
        return birthDate;
    }

    public String getBirthDateString() {
        return birthDate.toString();
    }

    /**
     * Returns an Arraylist of next of kin that represents the participant's next of kins.
     *
     * @return this object's nextOfKins.
     */
    public ObservableList<NextOfKin> getNextOfKins() {
        return nextOfKins;
    }

    public ParticipantId getParticipantId() {
        return id;
    }

    public String getParticipantIdValue() {
        return id.toString();
    }

    /**
     * Adds an Event to the list of events.
     *
     * @param event Event to be added.
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Deletes a specific Event from the list of events.
     *
     * @param event Event to be removed.
     */
    public void deleteEvent(Event event) {
        events.remove(event);
    }

    /**
     * Returns an Arraylist of events the participant is attending.
     *
     * @return this object's events.
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * Adds a next of kin to the participant.
     *
     * @param nextOfKin next of kin to be added.
     */
    public void addNextOfKin(NextOfKin nextOfKin) {
        nextOfKins.add(nextOfKin);
    }

    /**
     * Removes the next of kin from the participant.
     *
     * @param nextOfKin next of kin to be removed.
     */
    public void removeNextOfKin(NextOfKin nextOfKin) {
        nextOfKins.remove(nextOfKin);
    }

    /**
     * Returns a next of kin object at specified index.
     *
     * @param index index of next of kin to get.
     * @return next of kin at specified index.
     */
    public NextOfKin getNextOfKin(int index) {
        return nextOfKins.get(index);
    }

    public String getNextOfKinsListString() {
        final StringBuilder builder = new StringBuilder();
        IntStream.range(1, nextOfKins.size() + 1).forEach(i ->
                builder.append(i).append(". ").append(nextOfKins.get(i - 1).toString()).append("\n"));
        return builder.toString();
    }

    /**
     * Returns true if the given next of kin is already assigned to this participant.
     *
     * @param nextOfKin The given next of kin.
     * @return True if the nextOfKin is assigned to this participant.
     */
    public boolean hasNextOfKin(NextOfKin nextOfKin) {
        requireNonNull(nextOfKin);
        return this.nextOfKins.stream().anyMatch(nok -> nok.isSameNextOfKin(nextOfKin));
    }

    /**
     * Returns a string representation of the Participant's id.
     *
     * @return the Participant's id.
     */
    public String getIdValue() {
        return this.id.toString();
    }

    /**
     * Returns true if both participants have the same name.
     * This defines a weaker notion of equality between two participants.
     * This allow Participant to both pass in Person and Participant objects.
     */
    public boolean isSameParticipant(Participant otherParticipant) {
        if (otherParticipant == this) {
            return true;
        }

        return otherParticipant != null
                && otherParticipant.getName().equals(getName())
                && otherParticipant.getPhone().equals(getPhone())
                && otherParticipant.getEmail().equals(getEmail())
                && otherParticipant.getAddress().equals(getAddress())
                && otherParticipant.getBirthDate().equals(getBirthDate())
                && otherParticipant.getNextOfKins().equals(getNextOfKins());
    }

    /**
     * Removes this {@code Participant} from the {@code events} he/she is participating in.
     */
    public void deleteFromEvents() {
        for (int i = events.size() - 1; i >= 0; i--) {
            this.events.get(i).removeParticipant(this);
        }
    }

    /**
     * Re-links this participant with the newly editedEvent.
     *
     * @param oldEvent      An instance of event that was edited.
     * @param editedEvent   The newly edited event to link this participant to.
     */
    public void replaceEvent(Event oldEvent, Event editedEvent) {
        events.remove(oldEvent);
        events.add(editedEvent);
    }

    /**
     * Replaces this Participant with a given edited Participant in this Participant's Events.
     *
     * @param editedParticipant The given edited Participant.
     */
    public void shiftEvents(Participant editedParticipant) {
        for (int i = 0; i < events.size(); i++) {
            events.get(i).replaceParticipant(this, editedParticipant);
        }
        editedParticipant.getAllEvents(this.events);
    }

    private void getAllEvents(ArrayList<Event> events) {
        this.events.addAll(events);
    }

    /**
     * Returns true if both participants have the same identity and data fields.
     * This defines a stronger notion of equality between two participants.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Participant)) {
            return false;
        }

        Participant otherParticipant = (Participant) other;
        return otherParticipant.getName().equals(getName())
                && otherParticipant.getPhone().equals(getPhone())
                && otherParticipant.getEmail().equals(getEmail())
                && otherParticipant.getAddress().equals(getAddress())
                && otherParticipant.getBirthDate().equals(getBirthDate())
                && otherParticipant.getNextOfKins().equals(getNextOfKins())
                && otherParticipant.getParticipantId().equals(getParticipantId());

    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(getName(), getPhone(), getEmail(), getAddress(), birthDate, nextOfKins);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(getName())
                .append("\nPhone: ")
                .append(getPhone())
                .append("\nEmail: ")
                .append(getEmail())
                .append("\nAddress: ")
                .append(getAddress());

        builder.append("\nDate of birth: ").append(getBirthDate());

        if (!nextOfKins.isEmpty()) {
            builder.append("\nNext Of Kins: \n");
            builder.append(getNextOfKinsListString());

        }

        if (!events.isEmpty()) {
            builder.append("\n\nAttending events:\n").append(formEventsList());
        }

        return builder.toString();
    }

    private String formEventsList() {
        int index = 1;
        StringBuilder builder = new StringBuilder();
        for (Event event : events) {
            builder.append(index)
                    .append(". ")
                    .append(event.getNameString())
                    .append("\n");
            index++;
        }
        return builder.toString();
    }
}
