package model;

import utils.SkeletonLogger;

/**
 * Az idő múlását nyilvántartó objektum. 
 * Elsősorban Stickman esését szabályozhatjuk vele, de a
 * periodikus időközönként kiadott 'tick' események
 * a stickmentől vagy bármely más objektumtól függetlenek. 
 * 
 * @responsibility Időzítésért felelős osztály, bizonyos időközönként kibocsát egy 'tick' eseményt az átadott PubSub objektumra.
 * @file Timer osztály
 */
public class Timer {
	
	public Timer() {
		SkeletonLogger.create(this, "t");
	}
	
	/**
	 * Az eseménykezelő csatorna,
	 * melyen jelzi az idő múlását.
	 */
    PubSub pubsub;

}