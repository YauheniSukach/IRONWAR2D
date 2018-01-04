import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Plane implements Runnable{

    private String sprite = "plane.png";
    private int dy;
    private int dy1;
    private int x;
    private int y;
    public boolean die=false;
    public Image image;
    public int hp=4;

    public Plane() {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(sprite));
        image = ii.getImage();
        x = 1;
        y = 100;
    }


    public void moveup() {
        if(y!=10)
            y += dy;
    }
    public void movedown() {
        if(y!=215)
            y += dy1;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            dy = -1;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy1=1;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy1 = 0;
        }
    }

    public void run() {

        Plane s1 = new Plane();
        new Thread(s1).start();
    }
}
