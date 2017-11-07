package csuchico.smartnap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *Created by gerald on 11/6/2017.
 */

public class FlashCardAdapter extends ArrayAdapter<FlashCard> {
    public FlashCardAdapter(Activity context, ArrayList<FlashCard> flashCards) {super(context, 0, flashCards);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.checkable_list, parent, false);
        }

        final FlashCard currentFlashCard = this.getItem(position);

        TextView flashcardTextView = listItemView.findViewById(R.id.flashCardBox);
        //Button alarmEdit = (Button) listItemView.findViewById(R.id.toggle_view);

        String FlashCardName = currentFlashCard.getClassName();

        flashcardTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String key = getString(R.string.extraKey_alarm);
                Intent intent = new Intent(getContext(),AlarmQuestions.class);
                Bundle data = new Bundle();
                long FlashCardId = currentFlashCard.getId();
                data.putLong("extraKey_flashcard",FlashCardId);
                intent.putExtras(data);
                view.getContext().startActivity(intent);
            }
        });

        flashcardTextView.setText(FlashCardName);

        return listItemView;
    }
}
