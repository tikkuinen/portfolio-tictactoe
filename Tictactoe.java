import java.util.Scanner;

public class Tictactoe {

    private static int randomizeStart() {
        // get a number between 1-100
        int startNum = (int)(Math.random() * 100) + 1;
        // if number is even, return 1. Otherwise return 2
        int ret = (startNum % 2 == 0) ? (1) : (2);
        return ret;
    }

    private static void showBoard(int[][] gameboard) {
        int square = 1; // for numbering squares. First square number is 1
        final int cross = character('X');
        final int zero = character('0');

        for (int row = 0; row < gameboard.length; ++row) {
            for (int col = 0; col < gameboard[row].length; ++col) {
            
                // print cross, zero or square number
                if (gameboard[row][col] == cross)
                    System.out.print('X'); // print X
                else if (gameboard[row][col] == zero) 
                    System.out.print('0'); // print 0
                else 
                    // print the square number
                    System.out.print(square);
            
                if (col == gameboard[row].length-1)
                    // last number of the row printed, print a line change
                    System.out.println();
                else
                    // after the first and second number of each row print a | character
                    System.out.print('|');
            
                square++;  // number of next square is one bigger than the previous 
            }
            
            if (row != gameboard.length - 1)
                System.out.println("-+-+-");
        }
    }

    private static boolean saveMove(int[][] gameBoard, int pNo, int r) {
        int square = 1; // index of the checked element
        final int cross = character('X');
        final int zero = character('0');
        for (int row = 0; row < gameBoard.length; ++row) {
            for (int col = 0; col < gameBoard[row].length; ++col) {
                // in the square player chose
                if (square == r) {
                    if (gameBoard[row][col] == cross || gameBoard[row][col] == zero) {
                        // chosen place already has a cross or zero
                        return false;
                    } else {
                        // no character already --> place a cross or zero
                        int character = (pNo == 1) ? cross : zero;
                        gameBoard[row][col] = character;
                        return true;
                    }
                }
                square++; // move to the next element
            }
        }
        /* if execution reaches this point, 
        saving the character was unsuccessful and false is returned */
        return false;
    }

    private static int checkWinner(int[][] gameBoard) {
        // calculate the squares of numbers presenting cross and zero
        final int threeCrosses = character('X') * character('X') * character('X');
        final int threeZeros = character('0') * character('0') * character('0');

        // check for horizontal lines, row multiplication
        for (int row = 0; row < gameBoard.length; ++row) {
            int multi = gameBoard[row][0] * gameBoard[row][1] * gameBoard[row][2];
            // three crosses in a line?
            if (multi == threeCrosses)
                return 1; // player 1 won
            // three zeros in line?
            if (multi == threeZeros)
                return 2; // player 2 won
        }

        // check for vertical lines, column multiplication
        for (int col = 0; col < gameBoard[0].length; ++col) {
            int multi = gameBoard[0][col] * gameBoard[1][col] * gameBoard[2][col];
            // three crosses in a line?
            if (multi == threeCrosses)
                return 1; // player 1 won
            // three zeros in a line?
            if (multi == threeZeros)
                return 2; // player 2 won
        }

        // finally check for diagonal lines

        // from top left to bottom right
        int tlbr = gameBoard[0][0] * gameBoard[1][1] * gameBoard[2][2];
        // from bottom left to top right
        int bltr = gameBoard[2][0] * gameBoard[1][1] * gameBoard[0][2];
        // three crosses in a line
        if (tlbr == threeCrosses || bltr == threeCrosses)
        return 1; // player 1 won
        // three zeros in a line?
        if (tlbr == threeZeros || bltr == threeZeros)
            return 2; // player 2 won 

        // If execution reaches this point, winner is not known
	    return 0;
    }

    private static int character(char m) {
        if (m == 'X')
           return 1;
        else if (m == '0')
           return 2;
        else
           return 0;
    }

    public static void main(String[] args) {
        // viesti tallennetaan
        String greeting = "Welcome to play Tic-tac-toe!\n" + "Type in the player names.\n";
        // tehdään muuttujat nimille
        String name1, name2;
        // taulukko pelialuetta varten
        int[][] gameBoard = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
                };
        // player in turn??????
        int turn;
        // siirtoja jäljellä, eli 9
        int moves = 9;
        // voittajan numero, tasatilanteessa 0
        int winner = 0;

        // datan lukeminen
        Scanner reader = new Scanner(System.in);

        // alkutervehdys
        System.out.println(greeting);

        // kysytään nimet
        System.out.print("Player 1 name (X): ");
		name1 = reader.nextLine();
		System.out.print("Player 2 name (0): ");
		name2 = reader.nextLine();

        // randomize who starts first
		turn = randomizeStart();

        // show empty game board
		showBoard(gameBoard);

        do {
            // kysytään vuorossa olevaa pelaajaa tekemään jotain
            System.out.print("Player " + turn + ": ");
            int square = reader.nextInt();
            boolean moveOK = saveMove(gameBoard, turn, square);

            if (moveOK) {
                // jos ok, vaihdetaan vuoroa
                turn = (turn == 1) ? 2 : 1;
                // vähennetään vuoro
                moves--;
                // näytä pelilaudan tilanne
                showBoard(gameBoard);
                // tarkista tuliko jo voittajaa
                winner = checkWinner(gameBoard);
            } else {
                // virhe
                System.out.println("Square you chose is not available!");
            }
        } while ((moves > 0) && (winner == 0));

        // tuloksen ilmoittaminen
        if (winner != 0) {
			System.out.print("Winner is ");
			System.out.println((winner == 1) ? (name1) : (name2) + "!");
			System.out.println("Congratulations!");
		} else {
			System.out.println("Game was a tie!");
			System.out.println("Thank you for playing!");
		}
    }
}
