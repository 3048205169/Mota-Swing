package GameObject.Characters;

import javax.swing.*;

public class Hero extends NPC{
    public int level;
    public int life;
    public int attack;
    public int defence;
    public int coin;
    public int experience;
    public int yellowkey;
    public int bluekey;
    public int redkey;
    public boolean handbook;
    public boolean cross;
    public boolean fly;
    public boolean hook;
    public boolean holywater;

    public Hero() {
        this.level = 0;
        this.life = 1000;
        this.attack = 1000;
        this.defence = 1000;
        this.coin = 0;
        this.experience = 0;
        this.yellowkey = 100;
        this.bluekey = 100;
        this.redkey = 100;
        this.handbook = true;
        this.cross=  false;
        this.fly = true;
        this.hook = false;
        this.holywater = true;
    }

}
