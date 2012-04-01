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
        // TODO review this whole method after prototype release
        //timer.start();
        // show map
        Logger.logStatus("Start game");
        pubSub.emit("invalidate", null);
    }

    public void toggleViewportState() {
        if (viewportState == VIEWPORT_STATE.CLOSE) {
            viewportState = VIEWPORT_STATE.MAP;
            Logger.logStatus("Viewport changed to map view");
            timer.stop();
        } else {
            viewportState = VIEWPORT_STATE.CLOSE;
            Logger.logStatus("Viewport changed to close view");
            // TODO enable this after prototype release
            //timer.start();
        }
    }

    public PubSub getPubSub() {
        return pubSub;
    }

    public Map getMap() {
        return currentMap;
    }

}