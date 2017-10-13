package csuchico.smartnap;

import com.orm.SugarRecord;

/**
 * Created by gerald on 10/13/17.
 */

public class FlashCard extends SugarRecord<FlashCard>{

    private int FC_ID;
    private String m_question;
    private String m_answer;

    public FlashCard (String question, String answer){
        m_question = question;
        m_answer = answer;
    }
}
