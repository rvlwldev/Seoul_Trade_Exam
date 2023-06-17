package Poker.Domain.Rule;

public interface PokerRank {

    Character[] NUMBER_PATTERN = {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
    Character[] PICTURE_PATTERN = {'S', 'C', 'D', 'H'};

    Character[] HIGHEST_PICTURES_SET = {'T', 'J', 'Q', 'K', 'A'};

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

    static int numberIndexOf(char number) {
        for (int i = 0; i < NUMBER_PATTERN.length; i++) {
            if (NUMBER_PATTERN[i] == number) return i;
        }

        return -1;
    }

    static int pictureIndexOf(char picture) {
        for (int i = 0; i < PICTURE_PATTERN.length; i++) {
            if (PICTURE_PATTERN[i] == picture) return i;
        }

        return -1;
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
