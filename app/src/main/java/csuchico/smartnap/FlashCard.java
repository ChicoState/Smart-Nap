package csuchico.smartnap;

import com.orm.SugarRecord;

import java.util.List;

/**
 *Created by gerald on 10/13/17.
 */

public class FlashCard extends SugarRecord<FlashCard>{

  private String className;
  private String question;
  private String answer;

  // Note: Please retain default constructor for database
  public FlashCard() {
  }

  public FlashCard (String className, String question, String answer){
      this.question = question;
      this.answer = answer;
      this.className = className;
  }

  public String getClassName() {
    return this.className;
  }

  public void updatefc(String className, String question, String answer) {
      this.className = className;
      this.question = question;
      this.answer = answer;
  }

  public String getQuestion() {
    return this.question;
  }

  public String getAnswer() {
    return this.answer;
  }

  public List<AlarmClockFlashCardLinker> getAlarms() {
      return AlarmClockFlashCardLinker.find(
              AlarmClockFlashCardLinker.class,"card = ?", Long.toString(this.getId())
      );
  }
}
