package tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ProfileTest {
    Profile profile;
    BooleanQuestion questionIsThereRelocation;
    Answer answerThereIsRelocation;

    @BeforeEach
    void createProfile() {
        this.profile = new Profile();
    }

    @BeforeEach
    void createQuestion() {
        questionIsThereRelocation = new BooleanQuestion(1, "転居時のサポートはありますか?");
        answerThereIsRelocation = new Answer(questionIsThereRelocation, Bool.TRUE);
    }

    @Test
    void matchesNothingWhenProfileEmpty() {
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.DontCare);

        boolean result = profile.matches(criterion);

        assertThat(result).isFalse();
    }

    @Test
    public void matchesWhenProfileContainsMatchingAnswer() {
        profile.add(answerThereIsRelocation);
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);

        boolean result = profile.matches(criterion);

        assertThat(result).isTrue();
    }

}
