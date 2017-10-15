package csuchico.smartnap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.util.Log;

// API-24 required for 'android.icu.util.Calendar', use 'java.util.Calendar' for older API
//import android.icu.util.Calendar;
import java.util.Calendar;

public class AlarmEdit extends AppCompatActivity {
  static final int ADD_FLASHCARD_REQUEST = 1; // requestCode for adding flash card

  AlarmManager alarmManager;
  private PendingIntent servicePendingIntent;
  private TimePicker alarmTimePicker;
  EditText alarmNameText;

  @Override
  public void onStart() {
        super.onStart();
    }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alarm_edit);

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

    alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    alarmNameText = (EditText) findViewById(R.id.alarmNameEdit);
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
            "This is a test question built ahead of time",
            "And then our answer or the other side of this card too!");
    card.save();

    AlarmClock alarm = new AlarmClock(alarmTime,alarmName,card);
    alarm.save();
    long alarmID = alarm.getId();

    // create a new bundle to store the data of our alarm
    Bundle dataBundle = new Bundle();
    dataBundle.putInt("alarmID", (int) alarmID);
    dataBundle.putString("alarmName", alarmName);

    // create intent for the alarm
    Intent receiverIntent = new Intent(AlarmEdit.this, AlarmReceiver.class);
    receiverIntent.putExtras(dataBundle);

    // broadcast myIntent to pendingIntent
    servicePendingIntent = PendingIntent.getBroadcast(AlarmEdit.this, 0, receiverIntent, 0);

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
