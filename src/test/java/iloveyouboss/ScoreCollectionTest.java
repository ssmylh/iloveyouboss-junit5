package iloveyouboss;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ScoreCollectionTest {

    @Test
    void answersArithmeticMeanOfTwoNumbers() {
        ScoreCollection collection = new ScoreCollection();
        collection.add(() -> 5);
        collection.add(() -> 7);

        assertThat(collection.arithmeticMean()).isEqualTo(6);
    }
}
