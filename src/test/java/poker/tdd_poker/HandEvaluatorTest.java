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
    @Test
void detectsOnePair() {
    List<Card> hole = List.of(
        new Card(Rank.ACE, Suit.SPADES),
        new Card(Rank.ACE, Suit.HEARTS)
    );
    List<Card> board = List.of(
        new Card(Rank.TWO, Suit.CLUBS),
        new Card(Rank.FIVE, Suit.DIAMONDS),
        new Card(Rank.NINE, Suit.HEARTS),
        new Card(Rank.JACK, Suit.CLUBS),
        new Card(Rank.THREE, Suit.DIAMONDS)
    );
    EvaluatedHand result = HandEvaluator.bestHand(hole, board);
    assertEquals(HandRank.ONE_PAIR, result.handRank());
    assertEquals(5, result.chosen5().size());
}

@Test
void detectsTwoPair() {
    List<Card> hole = List.of(
        new Card(Rank.ACE, Suit.SPADES),
        new Card(Rank.ACE, Suit.HEARTS)
    );
    List<Card> board = List.of(
        new Card(Rank.KING, Suit.CLUBS),
        new Card(Rank.KING, Suit.DIAMONDS),
        new Card(Rank.NINE, Suit.HEARTS),
        new Card(Rank.JACK, Suit.CLUBS),
        new Card(Rank.THREE, Suit.DIAMONDS)
    );
    EvaluatedHand result = HandEvaluator.bestHand(hole, board);
    assertEquals(HandRank.TWO_PAIR, result.handRank());
    assertEquals(5, result.chosen5().size());
}

@Test
void detectsThreeOfAKind() {
    List<Card> hole = List.of(
        new Card(Rank.ACE, Suit.SPADES),
        new Card(Rank.ACE, Suit.HEARTS)
    );
    List<Card> board = List.of(
        new Card(Rank.ACE, Suit.CLUBS),
        new Card(Rank.KING, Suit.DIAMONDS),
        new Card(Rank.NINE, Suit.HEARTS),
        new Card(Rank.JACK, Suit.CLUBS),
        new Card(Rank.THREE, Suit.DIAMONDS)
    );
    EvaluatedHand result = HandEvaluator.bestHand(hole, board);
    assertEquals(HandRank.THREE_OF_A_KIND, result.handRank());
    assertEquals(5, result.chosen5().size());
}

@Test
void detectsStraight() {
    List<Card> hole = List.of(
        new Card(Rank.TEN, Suit.SPADES),
        new Card(Rank.NINE, Suit.HEARTS)
    );
    List<Card> board = List.of(
        new Card(Rank.EIGHT, Suit.CLUBS),
        new Card(Rank.SEVEN, Suit.DIAMONDS),
        new Card(Rank.SIX, Suit.HEARTS),
        new Card(Rank.TWO, Suit.CLUBS),
        new Card(Rank.THREE, Suit.DIAMONDS)
    );
    EvaluatedHand result = HandEvaluator.bestHand(hole, board);
    assertEquals(HandRank.STRAIGHT, result.handRank());
    assertEquals(5, result.chosen5().size());
}

@Test
void detectsWheelStraight() {
    List<Card> hole = List.of(
        new Card(Rank.FIVE, Suit.CLUBS),
        new Card(Rank.KING, Suit.DIAMONDS)
    );
    List<Card> board = List.of(
        new Card(Rank.ACE, Suit.CLUBS),
        new Card(Rank.TWO, Suit.DIAMONDS),
        new Card(Rank.THREE, Suit.HEARTS),
        new Card(Rank.FOUR, Suit.SPADES),
        new Card(Rank.NINE, Suit.DIAMONDS)
    );
    EvaluatedHand result = HandEvaluator.bestHand(hole, board);
    assertEquals(HandRank.STRAIGHT, result.handRank());
    // chosen5 doit être 5,4,3,2,A
    assertEquals(Rank.FIVE, result.chosen5().get(0).rank());
    assertEquals(Rank.ACE, result.chosen5().get(4).rank());
}

@Test
void detectsFlush() {
    List<Card> hole = List.of(
        new Card(Rank.SIX, Suit.HEARTS),
        new Card(Rank.KING, Suit.DIAMONDS)
    );
    List<Card> board = List.of(
        new Card(Rank.ACE, Suit.HEARTS),
        new Card(Rank.JACK, Suit.HEARTS),
        new Card(Rank.NINE, Suit.HEARTS),
        new Card(Rank.FOUR, Suit.HEARTS),
        new Card(Rank.TWO, Suit.CLUBS)
    );
    EvaluatedHand result = HandEvaluator.bestHand(hole, board);
    assertEquals(HandRank.FLUSH, result.handRank());
    // chosen5 doit être A,J,9,6,4 of hearts (pas le 2C)
    assertEquals(5, result.chosen5().size());
    assertEquals(Rank.ACE, result.chosen5().get(0).rank());
}

@Test
void detectsFullHouse() {
    List<Card> hole = List.of(
        new Card(Rank.ACE, Suit.SPADES),
        new Card(Rank.ACE, Suit.HEARTS)
    );
    List<Card> board = List.of(
        new Card(Rank.ACE, Suit.CLUBS),
        new Card(Rank.KING, Suit.DIAMONDS),
        new Card(Rank.KING, Suit.HEARTS),
        new Card(Rank.TWO, Suit.CLUBS),
        new Card(Rank.THREE, Suit.DIAMONDS)
    );
    EvaluatedHand result = HandEvaluator.bestHand(hole, board);
    assertEquals(HandRank.FULL_HOUSE, result.handRank());
    assertEquals(5, result.chosen5().size());
}

@Test
void detectsFourOfAKind() {
    List<Card> hole = List.of(
        new Card(Rank.ACE, Suit.SPADES),
        new Card(Rank.ACE, Suit.HEARTS)
    );
    List<Card> board = List.of(
        new Card(Rank.ACE, Suit.CLUBS),
        new Card(Rank.ACE, Suit.DIAMONDS),
        new Card(Rank.KING, Suit.HEARTS),
        new Card(Rank.TWO, Suit.CLUBS),
        new Card(Rank.THREE, Suit.DIAMONDS)
    );
    EvaluatedHand result = HandEvaluator.bestHand(hole, board);
    assertEquals(HandRank.FOUR_OF_A_KIND, result.handRank());
    assertEquals(5, result.chosen5().size());
}

@Test
void detectsStraightFlush() {
    List<Card> hole = List.of(
        new Card(Rank.TEN, Suit.HEARTS),
        new Card(Rank.NINE, Suit.HEARTS)
    );
    List<Card> board = List.of(
        new Card(Rank.EIGHT, Suit.HEARTS),
        new Card(Rank.SEVEN, Suit.HEARTS),
        new Card(Rank.SIX, Suit.HEARTS),
        new Card(Rank.TWO, Suit.CLUBS),
        new Card(Rank.THREE, Suit.DIAMONDS)
    );
    EvaluatedHand result = HandEvaluator.bestHand(hole, board);
    assertEquals(HandRank.STRAIGHT_FLUSH, result.handRank());
    assertEquals(5, result.chosen5().size());
}

    
}
