package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;

public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    public final Event event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label isDone;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label numberOfParticipants;

    /**
     * Creates a {@code EventCard} with the given {@code Event} and index to display.
     */
    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        name.setText(event.getNameString());
        isDone.setText(event.getDoneValue() ? Event.COMPLETED : Event.UNCOMPLETED);
        date.setText(event.getDateString());
        time.setText(event.getTimeDisplayString());
        int noOfParticipants = event.getParticipants().size();
        if (noOfParticipants == 1) {
            numberOfParticipants.setText(String.format("%d participant", noOfParticipants));
        } else {
            numberOfParticipants.setText(String.format("%d participants", noOfParticipants));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        EventCard card = (EventCard) other;
        return id.getText().equals(card.id.getText())
                && event.equals(card.event);
    }
}
