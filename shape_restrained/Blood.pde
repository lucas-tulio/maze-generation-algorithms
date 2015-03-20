class Blood {
  ArrayList<Drop> drops;
  
  public Blood(int x, int y) {
    drops = new ArrayList<Drop>();
    drops.add(new Drop(x, y));
  }
  
  public void update(Maze maze) {
    
    // Drop life control
    ArrayList<Drop> newDrops = new ArrayList<Drop>();
    ArrayList<Drop> deadDrops = new ArrayList<Drop>();
    
    // Drop movement
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
      
      if (d.canDie) {
        deadDrops.add(d);
      }
    }
    
    // Create the new drops
    for (Drop d : newDrops) {
      drops.add(d);
    }
    
    // Kill off the old ones
    for (Drop d : deadDrops) {
      drops.remove(d);
    }
  }
}
