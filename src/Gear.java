import java.awt.Graphics;
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
    private Image newSizeImage;
    // current position of the gear on the board grid
    private Point pos;
    private int rot;
    private int rotSpeed;

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
            newSizeImage = image.getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, Image.SCALE_DEFAULT);
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        // with the Point class, note that pos.getX() returns a double, but
        // pos.x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
        g.drawImage(
                newSizeImage,
                pos.x * Board.TILE_SIZE,
                pos.y * Board.TILE_SIZE,
                observer);
    }

    public Point getPos() {
        return pos;
    }

    public void tick() {
        // called before repaint
        
    }
    
    public int getRot(){
        return rot;
    }
    public int getRotSpeed(){
        return rotSpeed;
    }
    public void setRot(int newRot){
        rot=newRot;
    }
    public void setRotSpeed(int newRotSpeed){
        rotSpeed=newRotSpeed;
    }
    public static BufferedImage rotate(BufferedImage img)
    {
 
        // Getting Dimensions of image
        int width = img.getWidth();
        int height = img.getHeight();
 
        // Creating a new buffered image
        BufferedImage newImage = new BufferedImage(
            img.getWidth(), img.getHeight(), img.getType());
 
        // creating Graphics in buffered image
        Graphics2D g2 = newImage.createGraphics();
 
        // Rotating image by degrees using toradians()
        // method
        // and setting new dimension t it
        g2.rotate(Math.toRadians(90), width / 2,
                  height / 2);
        g2.drawImage(img, null, 0, 0);
 
        // Return rotated buffer image
        return newImage;
    }


}
