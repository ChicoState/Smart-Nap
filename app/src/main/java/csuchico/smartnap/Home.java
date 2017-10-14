package csuchico.smartnap;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.orm.SugarDb;

import java.io.File;
import java.util.List;

public class Home extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    SugarDb smartNapDB = new SugarDb(getApplicationContext());

    //AlarmClock.findById(AlarmClock.class, (long) 1); // Perform this for each SugarRecord  model
    //FlashCard.findById(FlashCard.class, (long) 1); // Perform this for each SugarRecord  model
  }

  public void alarmSetup(View view) {
    Intent openAlarmPage = new Intent(this, AlarmEdit.class);
    startActivity(openAlarmPage);
  }

  public void GotoQuestion(View view){
    Intent openQuestionPage = new Intent(this, Question.class);
    startActivity(openQuestionPage);
  }
}