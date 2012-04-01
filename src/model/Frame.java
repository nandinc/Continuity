package model;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import debug.Logger;

/**
 * Egy pályán belüli keret, amelyben mozoghat a Stickman. Tartalmazza a pálya többi elemét (Door, Key, Platform).
 * 
 * @responsibility A pálya által alkotott táblázat egy cellája, amely elemeket tartalmaz. Ez felelős az elemek (Stickman) mozgatásáért kereten belül és között egyaránt. Két elem egy helyen való tartózkodásáról értesíti az elemeket (collision notify).
 */
public class Frame {

    public static final int FRAME_WIDTH = 14;
    public static final int FRAME_HEIGHT = 5;

    private Map map;
    private List<FrameItem> items = new ArrayList<FrameItem>();

    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Hozzáadja a megadott elemet a kerethez.
     * 
     * @param item
     */
    public void addItem(FrameItem item) {
        items.add(item);
        item.setFrame(this);
    }

    /**
     * Eltávolítja a megadott elemet a keretből
     * 
     * @param item
     */
    public void removeItem(FrameItem item) {
        items.remove(item);
    }
    
    private boolean hasItem(FrameItem item) {
        return items.contains(item);
    }

    /**
     * A metódust hívó elem kérést intéz a kerethez,
     * hogy el szeretné foglalni a megadott területet.
     * A keret felelőssége a terület ellenőrzése, és szabad
     * terület esetén az elem pozíciójának frissítése.
     * 
     * @param item
     * @param area
     */
    public boolean requestArea(FrameItem item, Area area) {
        boolean inBounds = isAreaInBounds(area);

        if (inBounds) {
            Logger.logStatus("In-frame move");
            if (!hasCollision(item, area)) {
                if (!hasItem(item)) {
                    addItem(item);
                }
                // no collision with solid items
                Logger.logStatus("No collision, do move");
                item.setArea(area);

                // notify non solid colliding items
                notifyCollision(item, area);
                return true;
            } else {
                Logger.logStatus("Colliding with solid item, do nothing");
                // solid item in the way
                return false;
            }
        } else {
            Logger.logStatus("Inter-frame move");
            DIRECTION moveDirection = item.getArea().getRelativeDirection(area);

            if (moveDirection != null) {    // new area changed compared to the actual one
                Frame neighbour = map.getNeighbour(this, moveDirection);

                if (neighbour != null) {    // frame has neighbour in direction
                    Logger.logStatus("Neighbour found");
                    Area neighbourArea = transformAreaToNeighbourAreaByDirection(area, moveDirection);
                    // remove item before passing it to the neighbour
                    // to avoid cloned items
                    removeItem(item);
                    boolean successfulMove = neighbour.requestArea(item, neighbourArea);

                    if (successfulMove) {
                        return true;
                    } else {
                        // the way is blocked
                        // (e.g: another stickman is in the way, 
                        //  but not at the edge of the frame)
                        addItem(item);
                        Logger.logStatus("Colliding with solid item, do nothing");
                        return false;
                    }

                } else {	// no traversable neighbour
                    Logger.logStatus("No neighbour found");
                    
                    if (moveDirection == DIRECTION.DOWN) {
                        // TODO cast ahead, fix this design error.
                        // fall out
                        removeItem(item);
                        Stickman stickman = (Stickman) item;
                        stickman.resetToCheckpoint();
                        Logger.logStatus("Fall out, reset to checkpoint");
                    } else {
                        Logger.logStatus("Don't move");
                    }

                    return false;
                }
            } else {
                // no movement detected
                // shouldn't get here
                return false;
            }
        }
    }

    private boolean isAreaInBounds(Area area) {
        return (
                area.getX() >= 0 
                &&  area.getX() < FRAME_WIDTH
                &&  area.getY() >= 0
                &&  area.getY() < FRAME_HEIGHT
                );
    }

    /**
     * Ellenőrzi, hogy a megadott területen
     * található-e szilárd objektum.
     * 
     * @param area
     * @return true if there is collision, false otherwise
     */
    private boolean hasCollision(FrameItem movingItem, Area requestedArea) {
        for (FrameItem item : items) {
            if (item != movingItem && item.isSolid() && item.getArea().hasCollision(requestedArea)) {
                // area collides with solid item
                return true;
            }
        }

        return false;
    }

    private void notifyCollision(FrameItem colliding, Area area) {
        for (FrameItem item : items) {
            if (item.getArea().hasCollision(area)) {
                item.collision(colliding);
            }
        }
    }

    /**
     * Megállapítja, hogy a megkapott Keret és saját
     * maga között fennáll-e az átjárhatóság a
     * megadott irányban.
     * 
     * @param neighbour
     * @param d
     */
    protected boolean isTraversable(Frame neighbour, DIRECTION d) {
        boolean[] ownProfile = getSideProfile(d);

        DIRECTION neighbourSide;
        switch (d) {
        case UP:
            neighbourSide = DIRECTION.DOWN;
            break;

        case RIGHT:
            neighbourSide = DIRECTION.LEFT;
            break;

        case DOWN:
            neighbourSide = DIRECTION.UP;
            break;

        case LEFT:
            neighbourSide = DIRECTION.RIGHT;
            break;

        default:
            neighbourSide = null;
            break;
        }

        boolean[] neighbourProfile = neighbour.getSideProfile(neighbourSide);

        // check for profile equality
        for (int i = 0; i < ownProfile.length; i++) {
            if (ownProfile[i] != neighbourProfile[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns a boolean array side profile.
     * 
     * The side profiles size equals to the
     * FRAME_WIDTH or FRAME_HEIGHT, depending
     * on the passed in side argument.
     * 
     * sideProfile[i] is true, if there are
     * any part of any solid object at the i.
     * position of the requested side.
     * 
     * @param side
     * @return side profile
     */
    private boolean[] getSideProfile(DIRECTION side) {
        boolean[] profile;
        if (side == DIRECTION.UP || side == DIRECTION.DOWN) {
            // horizontal profile
            profile = new boolean[FRAME_WIDTH];
        } else {
            // vertical profile
            profile = new boolean[FRAME_HEIGHT];
        }

        // init profile with false
        for (int i = 0; i < profile.length; i++) {
            profile[i] = false;
        }

        // check items
        for (FrameItem item : items) {
            if (item.doesAffectTraversability()) {
                Area area = item.getArea();
                int fillStart = -1;
                int fillEnd = -1;

                switch (side) {
                case UP:
                    if (area.getY() <= 0) {
                        fillStart = area.getX();
                        fillEnd = area.getX() + area.getWidth();
                    }
                    break;

                case RIGHT:
                    if (area.getX() + area.getWidth() >= FRAME_WIDTH) {
                        fillStart = area.getY();
                        fillEnd = area.getY() + area.getHeight();
                    }

                    break;

                case DOWN:
                    if (area.getY() + area.getHeight() >= FRAME_HEIGHT) {
                        fillStart = area.getX();
                        fillEnd = area.getX() + area.getWidth();
                    }

                    break;

                case LEFT:
                    if (area.getX() <= 0) {
                        fillStart = area.getY();
                        fillEnd = area.getY() + area.getHeight();
                    }

                    break;

                default:
                    break;
                }

                // normalize bounds
                fillStart = Math.min(Math.max(fillStart, 0), profile.length);
                fillEnd = Math.min(Math.max(fillEnd, 0), profile.length);

                // fill
                for (int index = fillStart; index < fillEnd; index++) {
                    profile[index] = true;
                }
            }
        }

        return profile;
    }
    
    private Area transformAreaToNeighbourAreaByDirection(Area area, DIRECTION direction) {
        Area neighbourArea = area.clone();
        
        switch (direction) {
        case UP:
            neighbourArea.setY(FRAME_HEIGHT - 1);
            break;

        case RIGHT:
            neighbourArea.setX(0);
            break;

        case DOWN:
            neighbourArea.setY(0);
            break;

        case LEFT:
            neighbourArea.setX(FRAME_WIDTH - 1);
            break;

        default:
            break;
        }
        
        return neighbourArea;
    }

    public Iterator<FrameItem> itemIterator() {
        return items.iterator();
    }
}