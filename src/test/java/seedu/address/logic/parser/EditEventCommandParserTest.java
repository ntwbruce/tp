package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_JOGGING;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_SLEEP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_JOGGING;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_SLEEP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_TIME_DESC_JOGGING;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_TIME_DESC_SLEEP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_JOGGING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_SLEEP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_JOGGING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_SLEEP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TIME_JOGGING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TIME_SLEEP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditEventCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

    private EditEventCommandParser parser = new EditEventCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_EVENT_NAME_JOGGING, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditEventCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PREFIX_NAME + VALID_EVENT_NAME_JOGGING, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PREFIX_NAME + VALID_EVENT_NAME_JOGGING, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid EventName
        assertParseFailure(parser, "1" + INVALID_EVENT_NAME_DESC, EventName.MESSAGE_CONSTRAINTS);
        // invalid EventDate
        assertParseFailure(parser, "1" + INVALID_EVENT_DATE_DESC, EventDate.MESSAGE_DATE_FORMAT_ERROR);
        // invalid EventTime
        assertParseFailure(parser, "1" + INVALID_EVENT_TIME_DESC, EventTime.MESSAGE_CONSTRAINTS);

        // valid EventName followed by invalid EventDate
        assertParseFailure(parser, "1" + EVENT_NAME_DESC_JOGGING + INVALID_EVENT_DATE_DESC,
                EventDate.MESSAGE_DATE_FORMAT_ERROR);

        // invalid EventName followed by valid EventDate
        assertParseFailure(parser, "1" + INVALID_EVENT_NAME_DESC + EVENT_DATE_DESC_JOGGING,
                EventName.MESSAGE_CONSTRAINTS);

        // multiple invalid values, only first is captured
        assertParseFailure(parser, "1" + INVALID_EVENT_NAME_DESC + PREFIX_DATE + "2021-13" + PREFIX_TIME + "3333",
                EventName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EVENT;
        String userInput = targetIndex.getOneBased() + EVENT_NAME_DESC_JOGGING + EVENT_DATE_DESC_JOGGING
                + EVENT_TIME_DESC_JOGGING;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withEventName(VALID_EVENT_NAME_JOGGING)
                .withEventDate(VALID_EVENT_DATE_JOGGING).withEventTime(VALID_EVENT_TIME_JOGGING).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + EVENT_NAME_DESC_SLEEP + EVENT_TIME_DESC_SLEEP;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withEventName(VALID_EVENT_NAME_SLEEP)
                .withEventTime(VALID_EVENT_TIME_SLEEP).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // EventName
        Index targetIndex = INDEX_SECOND_EVENT;
        String userInput = targetIndex.getOneBased() + EVENT_NAME_DESC_SLEEP;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withEventName(VALID_EVENT_NAME_SLEEP).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // EventDate
        userInput = targetIndex.getOneBased() + EVENT_DATE_DESC_SLEEP;
        descriptor = new EditEventDescriptorBuilder().withEventDate(VALID_EVENT_DATE_SLEEP).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // EventTime
        userInput = targetIndex.getOneBased() + EVENT_TIME_DESC_SLEEP;
        descriptor = new EditEventDescriptorBuilder().withEventTime(VALID_EVENT_TIME_SLEEP).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + EVENT_TIME_DESC_SLEEP + EVENT_TIME_DESC_JOGGING;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withEventTime(VALID_EVENT_TIME_JOGGING).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + INVALID_EVENT_NAME_DESC + EVENT_NAME_DESC_SLEEP;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withEventName(VALID_EVENT_NAME_SLEEP).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values
        userInput = targetIndex.getOneBased() + EVENT_DATE_DESC_JOGGING
                + INVALID_EVENT_TIME_DESC + EVENT_TIME_DESC_JOGGING;
        descriptor = new EditEventDescriptorBuilder().withEventDate(VALID_EVENT_DATE_JOGGING)
                .withEventTime(VALID_EVENT_TIME_JOGGING).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
