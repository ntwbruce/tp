package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.participant.Address;
import seedu.address.model.participant.BirthDate;
import seedu.address.model.participant.Email;
import seedu.address.model.participant.Name;
import seedu.address.model.participant.NextOfKin;
import seedu.address.model.participant.Participant;
import seedu.address.model.participant.ParticipantId;
import seedu.address.model.participant.Phone;

/**
 * Edits the details of an existing Participant in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the participant identified "
            + "by the index number used in the displayed participant list. "
            + "Index should be positive integer.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_DATE + "BIRTHDATE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PARTICIPANT_SUCCESS = "Edited participant:\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PARTICIPANT = "This participant already exists in Managera.";

    private final Index index;
    private final EditParticipantDescriptor editParticipantDescriptor;

    /**
     * @param index                     of the Participant in the filtered Participant list to edit
     * @param editParticipantDescriptor details to edit the Participant with
     */
    public EditCommand(Index index, EditParticipantDescriptor editParticipantDescriptor) {
        requireNonNull(index);
        requireNonNull(editParticipantDescriptor);

        this.index = index;
        this.editParticipantDescriptor = new EditParticipantDescriptor(editParticipantDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Participant> lastShownList = model.getFilteredParticipantList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX);
        }

        Participant participantToEdit = lastShownList.get(index.getZeroBased());
        Participant editedParticipant = createEditedParticipant(participantToEdit, editParticipantDescriptor);

        if (!participantToEdit.isSameParticipant(editedParticipant) && model.hasParticipant(editedParticipant)) {
            throw new CommandException(MESSAGE_DUPLICATE_PARTICIPANT);
        }

        model.setParticipant(participantToEdit, editedParticipant);
        return new CommandResult(String.format(MESSAGE_EDIT_PARTICIPANT_SUCCESS, editedParticipant));
    }

    /**
     * Creates and returns a {@code Participant} with the details of {@code participantToEdit}
     * edited with {@code editParticipantDescriptor}.
     */
    private static Participant createEditedParticipant(Participant participantToEdit,
                                                       EditParticipantDescriptor editParticipantDescriptor) {
        assert participantToEdit != null;

        Name updatedName = editParticipantDescriptor.getName().orElse(participantToEdit.getName());
        Phone updatedPhone = editParticipantDescriptor.getPhone().orElse(participantToEdit.getPhone());
        Email updatedEmail = editParticipantDescriptor.getEmail().orElse(participantToEdit.getEmail());
        Address updatedAddress = editParticipantDescriptor.getAddress().orElse(participantToEdit.getAddress());
        BirthDate updatedBirthDate = editParticipantDescriptor.getBirthDate().orElse(participantToEdit.getBirthDate());
        ObservableList<NextOfKin> updatedNextOfKins =
                editParticipantDescriptor.getNextOfKins().orElse(participantToEdit.getNextOfKins());

        if (isIdUnchanged(participantToEdit.getName(), updatedName)) {
            return new Participant(updatedName, updatedPhone, updatedEmail, updatedAddress,
                    updatedBirthDate, updatedNextOfKins, participantToEdit.getParticipantId());
        }

        return new Participant(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedBirthDate,
                updatedNextOfKins);
    }

    /**
     * Checks if the edited {@code Participant} needs to be assigned a new Participant ID.
     *
     * @param currentName Current Participant name.
     * @param updatedName Updated Participant name.
     * @return true if the Participant ID is unchanged.
     */
    private static boolean isIdUnchanged(Name currentName, Name updatedName) {
        return ParticipantId.generateIdString(currentName.toString())
                .equals(ParticipantId.generateIdString(updatedName.toString()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
            && editParticipantDescriptor.equals(e.editParticipantDescriptor);
    }

    /**
     * Stores the details to edit the Participant with. Each non-empty field value will replace the
     * corresponding field value of the Participant.
     */
    public static class EditParticipantDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private BirthDate birthDate;
        private ObservableList<NextOfKin> nextOfKins;

        public EditParticipantDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditParticipantDescriptor(EditParticipantDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setBirthDate(toCopy.birthDate);
            setNextOfKins(toCopy.nextOfKins);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, birthDate);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setBirthDate(BirthDate birthDate) {
            this.birthDate = birthDate;
        }

        public Optional<BirthDate> getBirthDate() {
            return Optional.ofNullable(birthDate);
        }

        public void setNextOfKins(ObservableList<NextOfKin> nextOfKins) {
            this.nextOfKins = (nextOfKins != null) ? FXCollections.observableArrayList(nextOfKins) : null;
        }

        public Optional<ObservableList<NextOfKin>> getNextOfKins() {
            return (nextOfKins != null) ? Optional.of(FXCollections.observableArrayList((nextOfKins)))
                    : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditParticipantDescriptor)) {
                return false;
            }

            // state check
            EditParticipantDescriptor e = (EditParticipantDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getBirthDate().equals(e.getBirthDate());
        }
    }
}
