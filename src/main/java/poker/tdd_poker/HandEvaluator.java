package poker.tdd_poker;

import java.util.*;
import java.util.stream.Collectors;

public class HandEvaluator {

    public static EvaluatedHand bestHand(List<Card> holeCards, List<Card> board) {
        List<Card> all = new ArrayList<>();
        all.addAll(holeCards);
        all.addAll(board);

        List<List<Card>> combos = combinations(all, 5);

        EvaluatedHand best = null;
        for (List<Card> combo : combos) {
            EvaluatedHand candidate = evaluate(combo);
            if (best == null || candidate.compareTo(best) > 0) {
                best = candidate;
            }
        }
        return best;
    }

    static List<List<Card>> combinations(List<Card> cards, int k) {
        List<List<Card>> result = new ArrayList<>();
        combine(cards, k, 0, new ArrayList<>(), result);
        return result;
    }

    private static void combine(List<Card> cards, int k, int start, List<Card> current, List<List<Card>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < cards.size(); i++) {
            current.add(cards.get(i));
            combine(cards, k, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    static EvaluatedHand evaluate(List<Card> five) {
        if (isStraightFlush(five)) return straightFlush(five);
        if (isFourOfAKind(five)) return fourOfAKind(five);
        if (isFullHouse(five)) return fullHouse(five);
        if (isFlush(five)) return flush(five);
        if (isStraight(five)) return straight(five);
        if (isThreeOfAKind(five)) return threeOfAKind(five);
        if (isTwoPair(five)) return twoPair(five);
        if (isOnePair(five)) return onePair(five);
        return highCard(five);
    }

    public static GameResults compare(List<List<Card>> holeCards, List<Card> board) {
        List<EvaluatedHand> hands = holeCards.stream()
            .map(h -> bestHand(h, board))
            .toList();

        EvaluatedHand best = Collections.max(hands);

        List<Integer> winners = new ArrayList<>();
        for (int i = 0; i < hands.size(); i++) {
            if (hands.get(i).compareTo(best) == 0) {
                winners.add(i);
            }
        }

        return new GameResults(winners, hands);
    }

    // ----------------- DETECTION -----------------

    private static Map<Rank, Long> rankCounts(List<Card> five) {
        return five.stream().collect(Collectors.groupingBy(Card::rank, Collectors.counting()));
    }

    private static boolean isFlush(List<Card> five) {
        return five.stream().map(Card::suit).distinct().count() == 1;
    }

    private static boolean isStraight(List<Card> five) {
        return getStraightHigh(five) != -1;
    }

    private static int getStraightHigh(List<Card> five) {
        List<Integer> values = five.stream()
            .map(c -> c.rank().value)
            .distinct()
            .sorted((a, b) -> b - a)
            .toList();

        if (values.size() < 5) return -1;

        if (values.get(0) - values.get(4) == 4) return values.get(0);

        if (values.equals(List.of(14, 5, 4, 3, 2))) return 5;

        return -1;
    }

    private static boolean isStraightFlush(List<Card> five) {
        return isFlush(five) && isStraight(five);
    }

    private static boolean isFourOfAKind(List<Card> five) {
        return rankCounts(five).containsValue(4L);
    }

    private static boolean isFullHouse(List<Card> five) {
        Map<Rank, Long> counts = rankCounts(five);
        return counts.containsValue(3L) && counts.containsValue(2L);
    }

    private static boolean isThreeOfAKind(List<Card> five) {
        Map<Rank, Long> counts = rankCounts(five);
        return counts.containsValue(3L) && !counts.containsValue(2L);
    }

    private static boolean isTwoPair(List<Card> five) {
        return rankCounts(five).values().stream().filter(v -> v == 2L).count() == 2;
    }

    private static boolean isOnePair(List<Card> five) {
        return rankCounts(five).values().stream().filter(v -> v == 2L).count() == 1
            && !rankCounts(five).containsValue(3L);
    }

    // ----------------- BUILD -----------------

    private static EvaluatedHand highCard(List<Card> five) {
        List<Card> sorted = five.stream()
            .sorted((a, b) -> b.rank().value - a.rank().value)
            .toList();
        List<Integer> tb = sorted.stream().map(c -> c.rank().value).toList();
        return new EvaluatedHand(HandRank.HIGH_CARD, sorted, tb);
    }

    private static EvaluatedHand onePair(List<Card> five) {
        Map<Rank, Long> counts = rankCounts(five);
        Rank pair = counts.entrySet().stream().filter(e -> e.getValue() == 2).map(Map.Entry::getKey).findFirst().get();

        List<Card> ordered = new ArrayList<>();
        five.stream().filter(c -> c.rank() == pair).forEach(ordered::add);

        List<Card> kickers = five.stream().filter(c -> c.rank() != pair)
            .sorted((a, b) -> b.rank().value - a.rank().value).toList();
        ordered.addAll(kickers);

        List<Integer> tb = new ArrayList<>();
        tb.add(pair.value);
        kickers.forEach(c -> tb.add(c.rank().value));

        return new EvaluatedHand(HandRank.ONE_PAIR, ordered, tb);
    }

    private static EvaluatedHand twoPair(List<Card> five) {
        Map<Rank, Long> counts = rankCounts(five);

        List<Rank> pairs = counts.entrySet().stream()
            .filter(e -> e.getValue() == 2)
            .map(Map.Entry::getKey)
            .sorted((a, b) -> b.value - a.value)
            .toList();

        Rank high = pairs.get(0);
        Rank low = pairs.get(1);
        Rank kicker = counts.entrySet().stream().filter(e -> e.getValue() == 1).map(Map.Entry::getKey).findFirst().get();

        List<Card> ordered = new ArrayList<>();
        five.stream().filter(c -> c.rank() == high).forEach(ordered::add);
        five.stream().filter(c -> c.rank() == low).forEach(ordered::add);
        five.stream().filter(c -> c.rank() == kicker).forEach(ordered::add);

        return new EvaluatedHand(HandRank.TWO_PAIR, ordered, List.of(high.value, low.value, kicker.value));
    }

    private static EvaluatedHand threeOfAKind(List<Card> five) {
        Map<Rank, Long> counts = rankCounts(five);
        Rank triplet = counts.entrySet().stream().filter(e -> e.getValue() == 3).map(Map.Entry::getKey).findFirst().get();

        List<Card> ordered = new ArrayList<>();
        five.stream().filter(c -> c.rank() == triplet).forEach(ordered::add);

        List<Card> kickers = five.stream().filter(c -> c.rank() != triplet)
            .sorted((a, b) -> b.rank().value - a.rank().value).toList();
        ordered.addAll(kickers);

        List<Integer> tb = new ArrayList<>();
        tb.add(triplet.value);
        kickers.forEach(c -> tb.add(c.rank().value));

        return new EvaluatedHand(HandRank.THREE_OF_A_KIND, ordered, tb);
    }

   
    private static EvaluatedHand straight(List<Card> five) {
        int high = getStraightHigh(five);
        List<Card> ordered = orderStraight(five, high);
        return new EvaluatedHand(HandRank.STRAIGHT, ordered, List.of(high));
    }

    private static EvaluatedHand flush(List<Card> five) {
        List<Card> sorted = five.stream()
            .sorted((a, b) -> b.rank().value - a.rank().value)
            .toList();
        List<Integer> tb = sorted.stream().map(c -> c.rank().value).toList();
        return new EvaluatedHand(HandRank.FLUSH, sorted, tb);
    }

    private static EvaluatedHand fullHouse(List<Card> five) {
        Map<Rank, Long> counts = rankCounts(five);
        Rank triplet = counts.entrySet().stream().filter(e -> e.getValue() == 3).map(Map.Entry::getKey).findFirst().get();
        Rank pair = counts.entrySet().stream().filter(e -> e.getValue() == 2).map(Map.Entry::getKey).findFirst().get();

        List<Card> ordered = new ArrayList<>();
        five.stream().filter(c -> c.rank() == triplet).forEach(ordered::add);
        five.stream().filter(c -> c.rank() == pair).forEach(ordered::add);

        return new EvaluatedHand(HandRank.FULL_HOUSE, ordered, List.of(triplet.value, pair.value));
    }

    private static EvaluatedHand fourOfAKind(List<Card> five) {
        Map<Rank, Long> counts = rankCounts(five);
        Rank quad = counts.entrySet().stream().filter(e -> e.getValue() == 4).map(Map.Entry::getKey).findFirst().get();
        Rank kicker = counts.entrySet().stream().filter(e -> e.getValue() == 1).map(Map.Entry::getKey).findFirst().get();

        List<Card> ordered = new ArrayList<>();
        five.stream().filter(c -> c.rank() == quad).forEach(ordered::add);
        five.stream().filter(c -> c.rank() == kicker).forEach(ordered::add);

        return new EvaluatedHand(HandRank.FOUR_OF_A_KIND, ordered, List.of(quad.value, kicker.value));
    }

    
    private static EvaluatedHand straightFlush(List<Card> five) {
        int high = getStraightHigh(five);
        List<Card> ordered = orderStraight(five, high);
        return new EvaluatedHand(HandRank.STRAIGHT_FLUSH, ordered, List.of(high));
    }

    // ordonne le straight correctement (wheel = 5,4,3,2,A)
    private static List<Card> orderStraight(List<Card> five, int high) {
        if (high == 5) {
            List<Card> ordered = new ArrayList<>();
            for (int v : List.of(5, 4, 3, 2)) {
                int fv = v;
                five.stream().filter(c -> c.rank().value == fv).findFirst().ifPresent(ordered::add);
            }
            five.stream().filter(c -> c.rank() == Rank.ACE).findFirst().ifPresent(ordered::add);
            return ordered;
        }
        return five.stream()
            .sorted((a, b) -> b.rank().value - a.rank().value)
            .toList();
    }
}
