import java.awt.Image;
import java.awt.Point;
import javax.swing.*;

public class Boss {

    public Point c;
    public Image image;
    private String sprite1 = "BOSS.png";
    private String sprite2 = "BOSS1.png";
    public boolean bossdie;
    public Boss(int skin,int x,int y) {
        c = new Point(x, y);
        if (skin == 0) {
            ImageIcon ii = new ImageIcon(this.getClass().getResource(sprite1));
            image = ii.getImage();
        }
        if (skin == 1) {
            ImageIcon ii = new ImageIcon(this.getClass().getResource(sprite2));
            image = ii.getImage();
        }
    }
    public void move(Plane pl)
    {
        if (c.x > 650)
            c.x-= 2;
        else
        {
            if (c.y < pl.getY()-20 && c.y < 300 )
                c.y++;
            if (c.y > pl.getY()-20 && c.y > 0)
                c.y--;
        }
    }
    public void moveback()
    {
        if (c.x < 800)
            c.x+= 1;
    }
    public void move2(Tank tk)
    {
        if (c.x > 650)
            c.x-= 6;
        else
        {
            if (c.y < tk.getY()-20 && c.y <650 )
                c.y++;
            if (c.y > tk.getY()-20 && c.y >200)
                c.y--;
        }
    }
}
