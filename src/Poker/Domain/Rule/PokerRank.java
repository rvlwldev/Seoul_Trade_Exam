package Poker.Domain.Rule;

public interface PokerRank {
    char[] SUIT_PATTERN = new char[]{'S', 'C', 'D', 'H'};
    char[] NUMBER_PATTERN = new char[]{'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};

    boolean isRoyalFlush();
    boolean isStraightFlush();
    boolean isFourOfKind();
    boolean isFullHouse();
    boolean isFlush();
    boolean isStraight();
    boolean isThreeOfKind();
    boolean isTwoPairs();
    boolean isOnePair();

}
