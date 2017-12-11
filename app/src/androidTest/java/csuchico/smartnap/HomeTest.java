package csuchico.smartnap;

import android.support.test.espresso.intent.Intents;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 *Created by gerald on 12/10/2017.
 */
public class HomeTest {
    @Test
    public void alarmSetup() throws Exception {
        onView(withId(R.id.NewAlarm)).perform(click());
        Intents.init();
        intended(hasComponent(AlarmEdit.class.getName()));
        Intents.release();
    }

    @Test
    public void gotoQuestion() throws Exception {
        onView(withId(R.id.AddFlashCard)).perform(click());
        Intents.init();
        intended(hasComponent(Question.class.getName()));
        Intents.release();
    }
}