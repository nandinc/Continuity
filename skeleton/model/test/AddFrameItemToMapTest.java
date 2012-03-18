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
		Frame f = new Frame(map);
		SkeletonLogger.register(f, "f");
		
		// Hozzáadjuk a FrameItemet a Maphoz.
		map.addItem(fi);
		
		// Feltételes elágazás, melynek eldöntése a
		// felhasználó dolga. A kérdést a Logger
		// osztály közvetíti.
		if(SkeletonLogger.askYesOrNo("IsFrameAtArea")) {
			// Ha van a lépni kívánt helyen Frame, akkor
			// hozzáadjuk a frame-hez az elemet.
			f.addItem(fi);
		} else {
			// Ha nincs a lépni kívánt helyen Frame, akkor
			// létrehozunk egyet, regisztráljuk a Loggerbe
			// majd regisztráljuk a művelethívást és
			// hozzáadjuk az elemet az új Frame-hez.
			Frame f2 = new Frame(map);
			SkeletonLogger.create(f2, "f2");
			
			f2.addItem(fi);
		}
		
		// Létrehozunk egy új terület elemet, melyet
		// regisztrálunk a Loggerben, majd beállítjuk
		// a FrameItem-hez.
		Area na = new Area();
		SkeletonLogger.create(na, "na");
		
		fi.setArea(na);
	}

}
