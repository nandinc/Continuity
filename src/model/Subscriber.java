package model;

/**
 * A PubSub objektumnak átadható eseménykezelő felülete.
 */
public interface Subscriber {
	/**
	 * A PubSub objektum által meghívott metódus,
	 * a feliratkozott esemény bekövetkeztekor.
	 * 
	 * @param eventName az esemény neve
	 * @param eventParameter az esemény paramétere
	 */
	public void eventEmitted(String eventName, Object eventParameter);
}