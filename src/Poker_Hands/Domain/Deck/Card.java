package Poker_Hands.Domain.Deck;

import Poker_Hands.Domain.Rule.PokerHand;

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
        return PokerHand.numberIndexOf(this.number);
    }

    public int getPictureRankIndex() {
        return PokerHand.numberIndexOf(this.number);
    }
}
