package model;

/**
 * A játékot szervező objektum. Felelős az új pályák betöltéséért és a teljesített pályák törléséért.
 */
public class Game {

	/**
	 * Az aktuális pálya
	 */
    protected Map currentMap;

    /**
     * Betölti a megadott pályát.
     * @param mapId
     */
    public void loadMap(int mapId) {
        throw new UnsupportedOperationException();
    }

}