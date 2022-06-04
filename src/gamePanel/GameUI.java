package gamePanel;
import Characters.Celestial.Celestial;
import Characters.Hero;
import Matter.Door.DarkStar;
import Matter.Door.Lava;
import Matter.Door.YellowDoor;
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
    List<YellowDoor> yellowDoors = new ArrayList<>();
    List<WallCell> wallCells = new ArrayList<>();
    List<Lava> lavas = new ArrayList<>();
    List<DarkStar>darkStars = new ArrayList<>();
    List<Upstair>upstairs = new ArrayList<>();


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


    public GameUI(){
        setFloor();
        JFrame.setDefaultLookAndFeelDecorated(true);
        gameFrame.setSize(820,600);
        setGameFrame();
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





    }

    public void loadGameFrame(){
        bottom.remove(hero.heroLabel);
        System.out.println("英雄被删除了！");
    }

    private void showFloor() {
        for (WallCell wallCell : wallCells) {
            if (wallCell.z==floorNum){
                bottom.add(wallCell.wcLabel);
//                System.out.println("add wall");
            }
        }
        for (YellowDoor yellowDoor : yellowDoors) {
            if (yellowDoor.z==floorNum){
                bottom.add(yellowDoor.yellowDoorLabel);
            }
        }

        for (Lava lava : lavas) {
            if (lava.z==floorNum){
                bottom.add(lava.lavaLabel);
            }
        }
        for (DarkStar darkStar : darkStars) {
            if (darkStar.z==floorNum){
                bottom.add(darkStar.darkStarLabel);
            }
        }
        for (Upstair upstair : upstairs) {
            if (upstair.z==floorNum){
                bottom.add(upstair.upstairLabel);
            }
        }

        bottom.add(hero.heroLabel);
        bottom.add(celestial.celestialLabel);

    }

    private void setCells() {
        for(int i=0;i<21;i++){
            for(int j=0;j<11;j++){
                for(int k=0;k<11;k++){
                    JLabel cellLabel = new JLabel();
                    cellLabel.setSize(34,34);
                    cellLabel.setLocation(34*j,94+34*k);
                    cellLabel.setVisible(true);

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
                        yellowDoors.add(yellowDoor);
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
                        upstairs.add(upstair);
                    }




                }
            }
        }

    }

    private JPanel setGameFrame() {
        gameFrame.setVisible(true);
        gameFrame.setSize(820,600);
        gamePanel.setLocation(0,0);
        gamePanel.setSize(820,600);
        prepareBG();
        gameFrame.setContentPane(gamePanel);
        gameFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                hero.heroLabel.setVisible(false);

//                bottom.remove(hero.heroLabel);
                if (e.getKeyCode()==KeyEvent.VK_UP){
                    hero.x = hero.x-1;
                    if(hero.x>=0){
                        if(floor[hero.z][hero.y][hero.x]==0){
                            hero.heroLabel.setLocation(hero.heroLabel.getLocation().x,hero.heroLabel.getLocation().y-34);
                            floor[hero.z][hero.y][hero.x]=HERO;
                            floor[hero.z][hero.y][hero.x+1]=0;

                        }else if(floor[hero.z][hero.y][hero.x]==YELLOWDOOR){//黄门，允许开门
                            if (hero.yellowKey>=1){//黄钥匙够
                                System.out.println("踩到黄门");
                                hero.yellowKey = hero.yellowKey-1;//黄钥匙数量-1
                                //黄门的标签直接设置为不可见

                                //修改英雄标签所在的位置
                                hero.heroLabel.setLocation(hero.heroLabel.getLocation().x,hero.heroLabel.getLocation().y-34);
                                //英雄现在站立的地方变成英雄的坐标
                                floor[hero.z][hero.y][hero.x]=HERO;
                                //英雄原本站立的地方变成空地
                                floor[hero.z][hero.y][hero.x+1]=BLANK;
                            }else{
                                hero.x = hero.x+1;
                            }


                        }else{
                            hero.x = hero.x+1;
                        }
                    }else{
                        hero.x = hero.x+1;
                    }

                }else if (e.getKeyCode()==KeyEvent.VK_DOWN){
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



                }else if (e.getKeyCode()==KeyEvent.VK_LEFT){
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


                }else if (e.getKeyCode()==KeyEvent.VK_RIGHT){
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


                }else{
                    System.out.println("is not ready!");
                }



                hero.heroLabel.setVisible(true);

            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        return gamePanel;
    }

    private void setInitData(){
        //现在先放置一些假的数据，到时候再使用勇者的数据放进去


        bottom.setLocation(200,-55);
        ImageIcon bottomIcon = new ImageIcon("src/imageResource/BlankBg.png");
        bottomIcon.setImage(bottomIcon.getImage().getScaledInstance(374,370,1));
        bottom.setIcon(bottomIcon);
        bottom.setSize(500,560);
        bottom.setLocation(199,-60);
        bottom.setVisible(true);

        setCells();

        showFloor();

        bgLabel.add(bottom);

        setHeroData();
        setFloorNum();
    }

    private void setHeroData(){
        setHeroAttribute();

    }

    private void setHeroAttribute(){
        int[]yCoor = new int[]{-92,-65,-40,-15,10,35,70,103,130};

        for (int i=0;i<hero.getClass().getDeclaredFields().length-4;i++){
            JLabel attackLabel = new JLabel();
            attackLabel.setLocation(100,yCoor[i]);
            attackLabel.setSize(200,300);
            attackLabel.setVisible(true);
            attackLabel.setFont(new Font("宋体", Font.PLAIN, 20));
            attackLabel.setForeground(Color.WHITE);
            try {
                attackLabel.setText(""+hero.getClass().getDeclaredFields()[i].getInt(hero));
            }catch (Exception e){
                e.printStackTrace();
            }
            attackLabel.setVisible(true);
            bgLabel.add(attackLabel);
        }


    }

    private void setFloorNum() {
        JLabel attackLabel = new JLabel();
        attackLabel.setLocation(92,167);
        attackLabel.setSize(200,300);
        attackLabel.setVisible(true);
        attackLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        attackLabel.setForeground(Color.WHITE);
        attackLabel.setText("1");
        attackLabel.setVisible(true);
        bgLabel.add(attackLabel);
    }

    private void setRedKey(JLabel bgLabel) {
        JLabel attackLabel = new JLabel();
        attackLabel.setLocation(100,130);
        attackLabel.setSize(200,300);
        attackLabel.setVisible(true);
        attackLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        attackLabel.setForeground(Color.WHITE);
        attackLabel.setText(""+hero.redKey);
        attackLabel.setVisible(true);
        bgLabel.add(attackLabel);
    }

    private void setBlueKey(JLabel bgLabel) {
        JLabel attackLabel = new JLabel();
        attackLabel.setLocation(100,103);
        attackLabel.setSize(200,300);
        attackLabel.setVisible(true);
        attackLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        attackLabel.setForeground(Color.WHITE);
        attackLabel.setText(""+hero.blueKey);
        attackLabel.setVisible(true);
        bgLabel.add(attackLabel);
    }

    private void setYellowKey(JLabel bgLabel) {
        JLabel attackLabel = new JLabel();
        attackLabel.setLocation(100,70);
        attackLabel.setSize(200,300);
        attackLabel.setVisible(true);
        attackLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        attackLabel.setForeground(Color.WHITE);
        attackLabel.setText(""+hero.yellowKey);
        attackLabel.setVisible(true);
        bgLabel.add(attackLabel);
    }

    private void setExperience(JLabel bgLabel) {
        JLabel attackLabel = new JLabel();
        attackLabel.setLocation(100,35);
        attackLabel.setSize(200,300);
        attackLabel.setVisible(true);
        attackLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        attackLabel.setForeground(Color.WHITE);
        attackLabel.setText(""+hero.experience);
        attackLabel.setVisible(true);
        bgLabel.add(attackLabel);
    }

    private void setCoin(JLabel bgLabel) {
        JLabel attackLabel = new JLabel();
        attackLabel.setLocation(100,10);
        attackLabel.setSize(200,300);
        attackLabel.setVisible(true);
        attackLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        attackLabel.setForeground(Color.WHITE);
        attackLabel.setText(""+hero.coin);
        attackLabel.setVisible(true);
        bgLabel.add(attackLabel);
    }

    private void setDefence(JLabel bgLabel) {
        JLabel attackLabel = new JLabel();
        attackLabel.setLocation(100,-15);
        attackLabel.setSize(200,300);
        attackLabel.setVisible(true);
        attackLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        attackLabel.setForeground(Color.WHITE);
        attackLabel.setText(""+hero.defence);
        attackLabel.setVisible(true);
        bgLabel.add(attackLabel);
    }

    private void setAttack(JLabel bgLabel) {
        JLabel attackLabel = new JLabel();
        attackLabel.setLocation(100,-40);
        attackLabel.setSize(200,300);
        attackLabel.setVisible(true);
        attackLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        attackLabel.setForeground(Color.WHITE);
        attackLabel.setText(""+hero.attack);
        attackLabel.setVisible(true);
        bgLabel.add(attackLabel);
    }

    private void setLife(JLabel bgLabel) {
        JLabel attackLabel = new JLabel();
        attackLabel.setLocation(100,-65);
        attackLabel.setSize(200,300);
        attackLabel.setVisible(true);
        attackLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        attackLabel.setForeground(Color.WHITE);
        attackLabel.setText(""+hero.life);
        attackLabel.setVisible(true);
        bgLabel.add(attackLabel);
    }

    private void setLevel(JLabel bgLabel) {
        JLabel levelLabel = new JLabel();
        levelLabel.setLocation(100,-92);
        levelLabel.setSize(200,300);
        levelLabel.setVisible(true);
        levelLabel.setFont(new Font("宋体", Font.PLAIN, 25));
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setText(""+hero.level);
        levelLabel.setVisible(true);
        bgLabel.add(levelLabel);
    }



    private void prepareBG() {
        //设置背景图片，但是不设置人物等等
        //这个设置为了各个楼层都可以使用的
        bgLabel.setLocation(0,0);
        ImageIcon bgIcon = new ImageIcon("src/imageResource/GameBg.png");
        bgIcon.setImage(bgIcon.getImage().getScaledInstance(605,438,1));
        bgLabel.setIcon(bgIcon);
        bgLabel.setSize(520,520);
        setInitData();
        gamePanel.add(bgLabel);
    }




}
