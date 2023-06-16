package Poker.Domain.Rule;

import java.util.List;
import Poker.Domain.Player;

import static Poker.Domain.Rule.ErrorMessage.*;

public class GameValidator implements GameRule {

    public boolean validate(List<String[]> games, int playerCount) {
        if (games.isEmpty()) {
            printErrorMessage(NO_GAME_INFO_LIST);
            return false;
        }

        int cardCount = games.get(0).length;

        if (!isValidCardCount(cardCount, games)) {
            printErrorMessage(NOT_PROPER_CARD_COUNT);
            return false;
        }

        if (!isProperCardsAndPlayers(cardCount, playerCount)) {
            printErrorMessage(NOT_PROPER_PLAYER_AND_CARD_COUNT, CARD_COUNT_PER_PLAYER);
            return false;
        }

        return true;
    }

    @Override
    public boolean isValidCardCount(int cardCount, List<String[]> games) {
        for (int i = 1; i < games.size(); i++) {
            if (cardCount != games.get(i).length) return false;
        }

        return true;
    }

    @Override
    public boolean isProperCardsAndPlayers(int cardCount, int playerCount) {
        return playerCount * 5 == cardCount;
    }

    @Override
    public boolean isProperResultTargetPlayer(List<Player> playerList, int resultTargetPlayerNumber) {
        for (Player player : playerList) {
            if (player.ID == resultTargetPlayerNumber) return true;
        }

        printErrorMessage(NOT_FOUND_RESULT_TARGET_PLAYER, resultTargetPlayerNumber);

        return false;
    }

}
