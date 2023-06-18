package Poker.Domain.Deck;

import Poker.Domain.Rule.PokerRank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deck implements PokerRank {

    private int handLevel;

    private final List<Card> cardList;
    private final Map<Character, Integer> numberCountMap;
    private final Map<Character, Integer> pictureCountMap;

    public Deck() {
        this.cardList = new ArrayList<>();
        this.handLevel = 0;
        numberCountMap = new HashMap<>();
        pictureCountMap = new HashMap<>();
    }

    public int getHandLevel() {
        return this.handLevel;
    }

    public Map<Character, Integer> getNumberCountMap() {
        return numberCountMap;
    }

    public void removeNumber(char number) {
        int count = numberCountMap.get(number);

        if (count == 1) {
            numberCountMap.remove(number);
        } else {
            numberCountMap.put(number, count - 1);
        }
    }

    public Map<Character, Integer> getPictureCountMap() {
        return pictureCountMap;
    }

    public void removePicture(char picture) {
        int count = pictureCountMap.get(picture);

        if (count == 1) {
            pictureCountMap.remove(picture);
        } else {
            pictureCountMap.put(picture, count - 1);
        }
    }

    public void setCardList(String[] cards) {
        for (String info : cards) {
            Card card = new Card();
            card.setCard(info);

            this.cardList.add(card);
        }

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
        if (isRoyalFlush()) this.handLevel = 9;
        else if (isStraightFlush()) this.handLevel = 8;
        else if (isFourOfKind()) this.handLevel = 7;
        else if (isFullHouse()) this.handLevel = 6;
        else if (isFlush()) this.handLevel = 5;
        else if (isStraight()) this.handLevel = 4;
        else if (isThreeOfKind()) this.handLevel = 3;
        else if (isTwoPairs()) this.handLevel = 2;
        else if (isOnePair()) this.handLevel = 1;
        else this.handLevel = 0;
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
        cardList.sort((card1, card2) -> card1.getNumberRankIndex() - card2.getNumberRankIndex());

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
