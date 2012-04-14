package ui.console;

import java.util.Iterator;

import debug.Logger;

import model.Area;
import model.Door;
import model.Frame;
import model.FrameItem;
import model.Game;
import model.Key;
import model.Map;
import model.Platform;
import model.Map.FrameIterator;
import model.PubSub;
import model.Stickman;
import model.Subscriber;

/**
 * Egy játékba betöltött pálya aktuális állapotát jeleníti meg a sztandard kimeneten.
 * @responsibility A felügyelt játékhoz tartozó pálya szöveges reprezentációjának előállítása és megjelenítése. 
 */
public class FrontView {

    /**
     * Felügyelt pálya
     */
    private Game game;

    /**
     * Kimenet szélessége
     */
    private int printWidth;
    
    /**
     * Kimenet magassága
     */
    private int printHeight;

    /**
     * Inicializálás, feliratkozás a felügyelt pálya változásaira.
     * 
     * @param game a felügyelt pálya
     */
    public FrontView(Game game) {
        this.game = game;

        PubSub pubSub = game.getPubSub();
        pubSub.on("invalidate", new Subscriber() {

            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                repaint();
            }
        });
    }
    
    /**
     * A szöveges reprezentáció előállítása és megjelenítése
     */
    private void repaint() {
        Map map = game.getMap();
        
        printWidth = map.horizontalFrameCount() * (Frame.FRAME_WIDTH + 2);
        printHeight = map.verticalFrameCount() * (Frame.FRAME_HEIGHT + 2);

        char[][] canvas = new char[printHeight][printWidth];
        
        // init canvas - prevent print stop
        for (int row = 0; row < printHeight; row++) {
            for (int col = 0; col < printWidth; col++) {
                canvas[row][col] = ' ';
            }
        }

        // draw content on canvas
        FrameIterator frameIterator = map.frameIterator();
        while (frameIterator.hasNext()) {
            Frame frame = frameIterator.next();
            
            if (frame != null) {    // check for the empty frame
                Area framePosition = frameIterator.getFramePosition();
                Area itemOffset = itemOffsetByFramePosition(framePosition);
                
                Iterator<FrameItem> itemIterator = frame.itemIterator();
                
                while (itemIterator.hasNext()) {
                    FrameItem item = itemIterator.next();
                    //System.out.println(item.getClass());
                    drawItemToCanvas(item, itemOffset, canvas);
                }
                
                drawFrameToCanvas(framePosition, canvas);
            }
        }

        // print canvas
        for (int row = 0; row < printHeight; row++) {
            for (int col = 0; col < printWidth; col++) {
                //System.out.print(canvas[row][col]);
                Logger.logMap(new Character(canvas[row][col]).toString());
            }
            //System.out.println("");
            Logger.logMap("\n");
        }
        
        //System.out.println("");
        Logger.logMap("\n");
    }
    
    /**
     * Elem abszolút eltolásának megállapítása,
     * számbavéve a keretek széleit is.
     * 
     * @param framePosition keret pozíció
     * @return abszolút eltolás
     */
    private Area itemOffsetByFramePosition(Area framePosition) {
        Area itemOffset = new Area();
        
        itemOffset.setX(
            framePosition.getX() * (Frame.FRAME_WIDTH + 2) + 1
        );
        
        itemOffset.setY(
            framePosition.getY() * (Frame.FRAME_HEIGHT + 2) + 1
        );
        
        return itemOffset;
    }

    /**
     * Kapott elem kirajzolása a kapott vászonra.
     * 
     * @param item kirajzolandó elem
     * @param offset elem abszolút eltolása
     * @param canvas a felhasználandó vászon
     */
    private void drawItemToCanvas(FrameItem item, Area offset, char[][] canvas) {
        // top left corner
        int cornerY = offset.getY() + item.getArea().getY();
        int cornerX = offset.getX() + item.getArea().getX();

        if (item instanceof Door) {
            canvas[cornerY][cornerX] = 'A';
        } else if (item instanceof Key) {
            // TODO cast ahead...
            Key key = (Key)item;
            if (key.isCollected() == false) {
                canvas[cornerY][cornerX] = 'K';
            }
        } else if (item instanceof Platform) {
            for (int row = cornerY; row < cornerY + item.getArea().getHeight(); row++) {
                for (int col = cornerX; col < cornerX + item.getArea().getWidth(); col++) {
                    canvas[row][col] = '#';
                }
            }
        } else if (item instanceof Stickman) {
            canvas[cornerY][cornerX] = 'X';
        } else {
            canvas[cornerY][cornerX] = '?';
        }
    }

    /**
     * Keret rajzolása a vászonra, a megadott pozícióban
     * 
     * @param framePosition keret pozíciója
     * @param canvas a felhasználandó vászon
     */
    private void drawFrameToCanvas(Area framePosition, char[][] canvas) {
        // top left corner
        int cornerY = framePosition.getY() * (Frame.FRAME_HEIGHT + 2); 
        int cornerX = framePosition.getX() * (Frame.FRAME_WIDTH + 2);
                
        // top left
        canvas[cornerY][cornerX] = '+';
        
        // top bar
        for (int col = cornerX + 1; col < cornerX + Frame.FRAME_WIDTH + 1; col++) {
            canvas[cornerY][col] = '-';
        }
        
        // top right
        canvas[cornerY][cornerX + Frame.FRAME_WIDTH + 1] = '+';
        
        // side bars
        for (int row = cornerY + 1; row < cornerY + Frame.FRAME_HEIGHT + 1; row++) {
            // left
            canvas[row][cornerX] = '|';
            
            // right
            canvas[row][cornerX + Frame.FRAME_WIDTH + 1] = '|';
        }
        
        // bottom left
        canvas[cornerY + Frame.FRAME_HEIGHT + 1][cornerX] = '+';
        
        // bottom bar
        for (int col = cornerX + 1; col < cornerX + Frame.FRAME_WIDTH + 1; col++) {
            canvas[cornerY + Frame.FRAME_HEIGHT + 1][col] = '-';
        }
        
        // bottom right
        canvas[cornerY + Frame.FRAME_HEIGHT + 1][cornerX + Frame.FRAME_WIDTH + 1] = '+';
    }
}
