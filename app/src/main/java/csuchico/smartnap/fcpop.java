package csuchico.smartnap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
        /*
        // storing string resources into Array
        List<FlashCard> fc_list = FlashCard.listAll(FlashCard.class);
        // here you store the array of string you got from the database
        String[] array = new String[fc_list.size()];
        int index = 0;
        for (Object value : fc_list) {
            array[index] = (String) value;
            index++;
        }
        // Binding Array to ListAdapter
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.fc_pop, R.id.list_fc, array));
        // refer the ArrayAdapter Document in developer.android.com
        ListView lv = getListView();

        // listening to single list item on click
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // selected item
                String fc = ((TextView) view).getText().toString();
                // Launching new Activity on selecting single List Item
                Intent i = new Intent(getApplicationContext(), SingleListItem.class);
                // sending data to new activity
                i.putExtra("FlashCard", fc);
                startActivity(i);

            }
        });*/
    }
}