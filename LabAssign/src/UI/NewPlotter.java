package UI;

import algorithmDataStructures.Student;
import comp102.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NewPlotter extends Canvas implements MouseListener{//implements UIButtonListener, MouseListener, UIMouseListener{
	// Fields
	private ArrayList<Section> sections = new ArrayList<Section>();
	private Graphics g;
	
	private final int graphLeft	  = 10;
	private final int graphTop    = 10;
	private final int graphWidth  = 500;
	private final int graphHeight = 450;
	private final int graphBot 	  = graphTop  + graphHeight;

	private int columnWidth;
	private int largestSection = -1;
	
	public NewPlotter(Map<String, List<Student>> data, Map<String, int[]> thresholds, Graphics g){
		this.g = g;
		for(String s: data.keySet()){
			Section newSection = new Section(s, data.get(s), thresholds.get(s));
			if(newSection.getSize() > largestSection)
				largestSection = newSection.getSize();
			sections.add(newSection);
		}
		columnWidth = graphWidth/sections.size();
	}

	
	/** Histogram representation of all Sections		*/
	public void histogram(){
		if (this.sections==null) {	// There's no data
			System.out.println("No data to plot histogram");
			return;
		}

		int ratio = graphHeight/largestSection;
		int x = graphLeft;
		int y = graphBot;
		int drop = 5;
		int change = 10;


		// Plots a GREEN  bar for all sections within  preferred range
		// Plots a YELLOW bar for all sections outside preferred range
		// Plots a RED    bar for all sections below min/above max    

		for (Section s : sections) {

			int columnHeight = s.getSize() * ratio;	// Size of bar

			if (s.getSize() < s.getMin() || s.getSize() > s.getMax())	// If below min/above max
				g.setColor(Color.RED);
			else if (s.getSize() < s.getPrefMin() || s.getSize() > s.getPrefMax()) 
				g.setColor(Color.YELLOW);
			else
				g.setColor(Color.GREEN);	// If neither previous, should be within preferred range

			g.fillRect(x, y - columnHeight, columnWidth, columnHeight);

			g.setColor(Color.black);
			g.drawRect(x, y - columnHeight, columnWidth, columnHeight);
			g.drawLine(x, y, x, y + 5);
			g.drawLine(x + columnWidth, y, x + columnWidth, y + 5);

			s.setLeft(x);
			s.setTop(y - columnHeight);
			s.setWidth(columnWidth);
			s.setHeight(columnHeight);
			
			g.drawString(s.getSectionTime(), x, y + drop);
			drop += change;
			//if(drop >= 40 || drop <= -40)
			//	change = -1 * change;
			x += columnWidth;
		}
	}

	// Main
	public static void main(String[] arguments){
		ArrayList<String> sects = new ArrayList<String>();
		sects.add("Monday 10:00 - 12:50");
		sects.add("Monday13:10 - 16:00");
		sects.add("Monday16:10 - 19:00");
		sects.add("Tuesday9:00 -11:50");
		sects.add("Tuesday13:10 - 16:00");
		sects.add("Tuesday16:10 - 19:00");
		sects.add("Wednesday 11:00 - 12:50");
		sects.add("Wednesday 10:10 - 16:00");
		sects.add("Wednesday 16:10 - 19:00");
		sects.add("Thursday9:00 -11:50");
		sects.add("Thursday13:10 - 16:00");
		sects.add("Thursday16:10 - 19:00");

		Map<String, List<Student>> test = new HashMap <String, List<Student>>();	// String Section -> List of Students
		Map<String, int[]> thresholds = new HashMap <String, int[]>();		// String Section -> [min, prefMin, prefMax, max]
		for(String s: sects){
			ArrayList<Student> studs = new ArrayList<Student>();
			String name = "namer";
			for(int i = 0; i < (Math.random() * 50); i ++){
				int UID = (int)(Math.random() * 1000);
				String fname = (name.charAt((int) (Math.random() * 5)) + name);
				String lname = (name.charAt((int) (Math.random() * 5)) + name);
				int sNumb = (int)(Math.random() * 6);
				Student stud = new Student (UID, fname, lname, sNumb);
				studs.add(stud);
			}
			test.put(s, studs);

			int minPref = 5 + (int)(Math.random() * 10);
			int maxPref = 15 + (int)(Math.random() * 20);
			int max = 35 + (int)(Math.random() * 10);
			int[] threshold = {5, minPref, maxPref, max};

			thresholds.put(s, threshold);
		}
		NewPlotter p = new NewPlotter(test, thresholds, UI.getCanvas());
		p.histogram();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for(Section s : sections){
			if(s.hoverOver(e.getX(), e.getY())){
				System.out.println(s.toString());
			}
		}	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// For MouseListener Implementation Only

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(Section s : sections){
			if(s.hoverOver(e.getX(), e.getY())){
				System.out.println(s.toString());
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// For MouseListener Implementation Only
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// For MouseListener Implementation Only
	}  
}