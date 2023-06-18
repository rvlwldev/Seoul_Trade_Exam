package Poker.Domain;

import Poker.Domain.Rule.ErrorMessage;
import Poker.Domain.Rule.GameValidator;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


public class Game extends GameValidator {

    private final List<String[]> cardsPerGames = new ArrayList<>();
    private final List<Player> playerList = new ArrayList<>();

    private int gameCount = 0;

    public boolean prepare(String pokerGameFileName, Player... players) {
        try {
            FileReader reader = new FileReader(pokerGameFileName);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                line = line.replaceAll("10", "T");
                cardsPerGames.add(line.split(" "));
            }

        } catch (IOException e) {
            printErrorMessage(ErrorMessage.NOT_FOUND_POKER_GAME_FILE, pokerGameFileName);
            return false;
        }

        if (validate(cardsPerGames, players.length)) {
            for (Player player : players) playerList.add(player);
            return true;
        }

        return false;
    }

    public void play(int resultTargetPlayerNumber) {
        if (isProperResultTargetPlayer(playerList, resultTargetPlayerNumber)) {
            play();
            printResult(resultTargetPlayerNumber);
        }
    }

    private void play() {
        if (!isPlayable()) return;

        String[] cards = cardsPerGames.get(gameCount);
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
        return gameCount < cardsPerGames.size();
    }

    private Player findWinner() {
        List<Player> winnerCandidate = new ArrayList<>();

        int highestRankLevel = -1;
        for (Player player : playerList) {
            int rankLevel = player.getDeckRankLevel();

            if (rankLevel > highestRankLevel) {
                highestRankLevel = rankLevel;
                winnerCandidate.clear();

                winnerCandidate.add(player);
            } else if (rankLevel == highestRankLevel) {
                winnerCandidate.add(player);
            }
        }

        if (winnerCandidate.size() == 1) return winnerCandidate.get(0);

        List<Player> winnersByNumber = findWinnerCandidatesByNumber(winnerCandidate);
        if (winnersByNumber.size() == 1) return winnersByNumber.get(0);

        winnersByNumber = findWinnerCandidatesByPicture(winnerCandidate);
        if (winnersByNumber.size() == 1) return winnersByNumber.get(0);

        return null;
    }

    private List<Player> findWinnerCandidatesByNumber(List<Player> candidates) {
        List<Player> result = new ArrayList<>();

        int highestNumber = -1;

        for (Player player : candidates) {
            int cardIndex = player.getHighestNumberIndex();

            if (cardIndex > highestNumber) {
                highestNumber = cardIndex;
                result.clear();
                result.add(player);
            }
        }

        return result;
    }

    private List<Player> findWinnerCandidatesByPicture(List<Player> candidates) {
        List<Player> result = new ArrayList<>();

        int highestPicture = -1;

        for (Player player : candidates) {
            int cardIndex = player.getHighestPictureIndex();

            if (cardIndex > highestPicture) {
                highestPicture = cardIndex;
                result.clear();
                result.add(player);
            }
        }

        return result;
    }

    private void printResult(int resultTargetPlayerNumber) {
        for (Player player : playerList) {
            if (player.getID() == resultTargetPlayerNumber) {
                System.out.println(playerList.get(resultTargetPlayerNumber).getWinCount());
                break;
            }
        }
    }

}
