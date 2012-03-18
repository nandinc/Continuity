package model;

import utils.SkeletonLogger;

/**
 * A pályák befejezésére szolgáló ajtót reprezentálja.
 * Ha a Stickman az összes kulcs birtokában megérinti,
 * a pálya teljesítésre kerül.
 * 
 * @responsibility Ajtó objektum, melyet ha megérint a Stickman arról esemény bocsát ki.
 * @file Door osztály
 */
public class Door extends AbstractFrameItem {

	public Door() {
		area = new Area();
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void collision(FrameItem colliding) {
		// TODO Auto-generated method stub
		
	}

}