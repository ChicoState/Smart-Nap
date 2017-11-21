package csuchico.smartnap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *Created by gerald on 11/20/2017.
 */

public class FlashCardEditAdapter {
    public FlashCardEditAdapter(Activity context, ArrayList<FlashCard> flashCards){super(context, 0, flashCards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.editflashcard, parent, false);
        }

        final FlashCard currentFC = this.getItem(position);

        TextView fcTextView = (TextView) listItemView.findViewById(R.id.flashCardBox);
        fcTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),Question.class);
                Bundle data = new Bundle();
                long flashcardId = currentFC.getId();
                data.putLong("extraKey_flashcard",flashcardId);
                intent.putExtras(data);
                view.getContext().startActivity(intent);
            }
        });
        String FlashCardName = currentFC.getClassName();
        fcTextView.setText(FlashCardName);

        return listItemView;
    }
}