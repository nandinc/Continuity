package model;

import model.exception.MapNotFoundException;

/**
 * A játékot szervező objektum. Felelős az új pályák betöltéséért és a teljesített pályák törléséért.
 * 
 * @responsibility A játékot reprezentáló objektum, amely kezeli az aktuális pályát.
 */
public class Game {

    /**
     * Az aktuális pálya
     */
    protected Map currentMap;

    /**
     * Pályákat előállítására
     */
    protected MapFactory mapFactory;

    /**
     * Idő múlásának követésére
     */
    protected Timer timer;

    /**
     * Kommunikációs csatorna
     */
    protected PubSub pubSub;

    protected VIEWPORT_STATE viewportState; 

    public Game() {
        mapFactory = new MapFactory();
        pubSub = new PubSub();
        timer = new Timer();
        viewportState = VIEWPORT_STATE.CLOSE;

        timer.setPubSub(pubSub);

        // TODO remove this debug subscription
        /*pubSub.on("tick", new Subscriber() {

			@Override
			public void eventEmitted(String eName, Object eParameter) {
				System.out.println(eName);
			}
		});*/
    }

    /**
     * Betölti a megadott pályát.
     * @param mapId Pálya azonosítója
     * @throws MapNotFoundException 
     */
    public void loadMap(int mapId) throws MapNotFoundException {
        currentMap = mapFactory.getMap(mapId, pubSub);
    }

    public void start() {
        timer.start();
    }

    public void toggleViewportState() {
        if (viewportState == VIEWPORT_STATE.CLOSE) {
            viewportState = VIEWPORT_STATE.MAP;
            timer.stop();
        } else {
            viewportState = VIEWPORT_STATE.CLOSE;
            timer.start();
        }
    }

    public PubSub getPubSub() {
        return pubSub;
    }

    public Map getMap() {
        return currentMap;
    }

}