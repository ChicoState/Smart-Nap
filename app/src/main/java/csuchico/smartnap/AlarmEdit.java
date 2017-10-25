package csuchico.smartnap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.util.Log;


// API-24 required for 'android.icu.util.Calendar', use 'java.util.Calendar' for older API
//import android.icu.util.Calendar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static csuchico.smartnap.R.layout.activity_alarm_edit;

public class AlarmEdit extends AppCompatActivity {

  //static final int ADD_FLASHCARD_REQUEST = 1; // requestCode for adding flash card

  private boolean alarmNameIsNotDefault = false;
  private boolean userIsEditingExistingAlarm;

  private AlarmClock alarmClock;
  AlarmManager alarmManager;
  private PendingIntent servicePendingIntent;
  private TimePicker alarmTimePicker;
  EditText alarmNameText;
  Button saveAlarm, deleteAlarm, addFlashCard;
  private List<FlashCard> alarmCards;

  private final EditText.OnTouchListener editAlarmNameListener = new EditText.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
      if (!alarmNameIsNotDefault) {
        alarmNameIsNotDefault = true;
        alarmNameText.getText().clear();
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
    setContentView(activity_alarm_edit);

    alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    alarmNameText = (EditText) findViewById(R.id.alarmNameEdit);
    alarmNameText.setOnTouchListener(editAlarmNameListener);
    saveAlarm = (Button) findViewById(R.id.buttonCreateAlarm);
    deleteAlarm = (Button) findViewById(R.id.buttonDeleteAlarm);
    addFlashCard = (Button)findViewById(R.id.buttonAddFlashCard);
    addFlashCard.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(AlarmEdit.this,fcpop.class));
      }
    });

    Intent currentIntent = this.getIntent();
    initActivityBasedOnIntent(currentIntent);
  }

  private void initActivityBasedOnIntent(Intent intent) {
    String actionBarTitle;
    Bundle data = intent.getExtras(); // grab any extras available
    if (data != null) {
      Calendar calendar;
      long id, time;
      String name;
      int currentHour, currentMinute;
      actionBarTitle = getString(R.string.header_editAlarm);
      userIsEditingExistingAlarm = true;
      id = data.getLong(getString(R.string.key_alarmID));
      alarmClock = AlarmClock.findById(AlarmClock.class,id);
      name = alarmClock.getName();
      time = alarmClock.getTime();
      calendar = Calendar.getInstance();
      calendar.setTimeInMillis(time);
      currentHour = calendar.get(Calendar.HOUR);
      currentMinute = calendar.get(Calendar.MINUTE);
      alarmNameText.setText(name);
      alarmTimePicker.setHour(currentHour);
      alarmTimePicker.setMinute(currentMinute);
      deleteAlarm.setVisibility(View.VISIBLE); // make delete alarm button visible
    }
    else {
      actionBarTitle = getString(R.string.header_createAlarm);
      userIsEditingExistingAlarm = false;
      deleteAlarm.setVisibility(View.GONE); // make delete alarm button invisible
    }
    try {
      if(getSupportActionBar() != null) {
        getSupportActionBar().setTitle(actionBarTitle);
      }
    }
    catch (NullPointerException npe) {
      Log.e("AlarmEdit","Exception thrown while setting actionBar title",npe);
    }
  }

  /*
    Function:   createNewAlarm(View)
    Operation:  Takes the information provided by user on the AlarmEdit activity and creates
                a new alarm with the AlarmEdit Manager.
    Called:     When user pushes the "Create AlarmEdit" button on the AlarmEdit activity
  */
  public void createNewAlarm(View view) {

    String alarmName = alarmNameText.getText().toString();

    // Setup calendar based on the current time chosen by the user
    Calendar calendar = Calendar.getInstance();

    calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
    calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

    // Make a new intent for the broadcast
    // Intent d = new Intent("csuchico.smartnap.AlarmDialog");
    // pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, d, Intent.FLAG_ACTIVITY_NEW_TASK);
    long alarmTime = calendar.getTimeInMillis();

    if(!userIsEditingExistingAlarm) {       // user is creating a new alarm
      alarmClock = new AlarmClock(alarmTime,alarmName,alarmCards);
    }
    else {    // user is editing an existing alarm
      // we want to update the AlarmClock fields
      try {
        alarmClock.setName(alarmName);
        alarmClock.setTime(alarmTime);
      }
      catch (NullPointerException npe) {
        Log.w("AlarmEdit","There was a null pointer exception while setting the Alarm Clock!");
        npe.printStackTrace();
      }
    }

    alarmClock.save();
    long alarmID = alarmClock.getId();
    int requestCode = (int) alarmID;

    // create a new bundle to store the data of our alarm
    Bundle dataBundle = new Bundle();
    dataBundle.putInt(getString(R.string.key_alarmID), (int) alarmID);
    dataBundle.putString(getString(R.string.key_alarmName), alarmName);

    // create intent for the alarm
    Intent receiverIntent = new Intent(AlarmEdit.this, AlarmReceiver.class);
    receiverIntent.putExtras(dataBundle);

    // broadcast myIntent to pendingIntent
    servicePendingIntent = PendingIntent.getBroadcast(
            AlarmEdit.this, requestCode, receiverIntent, 0);

    // sets the alarm up using our pendingIntent operation defined to retrieve broadcasts
    alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, servicePendingIntent);
    Log.d("AlarmEdit", "Setting alarm in AlarmManager.");

    finish();
  } // createNewAlarm()

  private void deleteAlarm() {

  }
  /*
    @function:  addFlashCard()
    @desc:      Called when user touches "Add Flash Card" button for an alarm. Used to
                start the process of attaching flash cards to the alarm clock.
   */


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
