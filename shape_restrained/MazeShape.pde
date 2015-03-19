class MazeShape {
  boolean free[][];
  int size;
  
  public MazeShape(int size) {
    this.size = size;
    free = new boolean[size][size];
    resetShape();
    createHeartShape();
  }
  
  public void createHeartShape() {
    
    int midHeight = int(size / 3);
    int midWidth = int(size / 2);
    
    // Upper left
    boolean upperLeft[][] = new boolean[size][size];
    for (float i = 0; i < Math.PI; i = i + 0.005) {
      int x = int(size * 0.25 + -sin(i) * size * 0.25);
      int y = int(size * 0.25 +  cos(i) * size * 0.25); 
      upperLeft[x][y] = true;
    }
    
    // Upper right
    boolean upperRight[][] = new boolean[size][size];
    for (float i = 0; i < Math.PI; i = i + 0.005) {
      int x = int(size * 0.25 + -sin(i) * size * 0.25);
      int y = int(size * 0.75 +  cos(i) * size * 0.25);
      upperRight[x][y-1] = true;  // -1 because of rounding error
    }
    
    // Lower left
    boolean lowerLeft[][] = new boolean[size][size];
    int y = int(size / 4);
    for (int x = 0; x < int(size / 2); x++) {
      lowerLeft[y][x] = true;
      y++;
    }
    
    // Lower right
    boolean lowerRight[][] = new boolean[size][size];
    for (int x = int(size/2); x < size; x++) {
      lowerRight[y][x] = true;
      y--;
    }
    
    // Apply the shape: upperLeft + upperRight
    for (int i = 0; i < size; i++) {
      boolean canBlock = true;
      for (int j = 0; j < int(size/4); j++) {
        if (upperLeft[j][i] || upperRight[j][i]) {
          canBlock = false;
        }
        if (canBlock) {
          free[j][i] = false;
        }
      }
    }
    
    // Apply the shape: lowerLeft + lowerRight
    for (int i = 0; i < size; i++) {
      boolean canBlock = false;
      for (int j = int(size/4); j < size; j++) {
        if (lowerLeft[j][i] || lowerRight[j][i]) {
          canBlock = true;
        }
        if (canBlock) {
          free[j][i] = false;
        }
      }
    }
  }
  
  public void createBasicShape() {
    for (int i = 0; i < int(size / 2); i++) {
      for (int j = 0; j < int(size / 2); j++) {
        free[i][j] = false;
      }
    }
  }
  
  private void resetShape() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        free[i][j] = true;
      }
    }
  }
}

