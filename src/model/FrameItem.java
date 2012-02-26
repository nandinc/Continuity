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
    Area getArea();

    /**
     * Beállítja az elem pozícióját a kereten belül.
     * @param area
     */
    void setArea(Area area);

    /**
     * Beállítja az elemet tartalmazó keretet.
     * @param frame
     */
    void setFrame(Frame frame);
    
    /**
     * Megadja, hogy az elem szilárd-e vagy sem.
     * Ez a kereten belüli mozgások esetén az
     * ütközések ellenőrzésekor használatos.
     * @return
     */
    boolean isSolid();

    /**
     * A tartalmazó keret jelezheti ezen a metóduson keresztül,
     * hogy egy másik elem, melyet paraméterül ad,
     * hozzáért (collision) ehez az elemhez.
     * @param colliding
     */
    void collision(FrameItem colliding);
}
