class Drop extends Point {
  int opacity;
  int life;
  boolean canDie;
  
  public Drop(int x, int y) {
    super(x, y);
    opacity = 255;
    life = 2;
  }

  public void update() {
    if (opacity > 0) {
      opacity = opacity - 1;
    } else if (opacity <= 0) {
      canDie = true;
    }
  }
}
