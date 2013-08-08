package maze;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameSystem extends StateBasedGame {

	// States
	private MainState mainState;
	
	// Settings
	public static float globalScale = 8f;
	
	public static final int MAIN_STATE = 0;
	
	public GameSystem(String name) {
		super(name);
		
		mainState = new MainState();
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(mainState);
	}
}
