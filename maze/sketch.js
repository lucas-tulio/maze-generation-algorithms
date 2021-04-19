// Doesn't work on even numbers since we're always moving by two tiles at a time
const SIZE = 31
const TILE_SIZE = 16
const NODE_SIZE = TILE_SIZE
const walls = []
const nodes = []
const U = 0
const D = 1
const L = 2
const R = 3
const STARTING_POS = { x: 1, y: 1 }
const entrance = 1
const exit = SIZE ** 2 - 2
const STEP = {
  BUILDING: 0,
  MAPPING: 1,
  SOLVING: 2,
}

const builder = {
  pos: STARTING_POS, // 0, 0 is the outer wall
  moves: [pointToIndex(STARTING_POS)], // Possible movement list
}

const solver = {
  agents: [{
    pos: { x: 1, y: 0 },
    direction: D,
    willDie: false
  }],
}

let currentStep = STEP.BUILDING
const time = { start: new Date(), end: null }

function setup() {
  createCanvas(SIZE * TILE_SIZE, SIZE * TILE_SIZE)
  background(0)
  noStroke()
  noSmooth()
  initialize()
}

function initialize() {
  for (let i = 0; i < SIZE ** 2; i++) {
    walls.push(true)
  }

  // Entrance and exit
  walls[entrance] = false
  walls[exit] = false
  rect(indexToX(entrance) * TILE_SIZE, indexToY(entrance) * TILE_SIZE, TILE_SIZE, TILE_SIZE)
  rect(indexToX(exit) * TILE_SIZE, indexToY(exit) * TILE_SIZE, TILE_SIZE, TILE_SIZE)

  // Start position
  walls[pointToIndex(STARTING_POS)] = false
  fill(255)
  rect(STARTING_POS.x * TILE_SIZE, STARTING_POS.y * TILE_SIZE, TILE_SIZE, TILE_SIZE)
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

function draw() {
  // TODO: rendering improvement: save points to a texture, render the texture
  switch(currentStep) {
    case STEP.BUILDING:
      buildMaze()
      break
    case STEP.MAPPING:
      createMap()
      break
    case STEP.SOLVING:
      break
  }
}

function createMap() {
  nodes[entrance] = true
  nodes[exit] = true
  let running = true
  fill(0, 0, 255)
  ellipseMode(CENTER)
  while (running) {
    let newAgents = []
    for (let i = 0; i < solver.agents.length; i++) {
      const agent = solver.agents[i]
      // 1. Check sides
      const left = agent.pos.x - 1
      const right = agent.pos.x + 1
      const up = agent.pos.y - 1
      const down = agent.pos.y + 1
      switch (agent.direction) {
        case U:
        case D:
          if (!walls[pointToIndex({ x: left, y: agent.pos.y })]) {
            nodes[pointToIndex({ x: agent.pos.x, y: agent.pos.y })] = true
            rect(agent.pos.x * NODE_SIZE, agent.pos.y * NODE_SIZE, NODE_SIZE, NODE_SIZE)
            newAgents.push({ pos: { x: left, y: agent.pos.y }, direction: L, willDie: false })
          }
          if (!walls[pointToIndex({ x: right, y: agent.pos.y })]) {
            nodes[pointToIndex({ x: agent.pos.x, y: agent.pos.y })] = true
            rect(agent.pos.x * NODE_SIZE, agent.pos.y * NODE_SIZE, NODE_SIZE, NODE_SIZE)
            newAgents.push({ pos: { x: right, y: agent.pos.y }, direction: R, willDie: false })
          }
          break;
        case L:
        case R:
          if (!walls[pointToIndex({ x: agent.pos.x, y: up })]) {
            nodes[pointToIndex({ x: agent.pos.x, y: agent.pos.y })] = true
            rect(agent.pos.x * NODE_SIZE, agent.pos.y * NODE_SIZE, NODE_SIZE, NODE_SIZE)
            newAgents.push({ pos: { x: agent.pos.x, y: up }, direction: U, willDie: false })
          }
          if (!walls[pointToIndex({ x: agent.pos.x, y: down })]) {
            nodes[pointToIndex({ x: agent.pos.x, y: agent.pos.y })] = true
            rect(agent.pos.x * NODE_SIZE, agent.pos.y * NODE_SIZE, NODE_SIZE, NODE_SIZE)
            newAgents.push({ pos: { x: agent.pos.x, y: down }, direction: D, willDie: false })
          }
          break;
      }

      // 2. Move forward or die
      if (agent.direction === U) {
        if (walls[pointToIndex({ x: agent.pos.x, y: up })]) {
          agent.willDie = true
        } else {
          agent.pos.y = up
        }
      } else if (agent.direction === D) {
        if (walls[pointToIndex({ x: agent.pos.x, y: down })] || agent.pos.y === SIZE - 1) {
          agent.willDie = true
        } else {
          agent.pos.y = down
        }
      } else if (agent.direction === L) {
        if (walls[pointToIndex({ x: left, y: agent.pos.y })]) {
          agent.willDie = true
        } else {
          agent.pos.x = left
        }
      } else if (agent.direction === R) {
        if (walls[pointToIndex({ x: right, y: agent.pos.y })]) {
          agent.willDie = true
        } else {
          agent.pos.x = right
        }
      }
    }

    // 3. Kill agents at the end of their path
    solver.agents = solver.agents.filter((agent) => !agent.willDie)

    // 4. Create new agents
    solver.agents.push(...newAgents)

    // 5. Stop condition
    for (let i = 0; i < solver.agents.length; i++) {
      if (pointToIndex({ x: solver.agents[i].pos.x, y: solver.agents[i].pos.y }) === exit) {
        running = false
        currentStep = STEP.SOLVING
      }
    }
  }

  console.log(nodes)
}

function buildMaze() {
  for (let i = 0; i < SIZE ** 2; i++) {
    if (builder.moves.length > 0) {
      const possibleDirections = []
      if (builder.pos.y + 2 < SIZE && builder.pos.y + 2 !== SIZE - 1 && walls[pointToIndex({ x: builder.pos.x, y: builder.pos.y + 2 })]) {
        possibleDirections.push(D)
      }
  
      if (builder.pos.y - 2 >= 0 && walls[pointToIndex({ x: builder.pos.x, y: builder.pos.y - 2 })]) {
          possibleDirections.push(U)
      }
  
      if (builder.pos.x - 2 >= 0 && walls[pointToIndex({ x: builder.pos.x - 2, y: builder.pos.y })]) {
          possibleDirections.push(L)
      }
  
      if (builder.pos.x + 2 < SIZE && builder.pos.x + 2 !== SIZE - 1 && walls[pointToIndex({ x: builder.pos.x + 2, y: builder.pos.y })]) {
          possibleDirections.push(R)
      }
  
      // Found any possible movements?
      if (possibleDirections.length > 0) {
        const direction = possibleDirections.sample()
        switch (direction) {
          case U:
            walls[pointToIndex({ x: builder.pos.x, y: builder.pos.y - 2 })] = false
            walls[pointToIndex({ x: builder.pos.x, y: builder.pos.y - 1 })] = false
            rect(builder.pos.x * TILE_SIZE, (builder.pos.y - 2) * TILE_SIZE, TILE_SIZE, TILE_SIZE)
            rect(builder.pos.x * TILE_SIZE, (builder.pos.y - 1) * TILE_SIZE, TILE_SIZE, TILE_SIZE)
            builder.pos.y -= 2
            break
          case D:
            walls[pointToIndex({ x: builder.pos.x, y: builder.pos.y + 2 })] = false  
            walls[pointToIndex({ x: builder.pos.x, y: builder.pos.y + 1 })] = false
            rect(builder.pos.x * TILE_SIZE, (builder.pos.y + 2) * TILE_SIZE, TILE_SIZE, TILE_SIZE)
            rect(builder.pos.x * TILE_SIZE, (builder.pos.y + 1) * TILE_SIZE, TILE_SIZE, TILE_SIZE)
            builder.pos.y += 2
            break
          case L:
            walls[pointToIndex({ x: builder.pos.x - 2, y: builder.pos.y })] = false  
            walls[pointToIndex({ x: builder.pos.x - 1, y: builder.pos.y })] = false
            rect((builder.pos.x - 2) * TILE_SIZE, builder.pos.y * TILE_SIZE, TILE_SIZE, TILE_SIZE)
            rect((builder.pos.x - 1) * TILE_SIZE, builder.pos.y * TILE_SIZE, TILE_SIZE, TILE_SIZE)
            builder.pos.x -= 2
            break
          case R:
            walls[pointToIndex({ x: builder.pos.x + 2, y: builder.pos.y })] = false
            walls[pointToIndex({ x: builder.pos.x + 1, y: builder.pos.y })] = false
            rect((builder.pos.x + 2) * TILE_SIZE, builder.pos.y * TILE_SIZE, TILE_SIZE, TILE_SIZE)
            rect((builder.pos.x + 1) * TILE_SIZE, builder.pos.y * TILE_SIZE, TILE_SIZE, TILE_SIZE)
            builder.pos.x += 2
            break
        }
        // Add a new possible movement
        builder.moves.push(pointToIndex({ x: builder.pos.x, y: builder.pos.y }))
      } else {
        // There are no more possible movements
        const previous = builder.moves.pop()
        builder.pos.x = indexToX(previous)
        builder.pos.y = indexToY(previous)
      }
    }
  }
  currentStep = STEP.MAPPING
}
