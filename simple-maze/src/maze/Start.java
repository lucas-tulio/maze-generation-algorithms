package maze;

import org.newdawn.slick.AppGameContainer;

public class Start {
	public static void main(String args[]) {
		try {
			// Create the App Container
			AppGameContainer container = new AppGameContainer(new GameSystem("Mazenator"));
			
			// Start shit up
			container.setDisplayMode(1280, 800, false);
			container.setShowFPS(false);
			container.setTargetFrameRate(60);
			container.setMinimumLogicUpdateInterval(20);
			container.setMaximumLogicUpdateInterval(20);
			container.setVSync(true);
			container.start();

		} catch(Exception e) {e.printStackTrace();}
	}
}
