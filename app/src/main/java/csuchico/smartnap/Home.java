package csuchico.smartnap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    AlarmClock alarm = AlarmClock.findById(AlarmClock.class,(long) 0);
    FlashCard cards = FlashCard.findById(FlashCard.class,(long) 0);

    // pull list of alarms from database
    List<AlarmClock> listOfAlarms = AlarmClock.listAll(AlarmClock.class);
    ArrayList<AlarmClock> listOfAlarmsForAdapter = new ArrayList<>(listOfAlarms);
    // pass the casted ArrayList to the AlarmAdapter for our listview
    AlarmAdapter adapter = new AlarmAdapter(this, listOfAlarmsForAdapter);

    // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
    // There should be a {@link ListView} with the view ID called list, which is declared in the
    // activity_numbers.xml layout file.
    ListView listView = (ListView) findViewById(R.id.homeAlarmList);

    // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
    // {@link ListView} will display list items for each word in the list of words.
    // Do this by calling the setAdapter method on the {@link ListView} object and pass in
    // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
    listView.setAdapter(adapter);
  }

  public void alarmSetup(View view) {
    Intent openAlarmPage = new Intent(this, AlarmEdit.class);
    startActivity(openAlarmPage);
  }

  public void GotoQuestion(View view){
    Intent openQuestionPage = new Intent(this, AlarmQuestions.class);
    startActivity(openQuestionPage);
  }
}
