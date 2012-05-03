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
import java.util.Map;

import controller.console.exception.InvalidArgumentException;

import debug.Logger;

import model.DIRECTION;
import model.PubSub;

/**
 * A felhasználó parancsait kezelő felület.
 * @responsibility Felhasználói parancsok fogadása, értelemezése, továbbítása.
 */
public class FrontController {
	/**
	 * Kommunikációs csatorna a Game felé
	 */
	private PubSub pubSub;
	
	private java.util.Map<Integer,Object[]> keyListeners = new java.util.HashMap<Integer,Object[]>();
	
	/**
     * Inicializálás
     * @param pubSub kommunikációs csatorna
     */
    public FrontController(PubSub pubSub) {
    	this.pubSub = pubSub;
    }
    
    public void start() {
    	// Listen to keystrokes
    	this.keyListeners.put(KeyEvent.VK_LEFT, new Object[] {"controller:move:1", DIRECTION.LEFT});
    	this.keyListeners.put(KeyEvent.VK_RIGHT, new Object[] {"controller:move:1", DIRECTION.RIGHT});
    	this.keyListeners.put(KeyEvent.VK_UP, new Object[] {"controller:move:1", DIRECTION.UP});
    	this.keyListeners.put(KeyEvent.VK_DOWN, new Object[] {"controller:move:1", DIRECTION.DOWN});
    	
    	this.keyListeners.put(KeyEvent.VK_SPACE, new Object[] {"controller:viewportSwitch", null});
    	
    	
    	KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    	manager.addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
			    int id = e.getID();
			    if (id != KeyEvent.KEY_PRESSED) {
			        return false;
			    }
			    
				System.out.println("Got key: " + e.getKeyChar());
				if (keyListeners.containsKey(e.getKeyCode())) {
					Object[] message = keyListeners.get(e.getKeyCode());
					pubSub.emit((String) message[0], message[1]);
				}
				return true;
			}
		});
    	
    	// Load a map
    	this.pubSub.emit("controller:loadMap", 7);
    }
}
