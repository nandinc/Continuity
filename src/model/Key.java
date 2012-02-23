package model;

/**
 * Kulcsok megszerzése a pálya teljesítésének feltétele.
 */
public class Key extends AbstractFrameItem {

    /**
     * Flag, ami jelzi, hogy megszerezték-e az adott kulcs objektumot.
     */
    private boolean collected = false;

    /**
     * @return Igaz, ha megszerezték-e az adott kulcs objektumot.
     */
    public boolean isCollected() {
        return this.collected;
    }

}
