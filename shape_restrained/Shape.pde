class Shape {
  boolean blocked[][];
  int size;
  
  public Shape(int size) {
    this.size = size;
    blocked = new boolean[size][size];
    createBasicShape();
  }
  
  /**
  * Restrain the maze into a shape
  */
  public void createBasicShape() {
    for (int i = 0; i < size / 2; i++) {
      for (int j = 0; j < size / 2; j++) {
        blocked[i][j] = true;
      }
    }
  }
}

