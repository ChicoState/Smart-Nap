package csuchico.smartnap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void Alarmsetup(View view){
        Intent openAlarmpage = new Intent(this, AlarmEdit.class);
        startActivity(openAlarmpage);
    }
    public void GotoQuestion(View view){
        Intent openQuestionpage = new Intent(this, Question.class);
        startActivity(openQuestionpage);
    }
}

