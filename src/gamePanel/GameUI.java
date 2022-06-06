package gamePanel;
import Characters.Celestial.Celestial;
import Characters.Enemy.*;
import Characters.Hero;
import Matter.Door.*;
import Matter.Key.BlueKey;
import Matter.Key.RedKey;
import Matter.Key.YellowKey;
import Matter.Stairs.DownStair;
import Matter.Stairs.Upstair;
import Matter.Wall.DarkStar;
import Matter.Wall.Lava;
import Matter.Wall.WallCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameUI{

    int floorNum = 0;
    JLabel floorLabel = new JLabel();

    Map<String,Integer> cellMap = new HashMap<>();

    JFrame gameFrame = new JFrame();
    JLabel bottom = new JLabel();
    JPanel gamePanel = new JPanel();
    JLabel bgLabel = new JLabel();
    int[][][] floor = new int[21][11][11];

    Hero hero = new Hero();
    Celestial[][][]celestials = new Celestial[21][11][11];
    GreenSlime[][][]greenSlimes = new GreenSlime[21][11][11];
    Skeleton[][][]skeletons = new Skeleton[21][11][11];
    SkeletonSoldier[][][]skeletonSoldiers = new SkeletonSoldier[21][11][11];
    RedSlime[][][]redSlimes = new RedSlime[21][11][11];


    YellowDoor[][][]yellowDoors = new YellowDoor[21][11][11];
    BlueDoor[][][]blueDoors = new BlueDoor[21][11][11];
    RedDoor[][][]redDoors = new RedDoor[21][11][11];


    RedKey[][][]redKeys = new RedKey[21][11][11];
    BlueKey[][][]blueKeys = new BlueKey[21][11][11];
    YellowKey[][][]yellowKeys = new YellowKey[21][11][11];

    WallCell[][][]wallCells = new WallCell[21][11][11];
    Lava[][][]lavas = new Lava[21][11][11];
    DarkStar[][][]darkStars = new DarkStar[21][11][11];


    Upstair[][][]upstairs = new Upstair[21][11][11];
    DownStair[][][]downStairs = new DownStair[21][11][11];



    JLabel[]attributeLabels = new JLabel[9];

    public final int UP =  1;
    public final int DOWN = -1;
    public final int LEFT = 2;
    public final int RIGHT = -2;


    public final int BLANK = 0;
    public final int HERO = 1;
    public final int CELESTIAL = 2;

    public final int YELLOWDOOR = 10;
    public final int BLUEDOOR = 11;
    public final int REDDOOR = 12;

    public final int CELL = 20;
    public final int LAVA = 21;
    public final int DARKSTAR = 22;

    public final int UPSTAIR = 30;
    public final int DOWNSTAIR = 31;

    public final int ITEM = 40;
    public final int YELLOWKEY = 50;
    public final int REDKEY = 51;
    public final int BLUEKEY = 52;

    public final int BLUEBOTTLE = 61;
    public final int REDBOTTLE = 62;

    public final int BLUEDIAMOND = 71;
    public final int REDDIAMOND = 72;

    public final int MONSTER = -1;
    public final int GREENSLIME = 101;
    public final int SKELETON = 102;
    public final int SKELETONSOLDIER = 103;
    public final int REDSLIME = 104;



    public GameUI(){
        initCellMap();
        setFloor();//设置棋盘，[21][11][11]每一个点都放着东西，不是勇士就是墙就是敌人或者是道具
        JFrame.setDefaultLookAndFeelDecorated(true);
        gameFrame.setSize(820,600);
        setAttributeLabels();
        setGameFrame();//设置gameFrame，比如大小，高度，还有键盘的触发事件
        setBgLael();
        setBottom();

        setCells();//设置所有的棋盘上的格子
        addComponent2Floor();//将人物还有道具全部都加进bottom里面
        showFloor();//把所有的墙还有人物还有道具全部都显示出来
        setHeroStatus();//设置英雄的状态
        setFloorNum();//设置楼层功能表里面的楼层数

        bgLabel.add(bottom);
        gamePanel.add(bgLabel);
        gameFrame.setContentPane(gamePanel);


    }

    public void initCellMap(){
        cellMap.put("item",ITEM);
        cellMap.put("blank",BLANK);
        cellMap.put("yellowkey",YELLOWKEY);
        cellMap.put("downstair",DOWNSTAIR);
        cellMap.put("bluebottle",BLUEBOTTLE);
        cellMap.put("redkey",REDKEY);
        cellMap.put("celestial",CELESTIAL);
        cellMap.put("bluekey",BLUEKEY);
        cellMap.put("cell",CELL);
        cellMap.put("upstair",UPSTAIR);
        cellMap.put("reddiamond",REDDIAMOND);
        cellMap.put("monster",MONSTER);
        cellMap.put("greenslime",GREENSLIME);
        cellMap.put("reddoor",REDDOOR);
        cellMap.put("bluediamond",BLUEDIAMOND);
        cellMap.put("lava",LAVA);
        cellMap.put("redbottle",REDBOTTLE);
        cellMap.put("darkstar",DARKSTAR);
        cellMap.put("yellowdoor",YELLOWDOOR);
        cellMap.put("hero",HERO);
        cellMap.put("skeleton",SKELETON);
        cellMap.put("skeletonsoldier",SKELETONSOLDIER);
        cellMap.put("redslime", REDSLIME);

    }



    private void setBottom(){
        //设置bottom
        bottom.setLocation(200,-55);
        ImageIcon bottomIcon = new ImageIcon("src/imageResource/BlankBg.png");
        bottomIcon.setImage(bottomIcon.getImage().getScaledInstance(374,370,1));
        bottom.setIcon(bottomIcon);
        bottom.setSize(500,560);
        bottom.setLocation(199,-60);
        bottom.setVisible(true);

    }

    private void setBgLael() {
        //只设置bgLabel
        bgLabel.setLocation(0,0);
        ImageIcon bgIcon = new ImageIcon("src/imageResource/GameBg.png");
        bgIcon.setImage(bgIcon.getImage().getScaledInstance(605,438,1));
        bgLabel.setIcon(bgIcon);
        bgLabel.setSize(520,520);
    }



    public void setAttributeLabels() {
        int[]yCoor = new int[]{-85,-55,-30,-5,25,50,90,125,155};

        for (int i=0;i<hero.getClass().getDeclaredFields().length-4;i++){
            JLabel attributeLabel = new JLabel();
            attributeLabel.setLocation(100,yCoor[i]);
            attributeLabel.setSize(200,300);
            attributeLabel.setVisible(true);
            attributeLabel.setFont(new Font("宋体", Font.PLAIN, 20));
            attributeLabel.setForeground(Color.WHITE);
            try {
                attributeLabel.setText(""+hero.getClass().getDeclaredFields()[i].getInt(hero));
            }catch (Exception e){
                e.printStackTrace();
            }
            attributeLabel.setVisible(true);
            bgLabel.add(attributeLabel);
            attributeLabels[i]=attributeLabel;
        }
    }


    public void setFloor() {
        //从floor.txt当中读取数据
        int i=0;
        int j=0;
        int k=0;
        File floorTxt = new File("src/gamePanel/floor.txt");
        try{
            BufferedReader br = new BufferedReader(new FileReader(floorTxt));
            String s = null;
            while((s=br.readLine())!=null){
                //在这里进行读取
                String line = s.toLowerCase();
                if(s.equals("")){
                    continue;
                }else if(s.startsWith("#")){
                    continue;
                }
                floor[i][j][k] = cellMap.get(line);
                k=k+1;
                if(k==11){
                    j=j+1;
                    k=0;
                }
                if(j==11){
                    i=i+1;
                    j=0;
                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }



        //第一层
//        floor[0][0][0] = CELL;
//        floor[0][0][1] = CELL;
//        floor[0][0][2] = CELL;
//        floor[0][0][3] = CELL;
//        floor[0][0][4] = CELL;
//        floor[0][0][5] = CELL;
//        floor[0][0][6] = CELL;
//        floor[0][0][7] = CELL;
//
//        floor[0][10][0] = CELL;
//        floor[0][10][1] = CELL;
//        floor[0][10][2] = CELL;
//        floor[0][10][3] = CELL;
//        floor[0][10][4] = CELL;
//        floor[0][10][5] = CELL;
//        floor[0][10][6] = CELL;
//        floor[0][10][7] = CELL;
//
//        floor[0][4][0] = CELL;
//        floor[0][4][1] = CELL;
//        floor[0][4][2] = CELL;
//        floor[0][4][3] = CELL;
//        floor[0][4][4] = CELL;
//        floor[0][4][5] = CELL;
//        floor[0][4][6] = CELL;
//        floor[0][4][7] = CELL;
//
//        floor[0][6][0] = CELL;
//        floor[0][6][1] = CELL;
//        floor[0][6][2] = CELL;
//        floor[0][6][3] = CELL;
//        floor[0][6][4] = CELL;
//        floor[0][6][5] = CELL;
//        floor[0][6][6] = CELL;
//        floor[0][6][7] = CELL;
//
//        floor[0][0][7] = CELL;
//        floor[0][1][7] = CELL;
//        floor[0][2][7] = CELL;
//        floor[0][3][7] = CELL;
//        floor[0][4][7] = CELL;
//        floor[0][5][7] = YELLOWDOOR;
//        floor[0][6][7] = CELL;
//        floor[0][7][7] = CELL;
//        floor[0][8][7] = CELL;
//        floor[0][9][7] = CELL;
//        floor[0][10][7] = CELL;
//
//        floor[0][1][6] = CELL;
//        floor[0][1][8] = CELL;
//
//        floor[0][9][6] = CELL;
//        floor[0][9][8] = CELL;
//
//        floor[0][3][8] = CELL;
//        floor[0][7][8] = CELL;
//
//
//        floor[0][5][9] = HERO;
//        floor[0][4][8] = CELESTIAL;
//
//
//
//        floor[0][0][9] = LAVA;
//        floor[0][1][9] = LAVA;
//        floor[0][2][9] = LAVA;
//        floor[0][3][9] = LAVA;
//        floor[0][4][9] = LAVA;
//
//
//        floor[0][0][10] = LAVA;
//        floor[0][1][10] = LAVA;
//        floor[0][2][10] = LAVA;
//        floor[0][3][10] = LAVA;
//        floor[0][4][10] = LAVA;
//
//        floor[0][6][9] = LAVA;
//        floor[0][7][9] = LAVA;
//        floor[0][8][9] = LAVA;
//        floor[0][9][9] = LAVA;
//        floor[0][10][9] = LAVA;
//
//
//        floor[0][6][10] = LAVA;
//        floor[0][7][10] = LAVA;
//        floor[0][8][10] = LAVA;
//        floor[0][9][10] = LAVA;
//        floor[0][10][10] = LAVA;
//
//        floor[0][0][8] = LAVA;
//        floor[0][2][8] = LAVA;
//
//        floor[0][8][8] = LAVA;
//        floor[0][10][8] = LAVA;
//
//        floor[0][1][0] = DARKSTAR;
//        floor[0][1][1] = DARKSTAR;
//        floor[0][1][2] = DARKSTAR;
//        floor[0][1][3] = DARKSTAR;
//        floor[0][1][4] = DARKSTAR;
//        floor[0][1][5] = DARKSTAR;
//
//        floor[0][2][0] = DARKSTAR;
//        floor[0][2][1] = DARKSTAR;
//        floor[0][2][2] = DARKSTAR;
//        floor[0][2][3] = DARKSTAR;
//        floor[0][2][4] = DARKSTAR;
//        floor[0][2][5] = DARKSTAR;
//        floor[0][2][6] = DARKSTAR;
//
//        floor[0][3][0] = DARKSTAR;
//        floor[0][3][1] = DARKSTAR;
//        floor[0][3][2] = DARKSTAR;
//        floor[0][3][3] = DARKSTAR;
//        floor[0][3][4] = DARKSTAR;
//        floor[0][3][5] = DARKSTAR;
//        floor[0][3][6] = DARKSTAR;
//
//        floor[0][9][0] = DARKSTAR;
//        floor[0][9][1] = DARKSTAR;
//        floor[0][9][2] = DARKSTAR;
//        floor[0][9][3] = DARKSTAR;
//        floor[0][9][4] = DARKSTAR;
//        floor[0][9][5] = DARKSTAR;
//
//        floor[0][8][0] = DARKSTAR;
//        floor[0][8][1] = DARKSTAR;
//        floor[0][8][2] = DARKSTAR;
//        floor[0][8][3] = DARKSTAR;
//        floor[0][8][4] = DARKSTAR;
//        floor[0][8][5] = DARKSTAR;
//        floor[0][8][6] = DARKSTAR;
//
//        floor[0][7][0] = DARKSTAR;
//        floor[0][7][1] = DARKSTAR;
//        floor[0][7][2] = DARKSTAR;
//        floor[0][7][3] = DARKSTAR;
//        floor[0][7][4] = DARKSTAR;
//        floor[0][7][5] = DARKSTAR;
//        floor[0][7][6] = DARKSTAR;
//
//        floor[0][5][0] = UPSTAIR;
//
//
//
//
//
//        floor[1][5][10] = DOWNSTAIR;
//
//        floor[1][3][1] = CELL;
//        floor[1][3][2] = CELL;
//        floor[1][3][3] = CELL;
//        floor[1][3][4] = CELL;
//        floor[1][3][5] = CELL;
//        floor[1][3][6] = CELL;
//        floor[1][3][7] = CELL;
//        floor[1][3][8] = CELL;
//        floor[1][3][9] = CELL;
//        floor[1][3][10] = CELL;
//
//        floor[1][0][1] = CELL;
//        floor[1][1][1] = CELL;
//        floor[1][2][1] = CELL;
//        floor[1][3][1] = CELL;
//        floor[1][4][1] = CELL;
//        floor[1][5][1] = CELL;
//        floor[1][6][1] = CELL;
//        floor[1][7][1] = CELL;
//        floor[1][8][1] = CELL;
//        floor[1][9][1] = CELL;



    }



    private void updateStatus() {
        for (int i=0;i<hero.getClass().getDeclaredFields().length-4;i++){
            try{
                attributeLabels[i].setText(""+hero.getClass().getDeclaredFields()[i].getInt(hero));
            }catch (Exception e){
                e.printStackTrace();
            }

        }


    }



    private void addComponent2Floor(){
        bottom.add(hero.heroLabel);
        //todo
//        bottom.add(celestial.celestialLabel);

        for(int i=0;i<21;i++){//楼层
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(celestials[i][j][k]!=null){
                        bottom.add(celestials[i][j][k].celestialLabel);
                    }
                }
            }
        }




        for(int i=0;i<21;i++){//楼层
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(wallCells[i][j][k]!=null){
                        bottom.add(wallCells[i][j][k].wcLabel);
                    }
                }
            }
        }


        for(int i=0;i<21;i++){//楼层
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(yellowDoors[i][j][k]!=null){
                        bottom.add(yellowDoors[i][j][k].doorLabel);
                    }
                }
            }
        }

        for(int i=0;i<21;i++){//楼层
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(redDoors[i][j][k]!=null){
                        bottom.add(redDoors[i][j][k].redDoorLabel);
                    }
                }
            }
        }


        for(int i=0;i<21;i++){//楼层
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(redKeys[i][j][k]!=null){
                        bottom.add(redKeys[i][j][k].keyLabel);
                    }
                    if(yellowKeys[i][j][k]!=null){
                        bottom.add(yellowKeys[i][j][k].keyLabel);
                    }
                }
            }
        }

        for(int i=0;i<21;i++){//楼层
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(lavas[i][j][k]!=null){
                        bottom.add(lavas[i][j][k].lavaLabel);
                    }
                }
            }
        }

        for(int i=0;i<21;i++){//楼层
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(downStairs[i][j][k]!=null){
                        bottom.add(downStairs[i][j][k].downstairLabel);
                    }
                }
            }
        }



        for(int i=0;i<21;i++){//楼层
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(upstairs[i][j][k]!=null){
                        bottom.add(upstairs[i][j][k].upstairLabel);
                    }
                }
            }
        }

        for(int i=0;i<21;i++){//楼层
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(downStairs[i][j][k]!=null){
                        bottom.add(downStairs[i][j][k].downstairLabel);
                    }
                }
            }
        }

        for(int i=0;i<21;i++){//楼层
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(greenSlimes[i][j][k]!=null){
                        bottom.add(greenSlimes[i][j][k].monsterLabel);
                    }
                }
            }
        }
        for(int i=0;i<21;i++){//楼层
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(skeletons[i][j][k]!=null){
                        bottom.add(skeletons[i][j][k].monsterLabel);
                    }
                }
            }
        }
        for(int i=0;i<21;i++){//楼层
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(skeletonSoldiers[i][j][k]!=null){
                        bottom.add(skeletonSoldiers[i][j][k].monsterLabel);
                    }
                }
            }
        }

        for(int i=0;i<21;i++){//楼层
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(redSlimes[i][j][k]!=null){
                        bottom.add(redSlimes[i][j][k].monsterLabel);
                    }
                }
            }
        }




    }

    private void showFloor() {
        for(int i=0;i<21;i++){
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(wallCells[i][j][k]!=null){
                        if(i==floorNum){
                            wallCells[i][j][k].wcLabel.setVisible(true);
                        }else{
                            wallCells[i][j][k].wcLabel.setVisible(false);
                        }
                    }
                }
            }
        }


        for(int i=0;i<21;i++){
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(yellowDoors[i][j][k]!=null){
                        if(i==floorNum){
                            yellowDoors[i][j][k].doorLabel.setVisible(true);
                        }else{
                            yellowDoors[i][j][k].doorLabel.setVisible(false);
                        }
                    }
                }
            }
        }

        for(int i=0;i<21;i++){
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(redDoors[i][j][k]!=null){
                        if(i==floorNum){
                            redDoors[i][j][k].redDoorLabel.setVisible(true);
                        }else{
                            redDoors[i][j][k].redDoorLabel.setVisible(false);
                        }
                    }
                }
            }
        }

        for(int i=0;i<21;i++){
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(redKeys[i][j][k]!=null){
                        if(i==floorNum){
                            redKeys[i][j][k].keyLabel.setVisible(true);
                        }else{
                            redKeys[i][j][k].keyLabel.setVisible(false);
                        }
                    }
                }
            }
        }

        for(int i=0;i<21;i++){
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(yellowKeys[i][j][k]!=null){
                        if(i==floorNum){
                            yellowKeys[i][j][k].keyLabel.setVisible(true);
                        }else{
                            yellowKeys[i][j][k].keyLabel.setVisible(false);
                        }
                    }
                }
            }
        }
        for(int i=0;i<21;i++){
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(lavas[i][j][k]!=null){
                        if(i==floorNum){
                            lavas[i][j][k].lavaLabel.setVisible(true);
                        }else{
                            lavas[i][j][k].lavaLabel.setVisible(false);
                        }
                    }
                }
            }
        }

        for(int i=0;i<21;i++){
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(darkStars[i][j][k]!=null){
                        if(i==floorNum){
                            darkStars[i][j][k].darkStarLabel.setVisible(true);
                        }else{
                            darkStars[i][j][k].darkStarLabel.setVisible(false);
                        }
                    }
                }
            }
        }




        for(int i=0;i<21;i++){//楼层
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(upstairs[i][j][k]!=null){
                        if(i==floorNum){
                            upstairs[i][j][k].upstairLabel.setVisible(true);
                        }else {
                            upstairs[i][j][k].upstairLabel.setVisible(false);
                        }

                    }
                }
            }
        }


        for(int i=0;i<21;i++){//楼层
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(downStairs[i][j][k]!=null){
                        downStairs[i][j][k].downstairLabel.setVisible(i==floorNum);
                    }
                    if(celestials[i][j][k]!=null){
                        celestials[i][j][k].celestialLabel.setVisible(i==floorNum);
                    }
                    if(skeletons[i][j][k]!=null){
                        skeletons[i][j][k].monsterLabel.setVisible(i==floorNum);
                    }
                    if(skeletonSoldiers[i][j][k]!=null){
                        skeletonSoldiers[i][j][k].monsterLabel.setVisible(i==floorNum);
                    }
                    if(redSlimes[i][j][k]!=null){
                        redSlimes[i][j][k].monsterLabel.setVisible(i==floorNum);
                    }
                    if(greenSlimes[i][j][k]!=null){
                        greenSlimes[i][j][k].monsterLabel.setVisible(i==floorNum);
                    }
                }
            }
        }





        hero.heroLabel.setVisible(true);

    }

    private void setCells() {
        for(int i=0;i<21;i++){
            for(int j=0;j<11;j++){
                for(int k=0;k<11;k++){
                    JLabel cellLabel = new JLabel();
                    cellLabel.setSize(34,34);
                    cellLabel.setLocation(34*j,94+34*k);

                    if (floor[i][j][k]==CELL){
                        ImageIcon wallIcon = new ImageIcon("src/imageResource/Cell/1.png");
                        wallIcon.setImage(wallIcon.getImage().getScaledInstance(34,34,1));
                        cellLabel.setIcon(wallIcon);

                        WallCell wallCell = new WallCell();
                        wallCell.z = i;
                        wallCell.y = j;
                        wallCell.x = k;
                        wallCell.wcLabel = cellLabel;
                        wallCells[i][j][k]=wallCell;
                    }else if(floor[i][j][k]==CELESTIAL){
                        ImageIcon celestialIcon = new ImageIcon("src/imageResource/Celestial/1.png");
                        celestialIcon.setImage(celestialIcon.getImage().getScaledInstance(34,34,1));
                        cellLabel.setIcon(celestialIcon);

                        Celestial celestial = new Celestial();
                        celestial.z = i;
                        celestial.y = j;
                        celestial.x = k;
                        celestial.celestialLabel = cellLabel;
                        celestials[i][j][k]=celestial;

                    }else if(floor[i][j][k]==GREENSLIME){
                        ImageIcon greenSlimeIcon = new ImageIcon("src/imageResource/Monster/greenSlime.png");
                        greenSlimeIcon.setImage(greenSlimeIcon.getImage().getScaledInstance(34,34,1));
                        cellLabel.setIcon(greenSlimeIcon);
                        GreenSlime greenSlime = new GreenSlime();
                        greenSlime.z = i;
                        greenSlime.y = j;
                        greenSlime.x = k;
                        greenSlime.life = 50;
                        greenSlime.attack = 20;
                        greenSlime.defence = 1;
                        greenSlime.coin = 1;
                        greenSlime.experience = 1;
                        greenSlime.monsterLabel = cellLabel;
                        greenSlimes[i][j][k]=greenSlime;

                    }else if(floor[i][j][k]==YELLOWDOOR){
                        ImageIcon yellowDoorIcon = new ImageIcon("src/imageResource/Cell/Door/1.png");
                        yellowDoorIcon.setImage(yellowDoorIcon.getImage().getScaledInstance(34,34,1));
                        cellLabel.setIcon(yellowDoorIcon);
                        YellowDoor yellowDoor = new YellowDoor();
                        yellowDoor.z = i;
                        yellowDoor.y = j;
                        yellowDoor.x = k;
                        yellowDoor.doorLabel = cellLabel;
                        yellowDoors[i][j][k]=yellowDoor;
                    }else if(floor[i][j][k]==HERO){
                        ImageIcon heroIcon = new ImageIcon("src/imageResource/Hero/1.png");
                        heroIcon.setImage(heroIcon.getImage().getScaledInstance(34,34,1));
                        cellLabel.setIcon(heroIcon);
                        hero.heroLabel = cellLabel;
                        hero.x = k;
                        hero.y = j;
                        hero.z = i;
                    }else if(floor[i][j][k]==LAVA){
                        ImageIcon lavaIcon = new ImageIcon("src/imageResource/Cell/Door/19.png");
                        lavaIcon.setImage(lavaIcon.getImage().getScaledInstance(34,34,1));
                        cellLabel.setIcon(lavaIcon);
                        Lava lava = new Lava();
                        lava.z = i;
                        lava.y = j;
                        lava.x = k;
                        lava.lavaLabel = cellLabel;
                        lavas[i][j][k]=lava;

                    }else if(floor[i][j][k]==DARKSTAR){
                        ImageIcon lavaIcon = new ImageIcon("src/imageResource/Cell/Door/20.png");
                        lavaIcon.setImage(lavaIcon.getImage().getScaledInstance(34,34,1));
                        cellLabel.setIcon(lavaIcon);
                        DarkStar darkStar = new DarkStar();
                        darkStar.z = i;
                        darkStar.y = j;
                        darkStar.x = k;
                        darkStar.darkStarLabel = cellLabel;
                        darkStars[i][j][k]=darkStar;


                    }else if(floor[i][j][k]==UPSTAIR){
                        ImageIcon upstairIcon = new ImageIcon("src/imageResource/Cell/Stairs/1.png");
                        upstairIcon.setImage(upstairIcon.getImage().getScaledInstance(34,34,1));
                        cellLabel.setIcon(upstairIcon);
                        Upstair upstair = new Upstair();

                        upstair.z = i;
                        upstair.y = j;
                        upstair.x = k;
                        upstair.upstairLabel = cellLabel;
                        upstairs[i][j][k]=upstair;
                    }else if(floor[i][j][k]==DOWNSTAIR){
                        ImageIcon downIcon = new ImageIcon("src/imageResource/Cell/Stairs/2.png");
                        downIcon.setImage(downIcon.getImage().getScaledInstance(34,34,1));
                        cellLabel.setIcon(downIcon);
                        DownStair downStair = new DownStair();

                        downStair.z = i;
                        downStair.y = j;
                        downStair.x = k;
                        downStair.downstairLabel = cellLabel;
                        downStairs[i][j][k]=downStair;
                    }else if(floor[i][j][k]==REDDOOR){
                        ImageIcon redDoorIcon = new ImageIcon("src/imageResource/Cell/Door/4.png");
                        redDoorIcon.setImage(redDoorIcon.getImage().getScaledInstance(34,34,1));
                        cellLabel.setIcon(redDoorIcon);
                        RedDoor redDoor = new RedDoor();

                        redDoor.z = i;
                        redDoor.y = j;
                        redDoor.x = k;
                        redDoor.redDoorLabel = cellLabel;
                        redDoors[i][j][k]=redDoor;
                    }else if(floor[i][j][k]==REDKEY){
                        ImageIcon redKeyIcon = new ImageIcon("src/imageResource/Cell/Key/8.png");
                        redKeyIcon.setImage(redKeyIcon.getImage().getScaledInstance(34,34,1));
                        cellLabel.setIcon(redKeyIcon);
                        RedKey redKey = new RedKey();

                        redKey.z = i;
                        redKey.y = j;
                        redKey.x = k;
                        redKey.keyLabel = cellLabel;
                        redKeys[i][j][k]=redKey;
                    }else if(floor[i][j][k]==YELLOWKEY){
                        ImageIcon yellowKeyIcon = new ImageIcon("src/imageResource/Cell/Key/6.png");
                        yellowKeyIcon.setImage(yellowKeyIcon.getImage().getScaledInstance(34,34,1));
                        cellLabel.setIcon(yellowKeyIcon);
                        YellowKey yellowKey = new YellowKey();

                        yellowKey.z = i;
                        yellowKey.y = j;
                        yellowKey.x = k;
                        yellowKey.keyLabel = cellLabel;
                        yellowKeys[i][j][k]=yellowKey;
                    }else if(floor[i][j][k]==SKELETON){
                        ImageIcon skeletonIcon = new ImageIcon("src/imageResource/Monster/skeleton.png");
                        skeletonIcon.setImage(skeletonIcon.getImage().getScaledInstance(34,34,1));
                        cellLabel.setIcon(skeletonIcon);
                        Skeleton skeleton = new Skeleton();


                        skeleton.z = i;
                        skeleton.y = j;
                        skeleton.x = k;
                        skeleton.life = 110;
                        skeleton.attack = 25;
                        skeleton.defence = 5;
                        skeleton.coin = 5;
                        skeleton.experience = 4;
                        skeleton.monsterLabel = cellLabel;
                        skeletons[i][j][k]=skeleton;

                    }else if(floor[i][j][k]==SKELETONSOLDIER){
                        ImageIcon skeletonSoldierIcon = new ImageIcon("src/imageResource/Monster/skeletonSoldier.png");
                        skeletonSoldierIcon.setImage(skeletonSoldierIcon.getImage().getScaledInstance(34,34,1));
                        cellLabel.setIcon(skeletonSoldierIcon);
                        SkeletonSoldier skeletonSoldier = new SkeletonSoldier();


                        skeletonSoldier.z = i;
                        skeletonSoldier.y = j;
                        skeletonSoldier.x = k;
                        skeletonSoldier.life = 150;
                        skeletonSoldier.attack = 40;
                        skeletonSoldier.defence = 20;
                        skeletonSoldier.coin = 8;
                        skeletonSoldier.experience = 6;
                        skeletonSoldier.monsterLabel = cellLabel;
                        skeletonSoldiers[i][j][k]=skeletonSoldier;

                    }else if(floor[i][j][k]==REDSLIME){
                        ImageIcon redSlimeIcon = new ImageIcon("src/imageResource/Monster/redSlime.png");
                        redSlimeIcon.setImage(redSlimeIcon.getImage().getScaledInstance(34,34,1));
                        cellLabel.setIcon(redSlimeIcon);
                        RedSlime redSlime = new RedSlime();

                        redSlime.z = i;
                        redSlime.y = j;
                        redSlime.x = k;
                        redSlime.life = 70;
                        redSlime.attack = 15;
                        redSlime.defence = 2;
                        redSlime.coin = 2;
                        redSlime.experience = 2;
                        redSlime.monsterLabel = cellLabel;
                        redSlimes[i][j][k]=redSlime;

                    }

                }
            }
        }

    }



    private void setGameFrame() {
        gameFrame.setVisible(true);
        gameFrame.setSize(820,600);
        gamePanel.setLocation(0,0);
        gamePanel.setSize(820,600);
        gameFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                heroDoAction(e);

            }

            @Override
            public void keyReleased(KeyEvent e) {
            }


        });
    }

    private void heroDoAction(KeyEvent e) {
        hero.heroLabel.setVisible(false);
        if (e.getKeyCode()==KeyEvent.VK_UP){
            heroGoUp();
        }
        else if (e.getKeyCode()==KeyEvent.VK_DOWN){
            heroGoDown();
        }
        else if (e.getKeyCode()==KeyEvent.VK_LEFT){
            heroGoLeft();
        }
        else if (e.getKeyCode()==KeyEvent.VK_RIGHT){
            heroGoRight();
        }
        else{
            System.out.println("is not ready!");
        }
        hero.heroLabel.setVisible(true);
        reLoadGameFrame();
    }

    public void reLoadGameFrame(){
        //修改勇者的状态栏
        updateStatus();
        //重新显示整个界面
        showFloor();
        updateFloorLabel();
    }

    private void updateFloorLabel() {
        floorLabel.setText(""+floorNum);
    }

    private void heroGoRight() {
        hero.y = hero.y+1;
        if(hero.y<=10){
            if(floor[hero.z][hero.y][hero.x]==BLANK){//空白格子，允许站上去
                //修改英雄标签所在的位置
                hero.heroLabel.setLocation(hero.heroLabel.getLocation().x+34,hero.heroLabel.getLocation().y);
                //英雄现在站立的地方变成英雄的坐标
                floor[hero.z][hero.y][hero.x]=HERO;
                //英雄原本站立的地方变成空地
                floor[hero.z][hero.y-1][hero.x]=BLANK;

            }else if(floor[hero.z][hero.y][hero.x]==YELLOWDOOR){//黄门，允许开门
                if (hero.yellowKey>=1){//黄钥匙够
                    System.out.println("踩到黄门");
                    hero.yellowKey = hero.yellowKey-1;//黄钥匙数量-1
                    //黄门的标签直接设置为不可见
                    bottom.remove(yellowDoors[hero.z][hero.y][hero.x].doorLabel);
                    //修改英雄标签所在的位置
                    hero.heroLabel.setLocation(hero.heroLabel.getLocation().x+34,hero.heroLabel.getLocation().y);
                    //英雄现在站立的地方变成英雄的坐标
                    floor[hero.z][hero.y][hero.x]=HERO;
                    //英雄原本站立的地方变成空地
                    floor[hero.z][hero.y-1][hero.x]=BLANK;
                }else{
                    hero.y = hero.y-1;
                }


            }else {
                hero.y = hero.y-1;
            }
        }else{
            hero.y = hero.y-1;
        }
    }

    private void heroGoLeft() {
        hero.y = hero.y-1;
        if(hero.y>=0){
            if(floor[hero.z][hero.y][hero.x]==BLANK){
                hero.heroLabel.setLocation(hero.heroLabel.getLocation().x-34,hero.heroLabel.getLocation().y);
                floor[hero.z][hero.y][hero.x]=HERO;
                floor[hero.z][hero.y+1][hero.x]=BLANK;

            }else if (floor[hero.z][hero.y][hero.x]==REDKEY){//踩到红钥匙
                System.out.println("得到红钥匙");
                hero.redKey = hero.redKey+1;//
                //红钥匙的标签直接设置为空
                bottom.remove(redKeys[hero.z][hero.y][hero.x].keyLabel);
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
                //修改英雄标签所在的位置
                hero.heroLabel.setLocation(hero.heroLabel.getLocation().x-34,hero.heroLabel.getLocation().y);
                //英雄现在站立的地方变成英雄的坐标
                floor[hero.z][hero.y][hero.x]=HERO;
                //英雄原本站立的地方变成空地
                floor[hero.z][hero.y+1][hero.x]=BLANK;


            }else if (floor[hero.z][hero.y][hero.x]==YELLOWKEY){//踩到黄钥匙
                System.out.println("得到黄钥匙");
                hero.yellowKey = hero.yellowKey+1;//
                //红钥匙的标签直接设置为空
                bottom.remove(yellowKeys[hero.z][hero.y][hero.x].keyLabel);
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
                //修改英雄标签所在的位置
                hero.heroLabel.setLocation(hero.heroLabel.getLocation().x-34,hero.heroLabel.getLocation().y);
                //英雄现在站立的地方变成英雄的坐标
                floor[hero.z][hero.y][hero.x]=HERO;
                //英雄原本站立的地方变成空地
                floor[hero.z][hero.y+1][hero.x]=BLANK;


            }else if(floor[hero.z][hero.y][hero.x]==YELLOWDOOR){//黄门，允许开门
                if (hero.yellowKey>=1){//黄钥匙够
                    System.out.println("踩到黄门");
                    hero.yellowKey = hero.yellowKey-1;//黄钥匙数量-1
                    //黄门的标签直接设置为不可见
                    bottom.remove(yellowDoors[hero.z][hero.y][hero.x].doorLabel);
                    //修改英雄标签所在的位置
                    hero.heroLabel.setLocation(hero.heroLabel.getLocation().x-34,hero.heroLabel.getLocation().y);
                    //英雄现在站立的地方变成英雄的坐标
                    floor[hero.z][hero.y][hero.x]=HERO;
                    //英雄原本站立的地方变成空地
                    floor[hero.z][hero.y+1][hero.x]=BLANK;
                }else{
                    hero.y = hero.y+1;
                }


            }else if(floor[hero.z][hero.y][hero.x]==UPSTAIR){//上楼
                System.out.println("上楼");
                //楼层数+1
                floorNum = floorNum+1;
                //原本站立的地方变成空地
                floor[hero.z][hero.y+1][hero.x]=BLANK;
                //修改英雄标签所在的位置，改为上一层的下楼所在的坐标
                SetHeroCoor(true);//此方法寻找上一层的downStairs的在floor上的坐标然后选取一个可以站立的位置赋值给hero
                //将英雄的现在的位置在floor里面设置值
                floor[hero.z][hero.y][hero.x]=HERO;
                //修改英雄标签所在的位置,将其设置为新楼层的downstairs所在的位置
                //


            }else if(floor[hero.z][hero.y][hero.x]==GREENSLIME){//遇到绿色史莱姆
                System.out.println("遇到绿色史莱姆");
                //进行模拟战斗，如果能够打得过，就进行真战斗，然后设置位置等等，如果打不过，就直接退回原位
                boolean fightingResult = fight(greenSlimes[hero.z][hero.y][hero.x]);
                if (fightingResult){//打得过
                    //史莱姆的标签直接从bottom当中剔除
                    bottom.remove(greenSlimes[hero.z][hero.y][hero.x].monsterLabel);
                    //修改英雄标签所在的位置
                    hero.heroLabel.setLocation(hero.heroLabel.getLocation().x-34,hero.heroLabel.getLocation().y);
                    //英雄现在站立的地方变成英雄的坐标
                    floor[hero.z][hero.y][hero.x]=HERO;
                    //原本站立的地方变成空地
                    floor[hero.z][hero.y+1][hero.x]=BLANK;


                }else{//打不过
                    hero.y = hero.y+1;
                }


            }else if(floor[hero.z][hero.y][hero.x]==REDSLIME){//遇到红色史莱姆
                System.out.println("遇到红色史莱姆");
                //进行模拟战斗，如果能够打得过，就进行真战斗，然后设置位置等等，如果打不过，就直接退回原位
                boolean fightingResult = fight(redSlimes[hero.z][hero.y][hero.x]);
                if (fightingResult){//打得过
                    //史莱姆的标签直接从bottom当中剔除
                    bottom.remove(redSlimes[hero.z][hero.y][hero.x].monsterLabel);
                    //修改英雄标签所在的位置
                    hero.heroLabel.setLocation(hero.heroLabel.getLocation().x-34,hero.heroLabel.getLocation().y);
                    //英雄现在站立的地方变成英雄的坐标
                    floor[hero.z][hero.y][hero.x]=HERO;
                    //原本站立的地方变成空地
                    floor[hero.z][hero.y+1][hero.x]=BLANK;


                }else{//打不过
                    hero.y = hero.y+1;
                }


            }else{
                hero.y = hero.y+1;
            }
        }else{
            hero.y = hero.y+1;
        }
    }

    private boolean fight(Monster monster) {
        Hero simulateHero = new Hero() ;//模拟的英雄的数据
        simulateHero.x = hero.x;
        simulateHero.y = hero.y;
        simulateHero.z = hero.z;
        simulateHero.attack =  hero.attack;
        simulateHero.defence = hero.defence;
        simulateHero.life = hero.life;

        Monster simulateMonster = new Monster();
        simulateMonster.x = monster.x;
        simulateMonster.y = monster.y;
        simulateMonster.z = monster.z;
        simulateMonster.attack = monster.attack;
        simulateMonster.defence = monster.defence;
        simulateMonster.life = monster.life;


        if(simulateMonster.defence>=simulateHero.attack){
            return false;
        }
        while (simulateMonster.life>0){//怪物的生命值不为0的时候
            //怪物砍英雄一刀
            simulateHero.life =simulateHero.life-(simulateMonster.attack-simulateHero.defence);

            //英雄砍怪物一刀
            simulateMonster.life = simulateMonster.life-(simulateHero.attack-simulateMonster.defence);

        }
        if(simulateHero.life>0){//能够打得赢
            hero.life = simulateHero.life;
            hero.coin = hero.coin+monster.coin;
            hero.experience = hero.experience+monster.experience;
            return true;
        }else {
            return false;
        }


    }

    private void heroGoDown() {
        hero.x = hero.x+1;
        if(hero.x<=10){
            if(floor[hero.z][hero.y][hero.x]==BLANK){
                hero.heroLabel.setLocation(hero.heroLabel.getLocation().x,hero.heroLabel.getLocation().y+34);
                floor[hero.z][hero.y][hero.x]=HERO;
                floor[hero.z][hero.y][hero.x-1]=0;

            }else if(floor[hero.z][hero.y][hero.x]==DOWNSTAIR){//下楼
                System.out.println("下楼");
                //楼层数-1
                floorNum = floorNum-1;
                //原本站立的地方变成空地
                floor[hero.z][hero.y][hero.x-1]=BLANK;
                //修改英雄标签所在的位置，改为上一层的下楼所在的坐标
                SetHeroCoor(false);//此方法寻找上一层的downStairs的在floor上的坐标然后选取一个可以站立的位置赋值给hero
                //将英雄的现在的位置在floor里面设置值
                floor[hero.z][hero.y][hero.x]=HERO;
                //修改英雄标签所在的位置,将其设置为新楼层的downstairs所在的位置
                //

            }else if(floor[hero.z][hero.y][hero.x]==YELLOWDOOR){//黄门，允许开门
                if (hero.yellowKey>=1){//黄钥匙够
                    System.out.println("踩到黄门");
                    hero.yellowKey = hero.yellowKey-1;//黄钥匙数量-1
                    //黄门的标签直接设置为不可见
                    bottom.remove(yellowDoors[hero.z][hero.y][hero.x].doorLabel );
                    //修改英雄标签所在的位置
                    hero.heroLabel.setLocation(hero.heroLabel.getLocation().x,hero.heroLabel.getLocation().y+34);
                    //英雄现在站立的地方变成英雄的坐标
                    floor[hero.z][hero.y][hero.x]=HERO;
                    //英雄原本站立的地方变成空地
                    floor[hero.z][hero.y][hero.x-1]=BLANK;
                }else{
                    hero.y = hero.y-1;
                }


            }else{
                hero.x = hero.x-1;
            }
        }else{
            hero.x = hero.x-1;
        }
    }

    private void heroMove(int direction,int destination){
        //direction就是方向，有前后左右，也就是up down right left
        //destination就是目标，也就是黄门，史莱姆，墙，上下楼这些
        if(destination==BLANK){//空白格子，允许站上去

        }

    }

    private void heroGoUp() {
        hero.x = hero.x-1;
        if(hero.x>=0){
            if(floor[hero.z][hero.y][hero.x]==BLANK){//空白格子，允许站上去
//                hero.heroLabel.setLocation(hero.heroLabel.getLocation().x,hero.heroLabel.getLocation().y-34);
//
//                floor[hero.z][hero.y][hero.x]=HERO;
//                floor[hero.z][hero.y][hero.x+1]=0;
                heroMove(UP,BLANK);

            }
            else if(floor[hero.z][hero.y][hero.x]==YELLOWDOOR){//黄门，允许开门
                if (hero.yellowKey>=1){//黄钥匙够
                    System.out.println("踩到黄门");
                    hero.yellowKey = hero.yellowKey-1;//黄钥匙数量-1
                    //黄门的标签直接设置为空
                    bottom.remove(yellowDoors[hero.z][hero.y][hero.x].doorLabel);
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
                    //修改英雄标签所在的位置
                    hero.heroLabel.setLocation(hero.heroLabel.getLocation().x,hero.heroLabel.getLocation().y-34);
                    //英雄现在站立的地方变成英雄的坐标
                    floor[hero.z][hero.y][hero.x]=HERO;
                    //英雄原本站立的地方变成空地
                    floor[hero.z][hero.y][hero.x+1]=BLANK;
                }else{
                    hero.x = hero.x+1;
                }

            }
            else if(floor[hero.z][hero.y][hero.x]==REDDOOR){//红门，允许开门
                if (hero.redKey>=1){//红钥匙够
                    System.out.println("踩到红门");
                    hero.redKey = hero.redKey-1;//红钥匙数量-1
                    //从bottom当中去除此红门
                    bottom.remove(redDoors[hero.z][hero.y][hero.x].redDoorLabel);
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
                    //修改英雄标签所在的位置
                    hero.heroLabel.setLocation(hero.heroLabel.getLocation().x,hero.heroLabel.getLocation().y-34);
                    //英雄现在站立的地方变成英雄的坐标
                    floor[hero.z][hero.y][hero.x]=HERO;
                    //英雄原本站立的地方变成空地
                    floor[hero.z][hero.y][hero.x+1]=BLANK;
                }else{
                    hero.x = hero.x+1;
                }

            }
            else if(floor[hero.z][hero.y][hero.x]==UPSTAIR){//上楼
                System.out.println("上楼");
                //楼层数+1
                floorNum = floorNum+1;
                //原本站立的地方变成空地
                floor[hero.z][hero.y][hero.x+1]=BLANK;
                //修改英雄标签所在的位置，改为上一层的下楼所在的坐标
                SetHeroCoor(true);//此方法寻找上一层的downStairs的在floor上的坐标然后选取一个可以站立的位置赋值给hero
                //将英雄的现在的位置在floor里面设置值
                floor[hero.z][hero.y][hero.x]=HERO;
                //修改英雄标签所在的位置,将其设置为新楼层的downstairs所在的位置
                //


            }
            else if(floor[hero.z][hero.y][hero.x]==DOWNSTAIR){//下楼
                System.out.println("下楼");
                //楼层数-1
                floorNum = floorNum-1;
                //原本站立的地方变成空地
                floor[hero.z][hero.y][hero.x+1]=BLANK;
                //修改英雄标签所在的位置，改为上一层的下楼所在的坐标
                SetHeroCoor(false);//此方法寻找上一层的downStairs的在floor上的坐标然后选取一个可以站立的位置赋值给hero
                //将英雄的现在的位置在floor里面设置值
                floor[hero.z][hero.y][hero.x]=HERO;
                //修改英雄标签所在的位置,将其设置为新楼层的downstairs所在的位置
                //

            }else{
                hero.x = hero.x+1;
            }
        }else{
            hero.x = hero.x+1;
        }



    }


    private void SetHeroCoor(boolean upOrDown) {
        if(upOrDown==true){
            for(int j=0;j<11;j++){
                for(int k=0;k<11;k++){
                    if (floor[floorNum][j][k]==DOWNSTAIR){
                        hero.z = floorNum;
                        hero.y = j;
                        hero.x = k;
                        hero.heroLabel.setLocation(downStairs[floorNum][j][k].downstairLabel.getLocation().x,
                                downStairs[floorNum][j][k].downstairLabel.getLocation().y);
                    }
                }
            }

            //现在hero的坐标就是上一层的downstair的坐标，然而需要上下左右遍历一次，然后得到空地的坐标，把空地的坐标设置给hero
            if(hero.x-1>=0&&floor[hero.z][hero.y][hero.x-1]==BLANK){//上
                hero.x = hero.x-1;
                hero.heroLabel.setLocation(hero.heroLabel.getLocation().x ,hero.heroLabel.getLocation().y-34);


            }else if(hero.x+1<=10&&floor[hero.z][hero.y][hero.x+1]==BLANK){//下
                hero.x = hero.x+1;
                hero.heroLabel.setLocation(hero.heroLabel.getLocation().x,hero.heroLabel.getLocation().y+34);

            }else if(hero.y-1>=0&&floor[hero.z][hero.y-1][hero.x]==BLANK){//左
                hero.y = hero.y-1;
                hero.heroLabel.setLocation(hero.heroLabel.getLocation().x-34,
                        hero.heroLabel.getLocation().y);
            }else{//右
                hero.y = hero.y+1;
                hero.heroLabel.setLocation(hero.heroLabel.getLocation().x+34,
                        hero.heroLabel.getLocation().y);
            }




        }else {

            for(int j=0;j<11;j++){
                for(int k=0;k<11;k++){
                    if (floor[floorNum][j][k]==UPSTAIR){
                        hero.z = floorNum;
                        hero.y = j;
                        hero.x = k;
                        hero.heroLabel.setLocation(upstairs[floorNum][j][k].upstairLabel.getLocation().x,
                                upstairs[floorNum][j][k].upstairLabel.getLocation().y);
                    }
                }
            }

            //现在hero的坐标就是上一层的downstair的坐标，然而需要上下左右遍历一次，然后得到空地的坐标，把空地的坐标设置给hero
            if(hero.x-1>=0&&floor[hero.z][hero.y][hero.x-1]==BLANK){//上
                hero.x = hero.x-1;
                hero.heroLabel.setLocation(hero.heroLabel.getLocation().x ,hero.heroLabel.getLocation().y-34);


            }else if(hero.x+1<=10&&floor[hero.z][hero.y][hero.x+1]==BLANK){//下
                hero.x = hero.x+1;
                hero.heroLabel.setLocation(hero.heroLabel.getLocation().x,hero.heroLabel.getLocation().y+34);

            }else if(hero.y-1>=0&&floor[hero.z][hero.y-1][hero.x]==BLANK){//左
                hero.y = hero.y-1;
                hero.heroLabel.setLocation(hero.heroLabel.getLocation().x-34,
                        hero.heroLabel.getLocation().y);
            }else{//右
                hero.y = hero.y+1;
                hero.heroLabel.setLocation(hero.heroLabel.getLocation().x+34,
                        hero.heroLabel.getLocation().y);
            }



        }
    }





    private void setHeroStatus(){
        for(int i=0;i<hero.getClass().getDeclaredFields().length-4;i++){
            try{
                attributeLabels[i].setText(""+hero.getClass().getDeclaredFields()[i].getInt(hero));
            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }


    private void setFloorNum() {
        floorLabel.setLocation(92,195);
        floorLabel.setSize(200,300);
        floorLabel.setVisible(true);
        floorLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        floorLabel.setForeground(Color.WHITE);
        floorLabel.setText(""+floorNum);
        floorLabel.setVisible(true);
        bgLabel.add(floorLabel);
    }






}
