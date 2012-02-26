package model;

/**
 * Üzenet közvetítő csatorna, mely a Publish/Subscribe mintát valósítja meg.
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

}
