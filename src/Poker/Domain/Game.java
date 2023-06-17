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

        gameCount++;
        play();
    }

    private boolean isPlayable() {
        return gameCount < cardsPerGames.size();
    }

    private void printResult(int resultTargetPlayerNumber) {
        for (Player player : playerList) {
            if (player.ID == resultTargetPlayerNumber) {
                System.out.println(playerList.get(resultTargetPlayerNumber).winCount);
                break;
            }
        }
    }

}
