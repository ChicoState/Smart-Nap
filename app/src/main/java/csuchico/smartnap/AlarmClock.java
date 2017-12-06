package csuchico.smartnap;

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

  private String key;
  private long time;
  private String name;

  @Ignore
  private static final String DEFAULT_TIME_FORMAT = "h:mm a";

  // Note: Please retain default constructor
  public AlarmClock() {
  }

  // Constructor
  public AlarmClock(long time, String name) {
    this.key = generateKey();
    this.time = time;
    this.name = name;
    //this.link = null;
  }

  private String generateKey() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }

  public List<AlarmClockFlashCardLinker> getCards() {
    return AlarmClockFlashCardLinker.find(
            AlarmClockFlashCardLinker.class,
            "alarm = ?",
            Long.toString(this.getId())
    );
  }

  public String getKey() {
    return this.key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public String getTimeFormatted(String format) {

    if ( format.isEmpty() ) {
      format = DEFAULT_TIME_FORMAT;
    }

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
    sdf.applyPattern(format);
    return sdf.format(date);
  }

} // AlarmClock
