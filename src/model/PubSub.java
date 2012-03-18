package model;

import java.util.*;

import utils.SkeletonLogger;

/**
 * Üzenetközvetítő csatorna, mely a Publish/Subscribe mintát valósítja meg.
 * 
 * @responsibility Üzenetközvetítő osztály, mely feliratkozásokat tart számon, és ha valakitől eseményt kap, arról értesíti az arra feliratkozottakat.
 * @file PubSub osztály
 */
public class PubSub {
	private Game g;

	public PubSub(Game g) {
		SkeletonLogger.create(this, "ps");
		this.g = g;
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
    	
    	// Értesítjük a map-ot a kulcs felvételéről.
    	g.callBack_KeyPickedUp(data);
    	
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
        throw new UnsupportedOperationException();
    }

    /**
     * Az eseményekre feliratkozott eseménykezelőket tárolja.
     */
    private Collection<Subscriber> subscribers;

}
