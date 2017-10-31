package csuchico.smartnap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.content.Context.ALARM_SERVICE;

public class AlarmClock extends SugarRecord<AlarmClock> {

  private String key;
  private long time;
  private String name;

  @Ignore
  private static final String DEFAULT_TIME_FORMAT = "h:mm a";
  @Ignore
  private AlarmManager manager;
  @Ignore
  private PendingIntent pendingIntent;
  @Ignore
  private boolean status;
  @Ignore
  private boolean broadcastInitialized = false;

  // Note: Please retain default constructor
  public AlarmClock() {
  }

  // Constructor
  public AlarmClock(long time, String name) {
    this.key = generateKey();
    this.time = time;
    this.name = name;
    this.status = false;
  }

  private String generateKey() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }

  /*
    Function    : getCards()
    Description : Returns a List<> of this AlarmClock's links in the database which allows
                  one to parse all of the associated Flash Card's to the alarm
   */

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

  public boolean getState() {
    return this.status;
  }

  public boolean toggleState() {
    if ( this.status ) {  // alarm is ON
      disableAlarm();
    }
    else {
      enableAlarm();
    }
    return this.getState();
  }

  public void initBroadcast(Context context, long id, Intent receiver) {
    manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
    pendingIntent = PendingIntent.getBroadcast(
            context,
            (int) id,
            receiver,
            0
    );
    this.broadcastInitialized = true;
  }

  private void enableAlarm() {
    if ( broadcastInitialized ) {
      this.status = true;
      manager.set(AlarmManager.RTC_WAKEUP, this.time, pendingIntent);
      Log.i("AlarmClock",
              "Setting alarm (id: " + Long.toString(id) + " to be ENABLED!");
    }
  }

  private void disableAlarm() {
    if ( broadcastInitialized ) {
      this.status = false;
      manager.cancel(pendingIntent);
      Log.d("AlarmClock",
              "Setting alarm (id: " + Long.toString(id) + " to be DISABLED!");
    }
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
