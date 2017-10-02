package csuchico.smartnap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;

public class AlarmQuestions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_questions);
    }

    public void truefalseVisible(View view) {
        LinearLayout tfbox = (LinearLayout) findViewById(R.id.TrueFalseOption);
        TableLayout mcbox = (TableLayout) findViewById(R.id.MultipleChoice);
        tfbox.setVisibility(View.VISIBLE);
        mcbox.setVisibility(View.GONE);
    }
    public void multiplechoicemultiple(View view) {
        LinearLayout tfbox = (LinearLayout) findViewById(R.id.TrueFalseOption);
        TableLayout mcbox = (TableLayout) findViewById(R.id.MultipleChoice);
        tfbox.setVisibility(View.GONE);
        mcbox.setVisibility(View.VISIBLE);
    }
}
