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
import android.widget.Toast;


// API-24 required for 'android.icu.util.Calendar', use 'java.util.Calendar' for older API
//import android.icu.util.Calendar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static csuchico.smartnap.R.layout.activity_alarm_edit;

public class AlarmEdit extends AppCompatActivity {

  static final int ADD_FLASHCARD_REQUEST = 1; // requestCode for adding flash card

  private boolean alarmNameIsNotDefault = false;
  private boolean userIsEditingExistingAlarm;

  private AlarmClock alarmClock;
  AlarmManager alarmManager;
  private PendingIntent servicePendingIntent;
  private TimePicker alarmTimePicker;
  EditText alarmNameText;
  Button saveAlarm, deleteAlarm, addFlashCard;
  private ArrayList<String> returnedCardList;
  private ArrayList<String> cardIdList;

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

    cardIdList = new ArrayList<>();

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
        Intent cardListView = new Intent(AlarmEdit.this, fcpop.class);
        Bundle data = new Bundle();
        data.putStringArrayList(getString(R.string.extraKey_cards),cardIdList);
        startActivityForResult(cardListView, ADD_FLASHCARD_REQUEST);
      }
    });

    Intent currentIntent = this.getIntent();
    initActivity(currentIntent);
  }

  private void initActivity(Intent intent) {
    cardIdList = new ArrayList<>();
    String actionBarTitle;
    Bundle data = intent.getExtras(); // grab any extras available
    actionBarTitle = getString(R.string.header_createAlarm);
    userIsEditingExistingAlarm = false;
    deleteAlarm.setVisibility(View.GONE); // make delete alarm button invisible

    if (data != null) {
      Calendar calendar;
      long id, time;
      String name;
      int currentHour, currentMinute;
      actionBarTitle = getString(R.string.header_editAlarm);
      userIsEditingExistingAlarm = true;
      id = data.getLong(getString(R.string.extraKey_alarm));
      alarmClock = AlarmClock.findById(AlarmClock.class,id);
      Log.i("AlarmEdit","Found AlarmClock with id: " + id + "!");
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

      // return a list of all linkedCards associated with this alarm
      List<AlarmClockFlashCardLinker> linkedCards = alarmClock.getCards();

      for (int i = 0; i < linkedCards.size(); i++ ) {
        FlashCard currentCard = linkedCards.get(i).card; // grab the card from current link data
        long cardId = currentCard.getId();
        cardIdList.add(Long.toString(cardId)); // save the cards table row Id
      }

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

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    // process a flashcard request
    if ( requestCode == ADD_FLASHCARD_REQUEST ) {
      if ( resultCode == RESULT_OK ) {
        // Flashcards were chosen and added successfully
        cardIdList = data.getStringArrayListExtra(getString(R.string.extraKey_cards));
        Log.i("AlarmEdit","onActivityResult processed the returned list of cards");
      }
      else {
        // returnedCardList returned a null value
        Log.w("AlarmEdit","onActivityResult has a null list of flash card ID");
      }
    } // ADD_FLASHCARD_REQUEST
  } // onActivityResult

  /*
    Function:   saveAlarm(View)
    Operation:  Takes the information provided by user on the AlarmEdit activity and creates
                a new alarm with the AlarmEdit Manager.
    Called:     When user pushes the "Create AlarmEdit" button on the AlarmEdit activity
  */
  public void saveAlarm(View view) {

    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
    calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

    long alarmTime = calendar.getTimeInMillis();
    String alarmName = alarmNameText.getText().toString();

    if ( !userIsEditingExistingAlarm ) {       // user is creating a new alarm
      alarmClock = new AlarmClock(alarmTime,alarmName);
      Log.i("AlarmEdit","Adding a new alarm clock!");
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

    List<AlarmClockFlashCardLinker> links = buildCardLinks();
    processLinkerTable(links);
    setAlarm(alarmTime);

    finish();
  } // saveAlarm()

  private List<AlarmClockFlashCardLinker> buildCardLinks() {
    List<AlarmClockFlashCardLinker> cardLinks = new ArrayList<>();
    for ( int i = 0; i < cardIdList.size(); i++ ) {
      Long id = Long.valueOf(cardIdList.get(i));
      FlashCard card = FlashCard.findById(FlashCard.class,id);
      AlarmClockFlashCardLinker link = new AlarmClockFlashCardLinker(alarmClock,card);
      cardLinks.add(link);
    }
    return cardLinks;
  }

  private void processLinkerTable(List<AlarmClockFlashCardLinker> receivedLinks) {
    List<AlarmClockFlashCardLinker> currentLinks = AlarmClockFlashCardLinker.listAll(
            AlarmClockFlashCardLinker.class);

    int sizeOfReceived = receivedLinks.size();

    for ( int i = 0; i < sizeOfReceived; i++ ) {
      AlarmClockFlashCardLinker link = receivedLinks.get(i);
      if ( currentLinks.contains(link) ) { // exists in currentLinks already
        receivedLinks.remove(link);
      }
      else {
        AlarmClockFlashCardLinker newLink = new AlarmClockFlashCardLinker(link.alarm,link.card);
        newLink.save();
      }
    }
  }

  public void deleteAlarm(View view) {


    /*
    if(servicePendingIntent == null) {
      servicePendingIntent.cancel();
    }
    */

    long alarmID = alarmClock.getId();
    int requestCode = (int) alarmID;

    // create intent for the alarm
    Intent receiverIntent = new Intent(AlarmEdit.this, AlarmReceiver.class);

    servicePendingIntent = PendingIntent.getBroadcast(
            AlarmEdit.this, requestCode, receiverIntent, 0);
    alarmManager.cancel(servicePendingIntent);

    // This code is replaced by the custom method getCards() in the AlarmClock class
    /*
    List<AlarmClockFlashCardLinker> links = AlarmClockFlashCardLinker.find(
            AlarmClockFlashCardLinker.class,
            "alarm = ?",
            Long.toString(alarmClock.getId())
    );
    */

    // create a list of links currently tied to this alarm through our linker table
    List<AlarmClockFlashCardLinker> links = alarmClock.getCards();
    // delete each link in the linker table for this alarm clock
    for ( int i = 0; i < links.size(); i++ ) {
      links.get(i).delete();
    }


    long id = alarmClock.getId();
    alarmClock.delete();
    AlarmClock testClock = AlarmClock.findById(AlarmClock.class,id);
    if ( testClock != null ) {
      Toast.makeText(AlarmEdit.this,"Alarm did not delete successfully!", Toast.LENGTH_SHORT).show();
    }
    Toast.makeText(AlarmEdit.this,"Deleted alarm!", Toast.LENGTH_SHORT).show();
    finish();
  }

  private void setAlarm(long time) {
    // Process the Alarm data to be sent to the receiver and service

    long alarmTime = time;
    long alarmID = alarmClock.getId();
    int requestCode = (int) alarmID;

    // create a new bundle to store the data of our alarm
    Bundle dataBundle = new Bundle();
    dataBundle.putInt(getString(R.string.extraKey_alarm), (int) alarmID);

    // create intent for the alarm
    Intent receiverIntent = new Intent(AlarmEdit.this, AlarmReceiver.class);
    receiverIntent.putExtras(dataBundle);

    // broadcast myIntent to pendingIntent
    servicePendingIntent = PendingIntent.getBroadcast(
            AlarmEdit.this, requestCode, receiverIntent, 0);

    // sets the alarm up using our pendingIntent operation defined to retrieve broadcasts
    alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, servicePendingIntent);
    Log.d("AlarmEdit", "Setting alarm in AlarmManager.");
  }

  @Override
  public void onBackPressed() {
    if ( userIsEditingExistingAlarm ) {
      // we want to save whatever their data was at this the time of backing up
      Log.i("AlarmEdit","User pressed back while editing an existing alarm");
    }
    else {
      // user must have decided NOT to setup a new alarm
      Log.i("AlarmEdit","User did not finish setting up new alarm before pressing back");
    }
  }
}
