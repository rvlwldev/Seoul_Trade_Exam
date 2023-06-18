package Poker.Domain.Rule;

public enum ErrorMessage {
    NOT_FOUND_POKER_GAME_FILE("파일이 존재하지 않습니다. (FILE_NAME : %s)"),
    DUPLICATED_PLAYER_ID("중복된 플레이어 ID가 존재합니다.\nID : %d"),
    NO_GAME_INFO_LIST("진행가능한 게임이 없습니다."),
    NOT_PROPER_PLAYER_AND_CARD_COUNT("플레이어 수와 게임당 카드의 수가 올바르지 않습니다. 플레이어 당 %d개의 카드를 받아야합니다."),
    NOT_PROPER_CARD_COUNT("분배 카드의 개수가 일정하지 않습니다."),
    NOT_FOUND_RESULT_TARGET_PLAYER("%d번 플레이어를 찾을 수 없습니다."),
    NOT_ALLOWED_INPUT_CARD_INFO("올바르지 않은 카드정보입니다.\n%s");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message + "\n게임을 종료합니다.";
    }
}
