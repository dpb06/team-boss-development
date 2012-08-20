package UI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;

//extending lightweight components doesn't work on Windows
//extending heavyweight components such as Canvas has a problem
//  if the canvas is in a scroll pane with horizontal scrolling -
//  the canvas will end up scrolling over the top of other components.
//  ==> If you use a Canvas, you must not allow scrolling.

class Comp102Canvas extends Canvas {
  private Image imgBuf;
  private Graphics imgGraphic, visibleGraphic;

  /** Maximum width of the canvas */
  public static final int MaxX = 1024;
  /** Maximum height of the canvas */
  public static final int MaxY = 768;

  public void addNotify() {
    super.addNotify();
    this.setBackground(Color.white);
    imgBuf = createImage(MaxX, MaxY);  // Can only be done by peer
    imgGraphic = imgBuf.getGraphics();
    imgGraphic.setPaintMode();
    imgGraphic.setColor(Color.black);
    visibleGraphic = this.getGraphics();
    clear(false);
  }

    /*
  private int printCount = 0;

  public void print(Graphics g) {
    super.print(g);
  }

    */


  public void paint(Graphics g) {
    g.drawImage(imgBuf, 0, 0, null);
  }

  /** Set the current font size */
  public void setFontSize(int size) {
    imgGraphic.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, size));
  }

  public void update(Graphics g) {  // Stops component being cleared
    paint(g);
  }

  public Dimension getPreferredSize() {
    return new Dimension(Math.min(640, MaxX), Math.min(480, MaxY));
  }

  public Dimension getMaximumSize() {
    return new Dimension(MaxX, MaxY);
  }

  /** Redisplay the canvas area, including all the shapes that have not yet been 
    * redisplayed . 
    */
  public void display() {
    Dimension d = getSize();
    display(0, 0, d.width, d.height);
  }

  public void display(int x, int y, int width, int height) {
    java.awt.Shape clip = visibleGraphic.getClip();
    visibleGraphic.setClip(x, y, width+1, height+1);
    visibleGraphic.drawImage(imgBuf, 0, 0, null);
    visibleGraphic.setClip(clip);
    repaint();   // added?
  }

  /** Get the Graphics object that is the backing store of the image, so that
      programs can do more complicated operations on the image than are
      provided by this class.   
      <BR>
      Standard usage would be to get the graphics object, call methods on it,
      and then call the display() method on the Canvas to update the 
      visible imagewith the modifications.*/
  public Graphics getBackingGraphics() {
    return imgGraphic;
  }

  /** Clear the canvas area. */
  public void clear(boolean redraw) {
    Color save = imgGraphic.getColor();
    imgGraphic.setColor(Color.white);
    imgGraphic.fillRect(0, 0, MaxX, MaxY);
    imgGraphic.setColor(save);
    if (redraw) { display(); }
  }

    /** Set the current foreground color - the color for all subsequent shapes or 
    * text 
    */
    public void setColor(Color c) {
	imgGraphic.setColor(c);
	//super.setForeground(c);
  }

  public void drawLine(int x1, int y1, int x2, int y2, boolean redraw) {
    imgGraphic.drawLine(x1, y1, x2, y2);
    if (redraw){
      display(Math.min(x1, x2), Math.min(y1, y2), 
              Math.abs(x2-x1)+1, Math.abs(y2-y1)+1);
    }
  }

    /** Invert the line between (x1, y1) and (x2, y2). */
  public void invertLine(int x1, int y1, int x2, int y2, boolean redraw) {
    imgGraphic.setXORMode(Color.white);
    imgGraphic.drawLine(x1, y1, x2, y2);
    imgGraphic.setPaintMode();
    if (redraw)
      display(Math.min(x1, x2), Math.min(y1, y2), 
              Math.abs(x2 - x1) + 1, Math.abs(y2 - y1) + 1);
  }

    /** Erase the line between (x1, y1) and (x2, y2). */
  public void eraseLine(int x1, int y1, int x2, int y2, boolean redraw) {
      Color save = imgGraphic.getColor();
      imgGraphic.setColor(Color.white);
      imgGraphic.drawLine(x1, y1, x2, y2);
      imgGraphic.setColor(save);
      if (redraw)
	  display(Math.min(x1, x2), Math.min(y1, y2), 
		  Math.abs(x2 - x1) + 1, Math.abs(y2 - y1) + 1);
  }

  public void drawRect(int x, int y, int width, int height, boolean redraw) {
    imgGraphic.drawRect(x, y, width-1, height-1);
    if (redraw)
      display(x, y, width, height);
  }

  public void fillRect(int x, int y, int width, int height, boolean redraw) {
    imgGraphic.fillRect(x, y, width, height);
    if (redraw)
      display(x, y, width, height);
  }

  public void invertRect(int x, int y, int width, int height, boolean redraw) {
    imgGraphic.setXORMode(Color.white);
    imgGraphic.drawRect(x, y, width-1, height-1);
    imgGraphic.setPaintMode();
    if (redraw)
      display(x, y, width, height);
  }


  public void eraseRect(int x, int y, int width, int height, boolean redraw) {
    Color save = imgGraphic.getColor();
    imgGraphic.setColor(Color.white);
    imgGraphic.fillRect(x, y, width, height);
    imgGraphic.setColor(save);
    if (redraw)
      display(x, y, width, height);
  }

  public void drawString(String s, int x, int y, boolean redraw) {
    imgGraphic.drawString(s, x, y);
    if (redraw) {
      FontMetrics fm = imgGraphic.getFontMetrics();
      display(x, y-fm.getMaxAscent(), 
              fm.stringWidth(s)+fm.getMaxAdvance(),
              fm.getMaxAscent()+fm.getMaxDescent());
    }
  }

  public void invertString(String s, int x, int y, boolean redraw) {
    imgGraphic.setXORMode(Color.white);
    imgGraphic.drawString(s, x, y);
    imgGraphic.setPaintMode();
    if (redraw) {
      FontMetrics fm = imgGraphic.getFontMetrics();
      display(x, y-fm.getMaxAscent(), 
              fm.stringWidth(s)+fm.getMaxAdvance(),
              fm.getMaxAscent()+fm.getMaxDescent());
    }
  }

  public void eraseString(String s, int x, int y, boolean redraw) {
      Color save = imgGraphic.getColor();
      imgGraphic.setColor(Color.white);
      imgGraphic.drawString(s, x, y);
      imgGraphic.setColor(save);
      if (redraw) {
	  FontMetrics fm = imgGraphic.getFontMetrics();
	  display(x, y-fm.getMaxAscent(), 
		  fm.stringWidth(s)+fm.getMaxAdvance(),
		  fm.getMaxAscent()+fm.getMaxDescent());
      }
  }

  public void drawOval(int x, int y, int width, int height, boolean redraw) {
    imgGraphic.drawOval(x, y, width-1, height-1);
    if (redraw)
      display(x, y, width, height);
  }

  public void fillOval(int x, int y, int width, int height, boolean redraw) {
    imgGraphic.fillOval(x, y, width, height);
    imgGraphic.drawOval(x, y, width-1, height-1);
    if (redraw)
      display(x, y, width, height);
  }

  public void invertOval(int x, int y, int width, int height, boolean redraw) {
    imgGraphic.setXORMode(Color.white);
    imgGraphic.drawOval(x, y, width-1, height-1);
    imgGraphic.setPaintMode();
    if (redraw)
      display(x, y, width, height);
  }

  public void eraseOval(int x, int y, int width, int height, boolean redraw) {
      Color save = imgGraphic.getColor();
      imgGraphic.setColor(Color.white);
      imgGraphic.drawOval(x, y, width-1, height-1);
      imgGraphic.fillOval(x, y, width, height);
      imgGraphic.setColor(save);
      if (redraw)
	  display(x, y, width, height);
  }

  public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints, boolean redraw) {
      Polygon poly = new Polygon(xPoints, yPoints, nPoints);
      imgGraphic.drawPolygon(poly);
      if (redraw){
	  Rectangle bounds = poly.getBounds();
	  display(bounds.x, bounds.y, bounds.width, bounds.height);
      }
  }

  public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints, boolean redraw) {
      Polygon poly = new Polygon(xPoints, yPoints, nPoints);
      imgGraphic.fillPolygon(poly);
      if (redraw){
	  Rectangle bounds = poly.getBounds();
	  display(bounds.x, bounds.y, bounds.width, bounds.height);
      }
  }

  public void invertPolygon(int[] xPoints, int[] yPoints, int nPoints, boolean redraw) {
    imgGraphic.setXORMode(Color.white);
      Polygon poly = new Polygon(xPoints, yPoints, nPoints);
      imgGraphic.drawPolygon(poly);
      imgGraphic.setPaintMode();
      if (redraw){
	  Rectangle bounds = poly.getBounds();
	  display(bounds.x, bounds.y, bounds.width, bounds.height);
      }
  }

  public void erasePolygon(int[] xPoints, int[] yPoints, int nPoints, boolean redraw) {
      Color save = imgGraphic.getColor();
      imgGraphic.setColor(Color.white);
      Polygon poly = new Polygon(xPoints, yPoints, nPoints);
      imgGraphic.fillPolygon(poly);
      imgGraphic.setColor(save);
      if (redraw){
	  Rectangle bounds = poly.getBounds();
	  display(bounds.x, bounds.y, bounds.width, bounds.height);
      }
  }

  public void drawArc(int x, int y, int width, int height, 
                      int startAngle, int arcAngle, boolean redraw) {
    imgGraphic.drawArc(x, y, width-1, height-1, startAngle, arcAngle);
    if (redraw)
      display(x, y, width, height);
  }

  public void fillArc(int x, int y, int width, int height, 
                      int startAngle, int arcAngle, boolean redraw) {
    imgGraphic.fillArc(x, y, width, height, startAngle, arcAngle);
    imgGraphic.drawArc(x, y, width-1, height-1, startAngle, arcAngle);
    if (redraw)
      display(x, y, width, height);
  }

  public void invertArc(int x, int y, int width, int height, 
                      int startAngle, int arcAngle, boolean redraw) {
    imgGraphic.setXORMode(Color.white);
    imgGraphic.drawArc(x, y, width-1, height-1, startAngle, arcAngle);
    imgGraphic.setPaintMode();
    if (redraw)
      display(x, y, width, height);
  }

  public void eraseArc(int x, int y, int width, int height, 
                      int startAngle, int arcAngle, boolean redraw) {
      Color save = imgGraphic.getColor();
      imgGraphic.setColor(Color.white);
      imgGraphic.drawArc(x, y, width-1, height-1, startAngle, arcAngle);
      imgGraphic.fillArc(x, y, width, height, startAngle, arcAngle);
      imgGraphic.setColor(save);
      if (redraw)
	  display(x, y, width, height);
  }


    /* from file, scaled*/
    public void drawImage(String name, int x, int y, int width, int height, 
                        boolean redraw) {
    File fh = new File(name);
    if (fh.canRead()) {
      MediaTracker media = new MediaTracker(this);
      Image img = Toolkit.getDefaultToolkit().getImage(name);
      media.addImage(img, 0);
      try {media.waitForID(0);} catch (Exception e) {}
      imgGraphic.drawImage(img, x, y, width, height, this.getBackground(), this);
    } else {
      // The file either doesn't exist or we don't have read access
      imgGraphic.drawRect(x, y, width, height);
      imgGraphic.drawLine(x, y, x+width, y+height);
      imgGraphic.drawLine(x+width, y, x, y+height);
    }
    if (redraw)
      display();
  }

    /* from file, unscaled*/
    public void drawImage(String name, int x, int y, boolean redraw) {
    File fh = new File(name);
    if (fh.canRead()) {
	Image img;
	MediaTracker media = new MediaTracker(this);
	img = Toolkit.getDefaultToolkit().getImage(name);
	media.addImage(img, 0);
	try {media.waitForID(0);} catch (Exception e) {}
	imgGraphic.drawImage(img, x, y, this.getBackground(), this);
    } else {
      // The file either doesn't exist or we don't have read access
      imgGraphic.drawRect(x, y, 10, 10);
      imgGraphic.drawLine(x, y, x+10, y+10);
      imgGraphic.drawLine(x+10, y, x, y+10);
    }
    if (redraw)
      display();
  }

    /* from Image, scaled*/
    public void drawImage(Image img, int x, int y, int width, int height,
			  boolean redraw) {
	imgGraphic.drawImage(img, x, y, width, height, this.getBackground(), this);
	if (redraw)
	    display();
  }

    /* from Image, unscaled*/
    public void drawImage(Image img, int x, int y, boolean redraw) {
	imgGraphic.drawImage(img, x, y, this.getBackground(), this);
	if (redraw)
	    display();
  }

    public void eraseImage(String name, int x, int y, boolean redraw) {
    File fh = new File(name);
    if (fh.canRead()) {
	Image img;
	MediaTracker media = new MediaTracker(this);
	img = Toolkit.getDefaultToolkit().getImage(name);
	media.addImage(img, 0);
	try {media.waitForID(0);} catch (Exception e) {}
	int width = img.getWidth(this);
	int height = img.getHeight(this);
	Color save = imgGraphic.getColor();
	imgGraphic.setColor(Color.white);
	imgGraphic.fillRect(x, y, width+1, height+1);
	imgGraphic.setColor(save);
    } 
    if (redraw)
      display();
  }

    public void eraseImage(Image img, int x, int y, boolean redraw) {
	int width = img.getWidth(this);
	int height = img.getHeight(this);
	Color save = imgGraphic.getColor();
	imgGraphic.setColor(Color.white);
	imgGraphic.fillRect(x, y, width+1, height+1);
	imgGraphic.setColor(save);
	if (redraw)
	    display();
  }


}
