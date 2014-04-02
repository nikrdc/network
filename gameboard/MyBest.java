/* MyBest.java */

package gameboard;

public class MyBest {
  
  private player.Move move;
  private int score;
  private int searchDepth;
  
  // MyBest constructor with 3 parameters
  //
  // Parameters:
  //    m: the move of the best possible move
  //    score1: the score of the best possible move
  //    sd: the searchDepth of the best possible move
  //
  // Return Value:
  //    a newly constructed MyBest object
  public MyBest(player.Move m, int score1, int sd) {
    move = m;
    score = score1;
    searchDepth = sd;
  }
  
  // MyBest constructor with 0 parameters
  //
  // Parameters:
  //    none, gives default values to fields
  //
  // Return Value:
  //    a newly constructed MyBest object
  public MyBest() {
    move = null;
    score = 0;
    searchDepth = 0;
  }
  
  // Gets the move within the MyBest object
  //
  // Parameters:
  //    none
  //
  // Return Value:
  //    the move with the MyBest object
  public player.Move getMove() {
    return move;
  }
  
  // Sets or changes the move within the MyBest object
  //
  // Parameters:
  //    m: the move you want to change the field within the MyBest obejct to
  //
  // Return Value:
  //    none
  public void setMove(player.Move m) {
    move = m;
  }
  
  // Gets the score within the MyBest object
  //
  // Parameters:
  //    none
  //
  // Return Value:
  //    the score with the MyBest object
  public int getScore() {
    return score;
  }
  
  // Sets or changes the score within the MyBest object
  //
  // Parameters:
  //    score1: the score you want to change the field within the MyBest obejct to
  //
  // Return Value:
  //    none
  public void setScore(int score1) {
    score = score1;
  }
  
  // Gets the searchDepth within the MyBest object
  //
  // Parameters:
  //    none
  //
  // Return Value:
  //    the searchDepth with the MyBest object
  public int getSearchDepth() {
    return searchDepth;
  }
  
  // Sets or changes the searchDepth within the MyBest object
  //
  // Parameters:
  //    sd: the searchDepth you want to change the field within the MyBest obejct to
  //
  // Return Value:
  //    none
  public void setSearchDepth(int sd) {
    searchDepth = sd;
  }
}