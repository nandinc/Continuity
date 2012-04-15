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
        
        pubSub.on("controller:loadMap", new Subscriber() {
            
            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                int mapId = (Integer)eventParameter;
                
                try {
                    if (currentMap != null) {
                        unloadMap(currentMap);
                    }
                    Logger.logStatus("Load map" + mapId);
                    loadMap(mapId);
                    start();
                } catch (MapNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        
        pubSub.on("tick", new Subscriber() {
            
            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                if (viewportState == VIEWPORT_STATE.CLOSE) {
                    pubSub.emit("game:tick", null);
                }
            }
        });
        
        pubSub.on("controller:move:1", new Subscriber() {
            
            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                if (viewportState == VIEWPORT_STATE.CLOSE) {
                    pubSub.emit("game:move:1", eventParameter);
                } else {
                    Logger.logStatus("Don't move: wrong viewport");
                }
            }
        });
        
        pubSub.on("controller:move:2", new Subscriber() {
            
            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                if (viewportState == VIEWPORT_STATE.CLOSE) {
                    pubSub.emit("game:move:2", eventParameter);
                } else {
                    Logger.logStatus("Don't move: wrong viewport");
                }
            }
        });
        
        pubSub.on("controller:viewportSwitch", new Subscriber() {
            
            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                toggleViewportState();
            }
        });
        
        pubSub.on("controller:moveFrame", new Subscriber() {
            
            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                if (viewportState == VIEWPORT_STATE.MAP) {
                    DIRECTION direction = (DIRECTION)eventParameter;
                    
                    getMap().moveFrame(direction);
                } else {
                    Logger.logStatus("Don't move frame: wrong viewport");
                }
            }
        });
        
        pubSub.on("map:completed", new Subscriber() {
            
            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                try {
                    unloadMap(currentMap);
                    Logger.reset();
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
        currentMap = mapFactory.getMap(mapId, pubSub);
        currentMapId = mapId;
    }
    
    /**
     * Leiratkoztatja a kapott pálya elemeit a kommunikácós csatornáról
     */
    private void unloadMap(Map map) {
        map.unsubscribe();
    }

    /**
     * Elindítja a játékot
     */
    public void start() {
        // TODO review this whole method after prototype release
        //timer.start();
        // show map
        Logger.logStatus("Start game");
        pubSub.emit("view:invalidate", null);
    }

    /**
     * Megváltoztatja a nézetet a jelenlegi ellenkezőjére
     */
    public void toggleViewportState() {
        if (viewportState == VIEWPORT_STATE.CLOSE) {
            viewportState = VIEWPORT_STATE.MAP;
            Logger.logStatus("Viewport changed to map view");
        } else {
            viewportState = VIEWPORT_STATE.CLOSE;
            Logger.logStatus("Viewport changed to close view");
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