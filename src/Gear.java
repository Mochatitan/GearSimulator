import java.awt.Graphics;
import java.awt.Graphics2D;
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
    private BufferedImage newSizeImage;
    // current position of the gear on the board grid
    private Point pos;
    private int rot = 45;
    private int rotSpeed = 5;

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
            finalImage = rotate(image.getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, Image.SCALE_DEFAULT));
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
                finalImage,
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
    
    public static BufferedImage rotate(BufferedImage img) {  
      int w = img.getWidth();  
      int h = img.getHeight();  
      BufferedImage newImage = new BufferedImage(width, height, img.getType());
      Graphics2D g2 = newImage.createGraphics();
      g2.rotate(Math.toRadians(rot), w/2, h/2);  
      g2.drawImage(img,null,0,0);
      return newImage;  
  }
   

}
