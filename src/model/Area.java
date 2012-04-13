package model;

import utils.SkeletonLogger;

/**
 * Egy tetszőleges keret egy területét leíró osztály.
 * Ez az osztály elfedi, hogy egy keret valójában hány dimenziós,
 * valamint azt, hogy egy keret egy összefüggő ponthalmaza milyen 
 * paraméterekkel írható le.
 * 
 * @responsibility Területrészt leíró osztály. Összehasonlítható egy másik osztállyal, hogy fedik-e egymást.
 * @file Area osztály
 */
public class Area {

    /**
     * Ellenőrzi, hogy a kapott Area objektummal van-e közös pontja.
     * A metódus feltételezi, hogy a két terület azonos keretben található.
     * 
     * @param other
     * @return true Ha van ilyen közös pont
     */
    public boolean hasCollision(Area other) {
        // TODO review this logic
        return
                (	x <= other.x
                &&  other.x < x+width
                &&  y <= other.y
                &&  other.y < y+height)

                ||

                (	other.x <= x
                &&  x < other.x+other.width
                &&  other.y <= y
                &&  y < other.y+other.height)
                ;
    }

    /**
     * Kiszámítja a kapott terület relatív elhelyezkedését this-hez képest.
     * 
     * Takes only one dimension into account at a time.
     * If the areas are placed diagonally, the horizontal
     * direction will be returned.
     * 
     * Egyszerre csak egy dimenziót vesz figyelembe. Amennyiben
     * a kapott terület x és y eltolása is eltérő a this eltolásához képest,
     * az x irányú relatív pozícióval tér vissza.
     * 
     * @param area
     * @return A kapott terület relatív elhelyezkedése vagy null, ha nincs eltérés
     */
    public DIRECTION getRelativeDirection(Area area) {
        int directionX = x - area.x;

        if (directionX < 0) {
            return DIRECTION.RIGHT;
        }

        if (directionX > 0) {
            return DIRECTION.LEFT;
        }

        int directionY = y - area.y;

        if (directionY < 0) {
            return DIRECTION.DOWN;
        }

        if (directionY > 0) {
            return DIRECTION.UP;
        }

        return null;
    }

    /**
     * Megadja a terület bal felső sarkának x eltolását
     * @return x eltolás
     */
    public int getX() {
        return this.x;
    }

    /**
     * Beállítja a terület bal felső sarkának x eltolását
     * @param x x eltolás
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Megadja a terület bal felső sarkának y eltolását
     * 
     * Az eltolás felülről lefelé nő
     * @return y eltolás
     */
    public int getY() {
        return this.y;
    }

    /**
     * Beállítja a terület bal felső sarkának y eltolását
     * 
     * Az eltolás felülről lefelé nő
     * @param y eltolás
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Megadja a terület szélességét
     * @return a terület szélessége
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Beállítja a terület szélességét
     * @param width a terület szélessége
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Megadja a terület magasságát
     * @return a terület magassága
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Beállítja a terület magasságát
     * @param height a terület magassága
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Létrehoz egy új területet, magával megegyező paraméterekkel
     * 
     * A másolt paraméterek: x, y, height, width.
     */
    @Override
    public Area clone() {
        Area clone = new Area();
        clone.setX(x);
        clone.setY(y);
        clone.setHeight(height);
        clone.setWidth(width);

        return clone;
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