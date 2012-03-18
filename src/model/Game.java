package model;

import utils.SkeletonLogger;

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
    
    private MapFactory mapFactory;
    private PubSub pubSub;
    
    public Game() {
    	// register this in JTrace
    	SkeletonLogger.register(this, "g");
    	
    	// initialize components
    	mapFactory = new MapFactory();
    	Timer t = new Timer();
    	pubSub = new PubSub();
    }

    /**
     * Betölti a megadott pályát.
     * @param mapId
     */
    public void loadMap(int mapId) {
		SkeletonLogger.call(this, "loadMap", mapId);
		
    	mapFactory.getMap(mapId, pubSub);
    	
		SkeletonLogger.back();
    }

}