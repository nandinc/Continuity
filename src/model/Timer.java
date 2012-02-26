package model;

/**
 * Az idő múlását nyilvántartó objektum. 
 * Elsősorban Stickman esését szabályozhatjuk vele, de a
 * periodikus időközönként kiadott 'tick' események
 * a stickmentől vagy bármely más objektumtól függetlenek. 
 */
public class Timer {
	
	/**
	 * Az eseménykezelő csatorna,
	 * melyen jelzi az idő múlását.
	 */
    PubSub pubsub;

}