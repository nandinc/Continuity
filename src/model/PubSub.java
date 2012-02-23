package model;

/**
 * Globális üzenet közvetítő csatorna, mely a Publish/Subscribe mintát valósítja meg.
 */
public class PubSub {

    /**
     * Esemény történésének jelzése.
     * @param eventName Az esemény neve.
     * @param data Az eseményhez tartozó paraméter.
     */
    public void emit(String eventName, Object data) {
        throw new UnsupportedOperationException();
    }

}
