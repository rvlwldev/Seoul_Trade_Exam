package Poker.Domain;

import Poker.Domain.Rule.GameValidator;

import java.util.ArrayList;
import java.util.List;

public class Game extends GameValidator {

    private final List<String[]> gameList;
    private final List<Player> playerList;

    private int gameCount;

    public Game(String pokerGameFilePath, List<Player> playerList) {
        this.gameList = validateGameFileAndGetGameList(pokerGameFilePath);

        validatePlayerList(playerList);
        this.playerList = playerList;

        this.gameCount = 0;
    }

    public void play(int resultTargetPlayerID) {
        validateResultTargetPlayerID(playerList, resultTargetPlayerID);
        validateGame(this.gameList, playerList);

        play();
        printResult(resultTargetPlayerID);
    }

    private void play() {
        if (!isPlayable()) return;

        String[] cards = gameList.get(gameCount);
        int deckStart = 0;
        int deckEnd = CARD_COUNT_PER_PLAYER;

        for (Player player : playerList) {
            String[] deck = new String[CARD_COUNT_PER_PLAYER];

            for (int i = deckStart; i < deckEnd; i++) deck[i % CARD_COUNT_PER_PLAYER] = cards[i];
            player.setDeck(deck);

            deckStart = deckEnd;
            deckEnd += CARD_COUNT_PER_PLAYER;
        }

        Player winner = findWinner();
        if (winner != null) winner.win();

        gameCount++;
        play();
    }

    private boolean isPlayable() {
        return gameCount < gameList.size();
    }

    private Player findWinner() {
        List<Player> winnerCandidate = new ArrayList<>();

        int highestRankLevel = -1;
        for (Player player : playerList) {
            int rankLevel = player.getDeckRankLevel();

            if (rankLevel > highestRankLevel) {
                winnerCandidate.clear();
                winnerCandidate.add(player);

                highestRankLevel = rankLevel;
            } else if (rankLevel == highestRankLevel) {
                winnerCandidate.add(player);
            }
        }

        if (winnerCandidate.size() == 1) return winnerCandidate.get(0);

        return findWinnerCandidatesByNumber(winnerCandidate);
    }

    private Player findWinnerCandidatesByNumber(List<Player> candidates) {
        List<Player> result = new ArrayList<>();

        int highestNumber = -1;
        for (Player player : candidates) {
            int cardIndex = player.popHighestNumberIndex();
            if (cardIndex < 0) return null;

            if (cardIndex > highestNumber) {
                result.clear();
                result.add(player);

                highestNumber = cardIndex;
            } else if (cardIndex == highestNumber) {
                result.add(player);
            }
        }

        if (result.size() == 1) return result.get(0);

        return findWinnerCandidatesByNumber(result);
    }

    private void printResult(int resultTargetPlayerID) {
        for (Player player : playerList) {
            if (player.getID() == resultTargetPlayerID) {
                System.out.printf("PLAYER%d's win count : %d", player.getID(), player.getWinCount());
                return;
            }
        }
    }

}
