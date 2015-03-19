Maze maze;
Blood blood;
int tileSize = 4;
int mazeSize = 200;

void setup() {
  size((mazeSize - 1) * tileSize + 1, (mazeSize - 1) * tileSize + 1);
  maze = new Maze(mazeSize);
  maze.createMaze();
  
  Point freePoint = maze.getFreePoint();
  blood = new Blood(freePoint.x, freePoint.y);
}

void draw() {
  
  background(255);
  
  // Draw the maze
  stroke(0);
  fill(0);
  for (int i = 0; i < mazeSize - 1; i++) {
    for (int j = 0; j < mazeSize - 1; j++) {
      if (maze.blocked[i][j]) {
        rect(j * tileSize, i * tileSize, tileSize, tileSize);
      }
    }
  }
  
  // Draw the blood
  blood.update(maze);
  
  for (Drop d : blood.drops) {
    
    d.update();
    
    if (d.lifetime >= 0) {
      stroke(d.lifetime, d.lifetime-255, d.lifetime-255);
      fill(d.lifetime, d.lifetime-255, d.lifetime-255);
      rect(d.x * tileSize, d.y * tileSize, tileSize, tileSize);
    }    
  }
}

void keyPressed() {
  maze.createMaze();
}
