Maze maze;
Blood blood;
int tileSize = 4;
int mazeSize = 50;

void setup() {
  size((mazeSize - 1) * tileSize + 1, (mazeSize - 1) * tileSize + 1);
  frameRate(60);
  noStroke();
  
  maze = new Maze(mazeSize);
  maze.createMaze();
  Point freePoint = maze.getFreePoint();
  blood = new Blood(freePoint.x, freePoint.y);
}

void draw() {
  
  background(255);
  fill(0, 0, 0);
  for (int i = 0; i < mazeSize - 1; i++) {
    for (int j = 0; j < mazeSize - 1; j++) {
      if (maze.blocked[i][j]) {
        rect(j * tileSize, i * tileSize, tileSize, tileSize);
      }
    }
  }
  
  blood.update(maze);
  for (Drop d : blood.drops) {
    d.update();
    
    // debug
//    if (d.life == 2) {
//      fill(0, 0, 255);
//    } else if (d.life == 1) {
//      fill(0, 255, 0);
//    } else {
//      fill(255, 0, 0);
//    }
    fill(255, 255 - d.opacity, 255 - d.opacity);
    rect(d.y * tileSize, d.x * tileSize, tileSize, tileSize);
  }
}


