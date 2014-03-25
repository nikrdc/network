/* Board.java */

import list.*;
import player.*;

/**
 * Implementation of the Board that a player is playing on.
 *
 **/

public class Board {

  public final static int LENGTH = 8;
  private player.Player me;
  private player.Player opponent;
  private int num0;
  private int num1;
  private int[][] boardGrid;
  private int length;
  
  // Board constructor
  public Board() {
    boardGrid = new int[LENGTH][LENGTH];
    for (int i = 0; i < LENGTH; i++) {
      for (int j = 0; j < LENGTH; j++) {
        boardGrid[i][j] = 2;
      }
    }
    length = LENGTH;
    num0 = 0;
    num1 = 0;
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
  // Methods used:
  //   getNumChips()
  //   setNumChips()
  //   neighbors()
  //   neighborsHelper()
  //
  //
  // Person in charge: Eric Hum
  public boolean isValidMove(player.Move m, int color) {
    if (boardGrid[m.x1][m.y1] != 2) { // checks if a chip is already at coordinate
      return false;
    }
    if (m.x1 == 0 || m.x1 == length()-1) { // checks if a chip is in opponent's goals for color 0
      if (m.y1 == 0 || m.y1 == length()-1) { // checks corners
        return false;
      }
      if (color == 0) {
        return false;
      }
    }
    if (m.y1 == 0 || m.y1 == length()-1) { // checks if a chip is in opponent's goals for color 1
      if (m.x1 == 0 || m.x1 == length()-1) { // checks corners
        return false;
      }
      if (color == 1) {
        return false;
      }
    }
    if (m.moveKind == player.Move.ADD) {
      int neighbors = this.neighbors(m.x1, m.y1, color);
      if (neighbors > 1) { // checks if has two or more neighbors of the same color, or neighbors' neighbors
        return false;
      }
      if (getNumChips(color) >= 10) { // checks if less than 10 chips of color on board
        return false;
      }
    }
    if (m.moveKind == player.Move.STEP) {
      if (boardGrid[m.x2][m.y2] == 2 || boardGrid[m.x2][m.y2] != color) { // checks if attempting step move with chip of different color or empty spot
        return false;
      }
      int tempRemoveChip = boardGrid[m.x2][m.y2];
      boardGrid[m.x2][m.y2] = 2;
      int neighbors = this.neighbors(m.x1, m.y1, color);
      boardGrid[m.x2][m.y2] = tempRemoveChip;
      if (neighbors > 1) { // checks if has two or more neighors of the same color, or neighbors' neighbors
        return false;
      }
      if (getNumChips(color) < 10) { // checks if more than or equal to 10 chips of color on board
        return false;
      }
    }
    return true;
  }
  
  // Gets the number of chips on the board for a particular color
  private int getNumChips(int color) {
    if (color == 0) {
      return num0;
    } else if (color == 1) {
      return num1;
    }  else {
      return 0;
    }
  }
  
  // Sets the number of chips on the board for a particular color
  private void setNumChips(int color, String op) {
    if (color == 0) {
      if (op == "+") {
        num0++;
      } else if (op == "-") {
        num0--;
      } else {
        num0 = num0;
      }
    } else if (color == 1) {
      if (op == "+") {
        num1++;
      } else if (op == "-") {
        num1--;
      } else {
        num1 = num1;
      }
    } else {
    }
  }
  
  // Finds the number neighbors, and the neighbors neighbors
  public int neighbors(int x, int y, int color) {
    int[][] list = new int[3][2];
    list[0][0] = x;
    list[0][1] = y;
    list[1][0] = -1;
    list[1][1] = -1;
    list[2][0] = -1;
    list[2][1] = -1;
    return neighborsHelper(x, y, color, list);
  }
  
  // Aids in the neighbors search
  private int neighborsHelper(int x, int y, int color, int[][] neighborsList) {
    int counter = 0;
    for (int w = x-1; w <= x+1; w++) {
      if (w < 0 || w > LENGTH-1) {
        continue;
      }
      for (int h = y-1; h <= y+1; h++) {
        if (h < 0 || h > LENGTH-1) {
          continue;
        } else if (boardGrid[w][h] == color) {
          counter += 1;
          if ((neighborsList[0][0] == w && neighborsList[0][1] == h) ||
              (neighborsList[1][0] == w && neighborsList[1][1] == h)) {
            counter -= 1;
          } else if (neighborsList[1][0] == -1) {
            neighborsList[1][0] = w;
            neighborsList[1][1] = h;
          } else {
            neighborsList[2][0] = w;
            neighborsList[2][1] = h;
          }
        }
      }
    }
    if (counter == 1 && neighborsList[2][0] == -1) {
      counter += this.neighborsHelper(neighborsList[1][0], neighborsList[1][1], color, neighborsList);
    }
    return counter;
  }

  
  // Creates a list of all valid moves
  //
  // Parameters:
  //   color: the color of the player we're searching a list of valid moves for
  //
  // Return Value:
  //   A DList of all valid Moves
  //
  // Other methods that rely on this method:
  //    minimax.minimax()
  //    evaluator()
  //
  // Person in charge: Nikhil Rajpal
  public DList validMoveList(int color) {
    DList validMoves = new DList();
    try {
      if (getNumChips(color) >= 10) {
        DList emptySpots = new DList();
        DList colorSpots = new DList();
        for (int i = 0; i < length; i++) {
          for (int j = 0; j < length; j++) {
            int[] position = new int[2];
            position[0] = i;
            position[1] = j;
            if (boardGrid[i][j] == 2) {
              emptySpots.insertBack(position);
            } else if (boardGrid[i][j] == color) {
              colorSpots.insertBack(position);
            }
          }
        }
        DListNode colorStart = (DListNode) colorSpots.front();
        for (int k = 0; k < colorSpots.length(); k++) {
          DListNode emptyEnd = (DListNode) emptySpots.front();
          for (int t = 0; t < emptySpots.length(); t++) {
            int[] startPosition = (int[]) colorStart.item();
            int[] endPosition = (int[]) emptyEnd.item();
            Move potentialStep = new Move(endPosition[0], endPosition[1], startPosition[0], startPosition[1]);
            if (isValidMove(potentialStep, color)) {
              validMoves.insertBack(potentialStep);
            }
            emptyEnd = (DListNode) emptyEnd.next();
          }
          colorStart = (DListNode) colorStart.next();
        }
      } else {
        for (int i = 0; i < length; i++) {
          for (int j = 0; j < length; j++) {
            Move potentialAdd = new Move(j, i);
            if (isValidMove(potentialAdd, color)) {
              validMoves.insertBack(potentialAdd);
            }
          }
        }
      }
    } catch (InvalidNodeException e) {
      System.out.println(e + " at validMoveList");
    }
    return validMoves;
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
  // Person in charge: Max Eusterbrock
  public boolean winningNetwork(int color) {
    for (int i = 1; i<= 6; i++){
      if (boardGrid[0][i] == 1){//start traversal for white chips
        int[][] accum = {{0, i}};
        for (int y = - 1; y <= 1; y++){
          int[] whiteVector = {1, y};
          boolean result = networkHelp(accum[0], nearestPiece(0, i, 1, y), whiteVector, accum);
          if (result == false){
            continue;
          } else {
            return true;
          }
        }
      } if (boardGrid[i][0] == 0) {//start traversal for black chips
        int[][] accum = {{0, i}};
        for (int x = - 1; x <= 1; x++){
          int[] blackVector = {x, 1};
          boolean result = networkHelp(accum[0], nearestPiece(i, 0, x, 1), blackVector, accum);
          if (result == false){
            continue;
          } else {
            return true;
          }
        }
      }
    }
    return false;
  }

  private boolean networkHelp(int[] prev, int[] curr, int[] lastVector, int[][] accumNetwork){
    int prevX = prev[0];
    int prevY = prev[1];
    int currX = curr[0];
    int currY = curr[1];
    int prevColor = boardGrid[prevX][prevY];

    for(int[] each : accumNetwork){
      if (each.equals(curr)){//Piece has already been visited in current search, so network terminates
        return false;
      }
    }
    
    if (curr == null){//Piece is off the board so the network terminates
      return false;

    } else if (boardGrid[prevX][prevY] != boardGrid[currX][currY]){//This piece is a different color so network terminates
      return false;

    } else {
      int[][] currAccum = new int[accumNetwork.length + 1][2];//creates new array to extend the network by the newest piece
      for (int i = 0; i < accumNetwork.length; i++){//sets old network equal to new network with intention of adding the newest piece
        currAccum[i] = accumNetwork[i];
      }
      currAccum[currAccum.length - 1] = curr; //sets last element as the newest piece
      if (currX == 7 || currY == 7){//newest piece in goal area
        if (currAccum.length >= 6){//network of 6 or more pieces
          return true;
        } else {//length < 6 so traversal terminates
          return false;
        }

      } else {
        int lastX = lastVector[0];
        int lastY = lastVector[1];
        for (int x = -1; x <= 1; x++){
          for (int y = -1; y <= 1; y++){//traversing in all 8 directions
            if (x == lastX && y == lastY){//ensures a corner is turned
              continue;
            } else if (-x == lastX && -y == lastY){//ensures traversal doesn't return to previous piece
              continue;
            } else if (x == 0 && y == 0){//ensures traversal moves off current piece
              continue;
            } else {
              int[] next = nearestPiece(currX, currY, x, y);
              int[] currVector = {x, y};
              if (next == null){//traversal falls off board
                continue;
              } else {
                boolean result = networkHelp(curr, next, currVector, currAccum);//!!!!!!!!!
                if (result == false){
                  continue;
                } else {
                  return true;
                }
              }
            }
          }
        }
        return false;
      }
    }
  }

  // Checks to see where the nearest piece in relation to a piece is.
  //
  // Parameters:
  //    x: the x coordinate of the intended piece
  //    y: the y coordinate of the inteded piece
  //    i: the x vector direction to traverse
  //    j: the y vector direction to traverse
  //
  // Return Value:
  //    Returns an int array of the X,Y coordinates of the nearest piece
  //    Returns null if no piece, or invalid inputs.
  //
  // Other methods that rely on this method:
  //    winningNetwork(), networkHelp()
  private int[] nearestPiece(int x, int y, int i, int j){
    int k = x + i;
    int l = y + j;
    if(k > 7 || l > 7 || k < 0 || l < 0){//traversed off the board
      return null;
    } else if (boardGrid[k][l] != 2){//returns found piece coordinate if square not empty
      int[] coords = {k, l};
      return coords;
    } else {// if square empty, keeps traversing
      return nearestPiece(k, l, i, j);
    }
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
    return 0;
  }
  
  public int[][] getBoardGrid() {
    return boardGrid;
  }
  
  public void setBoardGrid(player.Move move, int color) {
    if (move.moveKind == player.Move.STEP) {
      boardGrid[move.x2][move.y2] = 2;
      setNumChips(color, "-");
    }
    boardGrid[move.x1][move.y1] = color;
    setNumChips(color, "+");
  }
  
  public void undoMove(player.Move move, int color) {
    if (boardGrid[move.x1][move.y1] == color) {
      if (move.moveKind == player.Move.STEP) {
        boardGrid[move.x2][move.y2] = color;
        setNumChips(color, "+");
      }
      boardGrid[move.x1][move.y1] = 2;
      setNumChips(color, "-");
    }
  }
  
  public int length() {
    return length;
  }
  
  public String toString() {
    String s = "";
    for (int i = 0; i <= (length()-1); i++) {
      for (int j = 0; j <= (length()-1); j++) {
        if (boardGrid[j][i] == 2) {
          s += "- ";
        } else {
          s += boardGrid[j][i] + " ";
        }
      }
      s += "\n";
    }
    return s;
  }

  public int[] directConnections() {
    return new int[1];
  }
    
  
  public static void main(String[] args) {
    // Testing isValidMove()
    System.out.println("#################################\n##### Testing isValidNode() #####\n#################################\n");
    // Testing Basic Board
    System.out.println("Testing board constructor and toString() method");
    Board testBoard1 = new Board();
    System.out.println(testBoard1.length());
    System.out.println(testBoard1);
    
    // Testing Basic Move
    System.out.println("Testing Basic Move for color 1 to (1, 1)");
    player.Move testMove1 = new player.Move(1, 1);
    if (testBoard1.isValidMove(testMove1, 1)) {
      testBoard1.setBoardGrid(testMove1, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidMove() on already occupied spot
    System.out.println("Testing isValidMove() for color 0 to (1, 1)");
    System.out.println("Should not change board");
    System.out.println("Due to Rule 3");
    player.Move testMove2 = new player.Move(1, 1);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidMove() for corners
    System.out.println("Testing isValidMove() for color 1 to (0, 0), (7, 0), (0, 7), (7, 7)");
    System.out.println("Should not change board");
    System.out.println("Due to Rule 1");
    player.Move testMove3 = new player.Move(0, 0);
    player.Move testMove4 = new player.Move(7, 0);
    player.Move testMove5 = new player.Move(0, 7);
    player.Move testMove6 = new player.Move(7, 7);
    if (testBoard1.isValidMove(testMove3, 1)) {
      testBoard1.setBoardGrid(testMove3, 1);
    }
    if (testBoard1.isValidMove(testMove4, 1)) {
      testBoard1.setBoardGrid(testMove4, 1);
    }
    if (testBoard1.isValidMove(testMove5, 1)) {
      testBoard1.setBoardGrid(testMove5, 1);
    }
    if (testBoard1.isValidMove(testMove6, 1)) {
      testBoard1.setBoardGrid(testMove6, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidMove() for corners
    System.out.println("Testing isValidMove() for color 0 to (0, 0), (7, 0), (0, 7), (7, 7)");
    System.out.println("Should not change board");
    System.out.println("Due to Rule 1");
    testMove3 = new player.Move(0, 0);
    testMove4 = new player.Move(7, 0);
    testMove5 = new player.Move(0, 7);
    testMove6 = new player.Move(7, 7);
    if (testBoard1.isValidMove(testMove3, 0)) {
      testBoard1.setBoardGrid(testMove3, 0);
    }
    if (testBoard1.isValidMove(testMove4, 0)) {
      testBoard1.setBoardGrid(testMove4, 0);
    }
    if (testBoard1.isValidMove(testMove5, 0)) {
      testBoard1.setBoardGrid(testMove5, 0);
    }
    if (testBoard1.isValidMove(testMove6, 0)) {
      testBoard1.setBoardGrid(testMove6, 0);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for neighbors of same color
    System.out.println("Testing isValidMove() for color 1 to (1, 2)");
    testMove2 = new player.Move(1, 2);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for neighbors of same color
    System.out.println("Testing isValidMove() for color 1 to (2, 2)");
    System.out.println("Should not change board");
    System.out.println("Due to Rule 4");
    testMove2 = new player.Move(2, 2);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for neighbors of same color
    System.out.println("Testing isValidMove() for color 1 to (1, 3)");
    System.out.println("Should not change board");
    System.out.println("Due to Rule 4");
    testMove2 = new player.Move(1, 3);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for neighbors of same color
    System.out.println("Testing isValidMove() for color 1 to (1, 0)");
    System.out.println("Should not change board");
    System.out.println("Due to Rule 4");
    testMove2 = new player.Move(1, 0);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for neighbors of different color
    // Testing placing another color in a goal area
    System.out.println("Testing isValidMove() for color 0 to (1, 0)");
    testMove2 = new player.Move(1, 0);
    if (testBoard1.isValidMove(testMove2, 0)) {
      testBoard1.setBoardGrid(testMove2, 0);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for two chips of same color in one goal
    System.out.println("Testing isValidMove() for color 0 to (2, 0)");
    testMove2 = new player.Move(2, 0);
    if (testBoard1.isValidMove(testMove2, 0)) {
      testBoard1.setBoardGrid(testMove2, 0);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for neighbors 
    System.out.println("Testing isValidMove() for color 0 to (3, 1)");
    System.out.println("Should not change board");
    System.out.println("Due to Rule 4");
    testMove2 = new player.Move(3, 1);
    if (testBoard1.isValidMove(testMove2, 0)) {
      testBoard1.setBoardGrid(testMove2, 0);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for two chips of same color in two different goals
    System.out.println("Testing isValidMove() for color 0 to (0, 2)");
    System.out.println("Should not change board");
    System.out.println("Due to Rule 2");
    testMove2 = new player.Move(0, 2);
    if (testBoard1.isValidMove(testMove2, 0)) {
      testBoard1.setBoardGrid(testMove2, 0);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for two chips of same color in two different goals
    System.out.println("Testing isValidMove() for color 0 to (5, 7)");
    testMove2 = new player.Move(5, 7);
    if (testBoard1.isValidMove(testMove2, 0)) {
      testBoard1.setBoardGrid(testMove2, 0);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for black chip in wrong goal
    System.out.println("Testing isValidMove() for color 0 to (7, 4)");
    System.out.println("Should not change board");
    System.out.println("Because not black's goal");
    testMove2 = new player.Move(7, 4);
    if (testBoard1.isValidMove(testMove2, 0)) {
      testBoard1.setBoardGrid(testMove2, 0);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for placing one chip of one color in opponent's goal
    System.out.println("Testing isValidMove() for color 1 to (0, 4), (7, 2)");
    testMove2 = new player.Move(0, 4);
    testMove3 = new player.Move(7, 2);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    if (testBoard1.isValidMove(testMove3, 1)) {
      testBoard1.setBoardGrid(testMove3, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for neighbors if one neighbor in a goal
    System.out.println("Testing isValidMove() for color 1 to (1, 3)");
    System.out.println("Should not change board");
    System.out.println("Due to Rule 4");
    testMove2 = new player.Move(1, 3);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for placing one chip's color in the wrong goal
    System.out.println("Testing isValidMove() for color 1 to (3, 0), (2, 7)");
    System.out.println("Should not change board");
    System.out.println("Because white is in wrong goal");
    testMove2 = new player.Move(3, 0);
    testMove3 = new player.Move(2, 7);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    if (testBoard1.isValidMove(testMove3, 1)) {
      testBoard1.setBoardGrid(testMove3, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for number of Chips placed and type of move
    System.out.println("Testing isValidMove() for color 1 to (3, 6), (5, 6)");
    testMove2 = new player.Move(3, 6);
    testMove3 = new player.Move(5, 6);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    if (testBoard1.isValidMove(testMove3, 1)) {
      testBoard1.setBoardGrid(testMove3, 1);
    }
    System.out.println(testBoard1);
    System.out.println("Testing setNumChips() and getNumChips()");
    System.out.println("Number of White Chips = " + testBoard1.getNumChips(1));
    System.out.println("Number of Black Chips = " + testBoard1.getNumChips(0) + "\n");
    
    // Testing isValidNode() for number of Chips placed and type of move
    System.out.println("Testing isValidMove() for color 1 to (3, 2), (3, 3)");
    testMove2 = new player.Move(3, 2);
    testMove3 = new player.Move(3, 3);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    if (testBoard1.isValidMove(testMove3, 1)) {
      testBoard1.setBoardGrid(testMove3, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() step move for only 8 color 1 chips on board
    System.out.println("Testing isValidMove() for a step Move for color 1 to (4, 2) from (3, 2)");
    System.out.println("Should not change board");
    System.out.println("Due to there being only " + testBoard1.getNumChips(1) + " Chips of color 1 on board");
    testMove2 = new player.Move(4, 2, 3, 2);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for number of Chips placed and type of move
    System.out.println("Testing isValidMove() for color 1 to (6, 1), (6, 4)");
    testMove2 = new player.Move(6, 1);
    testMove3 = new player.Move(6, 4);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    if (testBoard1.isValidMove(testMove3, 1)) {
      testBoard1.setBoardGrid(testMove3, 1);
    }
    System.out.println(testBoard1);
    System.out.println("Testing setNumChips() and getNumChips()");
    System.out.println("Number of White Chips = " + testBoard1.getNumChips(1));
    System.out.println("Number of Black Chips = " + testBoard1.getNumChips(0) + "\n");
    
    // Testing isValidNode() add move for 10 color 1 chips on board
    System.out.println("Testing isValidMove() for an add Move for color 1 to (1, 5)");
    System.out.println("Should not change board");
    System.out.println("Due to there being " + testBoard1.getNumChips(1) + " Chips of color 1 on board");
    testMove2 = new player.Move(1, 5);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for step move
    System.out.println("Testing isValidMove() for a step Move for color 1 to (3, 4) from (3, 2)");
    testMove2 = new player.Move(3, 4, 3, 2);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for step move
    System.out.println("Testing isValidMove() for a step Move for color 1 to (5, 5) from (6, 4)");
    testMove2 = new player.Move(5, 5, 6, 4);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for step move to neighbors > 1
    System.out.println("Testing isValidMove() for a step Move for color 1 to (1, 3) from (3, 3)");
    System.out.println("Should not change board");
    System.out.println("Due to Rule 4");
    testMove2 = new player.Move(1, 3, 3, 3);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for step move to corner
    System.out.println("Testing isValidMove() for a step Move for color 1 to (7, 0) from (6, 1)");
    System.out.println("Should not change board");
    System.out.println("Due to Rule 1");
    testMove2 = new player.Move(7, 0, 6, 1);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for step move to opposite goals
    System.out.println("Testing isValidMove() for a step Move for color 1 to (6, 0) from (6, 1)");
    System.out.println("Should not change board");
    System.out.println("Due to Rule 2");
    testMove2 = new player.Move(6, 0, 6, 1);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for step move trying to move from opponent's color
    System.out.println("Testing isValidMove() for a step Move for color 1 to (2, 5) from (2, 0)");
    System.out.println("Should not change board");
    System.out.println("Because (2, 0) is occupied by color 0 chip");
    testMove2 = new player.Move(2, 5, 2, 0);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for step move trying to move from null spot
    System.out.println("Testing isValidMove() for a step Move for color 1 to (1, 4) from (5, 3)");
    System.out.println("Should not change board");
    System.out.println("Because (5, 3) is occupied by null");
    testMove2 = new player.Move(1, 4, 5, 3);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for add move for color 0 to color 1 neighborhood
    System.out.println("Testing isValidMove() for an add Move for color 0 to (2, 2)");
    testMove2 = new player.Move(2, 2);
    if (testBoard1.isValidMove(testMove2, 0)) {
      testBoard1.setBoardGrid(testMove2, 0);
    }
    System.out.println(testBoard1);
    
    // Testing isValidNode() for step move trying to move from null spot
    System.out.println("Testing isValidMove() for a step Move for color 1 to (5, 5) from (5, 5)");
    System.out.println("Should not change board");
    System.out.println("Because it is the same spot for a step Move");
    testMove2 = new player.Move(5, 5, 5, 5);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    System.out.println(testBoard1);
    
    // Testing undoMove() for add move
    System.out.println("Testing undoMove() for a move");
    testMove2 = new player.Move(3, 2);
    if (testBoard1.isValidMove(testMove2, 0)) {
      testBoard1.setBoardGrid(testMove2, 0);
    }
    System.out.println(testBoard1);
    System.out.println("Board below should be different than board above");
    testBoard1.undoMove(testMove2, 0);
    System.out.println(testBoard1);
    
    // Testing undoMove() for step move
    System.out.println("Testing undoMove() for a move");
    testMove2 = new player.Move(4, 3, 3, 3);
    if (testBoard1.isValidMove(testMove2, 1)) {
      testBoard1.setBoardGrid(testMove2, 1);
    }
    System.out.println(testBoard1);
    System.out.println("Board below should be different than board above");
    testBoard1.undoMove(testMove2, 1);
    System.out.println(testBoard1);

    // Testing validMoveList()
    System.out.println("Testing validMoveList() on empty board for player 1");
    Board nikBoard1 = new Board();
	DList nikList1 = nikBoard1.validMoveList(1);
	System.out.println(nikList1);

	System.out.println("Testing validMoveList() on a board with 10 player 1 pieces");
	Move nikMove1 = new Move(1, 1);
	Move nikMove2 = new Move(2, 2);
	Move nikMove3 = new Move(3, 3);
	Move nikMove4 = new Move(4, 4);
	Move nikMove5 = new Move(5, 5);
	Move nikMove6 = new Move(6, 6);
	Move nikMove7 = new Move(1, 2);
	Move nikMove8 = new Move(1, 3);
	Move nikMove9 = new Move(1, 4);
	Move nikMove10 = new Move(1, 5);
	nikBoard1.setBoardGrid(nikMove1, 1);
	nikBoard1.setBoardGrid(nikMove2, 1);
	nikBoard1.setBoardGrid(nikMove3, 1);
	nikBoard1.setBoardGrid(nikMove4, 1);
	nikBoard1.setBoardGrid(nikMove5, 1);
	nikBoard1.setBoardGrid(nikMove6, 1);
	nikBoard1.setBoardGrid(nikMove7, 1);
	nikBoard1.setBoardGrid(nikMove8, 1);
	nikBoard1.setBoardGrid(nikMove9, 1);
	nikBoard1.setBoardGrid(nikMove10, 1);
	DList nikList2 = nikBoard1.validMoveList(1);
	System.out.println("Here is the board:");
	System.out.println(nikBoard1);
	System.out.println("Here is the validMoveList for player 1:");
	System.out.println(nikList2);
	System.out.println("Here are the number of available moves for player 1:");
	System.out.println(nikList2.length());
  }
}


