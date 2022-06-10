package gamePanel;

import GameObject.Characters.SpecialChar.Celestial;
import GameObject.Characters.Hero;
import GameObject.Matter.Door.*;
import GameObject.Matter.Stairs.DownStair;
import GameObject.Matter.Stairs.Upstair;
import GameObject.Matter.Wall.DarkStar;
import GameObject.Matter.Wall.Lava;
import GameObject.Matter.Wall.WallCell;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class testing {
    int floorNum = 0;
    JLabel floorLabel = new JLabel();

    JFrame gameFrame = new JFrame();
    JLabel bottom = new JLabel();
    JPanel gamePanel = new JPanel();
    JLabel bgLabel = new JLabel();
    static int[][][] floor = new int[21][11][11];
    Hero hero = new Hero();

    Celestial celestial = new Celestial();
    YellowDoor[][][]yellowDoors = new YellowDoor[21][11][11];
    BlueDoor[][][]blueDoors = new BlueDoor[21][11][11];
    RedDoor[][][]redDoors = new RedDoor[21][11][11];

    List<WallCell> wallCells = new ArrayList<>();
    List<Lava> lavas = new ArrayList<>();
    List<DarkStar>darkStars = new ArrayList<>();

    Upstair[][][]upstairs = new Upstair[21][11][11];
    DownStair[][][]downStairs = new DownStair[21][11][11];



    JLabel[]attributeLabels = new JLabel[9];


    public final int BLANK = 0;
    public final int CELL = -1;
    public final int HERO = 1;
    public final int CELESTIAL = 2;

    public final int YELLOWDOOR = 10;
    public final int BLUEDOOR = 20;
    public final int REDDOOR = 30;

    public final int LAVA = 40;
    public final int DARKSTAR = 50;

    public final int UPSTAIR = 60;
    public final int DOWNSTAIR = 70;

    static Map<String,Integer> cellMap = new HashMap<>();

    public static void main(String[] args) {
        int i=0;
        int j=0;
        int k=0;
        File floorTxt = new File("src/gamePanel/floor.txt");
        try{
            BufferedReader br = new BufferedReader(new FileReader(floorTxt));
            String s = null;
            while((s=br.readLine())!=null){
                //在这里进行读取
                if(s.equals("")){
                    continue;
                }else if(s.startsWith("#")){
                    continue;
                }
                String s_new = s.toLowerCase();
                cellMap.put(s_new,0);
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(cellMap);
    }



}
