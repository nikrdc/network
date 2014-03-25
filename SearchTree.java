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
    try {
      if (searchDepth == -1) {
        Object[] temp = new Object[3];
        temp[1] = board.evaluator(currentColor);
        return temp;
      }
      if (board.winningNetwork(currentColor)) {
        Object[] temp = new Object[3];
        temp[1] = board.evaluator(currentColor);
        return temp;
      }
      list.List movesList = board.validMoveList(currentColor);
      list.ListNode node = movesList.front();
      Object[] myBest = new Object[3];
      Object[] reply;
      if (currentColor == originalColor) {
        myBest[1] = alpha;
      } else {
        myBest[1] = beta;
      }
      myBest[0] = ((player.Move) node.item());
      while (node != null && node.item() != null) {
        board.setBoardGrid(((player.Move) node.item()), currentColor);
        reply = minimax(board, searchDepth-1, originalColor, Math.abs(currentColor-1), alpha, beta);
        board.undoMove(((player.Move) node.item()), currentColor);
        if ((currentColor == originalColor) &&
            (((Integer) reply[1]) > ((Integer) myBest[1])) || 
            (currentColor == originalColor) &&
            (((Integer) reply[1] == ((Integer) myBest[1]))) &&
            (searchDepth > ((Integer) myBest[2]))) {
          myBest[0] = ((player.Move) node.item());
          myBest[1] = reply[1];
          myBest[2] = searchDepth;
          alpha = ((Integer) reply[1]);
        } else if ((currentColor != originalColor) &&
                   (((Integer) reply[1]) < ((Integer) myBest[1]))) {
          myBest[0] = ((player.Move) node.item());
          myBest[1] = reply[1];
          myBest[2] = searchDepth;
          beta = ((Integer) reply[1]);
        }
        if (alpha >= beta) { 
          return myBest; 
        }
        node = node.next();
      }
      return myBest;
    } catch (list.InvalidNodeException e) {
      return new Object[1];
    }
  }
    
  
  public static void main(String[] args) {
    // Testing minimax
    System.out.println("Testing Minimax\n");
    Board test1 = new Board();
    SearchTree tree1 = new SearchTree();
    Object[] bestMove = tree1.minimax(test1, 1, 1, 1, -10, 10);
    test1.setBoardGrid(((player.Move) bestMove[0]), 1);
    System.out.println(bestMove[0]);
    System.out.println(bestMove[1]);
    System.out.println(bestMove[2]);
    System.out.println(test1);
    bestMove = tree1.minimax(test1, 0, 0, 0, -10, 10);
    test1.setBoardGrid(((player.Move) bestMove[0]), 0);
    System.out.println(bestMove[0]);
    System.out.println(bestMove[1]);
    System.out.println(bestMove[2]);
    System.out.println(test1);
  }

}