package csuchico.smartnap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AlarmQuestions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_questions);
    }

    public void savequestion(View view) {
        EditText question = (EditText)findViewById(R.id.fc_question);
        EditText answer = (EditText)findViewById(R.id.fc_answer);
        EditText classn = (EditText) findViewById(R.id.classname);
        if(question.length() != 0 && answer.length() != 0 && classn.length() != 0) {
            FlashCard card = new FlashCard(classn.getText().toString(), question.getText().toString(), answer.getText().toString());
            Toast.makeText(AlarmQuestions.this, "FlashCard has been made!", Toast.LENGTH_SHORT).show();
            card.save();
        }
        else{
            Toast.makeText(AlarmQuestions.this, "ERROR: Can't save FlashCard!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
