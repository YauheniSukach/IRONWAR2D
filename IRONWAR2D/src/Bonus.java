import java.awt.*;
import javax.swing.ImageIcon;

public class Bonus {


    private String sprite1 = "iron.png";
    private String sprite2 = "star_green.png";
    private String sprite3 = "bonus3.png";
    public Image image;
    public int skin;
    public Point c;
    public boolean active;
    public Bonus(int x,int y,int skin,boolean active) {
        c=new Point(x,y);
        this.skin=skin;
        this.active=active;
        if (skin == 0) {
            ImageIcon ii = new ImageIcon(this.getClass().getResource(sprite1));
            image = ii.getImage();
        }
        if (skin == 1) {
            ImageIcon ii = new ImageIcon(this.getClass().getResource(sprite2));
            image = ii.getImage();
        }
        if (skin == 2) {
            ImageIcon ii = new ImageIcon(this.getClass().getResource(sprite3));
            image = ii.getImage();
        }
    }
}