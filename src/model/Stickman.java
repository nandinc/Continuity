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
    	// Metódushívás rögzítése.
    	SkeletonLogger.call(this, "move", direction);

    	// Új terület lekérése lépés irányába.
    	getNewAreaByDirection(direction);
    	
    	// Függvény vége, visszatérés logolása.
    	SkeletonLogger.back();
    }

    /**
     * A figura igényelt elmozdulása után elfoglalandó
     * terület kiszámítása.
     * @param direction
     */
    private Area getNewAreaByDirection(DIRECTION direction) {
    	// Metódushívás rögzítése.
    	SkeletonLogger.call(this, "getNewAreaByDirection", direction);
    	
    	// Teszteléshez új terület létrehozása.
    	Area na = new Area();
    	// Regisztrálás a logger osztályba.
    	SkeletonLogger.create(na, "na");
    	
    	// Kérés indítása a lépéshez.
    	frame.requestArea(this, na, direction);
    	
    	// Függvény vége, visszatérés logolása.
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
		// Metódushívás rögzítése.
		SkeletonLogger.call(this, "getArea");
		
		// Teszteléshez, ha nincs még terület újat készít.
		if(area == null) area = new Area();
		// Regisztrálás a logger osztályba.
		SkeletonLogger.register(area, "a");
		
		// Függvény vége, visszatérés logolása.
		SkeletonLogger.back(area);
		return area;
	}

	@Override
	public void setArea(Area area) {
		// Metódushívás rögzítése.
		SkeletonLogger.call(this, "setArea", area);
		
		// Terület beállítása.
		this.area = area;
		
		// Függvény vége, visszatérés logolása.
		SkeletonLogger.back();
	}

	@Override
	public void setFrame(Frame frame) {
		// Metódushívás rögzítése.
		SkeletonLogger.call(this, "setFrame", frame);
		
		// Frame beállítása
		this.frame = frame;
		
		// Függvény vége, visszatérés logolása.
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
