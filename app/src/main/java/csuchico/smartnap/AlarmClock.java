package csuchico.smartnap;

import android.app.AlarmManager;

import com.orm.SugarRecord;

/**
 * Created by caleb on 10/12/17.
 */

public class AlarmClock extends SugarRecord<AlarmClock> {
    
    long alarmTime;
    String  alarmName,

    // Note: Please retain default constructor
    public AlarmClock() {
    }

    // Constructor
    public AlarmClock(long time, String name) {
        this.alarmTime = time;
        this.alarmName = name;
    }

}
