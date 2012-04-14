package model;

import debug.Logger;

/**
 * A pályán található kulcsokat reprezentáló objektum.
 * 
 * @responsibility Kulcs elem, melyet megérintve a Stickman meg tud szerezni. Erről egy esemény küldésén keresztül értesíti a külvilágot.
 */
public class Key extends AbstractFrameItem {

    /**
     * A kulcs összegyűjtöttségének állapotát tárolja.
     */
    private boolean collected;

    /**
     * Megadja, hogy megszerezték-e a kulcsot.
     * @return Igaz, ha megszerezték-e a kulcsot.
     */
    public boolean isCollected() {
        return this.collected;
    }

    /**
     * Nem szilárd objektum
     * @return false
     */
    @Override
    public boolean isSolid() {
        return false;
    }
    
    /**
     * Kommunikációs csatorna beállítása
     * 
     * Jelzi a csatornán, hogy új kulcs került a rendszerbe.
     * 
     * @param pubSub a beállítandó kommunikációs csatorna
     */
    @Override
    public void setPubSub(PubSub pubSub) {
        this.pubSub = pubSub;
        
        pubSub.emit("key:added", null);
    }
    
    /**
     * A tartalmazó keret jelezheti ezen a metóduson keresztül,
     * hogy egy másik elem, melyet paraméterül ad,
     * hozzáért (collision) ehhez az elemhez.
     * 
     * A kulcs állapota összegyűjtöttre változik, és jelzi az összegyűjtés
     * tényét a kommunikációs csatornán.
     * 
     * @param colliding
     */
    @Override
    public void collision(FrameItem colliding) {
        if (!collected) {
            Logger.logStatus("Key collected");
            pubSub.emit("key:collected", colliding);
        }
        collected = true;
    }

}
