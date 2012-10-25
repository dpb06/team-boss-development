package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class HistoCanvas extends JPanel implements MouseListener {
	private ArrayList<Timeslot> timeslots;
	private HashMap<Rectangle, Timeslot> rectangles;
	private int scaleTop = 0;
	private JPanel results = new JPanel(); // For where selected TimeSlot displays
	private final int bottomOffset = 70;
	//The rightoffset allows the slanted labels to be shown
	private final int rightOffset = 30;
	private final int leftOffset = 30;
	private final int NUM_LABELS = 10;
	

	public HistoCanvas() {
		this.addMouseListener(this);
		// addMouseListener(this);
		setPreferredSize(new Dimension(600, 350));

	}

	public void setTimeslots(List<Timeslot> in) {
		timeslots = new ArrayList<Timeslot>(in);
		recalculate();
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		recalculate();
		histogram(g);
	}

	private void recalculate() {
		Dimension size = this.getPreferredSize();
		if (timeslots == null || timeslots.size() <= 0)
			return;
		Collections.sort(timeslots);
		int width = (size.width - rightOffset - leftOffset) / timeslots.size();
		int height = size.height - bottomOffset;
		int largestSection = Integer.MIN_VALUE;
		rectangles = new HashMap<Rectangle, Timeslot>();
		for (Timeslot t : timeslots) {
			if (t.getAssigned().size() > largestSection) {
				largestSection = t.getAssigned().size();
			}
		}
		if (largestSection <= 0)
			return;
		int student = height / largestSection;
		scaleTop = largestSection;
		for (int i = 0; i < timeslots.size(); i++) {
			Timeslot t = timeslots.get(i);
			rectangles.put(new Rectangle(i * width + leftOffset, height
					- (t.getAssigned().size() * student), width, t
					.getAssigned().size() * student), t);
		}
	}

	/** Histogram representation of all Timeslots */
	public void histogram(Graphics g) {
		g.drawLine(rightOffset, 0, rightOffset, getHeight() - bottomOffset);
		g.drawLine(rightOffset, getHeight() - bottomOffset, getWidth()-rightOffset, getHeight() - bottomOffset);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		((Graphics2D) g).setFont(new Font("Sans serif", 0, 9));
		if (this.rectangles == null) { // There's no data
			System.out.println("No data to plot histogram");
			return;
		}
		double valuesGap = scaleTop/(double)NUM_LABELS;
		double pixelGap = (scaleTop/(double)NUM_LABELS)*((getHeight()-bottomOffset)/scaleTop);
		for (int i = 0; i<NUM_LABELS+1; i++){
			g.drawLine(leftOffset, (int)Math.round(getHeight()-bottomOffset-(i*pixelGap)), leftOffset-leftOffset/3, (int)Math.round(getHeight()-bottomOffset-(i*pixelGap)));
			g.drawString(""+Math.round(valuesGap*i), leftOffset-20, (int)Math.round(getHeight()-bottomOffset-(i*pixelGap)+5));
		}

		// Plots a GREEN bar for all timeslots within preferred range
		// Plots a YELLOW bar for all timeslots outside preferred range
		// Plots a RED bar for all timeslots below min/above max

		for (Rectangle r : rectangles.keySet()) {
			Timeslot s = rectangles.get(r);
			// Set color according to things
			if (s.getAssigned().size() < s.getMinStudents()
					|| s.getAssigned().size() > s.getMaxStudents())
				g.setColor(Color.RED);
			else if (s.getAssigned().size() < s.getPreferredMin()
					|| s.getAssigned().size() > s.getPreferredMax())
				g.setColor(Color.YELLOW);
			else
				g.setColor(Color.GREEN);
			// If neither previous, should be within preferred range
			g.fillRect(r.x, r.y, r.width, r.height);
			g.setColor(Color.black);
			g.drawRect(r.x, r.y, r.width, r.height);
			((Graphics2D) g).rotate(Math.PI / 6);
			Point textPoint = new Point(r.x + r.width / 2, r.y + r.height + 10);
			Point newPos = new Point();
			try {
				((Graphics2D) g).getTransform().createInverse()
						.deltaTransform(textPoint, newPos);
				((Graphics2D) g).drawString(s.toString(), newPos.x, newPos.y);
				((Graphics2D) g).rotate(-Math.PI / 6);
			} catch (NoninvertibleTransformException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void validate() {
		super.validate();
		recalculate();
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (rectangles != null)
			for (Rectangle r : rectangles.keySet()) {
				if (r.contains(e.getPoint())) {
					final JDialog jD = new JDialog();
					jD.setTitle("Students in Session:");
					//jD.setSize(200, 500);
					JPanel jP = new JPanel();
					jP.setLayout(new BoxLayout(jP, BoxLayout.Y_AXIS));

					String result = rectangles.get(r).toString();
					JLabel jL = new JLabel(result);
					jP.add(jL);

					for (Student s : rectangles.get(r).getAssigned()) {
						result = s.toString();
						jL = new JLabel(result);
						jP.add(jL);
					}
					jD.add(jP);
					JButton exit = new JButton("Exit");
					exit.addActionListener(new ActionListener() {						
						@Override
						public void actionPerformed(ActionEvent e) {
							jD.setVisible(false);
							jD.dispose();
						}
					});
					jP.add(exit);
					jD.pack();
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
