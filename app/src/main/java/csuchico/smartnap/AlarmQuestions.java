package csuchico.smartnap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AlarmQuestions extends AppCompatActivity {

    boolean userIsEditingExistingFlashCard;
    Button delete, save;
    EditText question, answer, classn;
    ArrayList<String> alarmIdList;
    FlashCard FlashCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_questions);
        delete = findViewById(R.id.delete);
        save = findViewById(R.id.save_fc);
        question = findViewById(R.id.fc_question);
        answer = findViewById(R.id.fc_answer);
        classn = findViewById(R.id.classname);
        Intent currentIntent = this.getIntent();
        initActivity(currentIntent);
    }

    private void initActivity(Intent intent) {
        alarmIdList = new ArrayList<>();
        String actionBarTitle;
        Bundle data = intent.getExtras(); // grab any extras available
        actionBarTitle = getString(R.string.header_createFlashCard);
        userIsEditingExistingFlashCard = false;
        delete.setVisibility(View.GONE); // make delete alarm button invisible

        if (data != null) {
            long id;
            String name, eanswer, equestion;
            actionBarTitle = getString(R.string.header_editFlashCard);
            userIsEditingExistingFlashCard = true;
            id = data.getLong(getString(R.string.extraKey_alarm));
            FlashCard = csuchico.smartnap.FlashCard.findById(FlashCard.class,id);
            Log.i("FlashCardEdit","Found FlashCard with id: " + id + "!");
            name = FlashCard.getClassName();
            eanswer = FlashCard.getAnswer();
            equestion = FlashCard.getQuestion();
            classn.setText(name);
            question.setText(equestion);
            answer.setText(eanswer);
            delete.setVisibility(View.VISIBLE); // make delete alarm button visible

            // return a list of all linkedCards associated with this alarm
            List<AlarmClockFlashCardLinker> linkedCards = FlashCard.getAlarms();

            for (int i = 0; i < linkedCards.size(); i++ ) {
                AlarmClock currentAlarm = linkedCards.get(i).alarm; // grab the card from current link data
                long alarmId = currentAlarm.getId();
                alarmIdList.add(Long.toString(alarmId)); // save the cards table row Id
            }

        }

        try {
            if(getSupportActionBar() != null) {
                getSupportActionBar().setTitle(actionBarTitle);
            }
        }
        catch (NullPointerException npe) {
            Log.e("QuestionEdit","Exception thrown while setting actionBar title",npe);
        }
    }

    public void savequestion(View view) {
        EditText question = findViewById(R.id.fc_question);
        EditText answer = findViewById(R.id.fc_answer);
        EditText classn = findViewById(R.id.classname);
        if(question.length() != 0 && answer.length() != 0 && classn.length() != 0) {
            if (userIsEditingExistingFlashCard) {
                try {
                    FlashCard.updatefc(classn.toString(),question.toString(),answer.toString());
                } catch (NullPointerException npe) {
                    Log.w("FlashCardEdit", "There was a null pointer exception while setting the Alarm Clock!");
                    npe.printStackTrace();
                }
            } else {
                FlashCard = new FlashCard(classn.getText().toString(), question.getText().toString(), answer.getText().toString());
                Toast.makeText(AlarmQuestions.this, "FlashCard has been made!", Toast.LENGTH_SHORT).show();
                FlashCard.save();
            }
        }
        else{
            Toast.makeText(AlarmQuestions.this, "ERROR: Can't save FlashCard!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void deletequestion(View view)  {
        List<AlarmClockFlashCardLinker> links = FlashCard.getAlarms();
        // delete each link in the linker table for this alarm clock
        for ( int i = 0; i < links.size(); i++ ) {
            links.get(i).delete();
        }
        long id = FlashCard.getId();
        FlashCard.delete();
        FlashCard test = csuchico.smartnap.FlashCard.findById(FlashCard.class,id);
        if ( test != null ) {
            Toast.makeText(AlarmQuestions.this,"FlashCard did not delete successfully!", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(AlarmQuestions.this,"Deleted FlashCard!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
