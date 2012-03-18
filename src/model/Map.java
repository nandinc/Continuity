package model;

import java.util.*;

import utils.SkeletonLogger;

/**
 * A pályákat reprezentálja. Tartalmazza a kereteket és azok elhelyezkedését,
 * kontrolállja az átrendezésüket, melyet kommunikáció nélkül meg tud tenni, mivel a keretek nem tudnak relatív elhelyezkedésükről.
 * 
 * @responsibility Számon tartja a pályán elhelyezett kulcsokat számát, valamint a már összegyűjtött kulcsok számát. Felelős a keretek mozgatásáért, amit kommunikácó nélkül meg tud valósítani.
 * @file Map osztály
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
    	// Metódushívás rögzítése.
    	SkeletonLogger.call(this, "addItem", item);
    	
    	// Terület lekérdezése.
        item.getArea();
       
        // Függvény vége, visszatérés logolása.
        SkeletonLogger.back();
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
    	// Metódushívás rögzítése.
    	SkeletonLogger.call(this, "getNeighbour", caller, direction);
    	
    	// Felhasználótól megkérdezzük, hogy van-e a lépés irányában frame.
    	boolean hasNeighbourInDirection = SkeletonLogger.askYesOrNo("hasNeighbourInDirection");
    	
    	// Ha van frame a lépés irányába, akkor létrehozunk egy Frame-et a teszteléshez,
    	// amelyet a szomszédos frame-ként fogunk kezelni.
    	if (hasNeighbourInDirection) {
    		Frame neighbourFrame = new Frame(this);
        	SkeletonLogger.register(neighbourFrame, "neighbour");
        	
        	// Megvizsgáljuk, hogy az adott irányba átjárható-e a szomszédos frame.
    		boolean isTraversable = neighbourFrame.isTraversable(caller, direction);
    		
    		// Ha átjárható, akkor visszatérítjük a szomszédos framet.
    		if (isTraversable) {
    			SkeletonLogger.back(neighbourFrame);
    			return neighbourFrame;
    		// Ha nem átjárható, akkor null-al térünk vissza.
    		} else {
    			SkeletonLogger.back(null);
    			return null;
    		}
    	}
    	
    	// Ha nincs Frame a lépés irányába, akkor is null-al térünk vissza.
    	SkeletonLogger.back(null);
    	return null;
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