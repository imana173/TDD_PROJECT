package poker.tdd_poker;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class HandEvaluatorTest {

    @Test
    void detectsHighCard() {
        List<Card> hole = List.of(new Card(Rank.ACE, Suit.SPADES), new Card(Rank.THREE, Suit.DIAMONDS));
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
        List<Card> hole = List.of(new Card(Rank.ACE, Suit.SPADES), new Card(Rank.ACE, Suit.HEARTS));
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
        List<Card> hole = List.of(new Card(Rank.ACE, Suit.SPADES), new Card(Rank.ACE, Suit.HEARTS));
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
        List<Card> hole = List.of(new Card(Rank.ACE, Suit.SPADES), new Card(Rank.ACE, Suit.HEARTS));
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
        List<Card> hole = List.of(new Card(Rank.TEN, Suit.SPADES), new Card(Rank.NINE, Suit.HEARTS));
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
    void detectsFlush() {
        List<Card> hole = List.of(new Card(Rank.SIX, Suit.HEARTS), new Card(Rank.KING, Suit.DIAMONDS));
        List<Card> board = List.of(
            new Card(Rank.ACE, Suit.HEARTS),
            new Card(Rank.JACK, Suit.HEARTS),
            new Card(Rank.NINE, Suit.HEARTS),
            new Card(Rank.FOUR, Suit.HEARTS),
            new Card(Rank.TWO, Suit.CLUBS)
        );
        EvaluatedHand result = HandEvaluator.bestHand(hole, board);
        assertEquals(HandRank.FLUSH, result.handRank());
        assertEquals(5, result.chosen5().size());
    }

    @Test
    void detectsFullHouse() {
        List<Card> hole = List.of(new Card(Rank.ACE, Suit.SPADES), new Card(Rank.ACE, Suit.HEARTS));
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
        List<Card> hole = List.of(new Card(Rank.ACE, Suit.SPADES), new Card(Rank.ACE, Suit.HEARTS));
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
        List<Card> hole = List.of(new Card(Rank.TEN, Suit.HEARTS), new Card(Rank.NINE, Suit.HEARTS));
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

    @Test
    void comparePlayers_shouldReturnWinner() {
        List<List<Card>> players = List.of(
            List.of(new Card(Rank.ACE, Suit.SPADES), new Card(Rank.ACE, Suit.HEARTS)),
            List.of(new Card(Rank.KING, Suit.SPADES), new Card(Rank.KING, Suit.HEARTS))
        );
        List<Card> board = List.of(
            new Card(Rank.TWO, Suit.CLUBS),
            new Card(Rank.THREE, Suit.DIAMONDS),
            new Card(Rank.FOUR, Suit.HEARTS),
            new Card(Rank.FIVE, Suit.CLUBS),
            new Card(Rank.NINE, Suit.DIAMONDS)
        );
        GameResults result = HandEvaluator.compare(players, board);
        assertEquals(List.of(0), result.winners());
    }

    @Test
    void shouldHandleTie() {
        List<List<Card>> players = List.of(
            List.of(new Card(Rank.ACE, Suit.SPADES), new Card(Rank.KING, Suit.HEARTS)),
            List.of(new Card(Rank.QUEEN, Suit.SPADES), new Card(Rank.JACK, Suit.HEARTS))
        );
        List<Card> board = List.of(
            new Card(Rank.FIVE, Suit.CLUBS),
            new Card(Rank.SIX, Suit.DIAMONDS),
            new Card(Rank.SEVEN, Suit.HEARTS),
            new Card(Rank.EIGHT, Suit.SPADES),
            new Card(Rank.NINE, Suit.DIAMONDS)
        );
        GameResults result = HandEvaluator.compare(players, board);
        assertTrue(result.isSplit());
    }

    @Test
    void boardPlays() {
        List<List<Card>> players = List.of(
            List.of(new Card(Rank.ACE, Suit.SPADES), new Card(Rank.ACE, Suit.HEARTS)),
            List.of(new Card(Rank.KING, Suit.SPADES), new Card(Rank.QUEEN, Suit.HEARTS))
        );
        List<Card> board = List.of(
            new Card(Rank.FIVE, Suit.CLUBS),
            new Card(Rank.SIX, Suit.DIAMONDS),
            new Card(Rank.SEVEN, Suit.HEARTS),
            new Card(Rank.EIGHT, Suit.SPADES),
            new Card(Rank.NINE, Suit.DIAMONDS)
        );
        GameResults result = HandEvaluator.compare(players, board);
        assertTrue(result.isSplit());
    }

    // ---- Exemples de l'énoncé ----

    @Test
    void exampleA_wheelStraight() {
        List<Card> hole = List.of(new Card(Rank.FIVE, Suit.CLUBS), new Card(Rank.KING, Suit.DIAMONDS));
        List<Card> board = List.of(
            new Card(Rank.ACE, Suit.CLUBS),
            new Card(Rank.TWO, Suit.DIAMONDS),
            new Card(Rank.THREE, Suit.HEARTS),
            new Card(Rank.FOUR, Suit.SPADES),
            new Card(Rank.NINE, Suit.DIAMONDS)
        );
        EvaluatedHand result = HandEvaluator.bestHand(hole, board);
        assertEquals(HandRank.STRAIGHT, result.handRank());
        assertEquals(Rank.FIVE, result.chosen5().get(0).rank());
        assertEquals(Rank.ACE, result.chosen5().get(4).rank());
    }

    @Test
    void exampleB_aceHighStraight() {
        List<Card> hole = List.of(new Card(Rank.ACE, Suit.CLUBS), new Card(Rank.THREE, Suit.DIAMONDS));
        List<Card> board = List.of(
            new Card(Rank.TEN, Suit.CLUBS),
            new Card(Rank.JACK, Suit.DIAMONDS),
            new Card(Rank.QUEEN, Suit.HEARTS),
            new Card(Rank.KING, Suit.SPADES),
            new Card(Rank.TWO, Suit.DIAMONDS)
        );
        EvaluatedHand result = HandEvaluator.bestHand(hole, board);
        assertEquals(HandRank.STRAIGHT, result.handRank());
        assertEquals(Rank.ACE, result.chosen5().get(0).rank());
        assertEquals(Rank.TEN, result.chosen5().get(4).rank());
    }

    @Test
    void exampleC_flushBestFiveFromSix() {
        List<Card> hole = List.of(new Card(Rank.SIX, Suit.HEARTS), new Card(Rank.KING, Suit.DIAMONDS));
        List<Card> board = List.of(
            new Card(Rank.ACE, Suit.HEARTS),
            new Card(Rank.JACK, Suit.HEARTS),
            new Card(Rank.NINE, Suit.HEARTS),
            new Card(Rank.FOUR, Suit.HEARTS),
            new Card(Rank.TWO, Suit.CLUBS)
        );
        EvaluatedHand result = HandEvaluator.bestHand(hole, board);
        assertEquals(HandRank.FLUSH, result.handRank());
        assertEquals(Rank.ACE, result.chosen5().get(0).rank());
        assertEquals(Rank.JACK, result.chosen5().get(1).rank());
        assertEquals(Rank.NINE, result.chosen5().get(2).rank());
        assertEquals(Rank.SIX, result.chosen5().get(3).rank());
        assertEquals(Rank.FOUR, result.chosen5().get(4).rank());
        assertTrue(result.chosen5().stream().allMatch(c -> c.suit() == Suit.HEARTS));
    }

    @Test
    void exampleD_boardPlaysTie() {
        List<List<Card>> players = List.of(
            List.of(new Card(Rank.ACE, Suit.CLUBS), new Card(Rank.ACE, Suit.DIAMONDS)),
            List.of(new Card(Rank.KING, Suit.CLUBS), new Card(Rank.QUEEN, Suit.DIAMONDS))
        );
        List<Card> board = List.of(
            new Card(Rank.FIVE, Suit.CLUBS),
            new Card(Rank.SIX, Suit.DIAMONDS),
            new Card(Rank.SEVEN, Suit.HEARTS),
            new Card(Rank.EIGHT, Suit.SPADES),
            new Card(Rank.NINE, Suit.DIAMONDS)
        );
        GameResults result = HandEvaluator.compare(players, board);
        assertTrue(result.isSplit());
        assertEquals(2, result.winners().size());
    }

    @Test
    void exampleE_quadsKickerDecides() {
        List<List<Card>> players = List.of(
            List.of(new Card(Rank.ACE, Suit.CLUBS), new Card(Rank.KING, Suit.CLUBS)),
            List.of(new Card(Rank.QUEEN, Suit.CLUBS), new Card(Rank.JACK, Suit.CLUBS))
        );
        List<Card> board = List.of(
            new Card(Rank.SEVEN, Suit.CLUBS),
            new Card(Rank.SEVEN, Suit.DIAMONDS),
            new Card(Rank.SEVEN, Suit.HEARTS),
            new Card(Rank.SEVEN, Suit.SPADES),
            new Card(Rank.TWO, Suit.DIAMONDS)
        );
        GameResults result = HandEvaluator.compare(players, board);
        assertFalse(result.isSplit());
        assertEquals(List.of(0), result.winners());
    }

    // ---- Tie-breaks ----

    @Test
    void tieBreak_twoPair_highPairDecides() {
        List<List<Card>> players = List.of(
            List.of(new Card(Rank.ACE, Suit.SPADES), new Card(Rank.ACE, Suit.HEARTS)),
            List.of(new Card(Rank.KING, Suit.SPADES), new Card(Rank.KING, Suit.HEARTS))
        );
        List<Card> board = List.of(
            new Card(Rank.TWO, Suit.CLUBS),
            new Card(Rank.TWO, Suit.DIAMONDS),
            new Card(Rank.NINE, Suit.HEARTS),
            new Card(Rank.JACK, Suit.CLUBS),
            new Card(Rank.THREE, Suit.DIAMONDS)
        );
        GameResults result = HandEvaluator.compare(players, board);
        assertFalse(result.isSplit());
        assertEquals(List.of(0), result.winners());
    }

 @Test
void tieBreak_fullHouse_tripletDecides() {
    List<List<Card>> players = List.of(
        List.of(new Card(Rank.ACE, Suit.SPADES), new Card(Rank.ACE, Suit.HEARTS)),
        List.of(new Card(Rank.KING, Suit.SPADES), new Card(Rank.KING, Suit.HEARTS))
    );
    List<Card> board = List.of(
        new Card(Rank.ACE, Suit.CLUBS),
        new Card(Rank.KING, Suit.CLUBS),
        new Card(Rank.JACK, Suit.CLUBS),
        new Card(Rank.JACK, Suit.DIAMONDS),
        new Card(Rank.THREE, Suit.DIAMONDS)
    );
    GameResults result = HandEvaluator.compare(players, board);
    assertFalse(result.isSplit());
    assertEquals(List.of(0), result.winners()); // AAA-JJ > KKK-JJ
}

   @Test
void tieBreak_fourOfAKind_kickerDecides() {
    List<List<Card>> players = List.of(
        List.of(new Card(Rank.ACE, Suit.CLUBS), new Card(Rank.THREE, Suit.HEARTS)),
        List.of(new Card(Rank.KING, Suit.CLUBS), new Card(Rank.THREE, Suit.DIAMONDS))
    );
    List<Card> board = List.of(
        new Card(Rank.SEVEN, Suit.CLUBS),
        new Card(Rank.SEVEN, Suit.DIAMONDS),
        new Card(Rank.SEVEN, Suit.HEARTS),
        new Card(Rank.SEVEN, Suit.SPADES),
        new Card(Rank.TWO, Suit.DIAMONDS)
    );
    GameResults result = HandEvaluator.compare(players, board);
    assertFalse(result.isSplit());
    assertEquals(List.of(0), result.winners()); // A kicker > K kicker
}

    @Test
    void tieBreak_straight_highCardDecides() {
        List<List<Card>> players = List.of(
            List.of(new Card(Rank.TEN, Suit.SPADES), new Card(Rank.SIX, Suit.CLUBS)),
            List.of(new Card(Rank.NINE, Suit.SPADES), new Card(Rank.FIVE, Suit.CLUBS))
        );
        List<Card> board = List.of(
            new Card(Rank.SEVEN, Suit.CLUBS),
            new Card(Rank.EIGHT, Suit.DIAMONDS),
            new Card(Rank.NINE, Suit.HEARTS),
            new Card(Rank.TWO, Suit.CLUBS),
            new Card(Rank.THREE, Suit.DIAMONDS)
        );
        GameResults result = HandEvaluator.compare(players, board);
        assertFalse(result.isSplit());
        assertEquals(List.of(0), result.winners());
    }

    @Test
    void tieBreak_flush_highCardDecides() {
        List<List<Card>> players = List.of(
            List.of(new Card(Rank.ACE, Suit.HEARTS), new Card(Rank.TWO, Suit.CLUBS)),
            List.of(new Card(Rank.KING, Suit.HEARTS), new Card(Rank.THREE, Suit.CLUBS))
        );
        List<Card> board = List.of(
            new Card(Rank.JACK, Suit.HEARTS),
            new Card(Rank.NINE, Suit.HEARTS),
            new Card(Rank.SEVEN, Suit.HEARTS),
            new Card(Rank.FOUR, Suit.HEARTS),
            new Card(Rank.TWO, Suit.DIAMONDS)
        );
        GameResults result = HandEvaluator.compare(players, board);
        assertFalse(result.isSplit());
        assertEquals(List.of(0), result.winners());
    }

    @Test
    void tieBreak_onePair_kickerDecides() {
        List<List<Card>> players = List.of(
            List.of(new Card(Rank.ACE, Suit.SPADES), new Card(Rank.KING, Suit.CLUBS)),
            List.of(new Card(Rank.ACE, Suit.HEARTS), new Card(Rank.QUEEN, Suit.CLUBS))
        );
        List<Card> board = List.of(
            new Card(Rank.ACE, Suit.DIAMONDS),
            new Card(Rank.FOUR, Suit.CLUBS),
            new Card(Rank.FIVE, Suit.HEARTS),
            new Card(Rank.SIX, Suit.DIAMONDS),
            new Card(Rank.TWO, Suit.CLUBS)
        );
        GameResults result = HandEvaluator.compare(players, board);
        assertFalse(result.isSplit());
        assertEquals(List.of(0), result.winners());
    }

    @Test
    void tieBreak_highCard_decidesWinner() {
        List<List<Card>> players = List.of(
            List.of(new Card(Rank.ACE, Suit.SPADES), new Card(Rank.TWO, Suit.CLUBS)),
            List.of(new Card(Rank.KING, Suit.HEARTS), new Card(Rank.THREE, Suit.CLUBS))
        );
        List<Card> board = List.of(
            new Card(Rank.FOUR, Suit.CLUBS),
            new Card(Rank.SIX, Suit.DIAMONDS),
            new Card(Rank.EIGHT, Suit.HEARTS),
            new Card(Rank.TEN, Suit.SPADES),
            new Card(Rank.QUEEN, Suit.DIAMONDS)
        );
        GameResults result = HandEvaluator.compare(players, board);
        assertFalse(result.isSplit());
        assertEquals(List.of(0), result.winners());
    }

    @Test
    void tieBreak_threeOfAKind_kickersDecide() {
        List<List<Card>> players = List.of(
            List.of(new Card(Rank.ACE, Suit.SPADES), new Card(Rank.KING, Suit.CLUBS)),
            List.of(new Card(Rank.ACE, Suit.HEARTS), new Card(Rank.QUEEN, Suit.CLUBS))
        );
        List<Card> board = List.of(
            new Card(Rank.ACE, Suit.DIAMONDS),
            new Card(Rank.ACE, Suit.CLUBS),
            new Card(Rank.FIVE, Suit.HEARTS),
            new Card(Rank.SIX, Suit.DIAMONDS),
            new Card(Rank.TWO, Suit.CLUBS)
        );
        GameResults result = HandEvaluator.compare(players, board);
        assertFalse(result.isSplit());
        assertEquals(List.of(0), result.winners());
    }
}
