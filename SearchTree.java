/* SearchTree.java */

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
    return minimax_helper(board, searchDepth, color, color, -100, 100);
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
  //
  // Return Value:
  //    Returns an object array with the Move that is optimal for the CurrentPlayer given the board and the search depth, and the score of that move
  //
  // Other methods that rely on this method:
  //    evaluator:
  //
  // Person in charge: Eric Hum
  private MyBest minimax_helper(Board board, int searchDepth, int originalColor, int currentColor, int alpha, int beta) {
    try {
      if ((searchDepth == 0) || (board.winningNetwork(currentColor))) {
        MyBest temp;
        if (currentColor == originalColor) {
          temp = new MyBest(null, board.evaluator(currentColor), 0);
        } else {
          temp = new MyBest(null, -(board.evaluator(currentColor)), 0);
        }
        return temp;
      }
      if ((board.winningNetwork(originalColor))) {
        MyBest temp = new MyBest(null, board.evaluator(originalColor), 0);
        return temp;
      }
      list.List movesList = board.validMoveList(currentColor);
      list.ListNode node = movesList.front();
      MyBest myBest = new MyBest(null, 0, 0);
      MyBest reply;
      if (currentColor == originalColor) {
        myBest.score = alpha;
      } else {
        myBest.score = beta;
      }
      myBest.move = ((player.Move) node.item());
      while (node != null && node.item() != null) {
        board.setBoardGrid(((player.Move) node.item()), currentColor);
        reply = minimax_helper(board, searchDepth-1, originalColor, Math.abs(currentColor-1), alpha, beta);
        board.undoMove(((player.Move) node.item()), currentColor);
        if ((currentColor == originalColor) &&
            (reply.score > myBest.score) || 
            (currentColor == originalColor) &&
            (reply.score == myBest.score) &&
            (searchDepth > myBest.searchDepth)) {
          myBest.move = ((player.Move) node.item());
          myBest.score = reply.score;
          myBest.searchDepth = searchDepth;
          alpha = reply.score;
        } else if ((currentColor != originalColor) &&
                   (-(reply.score) < myBest.score)) {
          myBest.move = ((player.Move) node.item());
          myBest.score = -(reply.score);
          myBest.searchDepth = searchDepth;
          beta = -(reply.score);
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
      System.out.println(e);
      return new MyBest(null, 0, 0);
    }
  }
    
  
  public static void main(String[] args) {
    // Testing minimax
    System.out.println("Testing Minimax\n");
    Board test1 = new Board();
    SearchTree tree1 = new SearchTree();
    MyBest bestMove = tree1.minimax(test1, 3, 1);
    test1.setBoardGrid(bestMove.move, 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.move);
    System.out.println("score: " + bestMove.score);
    bestMove = tree1.minimax(test1, 3, 0);
    test1.setBoardGrid(bestMove.move, 0);
    System.out.println(test1);
    System.out.println("move: " + bestMove.move);
    System.out.println("score: " + bestMove.score);
    bestMove = tree1.minimax(test1, 3, 1);
    test1.setBoardGrid(bestMove.move, 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.move);
    System.out.println("score: " + bestMove.score);
    bestMove = tree1.minimax(test1, 3, 1);
    test1.setBoardGrid(bestMove.move, 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.move);
    System.out.println("score: " + bestMove.score);
    bestMove = tree1.minimax(test1, 3, 1);
    test1.setBoardGrid(bestMove.move, 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.move);
    System.out.println("score: " + bestMove.score);
    bestMove = tree1.minimax(test1, 3, 1);
    test1.setBoardGrid(bestMove.move, 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.move);
    System.out.println("score: " + bestMove.score);
    bestMove = tree1.minimax(test1, 3, 1);
    test1.setBoardGrid(bestMove.move, 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.move);
    System.out.println("score: " + bestMove.score);
    System.out.println("");
    
    // New test
    System.out.println("New Test 2\n");
    test1 = new Board();
    bestMove = tree1.minimax(test1, 1, 1);
    test1.setBoardGrid(bestMove.move, 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.move);
    System.out.println("score: " + bestMove.score);
    bestMove = tree1.minimax(test1, 1, 1);
    test1.setBoardGrid(bestMove.move, 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.move);
    System.out.println("score: " + bestMove.score);
    bestMove = tree1.minimax(test1, 1, 1);
    test1.setBoardGrid(bestMove.move, 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.move);
    System.out.println("score: " + bestMove.score);
    bestMove = tree1.minimax(test1, 1, 1);
    test1.setBoardGrid(bestMove.move, 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.move);
    System.out.println("score: " + bestMove.score);
    bestMove = tree1.minimax(test1, 1, 1);
    test1.setBoardGrid(bestMove.move, 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.move);
    System.out.println("score: " + bestMove.score);
    bestMove = tree1.minimax(test1, 1, 1);
    test1.setBoardGrid(bestMove.move, 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.move);
    System.out.println("score: " + bestMove.score);
    System.out.println(test1.winningNetwork(1));
    System.out.println(test1.winningNetwork(0));
    System.out.println("");
    
    // New test
    System.out.println("New Test 3\n");
    test1 = new Board();
    player.Move testMove = new player.Move(0, 2);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    testMove = new player.Move(0, 1);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    testMove = new player.Move(0, 5);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    testMove = new player.Move(7, 1);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    testMove = new player.Move(3, 5);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    bestMove = tree1.minimax(test1, 2, 1);
    test1.setBoardGrid(bestMove.move, 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.move);
    System.out.println("score: " + bestMove.score);
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
    testMove = new player.Move(2, 3);
    test1.setBoardGrid(testMove, 0);
    System.out.println(test1);
    bestMove = tree1.minimax(test1, 2, 0);
    test1.setBoardGrid(bestMove.move, 0);
    System.out.println(test1);
    System.out.println("move: " + bestMove.move);
    System.out.println("score: " + bestMove.score);
    System.out.println("");
    
    // New test
    System.out.println("New Test 5\n");
    test1 = new Board();
    testMove = new player.Move(0, 2);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    testMove = new player.Move(0, 1);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    testMove = new player.Move(0, 5);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    testMove = new player.Move(7, 1);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    testMove = new player.Move(3, 5);
    test1.setBoardGrid(testMove, 1);
    System.out.println(test1);
    bestMove = tree1.minimax(test1, 2, 0);
    test1.setBoardGrid(bestMove.move, 0);
    System.out.println(test1);
    System.out.println("move: " + bestMove.move);
    System.out.println("score: " + bestMove.score);
    bestMove = tree1.minimax(test1, 2, 0);
    test1.setBoardGrid(bestMove.move, 0);
    System.out.println(test1);
    System.out.println("move: " + bestMove.move);
    System.out.println("score: " + bestMove.score);
    bestMove = tree1.minimax(test1, 2, 1);
    test1.setBoardGrid(bestMove.move, 1);
    System.out.println(test1);
    System.out.println("move: " + bestMove.move);
    System.out.println("score: " + bestMove.score);
    System.out.println("");
    
    /**
    // New test
    System.out.println("New Test infinite loop\n");
    test1 = new Board();
    int turns = 0;
    while ((!test1.winningNetwork(1) && !test1.winningNetwork(0)) && turns < 30) {
      bestMove = tree1.minimax(test1, 2, 1);
      test1.setBoardGrid(bestMove.move, 1);
      System.out.println(test1);
      turns++;
      if (test1.winningNetwork(1)) {
        break;
      }
      bestMove = tree1.minimax(test1, 2, 0);
      test1.setBoardGrid(bestMove.move, 0);
      System.out.println(test1);
      turns++;
    }
    System.out.println(test1.winningNetwork(1));
    System.out.println(test1.winningNetwork(0));
    System.out.println(turns);
    **/
  }

}