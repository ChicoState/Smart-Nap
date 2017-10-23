package csuchico.smartnap;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 *Created by gerald on 10/21/2017.
 */

public class fcpop extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fc_pop);
        DisplayMetrics screensize = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screensize);
        int width = screensize.widthPixels;
        int height = screensize.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .8));

        ListView listview = findViewById(R.id.list_fc);
        ArrayList<String> list = new ArrayList<>();
        List<FlashCard> fc = FlashCard.listAll(FlashCard.class);
        if(fc.isEmpty()){
            Toast.makeText(fcpop.this,"Database is empty!", Toast.LENGTH_SHORT).show();
        }
        else{
            long i = 1;
            int size = fc.size();
            while(size > 0)
            {
                FlashCard newe = FlashCard.findById(FlashCard.class,i);
                list.add(newe.m_class);
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);
                listview.setAdapter(listAdapter);
                i++;
                size--;
            }
        }

    }
}