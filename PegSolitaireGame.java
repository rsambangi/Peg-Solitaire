/**
 * Title: Peg Solitaire
 * Author: Rushil Sambangi
 * 
 * Made for the University of Wisconsin-Madison CS300 course.
 */

import java.util.Scanner;

public class PegSolitaireGame {
  private static Scanner in;
  private static char[][] board;

  /**
   * This method is responsible for everything from displaying the opening welcome message to
   * printing out the final thank you.
   * 
   * @param args - any command line arguments may be ignored by this method.
   */
  public static void main(String[] args) {
    System.out.println(
        "WELCOME TO CS300 PEG SOLITAIRE!" + "\n" + "===============================" + "\n" + "\n");
    System.out.println("Board Style Menu" + "\n" + "1) Cross" + "\n" + "2) Circle" + "\n"
        + "3) Triangle" + "\n" + "4) Simple T");
    in = new Scanner(System.in);
    String[] dir = {"UP", "DOWN", "LEFT", "RIGHT"};
    int type = readValidInt(in, "Choose a board style: ", 1, 4);
    System.out.println("\n");
    board = createBoard(type);
    displayBoard(board);
    int moves = 1;

    while (moves > 0) {
      int[] move = readValidMove(in, board);
      boolean valid = isValidMove(board, move[1], move[0], move[2]);
      if (valid == true) {
        board = performMove(board, move[1], move[0], move[2]);
        System.out.println("\n");
        displayBoard(board);
        int pegs = countPegsRemaining(board);
        if (pegs == 1) {
          System.out.println("Congrats, you won!");
          break;
        } else {
          moves = countMovesAvailable(board);
        }
      } else {
        System.out.println("Moving a peg from row " + move[1] + " and column " + move[0] + " "
            + dir[move[2] - 1] + " is not currently a legal move.");
        System.out.println();
        continue;
      }
    }

    if (moves == 0) {
      System.out.println("It looks like there are no more legal moves.  Please try again.");
      System.out.println();
      System.out.println("==========================================" + "\n"
          + "THANK YOU FOR PLAYING CS300 PEG SOLITAIRE!");
    } else {
      System.out.println();
      System.out.println("==========================================" + "\n"
          + "THANK YOU FOR PLAYING CS300 PEG SOLITAIRE!");
    }
    in.close();
  }

  /**
   * This method is used to read in all inputs from the user. After printing the specified prompt,
   * it will check whether the user’s input is in fact an integer within the specified range.
   * 
   * @param in     - user input from standard in is ready through this.
   * @param prompt - message describing what the user is expected to enter.
   * @param min    - the smallest valid integer that the user may enter.
   * @param max    - the largest valid integer that the user may enter.
   * @return - the valid integer between min and max entered by the user.
   */
  public static int readValidInt(Scanner in, String prompt, int min, int max) {
    System.out.print(prompt);
    int input;
    while (true)
      try {
        input = Integer.parseInt(in.nextLine());
        if (input >= min && input <= max) {
          return input;
        } else {
          System.out.print(
              "Please enter your choice as an integer between " + min + " and " + max + ": ");
          continue;
        }
      } catch (NumberFormatException nfe) {
        System.out
            .print("Please enter your choice as an integer between " + min + " and " + max + ": ");
        continue;
      }
  }

  /**
   * This method creates, initializes, and then returns a rectangular two dimensional array of
   * characters according to the specified boardType. Initial configurations for each of the
   * possible board types are depicted below. Note that pegs are displayed as @s, empty holes are
   * displayed as -s, and extra blank positions that are neither pegs nor holes within each
   * rectangular array are displayed as #s.
   * 
   * @param boardType - 1-4 indicating one of the following initial patterns: 
   *          1) Cross 
   *                  ###@@@###
   *                  ###@@@###
   *                  @@@@@@@@@
   *                  @@@@-@@@@
   *                  @@@@@@@@@ 
   *                  ###@@@### 
   *                  ###@@@###
   * 
   *          2) Circle 
   *                  #-@@-# 
   *                  -@@@@-
   *                  @@@@@@
   *                  @@@@@@ 
   *                  -@@@@- 
   *                  #-@@-#
   * 
   *          3) Triangle 
   *                  ###-@-### 
   *                  ##-@@@-## 
   *                  #-@@-@@-# 
   *                  -@@@@@@@-
   * 
   *          4) Simple T 
   *                  ----- 
   *                  -@@@- 
   *                  --@-- 
   *                  --@-- 
   *                  -----
   * 
   * @return - the fully initialized two dimensional array.
   */
  public static char[][] createBoard(int boardType) {
    // CROSS
    if (boardType == 1) {
      board = new char[7][9];
      for (int i = 0, j = 6; i < 2 && j > 4; i++, j--) {
        for (int v = 0; v < board[0].length; v++) {
          if (v >= 3 && v <= 5) {
            board[i][v] = '@';
            board[j][v] = '@';
          } else {
            board[i][v] = '#';
            board[j][v] = '#';
          }
        }
      }
      for (int v = 0; v < board[0].length; v++) {
        board[2][v] = '@';
        board[4][v] = '@';
      }
      for (int v = 0; v < board[0].length; v++) {
        if (v == 4) {
          board[3][4] = '-';
        } else {
          board[3][v] = '@';
        }
      }
      return board;
    }

    // CIRCLE
    else if (boardType == 2) {
      board = new char[6][6];
      board[0][0] = '#';
      board[5][0] = '#';
      board[0][1] = '-';
      board[5][1] = '-';
      board[0][2] = '@';
      board[5][2] = '@';
      board[0][3] = '@';
      board[5][3] = '@';
      board[0][4] = '-';
      board[5][4] = '-';
      board[0][5] = '#';
      board[5][5] = '#';
      for (int v = 0; v < board[0].length; v++) {
        if (v == 0 || v == 5) {
          board[1][v] = '-';
          board[4][v] = '-';
        } else {
          board[1][v] = '@';
          board[4][v] = '@';
        }
      }
      for (int v = 0; v < board[0].length; v++) {
        board[2][v] = '@';
        board[3][v] = '@';
      }
      return board;
    }

    // TRIANGLE
    else if (boardType == 3) {
      board = new char[4][9];
      for (int v = 0; v < board[0].length; v++) {
        if (v == 3 || v == 5) {
          board[0][v] = '-';
        } else if (v == 4) {
          board[0][v] = '@';
        } else {
          board[0][v] = '#';
        }
      }
      for (int v = 0; v < board[0].length; v++) {
        if (v == 2 || v == 6) {
          board[1][v] = '-';
        } else if (v > 2 && v < 6) {
          board[1][v] = '@';
        } else {
          board[1][v] = '#';
        }
      }
      for (int v = 0; v < board[0].length; v++) {
        if (v == 1 || v == 4 || v == 7) {
          board[2][v] = '-';
        } else if ((v > 1 && v < 4) || (v > 4 && v < 7)) {
          board[2][v] = '@';
        } else {
          board[2][v] = '#';
        }
      }
      for (int v = 0; v < board[0].length; v++) {
        if (v < 1 || v > 7) {
          board[3][v] = '-';
        } else {
          board[3][v] = '@';
        }
      }
      return board;
    }

    // Simple T
    else if (boardType == 4) {
      board = new char[5][5];
      for (int v = 0; v < board[0].length; v++) {
        board[0][v] = '-';
        board[4][v] = '-';
      }
      for (int i = 2; i < 4; i++) {
        for (int v = 0; v < board[0].length; v++) {
          if (v == 2) {
            board[i][v] = '@';
          } else {
            board[i][v] = '-';
          }
        }
      }
      for (int v = 0; v < board[0].length; v++) {
        if (v == 0 || v == 4) {
          board[1][v] = '-';
        } else {
          board[1][v] = '@';
        }
      }
      return board;
    }

    else {
      return null;
    }
  }

  /**
   * This method prints out the contents of the specified board using @s to represent pegs, -s to
   * represent empty hole, and #s to represent empty positions that are neither pegs nor holes.
   * 
   * @param board - the current state of the board being drawn.
   */
  public static void displayBoard(char[][] board) {
    System.out.print("  ");
    for (int i = 0; i < board[0].length; i++) {
      System.out.print((i + 1));
    }
    System.out.println();

    for (int i = 0; i < board.length; i++) {
      System.out.print((i + 1) + " ");
      for (int j = 0; j < board[0].length; j++) {
        System.out.print(board[i][j]);
      }
      System.out.println();
    }
  }


  /**
   * This method is used to read in and validate each part of a user’s move choice: the row and
   * column that they wish to move a peg from, and the direction that they would like to move/jump
   * that peg in. When the player’s row, column, and direction selection does not represent a valid
   * move, your program should report that their choice does not constitute a legal move before
   * giving them another chance to enter a different move. They should be given as many chances as
   * necessary to enter a legal move. The array of three integers that this method returns will
   * contain: the user’s choice of column as the first integer, their choice of row as the second
   * integer, and their choice of direction as the third.
   * 
   * @param in    - user input from standard in is ready through this.
   * @param board - the state of the board that moves must be legal on.
   * @return - the user's choice of column, row, and direction representing a valid move and store
   *         in that order with an array.
   */
  public static int[] readValidMove(Scanner in, char[][] board) {
    int[] choice = new int[3];
    int col =
        readValidInt(in, "Choose the COLUMN of a peg you'd like to move: ", 1, board[0].length);
    int row = readValidInt(in, "Choose the ROW of a peg you'd like to move: ", 1, board.length);
    int dir = readValidInt(in,
        "Choose a DIRECTION to move that peg 1) UP, 2) DOWN, 3) LEFT, or 4) RIGHT: ", 1, 4);
    choice[0] = col;
    choice[1] = row;
    choice[2] = dir;
    return choice;
  }

  /**
   * This method checks whether a specific move (column + row + direction) is valid within a
   * specific board configuration. In order for a move to be a valid: 1)there must be a peg at
   * position row, column within the board, 2)there must be another peg neighboring that first one
   * in the specified direction, and 3)there must be an empty hole on the other side of that
   * neighboring peg (further in the specified direction). This method only returns true when all
   * three of these conditions are met. If any of the three positions being checked happen to fall
   * beyond the bounds of your board array, then this method should return false.
   * 
   * @param board     - the state of the board that moves must be legal on.
   * @param row       - the vertical position of the peg proposed to be moved.
   * @param column    - the horizontal position of the peg proposed to be moved.
   * @param direction - the direction proposed to move/jump that peg in.
   * @return - true when the proposed move is legal, otherwise false.
   */
  public static boolean isValidMove(char[][] board, int row, int column, int direction) {
    if (board[row - 1][column - 1] == '@') {
      if (direction == 1) {
        if (row - 3 < 0) {
          return false;
        } else if ((board[row - 2][column - 1] == '@') && (board[row - 3][column - 1] == '-')) {
          return true;
        } else {
          return false;
        }
      }

      else if (direction == 2) {
        if (row + 1 >= board.length) {
          return false;
        } else if ((board[row][column - 1] == '@') && (board[row + 1][column - 1] == '-')) {
          return true;
        } else {
          return false;
        }
      } 
      
      else if (direction == 3) {
        if (column - 3 < 0) {
          return false;
        } else if ((board[row - 1][column - 2] == '@') && (board[row - 1][column - 3] == '-')) {
          return true;
        } else {
          return false;
        }
      }

      else if (direction == 4) {
        if (column + 1 >= board[0].length) {
          return false;
        } else if ((board[row - 1][column] == '@') && (board[row - 1][column + 1] == '-')) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  /**
   * The parameters of this method are the same as those of the isValidMove() method. However this
   * method changes the board state according to this move parameter (column + row + direction),
   * instead of validating whether the move is valid. If the move specification that is passed into
   * this method does not represent a legal move, then do not modify the board.
   * 
   * @param board     - the state of the board will be changed by this move.
   * @param row       - the vertical position that a peg will be moved from.
   * @param column    - the horizontal position that a peg will be moved from.
   * @param direction - the direction of the neighbor to jump this peg over.
   * @return - the updated board state after the specified move is taken.
   */
  public static char[][] performMove(char[][] board, int row, int column, int direction) {
    if (direction == 1) {
      board[row - 1][column - 1] = '-';
      board[row - 2][column - 1] = '-';
      board[row - 3][column - 1] = '@';
      return board;
    } else if (direction == 2) {
      board[row - 1][column - 1] = '-';
      board[row][column - 1] = '-';
      board[row + 1][column - 1] = '@';
      return board;
    } else if (direction == 3) {
      board[row - 1][column - 1] = '-';
      board[row - 1][column - 2] = '-';
      board[row - 1][column - 3] = '@';
      return board;
    } else if (direction == 4) {
      board[row - 1][column - 1] = '-';
      board[row - 1][column] = '-';
      board[row - 1][column + 1] = '@';
      return board;
    } else {
      return null;
    }
  }

  /**
   * This method counts up the number of pegs left within a particular board configuration, and
   * returns that number.
   * 
   * @param board - the board that pegs are counted from.
   * @return - the number of pegs found in that board.
   */
  public static int countPegsRemaining(char[][] board) {
    int count = 0;
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        if (board[i][j] == '@') {
          count++;
        } else {
          continue;
        }
      }
    }
    return count;
  }

  /**
   * This method counts up the number of legal moves that are available to be performed in a given
   * board configuration.
   * 
   * @param board - the board that possible moves are counted from.
   * @return - the number of legal moves found in that board.
   */
  public static int countMovesAvailable(char[][] board) {
    int countMoves = 0;
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        if (isValidMove(board, (i + 1), (j + 1), 1) == true) {
          countMoves++;
        }
        if (isValidMove(board, (i + 1), (j + 1), 2) == true) {
          countMoves++;
        }
        if (isValidMove(board, (i + 1), (j + 1), 3) == true) {
          countMoves++;
        }
        if (isValidMove(board, (i + 1), (j + 1), 4) == true) {
          countMoves++;
        } else {
          continue;
        }
      }
    }
    return countMoves;
  }

}
