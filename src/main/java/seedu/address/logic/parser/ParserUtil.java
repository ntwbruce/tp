package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.participant.Address;
import seedu.address.model.participant.BirthDate;
import seedu.address.model.participant.Email;
import seedu.address.model.participant.Name;
import seedu.address.model.participant.NextOfKin;
import seedu.address.model.participant.ParticipantId;
import seedu.address.model.participant.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code String birthDate} into a {@code BirthDate}.
     */
    public static BirthDate parseBirthDate(String birthDate) throws ParseException {
        requireNonNull(birthDate);
        String trimmedBirthDate = birthDate.trim();
        if (trimmedBirthDate.equals("N/A")) {
            return BirthDate.notSpecified();
        } else if (!BirthDate.checkDateComponents(trimmedBirthDate)) {
            throw new ParseException(BirthDate.MESSAGE_DATE_FORMAT_ERROR);
        } else if (!BirthDate.isValidDate(trimmedBirthDate)) {
            throw new ParseException(BirthDate.MESSAGE_DATE_DOES_NOT_EXIST);
        } else if (!BirthDate.isValidBirthDate(trimmedBirthDate)) {
            throw new ParseException(BirthDate.MESSAGE_DATE_CONSTRAINTS);
        }
        return BirthDate.of(trimmedBirthDate);
    }

    /**
     * Parses {@code String nextOfKin} into a {@code NextOfKin}.
     */
    public static NextOfKin parseNextOfKin(String nextOfKin) throws ParseException {
        requireNonNull(nextOfKin);
        return new NextOfKin(new Name("Test"), new Phone("12345678"), new Tag("Test"));
    }

    /**
     * Parses {@code Collection<String> nextOfKins} into a {@code ArrayList<NextOfKin>}.
     */
    public static ArrayList<NextOfKin> parseNextOfKins(Collection<String> noks) throws ParseException {
        requireNonNull(noks);
        final ArrayList<NextOfKin> nextOfKins = new ArrayList<>();
        for (String nextOfKin : noks) {
            nextOfKins.add(parseNextOfKin(nextOfKin));
        }
        return nextOfKins;
    }

    /**
     * Parses {@code String eventName} into a {@code EventName}.
     */
    public static EventName parseEventName(String eventName) throws ParseException {
        requireNonNull(eventName);
        String trimmedEventName = eventName.trim();
        if (!EventName.isValidEventName(trimmedEventName)) {
            throw new ParseException(EventName.MESSAGE_CONSTRAINTS);
        }
        return new EventName(trimmedEventName);
    }

    /**
     * Parses {@code String eventDate} into a {@code EventDate}.
     */
    public static EventDate parseEventDate(String eventDate) throws ParseException {
        requireNonNull(eventDate);
        String trimmedEventDate = eventDate.trim();
        if (!EventDate.checkDateComponents(trimmedEventDate)) {
            throw new ParseException(EventDate.MESSAGE_DATE_FORMAT_ERROR);
        } else if (!EventDate.isValidDate(trimmedEventDate)) {
            throw new ParseException(EventDate.MESSAGE_CONSTRAINTS);
        }
        return new EventDate(trimmedEventDate);
    }

    /**

     * Parses {@code String eventTime} into a {@code EventTime}.
     */
    public static EventTime parseEventTime(String eventTime) throws ParseException {
        requireNonNull(eventTime);
        if (eventTime.equals("")) {
            return new EventTime();
        }
        String trimmedEventTime = eventTime.trim();
        if (!EventTime.isValidTime(trimmedEventTime)) {
            throw new ParseException(EventTime.MESSAGE_CONSTRAINTS);
        }
        return new EventTime(trimmedEventTime);
    }

    /**
     *  Parses {@code String id} into a {@code ParticipantId object}.
     */
    public static ParticipantId parseParticipantId(String id) throws ParseException {
        requireNonNull(id);
        String trimmedId = id.trim();
        if (!ParticipantId.isValidId(trimmedId)) {
            throw new ParseException(ParticipantId.MESSAGE_CONSTRAINTS);
        }
        return new ParticipantId(id);
    }
}
