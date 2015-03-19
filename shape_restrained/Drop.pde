class Drop extends Point {
  int lifetime;
  boolean alive;
  
  public Drop(int x, int y) {
    super(x, y);
    lifetime = 255;
    alive = true;
  }
  
  public void update() {
    if (lifetime >= 0) {
      lifetime--;
    }
  }
}
