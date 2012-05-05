package controller.graphical;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import controller.console.exception.InvalidArgumentException;

import debug.Logger;

import model.DIRECTION;
import model.Game;
import model.PubSub;
import model.VIEWPORT_STATE;

/**
 * A felhasználó parancsait kezelő felület.
 * @responsibility Felhasználói parancsok fogadása, értelemezése, továbbítása.
 */
public class FrontController {
	/**
	 * Kommunikációs csatorna a Game felé
	 */
	private Game game;
	
	private Set<Integer> pressedKeysNew = new HashSet<Integer>();
	private Set<Integer> pressedKeysOld = new HashSet<Integer>();
	
	/**
     * Inicializálás
     * @param pubSub kommunikációs csatorna
     */
    public FrontController(Game game) {
    	this.game = game;
    }
    
    public void start() {
    	// Initialize keyboard listener
    	KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    	manager.addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
			    int id = e.getID();
			    int keyCode = e.getKeyCode();
			    
			    synchronized (pressedKeysNew) {
			    	if (id == KeyEvent.KEY_PRESSED) {
				        pressedKeysNew.add(keyCode);
				    } else if (id == KeyEvent.KEY_RELEASED) {
				    	pressedKeysNew.remove(keyCode);
				    	pressedKeysOld.remove(keyCode);
				    }
				}
			    
			    if (id == KeyEvent.KEY_PRESSED) {
			    	processKeystroke(keyCode, true);
			    }
			    
				return true;
			}
		});
    	
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	synchronized (pressedKeysNew) {
            		for (int key : pressedKeysOld) {
                		processKeystroke(key, false);
                	}
                	
                	pressedKeysOld.addAll(pressedKeysNew);
            		pressedKeysNew.clear();
				}
            }
        }, 0, 40);
    	
    	// Load a map
    	this.game.getPubSub().emit("controller:loadMap", 7);
    }
    
    protected void processKeystroke(int key, boolean immediate) {
    	// Proccess stickman moves
		if (game.getViewportState() == VIEWPORT_STATE.CLOSE) {
			if (key == KeyEvent.VK_UP) {
				this.moveStickman(1, DIRECTION.UP);
			} else if (key == KeyEvent.VK_RIGHT) {
				this.moveStickman(1, DIRECTION.RIGHT);
			} else if (key == KeyEvent.VK_DOWN) {
				this.moveStickman(1, DIRECTION.DOWN);
			} else if (key == KeyEvent.VK_LEFT) {
				this.moveStickman(1, DIRECTION.LEFT);
			}
		}
		
		// One time things
		if (immediate) {
			// Move frame
			if (game.getViewportState() == VIEWPORT_STATE.MAP) {
				if (key == KeyEvent.VK_UP) {
					this.moveFrame(DIRECTION.UP);
				} else if (key == KeyEvent.VK_RIGHT) {
					this.moveFrame(DIRECTION.RIGHT);
				} else if (key == KeyEvent.VK_DOWN) {
					this.moveFrame(DIRECTION.DOWN);
				} else if (key == KeyEvent.VK_LEFT) {
					this.moveFrame(DIRECTION.LEFT);
				}
			}
			
			if (key == KeyEvent.VK_SPACE) {
				this.toggleViewport();
			}
		}
    }
    
    protected void moveStickman(int stickmanId, DIRECTION dir) {
    	this.game.getPubSub().emit("controller:move:" + stickmanId, dir);
    }
    
    protected void moveFrame(DIRECTION dir) {
    	this.game.getPubSub().emit("controller:moveFrame", dir);
    }
    
    protected void toggleViewport() {
    	this.game.getPubSub().emit("controller:viewportSwitch", null);
    }
}
