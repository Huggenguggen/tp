package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.task.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.task.logic.commands.FindCommand;
import seedu.task.model.task.Name;
import seedu.task.model.task.NameContainsAllKeywordsPredicate;
import seedu.task.model.task.NameContainsKeywordsPredicate;
import seedu.task.model.task.TagsContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate("Alice Bob"));
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);
        List<String> names = new ArrayList<>();
        names.add("Bob");
        names.add("Alice");
        FindCommand expectedFindCommand3 =
            new FindCommand(new NameContainsAllKeywordsPredicate(names));
        assertParseSuccess(parser, " all/hi n/Alice n/Bob", expectedFindCommand3);
    }

    @Test
    public void parse_inValidArgs_throwsParseException() {
        assertParseFailure(parser, " n/Alice d/test",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " n/", Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleTags_returnsFindCommand() {
        String[] tags = {"friend", "family"};
        FindCommand expectedFindCommand =
                new FindCommand(new TagsContainsKeywordsPredicate(Arrays.asList(tags)));
        assertParseSuccess(parser, " t/friend t/family", expectedFindCommand);
    }

    @Test
    public void parse_multipleNames_returnsFindCommand() {
        String[] names = {"study", "eat"};
        FindCommand expectedFindCommand2 =
            new FindCommand(new NameContainsAllKeywordsPredicate(Arrays.asList(names)));
        assertParseSuccess(parser, " all/ n/study n/eat ", expectedFindCommand2);
    }

}
