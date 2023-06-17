package Poker.Domain.Deck;

import Poker.Domain.Rule.PokerRank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deck implements PokerRank {

    private static int count = 1;

    private int rankLevel;

    private final List<Card> cardList;
    private final Map<Character, Integer> numberCountMap;
    private final Map<Character, Integer> pictureCountMap;

    public Deck() {
        this.cardList = new ArrayList<>();
        this.rankLevel = 0;
        numberCountMap = new HashMap<>();
        pictureCountMap = new HashMap<>();
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
        if      (isRoyalFlush())    this.rankLevel = 9;
        else if (isStraightFlush()) this.rankLevel = 8;
        else if (isFourOfKind())    this.rankLevel = 7;
        else if (isFullHouse())     this.rankLevel = 6;
        else if (isFlush())         this.rankLevel = 5;
        else if (isStraight())      this.rankLevel = 4;
        else if (isThreeOfKind())   this.rankLevel = 3;
        else if (isTwoPairs())      this.rankLevel = 2;
        else if (isOnePair())       this.rankLevel = 1;
        else                        this.rankLevel = 0;
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
//        System.out.println(pictureCountMap);
        return pictureCountMap.containsValue(5);
    }

    @Override
    public boolean isStraight() {
        cardList.sort((card1, card2) -> card1.getNumberRankIndex() - card2.getNumberRankIndex());

        int prev = cardList.get(0).getNumberRankIndex();
        for (int i = 1; i < cardList.size(); i++) {
            int next = cardList.get(i).getNumberRankIndex();

            if (next - prev != 1) return false;

            prev = next;
        }

        return true;
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

    private boolean isOnePair(Map<Character, Integer> countMap) {
        return countMap.containsValue(2);
    }

    private boolean isOnePair(char key) {
        return numberCountMap.get(key) == 2;
    }
}
