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
	
	/**
	 * Tárolja az utolsó óraütés óta lenyomott -- és még fel nem engedett -- billentyűket
	 */
	private Set<Integer> pressedKeysNew = new HashSet<Integer>();
	
	/**
	 * Tárolja az utolsó óraütés előtt lenyomott -- és még fel nem engedett -- billentyűket
	 */
	private Set<Integer> pressedKeysOld = new HashSet<Integer>();
	
	/**
     * Inicializálás
     * @param pubSub kommunikációs csatorna
     */
    public FrontController(Game game) {
    	this.game = game;
    }
    
    /**
     * Elindítja a parancsokra való figyelést
     */
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
				        // Record that the key has been pressed
			    		pressedKeysNew.add(keyCode);
				    } else if (id == KeyEvent.KEY_RELEASED) {
				    	// Remove key from list of pressed keys
				    	pressedKeysNew.remove(keyCode);
				    	pressedKeysOld.remove(keyCode);
				    }
				}
			    
			    // If the key has been just pressed then react to it right away
			    // this way there's no delay
			    if (id == KeyEvent.KEY_PRESSED) {
			    	processKeystroke(keyCode, true);
			    }
			    
				return true;
			}
		});
    	
    	// Initialize timer task that will proccess keys that
    	// have been pressed but haven't been released since
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	synchronized (pressedKeysNew) {
            		// Only go through keys that haven't been reacted to since the last tick
            		for (int key : pressedKeysOld) {
                		processKeystroke(key, false);
                	}
                	
            		// Move everything from the 'new' list to the 'old' list
                	pressedKeysOld.addAll(pressedKeysNew);
            		pressedKeysNew.clear();
				}
            }
        }, 0, 40);
    	
    	// Load a map
    	this.game.getPubSub().emit("controller:loadMap", 7);
    }
    
    /**
     * Reagál egy billentyűre
     * @param key Lenyomott billentyű kódja
     * @param immediate Friss leütés (true) vagy a billentyű lenyomva van tartva már hosszabb ideje (false)
     */
    protected void processKeystroke(int key, boolean immediate) {
    	// Proccess stickman moves
		if (game.getViewportState() == VIEWPORT_STATE.CLOSE) {
			// Stickman 1
			if (key == KeyEvent.VK_UP) {
				this.moveStickman(1, DIRECTION.UP);
			} else if (key == KeyEvent.VK_RIGHT) {
				this.moveStickman(1, DIRECTION.RIGHT);
			} else if (key == KeyEvent.VK_DOWN) {
				this.moveStickman(1, DIRECTION.DOWN);
			} else if (key == KeyEvent.VK_LEFT) {
				this.moveStickman(1, DIRECTION.LEFT);
			}
			
			// Stickman 2
			if (key == KeyEvent.VK_E) {
				this.moveStickman(2, DIRECTION.UP);
			} else if (key == KeyEvent.VK_F) {
				this.moveStickman(2, DIRECTION.RIGHT);
			} else if (key == KeyEvent.VK_D) {
				this.moveStickman(2, DIRECTION.DOWN);
			} else if (key == KeyEvent.VK_S) {
				this.moveStickman(2, DIRECTION.LEFT);
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
			
			// Switch viewport
			if (key == KeyEvent.VK_SPACE) {
				this.toggleViewport();
			}
		}
    }
    
    /**
     * Mozgatja a stickmant
     * @param stickmanId Stickman azonosítója
     * @param dir Mozgatás iránya
     */
    protected void moveStickman(int stickmanId, DIRECTION dir) {
    	this.game.getPubSub().emit("controller:move:" + stickmanId, dir);
    }
    
    /**
     * Átrendezi a kereteket
     * @param dir Keret mozgatásának iránya
     */
    protected void moveFrame(DIRECTION dir) {
    	this.game.getPubSub().emit("controller:moveFrame", dir);
    }
    
    /**
     * Megváltoztatja a nézetet a jelenlegi ellenkezőjére
     */
    protected void toggleViewport() {
    	this.game.getPubSub().emit("controller:viewportSwitch", null);
    }
}
