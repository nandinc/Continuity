package model;

/**
 * Egy tetszőleges keret egy területét leíró osztály.
 * Ez az osztály elfedi, hogy egy keret valójában hány dimenziós,
 * valamint azt, hogy egy keret egy összefüggő ponthalmaza milyen 
 * paraméterekkel írható le.
 * 
 * @responsibility Területrészt leíró osztály. Összehasonlítható egy másik osztállyal, hogy fedik-e egymást.
 */
public class Area {

    /**
     * Ellenőrzi, hogy a kapott Area objektummal van-e közös pontja.
     * A metódus feltételezi, hogy a két terület azonos keretben található.
     * 
     * @param area
     * @return true Ha van ilyen közös pont
     */
    public boolean hasCollision(Area area) {
        throw new UnsupportedOperationException();
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    /**
     * A terület bal felső sarkának x eltolása
     */
    private int x;
    
    /**
     * A terület bal felső sarkának y eltolása
     */
    private int y;
    
    /**
     * A terület szélessége
     */
    private int width;
    
    /**
     * A terület magassága
     */
    private int height;

}