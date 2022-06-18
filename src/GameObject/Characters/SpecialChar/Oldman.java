package GameObject.Characters.SpecialChar;

public class Oldman extends Shop{
    public int attack;
    public int defence;
    public int experience;

    public void init(){
        title = "经验换力量";
        firstLine = "升一级  "+experience+" 经验 (d)";
        secondLine = "攻击+"+attack+"(a)";
        thirdLine = "防御+"+defence+"(s)";
    }



}
