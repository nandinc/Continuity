package model;

/**
 * Alapértelmezett megvalósítása egy keretbe helyezett objektumnak. 
 * Nyilvántartja a kereten belüli pozícióját, valamint hordoz
 * egy referenciát a tartalmazó keretre.
 * 
 * @responsibility Alapértelmezett megvalósítása egy keretben lévő objektumnak.
 * @file AbstractFrameItem osztály
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
    
    /**
     * Üzenetküldő
     */
    protected PubSub ps = null;
    
    /**
     * Üzenetküldő beállítás
     * @param ps Üzenetküldő
     */
    public void setPubSub(PubSub ps) {
    	this.ps = ps;
    }
}