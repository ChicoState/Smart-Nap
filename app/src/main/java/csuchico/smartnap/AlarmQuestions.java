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

    public void flashcardVisible(View view) {
        LinearLayout fcbox = (LinearLayout) findViewById(R.id.FlashcardOption);
        TableLayout mcbox = (TableLayout) findViewById(R.id.MultipleChoice);
        fcbox.setVisibility(View.VISIBLE);
        mcbox.setVisibility(View.GONE);
    }
    public void multiplechoiceVisible(View view) {
        LinearLayout fcbox = (LinearLayout) findViewById(R.id.FlashcardOption);
        TableLayout mcbox = (TableLayout) findViewById(R.id.MultipleChoice);
        fcbox.setVisibility(View.GONE);
        mcbox.setVisibility(View.VISIBLE);
    }
}
