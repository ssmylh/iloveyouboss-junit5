package tdd;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ProfileTest {
    @Test
    void matchesNothingWhenProfileEmpty() {
        Profile profile = new Profile();
        Question question = new BooleanQuestion(1, "転居時のサポートはありますか?");
        Criterion criterion = new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare);

        boolean result = profile.matches(criterion);

        assertThat(result).isFalse();
    }
}
