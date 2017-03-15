package util;

import org.assertj.core.api.AbstractAssert;

public class MatchesAssert extends AbstractAssert<MatchesAssert, Match[]> {
    public MatchesAssert(Match[] actual) {
        super(actual, MatchesAssert.class);
    }

    public MatchesAssert containsMatches(Match[] expected) {
        isNotNull();

        if (actual.length != expected.length) {
            failWithMessage("The size of matches must be <%d>, but was <%d>", expected.length, actual.length);
        }

        for (int i = 0; i < expected.length; i++) {
            if (!equals(expected[i], actual[i])) {
                failWithMessage("At index <%d>, expected Match to be <%s>, but was <%s>", i, expected[i].toString(), actual[i].toString());
            }
        }
        return this;
    }

    private boolean equals(Match expected, Match actual) {
        return expected.searchString.equals(actual.searchString)
                && expected.surroundingContext.equals(actual.surroundingContext);
    }

}
