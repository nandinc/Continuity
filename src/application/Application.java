package application;

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
        
        /*// view is not unused, it subscribes to events
        // supress unused warning
        @SuppressWarnings("all") 
        ui.console.FrontView view = new ui.console.FrontView(game);
        
        controller.console.FrontController controller = new controller.console.FrontController(game.getPubSub());
        controller.start();*/
        
        ui.graphical.FrontView view = new ui.graphical.FrontView(game);
        
        controller.graphical.FrontController controller = new controller.graphical.FrontController(game);
        controller.start();
    }

}
