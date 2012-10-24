package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import algorithmDataStructures.AlgorithmOutput;
import algorithmDataStructures.Lab;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;
import algorithmDataStructures.Tutorial;
import algorithms.BossSort;
import algorithms.CuttingSort;
import algorithms.HowardsSort;
import algorithms.PermuSort;
import dataParsing.StudentDataParser;

/**
 * @author Haydn Newport and Barbara MacKenzie
 */
public class GUI extends JFrame implements ActionListener, ItemListener {
	// Parameters
	private JFrame frame;
	private JPanel boundsPanel;
	private JPanel fitnessFunctionPanel;
	private JMenuBar menuBar;

	private JTextArea textArea;
	private final JTextField LabFileTextField;
	private final JTextField TutFileTextField;
	private HistoCanvas canvas;
	private ArrayList<Bounds> sessionBoundsLabs = new ArrayList<Bounds>();
	private ArrayList<Bounds> sessionBoundsTuts = new ArrayList<Bounds>();
	private List<Timeslot> labsList = new ArrayList<Timeslot>();
	private List<Timeslot> tutorialsList = new ArrayList<Timeslot>();
	boolean alreadyRUN = false;

	private String selectedAlgorithm = "Boss Sort";
	
	private JLabel fit;
	
	//Fields for fileChosen()/doRun() use
	File labs;
	File tuts;
	private List<Student> labStudents = new ArrayList<Student>();
	private List<Student> tutStudents = new ArrayList<Student>();

	JButton save;	// save button created in GUI, field so can enable in another method
	AlgorithmOutput output;

	// currently this sets up all the graphical user interface. I'll later break
	// it up into component methods
	public GUI() {
		frame = new JFrame("LabAssign");
		BorderLayout l = new BorderLayout(4, 4);
		frame.setLayout(l);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setVisible(false);

		// call method to create the menu
		createMenu();

		JPanel fileAlgoPanel = new JPanel();
		fileAlgoPanel.setLayout(new GridBagLayout());

		int width = 500;
		int height = 200;
		fileAlgoPanel.setSize(width, height);

		// File for Labs textField
		LabFileTextField = new JTextField("File Name for Labs here....", 10);
		LabFileTextField.setMaximumSize(new Dimension(500, 20));
		LabFileTextField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {		alreadyRUN = false;	}

			@Override
			public void keyReleased(KeyEvent e) {	alreadyRUN = false;	}

			@Override
			public void keyPressed(KeyEvent e) {	alreadyRUN = false;	}
		});

		JButton labFileButton = new JButton("Browse");
		labFileButton.setBounds(0, 0, 50, 20);

		final JButton runButton = new JButton("Run");
		runButton.setEnabled(false);

		labFileButton.addActionListener(new ActionListener() {			 
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: Make this start in the text field path
				//Handle 'Browse' button action.
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(GUI.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					labs = fc.getSelectedFile();
					LabFileTextField.setText(fc.getSelectedFile().getAbsolutePath());
					runButton.setEnabled(true);
					fileChosen();
				}
			}
		});

		/** */
		// File for Tutorials textField
		TutFileTextField = new JTextField("File Name for Tutorials here....", 10);
		TutFileTextField.setMaximumSize(new Dimension(500, 20));
		TutFileTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {		alreadyRUN = false;	}

			@Override
			public void keyReleased(KeyEvent e) {	alreadyRUN = false;	}

			@Override
			public void keyPressed(KeyEvent e) {	alreadyRUN = false;	}
		});

		JButton tutFileButton = new JButton("Browse");
		tutFileButton.setBounds(0, 0, 50, 20);

		tutFileButton.addActionListener(new ActionListener() {			 
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: Make this start in the text field path
				//Handle 'Browse' button action.
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(GUI.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					tuts = fc.getSelectedFile();
					TutFileTextField.setText(fc.getSelectedFile().getAbsolutePath());
					runButton.setEnabled(true);
					fileChosen();
				}
			}
		});

		/** */

		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				output = null;	//Once 'Run' clicked, output wiped to be refilled
				doRun();
			}
		});



		// RadioButtons for Algorithm Selection
		JPanel algoSelect = new JPanel();
		algoSelect.setLayout(new BoxLayout(algoSelect, BoxLayout.Y_AXIS));
		// This array contains all algorithm options
		//String[] algorithms = {"Boss Sort", "Howard Sort", "Cutting Sort", "Permute Sort"};
		String[] algorithms = {"Boss Sort", "Howard Sort", "Permute Sort"};
		JComboBox algoGroup = new JComboBox(algorithms);


		algoGroup.setSelectedIndex(0);
		algoGroup.addActionListener(new ActionListener() {      
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				selectedAlgorithm = (String)cb.getSelectedItem();
			}
		});

		algoGroup.setMaximumSize(new Dimension(200,25));
		algoSelect.add(algoGroup);
		GridBagConstraints defaultCons = new GridBagConstraints();
		defaultCons.weightx = 0;
		GridBagConstraints fileCons = new GridBagConstraints();
		fileCons.weightx = 1;
		fileCons.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints lastCons = new GridBagConstraints();
		lastCons.insets = new Insets(0, 10, 0, 0);
		lastCons.ipadx = 20;
		lastCons.gridheight = 2;
		fileAlgoPanel.add(new JLabel("Lab Answers File      "), defaultCons);
		fileAlgoPanel.add(LabFileTextField, fileCons);
		fileAlgoPanel.add(labFileButton, defaultCons);
		defaultCons.gridy = 1;
		fileCons.gridy = 1;
		fileAlgoPanel.add(new JLabel("Tutorial Answers File "), defaultCons);
		fileAlgoPanel.add(TutFileTextField, fileCons);
		fileAlgoPanel.add(tutFileButton, defaultCons);
		fileAlgoPanel.add(algoSelect, lastCons);
		fileAlgoPanel.add(runButton, lastCons);
		JPanel canvasPanel = new JPanel();
		Dimension d = new Dimension(frame.getWidth() - 100, 500);
		canvasPanel.add(new Box.Filler(d, d, d));
		canvas = new HistoCanvas();
		// canvas.setBackground(Color.red);
		canvasPanel.add(canvas);
		frame.add(canvasPanel, BorderLayout.CENTER);
		textArea = new JTextArea(1, 4);
		textArea.setEditable(true);

		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BorderLayout());

		// Finish the panel, pack and display
		boundsPanel = new JPanel();
		boundsPanel.setLayout(new BoxLayout(boundsPanel, BoxLayout.Y_AXIS));
		boundsPanel.setLayout(new GridBagLayout());

		fitnessFunctionPanel = new JPanel();
		fitnessFunctionPanel.setLayout(new BorderLayout());
		fit = new JLabel();
		fitnessFunctionPanel.add(fit);
		fitnessFunctionPanel.setVisible(true);

		eastPanel.add(boundsPanel, BorderLayout.NORTH);
		eastPanel.add(fitnessFunctionPanel, BorderLayout.CENTER);
		eastPanel.setVisible(true);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.add(fileAlgoPanel);
		JPanel southPanel = new JPanel();
		save = new JButton("Save Results");
		save.setEnabled(false);
		save.addActionListener(new ActionListener() {			 
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: Make this start in the text field path
				//Handle 'Save' button action.
				if(output != null){
					JFileChooser jfc = new JFileChooser();
					if (jfc.showSaveDialog(GUI.this) ==  JFileChooser.APPROVE_OPTION){
						File fout = jfc.getSelectedFile();
						fileOutput(output, fout);
					}
				}
				else
					System.out.println("No output to save");
			}
		});

		southPanel.add(save);
		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(eastPanel, BorderLayout.EAST);
		frame.add(southPanel, BorderLayout.SOUTH);
		frame.setPreferredSize(new Dimension(1000, 800));
		frame.pack();		
		frame.setVisible(true);
	}

	private void fileOutput(AlgorithmOutput output, File fout) {
		//Algorithm output is HashMap<Timeslot,ArrayList<Student>>
		try{
			// Create file 
			FileWriter fstream = new FileWriter(fout);
			//TODO: Allow user to select output file
			BufferedWriter out = new BufferedWriter(fstream);

			out.write("Output for " + selectedAlgorithm);
			out.newLine();

			/** TODO Delete when flaggedForLabs/flaggedForTuts done**/
			out.write("Students Flagged:\n");			
			for(Student s: output.getFlagged()){
				if(s.getFlaggedForLabs())
					out.write("\t" + s.getStudentNum() + " - " + s.getName() + "\n");
			}

			/** TODO Uncomment when flaggedForLabs/flaggedForTuts done
			/** Flagged Students x 3 - for both, just labs & just tuts
			// Flagged for both:
			out.write("Students Flagged for both Labs & Tutorials:\n");			
			for(Student s: output.getFlagged()){
				if(s.getFlaggedForLabs() && s.getFlaggedForTuts())
					out.write("\t" + s.getStudentNum() + " - " + s.getName() + "\n");
			}
			// Flagged for labs:
			out.write("\nStudents Flagged for Labs:\n");			
			for(Student s: output.getFlagged()){
				if(s.getFlaggedForLabs() && !s.getFlaggedForTuts())
					out.write("\t" + s.getStudentNum() + " - " + s.getName() + "\n");
			}
			// Flagged for tuts:
			out.write("Students Flagged for Tutorials:\n");			
			for(Student s: output.getFlagged()){
				if(!s.getFlaggedForLabs() && s.getFlaggedForTuts())
					out.write("\t" + s.getStudentNum() + " - " + s.getName() + "\n");
			}**/

			/** Iterate through Timeslots twice times (labs & tuts) **/

			out.write("Assigned Students:");
			// Labs
			for(Timeslot t: output.keySet()){
				if(t instanceof Lab){
					out.write("Lab - " + t.toString()+"\n");
					for(Student s: t.getAssigned()){
						out.write("\t" + s.getStudentNum() + " - " + s.getName() + "\n");
					}							
				}
			}

			// Tuts
			for(Timeslot t: output.keySet()){
				if(t instanceof Tutorial){
					out.write("Tutorial - " + t.toString()+"\n");
					for(Student s: t.getAssigned()){
						out.write("\t" + s.getStudentNum() + " - " + s.getName() + "\n");
					}							
				}
			}

			//Close the output stream
			out.close();

		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
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

	public void fileChosen() {
		try {
			labs = new File(LabFileTextField.getText());
			tuts = new File(TutFileTextField.getText());

			labStudents = new ArrayList<Student>();
			tutStudents = new ArrayList<Student>();

			boolean havelabs = false;
			boolean havetuts = false;
			if(labs.exists())
				havelabs = true;
			if(tuts.exists())
				havetuts = true;

			List<Student> naughtyStudents = new ArrayList<Student>();
			if(havelabs){			
				StudentDataParser labParser = new StudentDataParser(labs);		
				labsList = labParser.getTimeslots();
				labStudents = labParser.parseSelections(labsList);
				doBounds(labsList, true, havelabs, havetuts);

				//NaughtyList for Students with 1 or 0 choices
				for(Student s: labStudents){
					if((s.getFirstChoiceLabs().size() + s.getSecondChoiceLabs().size() + s.getThirdChoiceLabs().size()) < 2)
						naughtyStudents.add(s);
				}
			}
			if(naughtyStudents.size() > 0){
				new NaughtyList(naughtyStudents, labsList, "Students with one/no labs selected").getStudents();
				//TODO Stop it here awaiting user's checkover of list
			}
			naughtyStudents = new ArrayList<Student>();	// Cleared so can use for tuts too

			if(havetuts){
				StudentDataParser tutParser = new StudentDataParser(tuts);
				tutorialsList = tutParser.getTimeslots();
				tutStudents = tutParser.parseSelections(tutorialsList);
				doBounds(tutorialsList, false, havelabs, havetuts);
				//NaughtyList for Students with 1 or 0 choices
				for(Student s: tutStudents){
					if((s.getFirstChoiceTuts().size() + s.getSecondChoiceTuts().size() + s.getThirdChoiceTuts().size()) < 2)
						naughtyStudents.add(s);
				}
				if(naughtyStudents.size() > 0){
					tutStudents = new NaughtyList(naughtyStudents, labsList, "Students with one/no tutorials selected").getStudents();
					//TODO Stop it here awaiting user's checkover of list
				}
				naughtyStudents = new ArrayList<Student>();
		    }
			if (havelabs && havetuts){
				for(Student s: labStudents){
					for(Student t: tutStudents){
						s.merge(t);
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void doRun(){
		for(Timeslot t : tutorialsList){
			t.getAssigned().clear();
		}
		for(Timeslot t : labsList){
			t.getAssigned().clear();
		}
		if(tuts.exists()){
			StudentDataParser tutParser;
			try {
				tutParser = new StudentDataParser(tuts);
				tutStudents = tutParser.parseSelections(tutorialsList);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(labs.exists()){
			StudentDataParser tutParser;
			try {
				tutParser = new StudentDataParser(labs);
				labStudents = tutParser.parseSelections(labsList);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(selectedAlgorithm.equals("Boss Sort")){
			BossSort bs = new BossSort(new ArrayList<Timeslot>(labsList),new ArrayList<Timeslot>(tutorialsList),new ArrayList<Student>(labStudents));
			output = bs.start();
			canvas.setTimeslots(new ArrayList<Timeslot>(output.keySet()));
		}
		else if(selectedAlgorithm.equals("Howard Sort")){
			HowardsSort hs = new HowardsSort(new ArrayList<Timeslot>(labsList),new ArrayList<Timeslot>(tutorialsList),new ArrayList<Student>(labStudents));
			output = hs.start();
			canvas.setTimeslots(new ArrayList<Timeslot>(output.keySet()));
		}
		else if(selectedAlgorithm.equals("Cutting Sort")){
			CuttingSort cs = new CuttingSort(new ArrayList<Timeslot>(labsList),new ArrayList<Timeslot>(tutorialsList),new ArrayList<Student>(labStudents));
			output = cs.start();
			canvas.setTimeslots(new ArrayList<Timeslot>(output.keySet()));
		}else if(selectedAlgorithm.equals("Permute Sort")){
			PermuSort ps = new PermuSort(new ArrayList<Timeslot>(labsList),new ArrayList<Timeslot>(tutorialsList),new ArrayList<Student>(labStudents));
			output = ps.start();
			canvas.setTimeslots(new ArrayList<Timeslot>(output.keySet()));
		}
	    
		System.out.println(output.fitnessValue());
		String fitness = "Fitness - " + output.fitnessValue();
//		for(String f: output.getFitness().keySet()){
//			fitness += (f + " - " + output.getFitness().get(f) + "\n");
//		}
		fit.setText(fitness);	
		frame.validate();

		//Create Naughty Lists
		if(labs.exists())
			new NaughtyList(output.getFlagged(), labsList).setVisible(true);
		if(tuts.exists())
			new NaughtyList(output.getFlagged(), tutorialsList).setVisible(true);

		frame.repaint();
	}


	//TODO resolve it so that the labs and tutorials are saved
	// and not overwritten when re-running algorithm.
	//
	private void doBounds(List<Timeslot> slots, boolean isLabs, boolean hasLabs, boolean hasTuts){

			if ( !alreadyRUN ) {
			
			// created the last of the bounds when
			// - we only have Labs and are now making bounds for these
			// - or we have Tutorials and have done labs, or dont have labs
			if(isLabs && !hasTuts)
				alreadyRUN = true;
			else if(!isLabs && hasTuts){
				alreadyRUN = true;				
			}
			
			save.setEnabled(true);
			// recreate bounds array
			int startRow;
			if(isLabs){
				startRow = 0;
			}else
				startRow = sessionBoundsLabs.size()+5;

			ArrayList<Bounds> sessionBounds = new ArrayList<Bounds>();
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(2, 2, 2, 2);
			c.fill = GridBagConstraints.VERTICAL;
			if(isLabs || (!isLabs && !hasLabs) ){
				c.weightx = 0.5;
				c.gridx = 0;
				c.gridy = 0+startRow;
				boundsPanel.add(new JLabel("Session Name "));
				c.gridx = 1;
				c.gridy = 0+startRow;
				boundsPanel.add(new JLabel("Min      "));
				c.gridx = 2;
				c.gridy = 0+startRow;
				boundsPanel.add(new JLabel("Max      "));
				c.gridx = 3;
				c.gridy = 0+startRow;
				boundsPanel.add(new JLabel("Pref. Min"));

				c.gridx = 4;
				c.gridy = 0+startRow;
				boundsPanel.add(new JLabel("Pref. Max"));
			}
			String sectionTitle = "";

			if(isLabs){
				sectionTitle = "LABS";
			} else {
				sectionTitle = "TUTS";
			}

			createTitleRow(boundsPanel, 3+startRow, sectionTitle);

			for (int i = 0; i < slots.size(); i++) {
				Bounds timeslotBounds = new Bounds(slots.get(i));
				sessionBounds.add(timeslotBounds);
				// creates a set of input boxes in in the ith row, though
				// it starts two rows down to allow space for the title rows
				// (eg. Session Name)
				String slotTitle = slots.get(i).toString();
				timeslotBounds.createInputBoxes(boundsPanel, i + 4 + startRow, slotTitle);
			}
			if(isLabs)
				sessionBoundsLabs = sessionBounds;
			else
				sessionBoundsTuts = sessionBounds;
		}
		frame.validate();
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


	public boolean createTitleRow(JPanel panel, int drawRow, String name) {
		if (panel.getLayout() instanceof GridBagLayout) {
			// Uses the gridbag layout manager, so we can set up where we
			// want the
			// boxes to be placed in the grid.
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(1, 1, 1, 1);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.5;
			c.gridy = drawRow;
			JLabel title = new JLabel(name);
			c.gridx = 0;

			panel.add(title, c);
			final JLabel minText = new JLabel("");
			c.gridx++;
			panel.add(minText, c);

			final JLabel maxText = new JLabel("");

			c.gridx++;
			panel.add(maxText, c);

			final JLabel prefMinText = new JLabel("");
			c.gridx++;
			panel.add(prefMinText, c);

			final JLabel prefMaxText = new JLabel("");
			c.gridx++;
			panel.add(prefMaxText, c);

			return true;
		} else {
			return false;
		}

	}


	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
	}


	// The bounds class is used by the GUI to store the bounds
	// given to it by the user for use in the algorithm of choice.
	// Each set of bounds has a maximum, a minimum, and a preferred max and min.
	public class Bounds {
		private Timeslot timeslot;

		public Bounds(Timeslot _timeslot) {
			this.timeslot = _timeslot;
		}


		public boolean createInputBoxes(JPanel panel, int drawRow, String name) {
			if (panel.getLayout() instanceof GridBagLayout) {
				// Uses the gridbag layout manager, so we can set up where we
				// want the
				// boxes to be placed in the grid.
				GridBagConstraints c = new GridBagConstraints();
				c.insets = new Insets(1, 1, 1, 1);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 0.5;
				c.gridy = drawRow;
				JLabel title = new JLabel(name);
				c.gridx = 0;
				panel.add(title, c);
				final JTextArea minText = new JTextArea(timeslot.getMinStudents()+"");
				c.gridx++;
				panel.add(minText, c);
				minText.getDocument().addDocumentListener(
						new DocumentListener() {

							@Override
							public void changedUpdate(DocumentEvent e) {
								update(e);
							}

							@Override
							public void insertUpdate(DocumentEvent e) {
								update(e);
							}

							@Override
							public void removeUpdate(DocumentEvent e) {
								update(e);
							}

							public void update(DocumentEvent e) {
								try {
									if (Integer.parseInt(minText.getText()) < 0 ||
											Integer.parseInt(minText.getText()) > timeslot
											.getMaxStudents()) {
										minText.setForeground(Color.red);							

									} else{
										timeslot.setMinStudents(Integer
												.parseInt(minText.getText()));
										minText.setForeground(Color.black);
									}
								} catch (NumberFormatException nfe) {
								}
								System.out.println(timeslot.getMinStudents());
								System.out.println(timeslot.getMaxStudents());
								System.out.println(timeslot.getPreferredMin());
								System.out.println(timeslot.getPreferredMax());
							}
						});
				final JTextArea maxText = new JTextArea(timeslot.getMaxStudents()+"");
				c.gridx++;
				panel.add(maxText, c);
				maxText.getDocument().addDocumentListener(
						new DocumentListener() {

							@Override
							public void changedUpdate(DocumentEvent e) {
								update(e);
							}

							@Override
							public void insertUpdate(DocumentEvent e) {
								update(e);
							}

							@Override
							public void removeUpdate(DocumentEvent e) {
								update(e);
							}

							public void update(DocumentEvent e) {
								try {
									if (Integer.parseInt(maxText.getText()) < timeslot
											.getMinStudents()) {

										timeslot.setMaxStudents(timeslot
												.getMinStudents() + 1);

									} else
										timeslot.setMaxStudents(Integer
												.parseInt(maxText.getText()));
								} catch (NumberFormatException nfe) {
								}
								System.out.println(timeslot.getMinStudents());
								System.out.println(timeslot.getMaxStudents());
								System.out.println(timeslot.getPreferredMin());
								System.out.println(timeslot.getPreferredMax());
							}

						});
				final JTextArea prefMinText = new JTextArea(timeslot.getPreferredMin()+"");
				c.gridx++;
				panel.add(prefMinText, c);
				prefMinText.getDocument().addDocumentListener(
						new DocumentListener() {
							@Override
							public void changedUpdate(DocumentEvent e) {
								update(e);
							}

							@Override
							public void insertUpdate(DocumentEvent e) {
								update(e);
							}

							@Override
							public void removeUpdate(DocumentEvent e) {
								update(e);
							}

							public void update(DocumentEvent e) {
								try {
									if (Integer.parseInt(prefMinText.getText()) < 0 || 
										Integer.parseInt(prefMinText.getText()) > timeslot
										.getPreferredMax()) {
										// do nothing if not between preferred max and 0	
										prefMinText.setForeground(Color.red);
									} else{
										timeslot.setPreferredMin(Integer
												.parseInt(prefMinText.getText()));
										prefMinText.setForeground(Color.black);
									}
								} catch (NumberFormatException nfe) {

								}
								System.out.println(timeslot.getMinStudents());
								System.out.println(timeslot.getMaxStudents());
								System.out.println(timeslot.getPreferredMin());
								System.out.println(timeslot.getPreferredMax());
							}
						});
				final JTextArea prefMaxText = new JTextArea(timeslot.getPreferredMax()+"");
				c.gridx++;
				panel.add(prefMaxText, c);
				prefMaxText.getDocument().addDocumentListener(
						new DocumentListener() {
							@Override
							public void changedUpdate(DocumentEvent e) {
								update(e);
							}

							@Override
							public void insertUpdate(DocumentEvent e) {
								update(e);
							}

							@Override
							public void removeUpdate(DocumentEvent e) {
								update(e);
							}

							public void update(DocumentEvent e) {
								try {
									if (Integer.parseInt(prefMaxText.getText()) < timeslot
											.getPreferredMin()) {

										timeslot.setPreferredMax(timeslot
												.getPreferredMin() + 1);
									} else
										timeslot.setPreferredMax(Integer
												.parseInt(prefMaxText.getText()));
								} catch (NumberFormatException nfe) {
								}
								System.out.println(timeslot.getMinStudents());
								System.out.println(timeslot.getMaxStudents());
								System.out.println(timeslot.getPreferredMin());
								System.out.println(timeslot.getPreferredMax());
							}
						});
				return true;
			} else {
				return false;
			}

		}

		public Timeslot getTimeslot() {
			return this.timeslot;
		}


		public void addBoundsInputs() {
		}
	}
}
