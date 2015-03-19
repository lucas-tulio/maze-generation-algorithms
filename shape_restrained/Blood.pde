class Blood {
  ArrayList<Drop> drops;
  
  public Blood(int x, int y) {
    drops = new ArrayList<Drop>();
    drops.add(new Drop(x, y));
  }
  
  public void update(Maze maze) {
    
    // Block the blood areas
//    for (Drop d : drops) {
//      maze.blocked[d.x][d.y] = true;
//    }
    
    // Drop movement
    ArrayList<Drop> newDrops = new ArrayList<Drop>();
    
    for (Drop d : drops) {
      if (d.life == 2) {
        
        maze.blocked[d.x][d.y] = true;
        
        if (!maze.blocked[d.x + 1][d.y]) {
          newDrops.add(new Drop(d.x + 1, d.y));
        }
        if (!maze.blocked[d.x - 1][d.y]) {
          newDrops.add(new Drop(d.x - 1, d.y));
        }
        if (!maze.blocked[d.x][d.y + 1]) {
          newDrops.add(new Drop(d.x, d.y + 1));
        }
        if (d.y > 0) { // gambi alert
          if (!maze.blocked[d.x][d.y - 1]) {
            newDrops.add(new Drop(d.x, d.y - 1));
          }
        }
      } else if (d.life == 0) {
        maze.blocked[d.x][d.y] = false;
      }
      d.life--;
    }
    
    // Create the new drops
    for (Drop d : newDrops) {
      drops.add(d);
    }
  }
}
