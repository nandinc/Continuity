package model.test;

import utils.SkeletonLogger;
import model.Game;
import model.MapFactory;

/**
 * @file 1.1 Load Map
 */
public class LoadMapTest extends AbstractTest{

	public LoadMapTest() {
		setName("Load Map");
	}
	
	@Override
	protected void runTest() {
		// Objektumok inicializálása,
		// regisztrálása a Logger osztályba.
		Game g = new Game();
		SkeletonLogger.register(g, "g");
		MapFactory mp = new MapFactory();
		SkeletonLogger.register(mp, "mp");
		int mapId = 1;
		SkeletonLogger.register(mapId, "mapId");
		
		g.loadMap(mapId);
	}

}
