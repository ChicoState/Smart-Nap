package csuchico.smartnap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

  private List<AlarmClock> alarms;
  private ListView alarmListView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    alarmListView = (ListView) findViewById(R.id.homeAlarmList);

    initAlarmList();
  }

  @Override
  protected void onResume() {
    super.onResume();
    initAlarmList();
  }

  public void alarmSetup(View view) {
    Intent openAlarmPage = new Intent(getApplicationContext(), AlarmEdit.class);
    startActivity(openAlarmPage);
  }

  public void GotoQuestion(View view){
    Intent openQuestionPage = new Intent(getApplicationContext(),Question.class);
    startActivity(openQuestionPage);
  }

  private void initAlarmList() {
    alarms = AlarmClock.listAll(AlarmClock.class);
    AlarmAdapter adapter = new AlarmAdapter(this, new ArrayList<>(alarms));
    alarmListView.setAdapter(adapter);
  }
}
