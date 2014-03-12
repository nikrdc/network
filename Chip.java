/* Chip.java */

/** An implementation of the chips placed on the board
 *
 *  Person in charge of entire class: Max Eusterbrock
 */
public class Chip {

  private int x;
  private int y;
  private int color;
  
  // Returns an array of Chips that are directly next to "this" Chip
  public Chip(int x, int y, int color) {
    this.x = x;
    this.y = y;
    this.color = color;
  
  // Finds the neighbors of "this" chip
  //
  // Parameters:
  //    none
  //
  // Return Value:
  //    An array of Chips that are directly connected to "this" chip. ie, the neighbors
  //    are directly adjacent to "this" chip.
  public Chip[] directConnections() {
  }
  
  // find the neighbors of "this" chip, direct neighbors and secondary neighbors
  //
  // Parameters:
  //    boardGrid: the board on which the Chip is bein referred to
  //
  // return Value:
  //    The number of immediate neighbors, and the other neighbors of those neighbors
  public int neighbors(Chip[][] boardGrid) {
    int counter = 0
    for (int w = x-1; w <= x+1; w++) {
      if (w < 0 || w > boardGrid.length()-1) {
        continue;
      }
      for (int h = y-1; h <= y+1; h++) {
        if (h < 0 || h > boardGrid[0].length()-1) {
          continue;
        } else if (boardGrid[w][h] != null && boardGrid[w][h].color == color) {
          counter += 1 + board[w][h].neighbors(boardGrid);
        }
      }
    }
    return counter;
  }
  
  public int getColor() {
    return color;
  }
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
}