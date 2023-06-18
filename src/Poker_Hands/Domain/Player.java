package Poker_Hands.Domain;

import Poker_Hands.Domain.Deck.Deck;
import Poker_Hands.Domain.Rule.PokerHand;

public class Player {

    private final int ID;
    private int winCount;
    private Deck deck;

    public Player(int number) {
        this.ID = number;
        this.winCount = 0;
    }

    public int getID() {
        return this.ID;
    }

    public void win() {
        winCount++;
    }

    public int getWinCount() {
        return this.winCount;
    }

    public void setDeck(String[] cards) {
        this.deck = new Deck();
        deck.setCardList(cards);
    }

    public int getDeckRankLevel() {
        return deck.getHandValue();
    }

    public int popHighestNumberIndex() {
        int index = deck.getHighestNumberIndexOfHand();
        if (index < 0) return index;

        char number = PokerHand.NUMBER_PATTERN[index];
        deck.removeNumber(number);

        return index;
    }

}
