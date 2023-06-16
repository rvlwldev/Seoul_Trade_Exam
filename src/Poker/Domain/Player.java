package Poker.Domain;

public class Player {

    public final int ID;
    public int winCount;
    private PokerHand hand;

    public Player(int number) {
        this.ID = number;
        this.winCount = 0;
    }

}
