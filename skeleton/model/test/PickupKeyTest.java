package model.test;

import model.Game;
import model.Key;
import model.PubSub;
import model.Stickman;
import utils.SkeletonLogger;

/**
 * @file 2.5 Pick up a key
 */
public class PickupKeyTest extends AbstractTest{
	public PickupKeyTest(){
		setName("Pick up key");
	}

	@Override
	protected void runTest() {
		// Inicializálás és beregisztrálás a Loggerbe
		// Logger mute-olása, ami azt jelenti, hogy a konstruktorók
		// által hívott .create metódus nem fog lefutni.
		SkeletonLogger.mute();
			Game g = new Game();
			SkeletonLogger.register(g, "g");
			PubSub ps = new PubSub(g);
			SkeletonLogger.register(ps, "ps");
			Stickman s = new Stickman();
			SkeletonLogger.register(s, "stickman");
			Key k = new Key();
			SkeletonLogger.register(k, "k");
		SkeletonLogger.unMute();
		
		// Üzenet küldő bejegyzése a kulcshoz.
		k.setPubSub(ps);
		// Ütköztetés elindítása.
		k.collision(s);
	}
}
