package model.test;

import utils.SkeletonLogger;
import model.DIRECTION;
import model.Frame;
import model.Map;
import model.Stickman;

/**
 * @file 2.0 Stickman Moves
 */
public class StickmanMovesTest extends AbstractTest{

	public StickmanMovesTest(){
		setName("Stickman Moves");
	}
	
	@Override
	protected void runTest() {
		Map m = new Map();
		SkeletonLogger.register(m, "m");
		Stickman s = new Stickman();
		SkeletonLogger.register(s, "s");
		Frame f = new Frame(m);
		SkeletonLogger.register(f, "f");
		s.setFrame(f);
		DIRECTION d = DIRECTION.RIGHT;
		SkeletonLogger.register(d, "d");
		
		s.move(d);
	}

}
