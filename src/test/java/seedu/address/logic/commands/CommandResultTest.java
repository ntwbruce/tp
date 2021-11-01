package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.AddCommandParser;
import seedu.address.logic.parser.AddEventCommandParser;
import seedu.address.logic.parser.AddParticipantToEventParser;
import seedu.address.logic.parser.DeleteCommandParser;
import seedu.address.logic.parser.DeleteEventCommandParser;
import seedu.address.logic.parser.DoneCommandParser;
import seedu.address.logic.parser.EditCommandParser;
import seedu.address.logic.parser.FilterEventCommandParser;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.RemoveParticipantFromEventParser;
import seedu.address.logic.parser.ShowEventDetailsCommandParser;
import seedu.address.logic.parser.ShowEventParticipantsCommandParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CommandResultTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));
    }

    @Test
    public void isShowHelp_addCommand_returnsFalse() throws Exception {
        // CommandResult from AddCommand
        assertFalse(new AddCommandParser().parse(" n/Tom p/123 e/a@a.com a/Road").execute(model).isShowHelp());
    }

    @Test
    public void isShowHelp_addEventCommand_returnsFalse() throws Exception {
        // CommandResult from AddEventCommand
        assertFalse(new AddEventCommandParser().parse(" n/Marathon SG d/2022-10-10").execute(model).isShowHelp());
    }

    @Test
    public void isShowHelp_addParticipantToEventCommand_returnsFalse() throws Exception {
        // CommandResult from AddParticipantToEvent
        AddCommand addCommand = new AddCommandParser().parse(" n/Tom p/123 e/a@a.com a/Road");
        addCommand.execute(model);
        AddEventCommand addEventCommand = new AddEventCommandParser().parse(" n/M d/2021-11-11");
        addEventCommand.execute(model);
        assertFalse(new AddParticipantToEventParser().parse(" 1 1").execute(model).isShowHelp());
    }

    @Test
    public void isShowHelp_deleteCommand_returnsFalse() throws Exception {
        // CommandResult from DeleteCommand
        AddCommand addCommand = new AddCommandParser().parse(" n/Tom p/123 e/a@a.com a/Road");
        addCommand.execute(model);
        assertFalse(new DeleteCommandParser().parse(" 1").execute(model).isShowHelp());
    }

    @Test
    public void isShowHelp_doneEventCommand_returnsFalse() throws Exception {
        // CommandResult from DoneCommand
        AddEventCommand addEventCommand = new AddEventCommandParser().parse(" n/M d/2021-11-11");
        addEventCommand.execute(model);
        assertFalse(new DoneCommandParser().parse(" 1").execute(model).isShowHelp());
    }

    @Test
    public void isShowHelp_editCommand_returnsFalse() throws Exception {
        // CommandResult from EditCommand
        AddCommand addCommand = new AddCommandParser().parse(" n/Tom p/123 e/a@a.com a/Road");
        addCommand.execute(model);
        assertFalse(new EditCommandParser().parse(" 1 n/Kenny").execute(model).isShowHelp());
    }

    @Test
    public void isShowHelp_filterEventCommand_returnsFalse() throws Exception {
        // CommandResult from FilterEventCommand
        assertFalse(new FilterEventCommandParser().parse(" d/2021-11-1").execute(model).isShowHelp());
    }

    @Test
    public void isShowHelp_findCommand_returnsFalse() throws Exception {
        // CommandResult from FindCommand
        assertFalse(new FindCommandParser().parse(" tom").execute(model).isShowHelp());
    }

    @Test
    public void isShowHelp_listCommand_returnsFalse() throws Exception {
        // CommandResult from ListCommand
        assertFalse(new ListCommand().execute(model).isShowHelp());
    }

    @Test
    public void isShowHelp_listEventCommand_returnsFalse() throws Exception {
        // CommandResult from ListEventCommand
        assertFalse(new ListEventCommand().execute(model).isShowHelp());
    }

    @Test
    public void isShowHelp_removeEventCommand_returnsFalse() throws Exception {
        // CommandResult from RemoveEventCommand
        AddEventCommand addEventCommand = new AddEventCommandParser().parse(" n/M d/2021-11-11");
        addEventCommand.execute(model);
        assertFalse(new DeleteEventCommandParser().parse(" 1").execute(model).isShowHelp());
    }

    @Test
    public void isShowHelp_removeParticipantFromEventCommand_returnsFalse() throws Exception {
        // CommandResult from RemoveParticipantFromEventCommand
        AddCommand addCommand = new AddCommandParser().parse(" n/Tom p/123 e/a@a.com a/Road");
        addCommand.execute(model);
        AddEventCommand addEventCommand = new AddEventCommandParser().parse(" n/M d/2021-11-11");
        addEventCommand.execute(model);

        AddParticipantToEventCommand addParticipantToEventCommand = new AddParticipantToEventParser()
                .parse(" 1 1");
        addParticipantToEventCommand.execute(model);
        assertFalse(new RemoveParticipantFromEventParser().parse(" 1 1").execute(model).isShowHelp());
    }

    @Test
    public void isShowHelp_showEventDetailsCommand_returnsFalse() throws Exception {
        // CommandResult from ShowEventDetailsCommand
        AddEventCommand addEventCommand = new AddEventCommandParser().parse(" n/M d/2021-11-11");
        addEventCommand.execute(model);
        assertFalse(new ShowEventDetailsCommandParser().parse(" 1").execute(model).isShowHelp());
    }

    @Test
    public void isShowHelp_showEventParticipantsCommand_returnsFalse() throws Exception {
        // CommandResult from ShowEventParticipantsCommand
        AddEventCommand addEventCommand = new AddEventCommandParser().parse(" n/M d/2021-11-11");
        addEventCommand.execute(model);
        assertFalse(new ShowEventParticipantsCommandParser().parse(" 1").execute(model).isShowHelp());
    }

    @Test
    public void isShowHelp_sortEventCommand_returnsFalse() throws Exception {
        // CommandResult from SortEventCommand
        assertFalse(new SortEventCommand().execute(model).isShowHelp());
    }

    @Test
    public void isShowHelp_clearCommand_returnsFalse() throws Exception {
        // CommandResult from ClearCommand
        assertFalse(new ClearCommand().execute(model).isShowHelp());
    }

    @Test
    public void isShowHelp_exitCommand_returnsFalse() throws Exception {
        // CommandResult from ExitCommand
        assertFalse(new ExitCommand().execute(model).isShowHelp());
    }

    @Test
    public void isShowHelp_helpCommand_returnsTrue() throws Exception {
        // CommandResult from HelpCommand
        assertTrue(new HelpCommand().execute(model).isShowHelp());
    }

    @Test
    public void isExit_addCommand_returnsFalse() throws Exception {
        // CommandResult from AddCommand
        assertFalse(new AddCommandParser().parse(" n/Tom p/123 e/a@a.com a/Road").execute(model).isExit());
    }

    @Test
    public void isExit_addEventCommand_returnsFalse() throws Exception {
        // CommandResult from AddEventCommand
        assertFalse(new AddEventCommandParser().parse(" n/Marathon SG d/2022-10-10").execute(model).isExit());
    }

    @Test
    public void isExit_addParticipantToEventCommand_returnsFalse() throws Exception {
        // CommandResult from AddParticipantToEvent
        AddCommand addCommand = new AddCommandParser().parse(" n/Tom p/123 e/a@a.com a/Road");
        addCommand.execute(model);
        AddEventCommand addEventCommand = new AddEventCommandParser().parse(" n/M d/2021-11-11");
        addEventCommand.execute(model);
        assertFalse(new AddParticipantToEventParser().parse(" 1 1").execute(model).isExit());
    }

    @Test
    public void isExit_deleteCommand_returnsFalse() throws Exception {
        // CommandResult from DeleteCommand
        AddCommand addCommand = new AddCommandParser().parse(" n/Tom p/123 e/a@a.com a/Road");
        addCommand.execute(model);
        assertFalse(new DeleteCommandParser().parse(" 1").execute(model).isExit());
    }

    @Test
    public void isExit_doneEventCommand_returnsFalse() throws Exception {
        // CommandResult from DoneCommand
        AddEventCommand addEventCommand = new AddEventCommandParser().parse(" n/M d/2021-11-11");
        addEventCommand.execute(model);
        assertFalse(new DoneCommandParser().parse(" 1").execute(model).isExit());
    }

    @Test
    public void isExit_editCommand_returnsFalse() throws Exception {
        // CommandResult from EditCommand
        AddCommand addCommand = new AddCommandParser().parse(" n/Tom p/123 e/a@a.com a/Road");
        addCommand.execute(model);
        assertFalse(new EditCommandParser().parse(" 1 n/Kenny").execute(model).isExit());
    }

    @Test
    public void isExit_filterEventCommand_returnsFalse() throws Exception {
        // CommandResult from FilterEventCommand
        assertFalse(new FilterEventCommandParser().parse(" d/2021-11-1").execute(model).isExit());
    }

    @Test
    public void isExit_findCommand_returnsFalse() throws Exception {
        // CommandResult from FindCommand
        assertFalse(new FindCommandParser().parse(" tom").execute(model).isExit());
    }

    @Test
    public void isExit_listCommand_returnsFalse() throws Exception {
        // CommandResult from ListCommand
        assertFalse(new ListCommand().execute(model).isExit());
    }

    @Test
    public void isExit_listEventCommand_returnsFalse() throws Exception {
        // CommandResult from ListEventCommand
        assertFalse(new ListEventCommand().execute(model).isExit());
    }

    @Test
    public void isExit_removeEventCommand_returnsFalse() throws Exception {
        // CommandResult from RemoveEventCommand
        AddEventCommand addEventCommand = new AddEventCommandParser().parse(" n/M d/2021-11-11");
        addEventCommand.execute(model);
        assertFalse(new DeleteEventCommandParser().parse(" 1").execute(model).isExit());
    }

    @Test
    public void isExit_removeParticipantFromEventCommand_returnsFalse() throws Exception {
        // CommandResult from RemoveParticipantFromEventCommand
        AddCommand addCommand = new AddCommandParser().parse(" n/Tom p/123 e/a@a.com a/Road");
        addCommand.execute(model);
        AddEventCommand addEventCommand = new AddEventCommandParser().parse(" n/M d/2021-11-11");
        addEventCommand.execute(model);

        AddParticipantToEventCommand addParticipantToEventCommand = new AddParticipantToEventParser()
                .parse(" 1 1");
        addParticipantToEventCommand.execute(model);
        assertFalse(new RemoveParticipantFromEventParser().parse(" 1 1").execute(model).isExit());
    }

    @Test
    public void isExit_showEventDetailsCommand_returnsFalse() throws Exception {
        // CommandResult from ShowEventDetailsCommand
        AddEventCommand addEventCommand = new AddEventCommandParser().parse(" n/M d/2021-11-11");
        addEventCommand.execute(model);
        assertFalse(new ShowEventDetailsCommandParser().parse(" 1").execute(model).isExit());
    }

    @Test
    public void isExit_showEventParticipantsCommand_returnsFalse() throws Exception {
        // CommandResult from ShowEventParticipantsCommand
        AddEventCommand addEventCommand = new AddEventCommandParser().parse(" n/M d/2021-11-11");
        addEventCommand.execute(model);
        assertFalse(new ShowEventParticipantsCommandParser().parse(" 1").execute(model).isExit());
    }

    @Test
    public void isExit_sortEventCommand_returnsFalse() throws Exception {
        // CommandResult from SortEventCommand
        assertFalse(new SortEventCommand().execute(model).isExit());
    }

    @Test
    public void isExit_clearCommand_returnsFalse() throws Exception {
        // CommandResult from ClearCommand
        assertFalse(new ClearCommand().execute(model).isExit());
    }

    @Test
    public void isExit_helpCommand_returnsFalse() throws Exception {
        // CommandResult from HelpCommand
        assertFalse(new HelpCommand().execute(model).isExit());
    }

    @Test
    public void isExit_exitCommand_returnsTrue() throws Exception {
        // CommandResult from ExitCommand
        assertTrue(new ExitCommand().execute(model).isExit());
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());
    }
}
