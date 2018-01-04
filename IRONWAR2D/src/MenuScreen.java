import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuScreen extends JFrame {


    public JButton butt;
    public JButton butt1;
    public JButton butt2;
    public JButton butt3;
    public MenuScreen()
    {
        super("IRONWAR2D");
        setBounds(0, 0, 800, 600);
        setResizable(false);
        repaint();
        butt3 = new JButton ("SINGLEPLAYER");
        butt3.setBackground(Color.RED);
        butt3.setForeground(Color.BLACK);
        butt = new JButton ("MULTIPLAYER");
        butt.setBackground(Color.RED);
        butt.setForeground(Color.BLACK);
        butt1 = new JButton ("INFORMATION");
        butt1.setBackground(Color.RED);
        butt1.setForeground(Color.BLACK);
        butt2 = new JButton ("EXIT");
        butt2.setBackground(Color.RED);
        butt2.setForeground(Color.BLACK);
        butt.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){
                new GameWindow();
                dispose();
            }
        });
        butt1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(butt1, "PLANE KEYS:\nMOVEUP-↑!\nMOVEDOWN-↓\nATTACK-ENTER\n----------------------\nTANK KEYS:\nMOVEUP-W\nMOVEDOWN-S\nATTACK-H\n----------------------\nPAUSE-P\nTURN PAUSE-SPACE","Information",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        butt2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });
        butt3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
              new GameWindow1();
              dispose();
            }
        });
         setContentPane(new JPanel(){
            public void paintComponent(Graphics g){
                BufferedImage backGround = null;
                try {
                    backGround = ImageIO.read(new File("src/background.jpg"));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                g.drawImage(backGround, 0, 0, this);
            }
        });
        Container cont = getContentPane();
        cont.setLayout(new GridBagLayout());
        cont.add(butt,new GridBagConstraints(0, 0, 0, 0, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(100, 0, 200, 0), 250, 20));
        cont.add(butt1,new GridBagConstraints(0, 0, 0, 0, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(400, 0, 200, 0), 250, 20));
        cont.add(butt2,new GridBagConstraints(0, 0, 0, 0, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(550, 0, 200, 0), 250, 20));
        cont.add(butt3,new GridBagConstraints(0, 0, 0, 0, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(250, 0, 200, 0), 250, 20));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void music(){
        try {
            File soundFile = new File("src/MUSIC.wav");
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.setFramePosition(0);
            clip.loop(10);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
            exc.printStackTrace();
        }
    }
    public void paint(Graphics g) {
        super.paint(g);

        try {
            Font titleFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/OpenSans-BoldItalic.TTF")).deriveFont(Font.BOLD, 100);
            g.setFont(titleFont);
            g.setColor(Color.cyan);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawString("IRON WAR", 155, 150);
    }
    public static void main(String[] args) {

        MenuScreen mn = new MenuScreen();
        mn.music();
        mn.setVisible(true);
    }

}