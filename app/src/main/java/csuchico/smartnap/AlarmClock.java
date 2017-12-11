package csuchico.smartnap;

import android.support.annotation.Nullable;
import android.util.Log;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AlarmClock extends SugarRecord<AlarmClock> {

  private long time;
  private String name;

  @Ignore
  private static final String DEFAULT_TIME_FORMAT = "h:mm a";

  // Note: Please retain default constructor
  public AlarmClock() {
  }

  // Constructor
  public AlarmClock(long time, String name) {
    this.time = time;
    this.name = name;
    //this.link = null;
  }

  public List<AlarmClockFlashCardLinker> getCards() {
    return AlarmClockFlashCardLinker.find(
            AlarmClockFlashCardLinker.class,
            "alarm = ?",
            Long.toString(this.getId())
    );
  }

  public void updateAlarm(long time, String name) {
    this.time = time;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public long getTime() {
    return time;
  }

  public String getTimeFormatted() {

    /*
    If you are formatting multiple dates, it is more efficient to get the format and use
    it multiple times so that the system doesn't have to fetch the information about the local
    language and country conventions multiple times.
     */

    // this operation getDateInstance() is expensive operation
    // we should try to call this as few times as possible
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
    Calendar calendar = df.getCalendar();
    calendar.setTimeInMillis(this.time);
    Date date = df.getCalendar().getTime();

    // SimpleDateFormat may not work with certain locales, should place in a try block
    SimpleDateFormat sdf = (SimpleDateFormat) df;
    sdf.applyPattern(DEFAULT_TIME_FORMAT);
    return sdf.format(date);
  }

} // AlarmClock
