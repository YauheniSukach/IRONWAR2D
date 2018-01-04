import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Tank implements Runnable{


    private String sprite = "tank.png";
    private int dy1;
    private int dy;
    private int x;
    private int y;
    public boolean die;
    public Image image;
    public int hp=4;

    public Tank() {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(sprite));
        image = ii.getImage();
        x = -5;
        y = 350;
    }


    public void moveup() {
        if(y!=250)
            y+=dy;
    }
    public void movedown(){
        if(y!=520)
            y+=dy1;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            dy = -1;
        }

        if (key == KeyEvent.VK_S) {
            dy1 = 1;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            dy = 0;
        }

        if (key == KeyEvent.VK_S) {
            dy1 = 0;
        }
    }
    public void run() {
        Tank s1 = new Tank();
        new Thread(s1).start();
    }
}