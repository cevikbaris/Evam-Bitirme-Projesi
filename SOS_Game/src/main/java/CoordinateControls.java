
//This class to avoid repetitive codes.
public class CoordinateControls {

    public static String[][] board=Game.board;

    // ------------------ These methods control SOS as diagonally. ------------------------
    public static void checkLeftUp(int x, int y) {
        if ((board[x][y] + board[x - 1][y - 1] + board[x - 2][y - 2]).equals("SOS"))
            Game.updateScore();
    }

    public static void checkLeftDown(int x, int y) {
        if ((board[x][y] + board[x + 1][y - 1] + board[x + 2][y - 2]).equals("SOS"))
            Game.updateScore();
    }

    public static void checkRightUp(int x, int y) {
        if ((board[x][y] + board[x - 1][y + 1] + board[x - 2][y + 2]).equals("SOS"))
            Game.updateScore();
    }

    public static void checkRightDown(int x, int y) {
        if ((board[x][y] + board[x + 1][y + 1] + board[x + 2][y + 2]).equals("SOS"))
            Game.updateScore();
    }

    public static void checkRightUpAndLeftDown(int x , int y){
        if ((board[x - 1][y + 1] + board[x][y] + board[x + 1][y - 1]).equals("SOS"))
            Game.updateScore();
    }

    public static void checkLeftUpAndRightDown(int x , int y){
        if ((board[x - 1][y - 1] + board[x][y] + board[x + 1][y + 1]).equals("SOS"))
            Game.updateScore();
    }


    //------------------------------------------------------------------------------


    //--------------------------- Check rows----------------------------------------
    public static void checkToRight(int x, int y) {
        // close to left
        if ((board[x][y] + board[x][y + 1] + board[x][y + 2]).equals("SOS"))
            Game.updateScore();
    }
    public static void checkToLeft(int x, int y){
        if ((board[x][y - 2] + board[x][y - 1] + board[x][y]).equals("SOS"))
            Game.updateScore();
    }


    public static void checkRightAndLeft(int x, int y) {
        if((board[x][y - 1] + board[x][y] + board[x][y + 1]).equals("SOS"))
            Game.updateScore();
    }

    //--------------------------------------------------------------------------------

    //------------------------------Check Columns ------------------------------------

    public static void checkToUp(int x, int y) {
        if ((board[x - 2][y] + board[x - 1][y] + board[x][y]).equals("SOS"))
            Game.updateScore();
    }

    public static void checkToDown(int x, int y) {
        if ((board[x][y] + board[x + 1][y] + board[x + 2][y]).equals("SOS"))
            Game.updateScore();
    }

    public static void checkUpAndDown(int x, int y) {
        if((board[x - 1][y] + board[x][y] + board[x + 1][y]).equals("SOS"))
            Game.updateScore();
    }

    //--------------------------------------------------------------------------------



}
