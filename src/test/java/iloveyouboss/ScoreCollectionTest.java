package iloveyouboss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ScoreCollectionTest {
    ScoreCollection collection;

    @BeforeEach
    void create() {
        collection = new ScoreCollection();
    }


    @Test
    void answersArithmeticMeanOfTwoNumbers() {
        collection.add(() -> 5);
        collection.add(() -> 7);

        assertThat(collection.arithmeticMean()).isEqualTo(6);
    }

    @Test
    void throwsExceptionWhenAddingNull() {
        Throwable iae = catchThrowable(() -> {
            collection.add(null);
        });

        assertThat(iae).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void answersZeroWhenNoElementsAdded() {
        assertThat(collection.arithmeticMean()).isEqualTo(0);
    }

    @Test
    void dealsWithIntegerOverflow() {
        collection.add(() -> Integer.MAX_VALUE);
        collection.add(() -> 1);

        assertThat(collection.arithmeticMean()).isEqualTo(1073741824);
    }
}
