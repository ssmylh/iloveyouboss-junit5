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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Level;

import static util.SearchTestAssertions.*;


public class SearchTest {
    private static final String A_TITLE = "1";

    @Disabled
    @Test
    public void returnsMatchesShowingContextWhenSearchStringInContent() throws Exception {
        InputStream stream = streamOn("There are certain queer times and occasions "
                + "in this strange mixed affair we call life when a man "
                + "takes this whole universe for a vast practical joke, "
                + "though the wit thereof he but dimly discerns, and more "
                + "than suspects that the joke is at nobody's expense but "
                + "his own.");
        Search search = new Search(stream, "practical joke", A_TITLE);
        Search.LOGGER.setLevel(Level.OFF);
        search.setSurroundingCharacterCount(10);
        search.execute();
        assertThat(search.errored()).isFalse();
        List<Match> matches = search.getMatches();
        assertThat(matches.toArray(new Match[matches.size()])).containsMatches(new Match[]{
                new Match(A_TITLE, "practical joke", "or a vast practical joke, though t")
        });
        stream.close();
    }

    @Disabled
    @Test
    public void noMatchesReturnedWhenSearchStringNotInContent() throws Exception {
        URLConnection connection =
                new URL("http://bit.ly/15sYPA7").openConnection();
        InputStream stream = connection.getInputStream();
        Search search = new Search(stream, "smelt", A_TITLE);
        search.execute();
        assertThat(search.getMatches()).isEmpty();
        stream.close();
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