package Poker_Hands.Domain.Rule;

public interface PokerHand {

    Character[] NUMBER_PATTERN = {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
    Character[] PICTURE_PATTERN = {'S', 'C', 'D', 'H'};

    Character[] HIGHEST_PICTURES_SET = {'T', 'J', 'Q', 'K', 'A'};

    enum HandValue {
        ROYAL_FLUSH(10),
        STRAIGHT_FLUSH(9),
        FOUR_OF_KIND(8),
        FULL_HOUSE(7),
        FLUSH(6),
        STRAIGHT(5),
        THREE_OF_KIND(4),
        TWO_PAIRS(3),
        ONE_PAIR(2),
        HIGH_CARD(1);

        private final int value;

        HandValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

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
