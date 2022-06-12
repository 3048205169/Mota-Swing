package GameObject.Characters;

import javax.swing.*;

public class Hero extends NPC{
    public int level;
    public int life;
    public int attack;
    public int defence;
    public int coin;
    public int experience;
    public int yellowKey;
    public int blueKey;
    public int redKey;
    public boolean handbook;
    public boolean cross;
    public boolean fly;

    public Hero() {
        this.level = 0;
        this.life = 1000;
        this.attack = 100;
        this.defence = 100;
        this.coin = 0;
        this.experience = 0;
        this.yellowKey = 100;
        this.blueKey = 100;
        this.redKey = 100;
        this.handbook = false;
        this.cross=  false;
        this.fly = false;
    }

}
