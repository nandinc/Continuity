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
    protected int currentMapId = -1;

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
     * Nyitó pálya azonosítója
     */
    protected final int defaultMapId = 1;
    
    /**
     * Inicializálja a tartalmazott objektumokat
     */
    public Game() {
        mapFactory = new MapFactory();
        initPubSub();
        timer = new Timer();
        viewportState = VIEWPORT_STATE.MAP; 

        timer.setPubSub(pubSub);
    }
    
    private void initPubSub() {
        pubSub = new PubSub();
        
        pubSub.on("controller:loadMap", new Subscriber() {
            
            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                int mapId = (Integer)eventParameter;
                
                Logger.logStatus("Load map" + mapId);
                if (!loadMap(mapId)) {
                	pubSub.emit("game:mapNotFound", mapId);
                }
            }
        });

        pubSub.on("controller:resetMap", new Subscriber() {
        	
        	@Override
        	public void eventEmitted(String eventName, Object eventParameter) {
        		loadMap(currentMapId == -1 ? defaultMapId : currentMapId);
        	}
        });
        
        pubSub.on("controller:loadNextMap", new Subscriber() {
        	
        	@Override
        	public void eventEmitted(String eventName, Object eventParameter) {
        		loadNextMap();
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
                loadNextMap();
            }
        });
    }

    /**
     * Betölti a megadott pályát,
     * @param mapId Betöltendő pálya azonosítója
     * @return Sikerült-e betölteni a pályát. Ha a pálya nem létezett, false-ot ad vissza.
     */
    private boolean loadMap(int mapId) {
    	// Unload the previous map
    	if (currentMap != null) {
    		 currentMap.unsubscribe();
        }
    	
    	// Now load the new one
    	try {
    		// Load it
    		currentMap = mapFactory.getMap(mapId, pubSub);
            // Update the map id
    		currentMapId = mapId;
    		// Refresh the view
            pubSub.emit("view:invalidate", null);
        } catch (MapNotFoundException e) {
        	return false;
        }
    	
    	return true;
    }
    
    /**
     * Betölti a soron következő pályát.
     * @return Sikerült-e a pálya betöltése, ha vége a játéknak, false-ot ad vissza.
     */
    private boolean loadNextMap() {
    	if (!loadMap(currentMapId == -1 ? defaultMapId : currentMapId + 1)) {
    		pubSub.emit("game:endOfGame", null);
    		return false;
    	}
    	
    	return true;
    }

    /**
     * Elindítja a játékot
     */
    public void start() {
        timer.start();
    }

    /**
     * Megváltoztatja a nézetet a jelenlegi ellenkezőjére
     */
    private void toggleViewportState() {
        if (viewportState == VIEWPORT_STATE.CLOSE) {
            viewportState = VIEWPORT_STATE.MAP;
            Logger.logStatus("Viewport changed to map view");
        } else {
            viewportState = VIEWPORT_STATE.CLOSE;
            Logger.logStatus("Viewport changed to close view");
        }
    }
    
    /**
     * Visszaadja a jelenlegi nézet típusát
     * @return jelenlegi nézet típusa
     */
    public VIEWPORT_STATE getViewportState() {
    	return viewportState;
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