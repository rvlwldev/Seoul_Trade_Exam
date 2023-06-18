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

    public void setDeck(String[] cards) {
        this.deck = new Deck();
        deck.setCardList(cards);
    }

    public int getDeckRankLevel() {
        return deck.getRankLevel();
    }

    public char getHighestNumberIndex() {
        char result;
        Map<Character, Integer> numberMap = deck.getNumberCountMap();

        int highest = -1;
        for (char number : numberMap.keySet()) {
            int index = PokerRank.numberIndexOf(number);
            if (index > highest) highest = index;
        }

        result = PokerRank.NUMBER_PATTERN[highest];
        deck.removeNumber(result);

        return result;
    }

    public char getHighestPictureIndex() {
        char result;
        Map<Character, Integer> pictureMap = deck.getPictureCountMap();

        int highest = -1;
        for (char picture : pictureMap.keySet()) {
            int index = PokerRank.pictureIndexOf(picture);
            if (index > highest) highest = index;
        }

        result = PokerRank.PICTURE_PATTERN[highest];
        deck.removePicture(result);

        return result;
    }




}
