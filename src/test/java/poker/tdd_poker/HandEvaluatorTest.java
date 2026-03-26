package poker.tdd_poker;



import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class HandEvaluatorTest {

    @Test
    void detectsHighCard() {
        List<Card> hole  = List.of(new Card(Rank.ACE, Suit.SPADES), new Card(Rank.THREE, Suit.DIAMONDS));
        List<Card> board = List.of(
            new Card(Rank.SEVEN, Suit.CLUBS),
            new Card(Rank.NINE, Suit.HEARTS),
            new Card(Rank.TWO, Suit.DIAMONDS),
            new Card(Rank.FIVE, Suit.CLUBS),
            new Card(Rank.JACK, Suit.HEARTS)
        );
        EvaluatedHand result = HandEvaluator.bestHand(hole, board);
        assertEquals(HandRank.HIGH_CARD, result.handRank());
        assertEquals(5, result.chosen5().size());
    }
   
    
}
