package model;

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
    
    private MapFactory mapFactory;
    private PubSub pubSub;
    
    public Game() {
    	// TODO register this in JTrace
    	
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
    	mapFactory.getMap(mapId, pubSub);
    }

}