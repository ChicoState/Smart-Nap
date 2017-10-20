package csuchico.smartnap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.util.Log;

// API-24 required for 'android.icu.util.Calendar', use 'java.util.Calendar' for older API
//import android.icu.util.Calendar;
import java.util.ArrayList;
import java.util.Calendar;

public class AlarmEdit extends AppCompatActivity {

  //static final int ADD_FLASHCARD_REQUEST = 1; // requestCode for adding flash card

  private boolean mAlarmNameSet = false;

  AlarmManager alarmManager;
  private PendingIntent servicePendingIntent;
  private TimePicker alarmTimePicker;
  EditText alarmNameText;
  ArrayList<Integer> alarmCards;

  private final EditText.OnTouchListener editAlarmNameListener = new EditText.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
      if (!mAlarmNameSet) {
        mAlarmNameSet = true;
        alarmNameText.getText().clear();
        alarmNameText.setFocusable(true);
        alarmNameText.requestFocus();
      }
      return false;
    }
  };

  @Override
  public void onStart() {
        super.onStart();
    }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alarm_edit);

    // setup action bar to reflect title of activity
    String title;
    title = getString(R.string.editAlarmHeader);
    try {
      if(getSupportActionBar() != null) {
        getSupportActionBar().setTitle(title);
      }
    }
    catch (NullPointerException npe) {
      Log.e("AlarmEdit","Exception thrown while setting actionBar title",npe);
    }

    checkIfEditingExistingAlarm();

    alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    alarmNameText = (EditText) findViewById(R.id.alarmNameEdit);

    alarmNameText.setOnTouchListener(editAlarmNameListener);
  }

  private void checkIfEditingExistingAlarm() {
    // check whether this is a new alarm being created, or a current one being modified
    Intent currentIntent = this.getIntent();
    Bundle intentData = currentIntent.getExtras(); // grab any extras available
    if (intentData == null) {
      return;
    }
    else {
      int currentAlarmID = intentData.getInt("alarmID");
      processCurrentAlarmData(currentAlarmID);
    }
  }

  private void processCurrentAlarmData(int id) {
    AlarmClock clock = AlarmClock.findById(AlarmClock.class,id);

    String name = clock.getName();
    long time = clock.getTime();
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(time);
    int currentHour = calendar.get(Calendar.HOUR);
    int currentMinute = calendar.get(Calendar.MINUTE);

    alarmNameText.setText(name);
    alarmTimePicker.setHour(currentHour);
    alarmTimePicker.setMinute(currentMinute);
  }
  /*
    Function:   createNewAlarm(View)
    Operation:  Takes the information provided by user on the AlarmEdit activity and creates
                a new alarm with the AlarmEdit Manager.
    Called:     When user pushes the "Create AlarmEdit" button on the AlarmEdit activity
  */
  public void createNewAlarm(View view) {
    AlarmClock alarm;
    Calendar calendar;
    String alarmName;
    long alarmTime, alarmID;
    Bundle dataBundle;
    Intent receiverIntent;

    // Setup calendar based on the current time chosen by the user
    calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
    calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

    alarmName = alarmNameText.getText().toString();
    alarmTime = calendar.getTimeInMillis();

    // need code here to populate alarmCards with an array of number ID's for flash cards added
    // to this AlarmClock

    alarm = new AlarmClock(alarmTime,alarmName,alarmCards);
    alarm.save(); // save to database
    alarmID = alarm.getId();

    // setup intent for AlarmReceiver
    receiverIntent = new Intent(AlarmEdit.this, AlarmReceiver.class);
    dataBundle = new Bundle();
    dataBundle.putInt("alarmID", (int) alarmID);
    receiverIntent.putExtras(dataBundle);

    // setup PendingIntent to broadcast receiverIntent
    servicePendingIntent = PendingIntent.getBroadcast(
            AlarmEdit.this,
            (int) alarmID,
            receiverIntent,
            0);

    // finalizes setting up the alarm by passing the servicePendingIntent
    alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, servicePendingIntent);

    Log.i("AlarmEdit", "Setting alarm in AlarmManager.");
    finish();
  } // createNewAlarm()

  /*
    @function:  addFlashCard()
    @desc:      Called when user touches "Add Flash Card" button for an alarm. Used to
                start the process of attaching flash cards to the alarm clock.
   */
  public void addFlashCard(View view) {
    /*
    Intent editQuestion = new Intent(AlarmEdit.this, AlarmQuestions.class);
    startActivityForResult(editQuestion, ADD_FLASHCARD_REQUEST);
    */
    Log.w("AlarmEdit","addFlashCard() still needs implementation! Dead Button!");
  } // addFlashCard()

  /*
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == ADD_FLASHCARD_REQUEST) {
      if (resultCode == RESULT_OK) {
        // Flashcard was chosen and added successfully
      }
    }
  }
  */

}
