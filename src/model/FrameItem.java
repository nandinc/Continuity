package model;

/**
 * A Frame osztály ezen a felületen keresztül éri el a tartalmazott objektumokat.
 *
 * Ezen interfacet valósítják meg a pályán lévő objektumok (Platform, Door, Key, Stickman)
 * 
 * @responsibility A keretben elhelyezkedő elemek által megvalósított iterfész, melyen keresztül a keret menedzselni tudja a benne lévő elemeket.
 */
public interface FrameItem {

    /**
     * Visszaadja az elem pozícióját a kereten belül.
     * @return
     */
    public Area getArea();

    /**
     * Beállítja az elem pozícióját a kereten belül.
     * @param area
     */
    public void setArea(Area area);

    /**
     * Beállítja az elemet tartalmazó keretet.
     * @param frame
     */
    public void setFrame(Frame frame);

    /**
     * Beállítja a kommunikációs csatornát
     * @param pubsub object
     */
    public void setPubSub(PubSub pubSub);
    
    /**
     * Beállítja a map fájlban talált opcionális paramétereket
     * @param args Map file vonatkozó sorának összes argumentuma 
     */
    public void setAdditionalParameters(String[] args);

    /**
     * Megadja, hogy az elem szilárd-e vagy sem.
     * Ez a kereten belüli mozgások esetén az
     * ütközések ellenőrzésekor használatos.
     * @return true, ha szilárd, false egyébként
     */
    public boolean isSolid();

    /**
     * Megadja, hogy számba kell-e venni az elemet,
     * ha a keretek közötti átjárást vizsgáljuk.
     * 
     * @return true, ha számba kell venni
     */
    public boolean doesAffectTraversability();

    /**
     * A tartalmazó keret jelezheti ezen a metóduson keresztül,
     * hogy egy másik elem, melyet paraméterül ad,
     * hozzáért (collision) ehhez az elemhez.
     * @param colliding
     */
    public void collision(FrameItem colliding);
}
