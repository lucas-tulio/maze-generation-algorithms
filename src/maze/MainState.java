package maze;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MainState extends BasicGameState {

	// Camera
	private float xOffset;
	private float yOffset;
	private float cameraSpeed;
	
	// The Maze
	private Maze maze;
	
	// The Character
	private Character character;
	
	// Speed
	private int speed;
	
	// Timer
	
	
	@Override
	public int getID() {
		return 0;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
		// Start by creating the Maze
		restartMaze();
		
		// Set the Speed
		speed = 1;
		
		// Camera
		xOffset = 0f;
		yOffset = 0f;
		cameraSpeed = 1f;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		// Get Input
		Input i = container.getInput();
		
		// Generate the Maze again
		if(i.isKeyPressed(Input.KEY_R)) {
			restartMaze();
		}
				
		// Camera movement
		if(i.isKeyDown(Input.KEY_W)) {
			yOffset += cameraSpeed;
		} else if(i.isKeyDown(Input.KEY_S)) {
			yOffset -= cameraSpeed;
		}
		if(i.isKeyDown(Input.KEY_A)) {
			xOffset += cameraSpeed;
		} else if(i.isKeyDown(Input.KEY_D)) {
			xOffset -= cameraSpeed;
		}
		
		// Zoom
		if(i.isKeyPressed(Input.KEY_MINUS)) {
			if(GameSystem.globalScale >= 2f) {
				GameSystem.globalScale /= 2;
			}
		} else if(i.isKeyPressed(Input.KEY_EQUALS)) {
			GameSystem.globalScale *= 2;
		}
		
		// Pass the offset on to the Entities
		character.setxOffset(xOffset);
		character.setyOffset(yOffset);
		maze.setxOffset(xOffset);
		maze.setyOffset(yOffset);
		
		// Update the Maze (x and y offsets)
		maze.update(container, game, delta);
		
		// Move the Character!
		character.update(container, game, delta * speed);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		// Background Color
		g.setBackground(Color.white);
		
		// Scale
		g.scale(GameSystem.globalScale, GameSystem.globalScale);
		
		// Render the Map
		maze.render(container, game, g);
		
		// Render the Character
		character.render(container, game, g);
	}
	
	public void restartMaze() throws SlickException {
		
		// Create the Maze
		maze = new Maze(100, 100);
		
		// Create the Character
		character = new Character(1, 0, maze, 1);
	}
}