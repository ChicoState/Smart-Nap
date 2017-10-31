package csuchico.smartnap;

import android.util.Log;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

public class AlarmClock extends SugarRecord<AlarmClock> {

  private long time;
  private String name;
  //private List<FlashCard> cards;
  private ArrayList<Long> cardIDList;


  @Ignore // do not store in database
  private int CARD_LIST_INDEX;

  // Note: Please retain default constructor
  public AlarmClock() {
  }

  // Constructor
  public AlarmClock(long time, String name, ArrayList<Long> cardIDs) {
    this.time = time;
    this.name = name;
    this.cardIDList = new ArrayList<Long>(cardIDs);

    this.CARD_LIST_INDEX = 0; // default INDEX upon alarm construction
  }

  // All of the following functions are used to manage the list of cards attached to alarm

  public String getTimeFormatted() {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
    Calendar calendar = df.getCalendar();
    calendar.setTimeInMillis(this.time);
    SimpleDateFormat sdf = (SimpleDateFormat) df;
    Date date = df.getCalendar().getTime();

    sdf.applyPattern("h:mm a");
    return sdf.format(date);

  }

  public void resetIndex() {
    this.CARD_LIST_INDEX = 0;
  }

  public ArrayList<Long> getListOfCardIDs() { return this.cardIDList; }

  public String getName() {
    return name;
  }
  public long getTime() { return time; }

  public void putListOfCardIDs(ArrayList<Long> idList) { this.cardIDList = idList; }

  public void setName(String name) {
    this.name = name;
  }
  public void setTime(long time) {
    this.time = time;
  }
} // AlarmClock
