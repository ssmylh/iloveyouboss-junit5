package scratch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static scratch.RectangleAssert.*;

public class RectangleTest {
    private Rectangle rectangle;

    @AfterEach
    public void ensureInvariant() {
        assertThat(rectangle).constrainsSidesTo(100);
    }

    @Test
    public void answersArea() {
        rectangle = new Rectangle(new Point(5, 5), new Point(15, 10));
        assertThat(rectangle.area()).isEqualTo(50);
    }

    @Disabled
    @Test
    public void allowsDynamicallyChangingSize() {
        rectangle = new Rectangle(new Point(5, 5));
        rectangle.setOppositeCorner(new Point(130, 130));
        assertThat(rectangle.area()).isEqualTo(15625);
    }
}
