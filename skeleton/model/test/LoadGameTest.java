package model.test;

import model.Game;

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
