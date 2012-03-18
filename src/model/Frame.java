package model;

import utils.SkeletonLogger;

/**
 * Egy pályán belüli keret, amelyben mozoghat a Stickman. Tartalmazza a pálya többi elemét (Door, Key, Platform).
 * 
 * @responsibility A pálya által alkotott táblázat egy cellája, amely elemeket tartalmaz. Ez felelős az elemek (Stickman) mozgatásáért kereten belül és között egyaránt. Két elem egy helyen való tartózkodásáról értesíti az elemeket (collision notify).
 * @file Frame osztály
 */
public class Frame {
	private Map map;
	
	public Frame(Map m) {
		map = m;
	}
	
    /**
     * Hozzáadja a megadott elemet a kerethez.
     * 
     * @param item
     */
    public void addItem(FrameItem item) {
    	// register method call in JTrace
    	SkeletonLogger.call(this, "addItem", item);
    	
    	// register the frame to the item
    	item.setFrame(this);
    	
    	SkeletonLogger.back();
    }

    /**
     * Eltávolítja a megadott elemet a keretből
     * 
     * @param item
     */
    public void removeItem(FrameItem item) {
    	SkeletonLogger.call(this, "removeItem", item);
    	
    	
    	
    	SkeletonLogger.back();
    }

    /**
     * A metódust hívó elem kérést intéz a kerethez,
     * hogy el szeretné foglalni a megadott területet.
     * A keret felelőssége a terület ellenőrzése, és szabad
     * terület esetén az elem pozíciójának frissítése.
     * 
     * @param s
     * @param area
     */
    public boolean requestArea(Stickman s, Area area, DIRECTION direction) {
    	SkeletonLogger.call(this, "requestArea", s, area);
    	
    	if(!SkeletonLogger.askYesOrNo("areaInBound")) {
    		Frame newFrame = map.getNeighbour(this, direction);
    		SkeletonLogger.register(newFrame, "newFrame");
    		
    		if (newFrame != null) {
    			newFrame.requestArea(s, area, direction);
    			removeItem(s);
    			newFrame.addItem(s);
    			boolean _true = true;
    			SkeletonLogger.register(_true, "true");
    			SkeletonLogger.back(_true);
    			return true;
    		} else {
    			if (direction == DIRECTION.DOWN) {
    				s.resetToCheckpoint();
    			}
    			boolean _false = false;
    			SkeletonLogger.register(_false, "false");
    			SkeletonLogger.back(_false);
    			return false;
    		}
    	}
    	
    	boolean collision = checkCollision(area);
    	SkeletonLogger.register(!collision, "!collision");
    	
    	if(!collision) {
    		s.setArea(area);
    	}

		SkeletonLogger.back(!collision);
    	return !collision;
    }

    /**
     * Ellenőrzi, hogy a megadott területen
     * található-e szilárd objektum.
     * 
     * @param area
     */
    protected boolean checkCollision(Area area) {
    	SkeletonLogger.call(this, "checkCollision", area);
    	
    	boolean collision = SkeletonLogger.askYesOrNo("IsCollision");
    	SkeletonLogger.register(collision, "collision");
    	
    	SkeletonLogger.back(collision);
    	return collision;
    }

    /**
     * Megállapítja, hogy a megkapott Keret és saját
     * maga között fennáll-e az átjárhatóság a
     * megadott irányban.
     * 
     * @param frame
     * @param d
     */
    protected boolean isTraversable(Frame frame, DIRECTION d) {
    	SkeletonLogger.call(this, "isTraversable", frame, d);
    	
    	boolean isTraversable = SkeletonLogger.askYesOrNo("IsTraversable");
    	SkeletonLogger.register(isTraversable, "isTraversable");
    	
    	SkeletonLogger.back(isTraversable);
    	return isTraversable;
    }
}