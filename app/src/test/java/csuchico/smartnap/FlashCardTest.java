package csuchico.smartnap;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FlashCardTest {
    @Test
    public void getClassName() throws Exception {
        FlashCard test = new FlashCard("classtest", "classquestion", "classanswer");
        assertEquals("classtest", test.getClassName());
    }

    @Test
    public void setClassName() throws Exception {
        FlashCard test = new FlashCard("classtest", "classquestion", "classanswer");
        test.updatefc("newclassname", "classquestion", "classanswer");
        assertEquals("newclassname", test.getClassName());
    }

    @Test
    public void getQuestion() throws Exception {
        FlashCard test = new FlashCard("classtest", "classquestion", "classanswer");
        assertEquals("classquestion", test.getQuestion());
    }

    @Test
    public void setQuestion() throws Exception {
        FlashCard test = new FlashCard("classtest", "classquestion", "classanswer");
        test.updatefc("classtest","newquestion","classanswer");
        assertEquals("newquestion", test.getQuestion());
    }

    @Test
    public void getAnswer() throws Exception {
        FlashCard test = new FlashCard("classtest", "classquestion", "classanswer");
        assertEquals("classanswer", test.getAnswer());
    }

    @Test
    public void setAnswer() throws Exception {
        FlashCard test = new FlashCard("classtest", "classquestion", "classanswer");
        test.updatefc("classtests", "classquestion","newanswer");
        assertEquals("newanswer", test.getAnswer());
    }

}