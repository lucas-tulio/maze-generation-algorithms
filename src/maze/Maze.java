package maze;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class Maze implements TileBasedMap {

	// Size
	private int width;
	private int height;
	
	// Starting and ending positions
	private int startingX;
	private int startingY;
	private int endingX;
	private int endingY;
	
	// Offset
	private float xOffset;
	private float yOffset;
	
	// Blocked Tiles
	private boolean blocked[][];
	private ArrayList<Tile> tiles;
	private Image tileImage;
	
	// A*
	private AStarPathFinder pathFinder;
	
	// Possible Directions (used during maze generation)
	private final int UP = 0;
	private final int DOWN = 1;
	private final int LEFT = 2;
	private final int RIGHT = 3;
	
	public Maze(int width, int height) throws SlickException {
		
		this.width = width;
		this.height = height;
		startingX = 1;
		startingY = 0;
		endingX = width - 3;
		endingY = height - 2;
		
		// Start the Rectangles Array List
		tiles = new ArrayList<Tile>();
		tileImage = new Image("/res/1x1.png");
		tileImage.setFilter(Image.FILTER_NEAREST);
		
		// Setup the Map
		blocked = new boolean[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				blocked[i][j] = true;
			}
		}
		
		// Starting and ending positions are not blocked!
		blocked[startingX][startingY] = false;
		blocked[endingX][endingY] = false;
		
		// PathFinding
		pathFinder = new AStarPathFinder(this, width * height, false);
		
		// Create the maze
		createMaze();
		
		// Create the Tiles
        refreshTiles();
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

		// Updates the Tiles offsets
		for(Tile t : tiles) {
			t.setxOffset(xOffset);
			t.setyOffset(yOffset);
			t.update(container, game, delta);
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		// Render the Map
		for(Tile t : tiles) {
			if((t.getxPos() + t.getxOffset() >= 0 - GameSystem.globalScale && t.getxPos() + t.getxOffset() <= container.getWidth()  / GameSystem.globalScale)
			&& (t.getyPos() + t.getyOffset() >= 0 - GameSystem.globalScale && t.getyPos() + t.getyOffset() <= container.getHeight() / GameSystem.globalScale)) {
				t.render(container, game, g);
			}
		}
	}
	
	/**
	 * Generate the Tiles rectangles
	 * @throws SlickException 
	 */
	public void refreshTiles() throws SlickException {
		for(int i = 0; i < width; i++) {
			if(i != width - 1) {
				for(int j = 0; j < height; j++) {
					if(j != height - 1) {
						if(blocked[i][j]) {
							tiles.add(new Tile(i, j, tileImage));
						}
					}
				}
			}
		}
	}
	
	/**
	 * Runs the algorithm to generate the Maze
	 */
	public void createMaze() {
		
		// Basic settings
		int back;
		String possibleDirections;
		Point pos = new Point(1, 1);		// Leave the 0, 0 for the outer wall
		
		// Possible movement list
		ArrayList<Integer> moves = new ArrayList<Integer>();
		moves.add(pos.y + (pos.x * width));
		
		// Are there still available movements?
	    while (!moves.isEmpty()) {
	    		
	    		// Possible Directions
	    		possibleDirections = "";
	        
	    		if ((pos.y + 2 < height ) && (blocked[pos.x][pos.y + 2]) && (pos.y + 2 != height - 1)) {
	        		possibleDirections += DOWN;
	        }
	        
	        if ((pos.y - 2 >= 0 ) && (blocked[pos.x][pos.y - 2]) && (pos.y - 2 != height - 1) ) {
	            possibleDirections += UP;
	        }
	        
	        if ((pos.x - 2 >= 0 ) && (blocked[pos.x - 2][pos.y]) && (pos.x - 2 != width - 1) ) {
	            possibleDirections += LEFT;
	        }
	        
	        if ((pos.x + 2 < width ) && (blocked[pos.x + 2][pos.y])  && (pos.x + 2 != width - 1) ) {
	            possibleDirections += RIGHT;
	        }
	        
	        // Check if found any possible movements
	        if ( possibleDirections.length() > 0 ) {
	        	
	        		// Get a random direction
	        		switch (possibleDirections.charAt(new java.util.Random().nextInt(possibleDirections.length()))) {
	                
	        			case '0': // North
	                    blocked[pos.x][pos.y - 2] = false;
	                    blocked[pos.x][pos.y - 1] = false;
	                    pos.y -= 2;
	                    break;
	                
	                case '1': // South
	                		blocked[pos.x][pos.y + 2] = false;
	                		blocked[pos.x][pos.y + 1] = false;
	                    pos.y += 2;
	                    break;
	                
	                case '2': // West
	                		blocked[pos.x - 2][pos.y] = false;
	                		blocked[pos.x - 1][pos.y] = false;
	                    pos.x -= 2;
	                    break;
	                
	                case '3': // East
	                    blocked[pos.x + 2][pos.y] = false;
	                    blocked[pos.x + 1][pos.y] = false;
	                    pos.x += 2;
	                    break;        
	            }
	            
	        		// Add a new possible movement
	            moves.add(pos.y + (pos.x * width));
	        
	        } else {
	        
	        		// There are no more possible movements
	        		back = moves.remove(moves.size() - 1);
	        		pos.x = back / width;
	        		pos.y = back % width;
	        }
	    }
    }

	@Override
	public boolean blocked(PathFindingContext context, int x, int y) {
		return blocked[x][y];
	}

	@Override
	public float getCost(PathFindingContext arg0, int arg1, int arg2) {
		return 0;
	}

	@Override
	public int getHeightInTiles() {
		return height;
	}

	@Override
	public int getWidthInTiles() {
		return width;
	}

	@Override
	public void pathFinderVisited(int arg0, int arg1) {
	}
	
	public int getStartingX() {
		return startingX;
	}

	public void setStartingX(int startingX) {
		this.startingX = startingX;
	}

	public int getStartingY() {
		return startingY;
	}

	public void setStartingY(int startingY) {
		this.startingY = startingY;
	}

	public int getEndingX() {
		return endingX;
	}

	public void setEndingX(int endingX) {
		this.endingX = endingX;
	}

	public int getEndingY() {
		return endingY;
	}

	public void setEndingY(int endingY) {
		this.endingY = endingY;
	}

	public AStarPathFinder getPathFinder() {
		return pathFinder;
	}

	public void setPathFinder(AStarPathFinder pathFinder) {
		this.pathFinder = pathFinder;
	}
	
	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}

	@Override
	public String toString() {
		
		String fullMaze = "";
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				
				if(blocked[i][j]) {
					fullMaze += "*";
				} else {
					fullMaze += " ";
				}
			}
			
			fullMaze += "\n";
		}
		
		return fullMaze;
	}
}