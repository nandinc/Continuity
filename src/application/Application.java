package application;

import controller.console.FrontController;

import ui.console.FrontView;

import model.Game;

/**
 * Az alkalmazás osztályait inicializáló entitás 
 * 
 * @responsibility A program működéséhez szükséges osztályok inicializálása és összekötése
 */
public class Application {

    /**
     * Létrehoz egy új játékot, előkészíti a controllert és a megjelenítést.
     * 
     * @param args Indítási paraméterek -- használaton kívül
     */
    public static void main(String[] args) {
        final Game game = new Game();
        // view is not unused, it subscribes to events
        // supress unused warning
        @SuppressWarnings("all") 
        FrontView view = new FrontView(game);
        
        FrontController controller = new FrontController(game.getPubSub());
        controller.start();
    }

}
