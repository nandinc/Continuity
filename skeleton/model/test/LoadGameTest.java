package model.test;

import model.Game;

/**
 * 
 * @file 1.0 Load Game teszt
 */
public class LoadGameTest extends AbstractTest {

	public LoadGameTest() {
		setName("Load Game");
	}
	
	@Override
	public void runTest() {
		Game game = new Game();
		game.loadMap(1);
	}

}
