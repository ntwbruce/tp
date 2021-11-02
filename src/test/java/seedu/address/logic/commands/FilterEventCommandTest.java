package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventDateTimePredicate;

/**
 * Contains integration tests (interaction with the Model) for FilterEventCommand.
 */
public class FilterEventCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        EventDateTimePredicate firstPredicate =
                new EventDateTimePredicate(Collections.singletonList("2021-09-18"));
        EventDateTimePredicate secondPredicate =
                new EventDateTimePredicate(Arrays.asList("2021-09-18", "1000"));

        FilterEventCommand filterFirstCommand = new FilterEventCommand(firstPredicate);
        FilterEventCommand filterSecondCommand = new FilterEventCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterEventCommand filterFirstCopy = new FilterEventCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different object -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_onlyDateInput_multipleEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 4);
        EventDateTimePredicate predicate =
                new EventDateTimePredicate(Collections.singletonList("2021-09-18"));
        FilterEventCommand command = new FilterEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredEventList(), expectedModel.getFilteredEventList());
    }

    @Test
    public void execute_dateAndTimeInput_oneEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        EventDateTimePredicate predicate =
                new EventDateTimePredicate(Arrays.asList("2021-09-18", "1001"));
        FilterEventCommand command = new FilterEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredEventList(), expectedModel.getFilteredEventList());
    }

    @Test
    public void execute_onlyDateInput_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        EventDateTimePredicate predicate =
                new EventDateTimePredicate(Collections.singletonList("1-9-18"));
        FilterEventCommand command = new FilterEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredEventList(), expectedModel.getFilteredEventList());
    }

    @Test
    public void execute_dateAndTimeInput_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        EventDateTimePredicate predicate =
                new EventDateTimePredicate(Arrays.asList("2021-09-18", "1020"));
        FilterEventCommand command = new FilterEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredEventList(), expectedModel.getFilteredEventList());
    }
}
