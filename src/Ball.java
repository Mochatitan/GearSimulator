import java.awt.*;
import java.awt.Color.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;
import javax.swing.Timer;

final public class Ball extends JPanel implements ActionListener, MouseListener, KeyListener, MouseMotionListener {

    // double instead of Double
    // boolean instead of Boolean
    double q = 40.00;
    double w = 300.00;
    double t = 300.00;
    double z = 40.00;
    Timer timer;
    boolean direction = false;
    boolean start = false;

    public Ball() {
        this.setFocusable(true);
        // timer = new Timer(20, this);
        // timer.start();
        this.setBackground(Color.BLUE);
        this.addMouseListener(this);
        this.addKeyListener(this);
    }

    public void mouseDragged(MouseEvent me) {
        int x = me.getX();
        int y = me.getY();
        System.out.println("x = " + x);
        System.out.println("y = " + y);
    }

    public void mouseMoved(MouseEvent me) {
    }

    public void mousePressed(MouseEvent me) {
        // if (direction) {
        // direction = false;
        // }
        // else {
        // direction = true;
        // }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        // switch(e.getKeyCode()) {
        // case KeyEvent.VK_LEFT : direction = false; repaint(); break;
        // case KeyEvent.VK_RIGHT : direction = true; repaint(); break;
        // }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);// removes all previously drawn content and draw from scratch
        Graphics2D g2 = (Graphics2D) g;
        Ellipse2D ellipse = new Ellipse2D.Double(w, t, q, z); // x, y, width, height
        g2.setColor(Color.RED);
        g2.fill(ellipse);

        // Rectangle2D rec = new Rectangle2D.Double(q, w, e, r);
        // g2.draw(rec);
        // Line2D lin = new Line2D.Double(q - 20, w - 20, e - 20, r - 20);
        // g2.draw(lin);
        // g.setColor(Color.yellow);
        // Font b=new Font("TimesRoman",Font.PLAIN,45);
        // g.setFont(b);
        // g.drawString("HI", 10, 40);
    }

    public void actionPerformed(ActionEvent ex) {
    }

    final public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("ball");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        Container contentPane = frame.getContentPane();
        contentPane.add(new Ball());
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
