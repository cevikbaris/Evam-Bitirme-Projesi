
//This class to avoid repetitive codes.
public class CoordinateControls {
    /*
    The methods in this class check against the given X and Y indexes.
    It checks around the index given in the current table used and searches for the word SOS.
     */


    // ------------------ These methods control SOS as diagonally. ------------------------
    public void checkLeftUp(int x, int y) {
        if ((GameVariables.board[x][y] + GameVariables.board[x - 1][y - 1] + GameVariables.board[x - 2][y - 2]).equals("SOS"))
            GameMethods.updateScore();
    }

    public void checkLeftDown(int x, int y) {
        if ((GameVariables.board[x][y] + GameVariables.board[x + 1][y - 1] + GameVariables.board[x + 2][y - 2]).equals("SOS"))
            GameMethods.updateScore();
    }

    public void checkRightUp(int x, int y) {
        if ((GameVariables.board[x][y] + GameVariables.board[x - 1][y + 1] + GameVariables.board[x - 2][y + 2]).equals("SOS"))
            GameMethods.updateScore();
    }

    public void checkRightDown(int x, int y) {
        if ((GameVariables.board[x][y] + GameVariables.board[x + 1][y + 1] + GameVariables.board[x + 2][y + 2]).equals("SOS"))
            GameMethods.updateScore();
    }

    public void checkRightUpAndLeftDown(int x , int y){
        if ((GameVariables.board[x - 1][y + 1] + GameVariables.board[x][y] + GameVariables.board[x + 1][y - 1]).equals("SOS"))
            GameMethods.updateScore();
    }

    public void checkLeftUpAndRightDown(int x , int y){
        if ((GameVariables.board[x - 1][y - 1] + GameVariables.board[x][y] + GameVariables.board[x + 1][y + 1]).equals("SOS"))
            GameMethods.updateScore();
    }


    //------------------------------------------------------------------------------


    //--------------------------- Check rows----------------------------------------
    public void checkToRight(int x, int y) {
        // close to left
        if ((GameVariables.board[x][y] + GameVariables.board[x][y + 1] + GameVariables.board[x][y + 2]).equals("SOS"))
            GameMethods.updateScore();
    }
    public void checkToLeft(int x, int y){
        if ((GameVariables.board[x][y - 2] + GameVariables.board[x][y - 1] + GameVariables.board[x][y]).equals("SOS"))
            GameMethods.updateScore();
    }


    public void checkRightAndLeft(int x, int y) {
        if((GameVariables.board[x][y - 1] + GameVariables.board[x][y] + GameVariables.board[x][y + 1]).equals("SOS"))
            GameMethods.updateScore();
    }

    //--------------------------------------------------------------------------------

    //------------------------------Check Columns ------------------------------------

    public void checkToUp(int x, int y) {
        if ((GameVariables.board[x - 2][y] + GameVariables.board[x - 1][y] + GameVariables.board[x][y]).equals("SOS"))
            GameMethods.updateScore();
    }

    public void checkToDown(int x, int y) {
        if ((GameVariables.board[x][y] + GameVariables.board[x + 1][y] + GameVariables.board[x + 2][y]).equals("SOS"))
            GameMethods.updateScore();
    }

    public void checkUpAndDown(int x, int y) {
        if((GameVariables.board[x - 1][y] + GameVariables.board[x][y] + GameVariables.board[x + 1][y]).equals("SOS"))
            GameMethods.updateScore();
    }

    //--------------------------------------------------------------------------------



}
