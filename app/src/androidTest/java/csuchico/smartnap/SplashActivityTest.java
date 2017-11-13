package csuchico.smartnap;

import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.junit.Assert.*;

/**
 * Created by Justin on 11/13/2017.
 */

@RunWith(AndroidJUnit4.class)

public class SplashActivityTest {
    @Rule
    public final IntentsTestRule<SplashActivity> rule = new IntentsTestRule<SplashActivity>(SplashActivity.class);
    //Test that checks if splash page launched
    @Test
    public void testIfLaunched() throws Exception{
        Context appContext = InstrumentationRegistry.getTargetContext();
        intended(hasComponent(new ComponentName(rule.getActivity(), Home.class)));
    }
}