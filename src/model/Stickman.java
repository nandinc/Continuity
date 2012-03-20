package model;

/**
 * A játékos által irányított figurát reprezentáló elem.
 * 
 * @responsibility A játékos által irányított figura, mely a pályán mozog.
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

	@Override
	public boolean isSolid() {
		// By setting solid flag true,
		// it opens a new perspective to cooperative
		// strategies like jumping from the top of
		// the other player.
		return true;
	}
}
