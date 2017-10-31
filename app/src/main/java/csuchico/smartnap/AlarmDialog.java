package csuchico.smartnap;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

// The following import statements would allow us to use simpler code below where only
// the FLAG_* is necessary versus the full location.
// Think about "using std::cout;" and then not having to import the entire <iostream>
// and being able to type "cout <<" and not have to use "std::cout <<" every time
/*
import static android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
import static android.view.WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;
*/


public class AlarmDialog extends AppCompatActivity {

  private Ringtone mAlarmTone;
  private TextView m_alarmNameText;
  private TextView m_cardQuestionText;
  private TextView m_cardAnswerText;
  private FlashCard m_FlashCard;
  private AlarmClock alarm;
  private long alarmID;
  private ArrayList<FlashCard> cardsForDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // First we need to setup some flags on the creation of this window so that we can
    // access it and see it appropriately even when our phone is asleep and/or locked

    // FLAG_KEEP_SCREEN_ON ensures alarm dialog is visible until silenced
    // FLAG_SHOW_WHEN_LOCKED ensured dialog is accessible when screen is locked by keyguard
    // FLAG_TURN_SCREEN_ON allows this activity to turn the screen on when its created
    this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
            | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

    setContentView(R.layout.activity_alarm_dialog); // window decoration is created

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.hide();
    }

    m_cardQuestionText = (TextView) findViewById(R.id.fc_question);
    m_cardAnswerText = (TextView) findViewById(R.id.fc_answer);
    m_alarmNameText = (TextView)  findViewById(R.id.alarmName);

    cardsForDialog = new ArrayList<FlashCard>();

    alarmInit();

    playTone();
  }

  /*
    @function: alarmInit()
   */

  private void alarmInit() {
    Intent alarmIntent = getIntent();
    Bundle alarmData = alarmIntent.getExtras();
    alarmID = (long) alarmData.getInt(getString(R.string.extraKey_alarm));
    alarm = AlarmClock.findById(AlarmClock.class, alarmID);
    String name = alarm.getName();
    ArrayList<Long> flashCardIDList = alarm.getListOfCardIDs();
    if (flashCardIDList == null) {
      Log.i("AlarmDialog", "FLASH CARD LIST IS NULL!");
    }
    else {

      // parse all the ID's into flash cards for display
      for ( int i = 0; i < flashCardIDList.size(); i++ ) {
        FlashCard currentCard = FlashCard.findById(FlashCard.class, flashCardIDList.get(i));
        cardsForDialog.add(currentCard);
      }
    }
    if (cardsForDialog.size() > 0) {
      FlashCard cur = cardsForDialog.get(0);
      updateCurrentFlashCard(cur.question, cur.answer);
    }
    else {
      updateCurrentFlashCard("This QUESTION is a dummy because our flash card didn't load!",
              "This ANSWER is a dummy because of no flash card loading!");
    }
    m_alarmNameText.setText(name);
  } // alarmInit()

  private void updateCurrentFlashCard(String question, String answer) {
    m_cardQuestionText.setText(question);
    m_cardAnswerText.setText(answer);
  } // updateCurrentFlashCard()

  /**
   * @function    playTone()
   * @desc        Starts playing the users chosen ringtone for the specified alarm
   */
  private void playTone() {
    Log.i("AlarmDialog", "AlarmDialog initialized, playing tone for alarm");

    // first try to get the default alarm sound
    Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    if (alarmUri == null) {         // if default alarm sound isnt available
      // then get the default notification sound
      alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
      if (alarmUri == null) {     // if default notification sound isnt available
        // then get the default ringtone sound
        alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
      }
    }

    mAlarmTone = RingtoneManager.getRingtone(this, alarmUri);

    // Using minSDKVersion 21 is not what we originally intended for this project, but we
    // may be able to setup some kind of filter system that uses an older deprecated method
    // to play the alarm tone regardless of phones ringtone volume. As of right now, the
    // AudioAttributes method requires a minimum SDK of 21. However, there may be an older
    // method that allows us to use setStreamMethod.
    // See the following thread:
    // https://stackoverflow.com/questions/15578812/troubles-play-sound-in-silent-mode-on-android

    Log.i("AlarmDialog","Setting up AudioAttributes to attach to our ringtone USAGE_ALARM flag");
    AudioAttributes alarmSound = new AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM) // play alarm even if phone silenced
            .build();
    mAlarmTone.setAudioAttributes(alarmSound);
    mAlarmTone.play();
  } // playTone()

  /**
   * @function    onSilenceAlarm()
   * @desc        Called when a user interacts with the Silence AlarmEdit button
   */
  public void onSilenceAlarm(View view) {
    Log.i("AlarmDialog", "User has chosen to silence alarm");
    alarm.delete(); // delete from db table
    mAlarmTone.stop();
    finish();
  }

  /**
   * set fcquestion TextView visibility to gone and set fcanswer TextView visibility to visible
   */
  public void to_answer(View view){
    TextView answer = (TextView) findViewById(R.id.fc_answer);
    TextView question = (TextView) findViewById(R.id.fc_question);
    question.setVisibility(View.GONE);
    answer.setVisibility(View.VISIBLE);
  }

  /**
   * set fcanswer TextView visibility to gone and set fcquestion TextView visibility to visible
   */
  public void to_question(View view){
    TextView answer = (TextView) findViewById(R.id.fc_answer);
    TextView question = (TextView) findViewById(R.id.fc_question);
    answer.setVisibility(View.GONE);
    question.setVisibility(View.VISIBLE);
  }
}
