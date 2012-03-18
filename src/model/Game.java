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
    	// Regisztrálás
    	SkeletonLogger.create(this, "g");
    	
    	// initialize components
    	mapFactory = new MapFactory();
    	Timer t = new Timer();
    	pubSub = new PubSub(this);
    }

    /**
     * Betölti a megadott pályát.
     * @param mapId
     */
    public void loadMap(int mapId) {
    	// Metódushívás rögzítése.
		SkeletonLogger.call(this, "loadMap", mapId);
		
		// Map bekérése a Mapfactoryból
    	mapFactory.getMap(mapId, pubSub);
    	
    	// Függvény vége, visszatérés logolása.
		SkeletonLogger.back();
    }

	/**
	 * @param data
	 */
	public void callBack_KeyPickedUp(Object data) {
		// Metódushívás rögzítése.
		SkeletonLogger.call(this, "callBack:KeyPickedUp", data);
		// Függvény vége, visszatérés logolása.
		SkeletonLogger.back();
	}

}