import java.awt.*;
import javax.swing.ImageIcon;

public class UnionRocket {
    private String sprite = "rocket1.png";
    private String sprite1 = "rocket2.png";
    public Image image;
    public int skin;
    public Point c;
    public UnionRocket(int x,int y,int skin) {
        this.skin = skin;
        c = new Point(x, y);
        if (skin == 0) {
            ImageIcon ii = new ImageIcon(this.getClass().getResource(sprite));
            image = ii.getImage();
        }
        if (skin == 1) {
            ImageIcon ii = new ImageIcon(this.getClass().getResource(sprite1));
            image = ii.getImage();
        }
    }
}