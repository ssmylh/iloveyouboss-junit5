/***
 * Excerpted from "Pragmatic Unit Testing in Java with JUnit",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/utj2 for more book information.
 ***/
package util;


// text courtesy of Herman Melville (Moby Dick) from
// http://www.gutenberg.org/cache/epub/2701/pg2701.txt

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;

import static util.SearchTestAssertions.*;


public class SearchTest {
    private static final String A_TITLE = "1";
    private InputStream stream;

    @BeforeEach
    public void turnOffLogging() {
        Search.LOGGER.setLevel(Level.OFF);
    }

    @AfterEach
    public void closeResources() throws Exception {
        stream.close();
    }

    @Disabled
    @Test
    public void returnsMatchesShowingContextWhenSearchStringInContent() throws Exception {
        stream = streamOn("rest of text here"
                + "1234567890search term1234567890" + "more rest of text");
        Search search = new Search(stream, "search term", A_TITLE);

        search.setSurroundingCharacterCount(10);

        search.execute();

        List<Match> matches = search.getMatches();
        assertThat(matches.toArray(new Match[matches.size()])).containsMatches(new Match[]{
                new Match(A_TITLE, "search term", "1234567890search term1234567890")
        });
    }

    @Disabled
    @Test
    public void noMatchesReturnedWhenSearchStringNotInContent() throws Exception {
        stream = streamOn("any text");
        Search search = new Search(stream, "text that doesn't match", A_TITLE);

        search.execute();

        assertThat(search.getMatches()).isEmpty();
    }

    private InputStream streamOn(String pageContent) {
        return new ByteArrayInputStream(pageContent.getBytes());
    }
}

class SearchTestAssertions extends Assertions {
    public static MatchesAssert assertThat(Match[] actual) {
        return new MatchesAssert(actual);
    }
}