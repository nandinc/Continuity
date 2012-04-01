package model;

import debug.Logger;

/**
 * A pályák befejezésére szolgáló ajtót reprezentálja.
 * Ha a Stickman az összes kulcs birtokában megérinti,
 * a pálya teljesítésre kerül.
 * 
 * @responsibility Ajtó objektum, melyet ha megérint a Stickman arról esemény bocsát ki.
 */
public class Door extends AbstractFrameItem {

    @Override
    public boolean isSolid() {
        return false;
    }
    
    @Override
    public void collision(FrameItem colliding) {
        // TODO Door.collision: add business logic
        Logger.logStatus("Door touched");
    }

}