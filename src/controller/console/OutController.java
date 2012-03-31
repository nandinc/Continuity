package controller.console;

import java.util.Iterator;

import model.Area;
import model.Door;
import model.Frame;
import model.FrameItem;
import model.Game;
import model.Key;
import model.Map;
import model.Platform;
import model.Stickman;
import model.Map.FrameIterator;

public class OutController {
    private Map map;

    private int printWidth;
    private int printHeight;
    
	public OutController(Game game) {

        this.map = game.getMap();

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
	    private void drawItemToCanvas(FrameItem item, Area offset, char[][] canvas) {
	        // top left corner
	        int cornerY = offset.getY() + item.getArea().getY();
	        int cornerX = offset.getX() + item.getArea().getX();

	        if (item instanceof Door) {
	            canvas[cornerY][cornerX] = 'A';
	        } else if (item instanceof Key) {
	            // TODO check key state, don't display if already picked up
	            canvas[cornerY][cornerX] = 'K';
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
