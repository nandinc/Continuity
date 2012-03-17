package model;

/**
 * A Stickman és ezzel a játék mozgásterét korlátozó elem. A framen belül bejárható rész keretezésére szolgál.
 * 
 * @responsibility Olyan elem, mellyel nem tud a Stickman egy helyen tartózkodni, azaz korlátozza a Stickman mozgásterét.
 * @file Platform osztály
 */
public class Platform extends AbstractFrameItem {

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