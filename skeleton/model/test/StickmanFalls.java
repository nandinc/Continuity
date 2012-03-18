package model.test;

import model.DIRECTION;
import model.Frame;
import model.Game;
import model.Map;
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
			
			PubSub ps = new PubSub();
			SkeletonLogger.register(ps, "ps");
			
			Map m = new Map();
			Frame f = new Frame(m);
			
			Stickman s = new Stickman();
			SkeletonLogger.register(s, "s");
			s.setFrame(f);
			
			s.setPubSub(ps);
		SkeletonLogger.unMute();
		
		// Esés imitálása.
		s.move(DIRECTION.DOWN);
	}

}
