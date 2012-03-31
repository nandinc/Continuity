package ui.console;

import java.io.IOException;
import java.util.Iterator;

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

public class FrontView {

    private Map map;

    private int printWidth;
    private int printHeight;
    
    private char[][] canvas;
    private boolean[][] checked;

    public FrontView(Game game) {
        this.map = game.getMap();
        initPrintDimensions();

        PubSub pubSub = game.getPubSub();
        pubSub.on("invalidate", new Subscriber() {

            @Override
            public void eventEmitted(String eventName, Object eventParameter) {
                repaint();
            }
        });
        pubSub.on("makemap", new Subscriber() {
			
			@Override
			public void eventEmitted(String eventName, Object eventParameter) {
				createMap();
			}
		});
    }

    private void initPrintDimensions() {
        // +2 because of the borders
        printWidth = map.horizontalFrameCount() * (Frame.FRAME_WIDTH + 2);
        printHeight = map.verticalFrameCount() * (Frame.FRAME_HEIGHT + 2);
    }

    private void repaint() {
        clearConsole();

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

        // print canvas
        for (int row = 0; row < printHeight; row++) {
            for (int col = 0; col < printWidth; col++) {
                System.out.print(canvas[row][col]);
            }
            System.out.println("");
        }
        
        System.out.println("");
        this.canvas = canvas;
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

    private void clearConsole() {
        if (System.console() != null) {
            // Attention, hack around
            try {
                Runtime.getRuntime().exec("cls");
            } catch (IOException e) {
                try {
                    Runtime.getRuntime().exec("clear");
                } catch (IOException e1) {
                    // no way to clear, do nothing
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
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
    
    // Create the map
    private void createMap() {
    	if (canvas != null) {
    		checked = new boolean[canvas.length][canvas[0].length];
    		int vb = 0, hb = 0;
	    	for (int row = 0; row < printHeight-1; row++) {
	    		hb = 0;
	            for (int col = 0; col < printWidth-1; col++) {
	            	if (!checked[row][col]) {
		            	char c = canvas[row][col];
		            	int realCol = col - hb;
		            	int realRow = row - vb;
		            	
		            	if (c == '|') {
		            		hb++;
		            	}else if (c == '-') {
		            		vb++;
		            		row++;
		            	} else {
			            	if (c == 'X') {
			            		System.out.println("Stickman\t" + realCol + "\t" + realRow + "\t"
			            				+ getRange(c, row, col));
			            	}
			            	
			            	else if (c == '#') {
			            		System.out.println("Platform\t" + realCol + "\t" + realRow + "\t"
			            				+ getRange(c, row, col));
			            	}
			            	
			            	else if (c == 'K') {
			            		System.out.println("Key\t" + realCol + "\t" + realRow + "\t"
			            				+ getRange(c, row, col));
			            	}
			            	
			            	else if (c == 'A') {
			            		System.out.println("Door\t" + realCol + "\t" + realRow + "\t"
			            				+ getRange(c, row, col));
			            	}
			            	
			            	else if (c == '?') {
			            		System.out.println("Unknown\t" + realCol + "\t" + realRow + "\t"
			            				+ getRange(c, row, col));
			            	}
		            	}
	            	}
	            }
	        }
    	}
    }
    
    // Check horizontal and vertical range
    // TODO More comment
    private String getRange(char c, int row, int col) {
    	int tempWidth = 0;
    	int tempHeight = 0;
    	int minlength = Frame.FRAME_WIDTH;
    	boolean row_continous = true;
    	boolean col_continous;
    	
    	for (int i = 0; i < Frame.FRAME_HEIGHT; i++) {
    		int temp = 0;
    		if (canvas[row+i][col] != c) {
    			break;
    		}
    		for (int j = 0; j < Frame.FRAME_WIDTH; j++) {
    			if (canvas[row+i][col+j] == c) {
    				temp++;
    			} else {
    				break;
    			}
    		}
    		if (minlength > temp) {
    			minlength = temp;
    		}
    		tempHeight++;
    	}
    	tempWidth = minlength;
    	
		for (int i = row; i < row+tempHeight && row_continous; i++) {
			col_continous = true;
	    	for (int j = col; j < col+minlength && col_continous; j++) {
	    		if (canvas[i][j] == c) {
	    			checked[i][j] = true;
	    		} else {
	    			if (row != i) {
	    				row_continous = false;
	    			}
	    			col_continous = false;
	    		}
	    	}
		}
		
    	return tempWidth + "\t" + tempHeight;
    }
}
