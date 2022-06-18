package GameObject.Characters.SpecialChar;

import javax.swing.*;
import java.awt.*;

public class Firstgreedy extends Greedy{
    public Firstgreedy(){
        isOpen = false;
        life = 400;
        attack = 4;
        defence = 4;
        coin = 25;
        title = "献祭"+coin+"金币变强";
        firstLine = "生命+"+life+"(d)";
        secondLine = "攻击+"+attack+"(s)";
        thirdLine = "防御+"+defence+"(a)";
    }
}
