package UI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import algorithmDataStructures.Timeslot;

import comp102.UI;

public class HistoCanvas extends Canvas {
	private ArrayList<Timeslot> sections;
	private HashMap<Rectangle, Timeslot> rectangles;

	public void setSections(List<Timeslot> in) {
		sections = new ArrayList<Timeslot>(in);
		recalculate();
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		histogram(g);
	}

	private void recalculate() {
		int width = this.getWidth() / sections.size();
		int height = this.getHeight();
		int largestSection = -Integer.MAX_VALUE;
		for (Timeslot t : sections) {
			if (t.getMaxStudents() > largestSection) {
				largestSection = t.getMaxStudents();
			}
		}
		int student = height / largestSection;
		for (int i = 0; i < sections.size(); i++) {
			Timeslot t = sections.get(i);
			rectangles.put(
					new Rectangle(i * width, height
							- (t.getMaxStudents() * student), width, t
							.getMaxStudents() * student), t);
		}
	}

	/** Histogram representation of all Sections */
	public void histogram(Graphics g) {
		if (this.rectangles == null) { // There's no data
			UI.println("No data to plot histogram");
			return;
		}

		// Plots a GREEN bar for all sections within preferred range
		// Plots a YELLOW bar for all sections outside preferred range
		// Plots a RED bar for all sections below min/above max

		for (Rectangle r : rectangles.keySet()) {
			Timeslot s = rectangles.get(r);
			// Set color according to things
			if (s.getSize() < s.getRangeMin()
					|| s.getSize() > s.getMaxStudents())
				g.setColor(Color.RED);
			else if (s.getSize() < s.getRangePrefLow()
					|| s.getSize() > s.getRangePrefHigh())
				g.setColor(Color.YELLOW);
			else
				g.setColor(Color.GREEN); // If neither previous, should be
											// within preferred range
			g.drawRect(r.x, r.y, r.width, r.height);
		}
	}
}
