/* Board.java */

/**
 * Implementation of the Board that a player is playing on.
 *
 **/

public class Board {

  private Player me;
  private Player opponent;
  private Chip[][] boardGrid;
  
  // Board constructor
  public Board() {
    boardGrid = new Chip[8][8];
  }

  // Checks if Move m is valid with regards to "this" board.
  // Rules go as follow:
  //   1)  No chip may be placed in any of the four corners. 
  //   2)  No chip may be placed in a goal of the opposite color.
  //   3)  No chip may be placed in a square that is already occupied.
  //   4)  A player may not have more than two chips in a connected group, whether 
  //       connected orthogonally or diagonally.
  //
  // Parameters:
  //   m: an instance of the Move class
  //   color: the color of the player we're seeing if m is a valid move for
  //
  // Return Value:
  //   Returns True is Move m is valid
  //   Returns False if Move m is invalid
  //
  // Other methods that rely on this method:
  //   validMoveList()
  //   MachinePlayer.opponentMove()
  //   MachinePlayer.forceMove()
  //
  // Person in charge: Eric Hum
  public boolean isValidMove(Move m, int color) {
    Chip testChip = new Chip(m.x1, m.y1, color);
    if (boardGrid[m.x1][m.y1] != null) { // checks if a chip is already at coordinate
      return false;
    }
    if (m.x1 == 0 || m.x1 == boardGrid.length()-1) { // checks if a chip is in one set of possible opponent's goals
      if (m.y1 == 0 || m.y1 == boardGrid[0].length()-1) { // checks corners
        return false;
      }
      for (int i == 1; i < boardGrid.length()-1; i++) {
        if ((boardGrid[0][i] != null && boardGrid[0][i].getColor() != testChip.color) ||
            (boardGrid[boardGrid.length()-1][i] != null && boardGrid[boardGrid.length()-1][i].getColor() != testChip.color)) {
          return false;
        }
      }
    }
    if (m.y1 == 0 || m.y1 == boardGrid[0].length()-1) { // checks if a chip is in other set of possible opponent's goals
      for (int i == 1; i < boardGrid.length()-1; i++) {
        if ((boardGrid[i][0] != null && boardGrid[i][0].getColor() != testChip.color) ||
            (boardGrid[i][boardGrid[0].length()-1] != null && boardGrid[i][boardGrid[0].length()-1].getColor() != testChip.color)) {
          return false;
        }
      }
    }
    if (m.moveKind == player.Move.ADD) {
      int neighbors = testChip.neighbors(boardGrid);
      if (neighbors > 1) { // checks if has two or more neighbors of the same color, or neighbors' neighbors
        return false;
      }
    }
    if (m.moveKind == player.Move.STEP) {
      Chip tempRemoveChip = boardGrid[m.x2][m.y2];
      boardGrid[m.x2][m.y2] == null;
      int neighbors = testChip.neighbors(boardGrid);
      boardGrid[m.x2][m.y2] == tempRemoveChip;
      if (neighbors > 1) { // checks if has two or more neighors of the same color, or neighbors' neighbors
        return false;
      }
    }
    testChip = null;
    return true;
  }

  
  // Creates a list of all x and y coordinates that are open to valid moves
  //
  // Parameters:
  //   color: the color of the player we're searching a list of valid moves for
  //
  // Return Value:
  //   An array of all possible Moves
  //
  // Other methods that rely on this method:
  //    minimax.minimax()
  //    evaluator()
  //
  // Person in charge: Nikhil Rajpal
  public Move[] validMoveList(int color) {
  }
  
  // Checks to see if "this" board contains a winning network for the "color", as long as
  // it follows the following rules:
  //    1. All moves are valid
  //    2. Only 1 Chip in either goal
  //    3. A proper connection from one goal to the other
  //    4. At least 6 chips help form the connection
  //    5. There are no opponent chips in between a straight line between two chips to break the connection
  //    6. The network does not pass through the same chip twice
  //    7. A network always passes through each chip while turning a corner (ie changing direction at each chip in network)
  //
  // Parameters:
  //    color: the color of the player we're searching a winning network for
  //
  // Return Value:
  //    Returns True if there does exist a network for a player on "this" board
  //    Returns False if there is not a network for a player on "this" board
  //
  // Other methods that rely on this method:
  //    evaluator()
  //    minimax()
  public boolean winningNetwork(int color) {
  }
  
  // Creates a score for a given "color" based on "this" board
  //
  // Parameters:
  //    color: the color of the player we're evaluating the board for
  //
  // Return Value:
  //    Returns a score, high score if favorable 
  //
  // Other methods that rely on this method:
  //    minimax()
  //    
  public int evaluator(int color) {
  }
  
  public Chip[][] getBoardGrid() {
    return boardGrid;
  }
  
  public void setBoardGrid(Move move) {
    if (move.moveKind == player.Move.STEP) {
      boardGrid[move.x2][move.y2] = null;
    }
    boardGrid[move.x1][move.y1] = move;
  }
  
}


