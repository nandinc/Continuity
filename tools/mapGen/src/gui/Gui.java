package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.*;

public class Gui extends JFrame {
	
    static private final String newline = "\n";
    JButton openButton, saveButton;
    JTextArea log;
    ActionListener actionListener;
    
    private Log logger;
    private decoder.Decoder decoder = null;
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		super("Map generáló");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setContentPane(contentPane);
		
		//Create the log first, because the action listeners
        //need to refer to it.
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
		
		logger = new Log();
        
        //Create font.  
        //Font Name : Default text field font  
        //Font Style : Default text field font style  
        //Font Size : 16  
        Font newTextFieldFont = new Font(log.getFont().getName(), log.getFont().getStyle(), 12);  
         
        //Set JTextField font using new created font  
        log.setFont(newTextFieldFont);
        
        //Create action listener
        actionListener = new MyActionListener();
        
        //Create the open button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        openButton = new JButton("Open a File...",
                                 createImageIcon("images/Open.png"));
        openButton.addActionListener(actionListener);
        openButton.setFocusable(false);
 
        //Create the save button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        saveButton = new JButton("Save a File...",
                                 createImageIcon("images/Save.png"));
        saveButton.addActionListener(actionListener);
        saveButton.setFocusable(false);
        
        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
 
        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
	}
	
	/** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Gui.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

	class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
	        //Handle open button action.
	        if (e.getSource() == openButton) {
	        	JFileChooser fc = new JFileChooser(new File(System.getProperty("user.dir")));
	            fc.setAcceptAllFileFilterUsed(false);
	            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files only", "txt");
	            fc.setFileFilter(filter);
	            fc.setSelectedFile(new File("testmap.txt"));
	            
	            int returnVal = fc.showOpenDialog(Gui.this);
	 
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc.getSelectedFile();
	                //This is where a real application would open the file.
	                log.append("Opening: " + file.getName() + "." + newline);
	                decoder = io.getInputFile.readTextFile(file, logger);
	                log.append("Decoding...\n");
	                String out = decoder.Decode();
	                log.append("Decoded:\n" + out);
	            } else {
	                log.append("Open command cancelled by user." + newline);
	            }
	            log.setCaretPosition(log.getDocument().getLength());
	 
	        //Handle save button action.
	        } else if (e.getSource() == saveButton) {
	        	if (decoder != null) {
		        	JFileChooser fc = new JFileChooser(new File(System.getProperty("user.dir")));
		            fc.setAcceptAllFileFilterUsed(false);
		            FileNameExtensionFilter filter = new FileNameExtensionFilter("map files only", "map");
		            fc.setFileFilter(filter);
		            fc.setSelectedFile(new File("testmap.map"));
		            
		            int returnVal = fc.showSaveDialog(Gui.this);
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                File file = fc.getSelectedFile();
		                //This is where a real application would save the file.
		                log.append("Saving: " + file.getName() + "." + newline);
		                io.getInputFile.writeMapFile(file, logger, decoder.getMapFile());
		            } else {
		                log.append("Save command cancelled by user." + newline);
		            }
		            log.setCaretPosition(log.getDocument().getLength());
	        	}
	        }
		}
		
	}
	
	public class Log {
		public void doLog(String text) {
			log.append(text);
		}
	}
}
