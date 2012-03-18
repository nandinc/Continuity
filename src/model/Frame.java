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
	
	/**
	 * Frame létrehozása az őt tartalmazó pályával.
	 * @param m Tartalmazó pálya
	 */
	public Frame(Map m) {
		map = m;
	}
	
    /**
     * Hozzáadja a megadott elemet a kerethez.
     * 
     * @param item
     */
    public void addItem(FrameItem item) {
    	// Metódushívás rögzítése.
    	SkeletonLogger.call(this, "addItem", item);
    	
    	// A frame regisztrálása az itemhez.
    	item.setFrame(this);
    	
    	// Függvény vége, visszatérés logolása.
    	SkeletonLogger.back();
    }

    /**
     * Eltávolítja a megadott elemet a keretből
     * 
     * @param item
     */
    public void removeItem(FrameItem item) {
    	// Metódushívás rögzítése.
    	SkeletonLogger.call(this, "removeItem", item);
    	// Függvény vége, visszatérés logolása.
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
     * @param direction Irány, ha új keretbe lépne.
     */
    public boolean requestArea(Stickman s, Area area, DIRECTION direction) {
    	// Metódushívás rögzítése.
    	SkeletonLogger.call(this, "requestArea", s, area);
    	
    	// Bekérjük a tesztelőtől, hogy van-e terület, amerre lépni akarunk.
    	// Ha nem, akkor pályahatárként kezeljük tovább.
    	if(!SkeletonLogger.askYesOrNo("areaInBound")) {
    		// A pálya határnál új Frame lesz a szomszédos frame, amelyet a
    		// map-tól tudunk lekérdezni.
    		Frame newFrame = map.getNeighbour(this, direction);
    		// Regisztrálás a logger osztályba.
    		SkeletonLogger.register(newFrame, "newFrame");
    		
    		// Ha van szomszédos frame.
    		if (newFrame != null) {
    			newFrame.requestArea(s, area, direction);
    			// Elem eltávolítása a jelenlegi frameből
    			removeItem(s);
    			// Elem áthelyezése a szomszédos framebe
    			newFrame.addItem(s);
    			
    			boolean _true = true;
    			// Regisztrálás a logger osztályba.
    			SkeletonLogger.register(_true, "true");
    			// Függvény vége, visszatérés logolása.
    			SkeletonLogger.back(_true);
    			return true;
    		// Ha nincs szomszédos frame.
    		} else {
    			// Külön kezeljük, ha lefele lépne, mert az halállal ér véget.
    			if (direction == DIRECTION.DOWN) {
    				s.resetToCheckpoint();
    			}
    			boolean _false = false;
    			// Regisztrálás a logger osztályba.
    			SkeletonLogger.register(_false, "false");
    			// Függvény vége, visszatérés logolása.
    			SkeletonLogger.back(_false);
    			return false;
    		}
    	}
    	
    	// Ütközés lekérdezése.
    	boolean collision = checkCollision(area);
    	// Regisztrálás a logger osztályba.
    	SkeletonLogger.register(!collision, "!collision");
    	
    	// Ha nincs ütközés, akkor áthelyezzük a stickmant.
    	if(!collision) {
    		s.setArea(area);
    	// Skeletonhoz van a következő rész, COllision Notification:
    	} else {
    		// Elemek inicializálása
    		SkeletonLogger.mute();
	    		FrameItem fa = new Key();
				FrameItem fb = new Key();
				SkeletonLogger.register(fa, "fa");
				SkeletonLogger.register(fb, "fb");
			SkeletonLogger.unMute();
			
			// Lekérdezzük az egyes itemek helyét és a helyekre(area)
			// megnézzük, hogy ütközésben van-e a stickman új helyével.
			Area aa = fa.getArea();
			SkeletonLogger.register(aa, "aa");
			
			boolean _collision = aa.hasCollision(area);
			SkeletonLogger.register(_collision, "collision");
			
			if(_collision) {
				fa.collision(s);
			}
			
			Area ab = fb.getArea();
			SkeletonLogger.register(ab, "aa");
			
			_collision = ab.hasCollision(area);
			SkeletonLogger.register(_collision, "collision");
			
			if(_collision) {
				fb.collision(s);
			}
    	}

    	// Függvény vége, visszatérés logolása.
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
    	// Metódushívás rögzítése.
    	SkeletonLogger.call(this, "checkCollision", area);
    	
    	// Megkérdezzük a tesztelőtől, hogy lesz-e ütközés.
    	boolean collision = SkeletonLogger.askYesOrNo("IsCollision");
    	// Regisztrálás a logger osztályba.
    	SkeletonLogger.register(collision, "collision");
    	
    	// Függvény vége, visszatérés logolása.
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
    	// Metódushívás rögzítése.
    	SkeletonLogger.call(this, "isTraversable", frame, d);
    	
    	// Megkérdezzük a tesztelőt, hogy átjárható-e a frame a paraméterben
    	// kapott másik frame-ből.
    	boolean isTraversable = SkeletonLogger.askYesOrNo("IsTraversable");
    	// Regisztrálás a logger osztályba.
    	SkeletonLogger.register(isTraversable, "isTraversable");
    	
    	// Függvény vége, visszatérés logolása.
    	SkeletonLogger.back(isTraversable);
    	return isTraversable;
    }
}