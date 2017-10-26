package csuchico.smartnap;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 *Created by gerald on 10/21/2017.
 */

public class fcpop extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        final ArrayList<String> selectedclasses;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fc_pop);
        DisplayMetrics screensize = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screensize);
        int width = screensize.widthPixels;
        int height = screensize.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .8));

        ListView listview = findViewById(R.id.fc_list);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        selectedclasses = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        List<FlashCard> fc = FlashCard.listAll(FlashCard.class);
        if(fc.size() == 0){
            Toast.makeText(fcpop.this,"Database is empty!", Toast.LENGTH_SHORT).show();
        }
        else{
            long i = 1;
            int size = fc.size();
            while(size > 1) {
                FlashCard newe = FlashCard.findById(FlashCard.class, i);
                list.add(newe.m_class);
                i++;
                size--;
            }
                ListAdapter listAdapter = new ArrayAdapter<>(this, R.layout.checkable_list, list);
                listview.setAdapter(listAdapter);
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedclass = ((TextView) view).getText().toString();
                if(selectedclasses.contains(selectedclass))
                    selectedclasses.remove(selectedclass); //remove deselected item from the list of selected items
                else
                    selectedclasses.add(selectedclass); //add selected item to the list of selected items
            }
        });
    }
}