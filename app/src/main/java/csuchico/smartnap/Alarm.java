package csuchico.smartnap;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.EditText;
import android.util.Log;

public class Alarm extends AppCompatActivity {

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private static Alarm inst;
    private EditText alarmInputText;

    public static Alarm instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        Button buttonCreateAlarm = (Button) findViewById(R.id.buttonCreateAlarm);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public void onToggleClicked(View view) {

        long curTime = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
        calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

        long setTime = calendar.getTimeInMillis();
        long time = curTime - setTime;
        Log.d("Alarm", "current variable setTime: " + setTime);
        Log.d("Alarm", "current variable curTime: " + curTime);

        Intent myIntent = new Intent(Alarm.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(Alarm.this, 0, myIntent, 0);
        alarmManager.set(AlarmManager.RTC, setTime, pendingIntent);
        Log.d("Alarm", "Passing time into AlarmManager: " + setTime);

        finish();
    }

}
