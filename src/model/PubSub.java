package model;

/**
 * Globális üzenet közvetítő csatorna, feliratkozással lehet lekérdezni a csatornára feltett információkat.
 */
public class PubSub {

    /**
     * Esemény történésének jelzése.
     * @param eventName Az esemény neve.
     * @param data ????? Az eseményt generáló objektum.
     */
    public void emit(String eventName, Object data) {
        throw new UnsupportedOperationException();
    }

}