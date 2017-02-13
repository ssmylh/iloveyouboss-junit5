package iloveyouboss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ProfilePoolTest {
    ProfilePool pool;
    Profile langrsoft;
    Profile smeltInc;
    BooleanQuestion doTheyReimburseTuition;

    @BeforeEach
    void create() {
        pool = new ProfilePool();
        langrsoft = new Profile("Langrsoft");
        smeltInc = new Profile("Smelt Inc.");
        doTheyReimburseTuition = new BooleanQuestion(1, "Reimburses tuition?");
    }

    Criteria soleNeed(Question question, int value, Weight weight) {
        Criteria criteria = new Criteria();
        criteria.add(new Criterion(new Answer(question, value), weight));
        return criteria;
    }

    @Test
    void answersResultsInScoredOrder() {
        smeltInc.add(new Answer(doTheyReimburseTuition, Bool.FALSE));
        pool.add(smeltInc);
        langrsoft.add(new Answer(doTheyReimburseTuition, Bool.TRUE));
        pool.add(langrsoft);

        List<Profile> ranked = pool.ranked(soleNeed(doTheyReimburseTuition, Bool.TRUE, Weight.Important));

        assertThat(ranked.toArray()).containsExactly(new Profile[]{langrsoft, smeltInc});
    }
}
