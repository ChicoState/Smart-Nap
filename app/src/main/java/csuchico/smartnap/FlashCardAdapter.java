package csuchico.smartnap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

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
        CheckedTextView flashcardTextView = (CheckedTextView)listItemView.findViewById(R.id.flashCardBox);
        String FlashCardName = currentFlashCard.getClassName();
        flashcardTextView.setText(FlashCardName);
        return listItemView;
    }
}
