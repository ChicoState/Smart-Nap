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
import java.util.Calendar;

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

  private final EditText.OnTouchListener editAlarmNameListener = new EditText.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
      if (!alarmNameIsNotDefault) {
        alarmNameIsNotDefault = true;
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
    setContentView(activity_alarm_edit);

    alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    alarmNameText = (EditText) findViewById(R.id.alarmNameEdit);
    alarmNameText.setOnTouchListener(editAlarmNameListener);
    deleteAlarm = (Button) findViewById(R.id.button_deleteAlarm);
    deleteAlarm.setVisibility(View.INVISIBLE);
    Button addfc = (Button)findViewById(R.id.buttonAddFlashCard);
    addfc.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(AlarmEdit.this,fcpop.class));
      }
    });

    long id = getAlarmIfExists();

    // setup action bar to reflect title of activity
    String title;

    if(userIsEditingExistingAlarm) {
      title = getString(R.string.header_editAlarm);
      processCurrentAlarmData(id);
    }
    else {
      title = getString(R.string.header_createAlarm);
    }

    try {
      if(getSupportActionBar() != null) {
        getSupportActionBar().setTitle(title);
      }
    }
    catch (NullPointerException npe) {
      Log.e("AlarmEdit","Exception thrown while setting actionBar title",npe);
    }
  }

  /*
    @function   getAlarmIfExists()
    @returns    long
    @params     none
    @desc       Sets private member boolean userIsEditingExistingAlarm depending on
                whether current calling intent to this activity has packaged a long
                into a data bundle or not. Should the bundle contain this extra under
                the key "alarmID" then it is assumed the user is editing an existing alarm
                and will return the alarm clock's ID value in the database as a long.

                Returns alarm clock's ID in database if present in extra
                Returns -1 iff calling intent contains no alarmID extra

   */
  private long getAlarmIfExists() {
    // check whether this is a new alarm being created, or a current one being modified
    long id = -1; // set default return value of -1
    Intent currentIntent = this.getIntent();
    Bundle intentData = currentIntent.getExtras(); // grab any extras available
    if (intentData == null) {
      userIsEditingExistingAlarm = false;
    }
    else {
      userIsEditingExistingAlarm = true;
      id = intentData.getLong("alarmID");
    }
    return id;
  }

  private void processCurrentAlarmData(long id) {
    alarmClock = AlarmClock.findById(AlarmClock.class,id);
    String name = alarmClock.getName();
    long time = alarmClock.getTime();
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(time);
    int currentHour = calendar.get(Calendar.HOUR);
    int currentMinute = calendar.get(Calendar.MINUTE);

    alarmNameText.setText(name);
    alarmTimePicker.setHour(currentHour);
    alarmTimePicker.setMinute(currentMinute);

    deleteAlarm.setVisibility(View.VISIBLE); // make delete alarm button visible
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

    FlashCard card = new FlashCard(
            "csci430","This is a test question built ahead of time",
            "And then our answer or the other side of this card too!");
    card.save();

    if(!mCurrentAlarm) {       // user is creating a new alarm
      alarmClock = new AlarmClock(alarmTime,alarmName,alarmCards);
    }
    else {    // user is editing an existing alarm
      // we want to update the AlarmClock fields
      try {
        alarmClock.setName(alarmName);
        alarmClock.setTime(alarmTime);
      }
      catch (NullPointerException npe) {
        Log.w("AlarmEdit","AlarmClock may not be initialized!");
        npe.printStackTrace();
      }
    }

    alarm.save();
    long alarmID = alarm.getId();
    int requestCode = (int) alarmID;

    // create a new bundle to store the data of our alarm
    Bundle dataBundle = new Bundle();
    dataBundle.putInt("alarmID", (int) alarmID);
    dataBundle.putString("alarmName", alarmName);

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
