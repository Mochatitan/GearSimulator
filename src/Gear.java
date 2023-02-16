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

public class Gear {

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
    private int size = 1; // 1 = small aka default size

    /**
     * <p>
     * a new Gear object- made for gear simulator by Mochatitan.
     * 
     * @param gpos  Point of where the gear is.
     * @param gsize what size the gear is- size1 is small, size2 is medium, size3 is
     *              large.
     *
     */
    public Gear(Point gpos, int gsize) { // Point constructor

        // initialize the state

        pos = gpos;
        if (size == 3) {
            pos.translate(-1, -1);
        }
        size = gsize;

        // load the assets
        loadImage();

    }

    /**
     * <p>
     * a new Gear object- made for gear simulator by Mochatitan.
     * <p>
     * this version will create a gear of default size 1/small.
     * 
     * @param gpos Point of where the gear is.
     */
    public Gear(Point gpos) { // Default Point constructor(default size)

        // initialize the state
        pos = gpos;
        size = 1;

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
     * @param x the x-coordinate of the gear
     * @param y the y-coordinate of the gear
     */
    public Gear(int x, int y) { // X and Y constructor

        // initialize the state
        pos = new Point(x, y);
        size = 1;

        // load the assets
        loadImage();
    }

    private void loadImage() {
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            image = ImageIO.read(new File("src/images/gear.png"));
            scaledImage = image.getScaledInstance(Board.TILE_SIZE * size, Board.TILE_SIZE * size,
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
        tr.translate((getX() - (size - 1) / 2) * Board.TILE_SIZE, (getY() - (size - 1) / 2) * Board.TILE_SIZE);
        tr.rotate(
                Math.toRadians(this.drawingAngle),
                finalImage.getWidth() / 2,
                finalImage.getHeight() / 2);

        // img is a BufferedImage instance
        g2d.drawImage(scaledImage, tr, observer);

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

        if (this.drawingAngle == 360) { // may cause bugs, if theres a bug comment this out
            this.drawingAngle = 0;
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

    public int getSize() {
        return size;
    }

    public Point getPos() {
        return pos;
    }

    public int getX() {
        return pos.x;
    }

    public int getY() {
        return pos.y;
    }

    public int getXLocation() {
        return pos.x * Board.TILE_SIZE;
    }

    public int getYLocation() {
        return pos.y * Board.TILE_SIZE;
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

}
