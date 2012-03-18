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
		SkeletonLogger.create(this, "mp");
	}
	
    /**
     * Létrehozza a megadott azonosítójú pályát
     * és feltölti elemekkel.
     * 
     * @param mapId
     * @param ps
     */
    public Map getMap(int mapId, PubSub ps) {
    	// Metódushívás rögzítése.
    	SkeletonLogger.call(this, "getMap", mapId, ps);
    	
    	// Teszteléshez új pálya készítése és néhány elem belepakolása.
    	Map m = new Map();
    	// Regisztrálás a logger osztályba.
    	SkeletonLogger.create(m, "m");
    	
    	FrameItem fa = new Key();
    	// Regisztrálás a logger osztályba.
    	SkeletonLogger.create(fa, "fa");
    	m.addItem(fa);
    	
    	FrameItem fb = new Key();
    	// Regisztrálás a logger osztályba.
    	SkeletonLogger.create(fb, "fb");
    	m.addItem(fb);
    	
    	FrameItem fc = new Door();
    	// Regisztrálás a logger osztályba.
    	SkeletonLogger.create(fc, "fc");
    	m.addItem(fc);
    	
    	// Függvény vége, visszatérés logolása.
    	SkeletonLogger.back(m);
    	return m;
    }
}
