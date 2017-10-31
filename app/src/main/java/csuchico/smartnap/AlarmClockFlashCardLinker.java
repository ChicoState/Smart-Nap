package csuchico.smartnap;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by caleb on 10/30/17.
 */

public class AlarmClockFlashCardLinker extends SugarRecord<AlarmClockFlashCardLinker> {

  AlarmClock alarm;
  FlashCard card;

  // Please retain default constructor
  public AlarmClockFlashCardLinker() {

  }

  public AlarmClockFlashCardLinker(AlarmClock alarm, FlashCard card) {
    this.alarm = alarm;
    this.card = card;
  }
}
