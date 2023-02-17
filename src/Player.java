import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;

public class Player {

    // image that represents the player's position on the board
    private BufferedImage image;
    private Image newImage;
    // current position of the player on the board grid
    private Point pos;
    // keep track of the player's score
    private int score;
    private int speed = 5;

    public Player() {
        // load the assets
        loadImage();

        // initialize the state
        pos = new Point(0, 0);
        score = 0;
    }

    private void loadImage() {
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            image = ImageIO.read(new File("src/images/scratch_cat.png"));
            newImage = image.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

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
                newImage,
                pos.x,
                pos.y,
                observer);
    }

    public void keyPressed(KeyEvent e) {
        // every keyboard get has a certain code. get the value of that code from the
        // keyboard event so that we can compare it to KeyEvent constants
        int key = e.getKeyCode();

        // depending on which arrow key was pressed, we're going to move the player by
        // one whole tile for this input
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            pos.translate(0, -speed);
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            pos.translate(speed, 0);
        }
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            pos.translate(0, speed);
        }
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            pos.translate(-speed, 0);
        }

    }

    public void tick() {
        // this gets called once every tick, before the repainting process happens.
        // so we can do anything needed in here to update the state of the player.

        // prevent the player from moving off the edge of the board sideways
        if (pos.x < 0) {
            pos.x = 0;
        } else if (pos.x >= Board.COLUMNS * Board.TILE_SIZE) {
            pos.x = Board.COLUMNS * Board.TILE_SIZE - 1;
        }
        // prevent the player from moving off the edge of the board vertically
        if (pos.y < 0) {
            pos.y = 0;
        } else if (pos.y >= Board.ROWS * Board.TILE_SIZE) {
            pos.y = Board.ROWS * Board.TILE_SIZE - 1;
        }
    }

    public String getScore() {
        return String.valueOf(score);
    }

    public void addScore(int amount) {
        score += amount;
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

}