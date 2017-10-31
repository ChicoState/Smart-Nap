package csuchico.smartnap;

import android.util.Log;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AlarmClock extends SugarRecord<AlarmClock> {

  private String key;
  private long time;
  private String name;
  private AlarmClockFlashCardLinker link;

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
    this.link = null;
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

    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
    Calendar calendar = df.getCalendar();
    calendar.setTimeInMillis(this.time);
    Date date = df.getCalendar().getTime();

    SimpleDateFormat sdf = (SimpleDateFormat) df;
    sdf.applyPattern(format);
    return sdf.format(date);

  }

} // AlarmClock
