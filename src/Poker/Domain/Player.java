package Poker.Domain;

import Poker.Domain.Deck.Deck;

public class Player {

    public final int ID;
    public int winCount;
    private Deck deck;

    public Player(int number) {
        this.ID = number;
        this.winCount = 0;
    }

    public void setDeck(String[] cards) {
        this.deck = new Deck();
        deck.setCardList(cards);
    }

}
