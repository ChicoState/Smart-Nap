package csuchico.smartnap;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 *Created by gerald on 12/9/2017.
 */
public class AlarmQuestionsTest {
    @Test
    public void savequestionsuccess() throws Exception {
        onView(withId(R.id.fc_question)).perform(typeText("question"));
        onView(withId(R.id.fc_answer)).perform(typeText("answer"));
        onView(withId(R.id.classname)).perform(typeText("class name"));
        onView(withId(R.id.save_fc)).perform(click());
        onView(withText(R.string.successsave)).inRoot(new ToastMatcher()).check(matches(withText("FlashCard has been made!")));
    }

    @Test
    public void savequestionfail1() throws Exception {
        onView(withId(R.id.fc_question)).perform(typeText("question"));
        onView(withId(R.id.fc_answer)).perform(typeText("answer"));
        onView(withId(R.id.save_fc)).perform(click());
        onView(withText(R.string.failsave)).inRoot(new ToastMatcher()).check(matches(withText("ERROR: Can't save FlashCard!")));
    }

    @Test
    public void savequestionfail2() throws Exception {
        onView(withId(R.id.fc_question)).perform(typeText("question"));
        onView(withId(R.id.classname)).perform(typeText("class name"));
        onView(withId(R.id.save_fc)).perform(click());
        onView(withText(R.string.failsave)).inRoot(new ToastMatcher()).check(matches(withText("ERROR: Can't save FlashCard!")));
    }

    @Test
    public void savequestionfail3() throws Exception {
        onView(withId(R.id.classname)).perform(typeText("class name"));
        onView(withId(R.id.fc_answer)).perform(typeText("answer"));
        onView(withId(R.id.save_fc)).perform(click());
        onView(withText(R.string.failsave)).inRoot(new ToastMatcher()).check(matches(withText("ERROR: Can't save FlashCard!")));
    }

    @Test
    public void deletequestion() throws Exception {
    }
}