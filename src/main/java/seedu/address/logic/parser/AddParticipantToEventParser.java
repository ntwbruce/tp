package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddParticipantToEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AddParticipantToEventParser implements Parser<AddParticipantToEventCommand> {

    @Override
    public AddParticipantToEventCommand parse(String args) throws ParseException {
        String[] sections = args.split(" ");

        if (sections.length != 3) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AddParticipantToEventCommand.MESSAGE_USAGE));
        }

        Index participantIndex = ParserUtil.parseIndex(sections[1]);
        Index eventIndex = ParserUtil.parseIndex(sections[2]);
        return new AddParticipantToEventCommand(participantIndex, eventIndex);
    }
}
