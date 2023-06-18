package Poker.Domain.Rule;

import java.util.List;
import Poker.Domain.Player;

public interface GameRule {

    int CARD_COUNT_PER_PLAYER = 5;


    List<String[]> validateGameFileAndGetGameList(String pokerGameFilePath);
    void validatePlayerList(List<Player> playerList);
    void validateGame(List<String[]> games, List<Player> playerList);
    void validateResultTargetPlayerID(List<Player> playerList, int resultTargetPlayerID);

    default void printErrorMessage(ErrorMessage message) {
        System.err.println(message);
        System.exit(-1);
    }

    default void printErrorMessage(ErrorMessage message, String reference) {
        System.err.printf(String.valueOf(message), reference);
        System.exit(-1);
    }

    default void printErrorMessage(ErrorMessage message, int reference) {
        System.err.printf(String.valueOf(message), reference);
        System.exit(-1);
    }

    default void printErrorMessage(ErrorMessage message, int referenceInt, String referenceString) {
        System.err.printf(String.valueOf(message), referenceInt, referenceString);
        System.exit(-1);
    }




}
