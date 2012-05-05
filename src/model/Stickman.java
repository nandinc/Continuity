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
     * Azonosító
     * 
     * A pályán található különböző stickmanek megkülönböztetésére szolgál
     */
    private Integer identifier = 1;
    
    /**
     * Igaz, ha a Stickmen esés közben ütközött egy szilárd elemmel.
     * 
     * Ellenőrizendő ugrás előtt.
     */
    private boolean solidUnderFeet = false; 
    
    /**
     * Vertikális irányú sebesség
     */
    private int verticalSpeed = 0;
    
    /**
     * A figura mozgatása a megadott irányba.
     * @param direction
     */
    public void move(DIRECTION direction) {
        Area newArea = getNewAreaByDirection(direction);
        
        boolean successfulMove = frame.requestArea(this, newArea);
        
        if (!successfulMove && direction == DIRECTION.DOWN) {
            // something solid reached
            solidUnderFeet = true;
            verticalSpeed = 0;
        } else if (!successfulMove && direction == DIRECTION.UP && verticalSpeed > 0) {
            // upper border reached
            verticalSpeed = 0;
        }
    }
    
    /**
     * Ugrás indítása
     */
    public void jump() {
        if (solidUnderFeet) {
            verticalSpeed = 15;
            solidUnderFeet = false;
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
                verticalSpeed -= 2;
                
                if (verticalSpeed > 0) {
                    for (int i = 0; i < verticalSpeed; i++) {
                        move(DIRECTION.UP);
                    }
                } else {
                    for (int i = 0; i > verticalSpeed; i--) {
                        move(DIRECTION.DOWN);
                    }
                }
            }
        });
        
        pubSub.on("game:move:" + identifier.toString(), new Subscriber() {
            
            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                DIRECTION direction = (DIRECTION)eventParameter;
                
                if (direction != DIRECTION.UP) {
                    move(direction);
                } else {
                    jump();
                }
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
