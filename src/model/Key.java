package model;

/**
 * A pályán található kulcsokat reprezentáló objektum.
 */
public class Key extends AbstractFrameItem {

    /**
     * Flag, ami jelzi, hogy megszerezték-e a kulcsot.
     */
    private boolean collected = false;

    /**
     * Megadja, hogy megszerezték-e a kulcsot.
     * @return Igaz, ha megszerezték-e a kulcsot.
     */
    public boolean isCollected() {
        return this.collected;
    }

}
