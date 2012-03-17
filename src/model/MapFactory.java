package model;

import utils.SkeletonLogger;

/**
 * Azonosító alapján Pályákat szolgáltat.
 * Létrehozza a pályát, majd az azonosító alapján feltölti
 * a megfelelő objektumokkal.
 * 
 * @responsibility Felelős a pályák létrehozásáért, bennük a keretek és az elemek elhelyezéséért.
 * @file MapFactory osztály
 */
public class MapFactory {

	public MapFactory() {
		SkeletonLogger.register(this, "mf");
		SkeletonLogger.call(this, "<<create>>");
	}
	
    /**
     * Létrehozza a megadott azonosítójú pályát
     * és feltölti elemekkel.
     * 
     * @param mapId
     * @param ps
     */
    public Map getMap(int mapId, PubSub ps) {
    	SkeletonLogger.call(this, "getMap", mapId, ps);
    	return null;
    }
}
