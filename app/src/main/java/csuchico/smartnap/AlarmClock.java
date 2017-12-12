package csuchico.smartnap;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
    Calendar calendar = df.getCalendar();
    calendar.setTimeInMillis(this.time);
    Date date = df.getCalendar().getTime();

    SimpleDateFormat sdf = (SimpleDateFormat) df;
    sdf.applyPattern(DEFAULT_TIME_FORMAT);
    return sdf.format(date);
  }

} // AlarmClock
