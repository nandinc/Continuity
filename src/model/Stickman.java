package model;

/**
 * A játékos által irányított figurát reprezentáló elem.
 * 
 * @responsibility A játékos által irányított figura, mely a pályán mozog.
 */
public class Stickman extends AbstractFrameItem {

    /**
     * Ellenőrzőpontot tartalmazó keret
     */
    private Frame checkpointFrame = null;
    
    /**
     * Ellenőrzőponthoz tartozó terület
     */
    private Area checkpointArea = null;
    
    /**
     * Az ugrásból származó hátralévő felfelé
     * irányuló mozgások száma
     */
    private int jumpSpeed = 0;
    
    /**
     * Azonosító
     * 
     * A pályán található különböző stickmanek megkülönböztetésére szolgál
     */
    private Integer identifier;
    
    /**
     * A figura mozgatása a megadott irányba.
     * @param direction
     */
    public void move(DIRECTION direction) {
        Area newArea = getNewAreaByDirection(direction);
        frame.requestArea(this, newArea);
        
        if (direction == DIRECTION.UP && jumpSpeed == 0) {
            jumpSpeed = 1;
        }
    }

    /**
     * A figura igényelt elmozdulása után elfoglalandó
     * terület kiszámítása.
     * @param direction
     */
    private Area getNewAreaByDirection(DIRECTION direction) {
        Area newArea = area.clone();

        // change the new area by direction
        // please note: the origin of the coordinate system
        // is in the top left corner.
        switch (direction) {
        case UP:
            // TODO implement jump properly
            newArea.setY(
                newArea.getY() - 1
            );
            break;

        case RIGHT:
            newArea.setX(
                newArea.getX() + 1
            );
            break;

        case DOWN:
            newArea.setY(
                newArea.getY() + 1
            );
            break;

        case LEFT:
            newArea.setX(
                newArea.getX() - 1
            );
            break;

        default:
            break;
        }

        return newArea;
    }

    /**
     * A figura pozíciójának visszaállítása az
     * utolsó ellenőrzőpontra.
     */
    public void resetToCheckpoint() {
        area = checkpointArea;
        checkpointFrame.addItem(this);
        invalidate();
    }

    /**
     * Tartalmazó keret beállítása
     */
    @Override
    public void setFrame(Frame frame) {
        super.setFrame(frame);

        // set initial checkpoint
        // if not yet set
        if (checkpointFrame == null) {
            checkpointFrame = frame;
        }

        if (checkpointArea == null) {
            // area is the current area
            checkpointArea = area;
        }
    }

    /**
     * Kommunikációs csatorna beállítása
     * 
     * Feliratkozik a kezelendő eseményekre.
     */
    @Override
    public void setPubSub(PubSub pubSub) {
        this.pubSub = pubSub;

        pubSub.on("game:tick", new Subscriber() {

            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                if (jumpSpeed == 0) {
                    move(DIRECTION.DOWN);
                } else {
                    jumpSpeed--;
                }
            }
        });
        
        pubSub.on("controller:move:" + identifier.toString(), new Subscriber() {
            
            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                DIRECTION direction = (DIRECTION)eventParameter;
                move(direction);
            }
        });

        pubSub.on("key:collected", new Subscriber() {
            
            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                // eventParameter is the collector of the key
                // use Stickman.this because this refers to the anonymous Subscriber class
                if (Stickman.this == eventParameter) {
                    // we just collected a key
                    checkpointFrame = frame;
                    checkpointArea = area;
                }
            }
        });
    }
    
    /**
     * Beállítja a map fájlban talált opcionális paramétereket
     * 
     * Beállítja a stickmand id-t
     * 
     * @param args Map file vonatkozó sorának összes argumentuma 
     */
    public void setAdditionalParameters(String[] args) {
        identifier = Integer.parseInt(args[5]);
    }

    /**
     * Megadja, hogy az elem szilárd-e vagy sem.
     * Ez a kereten belüli mozgások esetén az
     * ütközések ellenőrzésekor használatos.
     * 
     * @return true, minden stickman szilárd
     */
    @Override
    public boolean isSolid() {
        // By setting solid flag true,
        // it opens a new perspective to cooperative
        // strategies like jumping from the top of
        // the other player.
        return true;
    }
    
    /**
     * Megadja a stickman azonosítóját
     * @return azonosító
     */
    public int getStickmanId() {
        return identifier;
    }
}
