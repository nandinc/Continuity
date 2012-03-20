package model;

import java.util.ArrayList;
import java.util.List;


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
	 * 
	 * A 2 dimenziós lista először az x eltolással, 
	 * majd az y eltolással indexelhető.
	 */
    protected List<List<Frame>> frames = new ArrayList<List<Frame>>();

    /**
     * Hozzáadja a megadott elemet az elem által specifikált pozícióhoz.
     * Amennyiben a pálya inicializálása során az adott helyen még nincs keret,
     * létrehoz egyet. 
     * A hozzáadott elem pozícióját megváltoztatja úgy, hogy az relatív
     * legyen a tartalmazó kerethez.
     * @param item
     */
    public void addItem(FrameItem item) {
        // determine containing frame
    	int frameCoordX = item.getArea().getX() / Frame.FRAME_WIDTH;
    	int frameCoordY = item.getArea().getY() / Frame.FRAME_HEIGHT;
    	
    	// add new frame columns if needed
    	if (frames.size() <= frameCoordX) {
    		for (int currentSize = frames.size(); currentSize <= frameCoordX; currentSize++) {
				frames.add(new ArrayList<Frame>());
			}
    	}
    	
    	List<Frame> column = frames.get(frameCoordX);
    	// add new frames if needed
    	if (column.size() <= frameCoordY) {
    		for (int currentSize = column.size(); currentSize <= frameCoordY; currentSize++) {
    			column.add(new Frame());
    		}
    	}
    	
    	// set item position relative to frame
    	int itemOffsetX = item.getArea().getX() % Frame.FRAME_WIDTH;
    	int itemOffsetY = item.getArea().getY() % Frame.FRAME_HEIGHT;
    	
    	item.getArea().setX(itemOffsetX);
    	item.getArea().setY(itemOffsetY);
    	
    	// finally add item to the containing frame
    	Frame containingFrame = column.get(frameCoordY);
    	containingFrame.addItem(item);
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