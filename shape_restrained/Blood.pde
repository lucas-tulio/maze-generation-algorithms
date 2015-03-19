class Blood {
  ArrayList<Drop> drops;
  
  public Blood(int x, int y) {
    drops = new ArrayList<Drop>();
    drops.add(new Drop(x, y));
  }
  
  public void update(Maze maze) {
    ArrayList<Drop> newDrops = new ArrayList<Drop>();
    
    for (Drop d : drops) {
      
      if (d.alive) {
        if (!maze.blocked[d.x + 1][d.y]) { // right
          newDrops.add(new Drop(d.x + 1, d.y));
        }
        
        if (!maze.blocked[d.x - 1][d.y]) { // left
          newDrops.add(new Drop(d.x - 1, d.y));
        }
        
        if (!maze.blocked[d.x][d.y + 1]) { // up
          newDrops.add(new Drop(d.x, d.y + 1));
        }
        
        if (!maze.blocked[d.x][d.y - 1]) { // down
          newDrops.add(new Drop(d.x, d.y -1));
        }
      }
      
      d.alive = false;
    }
    
    for (Drop d : newDrops) {
      drops.add(d);
    }
  }
}
