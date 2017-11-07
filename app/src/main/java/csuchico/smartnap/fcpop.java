package csuchico.smartnap;

import android.app.Activity;
import android.content.Intent;
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

  ArrayList<String> selectedClasses;
  ArrayList<String> selectedFlashCardId;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fc_pop);
    DisplayMetrics screenSize = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(screenSize);
    int width = screenSize.widthPixels;
    int height = screenSize.heightPixels;
    getWindow().setLayout((int) (width * .8), (int) (height * .8));

    ListView displayListView = findViewById(R.id.fc_list);
    displayListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    List<FlashCard> flashCards = FlashCard.listAll(FlashCard.class);
    //ArrayList<Long> displayList = new ArrayList<>();
    selectedClasses = new ArrayList<>();
    selectedFlashCardId = new ArrayList<>();

    Intent intent = this.getIntent();
    Bundle currentCardList = intent.getExtras();

    if ( currentCardList != null ) {  // if user has already selected cards for this alarm
      selectedFlashCardId = currentCardList.getStringArrayList(getString(R.string.extraKey_cards));
    }

    if ( flashCards.size() == 0 ) {
      Toast.makeText(fcpop.this,"No flash cards exist! Create one first!", Toast.LENGTH_SHORT).show();
    }
    else {
      FlashCardAdapter listAdapter = new FlashCardAdapter(this, new ArrayList<>(flashCards));
      displayListView.setAdapter(listAdapter);
    }

    displayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String selected = ((TextView) view).getText().toString();
        long selectedId = Long.valueOf(selected);
        String selectedKey = Long.toString(selectedId);

        if(selectedFlashCardId.contains(selectedKey)) {
          selectedFlashCardId.remove(selectedKey);
        }
        else {
          selectedFlashCardId.add(selectedKey);
        }
      }
    });
  }

  public void onBackPresseda(View view) {
    //super.onBackPressed();
    Intent returnData = new Intent();
    returnData.putStringArrayListExtra(getString(R.string.extraKey_cards), selectedFlashCardId);
    setResult(RESULT_OK, returnData);
    finish();
  }
}