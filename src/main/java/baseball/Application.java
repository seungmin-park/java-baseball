package baseball;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Application {

    private static final String ANSWER = "3스트라이크";
    private static final String BALL = "%d볼";
    private static final String BALL_AND_STRIKE = "%d볼 %d스트라이크";
    private static final String NOTHING = "낫싱";
    private static final String STRIKE = "%d스트라이크";

    private static final String CLOSE_MESSAGE = "애플리케이션을 종료합니다.";
    private static final String CONGRATULATION_MESSAGE = "3개의 숫자를 모두 맞히셨습니다! 게임 종료";
    private static final String GAME_START_MESSAGE = "숫자 야구 게임을 시작합니다.";
    private static final String GAME_OPTION_MESSAGE = "게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.";
    private static final String GAME_OPTION_ERROR_MESSAGE = "1 또는 2를 입력하세요.";
    private static final String INPUT_USER_NUMBER_MESSAGE = "숫자를 입력해주세요 : ";
    private static final String INPUT_USER_NUMBER_ERROR_MESSAGE = "1부터 9까지의 서로 다른 3자리의 양의 정수를 입력하세요.";

    private static final String RESTART_GAME = "1";
    private static final String GAME_OVER = "2";

    private static final int START_VALUE = 1;
    private static final int END_VALUE = 9;


    public static void main(String[] args) {
            notifyGameStart();
            startGame();
    }

    private static void startGame() throws IllegalArgumentException{
        List<Integer> computerNumbers = createComputerNumbers();
        boolean isCorrectAnswer = false;
        while (!isCorrectAnswer){
            List<Integer> userNumbers = InputUserNumber();
            String hint = getHint(computerNumbers, userNumbers);
            System.out.println(hint);
            isCorrectAnswer = isCorrectAnswer(hint);
        }
        gameOver();
    }

    private static boolean isCorrectAnswer(String hint) {
        if (hint.equals(ANSWER)) {
            System.out.println(CONGRATULATION_MESSAGE);
            return true;
        }
        return false;
    }

    public static void gameOver() throws IllegalArgumentException{
        System.out.println(GAME_OPTION_MESSAGE);
        String gameOption = Console.readLine();
        validGameOption(gameOption);
        if (gameOption.equals(RESTART_GAME)){
            startGame();
        } else if (gameOption.equals(GAME_OVER)) {
            System.out.println(CLOSE_MESSAGE);
        }
    }

    public static List<Integer> createComputerNumbers() {
        List<Integer> computerNumbers = new ArrayList<>();
        computerNumbers.add(Randoms.pickNumberInRange(START_VALUE, END_VALUE));
        while (computerNumbers.size() < 3) {
            int randomNumber = Randoms.pickNumberInRange(START_VALUE, END_VALUE);
            if (!computerNumbers.contains(randomNumber)) {
                computerNumbers.add(randomNumber);
            }
        }
        return computerNumbers;
    }

    public static void notifyGameStart() {
        System.out.println(GAME_START_MESSAGE);
    }

    public static List<Integer> InputUserNumber() throws IllegalArgumentException{
        System.out.print(INPUT_USER_NUMBER_MESSAGE);
        String userNumbers = Console.readLine();
        validUserNumbers(userNumbers);
        return Arrays.stream(userNumbers.split(""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public static String getHint(List<Integer> computerNumbers, List<Integer> userNumbers) {
        int ballCnt = getBallCnt(computerNumbers, userNumbers);
        int strikeCnt = getStrikeCnt(computerNumbers, userNumbers);
        ballCnt -= strikeCnt;

        if (ballCnt == 0 && strikeCnt == 0) {
            return NOTHING;
        } else if (strikeCnt == 0) {
            return String.format(BALL, ballCnt);
        } else if (ballCnt == 0) {
            return String.format(STRIKE, strikeCnt);
        } else {
            return String.format(BALL_AND_STRIKE, ballCnt, strikeCnt);
        }
    }

    private static int getStrikeCnt(List<Integer> computerNumbers, List<Integer> userNumbers) {
        int strikeCnt = 0;
        for (int i = 0; i < userNumbers.size(); i++) {
            if (computerNumbers.get(i).equals(userNumbers.get(i))) {
                strikeCnt++;
            }
        }

        return strikeCnt;
    }

    private static int getBallCnt(List<Integer> computerNumbers, List<Integer> userNumbers) {
        int ballCnt = 0;
        for (Integer userNumber : userNumbers) {
            if (computerNumbers.contains(userNumber)) {
                ballCnt++;
            }
        }
        return ballCnt;
    }

    public static void validUserNumbers(String userNumbers) throws IllegalArgumentException{
        if (!userNumbers.matches("^[1-9]{3}$")){
            throw new IllegalArgumentException(String.join(":",INPUT_USER_NUMBER_ERROR_MESSAGE, userNumbers));
        }
        if (userNumbers.charAt(0) == userNumbers.charAt(1) ||
                userNumbers.charAt(0) == userNumbers.charAt(2) ||
                userNumbers.charAt(1) == userNumbers.charAt(2)){
            throw new IllegalArgumentException(String.join(":",INPUT_USER_NUMBER_ERROR_MESSAGE, userNumbers));
        }
    }

    public static void validGameOption(String gameOption) throws IllegalArgumentException {
        if (!gameOption.matches("^[1-2]$")) {
            throw new IllegalArgumentException(String.join(":",GAME_OPTION_ERROR_MESSAGE, gameOption));
        }
    }
}
