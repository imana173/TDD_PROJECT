package poker.tdd_poker;



public record Card(Rank rank, Suit suit) {
    @Override
    public String toString() {
        return rank + "_" + suit;
    }
}
