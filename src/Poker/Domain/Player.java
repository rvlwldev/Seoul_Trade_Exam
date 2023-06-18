package Poker.Domain;

import Poker.Domain.Deck.Deck;
import Poker.Domain.Rule.PokerRank;

import java.util.Map;

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

    public Deck getDeck() {
        return this.deck;
    }

    public void setDeck(String[] cards) {
        this.deck = new Deck();
        deck.setCardList(cards);
    }

    public int getDeckRankLevel() {
        return deck.getHandLevel();
    }

    public int popHighestNumberIndex() {
        Map<Character, Integer> numberMap = deck.getNumberCountMap();
        if (numberMap.isEmpty()) return -1;

        int highest = -1;
        for (char number : numberMap.keySet()) {
            int index = PokerRank.numberIndexOf(number);
            if (index > highest) highest = index;
        }

        deck.removeNumber(PokerRank.NUMBER_PATTERN[highest]);

        return highest;
    }

}
