package csuchico.smartnap;

import com.orm.SugarRecord;

/**
 *Created by gerald on 10/13/17.
 */

public class FlashCard extends SugarRecord<FlashCard>{

    String m_question;
    String m_answer;
    String m_class;

    // Note: Please retain default constructor
    public FlashCard() {
        this.m_question = "";
        this.m_answer = "";
        this.m_class = "";
    }

    public FlashCard (String classn, String question, String answer){
        this.m_question = question;
        this.m_answer = answer;
        this.m_class = classn;
    }
}
