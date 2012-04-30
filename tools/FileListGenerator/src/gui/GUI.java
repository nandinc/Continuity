package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import sun.org.mozilla.javascript.internal.ast.ThrowStatement;

public class GUI extends JFrame {
	
    static private final String newline = "\n";
    JButton openButton, saveButton;
    JTextArea log;
    ActionListener actionListener;
    
    private Log logger;
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
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
	public GUI() {
		super("Map generáló");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 600);
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
        java.net.URL imgURL = GUI.class.getResource(path);
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
	            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	            
	            int returnVal = fc.showOpenDialog(GUI.this);
	 
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                final File file = fc.getSelectedFile();
	                //This is where a real application would open the file.
	                log.append("Opening: " + file.getName() + "." + newline);
	                log.append("Generating FileList...\n");
	                new Runnable() {
	        			public void run() {
	        				try {
	        					String content = fileLister.FileListGenerate.run(file);
	        					log.append(content);
	        				} catch (Exception e) {
	        					e.printStackTrace();
	        				}
	        			}
	                }.run();
	                
	                log.append("List generated.\n");
	            } else {
	                log.append("Open command cancelled by user." + newline);
	            }
	            log.setCaretPosition(log.getDocument().getLength());
	 
	        //Handle save button action.
	        } else if (e.getSource() == saveButton) {
	        	if (e != null) {
		        	JFileChooser fc = new JFileChooser(new File(System.getProperty("user.dir")));
		            fc.setAcceptAllFileFilterUsed(false);
		            FileNameExtensionFilter filter = new FileNameExtensionFilter("Tex files only", "tex");
		            fc.setFileFilter(filter);
		            fc.setSelectedFile(new File("13.tex"));
		            
		            int returnVal = fc.showSaveDialog(GUI.this);
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                File file = fc.getSelectedFile();
		                //This is where a real application would save the file.
		                log.append("Saving: " + file.getName() + "." + newline);
		                final File file2 = file;
		                
		                new Runnable(){
							
							@Override
							public void run(){
						        texHandler.TexHandler.run(file2);
							}
						}.run();
						
						log.append("Exporting finished.");
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
