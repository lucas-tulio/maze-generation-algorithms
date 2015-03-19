package maze;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;


public class Character implements Mover {
	
	// Basic Attributes
	private float xPos;
	private float yPos;
	private float xOffset;
	private float yOffset;
	private int xTile;
	private int yTile;
	
	// Sprite
	private Image sprite;
	private ArrayList<Point> track;
	
	// Movement Timer
	private int currentMovementTimer;
	private int movementTimerLimit;
	
	// Pathfinding
	private Path path;
	private int currentStep;
	
	public Character(int xTile, int yTile, Maze maze, int movementTimerLimit) throws SlickException {
		
		// Basic Attributes
		this.xTile = xTile;
		this.yTile = yTile;
		xPos = xTile;
		yPos = yTile;
		
		// Sprite
		sprite = new Image("/res/red1x1.png");
		sprite.setFilter(Image.FILTER_NEAREST);
		track = new ArrayList<Point>();
		
		// Start the Timer
		currentMovementTimer = 0;
		this.movementTimerLimit = movementTimerLimit;
		
		// Pathfinding
		path = maze.getPathFinder().findPath(this, xTile, yTile, maze.getEndingX(), maze.getEndingY());
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		// Timer
		currentMovementTimer += delta;
		if(currentMovementTimer >= movementTimerLimit) {
			currentMovementTimer = 0;
			
			// Read the next step in the Path
			if(path != null && currentStep < path.getLength()) {
				
				// Send the current position to the Track
				track.add(new Point(xTile, yTile));
				
				// Move!
				xTile = path.getX(currentStep);
				yTile = path.getY(currentStep);
						
				// Next time, read next step
				currentStep++;
			}
		}
		
		// Update the sprite position
		xPos = xTile;
		yPos = yTile;
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		// Render the Image
		g.drawImage(sprite, xPos + xOffset, yPos + yOffset);
		
		// Render the Track
		float s = GameSystem.globalScale;
		for(Point p : track) {			
			if((p.x * s + xOffset >= 0 - s && p.x + xOffset <= container.getWidth()  / s)
			&& (p.y * s + yOffset >= 0 - s && p.y + yOffset <= container.getHeight() / s)) {
				g.drawImage(sprite, p.x + xOffset, p.y + yOffset);
			}
		}
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
	
	
}