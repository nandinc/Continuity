package model;

import java.util.*;

/**
 * A pályákat reprezentálja. Tartalmazza a kereteket és azok elhelyezkedését,
 * kontrolállja az átrendezésüket, melyet kommunikáció nélkül meg tud tenni, mivel a keretek nem tudnak relatív elhelyezkedésükről.
 * 
 * @responsibility Számon tartja a pályán elhelyezett kulcsokat számát, valamint a már összegyűjtött kulcsok számát. Felelős a keretek mozgatásáért, amit kommunikácó nélkül meg tud valósítani.
 */
public class Map {

	/**
	 * A pályához tartozó kereteket tárolja. A Map osztály
	 * meg tudja állapítani a keretek közötti szomszédossági
	 * viszonyokat a gyűjtemény alapján.
	 */
    protected Collection<Frame> frames;

    /**
     * Hozzáadja a megadott elemet az elem által specifikált pozícióhoz.
     * Amennyiben a pálya inicializálása során az adott helyen még nincs keret,
     * létrehoz egyet. 
     * A hozzáadott elem pozícióját megváltoztatja úgy, hogy az relatív
     * legyen a tartalmazó kerethez.
     * @param item
     */
    public void addItem(FrameItem item) {
        throw new UnsupportedOperationException();
    }

    /**
     * Visszaadja a megadott keret direction irányba található
     * szomszédját. null-t ad vissza, ha a megadott irányban
     * nincs szomszéd.
     * 
     * @param caller
     * @param direction
     */
    public Frame getNeighbour(Frame caller, DIRECTION direction) {
        throw new UnsupportedOperationException();
    }

    /**
     * Kicseréli az üres helyet a megadott iránnyal ellentétes
     * szomszédjával.
     * 
     * @param d Mozgazás iránya
     */
    public void moveFrame(DIRECTION d) {
        throw new UnsupportedOperationException();
    }

}