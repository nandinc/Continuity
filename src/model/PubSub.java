package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import utils.SkeletonLogger;

/**
 * Üzenetközvetítő csatorna, mely a Publish/Subscribe mintát valósítja meg.
 * 
 * @responsibility Üzenetközvetítő osztály, mely feliratkozásokat tart számon, és ha valakitől eseményt kap, arról értesíti az arra feliratkozottakat.
 * @file PubSub osztály
 */
public class PubSub {
	
	public PubSub() {
		SkeletonLogger.create(this, "ps");
		
		subscribers = new HashMap<String, List<Subscriber>>();
	}
	
    /**
     * Esemény publikálása
     * 
     * @param eventName Az esemény neve.
     * @param data Az eseményhez tartozó paraméter.
     */
    public void emit(String eventName, Object data) {
    	// Metódushívás rögzítése.
    	SkeletonLogger.call(this, "emit", eventName, data);
    	
    	if (subscribers.containsKey(eventName)) {
	    	List<Subscriber> eventSubs = subscribers.get(eventName);
	    	for (Subscriber subscriber : eventSubs) {
				subscriber.eventEmitted(eventName, data);
			}
    	}
    	
    	// Függvény vége, visszatérés logolása.
    	SkeletonLogger.back();
    }

    /**
     * Feliratkozás eseményre
     * 
     * @param eventName Az esemény neve
     * @param callback Az esemény bekövetkeztekor meghívandó eseménykezelő
     */
    public void on(String eventName, Subscriber callback) {
        if (!subscribers.containsKey(eventName)) {
        	subscribers.put(eventName, new LinkedList<Subscriber>());
        }
        
        subscribers.get(eventName).add(callback);
    }

    /**
     * Az eseményekre feliratkozott eseménykezelőket tárolja.
     */
    private Map<String, List<Subscriber>> subscribers;

}
