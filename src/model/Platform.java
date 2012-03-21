package model;

/**
 * A Stickman és ezzel a játék mozgásterét korlátozó elem. A framen belül bejárható rész keretezésére szolgál.
 * 
 * @responsibility Olyan elem, mellyel nem tud a Stickman egy helyen tartózkodni, azaz korlátozza a Stickman mozgásterét.
 */
public class Platform extends AbstractFrameItem {

	@Override
	public boolean isSolid() {
		// Don't let them just raid over me
		return true;
	}
	
	/**
     * Tells whether the item should be considered
     * while checking frame traversability or shouldn't.
     * 
     * @return true if affects
     */
	@Override
    public boolean doesAffectTraversability() {
		return true;
	}
}