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
        invalidate();
    }

    /**
     * Beállítja az elemet tartalmazó keretet.
     * @param frame
     */
    @Override
    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    /**
     * Kommunikációs csatorna beállítása
     */
    @Override
    public void setPubSub(PubSub pubSub) {
        this.pubSub = pubSub;
    }
    
    /**
     * Beállítja a map fájlban talált opcionális paramétereket
     * 
     * Alapértelmezetten nem csinál semmit
     * 
     * @param args Map file vonatkozó sorának összes argumentuma 
     */
    public void setAdditionalParameters(String[] args) {
        // do nothing by default
    }

    /**
     * Megadja, hogy számba kell-e venni az elemet,
     * ha a keretek közötti átjárást vizsgáljuk.
     * 
     * @return true, ha számba kell venni
     */
    @Override
    public boolean doesAffectTraversability() {
        return false;
    }

    /**
     * Nem csinál semmit ütközés esetén
     * 
     * Alapértelmezetten az elemek nem reagálnak az üktözésre.
     * E metódus felüldefiniálásával ez a viselkedés megváltoztatható.
     */
    @Override
    public void collision(FrameItem colliding) {
        // do nothing like a boss
    }

    /**
     * Jelzés kibocsájtása az elem állapotának megváltozásáról
     */
    protected void invalidate() {
        if (pubSub != null) {
            pubSub.emit("view:invalidate", null);
        }
    }
}