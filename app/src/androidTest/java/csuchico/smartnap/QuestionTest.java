package csuchico.smartnap;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 *Created by gerald on 12/9/2017.
 */
public class QuestionTest {
    @Rule
    public final ActivityTestRule<Question> rule =
            new ActivityTestRule<>(Question.class, true, false);
    @Test
    public void addQuestion() throws Exception {
        onView(withId(R.id.createquestion)).perform(click());
        Intents.init();
        intended(hasComponent(AlarmQuestions.class.getName()));
        Intents.release();
    }

}