package model;

/**
 * A pályán található kulcsokat reprezentáló objektum.
 * 
 * @responsibility Kulcs elem, melyet megérintve a Stickman meg tud szerezni. Erről egy esemény küldésén keresztül értesíti a külvilágot.
 */
public class Key extends AbstractFrameItem {

	/**
	 * A kulcs összegyűjtöttségének állapotát tárolja.
	 */
    private boolean collected;

    /**
     * Megadja, hogy megszerezték-e a kulcsot.
     * @return Igaz, ha megszerezték-e a kulcsot.
     */
    public boolean isCollected() {
        return this.collected;
    }

}
