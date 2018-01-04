import javax.swing.*;

public class GameWindow1 {
    JFrame frame;

    public GameWindow1(){
        frame = new JFrame("Game window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setResizable(false);
        frame.add(new SinglePlayer());
        frame.setVisible(true);
    }

    public static void main(String [] args){
        new GameWindow1();
    }
}
