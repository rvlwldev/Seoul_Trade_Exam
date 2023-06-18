package Poker_Hands.Domain.Rule;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Poker_Hands.Domain.Collection.CustomSet;
import Poker_Hands.Domain.Player;

import static Poker_Hands.Domain.Rule.ErrorMessage.*;

public class GameValidator implements GameRule {

    @Override
    public List<String[]> validateGameFileAndGetGameList(String pokerGameFilePath) {
        List<String[]> gameLogList = new ArrayList<>();

        try {
            FileReader reader = new FileReader(pokerGameFilePath);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;

            int lineCount = 1;
            while ((line = bufferedReader.readLine()) != null) {
                if(line.contains("10")) line = line.replaceAll("10", "T");
                String[] cards = line.split(" ");
                validateDuplicatedCard(cards, lineCount);
                lineCount++;
                gameLogList.add(cards);
            }

        } catch (IOException e) {
            printErrorMessage(ErrorMessage.NOT_FOUND_POKER_GAME_FILE, pokerGameFilePath);
        }

        return gameLogList;
    }

    public void validateDuplicatedCard(String[] cards, int lineCount) {
        List<String> duplicatedList = new ArrayList<>();

        for (String card : cards) {
            if(duplicatedList.contains(card)) printErrorMessage(DUPLICATED_CARD, lineCount, card);
            else duplicatedList.add(card);
        }
    }

    @Override
    public void validatePlayerList(List<Player> playerList) {
        List<Integer> duplicatedIdList = new ArrayList<>();

        for (Player player : playerList) {
            int id = player.getID();

            if (duplicatedIdList.contains(id)) printErrorMessage(DUPLICATED_PLAYER_ID, id);
            else duplicatedIdList.add(player.getID());
        }

    }

    @Override
    public void validateGame(List<String[]> gameList, List<Player> playerList) {
        if (gameList.isEmpty()) printErrorMessage(NO_GAME_INFO_LIST);

        validateCardCount(gameList);

        int cardCount = gameList.get(0).length;
        validateCardAndPlayers(cardCount, playerList);

        validateCardsOfGame(gameList);
    }

    @Override
    public void validateResultTargetPlayerID(List<Player> playerList, int resultTargetPlayerID) {
        for (Player player : playerList) {
            if (player.getID() == resultTargetPlayerID) return;
        }

        printErrorMessage(NOT_FOUND_RESULT_TARGET_PLAYER, resultTargetPlayerID);
    }

    private void validateCardCount(List<String[]> gameList) {
        int cardCount = gameList.get(0).length;

        for (int i = 1; i < gameList.size(); i++) {
            if (cardCount != gameList.get(i).length) printErrorMessage(NOT_PROPER_CARD_COUNT);
        }

    }

    private void validateCardAndPlayers(int cardCount, List<Player> playerList) {
        if (!(playerList.size() * CARD_COUNT_PER_PLAYER == cardCount)) {
            printErrorMessage(NOT_PROPER_PLAYER_AND_CARD_COUNT, CARD_COUNT_PER_PLAYER);
        }
    }


    private void validateCardsOfGame(List<String[]> gameList) {
        CustomSet<String> allCards = getAllCardSet(gameList);
        List<String> invalidCardList = new ArrayList<>();

        for (String card : allCards) {
            if (!checkCard(card)) invalidCardList.add(card);
        }

        if (!invalidCardList.isEmpty()) printErrorMessage(NOT_ALLOWED_INPUT_CARD_INFO, invalidCardList.toString());
    }

    private CustomSet<String> getAllCardSet(List<String[]> gameList) {
        CustomSet<String> allCardSet = new CustomSet<>();

        for (String[] array : gameList) allCardSet.addAll(array);

        return allCardSet;
    }

    private boolean checkCard(String info) {
        if (info.length() != 2) return false;

        char[] arr = info.toCharArray();

        return PokerHand.isPokerNumber(arr[0]) && PokerHand.isPokerPicture(arr[1]);
    }

}
