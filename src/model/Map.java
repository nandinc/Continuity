package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import debug.Logger;


/**
 * A pályákat reprezentálja. Tartalmazza a kereteket és azok elhelyezkedését,
 * kontrolállja az átrendezésüket, melyet kommunikáció nélkül meg tud tenni, mivel a keretek nem tudnak relatív elhelyezkedésükről.
 * 
 * @responsibility Számon tartja a pályán elhelyezett kulcsokat számát, valamint a már összegyűjtött kulcsok számát. Felelős a keretek mozgatásáért, amit kommunikácó nélkül meg tud valósítani.
 */
public class Map {

    /**
     * A pályához tartozó kereteket tárolja. A Map osztály
     * meg tudja állapítani a keretek közötti szomszédossági
     * viszonyokat a gyűjtemény alapján.
     * 
     * A 2 dimenziós lista először az x eltolással, 
     * majd az y eltolással indexelhető.
     */
    protected List<List<Frame>> frames = new ArrayList<List<Frame>>();
    
    /**
     * Kommunikációs csatorna
     * 
     * Kulcs hozzáadás és felvétel, ajtó megérintésének
     * követéséhez.
     */
    private PubSub pubSub;
    
    /**
     * Beállítja a kommunikációs csatornát
     * 
     * Figyeli a kulccsal és ajtóval kapcsolatos eseményeket
     * @param pubSubParameter a beállítandó kommunikációs csatorna
     */
    public void setPubSub(PubSub pubSubParameter) {
        this.pubSub = pubSubParameter;
        
        pubSubParameter.on("key:added", new Subscriber() {
            
            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                remainingKeyCount++;
            }
        });
        
        pubSubParameter.on("key:collected", new Subscriber() {
            
            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                remainingKeyCount--;
            }
        });
        
        pubSubParameter.on("door:touched", new Subscriber() {
            
            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                if (remainingKeyCount == 0) {
                    Logger.logStatus("Door opened");
                    pubSub.emit("map:completed", null);
                } // else do nothing like a boss
            }
        });
    }
    
    /**
     * Nem összegyűjtött kulcsok száma
     */
    private int remainingKeyCount = 0;

    /**
     * Hozzáadja a megadott elemet az elem által specifikált pozícióhoz.
     * Amennyiben a pálya inicializálása során az adott helyen még nincs keret,
     * létrehoz egyet. 
     * A hozzáadott elem pozícióját megváltoztatja úgy, hogy az relatív
     * legyen a tartalmazó kerethez.
     * @param item
     */
    public void addItem(FrameItem item) {
        // determine containing frame
        int frameCoordX = item.getArea().getX() / Frame.FRAME_WIDTH;
        int frameCoordY = item.getArea().getY() / Frame.FRAME_HEIGHT;

        // add new frame columns if needed
        if (frames.size() <= frameCoordX) {
            for (int currentSize = frames.size(); currentSize <= frameCoordX; currentSize++) {
                frames.add(new ArrayList<Frame>());
            }
        }

        List<Frame> column = frames.get(frameCoordX);
        
        Frame containingFrame;
        
        // add new frames if needed
        if (column.size() <= frameCoordY) {
            // fill the column with nulls while reaching the needed row
            for (int currentSize = column.size(); currentSize < frameCoordY; currentSize++) {
                //Frame frame = new Frame();
                //frame.setMap(this);
                column.add(null);
            }
            // add the containing frame to the desired position
            containingFrame = new Frame();
            containingFrame.setMap(this);
            column.add(containingFrame);
            
        } else {    // there are already enough frames
            containingFrame = column.get(frameCoordY);
            if (containingFrame == null) {
                containingFrame = new Frame();
                containingFrame.setMap(this);
                column.set(frameCoordY, containingFrame);
            }
        }

        // set item position relative to frame
        int itemOffsetX = item.getArea().getX() % Frame.FRAME_WIDTH;
        int itemOffsetY = item.getArea().getY() % Frame.FRAME_HEIGHT;

        item.getArea().setX(itemOffsetX);
        item.getArea().setY(itemOffsetY);

        // finally add item to the containing frame
        containingFrame.addItem(item);
    }
    
    /**
     * Megkeresi az üres keret helyét, és feltölti null-al.
     */
    public void fillFrameGap() {
        int mapHeight = verticalFrameCount();
        for (List<Frame> column : frames) {
            int remaining = mapHeight - column.size();
            
            if (remaining > 0) {
                while (remaining > 0) {
                    column.add(null);
                    remaining--;
                }
            }
        }
    }

    /**
     * Visszaadja a megadott keret direction irányba található
     * szomszédját. null-t ad vissza, ha a megadott irányban
     * nincs szomszéd.
     * 
     * @param caller
     * @param direction
     */
    public Frame getNeighbour(Frame caller, DIRECTION direction) {
        // search for the given frame
        int callerColIndex = -2;
        int callerRowIndex = -2;

        SEARCH: for (int columnIndex = 0; columnIndex < frames.size(); columnIndex++) {
            List<Frame> column = frames.get(columnIndex);
            for (int rowIndex = 0; rowIndex < column.size(); rowIndex++) {
                if (column.get(rowIndex) == caller) {
                    // caller found
                    callerColIndex = columnIndex;
                    callerRowIndex = rowIndex;
                    break SEARCH;
                }
            }
        }

        // get neighbour index
        int neighbourColIndex = -1;
        int neighbourRowIndex = -1;

        switch (direction) {
        case UP:
            neighbourColIndex = callerColIndex;
            neighbourRowIndex = callerRowIndex - 1;
            break;

        case RIGHT:
            neighbourColIndex = callerColIndex + 1;
            neighbourRowIndex = callerRowIndex;
            break;

        case DOWN:
            neighbourColIndex = callerColIndex;
            neighbourRowIndex = callerRowIndex + 1;
            break;

        case LEFT:
            neighbourColIndex = callerColIndex - 1;
            neighbourRowIndex = callerRowIndex;
            break;

        default:
            break;
        }

        //check for bounds
        if (neighbourColIndex < 0 || neighbourColIndex >= frames.size()) {
            // neighbour is out of horizontal index
            return null;
        }

        List<Frame> neighbourColumn = frames.get(neighbourColIndex);

        if (neighbourRowIndex < 0 || neighbourRowIndex >= neighbourColumn.size()) {
            // neighbour is out of vertical index
            return null;
        }

        Frame neighbour = neighbourColumn.get(neighbourRowIndex);
        if (neighbour == null || caller.isTraversable(neighbour, direction) == false) {
            return null;
        } else {
            return neighbour;
        }
    }

    /**
     * Kicseréli az üres helyet a megadott iránnyal ellentétes
     * szomszédjával.
     * 
     * @param direction Mozgazás iránya
     */
    public void moveFrame(DIRECTION direction) {
        FrameIterator frameIterator = frameIterator();
        boolean emptyFrameFound = false;
        
        while (frameIterator.hasNext() && emptyFrameFound == false) {
            Frame frame = frameIterator.next();
            
            if (frame == null) { // empty frame found
                emptyFrameFound = true;
                Area emptyPosition = frameIterator.getFramePosition();
                Area neighbourPosition = new Area();
                
                // init neighbour position
                switch (direction) {
                case UP:
                    neighbourPosition.setX(
                        emptyPosition.getX()
                    );
                    
                    neighbourPosition.setY(
                        emptyPosition.getY() + 1
                    );
                    
                    break;

                case RIGHT:
                    neighbourPosition.setX(
                            emptyPosition.getX() - 1
                        );
                        
                        neighbourPosition.setY(
                            emptyPosition.getY()
                        );
                    break;

                case DOWN:
                    neighbourPosition.setX(
                        emptyPosition.getX()
                    );
                    
                    neighbourPosition.setY(
                        emptyPosition.getY() - 1
                    );
                    break;

                case LEFT:
                    neighbourPosition.setX(
                        emptyPosition.getX() + 1
                    );
                    
                    neighbourPosition.setY(
                        emptyPosition.getY()
                    );
                    break;

                default:
                    break;
                }
                
                //check for bounds
                if (neighbourPosition.getX() < 0 || neighbourPosition.getX() >= frames.size()) {
                    // neighbour is out of horizontal index
                    Logger.logStatus("Don't move frame: wrong direction");
                    return;
                }

                List<Frame> neighbourColumn = frames.get(neighbourPosition.getX());

                if (neighbourPosition.getY() < 0 || neighbourPosition.getY() >= neighbourColumn.size()) {
                    // neighbour is out of vertical index
                    Logger.logStatus("Don't move frame: wrong direction");
                    return;
                }

                Frame neighbourFrame = neighbourColumn.get(neighbourPosition.getY());
                if (neighbourFrame != null) {
                    // switch
                    frames.get(emptyPosition.getX()).set(emptyPosition.getY(), neighbourFrame);
                    frames.get(neighbourPosition.getX()).set(neighbourPosition.getY(), null);
                    
                    pubSub.emit("view:invalidate", null);
                    
                    Logger.logStatus("Move Frame "+direction.toString());
                } else {
                    // do nothing like a boss
                    Logger.logStatus("Don't move frame: wrong direction");
                }
            }
        }
        
        if (!emptyFrameFound) {
            Logger.logStatus("No empty frame found");
        }
    }

    /**
     * Visszaad egy iteratort, mellyel a tartalmazott kereteken lehet végigmenni
     * @return keret iterator
     */
    public FrameIterator frameIterator() {
        return new FrameIterator();
    }

    /**
     * A pálya által tartalmazott kereteken lépdel végig
     * @responsibility A mögöttes implementációtól függetlenül felsorolja a pálya által tartalmazott kereteket.
     */
    public class FrameIterator implements Iterator<Frame> {

        /**
         * Következő oszlop indexe
         */
        private int nextColumnIndex = 0;
        
        /**
         * Aktuális oszlop iteratora
         */
        private Iterator<Frame> currentColumnIterator = null;
        
        /**
         * Aktuális sor indexe
         */
        private int currentRowIndex;

        /**
         * Inicializálás
         */
        public FrameIterator() {
            List<Frame> firstColumn = getNextColumn();

            if (firstColumn != null) {
                currentColumnIterator = firstColumn.iterator(); 
            }
        }

        /**
         * Váltás a következő oszlopra
         * @return következő oszlop
         */
        private List<Frame> getNextColumn() {
            List<Frame> nextColumn = null;

            if (frames.size() > nextColumnIndex) {
                nextColumn = frames.get(nextColumnIndex);
                nextColumnIndex++;
                currentRowIndex = -1;
            }

            return nextColumn;
        }

        /**
         * Ellenőrzi, hogy van-e még bejáratlan keret
         * @return true, ha van még nem bejárt keret
         */
        @Override
        public boolean hasNext() {
            if (currentColumnIterator.hasNext() == true) {
                // current col is not finished yet
                return true;
            } else {
                // current col finished, is there any other col?
                List<Frame> nextColumn = getNextColumn();
                if (nextColumn != null && nextColumn.size() > 0) {
                    currentColumnIterator = nextColumn.iterator();
                    return true;
                } else {
                    return false;
                }
            }
        }

        /**
         * Lépés a következő keretre
         */
        @Override
        public Frame next() {
            currentRowIndex++;
            return currentColumnIterator.next();
        }

        /**
         * Megadja az aktuális keret által elfoglalt
         * pozíciót a pálya keretrácsában.
         * 
         * A bal felső sarokban a x:0, y:0
         * pozíciójú keret található.
         * 
         * @return aktuális keret pozíciója
         */
        public Area getFramePosition() {
            Area offset = new Area();

            offset.setX(nextColumnIndex-1);
            offset.setY(currentRowIndex);

            return offset;
        }

        /**
         * Nem támogatott a keret ilyen módú eltávolítása
         * @throws UnsupportedOperationException
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    /**
     * Megadja a tartalmazott keretek által alkotott keretrács szélességét
     * @return Tartalmazott keretek által alkotott keretrács szélessége
     */
    public int horizontalFrameCount() {
        return frames.size();
    }

    /**
     * Megadja a tartalmazott keretek által alkotott keretrács magasságát
     * @return Tartalmazott keretek által alkotott keretrács magassága
     */
    public int verticalFrameCount() {
        int verticalFrameCount = 0;
        for (List<Frame> column : frames) {
            if (column.size() > verticalFrameCount) {
                verticalFrameCount = column.size();
            }
        }

        return verticalFrameCount;
    }
    
    /**
     * Leiratkozik a kommunikációs csatornáról
     */
    public void unsubscribe() {
        pubSub.unsubEventSubscribers("key:added");
        pubSub.unsubEventSubscribers("key:collected");
        pubSub.unsubEventSubscribers("door:touched");
        pubSub.unsubEventSubscribers("game:tick");
        pubSub.unsubEventSubscribers("game:move:1");
        pubSub.unsubEventSubscribers("game:move:2");
        /*pubSub.unsubEventSubscribers("");
        pubSub.unsubEventSubscribers("");
        pubSub.unsubEventSubscribers("");*/
    }
}