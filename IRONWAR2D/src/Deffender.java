import java.awt.*;
import javax.swing.ImageIcon;

public class Deffender  {


    private String sprite = "deffend.png";
    public Image image;
    public Point c;
    public Deffender(int x,int y) {
        c=new Point(x,y);
        ImageIcon ii = new ImageIcon(this.getClass().getResource(sprite));
        image = ii.getImage();
    }
}
