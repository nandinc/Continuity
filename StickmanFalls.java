package model.test;

import model.Game;
import model.PubSub;
import model.Stickman;
import model.Timer;
import utils.SkeletonLogger;

/**
 * @file 2.4 Stickman Falls
 */
public class StickmanFalls extends AbstractTest{

	public StickmanFalls() {
		setName("Stickman falls");
	}
	
	@Override
	protected void runTest() {
		// Elemek inicializálása.
		SkeletonLogger.mute();
			Timer t = new Timer();
			SkeletonLogger.register(t, "t");
			PubSub ps = new PubSub(new Game());
			SkeletonLogger.register(ps, "ps");
			Stickman s = new Stickman();
			SkeletonLogger.register(s, "s");
			s.setPubSub(ps);
		SkeletonLogger.unMute();
		
		// Esés imitálása.
		s.fall();
	}

}
