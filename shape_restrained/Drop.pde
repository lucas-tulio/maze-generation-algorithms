class Drop extends Point {
  int lifetime;
  
  public Drop(int x, int y) {
    super(x, y);
    lifetime = 255;
  }
  
  public void update() {
    
    // Lifetime
    if (lifetime >= 0) {
      lifetime--;
    }
    
    // Movement
    
  }
}
