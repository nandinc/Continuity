package ui.graphical;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import model.*;
import model.Map.FrameIterator;

/**
 * Grafikus interfészt nyújt a programhoz.
 * @responsibility Kirajzolja a pálya aktuális állapotát, a benne lévő keretekkel, elemekkel stb. együtt, valamint megjeleníti a vezérlőket (pl. következő pályára lépés).
 */
public class FrontView {
	/**
	 * A játék modellje
	 */
	private Game game;
	
	/**
	 * Az aktuális pálya megjelenítésére szolgáló terület
	 */
	private JPanel canvas;
	
	/**
	 * A különböző típusú FrameItemekhez tartozó rajzoló osztályok tárolója
	 */
	private java.util.Map<Class<? extends FrameItem>, ItemDrawer> drawers = new java.util.HashMap<Class<? extends FrameItem>, ItemDrawer>();
	
	/**
	 * Keret rajzoló osztály
	 */
	private FrameDrawer frameDrawer = new FrameDrawer();
	
	/**
	 * Inicializálás
	 * @param game
	 */
	public FrontView(Game game) {
		this.game = game;
		
		// Create drawers
		this.drawers.put(Door.class, new DoorDrawer());
		this.drawers.put(Key.class, new KeyDrawer());
		this.drawers.put(Platform.class, new PlatformDrawer());
		this.drawers.put(Stickman.class, new StickmanDrawer());
		
		this.initGUI();
		
		// Subscribe to invalidate events
		this.game.getPubSub().on("view:invalidate", new Subscriber() {
			@Override
			public void eventEmitted(String eventName, Object eventParameter) {
				invalidate();
			}
		});
	}
	
	/**
	 * Kezdeményezi a pálya újrarajzolását.
	 */
	private void invalidate() {
		this.canvas.repaint();
	}
	
	/**
	 * Gondoskodik a keretek és keret elemek canvasra történő kirajzolásáról.
	 * @param g Objektum, amire rajzolni lehet
	 */
	private void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Map map = this.game.getMap();
		
		if (map == null) {
			return;
		}
		
        FrameIterator frameIterator = map.frameIterator();
        while (frameIterator.hasNext()) {
        	// Get the frame
            Frame frame = frameIterator.next();
            
            if (frame != null) {    // check for the empty frame
            	Area framePos = frameIterator.getFramePosition();
            	
            	// Draw the frame
            	this.frameDrawer.draw(framePos, g2d);
                
            	// Create a new Graphics object for the Frame
        		Graphics2D fg2d = (Graphics2D) g2d.create(
    				framePos.getX() * (Frame.FRAME_WIDTH + 2 * FrameDrawer.VERT_PADDING + FrameDrawer.VERT_MARGIN) + FrameDrawer.VERT_PADDING,
    				framePos.getY() * (Frame.FRAME_HEIGHT + 2 * FrameDrawer.HORIZ_PADDING + FrameDrawer.HORIZ_MARGIN) + FrameDrawer.HORIZ_PADDING,
    				Frame.FRAME_WIDTH,
    				Frame.FRAME_HEIGHT
    			);
            	
            	// Go through the list of items
                Iterator<FrameItem> itemIterator = frame.itemIterator();
                while (itemIterator.hasNext()) {
                	// Get item
                    FrameItem item = itemIterator.next();
                    
                    // Draw item
                    this.drawers.get(item.getClass()).draw(item, fg2d);
                }
            }
        }
	}
	
	/**
	 * Inicializálja a grafikus interfészt,
	 * többek közt megjeleníti a játékhoz tartozó ablakot.
	 */
	private void initGUI() {
		// Set up main window
		JFrame frame = new JFrame("Continuity by Nand Inc.");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(
			3 * (FrameDrawer.WIDTH + 2 * FrameDrawer.VERT_MARGIN + 2 * FrameDrawer.VERT_PADDING) - FrameDrawer.VERT_MARGIN,
			3 * (FrameDrawer.HEIGHT + 2 * FrameDrawer.HORIZ_MARGIN + 2 * FrameDrawer.HORIZ_PADDING) - FrameDrawer.HORIZ_MARGIN + 30
		);
		frame.setLocationRelativeTo(null);
		
		// Set up menu
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menu1 = new JMenu("Test");
		menuBar.add(menu1);
		menu1.addActionListener(null);
		
		// Set up main panel
		this.canvas = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				draw(g);
			}
		};
		this.canvas.setBackground(new Color(238, 238, 238));
		frame.add(this.canvas);
		
		// Show main window
		frame.setVisible(true);
	}
}
