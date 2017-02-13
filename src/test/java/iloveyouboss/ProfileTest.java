package iloveyouboss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.time.Duration.*;
import static org.assertj.core.api.Assertions.*;

public class ProfileTest {
    Profile profile;
    BooleanQuestion question;
    Criteria criteria;

    @BeforeEach
    void create() {
        profile = new Profile("Bull Hockey, Inc.");
        question = new BooleanQuestion(1, "Got bonuses?");
        criteria = new Criteria();
    }

    int[] ids(List<Answer> answers) {
        return answers.stream()
                .mapToInt(a -> a.getQuestion().getId()).toArray();
    }

    @Test
    void findsAnswersBasedOnPredicate() {
        profile.add(new Answer(new BooleanQuestion(1, "1"), Bool.FALSE));
        profile.add(new Answer(new PercentileQuestion(2, "2", new String[]{}), 0));
        profile.add(new Answer(new PercentileQuestion(3, "3", new String[]{}), 0));

        List<Answer> answers = profile.find(a -> a.getQuestion().getClass() == PercentileQuestion.class);
        assertThat(ids(answers)).containsExactly(2, 3);

        List<Answer> answersComplement = profile.find(a -> a.getQuestion().getClass() != PercentileQuestion.class);
        List<Answer> allAnswers = new ArrayList<>();
        allAnswers.addAll(answersComplement);
        allAnswers.addAll(answers);
        assertThat(ids(allAnswers)).containsExactly(1, 2, 3);
    }

    @Test
    void findAnswers() {
        int dataSize = 5000;
        for (int i = 0; i < dataSize; i++) {
            profile.add(new Answer(new BooleanQuestion(i, String.valueOf(i)), Bool.FALSE));
        }
        profile.add(new Answer(new PercentileQuestion(dataSize, String.valueOf(dataSize), new String[]{}), 0));

        org.junit.jupiter.api.Assertions.assertTimeout(ofMillis(1000), () -> {
            int numberOfTimes = 1000;
            for (int i = 0; i < numberOfTimes; i++) {
                profile.find(a -> a.getQuestion().getClass() == PercentileQuestion.class);
            }
        });
    }
}
