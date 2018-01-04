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


public class GameScreen extends JPanel {
    private static final long serialVersionUID = 1L;
    private Plane plane;
    private Tank tank;
    private Level lvl = new Level();
    private Boss boss= new Boss(0,780,150);
    private Boss boss2= new Boss(1,780,350);
    private ArrayList<Patrons> ps = new ArrayList<>();
    private ArrayList<Patrons> ps1 = new ArrayList<>();
    private ArrayList<Enemy>  em = new ArrayList<>();
    private ArrayList<Enemy1> em1 = new ArrayList<>();
    private ArrayList<Hearts> hr = new ArrayList<>();
    private ArrayList<Hearts> hr1 = new ArrayList<>();
    private ArrayList<Blow> bl = new ArrayList<>();
    private ArrayList<Blow> greatbl=new ArrayList<>();
    private ArrayList<Blow> greatbl1=new ArrayList<>();
    private ArrayList<Bonus> bon = new ArrayList<>();
    private ArrayList<Bonus> bon1= new ArrayList<>();
    private ArrayList<Deffender> def=new ArrayList<>();
    private ArrayList<UnionRocket> ur= new ArrayList<>();
    private Random rand= new Random();
    private boolean bonus1_tank=false;
    private boolean bonus1_plane=false;
    private boolean boss_time=false;
    private boolean pause=false;
    public GameScreen() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setDoubleBuffered(true);
        plane = new Plane();
        tank = new Tank();
        patrons_move.start();
        patrons_move_1.start();
        union_move.start();
        _repaint.start();
        enemy.start();
        enemy_move.start();
        addHeartsTank();
        addHeartsPlane();
        _bonus1_plane.start();
        _bonus1_tank.start();
        _bonus2_plane.start();
        _bonus3_plane.start();
        _bonus2_tank.start();
        _bonus3_tank.start();
        bonus_move.start();
        blow.start();
        LVL.start();
        BOSS_LVL.start();
        _move.start();
    }
    private Timer enemy_move = new Timer(10, new ActionListener(){
        public void actionPerformed(ActionEvent e){
                for (int i = 0; i < em.size(); i++) {
                    em.get(i).c.x -= lvl.speed;
                }
                for (int i = 0; i < em1.size(); i++) {
                    em1.get(i).c.x -= lvl.speed;
                }
        }
    });
    private Timer LVL = new Timer(30000, new ActionListener(){
        public void actionPerformed(ActionEvent e){
          lvl.next();
        }
    });
    private Timer BOSS = new Timer(20, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            if(!boss_time) {
                boss.move(plane);
            }
        }
    });
    private Timer BOSS2 = new Timer(20, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            boss2.move2(tank);
        }
    });
    private Timer enemy = new Timer(lvl.respawn, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            Random rand= new Random();
            em.add(new Enemy(800, 280 + rand.nextInt(250),true));
            em1.add(new Enemy1(800, rand.nextInt(180) + 40,true));
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
    private Timer boss2_patrons = new Timer(1000, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            ps1.add(new Patrons(boss2.c.x, boss2.c.y+20,1,true));
        }
    });
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
    private Timer tank_patrons = new Timer(600, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            ps.add(new Patrons(tank.getX()+125, tank.getY()+23,0,true));
        }
    });
    private Timer _move = new Timer(1, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            plane.movedown();
            plane.moveup();
            tank.movedown();
            tank.moveup();
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
                if(!tank.die) {
                    BOSS.start();
                    boss_patrons.start();
                }
                if(!plane.die) {
                    BOSS2.start();
                    boss2_patrons.start();
                }
                BOSS_TIME.start();
            }
            if(boss_time){
                BOSS.stop();
                boss_patrons.stop();
                BOSS2.stop();
                boss2_patrons.stop();
                boss.moveback();
                boss2.moveback();
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
            collisionwithbomb();
            collisionwithrockets();
            collisionwithtank();
            collisionwithplane();
            collisionwithbonus_plane();
            collisionwithbonus_tank();
            collisionwithplayer();
            collisionwithplayer2();
            _remove();
        }
    });

    private Timer _bonus1_plane = new Timer(80000, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            bon.add(new Bonus(1000, rand.nextInt(150)+40,0,true));
        }
    });
    private Timer _bonus1_tank = new Timer(96698, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            bon.add(new Bonus(1000,280+ rand.nextInt(250),0,true));
        }
    });
    private Timer _bonus2_plane = new Timer(17000, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            bon.add(new Bonus(1000, rand.nextInt(150)+40,1,true));
        }
    });
    private Timer _bonus2_tank = new Timer(25000, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            bon.add(new Bonus(1000,280+ rand.nextInt(250),1,true));
        }
    });
    private Timer _bonus3_plane = new Timer(100000, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            bon.add(new Bonus(1000, rand.nextInt(150)+40,2,true));
        }
    });
    private Timer _bonus3_tank = new Timer(116677, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            bon.add(new Bonus(1000,280+ rand.nextInt(250),2,true));
        }
    });
    private Timer bonus_time = new Timer(15000, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            bonus_time.stop();
            bonus1_plane=false;
            bonus1_tank=false;
        }
    });
    private void _remove() {
        for (int i = 0; i < em.size(); i++) {
            if (em.get(i).c.x==-100||em.get(i).active==false ) {
                em.remove(i);
            }
        }
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
        for(int i=0;i<hr.size();i++){
            if(!hr.get(i).active) {
                hr.remove(i);
            }
        }
        for(int i=0;i<hr1.size();i++){
            if(!hr1.get(i).active) {
                hr1.remove(i);
            }
        }
        for(int i=0;i<ur.size();i++) {
            if (ur.get(i).c.x > 500) {
                collision(2);
                ur.remove(i);
                greatbl1.add(new Blow(200, -250));
                em1.clear();
            }else if(ur.get(i).c.y > 400){
                ur.remove(i);
                collision(2);
                greatbl1.add(new Blow(200, 0));
                em.clear();
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
    private void addHeartsTank() {
        int xP = 650;
        for (int i = 0; i < tank.hp; i++) {
            hr1.add(new Hearts(xP += 30, 260,true));
        }
    }
    private void collisionwithbomb() {
         for (int j = 0; j < ps.size(); j++) {
            for (int i = 0; i < em.size(); i++) {
                    if (ps.get(j).c.x > em.get(i).c.x + 30 && ps.get(j).c.y > em.get(i).c.y && ps.get(j).c.y < em.get(i).c.y + 30&&ps.get(j).active&&em.get(i).active)
                    {
                        collision(1);
                        bl.add(new Blow(em.get(i).c.x,em.get(i).c.y));
                        ps.get(j).active=false;
                        em.get(i).active=false;
                        lvl.score1 += 10;
                    }
                }
            }
    }
    private void collisionwithplayer() {
        for (int j = hr.size() - 1; j >= 0; j--) {
            for (int i = 0; i < ps1.size(); i++) {
                if (plane.getX() + 100 > ps1.get(i).c.x && plane.getX() < ps1.get(i).c.x && plane.getY() + 50 > ps1.get(i).c.y && plane.getY() < ps1.get(i).c.y&&ps1.get(i).active&&hr.get(j).active) {
                    if (!bonus1_plane) {
                        hr.get(j).active=false;
                        ps1.get(i).active=false;
                        collision(1);
                    }
                    if (j==0 && !bonus1_plane) {
                        plane.die = true;
                        collision(2);
                        greatbl.add(new Blow(plane.getX() - 50, plane.getY() - 70));
                        if (j==0 & tank.die) {
                            game_over();
                        }
                    }
                }
            }
        }
    }
    private void collisionwithplayer2() {
        for (int j = hr1.size() - 1; j >= 0; j--) {
            for (int i = 0; i < ps1.size(); i++) {
                if (tank.getX() + 100 > ps1.get(i).c.x && tank.getX() < ps1.get(i).c.x && tank.getY() + 50 > ps1.get(i).c.y && tank.getY() < ps1.get(i).c.y&&hr1.get(j).active&&ps1.get(i).active) {
                    if (!bonus1_tank) {
                        hr1.get(j).active=false;
                        ps1.get(i).active=false;
                        collision(1);
                    }
                    if (j==0 && !bonus1_tank) {
                        tank.die = true;
                        collision(2);
                        greatbl.add(new Blow(tank.getX() - 50, tank.getY() - 70));
                        if (j==0 & plane.die) {
                            game_over();
                        }
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
    public void collisionwithtank() {
        for (int j=hr1.size()-1;j>=0; j--) {
            for (int i = 0; i < em.size(); i++) {
                if (tank.getX() + 130 > em.get(i).c.x && tank.getX() < em.get(i).c.x + 130 && tank.getY() + 50 > em.get(i).c.y && tank.getY() < em.get(i).c.y&&hr1.get(j).active&&em.get(i).active) {
                    if(!bonus1_tank) {
                        hr1.get(j).active=false;
                        em.get(i).active=false;
                        collision(1);
                        bl.add(new Blow(em.get(i).c.x, em.get(i).c.y));
                    }
                    if(j==0&&!bonus1_tank){
                        tank.die=true;
                        collision(2);
                        greatbl.add(new Blow(tank.getX()-50,tank.getY()-70));
                        if(j==0 &plane.die){
                            game_over();
                        }
                    }
                }
            }
        }
    }
    public void collisionwithplane() {
        for (int j=hr.size()-1;j>=0; j--){
        for (int i = 0; i < em1.size(); i++) {
                if (plane.getX() + 100 > em1.get(i).c.x && plane.getX() < em1.get(i).c.x + 100 && plane.getY() + 50 > em1.get(i).c.y && plane.getY() < em1.get(i).c.y&&hr.get(j).active&&em1.get(i).active) {
                    if(!bonus1_plane) {
                        hr.get(j).active=false;
                        em1.get(i).active=false;
                        bl.add(new Blow(em1.get(i).c.x, em1.get(i).c.y));
                        collision(1);
                    }
                    if(j==0&&!bonus1_plane){
                        plane.die=true;
                        collision(2);
                        greatbl.add(new Blow(plane.getX()-50,plane.getY()-70));
                        if(j==0 &tank.die){
                            game_over();
                        }
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
    private void collisionwithbonus_tank() {
        for (int i = 0; i < bon.size(); i++) {
            if (tank.getX() + 100 > bon.get(i).c.x && tank.getX() < bon.get(i).c.x + 100 && tank.getY() + 50 > bon.get(i).c.y && tank.getY() < bon.get(i).c.y&&bon.get(i).active) {
                if (bon.get(i).skin == 0&&!tank.die) {
                    collision(3);
                    bonus1_tank = true;
                    bon.get(i).active=false;
                    bonus_time.start();
                    def.add(new Deffender(tank.getX(), tank.getY()));
                    bon1.add(new Bonus(150,255,0,true));
                }
                if (bon.get(i).skin == 1&&!tank.die) {
                    collision(3);
                    bon.get(i).active=false;
                    lvl.score1 += 10000;
                }
                if (bon.get(i).skin == 2&&!tank.die) {
                    collision(3);
                    bon.get(i).active=false;
                    ur.add(new UnionRocket(500,-10,1));
                }
            }
        }
    }
    private void game_over() {
        enemy_move.stop();
        enemy.stop();
        bonus_move.stop();
        patrons_move_1.stop();
        patrons_move.stop();
        tank_patrons.stop();
        plane_patrons.stop();
        _repaint.stop();
        _move.stop();
    }
    public void paint(Graphics g) {
        super.paint(g);
        ImageIcon ii = new ImageIcon(this.getClass().getResource("background.png"));
        g.drawImage(ii.getImage(), 0, 0, this);
        if (!plane.die) {
            g.drawImage(plane.image, plane.getX(), plane.getY(), 100, 40, this);
        }
        if (!tank.die) {
            g.drawImage(tank.image, tank.getX(), tank.getY(), 140, 60, this);
        }
        if(!boss.bossdie) {
            g.drawImage(boss.image, boss.c.x, boss.c.y, 140, 60, this);
        }

        if(!boss2.bossdie) {
            g.drawImage(boss2.image, boss2.c.x, boss2.c.y, 140, 60, this);
        }
        g.setFont(new Font("TimesRoman", Font.BOLD, 12));
        g.setColor(Color.RED);
        g.drawString("HEALTH:", 630, 25);
        g.drawString("HEALTH: ", 630, 275);
        g.drawString("LVL:" + lvl.lvl + "", 380, 20);
        g.drawString("SCORE:" + lvl.score2 + "", 10, 20);
        g.drawString("SCORE:" + lvl.score1 + "", 10, 270);
        g.drawString("BONUS:", 100, 270);
        g.drawString("BONUS:", 100, 20);
        if (plane.die & tank.die) {
            Font f = new Font("TimesRoman", Font.BOLD, 35);
            g.setFont(f);
            g.drawString("GAME OVER!", 280, 270);
        }
        if(pause){
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
        if (!em.isEmpty()) {
            for (int i = 0; i < em.size(); i++) {
                Enemy e = em.get(i);
                g.drawImage(e.image, e.c.x, e.c.y, 25, 25, this);
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
        if(!hr1.isEmpty()) {
            for (int i = 0; i < hr1.size(); i++) {
                Hearts h1 = hr1.get(i);
                g.drawImage(h1.image, h1.c.x, h1.c.y, 20, 20, this);
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
                if (bonus1_tank) {
                    g.drawImage(d.image, tank.getX() - 20, tank.getY() - 65, 200, 200, this);
                }
            }
        }
        if(!bon1.isEmpty()) {
            for (int i = 0; i < bon1.size(); i++) {
                Bonus b = bon1.get(i);
                if (bonus1_tank) {
                    g.drawImage(b.image, 150, 255, 20, 20, this);
                }
                if (bonus1_plane) {
                    g.drawImage(b.image, 150, 6, 20, 20, this);
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
    public void run() {
        GameScreen t1 = new GameScreen();
        new Thread((Runnable) t1).start();
    }
    private class TAdapter extends KeyAdapter {
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            plane.keyReleased(e);
            tank.keyReleased(e);
            if(key == KeyEvent.VK_ENTER){
               plane_patrons.stop();

            }
            if(key == KeyEvent.VK_H){
               tank_patrons.stop();
            }
        }
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            plane.keyPressed(e);
            tank.keyPressed(e);
            if(key == KeyEvent.VK_ENTER&&!plane.die){
                plane_patrons.start();
            }
            if(key == KeyEvent.VK_H&&!tank.die){
                tank_patrons.start();
            }
            if(key == KeyEvent.VK_P){
                enemy_move.stop();
                enemy.stop();
                bonus_move.stop();
                patrons_move_1.stop();
                patrons_move.stop();
                tank_patrons.stop();
                plane_patrons.stop();
                _repaint.stop();
                _move.stop();
                pause=true;
            }
            if(key == KeyEvent.VK_SPACE){
                enemy_move.start();
                enemy.start();
                bonus_move.start();
                patrons_move_1.start();
                patrons_move.start();
                _repaint.start();
                _move.start();
                pause=false;
            }
        }
    }
}