package scratch;

import org.assertj.core.api.Condition;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class AssertTest {

    @Test
    void assertTrue() {
        assertThat(true).isTrue();
    }

    @Test
    void equalTo() {
        assertThat(1 + 1).isEqualTo(2);
    }

    @Test
    void notEqualTo() {
        assertThat(1 + 1).isNotEqualTo(1);
    }

    @Test
    void nullValue() {
        String str = null;
        assertThat(str).isNull();
    }

    @Test
    void notNullValue() {
        String str = "string";
        assertThat(str).isNotNull();
    }

    @Test
    void doubleEquals() {
        assertThat(2.32 * 3).isCloseTo(6.96, offset(0.005));
    }

    @Test
    void exceptionOfType() {
        assertThatExceptionOfType(IOException.class)
                .isThrownBy(() -> {
                    throw new IOException("test");
                })
                .withMessage("test");
    }

    @Test
    void exceptionWhenAndThen() {
        Throwable thrown = catchThrowable(() -> {
            throw new IOException("test");
        });

        assertThat(thrown).isInstanceOf(IOException.class)
                .hasMessage("test");
    }

    @Test
    void anyOfCondition() {
        String str = "Hello World";

        Condition<String> startsWith = new Condition<>((String s) -> s.startsWith("H"), "starts with 'H'");
        Condition<String> endsWith = new Condition<>((String s) -> s.endsWith("d"), "ends with 'd'");

        assertThat(str).is(anyOf(startsWith, endsWith));
    }

    @Test
    void containsAtIndex() {
        List<String> list = new ArrayList<>();
        list.add("a1");
        list.add("b1");
        list.add("a2");

        Condition<String> condition = new Condition<>((String str) -> str.equals("a1"), "equals 'a1'");

        assertThat(list).has(condition, Index.atIndex(0));
    }

    @Test
    void contains() {
        List<String> list = new ArrayList<>();
        list.add("a1");
        list.add("b1");
        list.add("a2");

        assertThat(list).contains("b1", "a1");
    }

    @Test
    void allMatch() {
        List<String> list = new ArrayList<>();
        list.add("a1");
        list.add("b1");
        list.add("a2");

        assertThat(list).allMatch((String str) -> {
            Integer.parseInt(str.substring(str.length() - 1), 10);
            return true;
        });
    }

    @Test
    void areAtLeastOneCondition() {
        List<String> list = new ArrayList<>();
        list.add("a1");
        list.add("b1");
        list.add("a2");

        Condition<String> condition = new Condition<>((String str) -> str.startsWith("a"), "starts with 'a'");
        assertThat(list).areAtLeastOne(condition);
    }
}