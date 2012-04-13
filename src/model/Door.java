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
    /**
     * Megadja, hogy az elem szilárd-e vagy sem.
     * Ez a kereten belüli mozgások esetén az
     * ütközések ellenőrzésekor használatos.
     * @return true, ha szilárd, false egyébként
     */
    @Override
    public boolean isSolid() {
        return false;
    }
    
    /**
     * A tartalmazó keret jelezheti ezen a metóduson keresztül,
     * hogy egy másik elem, melyet paraméterül ad,
     * hozzáért (collision) ehhez az elemhez.
     * 
     * A megérintésének tényét jelzi a kommunikációs csatornán,
     * hogy -- amennyiben minden kulcs össze van gyűjtve --
     * lehetővé váljon a pálya teljesítése.
     * 
     * @param colliding
     */
    @Override
    public void collision(FrameItem colliding) {
        pubSub.emit("door:touched", null);
        Logger.logStatus("Door touched");
    }

}