public class GameMethods {

    //print the current game values
    public static void printGameBoard() {
        System.out.println("------------------------");
        System.out.println("Player score is: " + GameVariables.playerScore);
        System.out.println("Computer score is: " + GameVariables.computerScore);
        System.out.println("------------------------");
        int columnNumber = 0;
        for (String[] strings : GameVariables.board) {

            System.out.print(columnNumber + " ");
            columnNumber++;
            for (int i = 0; i < GameVariables.board[0].length; i++) {
                System.out.print(strings[i]);
                System.out.print("  ");
            }
            System.out.println();

        }
    }

    // this method use with the other classes to update players score.
    public static void updateScore() {
        if (GameVariables.currentPlayer == 0) // 0 is player 1 is computer
            ++GameVariables.playerScore;
        else
            ++GameVariables.computerScore;
    }

    // assign "-" to all whole array
    public static void initGame(int gameSize) {
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                GameVariables.board[i][j] = "-";
            }
        }
        System.out.println();
    }


}
