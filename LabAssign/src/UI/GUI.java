package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import algorithmDataStructures.Day;
import algorithmDataStructures.Lab;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;
import algorithms.BossSort;
import dataParsing.StudentDataParser;

/**
 * @author Haydn Newport and Barbara MacKenzie
 */
public class GUI extends JFrame implements ActionListener, ItemListener {
	// Parameters
	private JFrame frame;
	private JMenuBar menuBar;
	// private Graphics g;
	private int NUM_SESSIONS = 12;

	private JTextArea textArea;
	private final JTextField fileText;
	private HistoCanvas canvas; 

	// currently this sets up all the graphical user interface. I'll later break
	// it up into component methods
	public GUI() {
		frame = new JFrame();
		BorderLayout l = new BorderLayout(4,4);
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
		//fileAlgoPanel.setBackground(Color.CYAN);

		fileText = new JTextField("File Name here....", 10);
		fileText.setMaximumSize(new Dimension(500, 20));
		JButton fileButton = new JButton("Browse");
		JButton runButton = new JButton("Run");
		runButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				doRun();				
			}
		});
		fileButton.setBounds(0, 0, 50, 20);
		fileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: Make this start in the text field path
				//TODO: MAKE HANDLE NULL RETURNS
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
		for (JRadioButton b : algoRadios) {
			algoGroup.add(b);
			algoSelect.add(b);
		}
		fileAlgoPanel.add(algoSelect);
		fileAlgoPanel.add(runButton);

		
		// gridPanel contains the the text areas for maximum sizes
		JPanel gridPanel = new JPanel();
		gridPanel.setBackground(Color.GRAY);
		gridPanel.setLayout(new GridLayout(2, NUM_SESSIONS, 2, 2));
		gridPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		JTextArea[] maxSizes = new JTextArea[NUM_SESSIONS];
		for (int session = 0; session < NUM_SESSIONS; session++) {
			(maxSizes[session] = new JTextArea(1, 1)).setEditable(true);
			// Possible initial value?
			// maxSizes[session].setText(val);
			gridPanel.add(maxSizes[session]);
		}
		// Add labels to inputs
		for (int session = 0; session < NUM_SESSIONS; session++) {
			gridPanel.add(new JLabel("Session " + (session + 1)));
		}
		JPanel canvasPanel = new JPanel();
		Dimension d = new Dimension(frame.getWidth()-100, 500);
		canvasPanel.add(new Box.Filler(d,d,d));
		canvas = new HistoCanvas();
		//canvas.setBackground(Color.red);
		
		//TODO: Create a stub-data generating class for test data like this
		List<Timeslot> in = new ArrayList<Timeslot>();
		in.add(new Lab(12,1640,3110,Day.Friday, 2));
		in.add(new Lab(13,1640,3110,Day.Thursday, 3));
		in.add(new Lab(13,1640,3110,Day.Friday, 6));
		in.get(0).addStudent(new Student(12, "h", "n", 1315));
		in.get(0).addStudent(new Student(1, "i", "n", 1316));
		in.get(0).addStudent(new Student(14, "j", "n", 1317));
		in.get(0).addStudent(new Student(18, "k", "n", 1318));
		in.get(1).addStudent(new Student(19, "l", "n", 1319));
		in.get(1).addStudent(new Student(20, "l", "n", 1319));
		in.get(1).addStudent(new Student(21, "l", "n", 1319));
		in.get(1).addStudent(new Student(22, "l", "n", 1319));
		in.get(1).addStudent(new Student(23, "l", "n", 1319));
		in.get(1).addStudent(new Student(24, "l", "n", 1319));
		in.get(1).addStudent(new Student(25, "l", "n", 1319));
		in.get(1).addStudent(new Student(12, "h", "n", 1315));
		in.get(1).addStudent(new Student(1, "i", "n", 1316));
		in.get(1).addStudent(new Student(14, "j", "n", 1317));
		in.get(1).addStudent(new Student(18, "k", "n", 1318));
		in.get(1).addStudent(new Student(19, "l", "n", 1319));
		in.get(1).addStudent(new Student(20, "l", "n", 1319));
		in.get(1).addStudent(new Student(21, "l", "n", 1319));
		in.get(2).addStudent(new Student(22, "l", "n", 1319));
		in.get(2).addStudent(new Student(23, "l", "n", 1319));
		in.get(2).addStudent(new Student(24, "l", "n", 1319));
		in.get(2).addStudent(new Student(25, "l", "n", 1319));
		canvas.setSections(in);
		canvasPanel.add(canvas);
		frame.add(canvasPanel, BorderLayout.CENTER);
		textArea = new JTextArea(1, 4);
		textArea.setEditable(true);
		//Finish the panel, pack and display
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.add(new JLabel("This is the east panel"));
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.add(fileAlgoPanel);	
		topPanel.add(gridPanel);
		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(eastPanel, BorderLayout.EAST);
		frame.pack();
		frame.setVisible(true);
	}

	/** Self explanatory. Builds the menu used by the game **/
	public void createMenu() {
		// Menu bar for the game
		menuBar = new JMenuBar();
		menuBar.setAlignmentX(JMenuBar.LEFT_ALIGNMENT);
		// first chain of menus
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F); // KeyEvents create hot-key shortcuts
		file.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");
		menuBar.add(file);

		// 'New Game' menu option
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.ALT_MASK));
		newGame.getAccessibleContext().setAccessibleDescription(
				"Doesn't do anything yet!"); // TODO:
		newGame.addActionListener(this);
		file.add(newGame);
		// 'Close' menu option
		JMenuItem close = new JMenuItem("Close");
		close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
				ActionEvent.ALT_MASK));
		close.getAccessibleContext().setAccessibleDescription(
				"Will eventually close the game!"); // TODO:
		close.addActionListener(this);
		file.add(close);
	}

	/** main method **/
	public static void main(String[] args) {
		GUI g = new GUI();
	}

	public void doRun(){
		try {
			StudentDataParser parser  = new StudentDataParser(new File(fileText.getText()));
			List<Timeslot> slots = parser.getTimeslots();
			List<Student> students = parser.parseSelections(slots);
			BossSort bs = new BossSort(new ArrayList<Timeslot>(slots),new ArrayList<Timeslot>(),new ArrayList<Student>(students));
			canvas.setSections(new ArrayList<Timeslot>(bs.getOutput().keySet()));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("New Game")) {
			// start a new game
		} else if (e.getActionCommand().equals("Close")) {
			// close the current game
			int q = JOptionPane.showConfirmDialog(this, new JLabel(
					"Are you sure you want to\nclose this application?"),
					"Close", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (q == 0) {
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