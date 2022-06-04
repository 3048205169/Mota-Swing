package Characters;

import javax.swing.*;

public class Hero {
    public int level;
    public int life;
    public int attack;
    public int defence;
    public int coin;
    public int experience;
    public int yellowKey;
    public int blueKey;
    public int redKey;
    public JLabel heroLabel;

    public int x;
    public int y;
    public int z;

    public Hero() {
        this.level = 0;
        this.life = 100;
        this.attack = 10;
        this.defence = 10;
        this.coin = 0;
        this.experience = 0;
        this.yellowKey = 100;
        this.blueKey = 100;
        this.redKey = 100;
    }

    public Hero(int level, int life, int attack, int defence, int coin, int experience, int yellowKey, int blueKey, int redKey) {
        this.level = level;
        this.life = life;
        this.attack = attack;
        this.defence = defence;
        this.coin = coin;
        this.experience = experience;
        this.yellowKey = yellowKey;
        this.blueKey = blueKey;
        this.redKey = redKey;
    }
}
