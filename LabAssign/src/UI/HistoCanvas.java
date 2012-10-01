package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class HistoCanvas extends JPanel implements MouseListener{
	private ArrayList<Timeslot> timeslots;
	private HashMap<Rectangle, Timeslot> rectangles;
	private JPanel results = new JPanel(); 		// For where selected TimeSlot displays

	public HistoCanvas(){
		this.addMouseListener(this);
		//addMouseListener(this);
		setPreferredSize(new Dimension(500, 200));
		
	}
	
	public void setTimeslots(List<Timeslot> in) {
		//TODO: Make this work with the AlgorithmOutput class.
		timeslots = new ArrayList<Timeslot>(in);
		recalculate();
		repaint();
	}

	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		histogram(g);
	}

	private void recalculate() {
		Rectangle bounds = this.getBounds();
		Dimension size = this.getPreferredSize();
		int width = size.width / timeslots.size();		
		int height = size.height;
		int largestSection = -Integer.MAX_VALUE;
		rectangles = new HashMap<Rectangle, Timeslot>();
		for (Timeslot t : timeslots) {
			if (t.getMaxStudents() > largestSection) {
				largestSection = t.getMaxStudents();
			}
		}
		int student = height / largestSection;
		for (int i = 0; i < timeslots.size(); i++) {
			Timeslot t = timeslots.get(i);
			rectangles.put(
					new Rectangle(i * width, height- (t.getMaxStudents() * student), width, t.getMaxStudents() * student), t);
		}
	}

	/** Histogram representation of all Timeslots */
	public void histogram(Graphics g) {
		if (this.rectangles == null) { // There's no data
			System.out.println("No data to plot histogram");
			return;
		}

		// Plots a GREEN bar for all timeslots within preferred range
		// Plots a YELLOW bar for all timeslots outside preferred range
		// Plots a RED bar for all timeslots below min/above max

		for (Rectangle r : rectangles.keySet()) {
			Timeslot s = rectangles.get(r);
			// Set color according to things
			if (s.getSize() < s.getRangeMin() || s.getSize() > s.getMaxStudents())
				g.setColor(Color.RED);
			else if (s.getSize() < s.getPreferredMin() || s.getSize() > s.getPreferredMax())
				g.setColor(Color.YELLOW);
			else
				g.setColor(Color.GREEN); 
				// If neither previous, should be within preferred range
			g.fillRect(r.x, r.y, r.width, r.height);
			g.setColor(Color.black);
			g.drawRect(r.x, r.y, r.width, r.height);
		}
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for(Rectangle r: rectangles.keySet()){
			if(r.contains(e.getPoint())){
				JDialog jD = new JDialog();
				jD.setTitle("Students in Session:");
				jD.setSize(200, 500);
				JPanel jP = new JPanel();
				jP.setLayout(new BoxLayout(jP, BoxLayout.Y_AXIS));
				
				String result =  rectangles.get(r).toString();
				JLabel jL = new JLabel(result);
				jP.add(jL);
				
				for(Student s: rectangles.get(r).getAssigned()){
					result = "" + s.getStudentNum();
					jL = new JLabel(result);
					jP.add(jL);
				}
				jD.add(jP);
				jD.setVisible(true);
				break;
			}
		}		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Just for MouseListener implementation		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Just for MouseListener implementation
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Just for MouseListener implementation		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Just for MouseListener implementation		
	}
}