import java.util.Random;
import java.util.Scanner;

public class Game {


    // ------------------------------ MAIN METHOD ---------------------------------------------

    public static void main(String[] args) {

        Random random = new Random(); // for computer's coordinate choosing process.
        int whoStart = random.nextInt(2);// 0 PLAYER - 1 COMPUTER
        Scanner scanner = new Scanner(System.in); //to get value from player.
        int gameSize;    // the user will enter game size.


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

        // This class has methods that control the indexes entered by the players.
        // It needs game size and Coordinate control class.
        CoordinateMethods coordinateMethods = new CoordinateMethods(new CoordinateControls(),gameSize);


        //create game board with entered size
        GameVariables.board = new String[gameSize][gameSize];

        // how many times the game will play.
        GameVariables.numberOfEmptyValues = gameSize * gameSize;

        //game board created and printed
        GameMethods.initGame(gameSize);
        GameMethods.printGameBoard();

        // select first player randomly. The first player will use 'S' the other will use 'O' letter.
        if (whoStart == 1) {
            System.out.println("Computer is starting to game. ");
            GameVariables.currentPlayer = 1;
        } else {
            System.out.println("Player is starting to game: ");
            GameVariables.currentPlayer = 0;
        }

        /* The number of rounds in the game.
        Depending on the size of the game, there will be between 9(3x3) and 49(7x7) rounds.
        There is no need to check the letters in the array because the players cannot put the letters to the filled indexes.

        Current player will play the game.
        After game played, the first player will change. It means the current will use which letter. If first player
        he/she will use 'S' or not will use 'O'.
        */
        System.out.println("First player starts with 'S'. The other player plays with 'O' . ");
        while (GameVariables.numberOfEmptyValues > 0) {
            if (GameVariables.currentPlayer == 1)
                coordinateMethods.computerRandomChoose();
            else
                coordinateMethods.playerChooseCoordinate();
            GameVariables.FIRST_PLAYER = !GameVariables.FIRST_PLAYER;
            GameMethods.printGameBoard();
        }

        // print winner at the end of the game
        System.out.println("Game finished..");
        if (GameVariables.computerScore > GameVariables.playerScore)
            System.out.println("Winner: Computer | Score :" + GameVariables.computerScore);
        else if (GameVariables.playerScore > GameVariables.computerScore)
            System.out.println("Winner: Player | Score :" + GameVariables.playerScore);
        else
            System.out.println("The result is a draw. There is no winner.");
    }

}
