import java.util.Random;
import java.util.Scanner;

public class CoordinateMethods {

    private int replayCountPlayer = 0; // when do SOS you  can replay. This is that count.
    private int replayCountComputer = 0;

    private int xAxis; // user will assign value to these variables.
    private int yAxis;

    private final int PLAYER = 0; // value of PLAYER and COMPUTER
    private final int COMPUTER = 1;

    Random random = new Random(); // for computer's coordinate choosing process.
    Scanner scanner = new Scanner(System.in);

    private int gameSize; // will get from game class.

    // This class is used to search horizontally, vertically and diagonally according to the given index.
    private CoordinateControls coordinateControls;

    public CoordinateMethods(CoordinateControls coordinateControls, int gameSize) {
        this.gameSize = gameSize;
        this.coordinateControls = coordinateControls;
    }

    public CoordinateMethods() {
    }


    //------------------------------------ METHODS ---------------------------------------------

    // This method only for player. When player play the game this method works.
    // Method first calls enterCoordinate() method for get X and Y values then
    // if x and y coordinates don't equal to "-"(if coordinates have chosen before)
    // enter the empty values coordinates and check these with playAndControlScore() method.
    public  void playerChooseCoordinate() {
        enterCoordinate();
        while (!GameVariables.board[xAxis][yAxis].equals("-")) {
            System.out.println("This coordinate is not empty please choose again. \n");
            enterCoordinate();
        }
        playAndControlScore();
        GameVariables.currentPlayer = COMPUTER;
    }

    // Computer chooses coordinates until these are empty.
    public  void computerRandomChoose() {
        System.out.println("\nComputer is playing...");
        xAxis = random.nextInt(gameSize);
        yAxis = random.nextInt(gameSize);
        while (!GameVariables.board[xAxis][yAxis].equals("-")) {
            xAxis = random.nextInt(gameSize);
            yAxis = random.nextInt(gameSize);
        }
        System.out.println("Computer's coordinates are: X: " + xAxis + " Y: " + yAxis);

        playAndControlScore();
        GameVariables.currentPlayer = PLAYER;

    }

    // This method asks enter value for x and y axis.
    private  void enterCoordinate() {
        System.out.println("Choose a coordinate between 0-" + (gameSize - 1) + " for x and y axis.");
        while(true) {
            try {//Check if the entered value is a number and is within the playing field.
                System.out.println("X axis : ");
                xAxis = Integer.parseInt(scanner.nextLine());
                System.out.println("Y axis : ");
                yAxis = Integer.parseInt(scanner.nextLine()); //scanner.nextInt();
                if(xAxis>=0 && xAxis<gameSize && yAxis>=0 && yAxis<gameSize ){
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Wrong input. Try again.");
            }
        }
    }

    // Firstly, player or PC puts the value to chosen coordinates.
    // The checkXY_Axis(xAxis, yAxis) checks 'SOS' if there is 'SOS' for current value ,his value will increase.
    // If player win one or more score , player can select new coordinate. This situation checks with method's parameter.
    // If previous player's point smaller than new point player can play again.
    public  void playAndControlScore() {
        GameVariables.board[xAxis][yAxis] = GameVariables.FIRST_PLAYER ? "S" : "O";
        // check SOS before the currentPlayer changes.
        checkXY_Axis(xAxis, yAxis);
        --GameVariables.numberOfEmptyValues;

        // if score changed, play again.
        // Who is the current player. Check difference between previous point and current point.
        // If still the game has empty value play again.
        while (((GameVariables.currentPlayer == PLAYER
                            ? GameVariables.playerScore
                            : GameVariables.computerScore) -
                (GameVariables.currentPlayer == PLAYER ? replayCountPlayer : replayCountComputer)) > 0
                && GameVariables.numberOfEmptyValues > 0) { //check replay game count ant according to this player can play again.

            GameMethods.printGameBoard();
            //check who is current player.
            if (GameVariables.currentPlayer == PLAYER) {
                ++replayCountPlayer;
                playerChooseCoordinate();
            } else {
                ++replayCountComputer;
                computerRandomChoose();
            }
        }


    }

    //control the 'SOS' in the game. Check row-column-diagonally
    public  void checkXY_Axis(int x, int y) {
        checkRowSOS(x, y);
        checkColumnSOS(x, y);
        checkDiagonally(x, y);
    }



    // Check the rows is there any 'SOS'.
    public  void checkRowSOS(int x, int y) { // x and y are axis

        if (y == gameSize - 1) {   // far right of the board [x][game size-1]
            coordinateControls.checkToLeft(x,y);
        } else if (y == 0) { //far left f the board  [x][0]
            coordinateControls.checkToRight(x,y);

        } else { // between the right and left border
            // if letter 'O' check right and left
            if (!GameVariables.FIRST_PLAYER )
                coordinateControls.checkRightAndLeft(x,y);
            if (y < gameSize - 2 )
                coordinateControls.checkToRight(x,y);
            if (y > 1 )
                coordinateControls.checkToLeft(x,y);

        }
    }

    // Check the columns is there any 'SOS'.
    public  void checkColumnSOS(int x, int y) { // x and y are axis

        if (x == gameSize - 1) {   // bottom of the board.
            coordinateControls.checkToUp(x, y);
        } else if (x == 0) { // top of the board.
            coordinateControls.checkToDown(x, y);
        }
        else { // between top and bottom
            // IF LETTER IS 'O' -- check up and down
            if (!GameVariables.FIRST_PLAYER ){
                coordinateControls.checkUpAndDown(x,y);
            }
            if (x < gameSize - 2){
                coordinateControls.checkToDown(x,y);
            }
            if (x > 1)
                coordinateControls.checkToUp(x,y);
        }
    }


    // Check coordinates diagonally.
    public  void checkDiagonally(int x, int y) {

        // if player will add the letter S
        // if game size bigger than 3x3
        if (GameVariables.FIRST_PLAYER && gameSize > 3) {
            checkDiagonallySizeBiggerThan3x3(x, y);
        }

        // IF GAME SIZE IS 3x3
        else if (gameSize == 3) {
            checkDiagonallySize3x3(x, y);
        }

        //IF GAME SIZE MORE THAN 3 AND THE LETTER IS NOT 'S'
        // if not at the edges or corners.
        else if (y > 0 && x > 0 & y != gameSize - 1 && x != gameSize - 1) {
            //player can get 2 point if both cross are 'SOS'.
            coordinateControls.checkLeftUpAndRightDown(x,y);
            coordinateControls.checkRightUpAndLeftDown(x,y);
        }
    }

    private void checkDiagonallySizeBiggerThan3x3(int x, int y) {
        //upper left corner
        if ((x == 0 & y == 0) || (x == 0 & y == 1) || (x == 1 & y == 0)) {
            coordinateControls.checkRightDown(x, y);

        }  //bottom left corner
        else if ((x == gameSize - 1 & y == 0) || (x == gameSize - 2 & y == 0) || (x == gameSize - 1 & y == 1)) {
            coordinateControls.checkRightUp(x, y);

        }  //upper right corner
        else if ((x == 0 & y == gameSize - 1) || (x == 0 & y == gameSize - 2) || (x == 1 & y == gameSize - 1)) {
            coordinateControls.checkLeftDown(x, y);

        } //bottom right corner
        else if ((x == gameSize - 1 & y == gameSize - 1) || (x == gameSize - 1 & y == gameSize - 2)
                || (x == gameSize - 2 & y == gameSize - 1)) {
            coordinateControls.checkLeftUp(x, y);
        }
    }

    private void checkDiagonallySize3x3(int x, int y) {
        if (x == 1 && y == 1 && !GameVariables.FIRST_PLAYER){ // Center and 'O' letter.
            coordinateControls.checkRightUpAndLeftDown(x, y);
            coordinateControls.checkLeftUpAndRightDown(x, y);
        }
        //upper left corner
        else if (x == 0 && y == 0) {
            coordinateControls.checkRightDown(x, y);

        } //bottom left corner
        else if (x == gameSize - 1 & y == 0) {
            coordinateControls.checkRightUp(x, y);

        } //upper right corner
        else if (x == 0 & y == gameSize - 1) {
            coordinateControls.checkLeftDown(x, y);

        } //bottom right corner
        else if (x == gameSize - 1 & y == gameSize - 1) {
            coordinateControls.checkLeftUp(x, y);
        }
    }


}
