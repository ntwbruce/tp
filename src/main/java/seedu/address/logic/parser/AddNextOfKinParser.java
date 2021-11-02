package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddNextOfKinCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.participant.Name;
import seedu.address.model.participant.NextOfKin;
import seedu.address.model.participant.Phone;
import seedu.address.model.tag.Tag;

public class AddNextOfKinParser implements Parser<AddNextOfKinCommand> {

    @Override
    public AddNextOfKinCommand parse(String args) throws ParseException {

        String[] sections = args.split("\\s+");

        if (sections.length < 5) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AddNextOfKinCommand.MESSAGE_USAGE));
        }

        Index participantIndex = ParserUtil.parseIndex(sections[1]);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNextOfKinCommand.MESSAGE_USAGE));
        }

        // REQUIRED
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Tag tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());

        NextOfKin nok = new NextOfKin(name, phone, tag);

        return new AddNextOfKinCommand(participantIndex, nok);
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
