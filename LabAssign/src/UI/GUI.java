package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
public class GUI extends JFrame implements ActionListener, ItemListener  { 
 private final boolean DEBUG = false; 

	// Parameters
	private JFrame frame;

	private JPanel fitnessFunctionPanel;

	private JTextArea textArea;
	private final JTextField LabFileTextField;
	private final JTextField TutFileTextField;
	private HistoCanvas labCanvas;
	private HistoCanvas tutCanvas;
	private ArrayList<Bounds> sessionBoundsLabs = new ArrayList<Bounds>();
	private ArrayList<Bounds> sessionBoundsTuts = new ArrayList<Bounds>();
	private List<Timeslot> labsList = new ArrayList<Timeslot>();
	private List<Timeslot> tutorialsList = new ArrayList<Timeslot>();
	boolean alreadyRUN = false;

	private String selectedAlgorithm = "Boss Sort";

	private JLabel fit;
	private JLabel flaggedLabs;
	private JButton flaggedLabsButton;
	private JLabel flaggedTuts;
	private JButton flaggedTutsButton;	

	//Fields for fileChosen()/doRun() use
	File labs;
	File tuts;
	private List<Student> mergedStudents = new ArrayList<Student>();

	private JButton save;	// save button created in GUI, field so can enable in another method
	private JButton unanswered;
	private List<Student> unansweredStudents = new ArrayList<Student>();

	AlgorithmOutput output;
	private List<Student> labStudents;
	private List<Student> tutStudents;

	private JPanel innerTutorialsPanel;

	private JPanel innerLabsPanel;

	private int heightOfBounds;

	private JPanel labsPanel;

	private JPanel tutorialsPanel;

	// currently this sets up all the graphical user interface. I'll later break
	// it up into component methods
	public GUI() {
		frame = new JFrame("LabAssign");
		BorderLayout l = new BorderLayout(4, 4);
		frame.setLayout(l);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setVisible(false);

		JPanel fileAlgoPanel = new JPanel();
		fileAlgoPanel.setLayout(new GridBagLayout());

		int width = 500, height = 200;
		fileAlgoPanel.setSize(width, height);

		/** TOP OF GUI */
		// File for Labs textField
		LabFileTextField = new JTextField("File Name for Labs here....", 10);
		LabFileTextField.setMaximumSize(new Dimension(500, 20));
		LabFileTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) 	{	alreadyRUN = false;	}
			@Override
			public void keyReleased(KeyEvent e) {	alreadyRUN = false;	}
			@Override
			public void keyPressed(KeyEvent e)	{	alreadyRUN = false;	}
		});

		JButton labFileButton = new JButton("Browse");
		labFileButton.setBounds(0, 0, 50, 20);

		// File for Tutorials textField
		TutFileTextField = new JTextField("File Name for Tutorials here....", 10);
		TutFileTextField.setMaximumSize(new Dimension(500, 20));
		TutFileTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e)	{	alreadyRUN = false;	}
			@Override
			public void keyReleased(KeyEvent e)	{	alreadyRUN = false;	}
			@Override
			public void keyPressed(KeyEvent e)	{	alreadyRUN = false;	}
		});

		JButton tutFileButton = new JButton("Browse");
		tutFileButton.setBounds(0, 0, 50, 20);

		final JButton runButton = new JButton("Run");
		runButton.setEnabled(false);

		labFileButton.addActionListener(new ActionListener() {			 
			@Override
			public void actionPerformed(ActionEvent e) {
				//Handle 'Browse' button action.
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(GUI.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					labs = fc.getSelectedFile();
					LabFileTextField.setText(fc.getSelectedFile().getAbsolutePath());
					runButton.setEnabled(true);
					alreadyRUN = false;
					fileChosen();
				}
			}
		});

		tutFileButton.addActionListener(new ActionListener() {			 
			@Override
			public void actionPerformed(ActionEvent e) {
				//Handle 'Browse' button action.
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(GUI.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					tuts = fc.getSelectedFile();
					TutFileTextField.setText(fc.getSelectedFile().getAbsolutePath());
					runButton.setEnabled(true);
					alreadyRUN = false;
					fileChosen();
				}
			}
		});

		final JButton manualAssignLabs = new JButton("Manually Assign Labs");	// At bottom of GUI but needed for runButton ActionListener
		final JButton manualAssignTuts = new JButton("Manually Assign Tuts");	// At bottom of GUI but needed for runButton ActionListener

		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				output = null;	//Once 'Run' clicked, output wiped to be refilled
				if(labs.exists())
					manualAssignLabs.setEnabled(true);
				if(tuts.exists())
					manualAssignTuts.setEnabled(true);
				doRun();
			}
		});

		// RadioButtons for Algorithm Selection
		JPanel algoSelect = new JPanel();
		algoSelect.setLayout(new BoxLayout(algoSelect, BoxLayout.Y_AXIS));
		// This array contains all algorithm options
		String[] algorithms = {"Boss Sort", "Howard Sort"};
		JComboBox algoGroup = new JComboBox(algorithms);

		/** Button for Students with incomplete answers
		 *  **Only visible if there are any **
		 *  */
		unanswered = new JButton("");
		unanswered.setEnabled(false);
		unanswered.setVisible(false);
		unanswered.addActionListener(new ActionListener() {      
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(unansweredStudents);
				new UnansweredDialog(unansweredStudents, mergedStudents, GUI.this).setVisible(true);
				unansweredStudents.clear();
				if (unansweredStudents.isEmpty()){
					unanswered.setVisible(false);
				}
					
			}
		});

		/** Large chunk of code for TOP OF GUI layout starts */
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
		lastCons.gridy = 2;
		lastCons.gridwidth = 3;
		fileAlgoPanel.add(unanswered, lastCons);
		
		JPanel canvasPanel = new JPanel();
		//Dimension d = new Dimension(frame.getWidth() - 100, 500);
		//canvasPanel.add(new Box.Filler(d, d, d));
		labCanvas = new HistoCanvas();	
		tutCanvas = new HistoCanvas();	
		canvasPanel.add(labCanvas);
		canvasPanel.add(tutCanvas);
		frame.add(canvasPanel, BorderLayout.CENTER);
		
		textArea = new JTextArea(1, 4);
		textArea.setEditable(true);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.add(fileAlgoPanel);	
		/** Large chunk of code for TOP OF GUI layout ends */

		/** RIGHT SIDE OF GUI */
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BorderLayout());

		JPanel boundsPanel = new JPanel();
		boundsPanel.setLayout(new GridLayout(2, 1));
		boundsPanel.setOpaque(false);
		
		labsPanel = new JPanel();
		labsPanel.setLayout(new BorderLayout());
		
		innerLabsPanel = new JPanel();		
		innerLabsPanel.setLayout(new GridBagLayout());
		labsPanel.add(innerLabsPanel, BorderLayout.NORTH);
		
		tutorialsPanel = new JPanel();
		tutorialsPanel.setLayout(new BorderLayout());
		innerTutorialsPanel = new JPanel();
		innerTutorialsPanel.setLayout(new GridBagLayout());
		tutorialsPanel.add(innerTutorialsPanel, BorderLayout.NORTH);
		
		

		
		boundsPanel.add(labsPanel);
		boundsPanel.add(tutorialsPanel);
		
		
		
		
		
		//Fitness/Flagged Section
		fitnessFunctionPanel = new JPanel(new BorderLayout());
		fit = new JLabel("Fitness - Yet to be Calculated");

		JPanel flagLab = new JPanel(new BorderLayout());
		flaggedLabs = new JLabel("Flagged for Labs - None");
		flaggedLabsButton = new JButton("Show Flagged for Labs");
		flaggedLabsButton.setEnabled(false);
		flaggedLabsButton.addActionListener(new ActionListener() {      
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Student> studs = new ArrayList<Student>();
				for(Student s: output.getFlagged()){
					if(s.getFlaggedForLabs())
						studs.add(s);
				}
				new NaughtyList(studs, labsList).setVisible(true);
			}
		});
		flagLab.add(flaggedLabs, BorderLayout.WEST);
		flagLab.add(flaggedLabsButton, BorderLayout.EAST);

		JPanel flagTut = new JPanel(new BorderLayout());
		flaggedTuts = new JLabel("Flagged for Tuts - None");
		flaggedTutsButton = new JButton("Show Flagged for Tuts");
		flaggedTutsButton.setEnabled(false);
		flaggedTutsButton.addActionListener(new ActionListener() {      
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Student> studs = new ArrayList<Student>();
				for(Student s: output.getFlagged()){
					if(s.getFlaggedForTuts())
						studs.add(s);
				}
				new NaughtyList(studs, tutorialsList).setVisible(true);
			}
		});
		flagTut.add(flaggedTuts, BorderLayout.WEST);
		flagTut.add(flaggedTutsButton, BorderLayout.EAST);

		fitnessFunctionPanel.add(fit, BorderLayout.NORTH);
		fitnessFunctionPanel.add(flagLab, BorderLayout.CENTER);
		fitnessFunctionPanel.add(flagTut, BorderLayout.SOUTH);
		fitnessFunctionPanel.setVisible(true);

		eastPanel.add(boundsPanel, BorderLayout.NORTH);
		eastPanel.add(fitnessFunctionPanel, BorderLayout.SOUTH);
		eastPanel.setVisible(true);

		/** BOTTOM OF GUI */
		JPanel southPanel = new JPanel();

		save = new JButton("Save Results");
		save.setEnabled(false);
		save.addActionListener(new ActionListener() {			 
			@Override
			public void actionPerformed(ActionEvent e) {
				//Handle 'Save' button action.
				if(output != null){
					JFileChooser jfc = new JFileChooser();
					if (jfc.showSaveDialog(GUI.this) ==  JFileChooser.APPROVE_OPTION){
						File fout = jfc.getSelectedFile();
						fileOutput(output, fout);
					}
				}
				else
					if(DEBUG){ System.out.println("No output to save"); } 
			}
		});

		manualAssignLabs.setBounds(0, 0, 50, 20);
		manualAssignLabs.setEnabled(false);
		//Only enabled after algorithm has been run

		manualAssignLabs.addActionListener(new ActionListener() {			 
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Student> studs = new ArrayList<Student>();
				for(Student s: output.getFlagged()){
					if(s.getFlaggedForLabs())
						studs.add(s);
				}
				ArrayList<Timeslot> ts = new ArrayList<Timeslot>();
				for(Timeslot t: output.keySet()){
					if(t instanceof Lab)
						ts.add(t);
				}
				new ManualAssignList(studs, ts, GUI.this);
				naughtyCalculation(); 
				frame.validate();
			}
		});

		manualAssignTuts.setBounds(0, 0, 50, 20);
		manualAssignTuts.setEnabled(false);
		//Only enabled after algorithm has been run

		manualAssignTuts.addActionListener(new ActionListener() {			 
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Student> studs = new ArrayList<Student>();
				for(Student s: output.getFlagged()){
					if(s.getFlaggedForTuts())
						studs.add(s);
				}
				ArrayList<Timeslot> ts = new ArrayList<Timeslot>();
				for(Timeslot t: output.keySet()){
					if(t instanceof Tutorial)
						ts.add(t);
				}
				new ManualAssignList(studs, ts, GUI.this);
				naughtyCalculation(); 
				frame.validate();
			}
		});	
		
		

		southPanel.add(save);
		southPanel.add(manualAssignLabs);
		southPanel.add(manualAssignTuts);

		/** Adding all (TOP, RIGHT & BOTTOM) of GUI to frame */
		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(eastPanel, BorderLayout.EAST);
		frame.add(southPanel, BorderLayout.SOUTH);
		frame.setPreferredSize(new Dimension(1000, 800));
		frame.pack();	
		frame.setVisible(true);
		
		heightOfBounds = canvasPanel.getHeight()/2;
	}

	private void fileOutput(AlgorithmOutput output, File fout) {
		//Algorithm output is HashMap<Timeslot,ArrayList<Student>>
		try{
			// Create file 
			FileWriter fstream = new FileWriter(fout);
			BufferedWriter out = new BufferedWriter(fstream);

			out.write("Output for " + selectedAlgorithm);
			out.newLine();

			//Flagged Students x 3 - for both, just labs & just tuts
			// Flagged for both:
			out.write("Students Flagged for both Labs & Tutorials:\n");			
			for(Student s: output.getFlagged()){
				if(s.getFlaggedForLabs() && s.getFlaggedForTuts())
					out.write(s.getStudentNum() + "," + s.getName() + "\n");
			}
			// Flagged for labs:
			out.write("\nStudents Flagged for Labs:\n");			
			for(Student s: output.getFlagged()){
				if(s.getFlaggedForLabs() && !s.getFlaggedForTuts())
					out.write(s.getStudentNum() + "," + s.getName() + "\n");
			}
			// Flagged for tuts:
			out.write("Students Flagged for Tutorials:\n");			
			for(Student s: output.getFlagged()){
				if(!s.getFlaggedForLabs() && s.getFlaggedForTuts())
					out.write(s.getStudentNum() + "," + s.getName() + "\n");
			}

			/** Iterate through Timeslots twice times (labs & tuts) **/

			out.write("Assigned Students:\n");
			// Labs
			for(Timeslot t: output.keySet()){
				if(t instanceof Lab){
					out.write("Lab - " + t.toString()+"\n");
					for(Student s: t.getAssigned()){
						out.write(s.getStudentNum() + "," + s.getName() + "\n");
					}							
				}
			}

			// Tuts
			for(Timeslot t: output.keySet()){
				if(t instanceof Tutorial){
					out.write("Tutorial - " + t.toString()+"\n");
					for(Student s: t.getAssigned()){
						out.write( s.getStudentNum() + "," + s.getName() + "\n");
					}							
				}
			}

			//Close the output stream
			out.close();

		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	/** main method **/
	public static void main(String[] args) {
		GUI g = new GUI();
	}

	public void fileChosen() {
		try {
			int problemStudents = 0;

			labs = new File(LabFileTextField.getText());
			tuts = new File(TutFileTextField.getText());

			
			tutStudents = new ArrayList<Student>();
			//unansweredStudents = new ArrayList<Student>();

			boolean havelabs = false;
			boolean havetuts = false;
			if(labs.exists())
				havelabs = true;
			if(tuts.exists())
				havetuts = true;
			mergedStudents = new ArrayList<Student>();

			List<Student> naughtyStudents = new ArrayList<Student>();
			if(havelabs){			
				StudentDataParser labParser = new StudentDataParser(labs);		
				labsList = labParser.getLabs();
				labStudents = labParser.parseSelections(labsList, true);
				doBounds(labsList, true, havelabs, havetuts);

				//NaughtyList for Students with 1 or 0 choices
				for(Student s: labStudents){
					if((s.getFirstChoiceLabs().size() + s.getSecondChoiceLabs().size() + s.getThirdChoiceLabs().size()) < 2)
						naughtyStudents.add(s);
				}
				if(naughtyStudents.size() > 0){
					new NaughtyList(naughtyStudents, labsList, "Students with one/no labs selected").getStudents();
				}
				naughtyStudents = new ArrayList<Student>();	// Cleared so can use for tuts too
				problemStudents += labParser.getProblemStudents().size();
				if (problemStudents > 0)
					unansweredStudents.addAll(labParser.getProblemStudents());
			}

			if(havetuts){
				StudentDataParser tutParser = new StudentDataParser(tuts);
				tutorialsList = tutParser.getTutorials();
				tutStudents = tutParser.parseSelections(tutorialsList, false);
				doBounds(tutorialsList, false, havelabs, havetuts);
				//NaughtyList for Students with 1 or 0 choices
				for(Student s: tutStudents){
					if((s.getFirstChoiceTuts().size() + s.getSecondChoiceTuts().size() + s.getThirdChoiceTuts().size()) < 2)
						naughtyStudents.add(s);
				}
				if(naughtyStudents.size() > 0){

					new NaughtyList(naughtyStudents, tutorialsList, "Students with one/no tutorials selected").getStudents();

				}
				naughtyStudents = new ArrayList<Student>();

				problemStudents += tutParser.getProblemStudents().size();
				if (problemStudents > 0)
					unansweredStudents.addAll(tutParser.getProblemStudents());
				System.out.println("Num Unanswered"+unansweredStudents.size());
			}		
			if (tuts.exists() && labs.exists()){
				boolean tutInLabsArray[] = new boolean[tutStudents.size()];
				for(Student s: labStudents){
					mergedStudents.add(s.clone());
				}
				for(Student s: mergedStudents){
					for(int i = 0 ; i < tutStudents.size() ; i++){
						Student t = tutStudents.get(i);
						if( s.merge(t) ){ // student was in both tuts and labs
							//merge was successful
							tutInLabsArray[i] = true;
						}						
					}
				}

				for(int j = 0; j < tutInLabsArray.length; j++){
					if(!tutInLabsArray[j]){
						//is a student only in a tut (not a lab)
						mergedStudents.add(tutStudents.get(j));
					}
				}
			}else if (havetuts){
				mergedStudents = tutStudents;
			}else if (havelabs){
				mergedStudents = labStudents;
			}

			if(problemStudents > 0){
				unanswered.setText(problemStudents + " incomplete students");
				unanswered.setEnabled(true);
				unanswered.setVisible(true);
				frame.validate();
			}
			else {
				unanswered.setText("");
				unanswered.setEnabled(false);
				unanswered.setVisible(false);
				frame.validate();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void doRun(){
		ArrayList<Timeslot> labC = new ArrayList<Timeslot>();	// Array for labCanvas histocanvas
		ArrayList<Timeslot> tutC = new ArrayList<Timeslot>();	// Array for tutCanvas histocanvas
		for(Timeslot t : labsList){
			t.getAssigned().clear();
		}
		for(Timeslot t : tutorialsList){
			t.getAssigned().clear();
		}		

		List<Student> students = new ArrayList<Student>();

		//clones the mergedStudents so that the original student objects
		//do not get tampered with by any algorithms
		for(Student s : mergedStudents){
			students.add(s.clone());
		}

		if(selectedAlgorithm.equals("Boss Sort")){
			BossSort bs = new BossSort(new ArrayList<Timeslot>(labsList),new ArrayList<Timeslot>(tutorialsList),new ArrayList<Student>(students));
			output = bs.start();
		}
		else if(selectedAlgorithm.equals("Howard Sort")){
			HowardsSort hs = new HowardsSort(new ArrayList<Timeslot>(labsList),new ArrayList<Timeslot>(tutorialsList),new ArrayList<Student>(students));
			output = hs.start();
		}
		else if(selectedAlgorithm.equals("Cutting Sort")){
			CuttingSort cs = new CuttingSort(new ArrayList<Timeslot>(labsList),new ArrayList<Timeslot>(tutorialsList),new ArrayList<Student>(students));
			output = cs.start();
		}else if(selectedAlgorithm.equals("Permute Sort")){
			PermuSort ps = new PermuSort(new ArrayList<Timeslot>(labsList),new ArrayList<Timeslot>(tutorialsList),new ArrayList<Student>(students));
			output = ps.start();
		}
		
		// Assigning output timeslots to lab or tuts...
		for(Timeslot t: output.keySet()){
			if(t instanceof Lab)
				labC.add(t);
			else
				tutC.add(t);
		}
		// ...and creating histocanas for lab & tuts
		labCanvas.setTimeslots(labC);
		tutCanvas.setTimeslots(tutC);

		if(DEBUG){ System.out.println(output.fitnessValue()); } 
		String fitness = "Fitness - " + output.fitnessValue();
		//		for(String f: output.getFitness().keySet()){
		//			fitness += (f + " - " + output.getFitness().get(f) + "\n");
		//		}

		fit.setText(fitness);	
		naughtyCalculation();	
		frame.repaint();
	}

	protected void naughtyCalculation(){
		//Enable Naughty Lists Buttons if applicable
		if(labs.exists()){
			ArrayList<Student> studs = new ArrayList<Student>();
			for(Student s: output.getFlagged()){
				if(s.getFlaggedForLabs())
					studs.add(s);
			}
			flaggedLabs.setText("Flagged for Labs - " + studs.size());
			if(!studs.isEmpty())				
				flaggedLabsButton.setEnabled(true);
			else
				flaggedLabsButton.setEnabled(false);
		}
		if(tuts.exists()){
			ArrayList<Student> studs = new ArrayList<Student>();
			for(Student s: output.getFlagged()){
				if(s.getFlaggedForTuts())
					studs.add(s);
			}
			flaggedTuts.setText("Flagged for Tuts - " + studs.size());
			if(!studs.isEmpty())				
				flaggedTutsButton.setEnabled(true);
			else
				flaggedTutsButton.setEnabled(false);
		}
		frame.validate();
	}

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
			JPanel panel;
			if(isLabs)
				panel = innerLabsPanel;
			else
				panel = innerTutorialsPanel;
				
			// if this is the Labs bounds
			// or we dont have labs and are making the tuts bounds
			// create a title row.
//			if(isLabs || ((hasTuts && !hasLabs) && !isLabs) ){
				panel.removeAll();
				c.weightx = 0.5;
				c.gridx = 0;
				c.gridy = 0+startRow;
				panel.add(new JLabel("Session Name "));
				c.gridx = 1;
				c.gridy = 0+startRow;
				panel.add(new JLabel("Min      "));
				c.gridx = 2;
				c.gridy = 0+startRow;
				panel.add(new JLabel("Max      "));
				c.gridx = 3;
				c.gridy = 0+startRow;
				panel.add(new JLabel("Pref. Min"));

				c.gridx = 4;
				c.gridy = 0+startRow;
				panel.add(new JLabel("Pref. Max"));
//			}
			String sectionTitle = "";

			if(isLabs){
				sectionTitle = "LABS";
			} else {
				sectionTitle = "TUTS";
			}

			createTitleRow(panel, 3 + startRow, sectionTitle);

			for (int i = 0; i < slots.size(); i++) {
				Bounds timeslotBounds = new Bounds(slots.get(i));
				sessionBounds.add(timeslotBounds);
				// creates a set of input boxes in in the ith row, though
				// it starts two rows down to allow space for the title rows
				// (eg. Session Name)
				String slotTitle = slots.get(i).toString();
				timeslotBounds.createInputBoxes(panel, i + 4 + startRow, slotTitle);
			}
			if(isLabs)
				sessionBoundsLabs = sessionBounds;
			else
				sessionBoundsTuts = sessionBounds;
			
			
		}
		innerLabsPanel.setOpaque(false);
		innerTutorialsPanel.setOpaque(false);	
		
		labsPanel.setOpaque(false);
		tutorialsPanel.setOpaque(false);
		
		labsPanel.setPreferredSize(new Dimension(innerLabsPanel.getWidth(), (int) (heightOfBounds)));
		tutorialsPanel.setPreferredSize(new Dimension(innerTutorialsPanel.getWidth(), (int) (heightOfBounds)));
		frame.validate();
		frame.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Stub for implementing ActionListener
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
		// Stub for implementing ItemListener
	}

	// The bounds class is used by the GUI to store the bounds
	// given to it by the user for use in the algorithm of choice.
	// Each set of bounds has a maximum, a minimum, and a preferred max and min.
	public class Bounds  { 
 private final boolean DEBUG = false; 

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
				final JTextArea maxText = new JTextArea(timeslot.getMaxStudents()+"");
				final JTextArea prefMinText = new JTextArea(timeslot.getPreferredMin()+"");
				final JTextArea prefMaxText = new JTextArea(timeslot.getPreferredMax()+"");
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
										maxText.setForeground(Color.red);							

									} else{

										timeslot.setMinStudents(Integer
												.parseInt(minText.getText()));
										timeslot.setMaxStudents(Integer
												.parseInt(maxText.getText()));
										minText.setForeground(Color.black);
										maxText.setForeground(Color.black);
									}
								} catch (NumberFormatException nfe) {
								}
								if(DEBUG){ System.out.println(timeslot.getMinStudents()); } 
								if(DEBUG){ System.out.println(timeslot.getMaxStudents()); } 
								if(DEBUG){ System.out.println(timeslot.getPreferredMin()); } 
								if(DEBUG){ System.out.println(timeslot.getPreferredMax()); } 
							}
						});

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

										minText.setForeground(Color.red);
										maxText.setForeground(Color.red);

									} else
										timeslot.setMinStudents(Integer
												.parseInt(minText.getText()));
									timeslot.setMaxStudents(Integer
											.parseInt(maxText.getText()));
									minText.setForeground(Color.black);
									maxText.setForeground(Color.black);
								} catch (NumberFormatException nfe) {
								}
								if(DEBUG){ System.out.println(timeslot.getMinStudents()); } 
								if(DEBUG){ System.out.println(timeslot.getMaxStudents()); } 
								if(DEBUG){ System.out.println(timeslot.getPreferredMin()); } 
								if(DEBUG){ System.out.println(timeslot.getPreferredMax()); } 
							}

						});

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
										prefMaxText.setForeground(Color.red);
									} else{
										timeslot.setPreferredMin(Integer
												.parseInt(prefMinText.getText()));
										timeslot.setPreferredMax(Integer
												.parseInt(prefMaxText.getText()));
										prefMinText.setForeground(Color.black);
										prefMaxText.setForeground(Color.black);
									}
								} catch (NumberFormatException nfe) {

								}
								if(DEBUG){ System.out.println(timeslot.getMinStudents()); } 
								if(DEBUG){ System.out.println(timeslot.getMaxStudents()); } 
								if(DEBUG){ System.out.println(timeslot.getPreferredMin()); } 
								if(DEBUG){ System.out.println(timeslot.getPreferredMax()); } 
							}
						});

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

										prefMinText.setForeground(Color.red);
										prefMaxText.setForeground(Color.red);
									} else
										timeslot.setPreferredMin(Integer
												.parseInt(prefMinText.getText()));
									timeslot.setPreferredMax(Integer
											.parseInt(prefMaxText.getText()));
									prefMinText.setForeground(Color.black);
									prefMaxText.setForeground(Color.black);
								} catch (NumberFormatException nfe) {
								}
								if(DEBUG){ System.out.println(timeslot.getMinStudents()); } 
								if(DEBUG){ System.out.println(timeslot.getMaxStudents()); } 
								if(DEBUG){ System.out.println(timeslot.getPreferredMin()); } 
								if(DEBUG){ System.out.println(timeslot.getPreferredMax()); } 
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
	public JFrame getFrame() {
		return frame;
	}
}
