# Texas Hold'em Hand Evaluator — TDD Exam 2026

## Description
A Texas Hold'em poker hand evaluator built with TDD in Java 17 + JUnit 5.
Given 5 community cards and 2 hole cards per player, it determines the best 5-card hand, compares all players and returns the winner(s).

## How to Run Tests
```bash
mvn test
```

## Project Structure
```
src/
├── main/java/poker/tdd_poker/
│   ├── Card.java           # Card model (rank + suit)
│   ├── Rank.java           # Rank enum (TWO to ACE)
│   ├── Suit.java           # Suit enum (CLUBS, DIAMONDS, HEARTS, SPADES)
│   ├── HandRank.java       # Hand category enum (HIGH_CARD to STRAIGHT_FLUSH)
│   ├── EvaluatedHand.java  # Result: category + chosen5 + tiebreakers
│   ├── HandEvaluator.java  # Core logic: evaluate + compare
│   └── GameResults.java    # Winners list + isSplit()
└── test/java/poker/tdd_poker/
    └── HandEvaluatorTest.java
```

## Hand Categories (highest to lowest)
1. Straight Flush
2. Four of a Kind
3. Full House
4. Flush
5. Straight
6. Three of a Kind
7. Two Pair
8. One Pair
9. High Card

## chosen5 Ordering
| Category | Order |
|---|---|
| STRAIGHT_FLUSH / STRAIGHT | Highest to lowest (wheel: 5,4,3,2,A) |
| FOUR_OF_A_KIND | Quad cards then kicker |
| FULL_HOUSE | Triplet cards then pair cards |
| FLUSH / HIGH_CARD | Descending rank order |
| THREE_OF_A_KIND | Triplet then kickers descending |
| TWO_PAIR | High pair, low pair, kicker |
| ONE_PAIR | Pair cards then kickers descending |

## Input Validity
We assume no duplicate cards in input. No validation is performed.

## Out of Scope
Betting, blinds, side pots, jokers, suit-based tie-breaking.

