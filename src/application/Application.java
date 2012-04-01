package application;

import controller.console.FrontController;

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

        //game.start();
        FrontController controller = new FrontController(game.getPubSub());
        controller.start();
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
