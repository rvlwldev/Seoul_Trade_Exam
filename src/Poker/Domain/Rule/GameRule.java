package Poker.Domain.Rule;

import java.util.List;
import Poker.Domain.Player;

public interface GameRule {

    int CARD_COUNT_PER_PLAYER = 5;

    boolean isValidCardCount(int cardCount, List<String[]> games);

    boolean isProperCardsAndPlayers(int cardCount, int playerCount);

    boolean isProperResultTargetPlayer(List<Player> playerList, int resultTargetPlayerNumber);


    default void printErrorMessage(ErrorMessage message) {
        System.err.println(message);
    }

    default void printErrorMessage(ErrorMessage message, String reference) {
        System.err.printf(String.valueOf(message), reference);
    }

    default void printErrorMessage(ErrorMessage message, int reference) {
        System.err.printf(String.valueOf(message), reference);
    }





}
