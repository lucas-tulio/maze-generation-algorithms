package maze;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class Tile {
	
	private float xPos;
	private float yPos;
	private float xOffset;
	private float yOffset;
	
	private Image tileImage;
	
	public Tile(float x, float y, Image tileImage) throws SlickException {
		
		this.tileImage = tileImage;
		xPos = x;
		yPos = y;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
	}
	
	/**
	 * Maze blocks
	 * @param container
	 * @param game
	 * @param g
	 * @throws SlickException
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(tileImage, xPos + xOffset, yPos + yOffset);
	}
	
	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
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