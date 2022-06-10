package GameObject.Characters.Enemy;

import GameObject.Characters.NPC;


public class Monster extends NPC {
    public int life;
    public int attack;
    public int defence;
    public int coin;
    public int experience;

    public Monster(){
        life = 5;
        attack = 5;
        coin = 5;
        experience = 5;
        defence = 5;
    }

}
