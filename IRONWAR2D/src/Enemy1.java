import java.awt.*;
import javax.swing.ImageIcon;

public class Enemy1  {

    private String sprite = "rocket.png";
    public Image image;
    public Point c;
    public boolean active;
    public Enemy1(int x,int y,boolean active) {
        this.active=active;
        c=new Point(x,y);
        ImageIcon ii = new ImageIcon(this.getClass().getResource(sprite));
        image = ii.getImage();
    }
}