/* MachinePlayer.java */

package player;

import gameboard.*;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 *
 * People in Charge of entire class: Eric Hum, Max Eusterbrock, Nikhil Rajpal
 */
public class MachinePlayer extends Player {

  private int color;
  private int opponentColor;
  private Board board;
  private int searchDepth;

  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
    this.color = color;
    this.opponentColor = Math.abs(color-1);
    searchDepth = 3;
    board = new Board();
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
    this(color);
    this.searchDepth = searchDepth;
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
    SearchTree tree = new SearchTree();
    MyBest bestMove = tree.minimax(board, searchDepth, color);
    Move chosenMove = bestMove.getMove();
    board.setBoardGrid(chosenMove, color);
    return chosenMove;
  } 

  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
  public boolean opponentMove(Move m) {
    boolean move = board.isValidMove(m, opponentColor);
    if (move == true) {
      board.setBoardGrid(m, opponentColor);
      return true;
    } else {
      return false;
    }
  }

  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  public boolean forceMove(Move m) {
    boolean move = board.isValidMove(m, color);
    if (move == true) {
      board.setBoardGrid(m, color);
      return true;
    } else {
      return false;
    }
  }
  
  // Gets the color of MachinePlayer
  public int getColor() {
    return color;
  }

}
