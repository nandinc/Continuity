package application;

import java.io.IOException;

import ui.console.FrontView;

import model.Game;
import model.exception.MapNotFoundException;

public class Application {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Game game = newGame(1);
        FrontView view = new FrontView(game);

        game.start();

        // prevent program termination
        try {
            System.in.read();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static Game newGame(int mapId) {
        Game game = new Game();

        try {
            game.loadMap(mapId);
        } catch (MapNotFoundException e) {
            e.printStackTrace();
        }

        return game;
    }

}
