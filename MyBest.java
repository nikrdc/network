/* MyBest.java */

public class MyBest {
  
  public player.Move move;
  public int score;
  public int searchDepth;
  
  public MyBest(player.Move m, int score1, int sd) {
    move = m;
    score = score1;
    searchDepth = sd;
  }
}