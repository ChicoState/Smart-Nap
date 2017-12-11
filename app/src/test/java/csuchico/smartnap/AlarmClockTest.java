package csuchico.smartnap;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
/**
 * Created by Justin on 12/5/2017.
 */

public class AlarmClockTest {
    @Test
    public void getName1() throws Exception {
        AlarmClock test = new AlarmClock(1000, "alarmTest");
        assertEquals("alarmTest", test.getName());
    }

    @Test
    public void getName2() throws Exception {
        AlarmClock test = new AlarmClock(20, "asdasdasdasdasdadsasddasdasdadsa");
        assertEquals("asdasdasdasdasdadsasddasdasdadsa", test.getName());
    }

    @Test
    public void getName3() throws Exception {
        AlarmClock test = new AlarmClock(5000, "12345");
        assertEquals("12345", test.getName());
    }

    @Test
    public void setName1() throws Exception {
        AlarmClock test = new AlarmClock(1, "Dummy");
        test.setName("Changed");
        assertEquals("Changed", test.getName());
    }

    @Test
    public void setName2() throws Exception{
        AlarmClock test = new AlarmClock(2, "ChangeMe");
        test.setName("Changed to This");
        assertEquals("Changed to This", test.getName());
    }

    @Test
    public void setName3() throws Exception {
        AlarmClock test = new AlarmClock(3,"ThisisTestnummmmmmmmmmmmmmmmber3");
        test.setName("ChangetoThis");
        assertEquals("ChangetoThis", test.getName());
    }

    @Test
    public void getTime1() throws Exception {
        AlarmClock test = new AlarmClock(123123123,"test1");
        assertEquals(123123123, test.getTime());
    }

    @Test
    public void getTime2() throws Exception {
        AlarmClock test = new AlarmClock(00000000, "test2");
        assertEquals(00000000, test.getTime());
    }

    @Test
    public void getTime3() throws Exception {
        AlarmClock test = new AlarmClock(120,"test3");
        assertEquals(120, test.getTime());
    }

    @Test
    public void setTime1() throws Exception {
        AlarmClock test = new AlarmClock(4000, "test1");
        test.setTime(123);
        assertEquals(123,test.getTime());
    }

    @Test
    public void setTime2() throws Exception {
        AlarmClock test = new AlarmClock(30000,"test2");
        test.setTime(23332);
        assertEquals(23332,test.getTime());
    }

    @Test
    public void setTime3() throws Exception {
        AlarmClock test = new AlarmClock(12953,"test3");
        test.setTime(12953);
        assertEquals(12953,test.getTime());
    }

    @Test
    public void getTimeFormattedTest1() throws Exception {
        long time = 1512976620;
        AlarmClock alarm = new AlarmClock(time,"test alarm");
        String timeFormatted = alarm.getTimeFormatted("h:mm a");
        assertEquals("4:16 AM",timeFormatted);
    }

    @Test
    public void getTimeFormattedTest2() throws Exception {
        long time = 1512934;
        AlarmClock alarm = new AlarmClock(time,"test alarm");
        String timeFormatted = alarm.getTimeFormatted("h:mm a");
        assertEquals("4:25 PM",timeFormatted);
    }


}
