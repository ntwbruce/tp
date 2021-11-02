package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.participant.Participant;

/**
 * Adds a Participant to Managera.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a participant to Managera. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_DATE + "BIRTHDATE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 ";

    public static final String MESSAGE_SUCCESS = "Added participant:\n%1$s";
    public static final String MESSAGE_DUPLICATE_PARTICIPANT = "This participant already exists in Managera";

    private final Participant toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Participant}
     */
    public AddCommand(Participant participant) {
        requireNonNull(participant);
        toAdd = participant;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasParticipant(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PARTICIPANT);
        }

        model.addParticipant(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof AddCommand)) {
            return false;
        }
        AddCommand otherAddCommand = (AddCommand) other;
        return otherAddCommand.toAdd.equals(toAdd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toAdd);
    }

    @Override
    public String toString() {
        return "AddCommand{"
            + "toAdd=" + toAdd
            + '}';
    }
}
