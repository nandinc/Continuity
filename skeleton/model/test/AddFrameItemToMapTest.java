package model.test;

import utils.SkeletonLogger;
import model.Area;
import model.Frame;
import model.FrameItem;
import model.Map;
import model.Stickman;

/**
 * @file 1.2 Add FrameItem to Map
 */
public class AddFrameItemToMapTest extends AbstractTest{

	/**
	 * A teszteset nevének beállítása.
	 */
	public AddFrameItemToMapTest() {
		setName("Add FrameItem to Map");
	}
	
	@Override
	protected void runTest() {
		// Inicializálódnak a szereplő objektumok, majd
		// Beregisztrálódnak a Logger osztályba.
		Map map = new Map();
		SkeletonLogger.register(map, "Map");
		FrameItem fi = new Stickman();
		SkeletonLogger.register(fi, "fi");
		Frame f = new Frame();
		SkeletonLogger.register(f, "f");
		
		// Művelethívás regisztrálása.
		SkeletonLogger.call(map, "addItem", fi);
		// Hozzáadjuk a FrameItemet a Maphoz.
		map.addItem(fi);
		
		// Feltételes elágazás, melynek eldöntése a
		// felhasználó dolga. A kérdést a Logger
		// osztály közvetíti.
		if(SkeletonLogger.askYesOrNo("IsFrameAtArea")) {
			// Ha van a lépni kívánt helyen Frame, akkor
			// Beregisztráljuk a művelethívást a loggerbe és
			// Hozzáadjuk a frame-hez az elemet.
			SkeletonLogger.call(f, "addItem", fi);
			f.addItem(fi);
		} else {
			// Ha nincs a lépni kívánt helyen Frame, akkor
			// létrehozunk egyet, regisztráljuk a Loggerbe
			// majd regisztráljuk a művelethívást és
			// hozzáadjuk az elemet az új Frame-hez.
			Frame f2 = new Frame();
			SkeletonLogger.create(f2, "f2");
			
			SkeletonLogger.call(f2, "addItem", fi);
			f2.addItem(fi);
		}
		
		// Létrehozunk egy új terület elemet, melyet
		// regisztrálunk a Loggerben, majd beállítjuk
		// a FrameItem-hez.
		Area na = new Area();
		SkeletonLogger.create(na, "na");
		
		SkeletonLogger.call(fi, "setArea", na);
		fi.setArea(na);
	}

}
