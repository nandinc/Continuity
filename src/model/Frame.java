package model;

/**
 * Egy pályán belüli keret, amelyben mozoghat a Stickman. Tartalmazza a pálya többi elemét (Door, Key, Platform).
 * 
 * @responsibility A pálya által alkotott táblázat egy cellája, amely elemeket tartalmaz. Ez felelős az elemek (Stickman) mozgatásáért kereten belül és között egyaránt. Két elem egy helyen való tartózkodásáról értesíti az elemeket (collision notify).
 */
public class Frame {

	public static final int FRAME_WIDTH = 200;
	public static final int FRAME_HEIGHT = 150;
	
    /**
     * Hozzáadja a megadott elemet a kerethez.
     * 
     * @param item
     */
    public void addItem(FrameItem item) {
        item.setFrame(this);
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
     * Megállapítja, hogy a megkapott Keret és saját
     * maga között fennáll-e az átjárhatóság a
     * megadott irányban.
     * 
     * @param frame
     * @param d
     */
    protected boolean isTraversable(Frame frame, DIRECTION d) {
        throw new UnsupportedOperationException();
    }
}