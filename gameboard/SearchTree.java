/* SearchTree.java */

package gameboard;

public class SearchTree {

  // Does a search tree algorithm that finds the best possible move based on the board and the number of searchDepth
  //
  // Parameters:
  //    board: the current board minimax is analyzing
  //    searchDepth: the number of recursions of minimax
  //    color: the player that is currently being minimaxed (either CurrentPlayer or Opponent)
  //
  // Return Value:
  //    Returns an object array with the Move that is optimal for the CurrentPlayer given the board and the search depth, and the score of that move
  //
  // Other methods that rely on this method:
  //    evaluator:
  //
  // Person in charge: Eric Hum
  public MyBest minimax(Board board, int searchDepth, int color) {
    return minimax_helper(board, searchDepth, color, color, -100, 100, 0);
  }
  
  // Helps minimax in performing the search
  //
  // Parameters:
  //    board: the current board minimax is analyzing
  //    searchDepth: the number of recursions of minimax
  //    originalColor: the player that was originally being minimaxed (the CurrentPlayer)
  //    currentColor: the player that is currently being minimaxed (either CurrentPlayer or Opponent)
  //    alpha: the score that the  CurrentPlayer knows with certainty it can achieve
  //    beta: the score that the Opponent is the worst score the Opponent is guarateed to attain
  //    recursion: the number of recursions the minimax function has undergone
  //
  // Return Value:
  //    Returns an object array with the Move that is optimal for the CurrentPlayer given the board and the search depth, and the score of that move
  //
  // Other methods that rely on this method:
  //    evaluator:
  //
  // Person in charge: Eric Hum
  private MyBest minimax_helper(Board board, int searchDepth, int originalColor, int currentColor, int alpha, int beta, int recursions) {
    try {
      if ((searchDepth == 0) || (board.winningNetwork(currentColor)) || (board.winningNetwork(Math.abs(currentColor-1)))){
        if (currentColor == originalColor) {
          return new MyBest(null, -(board.evaluator(Math.abs(currentColor-1)) - 2*(recursions-1)), searchDepth);
        } else {
          return new MyBest(null, board.evaluator(Math.abs(currentColor-1)) - 2*(recursions-1), searchDepth);
        }
      }
      list.List movesList = board.validMoveList(currentColor);
      list.ListNode node = movesList.front();
      MyBest myBest = new MyBest(null, 0, 0);
      MyBest reply;
      if (currentColor == originalColor) {
        myBest.setScore(alpha);
      } else {
        myBest.setScore(beta);
      }
      myBest.setMove((player.Move) node.item());
      while (node != null && node.item() != null) {
        board.setBoardGrid(((player.Move) node.item()), currentColor);
        reply = minimax_helper(board, searchDepth-1, originalColor, Math.abs(currentColor-1), alpha, beta, recursions+1);
        board.undoMove(((player.Move) node.item()), currentColor);
        if (((currentColor == originalColor) &&
            (reply.getScore() > myBest.getScore())) || 
            ((currentColor == originalColor) &&
            (reply.getScore() >= myBest.getScore()) &&
            (reply.getSearchDepth() > myBest.getSearchDepth()))) {
          myBest.setMove((player.Move) node.item());
          myBest.setScore(reply.getScore());
          myBest.setSearchDepth(reply.getSearchDepth());
          alpha = reply.getScore();
        } else if ((currentColor != originalColor) &&
                   (reply.getScore() < myBest.getScore())) {
          myBest.setMove((player.Move) node.item());
          myBest.setScore(reply.getScore());
          myBest.setSearchDepth(reply.getSearchDepth());
          beta = reply.getScore();
        }
        if (alpha >= beta) { 
          return myBest; 
        }
        if (!node.next().isValidNode()) {
          break;
        }
        node = node.next();
      }
      return myBest;
    } catch (list.InvalidNodeException e) {
      return new MyBest(null, 0, 0);
    }
  }
    
  
  public static void main(String[] args) {
  
    Board test1 = new Board();
    SearchTree tree1 = new SearchTree();
    player.Move testMove;
    MyBest bestMove;
    System.out.println("Test1");
    testMove = new player.Move(1, 1);
    test1.setBoardGrid(testMove, 0);
    testMove = new player.Move(2, 1);
    test1.setBoardGrid(testMove, 0);
    testMove = new player.Move(4, 1);
    test1.setBoardGrid(testMove, 0);
    testMove = new player.Move(5, 1);
    test1.setBoardGrid(testMove, 0);
    testMove = new player.Move(0, 2);
    test1.setBoardGrid(testMove, 1);
    testMove = new player.Move(1, 2);
    test1.setBoardGrid(testMove, 1);
    testMove = new player.Move(4, 2);
    test1.setBoardGrid(testMove, 1);
    testMove = new player.Move(1, 5);
    test1.setBoardGrid(testMove, 1);
    testMove = new player.Move(4, 5);
    test1.setBoardGrid(testMove, 1);
    testMove = new player.Move(1, 6);
    test1.setBoardGrid(testMove, 0);
    System.out.println(test1);
    bestMove = tree1.minimax(test1, 3, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    System.out.println(test1);
    
    
    System.out.println("Test1");
    test1 = new Board();
    testMove = new player.Move(0, 3);
    test1.setBoardGrid(testMove, 1);
    testMove = new player.Move(0, 5);
    test1.setBoardGrid(testMove, 1);
    testMove = new player.Move(2, 3);
    test1.setBoardGrid(testMove, 1);
    testMove = new player.Move(2, 4);
    test1.setBoardGrid(testMove, 1);
    testMove = new player.Move(4, 6);
    test1.setBoardGrid(testMove, 1);
    testMove = new player.Move(3, 3);
    test1.setBoardGrid(testMove, 0);
    testMove = new player.Move(3, 4);
    test1.setBoardGrid(testMove, 0);
    testMove = new player.Move(5, 5);
    test1.setBoardGrid(testMove, 0);
    testMove = new player.Move(5, 6);
    test1.setBoardGrid(testMove, 0);
    testMove = new player.Move(6, 3);
    test1.setBoardGrid(testMove, 0);
    System.out.println(test1);
    bestMove = tree1.minimax(test1, 3, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    System.out.println(test1);
    
  
    
    // Testing minimax
    System.out.println("Testing Minimax\n");
    test1 = new Board();
    tree1 = new SearchTree();
    bestMove = tree1.minimax(test1, 3, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    bestMove = tree1.minimax(test1, 3, 0);
    test1.setBoardGrid(bestMove.getMove(), 0);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    bestMove = tree1.minimax(test1, 3, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    bestMove = tree1.minimax(test1, 3, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    bestMove = tree1.minimax(test1, 3, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    bestMove = tree1.minimax(test1, 3, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    bestMove = tree1.minimax(test1, 3, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    System.out.println("");
    
    // New test
    System.out.println("New Test 2\n");
    test1 = new Board();
    bestMove = tree1.minimax(test1, 1, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    bestMove = tree1.minimax(test1, 1, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    bestMove = tree1.minimax(test1, 1, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    bestMove = tree1.minimax(test1, 1, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    bestMove = tree1.minimax(test1, 1, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    bestMove = tree1.minimax(test1, 1, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    bestMove = tree1.minimax(test1, 1, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    bestMove = tree1.minimax(test1, 1, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    System.out.println(test1.winningNetwork(1));
    System.out.println(test1.winningNetwork(0));
    System.out.println("");
    
    // New test
    System.out.println("New Test 3\n");
    test1 = new Board();
    testMove = new player.Move(0, 2);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    testMove = new player.Move(1, 1);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    testMove = new player.Move(3, 3);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    testMove = new player.Move(4, 5);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    testMove = new player.Move(3, 5);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    bestMove = tree1.minimax(test1, 2, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    System.out.println("");
    
    // New test
    System.out.println("New Test 4\n");
    test1 = new Board();
    testMove = new player.Move(2, 0);
    test1.setBoardGrid(testMove, 0);
    System.out.println(test1);
    testMove = new player.Move(1, 0);
    test1.setBoardGrid(testMove, 0);
    System.out.println(test1);
    testMove = new player.Move(5, 0);
    test1.setBoardGrid(testMove, 0);
    System.out.println(test1);
    testMove = new player.Move(6, 7);
    test1.setBoardGrid(testMove, 0);
    System.out.println(test1);
    bestMove = tree1.minimax(test1, 2, 0);
    test1.setBoardGrid(bestMove.getMove(), 0);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    bestMove = tree1.minimax(test1, 2, 0);
    test1.setBoardGrid(bestMove.getMove(), 0);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    System.out.println("");
    
    // New test
    System.out.println("New Test 5\n");
    test1 = new Board();
    testMove = new player.Move(0, 2);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    testMove = new player.Move(1, 1);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    testMove = new player.Move(3, 3);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    testMove = new player.Move(4, 5);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    testMove = new player.Move(3, 5);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    bestMove = tree1.minimax(test1, 2, 0);
    test1.setBoardGrid(bestMove.getMove(), 0);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    bestMove = tree1.minimax(test1, 2, 0);
    test1.setBoardGrid(bestMove.getMove(), 0);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    bestMove = tree1.minimax(test1, 2, 0);
    test1.setBoardGrid(bestMove.getMove(), 0);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    bestMove = tree1.minimax(test1, 2, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    bestMove = tree1.minimax(test1, 2, 0);
    test1.setBoardGrid(bestMove.getMove(), 0);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    bestMove = tree1.minimax(test1, 2, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    System.out.println("");
    
    // New test
    System.out.println("New Test 6\n");
    test1 = new Board();
    testMove = new player.Move(0, 2);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    testMove = new player.Move(1, 6);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    testMove = new player.Move(4, 6);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    testMove = new player.Move(4, 3);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    bestMove = tree1.minimax(test1, 2, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    System.out.println("");
    
    // New test
    System.out.println("New Test 7\n");
    test1 = new Board();
    testMove = new player.Move(0, 2);
    test1.setBoardGrid(testMove, 1);
    testMove = new player.Move(2, 4);
    test1.setBoardGrid(testMove, 1);
    testMove = new player.Move(4, 2);
    test1.setBoardGrid(testMove, 1);
    testMove = new player.Move(4, 6);
    test1.setBoardGrid(testMove, 1);
    testMove = new player.Move(7, 6);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    bestMove = tree1.minimax(test1, 3, 1);
    test1.setBoardGrid(bestMove.getMove(), 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.getMove());
    System.out.println("score: " + bestMove.getScore());
    System.out.println(test1.winningNetwork(1));
    System.out.println("");
    
    
    // New test
    System.out.println("New Test infinite loop\n");
    test1 = new Board();
    int turns = 0;
    while ((!test1.winningNetwork(1) && !test1.winningNetwork(0)) && turns < 30) {
      bestMove = tree1.minimax(test1, 2, 1);
      test1.setBoardGrid(bestMove.getMove(), 1);
      System.out.println(test1);
      turns++;
      if (test1.winningNetwork(1)) {
        break;
      }
      bestMove = tree1.minimax(test1, 2, 0);
      test1.setBoardGrid(bestMove.getMove(), 0);
      System.out.println(test1);
      turns++;
    }
    System.out.println(test1.winningNetwork(1));
    System.out.println(test1.winningNetwork(0));
    System.out.println(turns);
    
    
  }

}