package scratch;

import org.assertj.core.api.AbstractAssert;

public class RectangleSidesAssert extends AbstractAssert<RectangleSidesAssert, Rectangle> {

    public RectangleSidesAssert(Rectangle actual) {
        super(actual, RectangleSidesAssert.class);
    }

    public RectangleSidesAssert constrainsSidesTo(int length) {
        isNotNull();

        double width = Math.abs(actual.origin().x - actual.opposite().x);
        double height = Math.abs(actual.origin().y - actual.opposite().y);

        if (width > length || height > length) {
            failWithMessage("Both sids must be <= <%d> but was width = <%d> and height = <%d>", length, (int)width, (int)height);
        }

        return this;
    }
}
