package scratch;

import org.assertj.core.api.Assertions;

public class RectangleAssert extends Assertions {
    public static RectangleSidesAssert assertThat(Rectangle actual) {
        return new RectangleSidesAssert(actual);
    }
}
