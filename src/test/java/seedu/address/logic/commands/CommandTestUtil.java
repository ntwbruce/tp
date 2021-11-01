package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.model.participant.Participant;
import seedu.address.model.participant.ParticipantNameContainsKeywordsPredicate;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EditParticipantDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_BIRTHDATE_AMY = "2001-08-22";
    public static final String VALID_BIRTHDATE_BOB = "1989-12-31";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final String VALID_EVENT_NAME_SLEEP = "Sleep";
    public static final String VALID_EVENT_NAME_JOGGING = "Jogging";
    public static final String VALID_EVENT_DATE_SLEEP = "2022-10-10";
    public static final String VALID_EVENT_DATE_JOGGING = "2022-12-12";
    public static final String VALID_EVENT_TIME_SLEEP = "0000";
    public static final String VALID_EVENT_TIME_JOGGING = "2359";

    public static final String INVALID_EVENT_NAME_DESC = " " + PREFIX_NAME + "!@#@#!@";
    public static final String INVALID_EVENT_DATE_DESC = " " + PREFIX_DATE + "2021-13";
    public static final String INVALID_EVENT_TIME_DESC = " " + PREFIX_TIME + "2500";

    public static final String EVENT_NAME_DESC_SLEEP = " " + PREFIX_NAME + VALID_EVENT_NAME_SLEEP;
    public static final String EVENT_NAME_DESC_JOGGING = " " + PREFIX_NAME + VALID_EVENT_NAME_JOGGING;
    public static final String EVENT_DATE_DESC_SLEEP = " " + PREFIX_DATE + VALID_EVENT_DATE_SLEEP;
    public static final String EVENT_DATE_DESC_JOGGING = " " + PREFIX_DATE + VALID_EVENT_DATE_JOGGING;
    public static final String EVENT_TIME_DESC_SLEEP = " " + PREFIX_TIME + VALID_EVENT_TIME_SLEEP;
    public static final String EVENT_TIME_DESC_JOGGING = " " + PREFIX_TIME + VALID_EVENT_TIME_JOGGING;

    public static final EditCommand.EditParticipantDescriptor DESC_P_AMY;
    public static final EditCommand.EditParticipantDescriptor DESC_P_BOB;

    public static final EditEventCommand.EditEventDescriptor DESC_E_SLEEP;
    public static final EditEventCommand.EditEventDescriptor DESC_E_JOGGING;

    static {
        DESC_P_AMY = new EditParticipantDescriptorBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).build();;
        DESC_P_BOB = new EditParticipantDescriptorBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).build();
        DESC_E_SLEEP = new EditEventDescriptorBuilder().withEventName(VALID_EVENT_NAME_SLEEP).build();
        DESC_E_JOGGING = new EditEventDescriptorBuilder().withEventName(VALID_EVENT_NAME_JOGGING).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Participant> expectedFilteredList = new ArrayList<>(actualModel.getFilteredParticipantList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredParticipantList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the Participant at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showParticipantAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredParticipantList().size());

        Participant participant = model.getFilteredParticipantList().get(targetIndex.getZeroBased());
        final String[] splitName = participant.getFullName().split("\\s+");
        model.updateFilteredParticipantList(new ParticipantNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredParticipantList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the Event at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showEventAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEventList().size());

        Event event = model.getFilteredEventList().get(targetIndex.getZeroBased());
        final String[] splitName = event.getNameString().split("\\s+");
        model.updateFilteredEventList(new EventNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredEventList().size());
    }
}
