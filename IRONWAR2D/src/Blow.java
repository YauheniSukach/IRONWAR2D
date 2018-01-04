import java.awt.*;
import javax.swing.ImageIcon;

public class Blow  {

    private String sprite = "fair.png";
    public Image image;
    public Point c;
    public Blow(int x,int y) {
        c=new Point(x,y);
        ImageIcon ii = new ImageIcon(this.getClass().getResource(sprite));
        image = ii.getImage();
    }

}