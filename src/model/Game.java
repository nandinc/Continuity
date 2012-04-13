package model;

import debug.Logger;
import model.exception.MapNotFoundException;

/**
 * A játékot szervező objektum. Felelős az új pályák betöltéséért és a teljesített pályák törléséért.
 * 
 * @responsibility A játékot reprezentáló objektum, amely kezeli az aktuális pályát.
 * @file Game osztály
 */
public class Game {

    /**
     * Az aktuális pálya
     */
    protected Map currentMap;
    
    /**
     * Az aktuális pálya azonosítója
     */
    protected int currentMapId;

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

    /**
     * Nézet állapota
     */
    protected VIEWPORT_STATE viewportState; 

    /**
     * Inicializálja a tartalmazott objektumokat
     */
    public Game() {
        mapFactory = new MapFactory();
        initPubSub();
        timer = new Timer();
        viewportState = VIEWPORT_STATE.CLOSE;

        timer.setPubSub(pubSub);
    }
    
    private void initPubSub() {
        pubSub = new PubSub();
        
        pubSub.on("map:completed", new Subscriber() {
            
            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                try {
                    loadMap(currentMapId + 1);
                } catch (MapNotFoundException e) {
                    // end of map list
                    // TODO implement end of game rutine
                } 
            }
        });
    }

    /**
     * Betölti a megadott pályát.
     * @param mapId Pálya azonosítója
     * @throws MapNotFoundException 
     */
    public void loadMap(int mapId) throws MapNotFoundException {
        Logger.logStatus("Load map" + mapId);
        currentMap = mapFactory.getMap(mapId, pubSub);
        currentMapId = mapId;
    }

    /**
     * Elindítja a játékot
     */
    public void start() {
        // TODO review this whole method after prototype release
        //timer.start();
        // show map
        Logger.logStatus("Start game");
        pubSub.emit("invalidate", null);
    }

    /**
     * Megváltoztatja a nézetet a jelenlegi ellenkezőjére
     */
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

    /**
     * Megadja a használt kommunikációs csatornát
     * @return kommunikációs csatorna
     */
    public PubSub getPubSub() {
        return pubSub;
    }

    /**
     * Megadja az aktuális pályát
     * @return az aktuális pálya
     */
    public Map getMap() {
        return currentMap;
    }
}