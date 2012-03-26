package model;

import java.util.TimerTask;

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

    private java.util.Timer timer;

    public void setPubSub(PubSub pubSub) {
        this.pubSub = pubSub;
    }

    public void start() {
        // true: runs as a daemon
        // @see: http://docs.oracle.com/javase/1.4.2/docs/api/java/util/Timer.html#Timer(boolean)
        // timer couldn't be initialized in the constructor, because it's not restartable, see docs.
        timer = new java.util.Timer(true);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                pubSub.emit("tick", null);
            }
        }, 100, 100);
    }

    public void stop() {
        timer.cancel();
    }

}