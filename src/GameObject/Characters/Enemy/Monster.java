package GameObject.Characters.Enemy;

import GameObject.Characters.NPC;


public class Monster extends NPC {
    public int life;
    public int attack;
    public int defence;
    public int coin;
    public int experience;

    public Monster(){
        life = 50;
        attack = 50;
        coin = 50;
        experience = 50;
        defence = 50;
    }

}
