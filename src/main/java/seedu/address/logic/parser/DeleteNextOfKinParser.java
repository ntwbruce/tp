package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteNextOfKinCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteNextOfKinParser implements Parser<DeleteNextOfKinCommand> {
    @Override
    public DeleteNextOfKinCommand parse(String args) throws ParseException {
        String[] sections = args.split("\\s+");

        if (sections.length != 3) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, DeleteNextOfKinCommand.MESSAGE_USAGE));
        }

        Index nokIndex = ParserUtil.parseIndex(sections[1]);
        Index participantIndex = ParserUtil.parseIndex(sections[2]);
        return new DeleteNextOfKinCommand(nokIndex, participantIndex);
    }
}
