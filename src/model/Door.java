package model;

/**
 * A pályák befejezésére szolgáló ajtót reprezentálja.
 * Ha a Stickman az összes kulcs birtokában megérinti,
 * a pálya teljesítésre kerül.
 * 
 * @responsibility Ajtó objektum, melyet ha megérint a Stickman arról esemény bocsát ki.
 * @file Door osztály
 */
public class Door extends AbstractFrameItem {

	@Override
	public Area getArea() {
		// TODO Auto-generated method stub
		return null;
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