class Blood {
  ArrayList<Drop> drops;
  
  public Blood(int x, int y) {
    drops = new ArrayList<Drop>();
    drops.add(new Drop(x, y));
  }
  
  public void update(Maze maze) {
    Drop[] newDrops = new Drop[4];
    
    for (Drop d : drops) {
      if (d.alive) {
        if (!maze.blocked[d.x + 1][d.y]) { // right
          newDrops[0] = new Drop(d.x + 1, d.y);
          maze.blocked[d.x + 1][d.y] = true;
        }
        if (!maze.blocked[d.x - 1][d.y]) { // left
          newDrops[1] = new Drop(d.x - 1, d.y);
          maze.blocked[d.x - 1][d.y] = true;
        }
        if (!maze.blocked[d.x][d.y + 1]) { // up
          newDrops[2] = new Drop(d.x, d.y + 1);
          maze.blocked[d.x][d.y + 1] = true;
        }
        if (!maze.blocked[d.x][d.y - 1]) { // down
          newDrops[3] = new Drop(d.x, d.y - 1);
          maze.blocked[d.x][d.y - 1] = true;
        }
      }
      d.alive = false;
    }
    
    for (int i = 0; i < newDrops.length; i++) {
      if (newDrops[i] != null) {
        drops.add(newDrops[i]);
      }
    }
    
    int aliveCount = 0;
    for (Drop d : drops) {
      if (d.alive) {
        aliveCount++;
      }
    }
  }
}
