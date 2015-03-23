Maze maze;
Blood blood;
int tileSize = 15;
int mazeSize = 60;
Drop newDrop;
boolean showDebug = false;

void setup() {
  size((mazeSize - 1) * tileSize, int(((mazeSize - 1) * tileSize) / 1.3));
  frameRate(60);
  noStroke();
  
  startMaze();
}

void draw() {
  
  if (showDebug) {
    drawMaze();
  }
  
  blood.update(maze);
  
  boolean allDead = true;
  for (Drop d : blood.drops) {
    
    if (d.life > 0) {
      allDead = false;
    }
    
    d.update();
    
    fill(255, 255 - d.opacity, 255 - d.opacity);
    rect(d.y * tileSize, d.x * tileSize, tileSize, tileSize);
  }
  
  // Reset the maze and the blood
  if (allDead) {
    
    for (Drop d : blood.drops) {
      maze.blocked[d.x][d.y] = false;
    }
    
    Drop lastDrop = blood.drops.get(blood.drops.size() - 1);
    newDrop = new Drop(lastDrop.x, lastDrop.y);
    blood.drops.add(newDrop);
  }

  // Debug
  if (showDebug) {
    fill(255);
    text("drops: " + blood.drops.size(), 20, 20);
  }
}

void drawMaze() {
  background(255);
  fill(0);
  for (int i = 0; i < mazeSize - 1; i++) {
    for (int j = 0; j < mazeSize - 1; j++) {
      if (maze.blocked[i][j]) {
        rect(j * tileSize, i * tileSize, tileSize, tileSize);
      }
    }
  }
}

void startMaze() {
  maze = new Maze(mazeSize);
  maze.createMaze();
  Point freePoint = maze.getFreePoint();
  blood = new Blood(freePoint.x, freePoint.y);
  drawMaze();
}

void keyPressed() {
  if (keyCode == 68) {
    showDebug = !showDebug;
  } else if (keyCode == 82) {
    startMaze();
  }
}
