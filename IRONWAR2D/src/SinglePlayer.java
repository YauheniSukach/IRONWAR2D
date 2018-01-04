import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static com.sun.deploy.uitoolkit.ToolkitStore.dispose;


public class SinglePlayer extends JPanel {
    private static final long serialVersionUID = 1L;
    public JButton butt;
    private Plane plane;
    private Level lvl = new Level();
    private Boss boss= new Boss(0,780,150);
    private ArrayList<Patrons> ps = new ArrayList<>();
    private ArrayList<Patrons> ps1 = new ArrayList<>();
    private ArrayList<Enemy1> em1 = new ArrayList<>();
    private ArrayList<Hearts> hr = new ArrayList<>();
    private ArrayList<Blow> bl = new ArrayList<>();
    private ArrayList<Blow> greatbl=new ArrayList<>();
    private ArrayList<Blow> greatbl1=new ArrayList<>();
    private ArrayList<Bonus> bon = new ArrayList<>();
    private ArrayList<Bonus> bon1 = new ArrayList<>();
    private ArrayList<Deffender> def=new ArrayList<>();
    private ArrayList<UnionRocket> ur= new ArrayList<>();
    private Random rand= new Random();
    private boolean bonus1_plane=false;
    private boolean boss_time=false;
    private int randombk;
    public SinglePlayer(){
        addKeyListener(new TAdapter());
        setFocusable(true);
        setDoubleBuffered(true);
        plane = new Plane();
        patrons_move.start();
        patrons_move_1.start();
        union_move.start();
        _repaint.start();
        enemy_move.start();
        addHeartsPlane();
        _bonus1_plane.start();
        _bonus2_plane.start();
        _bonus3_plane.start();
        bonus_move.start();
        blow.start();
        LVL.start();
        BOSS_LVL.start();
        _move.start();
        enemy.start();
        randombk=rand.nextInt(3);
        butt = new JButton ("RETURN GAME");
        butt.setBackground(Color.RED);
        butt.setForeground(Color.BLACK);
      }
    private Timer enemy_move = new javax.swing.Timer(10, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            for (int i = 0; i < em1.size(); i++) {
                em1.get(i).c.x -= lvl.speed;
            }
        }
    });
    private Timer enemy = new Timer(lvl.respawn, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            Random rand= new Random();
            em1.add(new Enemy1(800, rand.nextInt(180) + 40,true));
        }
    });
    private Timer LVL = new javax.swing.Timer(30000, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            lvl.next();
        }
    });
    private Timer BOSS = new javax.swing.Timer(20, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            if(!boss_time) {
                boss.move(plane);
            }
        }
    });
    private Timer patrons_move = new Timer(5, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            for(int i=0;i<ps.size();i++){
                ps.get(i).c.x += 3;
            }
        }
    });
    private Timer patrons_move_1 = new Timer(6, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            for(int i=0;i<ps1.size();i++){
                ps1.get(i).c.x -= 2;
            }
        }
    });
    private Timer union_move = new Timer(5, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            for(int i=0;i<ur.size();i++){
                if(ur.get(i).skin==0) {
                    ur.get(i).c.x += 3;
                }else if(ur.get(i).skin==1){
                    ur.get(i).c.y+=3;
                }
            }
        }
    });
    private Timer bonus_move = new Timer(5, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            for(int i=0;i<bon.size();i++){
                bon.get(i).c.x-=1;
            }
        }
    });
    private Timer boss_patrons = new Timer(1800, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            if(!boss_time) {
                ps1.add(new Patrons(boss.c.x + 85, boss.c.y + 5, 1,true));
                ps1.add(new Patrons(boss.c.x + 85, boss.c.y + 50, 1,true));
            }
        }
    });
    private Timer plane_patrons = new Timer(600, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            ps.add(new Patrons(plane.getX()+85, plane.getY()+10,0,true));
        }
    });
    private Timer _move = new Timer(1, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            plane.movedown();
            plane.moveup();
        }
    });
    private Timer blow = new Timer(1500, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            remove_blow();
        }
    });
    private Timer BOSS_LVL = new Timer(1, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            if(lvl.lvl%2==0) {
                BOSS_TIME.start();
                BOSS.start();
                boss_patrons.start();
            }
            if(boss_time){
                BOSS.stop();
                boss_patrons.stop();
                boss.moveback();
            }
            if(boss.c.x==800&&lvl.lvl%2==1){
                boss_time=false;
            }

        }
    });
    private Timer BOSS_TIME = new Timer(15000, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            BOSS_TIME.stop();
            boss_time=true;
        }
    });
    private Timer _repaint = new Timer(1, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            repaint();
            collisionwithrockets();
            collisionwithplane();
            collisionwithbonus_plane();
            collisionwithplayer();
            _remove();
        }
    });
    private Timer _bonus1_plane = new Timer(42133, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            bon.add(new Bonus(1000, rand.nextInt(150)+40,0,true));
        }
    });
    private Timer _bonus2_plane = new Timer(17000, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            bon.add(new Bonus(1000, rand.nextInt(150)+40,1,true));
        }
    });
    private Timer _bonus3_plane = new Timer(55555, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            bon.add(new Bonus(1000, rand.nextInt(150)+40,2,true));
        }
    });
    private Timer bonus_time = new Timer(15000, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            bonus_time.stop();
            bonus1_plane=false;
        }
    });
    private void _remove() {
        for (int i = 0; i < em1.size(); i++) {
            if (em1.get(i).c.x==-100||em1.get(i).active==false) {
                em1.remove(i);
            }
        }
        for(int i=0;i<bon.size();i++){
            if (bon.get(i).c.x==-100||!bon.get(i).active ) {
                bon.remove(i);
            }
        }
        for (int i = 0; i < ps.size(); i++) {
            if (ps.get(i).c.x == 1000||!ps.get(i).active) {
                ps.remove(i);
            }
        }
        for (int i = 0; i < ps1.size(); i++) {
            if (ps1.get(i).c.x == -100||!ps1.get(i).active) {
                ps1.remove(i);
            }
        }
        for(int i = hr.size() - 1; i >= 0; i--){
            if(!hr.get(i).active) {
                hr.remove(i);
            }
        }

        for(int i=0;i<ur.size();i++) {
            if (ur.get(i).c.x > 500) {
                collision(2);
                ur.remove(i);
                greatbl1.add(new Blow(200, -250));
                em1.clear();
            }
        }
    }
    private void remove_blow(){
        for(int i=0;i<bl.size();i++){
            bl.remove(i);
        }
        for(int i=0;i<greatbl.size();i++){
            greatbl.remove(i);
        }
        for(int i=0;i<greatbl1.size();i++){
            greatbl1.remove(i);
        }
    }
    private void addHeartsPlane(){
        int xT=650;
        for(int i=0;i<plane.hp;i++){
            hr.add(new Hearts(xT+=30,10,true));
        }
    }
    private void collisionwithplayer() {
        for (int j = hr.size() - 1; j >= 0; j--) {
            for (int i = 0; i < ps1.size(); i++) {
                if (plane.getX() + 100 > ps1.get(i).c.x && plane.getX() < ps1.get(i).c.x && plane.getY() + 50 > ps1.get(i).c.y && plane.getY() < ps1.get(i).c.y&&hr.get(j).active&&ps1.get(i).active) {
                    if (!bonus1_plane) {
                        hr.get(j).active=false;
                        ps1.get(i).active=false;
                        collision(1);
                    }
                    if (j==0 && !bonus1_plane) {
                        plane.die = true;
                        collision(2);
                        greatbl.add(new Blow(plane.getX() - 50, plane.getY() - 70));
                        game_over();
                    }
                }
            }
        }
    }
    private void collisionwithrockets(){
        for (int j = 0; j < ps.size(); j++) {
            for (int i = 0; i < em1.size(); i++) {
                if (ps.get(j).c.x > em1.get(i).c.x + 30 && ps.get(j).c.y+10 > em1.get(i).c.y && ps.get(j).c.y < em1.get(i).c.y + 30&&ps.get(j).active&&em1.get(i).active) {
                    lvl.score2+=10;
                    collision(1);
                    bl.add(new Blow(em1.get(i).c.x,em1.get(i).c.y));
                    ps.get(j).active=false;
                    em1.get(i).active=false;
                }
            }
        }
    }
    public void collisionwithplane() {
        for (int j = hr.size() - 1; j >= 0; j--) {
            for (int i = 0; i < em1.size(); i++) {
                if (plane.getX() + 100 > em1.get(i).c.x && plane.getX() < em1.get(i).c.x + 100 && plane.getY() + 50 > em1.get(i).c.y && plane.getY() < em1.get(i).c.y&&hr.get(i).active&&em1.get(i).active) {
                    if (!bonus1_plane) {
                        hr.get(i).active=false;
                        bl.add(new Blow(em1.get(i).c.x, em1.get(i).c.y));
                        em1.get(i).active=false;
                        collision(1);
                    }
                    if (j == 0 && !bonus1_plane) {
                        plane.die = true;
                        collision(2);
                        greatbl.add(new Blow(plane.getX() - 50, plane.getY() - 70));
                        game_over();
                    }
                }
            }
        }
    }
    private void collisionwithbonus_plane() {
        for (int i = 0; i < bon.size(); i++) {
            if (plane.getX() + 100 > bon.get(i).c.x && plane.getX() < bon.get(i).c.x + 100 && plane.getY() + 50 > bon.get(i).c.y && plane.getY() < bon.get(i).c.y&&  bon.get(i).active) {
                if (bon.get(i).skin == 0&&!plane.die) {
                    collision(3);
                    bon.get(i).active=false;
                    bonus1_plane = true;
                    bonus_time.start();
                    def.add(new Deffender(plane.getX(), plane.getY()));
                    bon1.add(new Bonus(150,6,0,true));
                }
                if (bon.get(i).skin == 1&&!plane.die) {
                    collision(3);
                    bon.get(i).active=false;
                    lvl.score2 += 10000;
                }
                if (bon.get(i).skin == 2&&!plane.die) {
                    collision(3);
                    bon.get(i).active=false;
                    ur.add(new UnionRocket(0,120,0));
                }
            }
        }
    }
    private void game_over() {
        enemy_move.stop();
        patrons_move.stop();
        plane_patrons.stop();
        _repaint.stop();
        _move.stop();
    }
    public void paint(Graphics g) {
        super.paint(g);
        if(randombk==0) {
            ImageIcon ii = new ImageIcon(this.getClass().getResource("background1.png"));
            g.drawImage(ii.getImage(), 0, 0, this);
        }
        if(randombk==1) {
            ImageIcon ii = new ImageIcon(this.getClass().getResource("background2.png"));
            g.drawImage(ii.getImage(), 0, 0, this);
        }
        if(randombk==2) {
            ImageIcon ii = new ImageIcon(this.getClass().getResource("background3.jpg"));
            g.drawImage(ii.getImage(), 0, 0, this);
        }
        if (!plane.die) {
            g.drawImage(plane.image, plane.getX(), plane.getY(), 100, 40, this);
        }
        if(!boss.bossdie) {
            g.drawImage(boss.image, boss.c.x, boss.c.y, 140, 60, this);
        }

        g.setFont(new Font("TimesRoman", Font.BOLD, 12));
        g.setColor(Color.RED);
        g.drawString("HEALTH:", 630, 25);
        g.drawString("LVL:" + lvl.lvl + "", 380, 20);
        g.drawString("SCORE:" + lvl.score2 + "", 10, 20);
        g.drawString("BONUS:", 100, 20);
        if (plane.die ) {
            Font f = new Font("TimesRoman", Font.BOLD, 35);
            g.setFont(f);
            g.drawString("GAME OVER!", 280, 270);
        }
        if (!ps.isEmpty()) {
            for (int i = 0; i < ps.size(); i++) {
                Patrons p = ps.get(i);
                g.drawImage(p.image, p.c.x, p.c.y, 11, 5, this);
            }
        }
        if (!ps1.isEmpty()) {
            for (int i = 0; i < ps1.size(); i++) {
                Patrons p1 = ps1.get(i);
                g.drawImage(p1.image, p1.c.x, p1.c.y, 11, 5, this);
            }
        }
        if (!em1.isEmpty()) {
            for (int i = 0; i < em1.size(); i++) {
                Enemy1 e1 = em1.get(i);
                g.drawImage(e1.image, e1.c.x, e1.c.y, 30, 10, this);
            }
        }
        if(!hr.isEmpty()) {
            for (int i = 0; i < hr.size(); i++) {
                Hearts h = hr.get(i);
                g.drawImage(h.image, h.c.x, h.c.y, 20, 20, this);
            }
        }
        if (!bl.isEmpty()) {
            for (int i = 0; i < bl.size(); i++) {
                Blow b = bl.get(i);
                g.drawImage(b.image, b.c.x, b.c.y, 20, 20, this);
            }
        }
        if(!greatbl.isEmpty()) {
            for (int i = 0; i < greatbl.size(); i++) {
                Blow gr = greatbl.get(i);
                g.drawImage(gr.image, gr.c.x, gr.c.y, 200, 200, this);
            }
        }
        if(!greatbl1.isEmpty()) {
            for (int i = 0; i < greatbl1.size(); i++) {
                Blow gr1 = greatbl1.get(i);
                g.drawImage(gr1.image, gr1.c.x, gr1.c.y, 800, 800, this);
            }
        }
        if(!bon.isEmpty()) {
            for (int i = 0; i < bon.size(); i++) {
                Bonus bs = bon.get(i);
                g.drawImage(bs.image, bs.c.x, bs.c.y, 35, 35, this);
            }
        }
        if(!def.isEmpty()) {
            for (int i = 0; i < def.size(); i++) {
                Deffender d = def.get(i);
                if (bonus1_plane) {
                    g.drawImage(d.image, plane.getX() - 40, plane.getY() - 70, 200, 200, this);
                }
            }
        }
        if(!ur.isEmpty()) {
            for (int i = 0; i < ur.size(); i++) {
                UnionRocket u = ur.get(i);
                if (ur.get(i).skin == 0) {
                    g.drawImage(u.image, u.c.x, u.c.y, 50, 25, this);
                } else if (ur.get(i).skin == 1) {
                    g.drawImage(u.image, u.c.x, u.c.y, 25, 50, this);
                }
            }
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    public void collision(int sound){
        try {
            if(sound==1) {
                File soundFile = new File("src/player.wav"); //Звуковой файл
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                clip.setFramePosition(0);
                clip.start();
            }
            if(sound==2) {
                File soundFile = new File("src/collision.wav"); //Звуковой файл
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                clip.setFramePosition(0);
                clip.start();
            }
            if(sound==3) {
                File soundFile = new File("src/bonus.wav"); //Звуковой файл
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                clip.setFramePosition(0);
                clip.start();
            }
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
            exc.printStackTrace();
        }
    }
    public void run() {
        SinglePlayer t1 = new SinglePlayer();
        new Thread((Runnable) t1).start();
    }
    private class TAdapter extends KeyAdapter {
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            plane.keyReleased(e);
            if(key == KeyEvent.VK_ENTER){
                plane_patrons.stop();

            }
        }
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            plane.keyPressed(e);
            if(key == KeyEvent.VK_ENTER&&!plane.die){
                plane_patrons.start();
            }
            if(key == KeyEvent.VK_P){
                enemy_move.stop();
                enemy.stop();
                bonus_move.stop();
                patrons_move_1.stop();
                patrons_move.stop();
                _repaint.stop();
                _move.stop();
            }
            if(key == KeyEvent.VK_SPACE){
                enemy_move.start();
                enemy.start();
                bonus_move.start();
                patrons_move_1.start();
                patrons_move.start();
                _repaint.start();
                _move.start();
            }
        }
    }

}

