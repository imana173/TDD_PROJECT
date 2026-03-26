package poker.tdd_poker;

import java.util.List;

public record GameResults(List<Integer> winners, List<EvaluatedHand> hands) {

    public boolean isSplit() {
        return winners.size() > 1;
    }
}