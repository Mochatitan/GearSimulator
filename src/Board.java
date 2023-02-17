import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
// import java.util.Random;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

public class Board extends JPanel implements ActionListener, KeyListener, MouseInputListener {

    // controls the delay between each tick in ms
    private final int DELAY = 25;
    // controls the size of the board
    public static final int TILE_SIZE = 50;
    public static final int ROWS = 20;
    public static final int COLUMNS = 20;
    // Gear constants
    public static final double DEFAULT_SPEED = 4;
    public static final Boolean CLOCKWISE = true;
    public static final Boolean COUNTERCLOCKWISE = false;
    // Gear sizes
    public static final int SMALL = 25;
    public static final int MEDIUM = 75;
    public static final int LARGE = 125;
    // New Gear
    public static final Point NEWGEAR = new Point(500, 500);
    // suppress serialization warning
    private static final long serialVersionUID = 490905409104883233L;

    // keep a reference to the timer object that triggers actionPerformed() in
    // case we need access to it in another method
    private Timer timer;
    // objects that appear on the game board

    private ArrayList<Gear> gearList;
    private ArrayList<Gear> dontRotList;
    // private ArrayList<Point> tilesTaken;
    private Player player;
    private Gear mainGear;
    private Gear testingGear;

    private double initialSpeed = DEFAULT_SPEED;

    public Board() {
        // set the game board size
        setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
        // set the game board background color
        setBackground(new Color(232, 232, 232));

        // initialize the game state
        // tilesTaken = new ArrayList<Point>();
        gearList = new ArrayList<Gear>();

        gearList.add(new Gear(new Point(200, 250), SMALL));
        mainGear = new Gear(new Point(200, 250), SMALL);
        dontRotList = new ArrayList<Gear>();
        updateGears(new Point(200, 250));
        player = new Player();
        testingGear = new Gear(new Point(500, 500), SMALL);
        // this timer will call the actionPerformed() method every DELAY ms
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this method is called by the timer every DELAY ms.
        // use this space to update the state of your game or animation
        // before the graphics are redrawn.

        // give the player points for collecting apples

        // prevent the player from disappearing off the board
        player.tick();
        // update rotation
        for (Gear gear : gearList) {
            gear.tick();
        }
        testingGear.tick();
        // calling repaint() will trigger paintComponent() to run again,
        // which will refresh/redraw the graphics.
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // when calling g.drawImage() we can use "this" for the ImageObserver
        // because Component implements the ImageObserver interface, and JPanel
        // extends from Component. So "this" Board instance, as a Component, can
        // react to imageUpdate() events triggered by g.drawImage()
        Graphics2D g2d = (Graphics2D) g;
        // draw our graphics.
        drawBackground(g);
        drawScore(g);

        for (Gear gear : gearList) {
            gear.draw(g2d, this);
        }
        testingGear.draw(g2d, this);

        player.draw(g, this);

        // this smooths out animations on some systems
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // this is not used but must be defined as part of the KeyListener interface
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // react to key down events
        player.keyPressed(e);
        // every keyboard get has a certain code. get the value of that code from the
        // keyboard event so that we can compare it to KeyEvent constants
        int key = e.getKeyCode();

        // depending on which arrow key was pressed, we're going to move the player by
        // one whole tile for this input

        if (key == KeyEvent.VK_E) {
            addGear(new Point(player.getPos()));

        }
        if (key == KeyEvent.VK_Q) {
            removeGear();
        }
        if (key == KeyEvent.VK_R) {
            System.out.println("updating speeds");

            updateGears(player.getPos());
            System.out.println("done");
        }
        // speeds
        if (key == KeyEvent.VK_1) {
            initialSpeed = 1;
            updateGears(mainGear.getPos());
        } else if (key == KeyEvent.VK_1) {
            initialSpeed = 3;
            updateGears(mainGear.getPos());
        } else if (key == KeyEvent.VK_2) {
            initialSpeed = 4;
            updateGears(mainGear.getPos());
        } else if (key == KeyEvent.VK_3) {
            initialSpeed = 5;
            updateGears(mainGear.getPos());
        } else if (key == KeyEvent.VK_4) {
            initialSpeed = 6;
            updateGears(mainGear.getPos());
        } else if (key == KeyEvent.VK_5) {
            initialSpeed = 8;
            updateGears(mainGear.getPos());
        } else if (key == KeyEvent.VK_6) {
            initialSpeed = 10;
            updateGears(mainGear.getPos());
        } else if (key == KeyEvent.VK_7) {
            initialSpeed = 12;
            updateGears(mainGear.getPos());
        } else if (key == KeyEvent.VK_8) {
            initialSpeed = 14;
            updateGears(mainGear.getPos());
        } else if (key == KeyEvent.VK_9) {
            initialSpeed = 20;
            updateGears(mainGear.getPos());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // react to key up events
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        testingGear.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        testingGear.mouseReleased(e);

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse Entered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse Exited");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Mouse dragged");
        testingGear.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("Mouse Moved");
    }

    private void drawBackground(Graphics g) {
        // draw a checkered background
        g.setColor(new Color(214, 214, 214));
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                // only color every other tile
                if ((row + col) % 2 == 1) {
                    // draw a square tile at the current row/column position
                    g.fillRect(
                            col * TILE_SIZE,
                            row * TILE_SIZE,
                            TILE_SIZE,
                            TILE_SIZE);
                }
            }
        }
    }

    private void drawScore(Graphics g) {
        // set the text to be displayed
        String text = "pos: " + player.getPos();
        // we need to cast the Graphics to Graphics2D to draw nicer text
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
                RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        // set the text color and font
        g2d.setColor(new Color(30, 201, 139));
        g2d.setFont(new Font("Lato", Font.BOLD, 25));
        // draw the score in the bottom center of the screen
        // https://stackoverflow.com/a/27740330/4655368
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        // the text will be contained within this rectangle.
        // here I've sized it to be the entire bottom row of board tiles
        Rectangle rect = new Rectangle(0, TILE_SIZE * (ROWS - 1), TILE_SIZE * COLUMNS, TILE_SIZE);
        // determine the x coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // determine the y coordinate for the text
        // (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // draw the string
        g2d.drawString(text, x, y);
    }

    private void dragNewGear() {

    }

    private void stopDragging() {

    }

    private void removeGear() {
        if (getGear(player.getPos()) != null) {
            dontRotList.remove(getGear(player.getPos()));
            gearList.remove(getGear(player.getPos()));
        }
        for (Gear gear : gearList) {
            // if player is on same tile as an gear, destroy the gear
            if (player.getPos().equals(gear.getPos())) {
                // add gear to kill list

            }
        }

    }

    private void addGear(Point thePos) {
        // Point thePos = new Point(player.getPos());
        for (Gear gear : gearList) {
            // if player is on same tile as an gear, destroy the gear
            if (thePos.equals(gear.getPos()) == true) {
                // add gear to kill list

                System.out.println("gear already here");
                return;

            }
        }

        gearList.add(new Gear(thePos, SMALL));

        for (Gear gear : gearList) {
            if (thePos.distance(gear.getPos()) == 1 &&
                    gear.getSpeed() != 0) {
                getGear(thePos).setSpeed(gearSpeedFormula(gear,
                        getGear(thePos)));
                getGear(thePos).setDirection(!gear.getDirection());
                updateGearRotationSpeed(mainGear.getPos());
            }
        }
        // mainGear = getGear(thePos);
        // dontRotList.clear();
        // getGear(thePos).setDirection(CLOCKWISE);
        // getGear(thePos).setSpeed(initialSpeed);

        // updateGearRotationSpeed(mainGear.getPos());
    }

    private void updateGears(Point thePos) {
        // initialise only runs once
        mainGear = getGear(thePos);
        dontRotList.clear();
        getGear(thePos).setDirection(CLOCKWISE);
        getGear(thePos).setSpeed(initialSpeed);

        // makes a chain reaction of gears
        updateGearRotationSpeed(thePos);
    }

    private void updateGearRotationSpeed(Point mainGearPos) {

        System.out.println(mainGearPos);
        Gear mainGear = new Gear(new Point(50, 50), SMALL);
        for (Gear mgear : gearList) { // adds this gear to the dont spin list
            mainGear = getGear(mainGearPos);
            if (mgear.getPos().equals(mainGearPos) == true) {
                dontRotList.add(mgear);
            }
        }

        for (Gear gear : gearList) {
            if ((gear.getPos().distance(mainGearPos) == 50) && gearRemoved(gear.getPos()) == false) {

                gear.setSpeed(gearSpeedFormula(mainGear, gear));
                if (mainGear.getDirection() == CLOCKWISE) {
                    gear.setDirection(COUNTERCLOCKWISE);
                } else if (mainGear.getDirection() == COUNTERCLOCKWISE) {
                    gear.setDirection(CLOCKWISE);
                } else {
                    gear.setDirection(CLOCKWISE);
                    System.out.println("gear at " + gear.getPos() + " error, no direction found.");
                }
                updateGearRotationSpeed(gear.getPos());
            }
        }
    }

    /**
     * Formula for calculating the gear speed
     * 
     * @param gearOne the first gear thats already spinning
     * @param gearTwo the second gear which speed you want to calculate
     * @return the speed the second gear should spin
     */
    private double gearSpeedFormula(Gear gearOne, Gear gearTwo) {
        return (gearOne.getRadius() / gearTwo.getRadius()) * gearOne.getSpeed();
        // return DEFAULT_SPEED;
    }

    // private void updateTiles() {
    // for (Gear gear : gearList) {
    // if (!tilesTaken.contains(gear.getPos())) { // if a gear isnt in the list, add
    // it
    // switch (gear.getRadius()) {
    // case SMALL:
    // tilesTaken.add(gear.getPos());
    // break;
    // case MEDIUM:
    // for (var dx = -50; dx <= 50; dx += 50) {
    // for (var dy = -50; dy <= 50; dy += 50) {
    // tilesTaken.add(new Point(gear.getX() + dx, gear.getY() + dy));
    // }
    // }
    // break;
    // case LARGE:
    // // TODO add large size

    // break;

    // default:
    // System.out.println("error 404: size not found");
    // }
    // }
    // }

    // }

    private Gear getGear(Point posit) {

        for (Gear gear : gearList) {
            if (gear.getPos().equals(posit)) {
                return gear;
            }
        }

        return null;
    }

    private Boolean gearRemoved(Point posit) {
        for (Gear gear : dontRotList) {
            if (gear.getPos().equals(posit)) {
                return true;
            }
        }

        return false;
    }

}
