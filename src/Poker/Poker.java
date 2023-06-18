package Poker;

import Poker.Domain.Game;
import Poker.Domain.Player;

import java.util.ArrayList;
import java.util.List;

public class Poker {
    public static void main(String[] args) {
        String pokerGameFilePath = "src/Poker/File/poker.txt";
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player(1));
        playerList.add(new Player(2));

        Game game = new Game(pokerGameFilePath, playerList);

        game.play(1);
    }
}
