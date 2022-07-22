import java.util.Random;
import java.util.Scanner;

public class Game {

    // _________________________________ STATIC VALUES ________________________________

    public static String[][] board;    //game panel .
    public static Scanner scanner = new Scanner(System.in); //to get value from player.
    public static int gameSize;    // the user will enter game size.
    public static Random random = new Random(); // for computer's coordinate choosing process.


    public static final int PLAYER = 0;
    public static final int COMPUTER = 1;
    public static boolean FIRST_PLAYER = true;// first player will put 'S' letter.

    public static int playerScore = 0;
    public static int computerScore = 0;

    public static int currentPlayer; // who are playing? Player or computer. It will store it.
    public static int numberOfEmptyValues; // empty places in the game board.
    public static int replayCountPlayer = 0; // when do SOS you  can replay. This is that count.
    public static int replayCountComputer = 0;

    public static int xAxis; // user will assign value to these variables.
    public static int yAxis;

    // ------------------------------ MAIN METHOD ---------------------------------------------
    public static void main(String[] args) {

        //The game starts by changing how big the game is.
        //The user enter value between 3 and 7 until is valid.
         while (true) {
            System.out.println("Enter game Size:\n 3 - 3x3 \n 4 - 4x4\n 5 - 5x5\n 6 - 6x6\n 7 - 7x7");
            try{//Check if the entered value is a number and is within the playing field.
                gameSize = Integer.parseInt(scanner.nextLine());
                if((gameSize >= 3 & gameSize <= 7))
                    break;
            }catch (NumberFormatException e){
                System.out.println("Wrong input. Try again.");
            }
        }

        //create game board with entered size
        board = new String[gameSize][gameSize];

        // how many times the game will play.
        numberOfEmptyValues = gameSize * gameSize;

        //game board created and printed
        initGame();
        printGameBoard();

        // select first player randomly. The first player will use 'S' the other will use 'O' letter.
        int whoStart = random.nextInt(2);
        if (whoStart == 1) {
            System.out.println("Computer is starting to game. ");
            currentPlayer = 1;
        } else {
            System.out.println("Player is starting to game: ");
            currentPlayer = 0;
        }

        /* The number of rounds in the game.
        Depending on the size of the game, there will be between 9(3x3) and 49(7x7) rounds.
        There is no need to check the letters in the array because the players cannot put the letters to the filled indexes.

        Current player will play the game.
        After game played, the first player will change. It means the current will use which letter. If first player
        he/she will use 'S' or not will use 'O'.
        */
        System.out.println("First player starts with 'S'. The other player plays with 'O' . ");
        while (numberOfEmptyValues > 0) {
            if (currentPlayer == 1)
                computerRandomChoose();
            else
                playerChooseCoordinate();
            FIRST_PLAYER = !FIRST_PLAYER;
            printGameBoard();
        }

        // print winner at the end of the game
        System.out.println("Game finished..");
        if (computerScore > playerScore)
            System.out.println("Winner: Computer | Score :" + computerScore);
        else if (playerScore > computerScore)
            System.out.println("Winner: Player | Score :" + playerScore);
        else
            System.out.println("The result is a draw. There is no winner.");
    }

    //------------------------------------- METHODS ----------------------------------------




    // This method only for player. When player play the game this method works.
    // Method first calls enterCoordinate() method for get X and Y values then
    // if x and y coordinates don't equal to "-"(if coordinates have chosen before)
    // enter the empty values coordinates and check these with playAndControlScore() method.
    public static void playerChooseCoordinate() {
        enterCoordinate();
        while (!board[xAxis][yAxis].equals("-")) {
            System.out.println("This coordinate is not empty please choose again. \n");
            enterCoordinate();
        }
        playAndControlScore();
        currentPlayer = COMPUTER;
    }

    // Computer chooses coordinates until these are empty.
    public static void computerRandomChoose() {
        System.out.println("\nComputer is playing...");
        xAxis = random.nextInt(gameSize);
        yAxis = random.nextInt(gameSize);
        while (!board[xAxis][yAxis].equals("-")) {
            xAxis = random.nextInt(gameSize);
            yAxis = random.nextInt(gameSize);
        }
        System.out.println("Computer's coordinates are: X: " + xAxis + " Y: " + yAxis);

        playAndControlScore();
        currentPlayer = PLAYER;

    }

    // This method asks enter value for x and y axis.
    private static void enterCoordinate() {
        System.out.println("Choose a coordinate between 0-" + (gameSize - 1) + " for x and y axis.");
        while(true) {
            try {//Check if the entered value is a number and is within the playing field.
                System.out.println("X axis : ");
                xAxis = Integer.parseInt(scanner.nextLine());
                System.out.println("Y axis : ");
                yAxis = scanner.nextInt();
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
    public static void playAndControlScore() {
        board[xAxis][yAxis] = FIRST_PLAYER ? "S" : "O";
        // check SOS before the currentPlayer changes.
        checkXY_Axis(xAxis, yAxis);
        --numberOfEmptyValues;

        // if score changed, play again.
        // Who is the current player. Check difference between previous point and current point.
        // If still the game has empty value play again.
        while (((currentPlayer == PLAYER ? playerScore : computerScore) -
                (currentPlayer == PLAYER ? replayCountPlayer : replayCountComputer)) > 0
                && numberOfEmptyValues > 0) { //check replay game count ant according to this player can play again.

            printGameBoard();
            //check who is current player.
            if (currentPlayer == PLAYER) {
                ++replayCountPlayer;
                playerChooseCoordinate();
            } else {
                ++replayCountComputer;
                computerRandomChoose();
            }
        }


    }

    //control the 'SOS' in the game. Check row-column-diagonally
    public static void checkXY_Axis(int x, int y) {
        checkRowSOS(x, y);
        checkColumnSOS(x, y);
        checkDiagonally(x, y);
    }


    // assign "-" to all whole array
    public static void initGame() {
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                board[i][j] = "-";
            }
        }
        System.out.println();
    }

    //print the current game values
    public static void printGameBoard() {
        System.out.println("------------------------");
        System.out.println("Player score is: " + playerScore);
        System.out.println("Computer score is: " + computerScore);
        System.out.println("------------------------");
        int columnNumber = 0;
        for (String[] strings : board) {

            System.out.print(columnNumber + " ");
            columnNumber++;
            for (int i = 0; i < board[0].length; i++) {
                System.out.print(strings[i]);
                System.out.print("  ");
            }
            System.out.println();

        }
    }


    // Check the rows is there any 'SOS'.
    public static void checkRowSOS(int x, int y) { // x and y are axis

        if (y == gameSize - 1) {   // far right of the board [x][game size-1]
           CoordinateControls.checkToLeft(x,y);
        } else if (y == 0) { //far left f the board  [x][0]
            CoordinateControls.checkToRight(x,y);

        } else { // between the right and left border
            // if letter 'O' check right and left
            if (!FIRST_PLAYER )
                CoordinateControls.checkRightAndLeft(x,y);
            if (y < gameSize - 2 )
                CoordinateControls.checkToRight(x,y);
            if (y > 1 )
                CoordinateControls.checkToLeft(x,y);

        }
    }

    // Check the columns is there any 'SOS'.
    public static void checkColumnSOS(int x, int y) { // x and y are axis

        if (x == gameSize - 1) {   // bottom of the board.
            CoordinateControls.checkToUp(x, y);
        } else if (x == 0) { // top of the board.
            CoordinateControls.checkToDown(x, y);
        }
        else { // between top and bottom
            // IF LETTER IS 'O' -- check up and down
            if (!FIRST_PLAYER ){
                CoordinateControls.checkUpAndDown(x,y);
            }
            if (x < gameSize - 2){
                CoordinateControls.checkToDown(x,y);
            }
            if (x > 1)
                CoordinateControls.checkToUp(x,y);
        }
    }


    // Check coordinates diagonally.
    public static void checkDiagonally(int x, int y) {

        // if player will add the letter S
        if (FIRST_PLAYER && gameSize > 3) {
            //upper left corner
            if ((x == 0 & y == 0) || (x == 0 & y == 1) || (x == 1 & y == 0)) {
                CoordinateControls.checkRightDown(x, y);

            }  //bottom left corner
            else if ((x == gameSize - 1 & y == 0) || (x == gameSize - 2 & y == 0) || (x == gameSize - 1 & y == 1)) {
                CoordinateControls.checkRightUp(x, y);

            }  //upper right corner
            else if ((x == 0 & y == gameSize - 1) || (x == 0 & y == gameSize - 2) || (x == 1 & y == gameSize - 1)) {
                CoordinateControls.checkLeftDown(x, y);

            } //bottom right corner
            else if ((x == gameSize - 1 & y == gameSize - 1) || (x == gameSize - 1 & y == gameSize - 2)
                    || (x == gameSize - 2 & y == gameSize - 1)) {
                CoordinateControls.checkLeftUp(x, y);
            }
        }

        // IF GAME SIZE IS 3
        else if (gameSize == 3) {
            if (x == 1 && y == 1 && !FIRST_PLAYER){ // Center and 'O' letter.
                CoordinateControls.checkRightUpAndLeftDown(x, y);
                CoordinateControls.checkLeftUpAndRightDown(x , y);
            }
            //upper left corner
            else if (x == 0 && y == 0) {
                CoordinateControls.checkRightDown(x, y);

            } //bottom left corner
            else if (x == gameSize - 1 & y == 0) {
                CoordinateControls.checkRightUp(x, y);

            } //upper right corner
            else if (x == 0 & y == gameSize - 1) {
                CoordinateControls.checkLeftDown(x, y);

            } //bottom right corner
            else if (x == gameSize - 1 & y == gameSize - 1) {
                CoordinateControls.checkLeftUp(x, y);
            }
        }

        //IF GAME SIZE MORE THAN 3 AND THE LETTER IS NOT 'S'
        // if not at the edges or corners.
        else if (y > 0 && x > 0 & y != gameSize - 1 && x != gameSize - 1) {
            //player can get 2 point if both cross are 'SOS'.
            CoordinateControls.checkLeftUpAndRightDown(x,y);
            CoordinateControls.checkRightUpAndLeftDown(x,y);
        }
    }


    public static void updateScore() {
        if (currentPlayer == 0) // 0 is player 1 is computer
            ++playerScore;
        else
            ++computerScore;
    }


}
