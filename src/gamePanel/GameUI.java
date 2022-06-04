package gamePanel;
import Characters.Celestial.Celestial;
import Characters.Hero;
import Matter.Door.*;
import Matter.Stairs.DownStair;
import Matter.Stairs.Upstair;
import Matter.WallCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GameUI{

    int floorNum = 0;

    JFrame gameFrame = new JFrame();
    JLabel bottom = new JLabel();
    JPanel gamePanel = new JPanel();
    JLabel bgLabel = new JLabel();
    int[][][] floor = new int[21][11][11];
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


    public GameUI(){
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
        //第一层
        floor[0][0][0] = CELL;
        floor[0][0][1] = CELL;
        floor[0][0][2] = CELL;
        floor[0][0][3] = CELL;
        floor[0][0][4] = CELL;
        floor[0][0][5] = CELL;
        floor[0][0][6] = CELL;
        floor[0][0][7] = CELL;

        floor[0][10][0] = CELL;
        floor[0][10][1] = CELL;
        floor[0][10][2] = CELL;
        floor[0][10][3] = CELL;
        floor[0][10][4] = CELL;
        floor[0][10][5] = CELL;
        floor[0][10][6] = CELL;
        floor[0][10][7] = CELL;

        floor[0][4][0] = CELL;
        floor[0][4][1] = CELL;
        floor[0][4][2] = CELL;
        floor[0][4][3] = CELL;
        floor[0][4][4] = CELL;
        floor[0][4][5] = CELL;
        floor[0][4][6] = CELL;
        floor[0][4][7] = CELL;

        floor[0][6][0] = CELL;
        floor[0][6][1] = CELL;
        floor[0][6][2] = CELL;
        floor[0][6][3] = CELL;
        floor[0][6][4] = CELL;
        floor[0][6][5] = CELL;
        floor[0][6][6] = CELL;
        floor[0][6][7] = CELL;

        floor[0][0][7] = CELL;
        floor[0][1][7] = CELL;
        floor[0][2][7] = CELL;
        floor[0][3][7] = CELL;
        floor[0][4][7] = CELL;
        floor[0][5][7] = YELLOWDOOR;
        floor[0][6][7] = CELL;
        floor[0][7][7] = CELL;
        floor[0][8][7] = CELL;
        floor[0][9][7] = CELL;
        floor[0][10][7] = CELL;

        floor[0][1][6] = CELL;
        floor[0][1][8] = CELL;

        floor[0][9][6] = CELL;
        floor[0][9][8] = CELL;

        floor[0][3][8] = CELL;
        floor[0][7][8] = CELL;


        floor[0][5][9] = HERO;
        floor[0][4][8] = CELESTIAL;



        floor[0][0][9] = LAVA;
        floor[0][1][9] = LAVA;
        floor[0][2][9] = LAVA;
        floor[0][3][9] = LAVA;
        floor[0][4][9] = LAVA;


        floor[0][0][10] = LAVA;
        floor[0][1][10] = LAVA;
        floor[0][2][10] = LAVA;
        floor[0][3][10] = LAVA;
        floor[0][4][10] = LAVA;

        floor[0][6][9] = LAVA;
        floor[0][7][9] = LAVA;
        floor[0][8][9] = LAVA;
        floor[0][9][9] = LAVA;
        floor[0][10][9] = LAVA;


        floor[0][6][10] = LAVA;
        floor[0][7][10] = LAVA;
        floor[0][8][10] = LAVA;
        floor[0][9][10] = LAVA;
        floor[0][10][10] = LAVA;

        floor[0][0][8] = LAVA;
        floor[0][2][8] = LAVA;

        floor[0][8][8] = LAVA;
        floor[0][10][8] = LAVA;

        floor[0][1][0] = DARKSTAR;
        floor[0][1][1] = DARKSTAR;
        floor[0][1][2] = DARKSTAR;
        floor[0][1][3] = DARKSTAR;
        floor[0][1][4] = DARKSTAR;
        floor[0][1][5] = DARKSTAR;

        floor[0][2][0] = DARKSTAR;
        floor[0][2][1] = DARKSTAR;
        floor[0][2][2] = DARKSTAR;
        floor[0][2][3] = DARKSTAR;
        floor[0][2][4] = DARKSTAR;
        floor[0][2][5] = DARKSTAR;
        floor[0][2][6] = DARKSTAR;

        floor[0][3][0] = DARKSTAR;
        floor[0][3][1] = DARKSTAR;
        floor[0][3][2] = DARKSTAR;
        floor[0][3][3] = DARKSTAR;
        floor[0][3][4] = DARKSTAR;
        floor[0][3][5] = DARKSTAR;
        floor[0][3][6] = DARKSTAR;

        floor[0][9][0] = DARKSTAR;
        floor[0][9][1] = DARKSTAR;
        floor[0][9][2] = DARKSTAR;
        floor[0][9][3] = DARKSTAR;
        floor[0][9][4] = DARKSTAR;
        floor[0][9][5] = DARKSTAR;

        floor[0][8][0] = DARKSTAR;
        floor[0][8][1] = DARKSTAR;
        floor[0][8][2] = DARKSTAR;
        floor[0][8][3] = DARKSTAR;
        floor[0][8][4] = DARKSTAR;
        floor[0][8][5] = DARKSTAR;
        floor[0][8][6] = DARKSTAR;

        floor[0][7][0] = DARKSTAR;
        floor[0][7][1] = DARKSTAR;
        floor[0][7][2] = DARKSTAR;
        floor[0][7][3] = DARKSTAR;
        floor[0][7][4] = DARKSTAR;
        floor[0][7][5] = DARKSTAR;
        floor[0][7][6] = DARKSTAR;

        floor[0][5][0] = UPSTAIR;

        floor[1][5][10] = DOWNSTAIR;


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

    private void updateDoor() {
        for(int i=0;i<21;i++){//楼层
            for(int j=0;j<11;j++){//y
                for(int k=0;k<11;k++){//x
                    if(yellowDoors[i][j][k]!=null){
                        bottom.remove(yellowDoors[i][j][k].yellowDoorLabel);
                    }
                }
            }
        }
    }

    private void addComponent2Floor(){
        bottom.add(hero.heroLabel);
        bottom.add(celestial.celestialLabel);



        for (WallCell wallCell : wallCells) {
            bottom.add(wallCell.wcLabel);
        }

        for(int i=0;i<21;i++){//楼层
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(yellowDoors[i][j][k]!=null){
                        bottom.add(yellowDoors[i][j][k].yellowDoorLabel);
                    }
                }
            }
        }

        for (Lava lava : lavas) {
            bottom.add(lava.lavaLabel);
        }
        for (DarkStar darkStar : darkStars) {
            bottom.add(darkStar.darkStarLabel);
        }


        for(int i=0;i<21;i++){//楼层
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(i==floorNum&&upstairs[i][j][k]!=null){
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




    }

    private void showFloor() {
        for (WallCell wallCell : wallCells) {
            if (wallCell.z==floorNum){
                wallCell.wcLabel.setVisible(true);
            }else {
                wallCell.wcLabel.setVisible(false);
            }
        }

        for(int i=0;i<21;i++){
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(yellowDoors[i][j][k]!=null){
                        if(i==floorNum){
                            yellowDoors[i][j][k].yellowDoorLabel.setVisible(true);
                        }else{
                            yellowDoors[i][j][k].yellowDoorLabel.setVisible(false);
                        }
                    }
                }
            }
        }


        for (Lava lava : lavas) {
            if (lava.z==floorNum){
                lava.lavaLabel.setVisible(true);
            }else{
                lava.lavaLabel.setVisible(false);
            }
        }
        for (DarkStar darkStar : darkStars) {
            if (darkStar.z==floorNum){
                darkStar.darkStarLabel.setVisible(true);
            }else {
                darkStar.darkStarLabel.setVisible(false);
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
                        if(i==floorNum){
                            downStairs[i][j][k].downstairLabel.setVisible(true);
                        }else {
                            downStairs[i][j][k].downstairLabel.setVisible(false);
                        }
                    }
                }
            }
        }



        hero.heroLabel.setVisible(true);
        celestial.celestialLabel.setVisible(true);

    }

    private void setCells() {
        for(int i=0;i<21;i++){
            for(int j=0;j<11;j++){
                for(int k=0;k<11;k++){
                    JLabel cellLabel = new JLabel();
                    cellLabel.setSize(34,34);
                    cellLabel.setLocation(34*j,94+34*k);
//                    cellLabel.setVisible(true);

                    if (floor[i][j][k]==CELL){
                        ImageIcon wallIcon = new ImageIcon("src/imageResource/Cell/1.png");
                        wallIcon.setImage(wallIcon.getImage().getScaledInstance(34,34,1));
                        cellLabel.setIcon(wallIcon);

                        WallCell wallCell = new WallCell();
                        wallCell.z = i;
                        wallCell.y = j;
                        wallCell.x = k;
                        wallCell.wcLabel = cellLabel;
                        wallCells.add(wallCell);
                    }else if(floor[i][j][k]==CELESTIAL){
                        ImageIcon celestialIcon = new ImageIcon("src/imageResource/Celestial/1.png");
                        celestialIcon.setImage(celestialIcon.getImage().getScaledInstance(34,34,1));
                        cellLabel.setIcon(celestialIcon);

                        celestial.celestialLabel = cellLabel;
                        celestial.x = k;
                        celestial.y = j;
                        celestial.z = i;
                    }else if(floor[i][j][k]==YELLOWDOOR){
                        ImageIcon yellowDoorIcon = new ImageIcon("src/imageResource/Cell/Door/1.png");
                        yellowDoorIcon.setImage(yellowDoorIcon.getImage().getScaledInstance(34,34,1));
                        cellLabel.setIcon(yellowDoorIcon);
                        YellowDoor yellowDoor = new YellowDoor();
                        yellowDoor.z = i;
                        yellowDoor.y = j;
                        yellowDoor.x = k;
                        yellowDoor.yellowDoorLabel = cellLabel;
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
                        lavas.add(lava);

                    }else if(floor[i][j][k]==DARKSTAR){
                        ImageIcon lavaIcon = new ImageIcon("src/imageResource/Cell/Door/20.png");
                        lavaIcon.setImage(lavaIcon.getImage().getScaledInstance(34,34,1));
                        cellLabel.setIcon(lavaIcon);
                        DarkStar darkStar = new DarkStar();
                        darkStar.z = i;
                        darkStar.y = j;
                        darkStar.x = k;
                        darkStar.darkStarLabel = cellLabel;
                        darkStars.add(darkStar);

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
        //更新各种门的情况
        updateDoor();
        //修改勇者的状态栏
        updateStatus();
        //重新显示整个界面
        showFloor();
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
            if(floor[hero.z][hero.y][hero.x]==0){
                hero.heroLabel.setLocation(hero.heroLabel.getLocation().x-34,hero.heroLabel.getLocation().y);
                floor[hero.z][hero.y][hero.x]=HERO;
                floor[hero.z][hero.y+1][hero.x]=0;

            }else{
                hero.y = hero.y+1;
            }
        }else{
            hero.y = hero.y+1;
        }
    }

    private void heroGoDown() {
        hero.x = hero.x+1;
        if(hero.x<=10){
            if(floor[hero.z][hero.y][hero.x]==0){
                hero.heroLabel.setLocation(hero.heroLabel.getLocation().x,hero.heroLabel.getLocation().y+34);
                floor[hero.z][hero.y][hero.x]=HERO;
                floor[hero.z][hero.y][hero.x-1]=0;

            }else{
                hero.x = hero.x-1;
            }
        }else{
            hero.x = hero.x-1;
        }
    }

    private void heroGoUp() {
        hero.x = hero.x-1;
        if(hero.x>=0){
            if(floor[hero.z][hero.y][hero.x]==BLANK){//空白格子，允许站上去
                hero.heroLabel.setLocation(hero.heroLabel.getLocation().x,hero.heroLabel.getLocation().y-34);

                floor[hero.z][hero.y][hero.x]=HERO;
                floor[hero.z][hero.y][hero.x+1]=0;

            }else if(floor[hero.z][hero.y][hero.x]==YELLOWDOOR){//黄门，允许开门
                if (hero.yellowKey>=1){//黄钥匙够
                    System.out.println("踩到黄门");
                    hero.yellowKey = hero.yellowKey-1;//黄钥匙数量-1
                    //黄门的标签直接设置为空
                    yellowDoors[hero.z][hero.y][hero.x]=null;
                    //修改英雄标签所在的位置
                    hero.heroLabel.setLocation(hero.heroLabel.getLocation().x,hero.heroLabel.getLocation().y-34);
                    //英雄现在站立的地方变成英雄的坐标
                    floor[hero.z][hero.y][hero.x]=HERO;
                    //英雄原本站立的地方变成空地
                    floor[hero.z][hero.y][hero.x+1]=BLANK;
                }else{
                    hero.x = hero.x+1;
                }

            }else if(floor[hero.z][hero.y][hero.x]==UPSTAIR){//上楼
                System.out.println("上楼");
                //楼层数+1
                floorNum = floorNum+1;
                //原本站立的地方变成空地
                floor[hero.z][hero.y][hero.x+1]=BLANK;
                //修改英雄标签所在的位置，改为上一层的下楼所在的坐标
                SetHeroCoor(true);//此方法寻找上一层的downStairs的在floor上的坐标然后赋值给hero
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
        JLabel attackLabel = new JLabel();
        attackLabel.setLocation(92,195);
        attackLabel.setSize(200,300);
        attackLabel.setVisible(true);
        attackLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        attackLabel.setForeground(Color.WHITE);
        attackLabel.setText("1");
        attackLabel.setVisible(true);
        bgLabel.add(attackLabel);
    }






}
