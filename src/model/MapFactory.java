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
		SkeletonLogger.create(this, "mf");
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
    	
    	Map m = new Map();
    	SkeletonLogger.create(m, "m");
    	
    	FrameItem fa = new Key();
    	SkeletonLogger.create(fa, "fa");
    	m.addItem(fa);
    	
    	FrameItem fb = new Key();
    	SkeletonLogger.create(fb, "fb");
    	m.addItem(fb);
    	
    	FrameItem fc = new Door();
    	SkeletonLogger.create(fc, "fc");
    	m.addItem(fc);
    	
    	SkeletonLogger.back(m);
    	return m;
    }
}
