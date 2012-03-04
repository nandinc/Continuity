package model;

import java.util.*;

/**
 * Üzenetközvetítő csatorna, mely a Publish/Subscribe mintát valósítja meg.
 * 
 * @responsibility Üzenetközvetítő osztály, mely feliratkozásokat tart számon, és ha valakitől eseményt kap, arról értesíti az arra feliratkozottakat.
 */
public class PubSub {

    /**
     * Esemény publikálása
     * 
     * @param eventName Az esemény neve.
     * @param data Az eseményhez tartozó paraméter.
     */
    public void emit(String eventName, Object data) {
        throw new UnsupportedOperationException();
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
