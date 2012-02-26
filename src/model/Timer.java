package model;

/**
 * Az idő múlását nyilvántartó objektum. 
 * Elsősorban Stickman esését szabályozhatjuk vele, de a
 * periodikus időközönként kiadott 'tick' események
 * a stickmentől vagy bármely más objektumtól függetlenek. 
 * 
 * @responsibility Időzítésért felelős osztály, bizonyos időközönként kibocsát egy 'tick' eseményt az átadott PubSub objektumra.
 */
public class Timer {
	
	/**
	 * Az eseménykezelő csatorna,
	 * melyen jelzi az idő múlását.
	 */
    PubSub pubsub;

}