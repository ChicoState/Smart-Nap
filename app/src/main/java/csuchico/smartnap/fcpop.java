package csuchico.smartnap;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

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
        getWindow().setLayout((int)(width*.8),(int)(height*.8));
    }
}
