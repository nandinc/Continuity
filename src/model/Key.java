package model;

import utils.SkeletonLogger;

/**
 * A pályán található kulcsokat reprezentáló objektum.
 * 
 * @responsibility Kulcs elem, melyet megérintve a Stickman meg tud szerezni. Erről egy esemény küldésén keresztül értesíti a külvilágot.
 * @file Key osztály
 */
public class Key extends AbstractFrameItem {

	/**
	 * A kulcs összegyűjtöttségének állapotát tárolja.
	 */
    private boolean collected;

    public Key() {
    	this.area = new Area();
    }
    
    /**
     * Megadja, hogy megszerezték-e a kulcsot.
     * @return Igaz, ha megszerezték-e a kulcsot.
     */
    public boolean isCollected() {
        return this.collected;
    }

	@Override
	public Area getArea() {
		// Metódushívás rögzítése.
		SkeletonLogger.call(this, "getArea");
		
		// Teszteléshez, ha nicns terület beállítva, új terület beállítása.
		if (area == null) {
			area = new Area();
			// Regisztrálás a logger osztályba.
			SkeletonLogger.register(area, "area");
		}
		
		// Függvény vége, visszatérés logolása.
		SkeletonLogger.back(area);
		return area;
	}

	@Override
	public void setArea(Area area) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFrame(Frame frame) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSolid() {
		// Metódushívás rögzítése.
		SkeletonLogger.call(this, "isSolid");
		
		boolean _isSolid = false;
		SkeletonLogger.register(_isSolid, "true");
		
		// Függvény vége, visszatérés logolása.
		SkeletonLogger.back(_isSolid);
		return _isSolid;
	}

	@Override
	public void collision(FrameItem colliding) {
		// Metódushívás rögzítése.
		SkeletonLogger.call(this, "collision", colliding);
		
		// Teszteléshez kulcs felvételének eljátszása.
		String notif = "keyPickedUp";
		SkeletonLogger.register(notif, "'KeyPickedUp'");
		if(ps == null) {
			ps = new PubSub(new Game());
		}
		ps.emit(notif, this);
		
		// Függvény vége, visszatérés logolása.
		SkeletonLogger.back();
	}

}
