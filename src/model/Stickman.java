package model;

/**
 * A játékos által irányított figurát reprezentáló elem.
 * 
 * @responsibility A játékos által irányított figura, mely a pályán mozog.
 */
public class Stickman extends AbstractFrameItem {

	private Frame checkpointFrame = null;
	private Area checkpointArea = null;
    /**
     * A figura mozgatása a megadott irányba.
     * @param direction
     */
    public void move(DIRECTION direction) {
    	Area newArea = getNewAreaByDirection(direction);
    	frame.requestArea(this, newArea);
    }

    /**
     * A figura igényelt elmozdulása után elfoglalandó
     * terület kiszámítása.
     * @param direction
     */
    private Area getNewAreaByDirection(DIRECTION direction) {
        Area newArea = area.clone();
        
        // change the new area by direction
        // please note: the origin of the coordinate system
        // is in the top left corner.
        switch (direction) {
		case UP:
			// TODO implement jump properly
			newArea.setY(
				newArea.getY() - 1
			);
			break;
			
		case RIGHT:
			newArea.setX(
				newArea.getX() + 1
			);
			break;
			
		case DOWN:
			newArea.setY(
				newArea.getY() + 1
			);
			break;
			
		case LEFT:
			newArea.setX(
				newArea.getX() - 1
			);
			break;

		default:
			break;
		}
        
        return newArea;
    }

    /**
     * A figura pozíciójának visszaállítása az
     * utolsó ellenőrzőpontra.
     */
    public void resetToCheckpoint() {
        area = checkpointArea;
        checkpointFrame.addItem(this);
    }
    
    @Override
    public void setFrame(Frame frame) {
    	super.setFrame(frame);
    	
    	// set initial checkpoint
    	// if not yet set
    	if (checkpointFrame == null) {
    		checkpointFrame = frame;
    	}
    	
    	if (checkpointArea == null) {
    		// area is the current area
    		checkpointArea = area;
    	}
    }
    
    @Override
    public void setPubSub(PubSub pubSub) {
    	this.pubSub = pubSub;
    	
    	pubSub.on("tick", new Subscriber() {
			
			@Override
			public void eventEmitted(String eventName, Object eventParameter) {
				move(DIRECTION.DOWN);
			}
		});
    	
    	// TODO subscribe to key picked up event to collect key
    	// and save checkpoint. Take care about events emitted by
    	// another stickman
    }

	@Override
	public boolean isSolid() {
		// By setting solid flag true,
		// it opens a new perspective to cooperative
		// strategies like jumping from the top of
		// the other player.
		return true;
	}
}
