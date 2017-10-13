package csuchico.smartnap;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    private static final int ADD_ALARM_REQUEST = 1;
    private static final long alarmID = 1;
    AlarmClock alarm;
    Bundle alarmInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        alarm = AlarmClock.findById(AlarmClock.class, alarmID);
    }

    public void Alarmsetup(View view) {
        Intent openAlarmpage = new Intent(this, AlarmEdit.class);
        alarmInfo = new Bundle();

        startActivityForResult(openAlarmpage, ADD_ALARM_REQUEST, alarmInfo);
    }

    public void GotoQuestion(View view){
        Intent openQuestionpage = new Intent(this, Question.class);
        startActivity(openQuestionpage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_ALARM_REQUEST) {
            if (resultCode == RESULT_OK) {
                Bundle alarmInfo = data.getExtras();
            }
        }
    }
}

