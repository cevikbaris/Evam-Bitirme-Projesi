public class GameVariables {
    /*
    I have defined a global static variable class as other classes will use it,
    and they will need to change and use the same value.
    */

    public static String[][] board;    //game panel .
    public static boolean FIRST_PLAYER = true;// first player will put 'S' letter.

    public static int playerScore = 0;
    public static int computerScore = 0;

    public static int currentPlayer; // who are playing? Player or computer. It will store it.
    public static int numberOfEmptyValues; // empty places in the game board.

}
