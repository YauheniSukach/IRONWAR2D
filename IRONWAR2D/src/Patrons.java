import com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Patrons  {

    private String sprite = "patron.png";
    private String sprite1 = "patronboss.png";
    public Image image;
    public Point c;
    public boolean active;
    public Patrons(int x,int y,int skin,boolean active) {
        this.active=active;
        if(skin==0) {
            c = new Point(x, y);
            ImageIcon ii = new ImageIcon(this.getClass().getResource(sprite));
            image = ii.getImage();
        }
        if(skin==1){
            c = new Point(x, y);
            ImageIcon ii = new ImageIcon(this.getClass().getResource(sprite1));
            image = ii.getImage();
        }
    }

}