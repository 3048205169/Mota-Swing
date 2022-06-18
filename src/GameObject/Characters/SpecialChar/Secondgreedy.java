package GameObject.Characters.SpecialChar;

import javax.swing.*;

public class Secondgreedy extends Greedy{
    public Secondgreedy(){
        isOpen = false;
        life = 2000;
        attack = 20;
        defence = 20;
        coin = 100;
        title = "献祭"+coin+"金币变强";
        firstLine = "生命+"+life+"(d)";
        secondLine = "攻击+"+attack+"(s)";
        thirdLine = "防御+"+defence+"(a)";
    }
}
