package csuchico.smartnap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 *Created by gerald on 10/21/2017.
 */

public class SingleListItem extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fc_pop);

        TextView txtProduct = findViewById(R.id.fc_item);

        Intent i = getIntent();
        // getting attached intent data
        String product = i.getStringExtra("FlashCard");
        // displaying selected product name
        txtProduct.setText(product);

    }
}
