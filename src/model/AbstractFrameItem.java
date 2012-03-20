package model;

/**
 * Alapértelmezett megvalósítása egy keretbe helyezett objektumnak. 
 * Nyilvántartja a kereten belüli pozícióját, valamint hordoz
 * egy referenciát a tartalmazó keretre.
 * 
 * @responsibility Alapértelmezett megvalósítása egy keretben lévő objektumnak.
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
     * Publish Subscribe channel
     */
    protected PubSub pubSub;
    
	/**
	 * Visszaadja az elem pozícióját a kereten belül.
	 * @return
	 */
    @Override
    public Area getArea() {
    	return area;
    }

    /**
     * Beállítja az elem pozícióját a kereten belül.
     * @param area
     */
    @Override
    public void setArea(Area area) {
    	this.area = area;
    }

    /**
     * Beállítja az elemet tartalmazó keretet.
     * @param frame
     */
    @Override
    public void setFrame(Frame frame) {
    	this.frame = frame;
    }
    
    @Override
    public void setPubSub(PubSub pubSub) {
    	this.pubSub = pubSub;
    }
    
    /**
     * Do nothing on collision
     * 
     * By default, subclasses of this class doesn't response
     * to collisions. Override this method to take action
     * on collision.
     */
    @Override
    public void collision(FrameItem colliding) {
    	// do nothing like a boss
    }
}