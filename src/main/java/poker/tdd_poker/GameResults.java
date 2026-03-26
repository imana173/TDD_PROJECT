package poker.tdd_poker;

import java.util.List;

public record GameResults(List<Integer> winnerIndexes, List<EvaluatedHand> hands) {
    public boolean isSplit() {
        return winnerIndexes.size() > 1;
    }
}