package model;

import debug.Logger;
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
    }

    /**
     * Betölti a megadott pályát.
     * @param mapId Pálya azonosítója
     * @throws MapNotFoundException 
     */
    public void loadMap(int mapId) throws MapNotFoundException {
        Logger.logStatus("Load map" + mapId);
        currentMap = mapFactory.getMap(mapId, pubSub);
    }

    public void start() {
        timer.start();
    }

    public void toggleViewportState() {
        if (viewportState == VIEWPORT_STATE.CLOSE) {
            viewportState = VIEWPORT_STATE.MAP;
            timer.stop();
            Logger.logStatus("Viewport changed to map view");
        } else {
            viewportState = VIEWPORT_STATE.CLOSE;
            timer.start();
            Logger.logStatus("Viewport changed to close view");
        }
    }

    public PubSub getPubSub() {
        return pubSub;
    }

    public Map getMap() {
        return currentMap;
    }

}