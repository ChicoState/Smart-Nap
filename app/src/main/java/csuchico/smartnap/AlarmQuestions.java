package csuchico.smartnap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AlarmQuestions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_questions);
    }

    public void truefalseVisible() {
        TrueFalseOption.setVisibility(View.VISIBLE);
        MultipleChoiceOption.setVisibility(View.GONE);
    }
    public void multiplechoicemultiple() {
        TrueFalseOption.setVisibility(View.GONE);
        MultipleChoiceOption.setVisibility(View.VISIBLE);
    }
}
