package model;

/**
 * Egy pályán belüli keret, amelyben mozoghat a Stickman. Tartalmazza a pálya többi elemét (Door, Key, Platform).
 * 
 * @responsibility A pálya által alkotott táblázat egy cellája, amely elemeket tartalmaz. Ez felelős az elemek (Stickman) mozgatásáért kereten belül és között egyaránt.
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

    /**
     * Megállapítja, hogy a megadott kerettel
     * átjárható-e.
     * 
     * @param frame
     */
    protected boolean isTraversable(Frame frame) {
        throw new UnsupportedOperationException();
    }
}