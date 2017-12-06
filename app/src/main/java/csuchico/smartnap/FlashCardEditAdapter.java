package csuchico.smartnap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *Created by gerald on 11/20/2017.
 */

public class FlashCardEditAdapter extends ArrayAdapter<FlashCard> {
    public FlashCardEditAdapter(Activity context, ArrayList<FlashCard> flashCards){super(context, 0, flashCards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.editflashcard, parent, false);
        }
        final FlashCard currentFlashCard = this.getItem(position);
        // java.lang.ClassCastException: android.support.v7.widget.AppCompatTextView cannot be cast to android.widget.CheckedTextView
        // Cannot cast a TEXTVIEW into a CheckedTEXTVIEW
        TextView flashcardTextView = (TextView)listItemView.findViewById(R.id.flashCardBox);
        String FlashCardName = currentFlashCard.getClassName();
        flashcardTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String key = getString(R.string.extraKey_alarm);
                Intent intent = new Intent(getContext(),AlarmQuestions.class);
                Bundle data = new Bundle();
                long fcId = currentFlashCard.getId();
                data.putLong("extraKey_alarm",fcId);
                intent.putExtras(data);
                view.getContext().startActivity(intent);
            }
        });
        flashcardTextView.setText(FlashCardName);
        return listItemView;
    }
}