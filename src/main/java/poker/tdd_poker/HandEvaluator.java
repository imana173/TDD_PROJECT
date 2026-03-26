package poker.tdd_poker;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HandEvaluator {

    public static EvaluatedHand bestHand(List<Card> holeCards, List<Card> board) {
        List<Card> all = new ArrayList<>();
        all.addAll(holeCards);
        all.addAll(board);

        // Génère toutes les combinaisons de 5 cartes parmi 7
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
if (isThreeOfAKind(five)) return threeOfAKind(five);
if (isTwoPair(five)) return twoPair(five);
if (isOnePair(five)) return onePair(five);
return highCard(five);
}

    private static EvaluatedHand highCard(List<Card> five) {
        List<Card> sorted = five.stream()
            .sorted((a, b) -> b.rank().value - a.rank().value)
            .toList();
        List<Integer> tieBreakers = sorted.stream()
            .map(c -> c.rank().value)
            .toList();
        return new EvaluatedHand(HandRank.HIGH_CARD, sorted, tieBreakers);
    }

    public static GameResults compare(List<List<Card>> holeCards, List<Card> board) {
        // TODO
        return null;
    }

    private static boolean isOnePair(List<Card> five) {
    return five.stream()
        .collect(java.util.stream.Collectors.groupingBy(Card::rank, java.util.stream.Collectors.counting()))
        .values()
        .stream()
        .filter(count -> count == 2)
        .count() == 1;
}
private static EvaluatedHand onePair(List<Card> five) {

    var counts = five.stream()
        .collect(java.util.stream.Collectors.groupingBy(Card::rank, java.util.stream.Collectors.counting()));

    Rank pair = counts.entrySet().stream()
        .filter(e -> e.getValue() == 2)
        .map(e -> e.getKey())
        .findFirst()
        .get();

    List<Card> kickers = five.stream()
        .filter(c -> c.rank() != pair)
        .sorted((a, b) -> b.rank().value - a.rank().value)
        .toList();

    List<Card> ordered = new ArrayList<>();

    five.stream().filter(c -> c.rank() == pair).forEach(ordered::add);
    ordered.addAll(kickers);

    List<Integer> tb = new ArrayList<>();
    tb.add(pair.value);
    kickers.forEach(c -> tb.add(c.rank().value));

    return new EvaluatedHand(HandRank.ONE_PAIR, ordered, tb);
}

private static boolean isTwoPair(List<Card> five) {
    return five.stream()
        .collect(java.util.stream.Collectors.groupingBy(Card::rank, java.util.stream.Collectors.counting()))
        .values()
        .stream()
        .filter(count -> count == 2)
        .count() == 2;
}

private static EvaluatedHand twoPair(List<Card> five) {

    var counts = five.stream()
        .collect(java.util.stream.Collectors.groupingBy(Card::rank, java.util.stream.Collectors.counting()));

    List<Rank> pairs = counts.entrySet().stream()
        .filter(e -> e.getValue() == 2)
        .map(e -> e.getKey())
        .sorted((a, b) -> b.value - a.value)
        .toList();

    Rank highPair = pairs.get(0);
    Rank lowPair = pairs.get(1);

    Rank kicker = counts.entrySet().stream()
        .filter(e -> e.getValue() == 1)
        .map(e -> e.getKey())
        .findFirst()
        .get();

    List<Card> ordered = new ArrayList<>();

    five.stream().filter(c -> c.rank() == highPair).forEach(ordered::add);
    five.stream().filter(c -> c.rank() == lowPair).forEach(ordered::add);
    five.stream().filter(c -> c.rank() == kicker).forEach(ordered::add);

    return new EvaluatedHand(
        HandRank.TWO_PAIR,
        ordered,
        List.of(highPair.value, lowPair.value, kicker.value)
    );
}

private static boolean isThreeOfAKind(List<Card> five) {
    return five.stream()
        .collect(java.util.stream.Collectors.groupingBy(Card::rank, java.util.stream.Collectors.counting()))
        .values()
        .stream()
        .anyMatch(count -> count == 3);
}

private static EvaluatedHand threeOfAKind(List<Card> five) {

    var counts = five.stream()
        .collect(java.util.stream.Collectors.groupingBy(Card::rank, java.util.stream.Collectors.counting()));

    Rank triplet = counts.entrySet().stream()
        .filter(e -> e.getValue() == 3)
        .map(e -> e.getKey())
        .findFirst()
        .get();

    List<Card> kickers = five.stream()
        .filter(c -> c.rank() != triplet)
        .sorted((a, b) -> b.rank().value - a.rank().value)
        .toList();

    List<Card> ordered = new ArrayList<>();

    five.stream().filter(c -> c.rank() == triplet).forEach(ordered::add);
    ordered.addAll(kickers);

    List<Integer> tb = new ArrayList<>();
    tb.add(triplet.value);
    kickers.forEach(c -> tb.add(c.rank().value));

    return new EvaluatedHand(HandRank.THREE_OF_A_KIND, ordered, tb);
}
}
