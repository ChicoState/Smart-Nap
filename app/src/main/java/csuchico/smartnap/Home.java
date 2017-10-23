package csuchico.smartnap;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.orm.SugarDb;
import com.orm.SugarRecord;

import java.io.File;
import java.util.List;

public class Home extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
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