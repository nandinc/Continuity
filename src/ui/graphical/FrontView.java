package ui.graphical;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.graphical.FrontController;

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
	 * Kontroller
	 */
	private FrontController controller;
	
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
	 * Cache-elt szürke szín a háttérnek
	 */
	private Color greyColor = new Color(238, 238, 238);
	
	/**
	 * Cache-elt szín a pause grafikának
	 */
	private Color pauseColor = new Color(7, 7, 255, 128);
	
	/**
	 * Inicializálás
	 * @param game A model game objektuma
	 * @param controller A használt felület kontrollere
	 */
	public FrontView(Game game, FrontController controller) {
		this.game = game;
		this.controller = controller;
		
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
		
		// Subscribe to end of game event
		this.game.getPubSub().on("game:endOfGame", new Subscriber() {
			@Override
			public void eventEmitted(String eventName, Object eventParameter) {
				JOptionPane.showMessageDialog(null, "Congratulations, you finished the game!");
				FrontView.this.controller.quit();
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
		int canvasWidth = this.canvas.getWidth();
		int canvasHeight = this.canvas.getHeight();
		
		if (map == null) {
			return;
		}
		
		// Paint background
		g2d.setColor(this.greyColor);
		g2d.fillRect(0, 0, canvasWidth, canvasHeight);
		
		// Iterate through frames
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
        
        // Show pause graphic if in map viewport
        if (this.game.getViewportState() == VIEWPORT_STATE.MAP) {
        	g2d.setColor(this.pauseColor);
        	g2d.fillRect(canvasWidth - 40, 10, 10, 40);
        	g2d.fillRect(canvasWidth - 20, 10, 10, 40);
        }
	}
	
	/**
	 * Inicializálja a grafikus interfészt,
	 * többek közt megjeleníti a játékhoz tartozó ablakot.
	 */
	private void initGUI() {
		//
		// Set up main window
		//
		JFrame frame = new JFrame("Continuity by NaND Inc.");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(
			3 * (FrameDrawer.WIDTH + 2 * FrameDrawer.VERT_MARGIN + 2 * FrameDrawer.VERT_PADDING) - FrameDrawer.VERT_MARGIN,
			3 * (FrameDrawer.HEIGHT + 2 * FrameDrawer.HORIZ_MARGIN + 2 * FrameDrawer.HORIZ_PADDING) - FrameDrawer.HORIZ_MARGIN + 30
		);
		frame.setLocationRelativeTo(null);
		frame.setBackground(new Color(238, 238, 238));
		
		//
		// Set up menu
		//
		JMenuBar menuBar = new JMenuBar();
		menuBar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		frame.setJMenuBar(menuBar);
		
		JMenuItem menu;
		
		// New game
		menu = new JMenuItem("New game");
		menuBar.add(menu);
		menu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.newGame();
			}
		});
		
		
		// Skip level
		menu = new JMenuItem("Skip level");
		menuBar.add(menu);
		menu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.skipLevel();
			}
		});
		
		// About
		menu = new JMenuItem("About");
		menuBar.add(menu);
		menu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "This program was created by NaND Inc. as a group project for the course Software Laboratory 4.\n\nAuthors:\n - Berki Endre\n - Fodor Bertalan\n - Kádár András\n - Thaler Benedek\n\nThe program is based on Continuity, an award-winning flash game by continuitygame.com.");
			}
		});
		
		// Quit
		menu = new JMenuItem("Quit");
		menuBar.add(menu);
		menu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.quit();
			}
		});
		
		//
		// Set up main panel (canvas)
		//
		this.canvas = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				draw(g);
			}
		};
		frame.add(this.canvas);
		
		//
		// Show main window
		//
		frame.setVisible(true);
	}
}
