package Poker;

import Poker.Domain.Game;
import Poker.Domain.Player;

public class Poker {
    public static void main(String[] args) {
        Game game = new Game();
        String pokerGameFilePath = "src/Poker/File/poker.txt";

        Player player1 = new Player(1);
        Player player2 = new Player(2);

        if (game.prepare(pokerGameFilePath, player1, player2)) {
            game.play(1);
        }

    }
}
