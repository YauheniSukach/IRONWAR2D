public class Level {
    public int lvl;
    public double speed;
    public int score1;
    public int score2;
    public int respawn;

public Level(){
    lvl=1;
    speed=1;
    score1=0;
    score2=0;
    respawn=900;
}
public void next(){
    lvl+=1;
    if(lvl%3==0) {
        speed += 1;
    }
    respawn-=100;
}
}
