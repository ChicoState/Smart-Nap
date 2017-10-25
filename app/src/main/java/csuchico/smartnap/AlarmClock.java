package csuchico.smartnap;

import android.util.Log;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.List;

public class AlarmClock extends SugarRecord<AlarmClock> {

  private long time;
  private String name;
  private List<FlashCard> cards;


  @Ignore // do not store in database
  private int CARD_LIST_INDEX;

  // Note: Please retain default constructor
  public AlarmClock() {
  }

  // Constructor
  public AlarmClock(long time, String name, List<FlashCard> cards) {
    this.time = time;
    this.name = name;
    this.cards = cards;

    this.CARD_LIST_INDEX = 0; // default INDEX upon alarm construction
  }

  // All of the following functions are used to manage the list of cards attached to alarm

  public void resetIndex() {
    this.CARD_LIST_INDEX = 0;
  }

  public void clearCards() {
    cards.clear();
    this.CARD_LIST_INDEX = 0;
  }

  public void pushCard(FlashCard card) {
    try {
      this.cards.add(card);
    }
    catch ( NullPointerException npe ) {
      Log.w("AlarmClock","Cannot add null card to list!");
      npe.printStackTrace();
    }
  }

  public void pushCard(int index, FlashCard card) {
    try {
      this.cards.add(index, card);
    }
    catch ( NullPointerException npe ) {
      Log.w("AlarmClock","Cannot add null card to list!");
      npe.printStackTrace();
    }
    catch ( IndexOutOfBoundsException ie ) {
      Log.w("AlarmClock","Index does not exist in list!");
      ie.printStackTrace();
    }
  }

  public void popCard(int index) {
    try {
      this.cards.remove(index);
    }
    catch ( IndexOutOfBoundsException ie ) {
      Log.w("AlarmClock","Index does not exist in list!");
      ie.printStackTrace();
    }
  }

  public List<FlashCard> getList() { return cards; }

  public String getName() {
    return name;
  }

  public long getTime() { return time; }

  public void setCards(List<FlashCard> list) { this.cards = list; }

  public void setName(String name) {
    this.name = name;
  }

  public void setTime(long time) {
    this.time = time;
  }

  // The following functions getNextCard() and getPrevCard() are designed to be used
  // to flip through flash cards while on the alarm dialog page.

  /*
      @function     getNextCard()
      @desc         Grabs the card in the list at CARD_LIST_INDEX and then increments index. Will
                    return null value if cards list is empty.
   */
  public FlashCard getNextCard() {
    if ( this.cards.isEmpty() ) {
      Log.w("AlarmClock","This alarms list of flash cards returned empty!");
      return null;
    }
    FlashCard nextCard = this.cards.get(this.CARD_LIST_INDEX);
    this.CARD_LIST_INDEX++;
    if(this.CARD_LIST_INDEX == this.cards.size()) {
      this.resetIndex();
    }
    return nextCard;
  } // getNextCard()

  /*
      @function     getPrevCard()
      @desc         Decrements the CARD_LIST_INDEX and then returns the card in list. Will
                    return null value if cards list is empty.
 */
  public FlashCard getPrevCard() {
    if ( this.cards.isEmpty() ) {
      Log.w("AlarmClock","This alarms list of flash cards returned empty!");
      return null;
    }
    this.CARD_LIST_INDEX--;
    if ( CARD_LIST_INDEX < 0 ) {
      this.CARD_LIST_INDEX = this.cards.size() - 1;
    }
    return this.cards.get(this.CARD_LIST_INDEX);
  } // getPrevCard()

} // AlarmClock
