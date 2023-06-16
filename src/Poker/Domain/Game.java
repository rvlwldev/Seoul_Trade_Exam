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

            while ((line = bufferedReader.readLine()) != null) cardsPerGames.add(line.split(" "));

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
