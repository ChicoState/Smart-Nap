package csuchico.smartnap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
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
        ArrayList<Long> list = new ArrayList<>();
        List<FlashCard> fc = FlashCard.listAll(FlashCard.class);
        if(fc.size() == 0){
            Toast.makeText(Question.this,"Database is empty!", Toast.LENGTH_SHORT).show();
        }
        else{
            long i = 1;
            int size = fc.size();
            while(size > 0) {
                FlashCard newe = FlashCard.findById(FlashCard.class, i);
                list.add(newe.getId());
                i++;
                size--;
            }
            ListAdapter listAdapter = new ArrayAdapter<>(this, R.layout.checkable_list, list);
            listview.setAdapter(listAdapter);
        }
    }

    public void AddQuestion(View view){
        Intent openAlarmpage = new Intent(this, AlarmQuestions.class);
        startActivity(openAlarmpage);
    }

}