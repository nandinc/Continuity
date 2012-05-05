package model;

import java.util.TimerTask;

import debug.Logger;

/**
 * Az idő múlását nyilvántartó objektum. 
 * Elsősorban Stickman esését szabályozhatjuk vele, de a
 * periodikus időközönként kiadott 'tick' események
 * a stickmentől vagy bármely más objektumtól függetlenek. 
 * 
 * @responsibility Időzítésért felelős osztály, bizonyos időközönként kibocsát egy 'tick' eseményt az átadott PubSub objektumra.
 */
public class Timer {

    /**
     * Az eseménykezelő csatorna,
     * melyen jelzi az idő múlását.
     */
    private PubSub pubSub;

    /**
     * Az idő múlásának követését ténylegesen megvalósító osztály
     */
    private java.util.Timer timer;

    /**
     * Kommunikációs csatorna beállítása
     * @param pubSub a használni kíván kommunikációs csatorna
     */
    public void setPubSub(PubSub pubSub) {
        this.pubSub = pubSub;
    }

    /**
     * Időmúlás nyivlántartásának indítása
     */
    public void start() {
        // true: runs as a daemon
        // @see: http://docs.oracle.com/javase/1.4.2/docs/api/java/util/Timer.html#Timer(boolean)
        // timer couldn't be initialized in the constructor, because it's not restartable, see docs.
        timer = new java.util.Timer(true);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                pubSub.emit("tick", null);
                // TODO remove this after prototype release
                Logger.flush();
            }
        }, 80, 80);
    }

    /**
     * Időmúlás nyivlántartásának leállítása
     */
    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }

}