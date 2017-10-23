package csuchico.smartnap;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import static android.widget.Toast.LENGTH_SHORT;


public class AlarmQuestions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_questions);
    }

    public void savequestion() {
        EditText question = (EditText)findViewById(R.id.fc_question);
        EditText answer = (EditText)findViewById(R.id.fc_answer);
        EditText classn = (EditText) findViewById(R.id.classname);
        FlashCard card = new FlashCard(classn.toString(),question.toString(),answer.toString());
        Snackbar mysnack = Snackbar.make(findViewById(R.id.myCoordinatorLayout), "new FlashCard has been made", LENGTH_SHORT);
        mysnack.show();
        card.save();
    }
}
