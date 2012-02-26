package model;

/**
 * Alapértelmezett megvalósítása egy keretbe helyezett objektumnak. 
 * Nyilvántartja a kereten belüli pozícióját, valamint hordoz
 * egy referenciát a tartalmazó keretre.
 */
public abstract class AbstractFrameItem implements FrameItem {

	/**
	 * A tartalmazó kereten belül elfoglalt pozíció
	 */
    protected Area area;
    
    /**
     * A tartalmazó keret
     */
    protected Frame frame;
}