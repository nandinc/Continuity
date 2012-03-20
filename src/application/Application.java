package application;

import java.io.IOException;

import model.Game;
import model.exception.MapNotFoundException;

public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		newGame(1);
		
		// prevent program termination
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void newGame(int mapId) {
		Game game = new Game();
		
		try {
			game.loadMap(mapId);
		} catch (MapNotFoundException e) {
			e.printStackTrace();
		}
		
		game.start();
	}

}
