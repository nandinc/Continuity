package model;

/**
 * A Stickman és ezzel a játék mozgásterét korlátozó elem. A framen belül bejárható rész keretezésére szolgál.
 * 
 * @responsibility Olyan elem, mellyel nem tud a Stickman egy helyen tartózkodni, azaz korlátozza a Stickman mozgásterét.
 */
public class Platform extends AbstractFrameItem {

    /**
     * Megadja, hogy az elem szilárd-e vagy sem.
     * Ez a kereten belüli mozgások esetén az
     * ütközések ellenőrzésekor használatos.
     * 
     * A Platform mindig szilárd.
     * @return true
     */
    @Override
    public boolean isSolid() {
        // Don't let them just raid over me
        return true;
    }

    /**
     * Megadja, hogy számba kell-e venni az elemet,
     * ha a keretek közötti átjárást vizsgáljuk.
     * 
     * @return true, a Platformot mindig számba kell venni
     */
    @Override
    public boolean doesAffectTraversability() {
        return true;
    }
}