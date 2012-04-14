package controller.console;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.console.exception.InvalidArgumentException;
import model.DIRECTION;
import model.PubSub;

/**
 * Controller parancsok ősosztálya
 * 
 * @responsibility Parancsok közös szükségleteinek összefogása
 */
public abstract class Command {
    /**
     * Kommunikációs csatorna
     */
    private PubSub pubSub;
    
    /**
     * Beállítja a kommunikációs csatornát
     * @param pubSub
     */
    public void setPubSub(PubSub pubSub) {
        this.pubSub = pubSub;
    }
    
    /**
     * Visszaadja a beállított kommunikációs csatornát
     * @return a kommunikációs csatorna
     */
    protected PubSub getPubSub() {
        return pubSub;
    }
    
    /**
     * Végrehajtja a parancsot a megatott paraméterekkel
     * @param args Parancs argumentumai
     * @throws InvalidArgumentException
     */
    public abstract void execute(String[] args) throws InvalidArgumentException;
    
    /**
     * Beolvas egy számot az átadott tömb megadott pozíciójából
     * @param args
     * @param index
     * @return
     * @throws InvalidArgumentException
     */
    protected int readInteger(String[] args, Integer index) throws InvalidArgumentException {
        if (index > args.length) {
            throw new InvalidArgumentException("Argument #" + index.toString() + ". must be an integer");
        }
        
        String integerString = args[index];
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(integerString);
        boolean found = m.find();

        if (found) {
            return Integer.parseInt(m.group());
        } else {
            throw new InvalidArgumentException("Argument #" + index.toString() + ". must be an integer");
        }
    }
    
    /**
     * Beolvas egy irányt az átadott tömb megadott pozíciójából
     * @param args
     * @param index
     * @return Beolvasott irány
     */
    protected DIRECTION readDirection(String[] args, Integer index) throws InvalidArgumentException {
        if (index > args.length) {
            throw new InvalidArgumentException("Argument #" + index.toString() + ". must be a direction (up/down/left/right)");
        }
        
        String directionString = args[index];
        if (directionString.compareTo("up") == 0) {
            return DIRECTION.UP;
        } else if (directionString.compareTo("right") == 0) {
            return DIRECTION.RIGHT;
        } else if (directionString.compareTo("down") == 0) {
            return DIRECTION.DOWN;
        } else if (directionString.compareTo("left") == 0) {
            return DIRECTION.LEFT;
        } else {
            throw new InvalidArgumentException("Argument #" + index.toString() + ". must be a direction (up/down/left/right)");
        }
    }
}
