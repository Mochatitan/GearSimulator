import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
    private double drawingAngle = 10;
    private double speed = 5;
    private Boolean clockWise = true;

    public Gear(Point gpos) {
        // load the assets
        loadImage();

        // initialize the state
        pos = gpos;
    }

    private void loadImage() {
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            image = ImageIO.read(new File("src/images/gear.png"));
            scaledImage = image.getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, Image.SCALE_DEFAULT);
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
        // AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired,
        // locationX, locationY);
        // AffineTransformOp op = new AffineTransformOp(tx,
        // AffineTransformOp.TYPE_BILINEAR);
        // g2d.drawImage(
        // newImage,
        // pos.x * Board.TILE_SIZE,
        // pos.y * Board.TILE_SIZE,
        // observer);
        AffineTransform tr = new AffineTransform();
        // X and Y are the coordinates of the image
        tr.translate(getXLocation(), getYLocation());
        tr.rotate(
                Math.toRadians(this.drawingAngle),
                finalImage.getWidth() / 2,
                finalImage.getHeight() / 2);

        // img is a BufferedImage instance
        g2d.drawImage(scaledImage, tr, observer);

    }

    public void tick() {
        // called before repaint
        this.drawingAngle += speed;
    }

    // getters and setters

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

    public static BufferedImage imageToBufferedImage(Image im) {
        BufferedImage bi = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics bg = bi.getGraphics();
        bg.drawImage(im, 0, 0, null);
        bg.dispose();
        return bi;
    }

}