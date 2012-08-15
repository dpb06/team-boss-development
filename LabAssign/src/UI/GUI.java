package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * @author Haydn Newport and Barbara MacKenzie
 */
public class GUI extends JFrame implements ActionListener, ItemListener {
	// Parameters
	private JFrame frame;
	private JMenuBar menuBar;
	//private Graphics g;
	
	private JPanel southButtonPanel;
	
	private JTextArea textArea;
	private final JTextField fileText;

	// currently this sets up all the graphical user interface.  I'll later break it up into component methods
	public GUI(){
		frame = new JFrame();
		BorderLayout l = new BorderLayout();
		frame.setLayout(l);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setVisible(false);

		// call method to create the menu
		createMenu();

		// section for graphics

		// section for dice
		JPanel fileAlgoPanel = new JPanel();
		fileAlgoPanel.setLayout(new BoxLayout(fileAlgoPanel, BoxLayout.X_AXIS));
		int width = 500;
		int height = 200;
		fileAlgoPanel.setSize(width, height);
		fileAlgoPanel.setBackground(Color.CYAN);
		
		fileText = new JTextField("File Name here....", 10);
		fileText.setMaximumSize(new Dimension(500,20));
		JButton fileButton = new JButton("Browse");
		JButton runButton = new JButton("Run");
		fileButton.setBounds(0, 0, 50, 20);
		fileButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: Make this start in the text field path
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(fileText);
				fileText.setText(fc.getSelectedFile().getAbsolutePath());
				
			}
		});
		
		fileAlgoPanel.add(new JLabel("File    "));
		fileAlgoPanel.add(fileText);
		fileAlgoPanel.add(fileButton);
		
		// RadioButtons for Algorithm Selection
		JPanel algoSelect = new JPanel();
		algoSelect.setLayout(new BoxLayout(algoSelect, BoxLayout.Y_AXIS));
		ButtonGroup algoGroup = new ButtonGroup();
		JRadioButton[] algoRadios = new JRadioButton[3];
		algoRadios[0] = new JRadioButton("Naive");
		algoRadios[1] = new JRadioButton("Naive1");
		algoRadios[2] = new JRadioButton("Naive3");
		for (JRadioButton b : algoRadios){
			algoGroup.add(b);
			algoSelect.add(b);
		}
		fileAlgoPanel.add(algoSelect);
		fileAlgoPanel.add(runButton);

		frame.add(fileAlgoPanel, BorderLayout.CENTER);

		// informational text area
		textArea = new JTextArea(10, 80);
		textArea.setLineWrap(true);
		textArea.setEditable(true);
		textArea.scrollRectToVisible(getBounds());
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setPreferredSize(textArea.getPreferredScrollableViewportSize());
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		
		// southPanel contains the textArea and second panel of buttons
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());


		// adding the text area to the south panel
		southPanel.add(scroll, BorderLayout.SOUTH);

		frame.add(southPanel, BorderLayout.SOUTH);

		frame.pack();
		frame.setVisible(true);
	}

	/** Self explanatory.  Builds the menu used by the game **/
	public void createMenu(){
		// Menu bar for the game
		menuBar = new JMenuBar();

		// first chain of menus
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);	// KeyEvents create hot-key shortcuts
		file.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
		menuBar.add(file);

		// 'New Game' menu option
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
		newGame.getAccessibleContext().setAccessibleDescription("Doesn't do anything yet!");	//TODO:
		newGame.addActionListener(this);
		file.add(newGame);
		// 'Close' menu option
		JMenuItem close = new JMenuItem("Close");
		close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		close.getAccessibleContext().setAccessibleDescription("Will eventually close the game!");	//TODO:
		close.addActionListener(this);
		file.add(close);

		menuBar.add(new JMenuItem("Game"));	// probably not needed but I'm leaving it here for now...

		frame.add(menuBar, BorderLayout.NORTH);
	}

	/** main method **/
	public static void main(String[] args){
		GUI g = new GUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("New Game")){
			// start a new game
		} else if(e.getActionCommand().equals("Close")){
			// close the current game
			int q = JOptionPane.showConfirmDialog(this, new JLabel("Are you sure you want to\nclose this application?"), "Close", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(q == 0){
				frame.dispose();
				System.exit(0);
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
	}
}