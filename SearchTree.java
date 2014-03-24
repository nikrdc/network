/* SearchTree.java */

public class SearchTree {

  // Does a search tree algorithm that finds the best possible move based on the board and the number of searchDepth
  //
  // Parameters:
  //    board: the current board minimax is analyzing
  //    searchDepth: the number of recursions of minimax
  //    color: the player that is currently being minimaxed (either CurrentPlayer or Opponent)
  //    alpha: the score that the  CurrentPlayer knows with certainty it can achieve
  //    beta: the score that the Opponent is the worst score the Opponent is guarateed to attain
  //
  // Return Value:
  //    Returns an object array with the Move that is optimal for the CurrentPlayer given the board and the search depth, and the score of that move
  //
  // Other methods that rely on this method:
  //    evaluator:
  //
  // Person in charge: Eric Hum
  public Object[] minimax(Board board, int searchDepth, int originalColor, int currentColor, int alpha, int beta) {
    if (searchDepth == 0) {
      Object[] temp = new Object[2];
      Object[1] = board.evaluator(currentColor);
      Object[0] = new player.Move Move();
      return temp;
    }
    player.Move[] movesList = board.validMoveList(color);
    Object[] myBest = new Object[2];
    Object[] reply;
    if (board.winningNetwork(currentColor)) {
      Object[] temp = new Object[2];
      Object[1] = board.evaluator(currentColor);
      Object[0] = new player.Move Move();
      return temp;
    }
    if (currentColor == originalColor) {
      myBest[1] = alpha;
    } else {
      myBest[1] = beta;
    }
    for (player.Move move : movesList) {
      board.setBoardGrid(move, currentColor);
      reply = minimax(board, searchDepth-1, originalColor, Math.abs(currentColor-1), alpha, beta);
      board.undoMove(move, currentColor);
      if ((currentColor == originalColor) &&
          (((Integer) reply.score[1]) >= ((Integer) myBest[1]))) {
        myBest[0] = move;
        myBest[1] = reply[1];
        alpha = ((Integer) reply[1]);
      } else if ((currentColor != originalColor) &&
                 (((Integer) reply.score[1]) <= ((Integer) myBest[1]))) {
        myBest[0] = move;
        myBest[1] = reply[1];
        beta = ((Integer) reply[1]);
      }
      if (alpha >= beta) { 
        return myBest; 
      }
    }
    return myBest;
  }

}