package model;

import utils.SkeletonLogger;

/**
 * A játékos által irányított figurát reprezentáló elem.
 * 
 * @responsibility A játékos által irányított figura, mely a pályán mozog.
 * @file Stickman osztály
 */
public class Stickman extends AbstractFrameItem {

    /**
     * A figura mozgatása a megadott irányba.
     * @param direction
     */
    public void move(DIRECTION direction) {
    	SkeletonLogger.call(this, "move", direction);

    	getNewAreaByDirection(direction);
    	
    	SkeletonLogger.back();
    }

    /**
     * A figura igényelt elmozdulása után elfoglalandó
     * terület kiszámítása.
     * @param direction
     */
    private Area getNewAreaByDirection(DIRECTION direction) {
    	SkeletonLogger.call(this, "getNewAreaByDirection", direction);
    	
    	Area na = new Area();
    	SkeletonLogger.create(na, "na");
    	
    	frame.requestArea(this, na, direction);
    	
    	SkeletonLogger.back(na);
    	return na;
    }

    /**
     * A figura pozíciójának visszaállítása az
     * utolsó ellenőrzőpontra.
     */
    public void resetToCheckpoint() {
        throw new UnsupportedOperationException();
    }

	@Override
	public Area getArea() {
		SkeletonLogger.call(this, "getArea");
		area = new Area();
		SkeletonLogger.register(area, "a");
		SkeletonLogger.back(area);
		return area;
	}

	@Override
	public void setArea(Area area) {
		SkeletonLogger.call(this, "setArea", area);
		this.area = area;
		SkeletonLogger.back();
	}

	@Override
	public void setFrame(Frame frame) {
		SkeletonLogger.call(this, "setFrame", frame);
		this.frame = frame;
		SkeletonLogger.back();
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void collision(FrameItem colliding) {
		// TODO Auto-generated method stub
		
	}
}
