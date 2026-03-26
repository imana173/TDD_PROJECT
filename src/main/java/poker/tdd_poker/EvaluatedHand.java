package poker.tdd_poker;


import java.util.List;

public record EvaluatedHand(HandRank handRank, List<Card> chosen5, List<Integer> tieBreakers)
    implements Comparable<EvaluatedHand> {

    @Override
    public int compareTo(EvaluatedHand other) {
        int cmp = this.handRank.ordinal() - other.handRank.ordinal();
        if (cmp != 0) return cmp;
        for (int i = 0; i < Math.min(this.tieBreakers.size(), other.tieBreakers.size()); i++) {
            int t = this.tieBreakers.get(i) - other.tieBreakers.get(i);
            if (t != 0) return t;
        }
        return 0;
    }
}
