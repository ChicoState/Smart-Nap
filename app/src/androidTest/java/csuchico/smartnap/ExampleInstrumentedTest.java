package csuchico.smartnap;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("csuchico.smartnap", appContext.getPackageName());
    }

    @Test
    public void listOfFlashCardIDShouldNotReturnNull() throws Exception {
        FlashCard flashCard;
        String cardClassName, cardQuestion, cardAnswer;
        ArrayList<Long> listOfFlashCardId = new ArrayList<>(); // explicit type Long not necessary

        for ( int i = 0; i < 5; i++ ) {
            cardClassName = "CSCI430 - " + i + "";
            cardQuestion = "FlashCard #" + i + "Will SmartNap wake me up when I need it to?";
            cardAnswer = "FlashCard #" + i + "Yes!";
            flashCard = new FlashCard(cardClassName,cardQuestion,cardAnswer);
            flashCard.save(); // save to DB
            long cardId = flashCard.getId();
            listOfFlashCardId.add(cardId);
        }

        String alarmName =  "Test Alarm";
        long alarmTime = 292838232;

        AlarmClock alarm = new AlarmClock(alarmTime,alarmName,listOfFlashCardId);
        alarm.save();
        long alarmId = alarm.getId();
        alarm = AlarmClock.findById(AlarmClock.class,alarmId);
        assertNotEquals(null,alarm); // ensure that the alarm isn't null
        assertNotEquals(null,listOfFlashCardId); // this passes!
        listOfFlashCardId = alarm.getListOfCardIDs();
        assertNotEquals(null,listOfFlashCardId); // this fails!!!
        assertNotEquals(0,listOfFlashCardId.size());
    }
}
