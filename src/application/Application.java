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
        
        controller.graphical.FrontController controller = new controller.graphical.FrontController(game);
        
        ui.graphical.FrontView view = new ui.graphical.FrontView(game, controller);
        
        controller.start();
        game.start();
        
        controller.newGame();
    }

}
