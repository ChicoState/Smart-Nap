package csuchico.smartnap;

import android.app.AlarmManager;

import com.orm.SugarRecord;

/**
 * Created by caleb on 10/12/17.
 */

public class AlarmClock extends SugarRecord<AlarmClock> {

    long m_alarmTime;
    String  m_alarmName;
    FlashCard m_flashCard;

    // Note: Please retain default constructor
    public AlarmClock() {
    }

    // Constructor
    public AlarmClock(long time, String name, FlashCard card) {
        this.m_alarmTime = time;
        this.m_alarmName = name;
        this.m_flashCard = card;
    }
}
