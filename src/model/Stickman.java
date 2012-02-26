package model;

/**
 * A játék által irányított figurát reprezentáló objektum.
 */
public class Stickman extends AbstractFrameItem {

    /**
     * A figura mozgatása a megadott irányba.
     * @param direction
     */
    public void move(DIRECTION direction) {
        throw new UnsupportedOperationException();
    }

    /**
     * A figura igényelt elmozdulása után elfoglalandó
     * terület kiszámítása.
     * @param direction
     */
    private Area getNewAreaByDirection(DIRECTION direction) {
        throw new UnsupportedOperationException();
    }

    /**
     * A figura pozíciójának visszaállítása az
     * utolsó ellenőrzőpontra.
     */
    public void resetToCheckpoint() {
        throw new UnsupportedOperationException();
    }
}
