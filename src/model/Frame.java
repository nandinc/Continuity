package model;

/**
 * Egy pályán belüli keret, amelyben mozoghat a stickman. Tartalmazza a pálya többi objektumát (Door, Key, Platform).
 */
public class Frame {

    /**
     * Hozzáadja a megadott elemet a kerethez.
     * 
     * @param item
     */
    public void addItem(FrameItem item) {
        throw new UnsupportedOperationException();
    }

    /**
     * Eltávolítja a megadott elemet a keretből
     * 
     * @param item
     */
    public void removeItem(FrameItem item) {
        throw new UnsupportedOperationException();
    }

    /**
     * A metódust hívó elem kérést intéz a kerethez,
     * hogy el szeretné foglalni a megadott területet.
     * A keret felelőssége a terület ellenőrzése, és szabad
     * terület esetén az elem pozíciójának frissítése.
     * 
     * @param item
     * @param area
     */
    public boolean requestArea(FrameItem item, Area area) {
        throw new UnsupportedOperationException();
    }

    /**
     * Ellenőrzi, hogy a megadott területen
     * található-e szilárd objektum.
     * 
     * @param area
     */
    protected boolean checkCollision(Area area) {
        throw new UnsupportedOperationException();
    }
}