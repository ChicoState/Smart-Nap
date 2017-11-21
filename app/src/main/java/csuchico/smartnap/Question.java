package csuchico.smartnap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Question extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_questions);
        ListView listview = (ListView) findViewById(R.id.list);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        List<FlashCard> fc = FlashCard.listAll(FlashCard.class);
        if(fc.size() == 0){
            Toast.makeText(Question.this,"Database is empty!", Toast.LENGTH_SHORT).show();
        }
        else{
            FlashCardEditAdapter listAdapter = new FlashCardEditAdapter(this, new ArrayList<>(fc));
            listview.setAdapter(listAdapter);
        }
    }

    public void AddQuestion(View view){
        Intent openAlarmpage = new Intent(this, AlarmQuestions.class);
        startActivity(openAlarmpage);
    }

}