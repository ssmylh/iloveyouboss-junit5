package tdd;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class AnswerTest {
    @Test
    void matchAgainstNullAnswerReturnsFalse() {
        Answer answer = new Answer(new BooleanQuestion(0, ""), Bool.TRUE);
        assertThat(answer.match(null)).isFalse();
    }
}