package csuchico.smartnap;

import com.orm.SugarRecord;

import java.util.List;
import java.util.UUID;

/**
 *Created by gerald on 10/13/17.
 */

public class FlashCard extends SugarRecord<FlashCard>{

  private String key;
  private String className;
  private String question;
  private String answer;

  // Note: Please retain default constructor
  public FlashCard() {
  }

  public FlashCard (String className, String question, String answer){
      this.key = generateKey();
      this.question = question;
      this.answer = answer;
      this.className = className;
  }

  private String generateKey() {
      UUID uuid = UUID.randomUUID();
      return uuid.toString();
  }

  public String getKey() {
    return this.key;
  }

  public String getClassName() {
    return this.className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getQuestion() {
    return this.question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getAnswer() {
    return this.answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public List<AlarmClockFlashCardLinker> getAlarms() {
      return AlarmClockFlashCardLinker.find(
              AlarmClockFlashCardLinker.class,
              "card = ?",
              Long.toString(this.getId())
      );
  }
}
