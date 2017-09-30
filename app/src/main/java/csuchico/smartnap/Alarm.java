package csuchico.smartnap;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.util.Log;

// API-24 required for 'android.icu.util.Calendar', use 'java.util.Calendar' for older API
//import android.icu.util.Calendar;
import java.util.Calendar;

public class Alarm extends AppCompatActivity {

    AlarmManager alarmManager;
    private PendingIntent servicePendingIntent;
    private TimePicker alarmTimePicker;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    /*
        Function:   createNewalarm(View)
        Operation:  Takes the information provided by user on the Alarm activity and creates
                    a new alarm with the Alarm Manager.
        Called:     When user pushes the "Create Alarm" button on the Alarm activity
     */
    public void createNewAlarm(View view) {

        // Setup calendar based on the current time chosen by the user
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
        calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

        // Make a new intent for the broadcast
        // Intent d = new Intent("csuchico.smartnap.AlarmDialog");
        // pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, d, Intent.FLAG_ACTIVITY_NEW_TASK);
        long alarmTime = calendar.getTimeInMillis();

        Intent receiverIntent = new Intent(Alarm.this, AlarmReceiver.class);

        // broadcast myIntent to pendingIntent
        servicePendingIntent = PendingIntent.getBroadcast(Alarm.this, 0, receiverIntent, 0);

        // sets the alarm up using our pendingIntent operation defined to retrieve broadcasts
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, servicePendingIntent);
        Log.d("Alarm", "Setting alarm in AlarmManager.");

        finish();
    } // createNewAlarm()

}
