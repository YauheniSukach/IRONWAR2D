import java.awt.*;
import javax.swing.ImageIcon;

public class Hearts  {
    private String sprite = "heart.png";
    public Image image;
    public Point c;
    public boolean active;
    public Hearts(int x,int y,boolean active) {
        this.active=active;
        c=new Point(x,y);
        ImageIcon ii = new ImageIcon(this.getClass().getResource(sprite));
        image = ii.getImage();
    }
}