package Poker.Domain.Rule;

public interface PokerRank {

    char[] NUMBER_PATTERN = new char[]{'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
    char[] PICTURE_PATTERN = new char[]{'S', 'C', 'D', 'H'};

    static boolean isPokerNumber(char target) {
        for (char pattern : NUMBER_PATTERN) {
            if (target == pattern) return true;
        }

        return false;
    }

    static boolean isPokerPicture(char target) {
        for (char pattern : PICTURE_PATTERN) {
            if (target == pattern) return true;
        }

        return false;
    }

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
