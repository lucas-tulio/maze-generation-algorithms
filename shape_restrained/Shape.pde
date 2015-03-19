class Shape {
  boolean free[][];
  int size;
  
  public Shape(int size) {
    this.size = size;
    free = new boolean[size][size];
    resetShape();
  }
  
  public void createHeartShape() {
    
    int midHeight = size / 3;
    int midWidth = size / 2;
    
  }
  
  public void createBasicShape() {
    for (int i = 0; i < size / 2; i++) {
      for (int j = 0; j < size / 2; j++) {
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

