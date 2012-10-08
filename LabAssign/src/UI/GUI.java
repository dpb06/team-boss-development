package UI;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
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
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;
import algorithms.BossSort;
import algorithms.CuttingSort;
import algorithms.HowardsSort;
import dataParsing.StudentDataParser;

/**
 * @author Haydn Newport and Barbara MacKenzie
 */
public class GUI extends JFrame implements ActionListener, ItemListener {
	// Parameters
	private JFrame frame;
	private JPanel eastPanel;
	private JMenuBar menuBar;
	// private Graphics g;
	private int NUM_SESSIONS = 12;

	private JTextArea textArea;
	private final JTextField fileText;
	private HistoCanvas canvas;
	private ArrayList<Bounds> SessionBounds = new ArrayList<Bounds>();
	boolean alreadyRUN = false;
	
	private String selectedAlgorithm = "Boss Sort";

	// currently this sets up all the graphical user interface. I'll later break
	// it up into component methods
	public GUI() {
		frame = new JFrame();
		BorderLayout l = new BorderLayout(4, 4);
		frame.setLayout(l);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setVisible(false);

		// call method to create the menu
		createMenu();

		JPanel fileAlgoPanel = new JPanel();
		fileAlgoPanel.setLayout(new BoxLayout(fileAlgoPanel, BoxLayout.X_AXIS));
		int width = 500;
		int height = 200;
		fileAlgoPanel.setSize(width, height);

		fileText = new JTextField("File Name here....", 10);
		fileText.setMaximumSize(new Dimension(500, 20));
		
		JButton fileButton = new JButton("Browse");
		fileButton.setBounds(0, 0, 50, 20);
		
		final JButton runButton = new JButton("Run");
        runButton.setEnabled(false);
        
		fileButton.addActionListener(new ActionListener() {			 
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Make this start in the text field path
                //Handle 'Browse' button action.
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(GUI.this);
 
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    fileText.setText(fc.getSelectedFile().getAbsolutePath());
                    runButton.setEnabled(true);
                }
            }
        });
		
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doRun();
			}
		});
		
		fileAlgoPanel.add(new JLabel("File    "));
		fileAlgoPanel.add(fileText);
		fileAlgoPanel.add(fileButton);

		// RadioButtons for Algorithm Selection
		JPanel algoSelect = new JPanel();
		algoSelect.setLayout(new BoxLayout(algoSelect, BoxLayout.Y_AXIS));
		 // This array contains all algorithm options
        String[] algoriths = {"Boss Sort", "Howard Sort", "Cutting Sort"};
        JComboBox algoGroup = new JComboBox(algoriths);
 
 
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
        fileAlgoPanel.add(algoSelect);
        fileAlgoPanel.add(runButton);

		JPanel canvasPanel = new JPanel();
		Dimension d = new Dimension(frame.getWidth() - 100, 500);
		canvasPanel.add(new Box.Filler(d, d, d));
		canvas = new HistoCanvas();
		// canvas.setBackground(Color.red);
		canvasPanel.add(canvas);
		frame.add(canvasPanel, BorderLayout.CENTER);
		textArea = new JTextArea(1, 4);
		textArea.setEditable(true);
		// Finish the panel, pack and display
		eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setLayout(new GridBagLayout());
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.add(fileAlgoPanel);
		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(eastPanel, BorderLayout.EAST);
		
		frame.setPreferredSize(new Dimension(800, 600));
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

	public void doRun() {
		try {
			StudentDataParser parser = new StudentDataParser(new File(
					fileText.getText()));
			List<Timeslot> slots = parser.getTimeslots();
			List<Student> students = parser.parseSelections(slots);
			if (SessionBounds.size() != slots.size() || !alreadyRUN) {
				alreadyRUN = true;
				// recreate bounds array

				SessionBounds = new ArrayList<Bounds>();
				GridBagConstraints c = new GridBagConstraints();
				c.insets = new Insets(1, 1, 1, 1);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 0.5;
				c.gridx = 0;
				c.gridy = 0;
				eastPanel.add(new JLabel("Session"));
				c.gridy = 1;
				eastPanel.add(new JLabel("Name"));
				c.gridx = 1;
				c.gridy = 0;
				eastPanel.add(new JLabel("Min"));
				c.gridx = 2;
				c.gridy = 0;
				eastPanel.add(new JLabel("Max"));
				c.gridx = 3;
				c.gridy = 0;
				eastPanel.add(new JLabel("Pref."));
				c.gridy = 1;
				eastPanel.add(new JLabel("Min"));
				c.gridx = 4;
				c.gridy = 0;
				eastPanel.add(new JLabel("Pref."));
				c.gridy = 1;
				eastPanel.add(new JLabel("Max"));
				for (int i = 0; i < slots.size(); i++) {
					Bounds timeslotBounds = new Bounds(slots.get(i));
					SessionBounds.add(timeslotBounds);
					// creates a set of input boxes in in the ith row, though
					// it starts two rows down to allow space for the title rows
					// (eg. Session Name)
					timeslotBounds.createInputBoxes(eastPanel, i + 2,
							"Session " + (i + 1));
				}
			}
			frame.validate();
			if(selectedAlgorithm.equals("Boss Sort")){
                BossSort bs = new BossSort(new ArrayList<Timeslot>(slots),new ArrayList<Timeslot>(),new ArrayList<Student>(students));
                canvas.setTimeslots(new ArrayList<Timeslot>(bs.start().keySet()));
			}
			else if(selectedAlgorithm.equals("Howard Sort")){
                HowardsSort hs = new HowardsSort(new ArrayList<Timeslot>(slots),new ArrayList<Timeslot>(),new ArrayList<Student>(students));
                canvas.setTimeslots(new ArrayList<Timeslot>(hs.start().keySet()));
            }
            else if(selectedAlgorithm.equals("Cutting Sort")){
                CuttingSort cs = new CuttingSort(new ArrayList<Timeslot>(slots),new ArrayList<Timeslot>(),new ArrayList<Student>(students));
                canvas.setTimeslots(new ArrayList<Timeslot>(cs.start().keySet()));
            }
			
			frame.repaint();
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
				final JTextArea minText = new JTextArea("0");
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
									if (Integer.parseInt(minText.getText()) < 0) {
										timeslot.setMinStudents(0);
										SwingUtilities
												.invokeLater(new Runnable() {
													@Override
													public void run() {
														minText.setText(0 + "");
													}
												});
									} else
										timeslot.setMinStudents(Integer
												.parseInt(minText.getText()));
								} catch (NumberFormatException nfe) {
								}
							}
						});
				final JTextArea maxText = new JTextArea("20");
				timeslot.setMaxStudents(20);
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
									if (Integer.parseInt(maxText.getText()) <= timeslot
											.getMinStudents()) {

										timeslot.setMaxStudents(timeslot
												.getMinStudents() + 1);
										// resets the text, but cannot do so
										// within the listener.
										// Swing Utilities invoke later is a
										// work around
										SwingUtilities
												.invokeLater(new Runnable() {
													@Override
													public void run() {
														maxText.setText((timeslot
																.getMaxStudents())
																+ "");
													}
												});
									} else
										timeslot.setMaxStudents(Integer
												.parseInt(maxText.getText()));
								} catch (NumberFormatException nfe) {
								}
							}
						});
				final JTextArea prefMinText = new JTextArea("0");
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
									if (Integer.parseInt(prefMinText.getText()) < 0) {
										timeslot.setPreferredMin(0);
										// resets the text, but cannot do so
										// within the listener.
										// Swing Utilities invoke later is a
										// work around
										SwingUtilities
												.invokeLater(new Runnable() {
													@Override
													public void run() {
														prefMinText
																.setText(0 + "");
													}
												});
									} else
										timeslot.setPreferredMin(Integer
												.parseInt(prefMinText.getText()));
								} catch (NumberFormatException nfe) {

								}
							}
						});
				final JTextArea prefMaxText = new JTextArea("20");
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
									if (Integer.parseInt(prefMaxText.getText()) <= timeslot
											.getPreferredMin()) {
										timeslot.setPreferredMax(timeslot
												.getPreferredMin() + 1);
										// resets the text, but cannot do so
										// within the listener.
										// Swing Utilities invoke later is a
										// work around
										// this runs after we leave the listener
										SwingUtilities
												.invokeLater(new Runnable() {
													@Override
													public void run() {
														prefMaxText.setText((timeslot
																.getPreferredMax())
																+ "");
													}
												});
									} else
										timeslot.setPreferredMax(Integer
												.parseInt(prefMaxText.getText()));
								} catch (NumberFormatException nfe) {
								}
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
	}

	public void addBoundsInputs() {
	}
}