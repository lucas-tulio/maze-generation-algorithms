// Doesn't work on even numbers since we're always moving by two tiles at a time
const SIZE = 63
const TILE_SIZE = 8
const walls = []
const U = 0
const D = 1
const L = 2
const R = 3
const STARTING_POS = { x: 1, y: 1 }

function setup() {
  createCanvas(SIZE * TILE_SIZE, SIZE * TILE_SIZE)
  background(255)
  noStroke()
  noSmooth()
  createMaze()
  drawMaze()
}

function createMaze() {
  for (let i = 0; i < SIZE ** 2; i++) {
    walls.push(true)
  }

  // Entrance and exit
  walls[1] = false
  walls[SIZE ** 2 - 2] = false
  
  // Start position
  walls[pointToIndex(STARTING_POS)] = false
}

function pointToIndex(point) {
  return point.x + point.y * SIZE
}

function indexToPoint(index) {
  return { x: indexToX(index), y: indexToY(index) }
}

function indexToX(index) {
  return index % SIZE
}

function indexToY(index) {
  return Math.trunc(index / SIZE)
}

function drawMaze() {
  for (let i = 0; i < SIZE ** 2; i++) {
    if (walls[i]) {
      fill(0)
      rect(indexToX(i) * TILE_SIZE, indexToY(i) * TILE_SIZE, TILE_SIZE, TILE_SIZE)
    } else {
      fill(255)
      rect(indexToX(i) * TILE_SIZE, indexToY(i) * TILE_SIZE, TILE_SIZE, TILE_SIZE)
    }
  }

  fill(255, 0, 0)
  rect(pos.x * TILE_SIZE, pos.y * TILE_SIZE, TILE_SIZE, TILE_SIZE)
}

let previous
let pos = STARTING_POS // 0, 0 is the outer wall

// Possible movement list
let moves = []
moves.push(pointToIndex(pos))

function draw() {
  if (moves.length > 0) {

    const possibleDirections = []
    if (pos.y + 2 < SIZE && pos.y + 2 !== SIZE - 1 && walls[pointToIndex({ x: pos.x, y: pos.y + 2 })]) {
      possibleDirections.push(D)
    }

    if (pos.y - 2 >= 0 && walls[pointToIndex({ x: pos.x, y: pos.y - 2 })]) {
        possibleDirections.push(U)
    }

    if (pos.x - 2 >= 0 && walls[pointToIndex({ x: pos.x - 2, y: pos.y })]) {
        possibleDirections.push(L)
    }

    if (pos.x + 2 < SIZE && pos.x + 2 !== SIZE - 1 && walls[pointToIndex({ x: pos.x + 2, y: pos.y })]) {
        possibleDirections.push(R)
    }

    // Found any possible movements?
    if (possibleDirections.length > 0) {
      const direction = possibleDirections.sample()
      switch (direction) {
        case U:
          walls[pointToIndex({ x: pos.x, y: pos.y - 2 })] = false
          walls[pointToIndex({ x: pos.x, y: pos.y - 1 })] = false
          pos.y -= 2
          break
        case D:
          walls[pointToIndex({ x: pos.x, y: pos.y + 2 })] = false  
          walls[pointToIndex({ x: pos.x, y: pos.y + 1 })] = false
          pos.y += 2
          break
        case L:
          walls[pointToIndex({ x: pos.x - 2, y: pos.y })] = false  
          walls[pointToIndex({ x: pos.x - 1, y: pos.y })] = false
          pos.x -= 2
          break
        case R:
          walls[pointToIndex({ x: pos.x + 2, y: pos.y })] = false
          walls[pointToIndex({ x: pos.x + 1, y: pos.y })] = false
          pos.x += 2
          break
      }
      // Add a new possible movement
      moves.push(pointToIndex({ x: pos.x, y: pos.y }))
    } else {
      // There are no more possible movements
      previous = moves.pop()
      pos.x = indexToX(previous)
      pos.y = indexToY(previous)
    }
  }

  drawMaze()
}
