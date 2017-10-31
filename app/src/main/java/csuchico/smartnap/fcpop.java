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
  ArrayList<String> selectedFlashCards;

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
    ArrayList<Long> displayList = new ArrayList<>();
    selectedClasses = new ArrayList<>();
    selectedFlashCards = new ArrayList<>();

    if(flashCards.size() == 0) {
      Toast.makeText(fcpop.this,"Database is empty!", Toast.LENGTH_SHORT).show();
    }
    else {
      long i = 1;
      int size = flashCards.size();
      while(size > 1) {
        FlashCard currentCard = FlashCard.findById(FlashCard.class, i);
        displayList.add(currentCard.getId());
        i++;
        size--;
      }
      ListAdapter listAdapter = new ArrayAdapter<>(this, R.layout.checkable_list, displayList);
      displayListView.setAdapter(listAdapter);
    }

    displayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String selected = ((TextView) view).getText().toString();
        long selectedId = Long.valueOf(selected);
        FlashCard card = FlashCard.findById(FlashCard.class,selectedId);
        String selectedKey = card.getKey();
        if(selectedFlashCards.contains(selectedKey)) {
          selectedFlashCards.remove(selectedKey);
        }
        else {
          selectedFlashCards.add(selectedKey);
        }
      }
    });
  }

  public void onBackPresseda(View view) {
    //super.onBackPressed();
    Intent returnData = new Intent();
    returnData.putStringArrayListExtra(getString(R.string.extraKey_cards), selectedFlashCards);
    setResult(RESULT_OK, returnData);
    finish();
  }
}