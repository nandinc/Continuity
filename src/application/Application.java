package application;

import controller.console.FrontController;

import ui.console.FrontView;

import model.DIRECTION;
import model.Game;
import model.PubSub;
import model.Subscriber;
import model.exception.MapNotFoundException;

public class Application {

    /**
     * @param args
     */
    public static void main(String[] args) {
        final Game game = new Game();
        @SuppressWarnings("all") 
        FrontView view = new FrontView(game);
        
        PubSub pubSub = game.getPubSub();
        
        pubSub.on("loadMap", new Subscriber() {
            
            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                int mapId = (Integer)eventParameter;
                
                try {
                    game.loadMap(mapId);
                    game.start();
                } catch (MapNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        
        pubSub.on("viewportSwitch", new Subscriber() {
            
            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                game.toggleViewportState();
            }
        });
        
        pubSub.on("moveFrame", new Subscriber() {
            
            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                DIRECTION direction = (DIRECTION)eventParameter;
                
                game.getMap().moveFrame(direction);
            }
        });

        FrontController controller = new FrontController(game.getPubSub());
        controller.start();
    }

}
