package Poker_Hands.Domain.Deck;

import Poker_Hands.Domain.Rule.PokerHand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Poker_Hands.Domain.Rule.PokerHand.HandValue.*;

public class Deck implements PokerHand {

    private int handValue;

    private final List<Card> cardList;
    private final Map<Character, Integer> numberCountMap;
    private final Map<Character, Integer> pictureCountMap;

    public Deck() {
        this.cardList = new ArrayList<>();
        this.handValue = 0;
        numberCountMap = new HashMap<>();
        pictureCountMap = new HashMap<>();
    }

    public int getHandValue() {
        return this.handValue;
    }

    public int getHighestNumberIndexOfHand() {
        if (handValue == FOUR_OF_KIND.getValue()) {
            return getHighestOfNumberIndexByCount(4);
        }

        if (handValue == THREE_OF_KIND.getValue()
        ||  handValue == FULL_HOUSE.getValue()) {
            return getHighestOfNumberIndexByCount(3);
        }

        if (handValue == ONE_PAIR.getValue()
        ||  handValue == TWO_PAIRS.getValue()) {
            return getHighestOfNumberIndexByCount(2);
        }

        return getHighestIndexOfAllNumber();
    }

    private int getHighestOfNumberIndexByCount(int count) {
        int result = -1;

        for (char number : numberCountMap.keySet()) {
            if(numberCountMap.get(number) != count) continue;

            int index = PokerHand.numberIndexOf(number);
            if (index > result) result = index;
        }

        return result;
    }

    private int getHighestIndexOfAllNumber() {
        int result = -1;
        for (char number : numberCountMap.keySet()) {
            int index = PokerHand.numberIndexOf(number);
            if (index > result) result = index;
        }

        return result;
    }

    public void removeNumber(char number) {
        int count = numberCountMap.get(number);

        if (count == 1) numberCountMap.remove(number);
        else numberCountMap.put(number, count - 1);
    }

    public void setCardList(String[] cards) {
        for (String info : cards) {
            Card card = new Card();
            card.setCard(info);

            this.cardList.add(card);
        }
        cardList.sort((card1, card2) -> card1.getNumberRankIndex() - card2.getNumberRankIndex());

        for (Card card : cardList) {
            if (!numberCountMap.containsKey(card.getNumber())) {
                numberCountMap.put(card.getNumber(), getCountOfNumber(card.getNumber()));
            }

            if (!pictureCountMap.containsKey(card.getPicture())) {
                pictureCountMap.put(card.getPicture(), getCountOfPicture(card.getPicture()));
            }
        }

        calculateRank();
    }

    private int getCountOfNumber(char number) {
        int result = 0;

        for (Card card : cardList) {
            if (card.getNumber() == number) result++;
        }

        return result;
    }

    private int getCountOfPicture(char picture) {
        int result = 0;

        for (Card card : cardList) {
            if (card.getPicture() == picture) result++;
        }

        return result;
    }

    private void calculateRank() {
             if (isRoyalFlush())    this.handValue = ROYAL_FLUSH.getValue();
        else if (isStraightFlush()) this.handValue = STRAIGHT_FLUSH.getValue();
        else if (isFourOfKind())    this.handValue = FOUR_OF_KIND.getValue();
        else if (isFullHouse())     this.handValue = FULL_HOUSE.getValue();
        else if (isFlush())         this.handValue = FLUSH.getValue();
        else if (isStraight())      this.handValue = STRAIGHT.getValue();
        else if (isThreeOfKind())   this.handValue = THREE_OF_KIND.getValue();
        else if (isTwoPairs())      this.handValue = TWO_PAIRS.getValue();
        else if (isOnePair())       this.handValue = ONE_PAIR.getValue();
        else                        this.handValue = HIGH_CARD.getValue();
    }

    @Override
    public boolean isRoyalFlush() {
        for (char t : HIGHEST_PICTURES_SET) {
            if (!numberCountMap.containsKey(t)) return false;
        }

        return isFlush();
    }

    @Override
    public boolean isStraightFlush() {
        return isStraight() && isFlush();
    }

    @Override
    public boolean isFourOfKind() {
        return numberCountMap.containsValue(4);
    }

    @Override
    public boolean isFullHouse() {
        Map<Character, Integer> clone = new HashMap<>(numberCountMap);

        for (char key : clone.keySet()) {
            if (isThreeOfKind(key)) {
                clone.remove(key);
                return isOnePair(clone);
            }
        }

        return false;
    }

    @Override
    public boolean isFlush() {
        return pictureCountMap.containsValue(5);
    }

    @Override
    public boolean isStraight() {
        int prev = cardList.get(0).getNumberRankIndex();

        int loopEndIndex = cardList.size();
        if (isStraightWithAce()) loopEndIndex--;

        for (int i = 1; i < loopEndIndex; i++) {
            int next = cardList.get(i).getNumberRankIndex();
            if (next - prev != 1) return false;

            prev = next;
        }

        return true;
    }

    private boolean isStraightWithAce() {
        return cardList.get(0).getNumber() == '2' && cardList.get(cardList.size() - 1).getNumber() == 'A';
    }

    @Override
    public boolean isThreeOfKind() {
        return numberCountMap.containsValue(3);
    }

    private boolean isThreeOfKind(char key) {
        return numberCountMap.get(key) == 3;
    }

    @Override
    public boolean isTwoPairs() {
        Map<Character, Integer> clone = new HashMap<>(numberCountMap);

        for (char key : clone.keySet()) {
            if (isOnePair(key)) {
                clone.remove(key);
                break;
            }
        }

        return isOnePair(clone);
    }

    @Override
    public boolean isOnePair() {
        return numberCountMap.containsValue(2);
    }

    private boolean isOnePair(Map<Character, Integer> countMapClone) {
        return countMapClone.containsValue(2);
    }

    private boolean isOnePair(char key) {
        return numberCountMap.get(key) == 2;
    }

}
