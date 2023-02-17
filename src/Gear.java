import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
//import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Gear extends JPanel implements MouseMotionListener {

    // image that represents the gear's position on the board
    private BufferedImage image;
    private Image scaledImage;
    private BufferedImage finalImage;
    // current position of the gear on the board grid
    private Point pos;
    // variables of spinning
    private double drawingAngle = 0;
    private double speed = 0;
    private Boolean clockWise = true;
    // other variables of gear
    private int radius = 25;
    // draggable gear
    private int startDragX, startDragY;
    private boolean draggable = false, dragging = false, dragged = false;
    double mouseXPos = MouseInfo.getPointerInfo().getLocation().getX();
    double mouseYPos = MouseInfo.getPointerInfo().getLocation().getY();

    /**
     * <p>
     * a new Gear object- made for gear simulator by Mochatitan.
     * 
     * @param gpos  Point of where the gear midpoint is.
     * @param gsize the Radius of the gear
     *
     */
    public Gear(Point gpos, int gradius) { // Point constructor

        // initialize the state

        pos = gpos;
        if (radius == 75) {
            pos.translate(-50, -50);
        }
        radius = gradius;

        // load the assets
        loadImage();
        this.addMouseMotionListener(this);

    }

    /**
     * <p>
     * a new Gear object- made for gear simulator by Mochatitan.
     * <p>
     * this version will create a gear of default size 1/small.
     * 
     * @param gpos Point of where the gear is.
     */
    public Gear(Point gpos, int gsize, int gspeed) { // Default Point constructor(default size)

        // initialize the state
        pos = gpos;
        radius = 25;
        speed = gspeed;
        // load the assets
        loadImage();
    }

    /**
     * <p>
     * a new Gear object- made for gear simulator by Mochatitan.
     * <p>
     * this version will create a gear using x and y points instead of a Point,
     * mostly to simplify formulas
     * 
     * @param x the x-coordinate of the gear midpoint
     * @param y the y-coordinate of the gear midpoint
     */

    public Gear(int x, int y) { // X and Y constructor

        // initialize the state
        pos = new Point(x, y);
        radius = 25;

        // load the assets
        loadImage();
    }

    private void loadImage() {
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            image = ImageIO.read(new File("src/images/gear.png"));
            scaledImage = image.getScaledInstance(radius * 2, radius * 2,
                    Image.SCALE_DEFAULT);
            finalImage = imageToBufferedImage(scaledImage);
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics2D g2d, ImageObserver observer) {
        // with the Point class, note that pos.getX() returns a double, but
        // pos.x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.

        AffineTransform tr = new AffineTransform();
        // X and Y are the coordinates of the image
        tr.translate(getX() - radius, getY() - radius);
        tr.rotate(
                Math.toRadians(this.drawingAngle),
                radius,
                radius);

        // img is a BufferedImage instance
        g2d.drawImage(scaledImage, tr, observer);

    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked");
    }

    public void mousePressed(MouseEvent e) {
        System.out.println("mouse Pressed");
        System.out.println(startDragX + " x " + startDragY);
        System.out.println("testingGear.getPos() = " + this.getPos());
        System.out.println("e.getPoint() = " + e.getPoint());
        System.out.println("distance between 2 = " + e.getPoint().distance(this.getPos()));
        if (e.getPoint().distance(this.getPos()) <= this.getRadius()) {
            startDragX = e.getX(); // in case this is the start of a drag
            startDragY = e.getY();
            dragging = true;
        }
    }

    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse Released");
        // stopDragging();

        if (dragging) {
            this.setPos(this.getX() + (e.getX() - startDragX),
                    this.getY() + (e.getY() - startDragY));
            repaint();
            dragging = false;
        }

    }

    public void mouseDragged(MouseEvent e) {

    }

    /**
     * adds the turning speed to the drawingAngle, also resets a 360 degree angle to
     * 0 so that rotating is easier.
     */
    public void tick() {
        // called before repaint
        if (clockWise) {
            this.drawingAngle += speed;
        } else {
            this.drawingAngle -= speed;
        }

        if (this.drawingAngle >= 360) { // may cause bugs, if theres a bug comment this out
            this.drawingAngle = this.drawingAngle - 360;
        }

        // System.out.println("Mouse Dragged");
        // if (!dragging) { // this is the start of a drag event sequence
        // // ignore if drag moves less than a pixel or two...
        // if (Math.abs(startDragX - e.getX()) + Math.abs(startDragY - e.getY()) > 4) {
        // // System.out.println("we are dragging from " + startDragX + " x
        // // " + startDragY);
        // }
        // dragging = true;
        // }
        if (dragging) { // drag in progress
            mouseXPos = MouseInfo.getPointerInfo().getLocation().getX();
            mouseYPos = MouseInfo.getPointerInfo().getLocation().getY();
            System.out.println(this.getPos());
            System.out.println(mouseXPos);
            System.out.println(mouseYPos);
            // this.setPos(this.getX() + (mouseXPos - startDragX), this.getY() + (mouseYPos
            // - startDragY)); // for user feedback
            repaint();
        }

    }

    public static BufferedImage imageToBufferedImage(Image im) {
        BufferedImage bi = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics bg = bi.getGraphics();
        bg.drawImage(im, 0, 0, null);
        bg.dispose();
        return bi;
    }

    // getters and setters

    public int getRadius() {
        return radius;
    }

    public Point getPos() {
        return pos;
    }

    public void setPos(double x, double y) {
        pos.setLocation(x, y);
    }

    public int getX() {
        return pos.x;
    }

    public int getY() {
        return pos.y;
    }

    public double getDrawingAngle() {
        return drawingAngle;
    }

    public void setDrawingAngle(double newAngle) {
        drawingAngle = newAngle;
    }

    public void setSpeed(double newSpeed) {
        speed = newSpeed;
    }

    public double getSpeed() {
        return speed;
    }

    public Boolean getDirection() {
        return clockWise;
    }

    public void setDirection(Boolean status) {
        clockWise = status;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}
