package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;

/**
 * Edits the details of an existing Event in Managera.
 */
public class EditEventCommand extends Command {

    public static final String COMMAND_WORD = "editEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event with specified index. "
            + "Index should be positive integer.\n"
            + "Existing details will be overwritten by the new input details.\n"
            + "Parameters: INDEX "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TIME + "TIME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "24Km Run "
            + PREFIX_TIME + "0000";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited event:\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in Managera";

    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * This is a constructor of EditEventCommand.
     *
     * @param index  of the Event in the filtered Event list to edit.
     */
    public EditEventCommand(Index index, EditEventDescriptor editEventDescriptor) {
        requireNonNull(index);
        requireNonNull(editEventDescriptor);
        this.index = index;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownEventList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = lastShownEventList.get(index.getZeroBased());
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

        if (!eventToEdit.isSameEvent(editedEvent) && model.hasEvent(editedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }
        model.setEvent(eventToEdit, editedEvent);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EditEventCommand)) {
            return false;
        }
        EditEventCommand e = (EditEventCommand) other;
        return index.equals(e.index) && editEventDescriptor.equals(e.editEventDescriptor);
    }

    /**
     * Creates and returns an edited Event instance.
     *
     * @param eventToEdit           The event instance to be edited.
     * @param editEventDescriptor   The instance containing fields of the event to be edited.
     * @return An Event instance with fields modified.
     */
    private static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {
        requireNonNull(eventToEdit);
        requireNonNull(editEventDescriptor);

        EventName updatedEventName = editEventDescriptor.getEventName().orElse(eventToEdit.getName());
        EventDate updatedEventDate = editEventDescriptor.getEventDate().orElse(eventToEdit.getDate());
        EventTime updatedEventTime = editEventDescriptor.getEventTime().orElse(eventToEdit.getTime());

        return new Event(updatedEventName, updatedEventDate,
                updatedEventTime, eventToEdit.getDoneValue(), eventToEdit.getParticipants());
    }

    /**
     * Stores the details to edit the Event with. Each non-empty field value wil replace the
     * corresponding field value of the Event.
     */
    public static class EditEventDescriptor {
        private EventName eventName;
        private EventDate eventDate;
        private EventTime eventTime;

        public EditEventDescriptor() {
        }

        /**
         * Copy constructor.
         *
         * @param toCopy An instance of EditEventDescriptor.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setEventName(toCopy.eventName);
            setEventDate(toCopy.eventDate);
            setEventTime(toCopy.eventTime);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(eventName, eventDate, eventTime);
        }

        public void setEventName(EventName eventName) {
            this.eventName = eventName;
        }

        public Optional<EventName> getEventName() {
            return Optional.ofNullable(eventName);
        }

        public void setEventDate(EventDate eventDate) {
            this.eventDate = eventDate;
        }

        public Optional<EventDate> getEventDate() {
            return Optional.ofNullable(eventDate);
        }

        public void setEventTime(EventTime eventTime) {
            this.eventTime = eventTime;
        }

        public Optional<EventTime> getEventTime() {
            return Optional.ofNullable(eventTime);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }
            EditEventDescriptor e = (EditEventDescriptor) other;
            return getEventName().equals(e.getEventName())
                    && getEventDate().equals(e.getEventDate())
                    && getEventTime().equals(e.getEventTime());
        }
    }
}
