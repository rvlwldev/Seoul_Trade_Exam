package Poker.Domain.Deck;

import Poker.Domain.Rule.PokerRank;

public class Card {

    private char number;
    private char picture;

    public void setCard(String info) {
        char[] marks = info.toCharArray();

        this.number = marks[0];
        this.picture = marks[1];
    }

    public char getNumber() {
        return number;
    }

    public char getPicture() {
        return picture;
    }

    public int getNumberRankIndex() {
        return PokerRank.numberIndexOf(this.number);
    }

    public int getPictureRankIndex() {
        return PokerRank.numberIndexOf(this.number);
    }
}
