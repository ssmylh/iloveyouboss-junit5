package tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ProfileTest {
    Profile profile;
    BooleanQuestion questionIsThereRelocation;
    Answer answerThereIsRelocation;
    Answer answerThereIsNotRelocation;
    BooleanQuestion questionReimbursesTuition;
    Answer answerReimbursesTuition;
    Answer answerDoesNotReimburseTuition;

    @BeforeEach
    void createProfile() {
        this.profile = new Profile();
    }

    @BeforeEach
    void createQuestion() {
        questionIsThereRelocation = new BooleanQuestion(1, "転居時のサポートはありますか?");
        answerThereIsRelocation = new Answer(questionIsThereRelocation, Bool.TRUE);
        answerThereIsNotRelocation = new Answer(questionIsThereRelocation, Bool.FALSE);
        questionReimbursesTuition = new BooleanQuestion(1, "奨学金制度はありますか?");
        answerReimbursesTuition = new Answer(questionReimbursesTuition, Bool.TRUE);
        answerDoesNotReimburseTuition = new Answer(questionReimbursesTuition, Bool.FALSE);
    }

    @Nested
    class Profile_MatchesCriterionTest {
        @Test
        void trueWhenMatchesSoleAnswer() {
            profile.add(answerThereIsRelocation);
            Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);

            assertThat(profile.matches(criterion)).isTrue();
        }

        @Test
        void falseWhenNoMatchingAnswerContained() {
            profile.add(answerThereIsNotRelocation);
            Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);

            assertThat(profile.matches(criterion)).isFalse();
        }

        @Test
        void trueWhenOneOfMultipleAnswerMatches() {
            profile.add(answerThereIsRelocation);
            profile.add(answerDoesNotReimburseTuition);
            Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);

            assertThat(profile.matches(criterion)).isTrue();
        }

        @Test
        void trueForAnyDontCareCriterion() {
            profile.add(answerDoesNotReimburseTuition);
            Criterion criterion = new Criterion(answerReimbursesTuition, Weight.DontCare);

            assertThat(profile.matches(criterion)).isTrue();
        }
    }

    @Nested
    class Profile_MatchesCriteriaTest {
        Criteria criteria;

        @BeforeEach
        void createCriteria() {
            criteria = new Criteria();
        }

        @Test
        void falseWhenNoneOfMultipleCriteriaMatch() {
            profile.add(answerDoesNotReimburseTuition);
            criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
            criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));

            assertThat(profile.matches(criteria)).isFalse();
        }

        @Test
        void trueWhenAnyOfMultipleCriteriaMatch() {
            profile.add(answerThereIsRelocation);
            criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
            criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));

            assertThat(profile.matches(criteria)).isTrue();
        }

        @Test
        void falseWhenAnyMustMeetCriteriaNotMet() {
            profile.add(answerThereIsRelocation);
            profile.add(answerDoesNotReimburseTuition);
            criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
            criteria.add(new Criterion(answerReimbursesTuition, Weight.MustMatch));

            assertThat(profile.matches(criteria)).isFalse();
        }
    }
}
