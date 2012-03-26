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
     * Calculates the relative direction of the given area.
     * 
     * Takes only one dimension into account at a time.
     * If the areas are placed diagonally, the horizontal
     * direction will be returned.
     * 
     * @param area
     * @return the relative direction or null if it has the same position
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
            return DIRECTION.UP;
        }

        if (directionY > 0) {
            return DIRECTION.DOWN;
        }

        return null;
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