package gamePanel;
import GameObject.Characters.Enemy.*;
import GameObject.Characters.Hero;
import GameObject.Characters.SpecialChar.Firstgreedy;
import GameObject.GameObject;

import GameObject.Matter.Item.Bottle.Bluebottle;
import GameObject.Matter.Item.Bottle.Bottle;
import GameObject.Matter.Item.Bottle.Redbottle;
import GameObject.Matter.Item.Diamond.Bluediamond;
import GameObject.Matter.Item.Diamond.Diamond;
import GameObject.Matter.Item.Diamond.Reddiamond;
import GameObject.Matter.Item.Key.Key;
import GameObject.Matter.Item.Weapon.Weapon;
import com.sun.xml.internal.ws.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.List;

public class GameUI{

    int floorNum = 0;
    JLabel floorLabel = new JLabel();

    Map<String,Integer> cellMap = new HashMap<>();
    JLabel shopLabel = new JLabel();
    JLabel handbookLabel = new JLabel();
    JFrame gameFrame = new JFrame();
    JLabel bottom = new JLabel();
    JPanel gamePanel = new JPanel();

    JLabel bgLabel = new JLabel();
//    int[][][] floor = new int[21][11][11];
    GameObject [][][] gameObjects = new GameObject[22][11][11];

    Hero hero = new Hero();

    Set<String>monsterNameSet = new HashSet<>();

    JLabel[]attributeLabels = new JLabel[9];

    public int UpORDown =  0;
    public int LeftORRight = 0;


    public final int BLANK = 0;
    public final int HERO = 1;




    public void initMonsterNameSet(){
        Class[] classByPackage = ClassUtils.getClassByPackage("GameObject.Characters.Enemy");
        for (Class aClass : classByPackage) {
            String className = aClass.getName();
            if (className.equals("Monster")){
                continue;
            }
            className = className.replace("GameObject.Characters.Enemy.","");
            monsterNameSet.add(className.toLowerCase());
        }
    }

    public GameUI(){
        floorNum = 0;
        shopLabel.setVisible(false);
        gamePanel.add(shopLabel);

        handbookLabel.setVisible(false);
        gamePanel.add(handbookLabel);



        initMonsterNameSet();
        initCellMap();
        setFloor();//设置棋盘，[21][11][11]每一个点都放着东西，不是勇士就是墙就是敌人或者是道具
        JFrame.setDefaultLookAndFeelDecorated(true);
        gameFrame.setSize(820,600);
        setAttributeLabels();
        setGameFrame();//设置gameFrame，比如大小，高度，还有键盘的触发事件
        setBgLabel();
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
        cellMap.put("blank",BLANK);
    }

    public void SetHeroCoor(boolean upOrDown) {
        if(upOrDown==true){
            for(int j=0;j<11;j++){
                for(int k=0;k<11;k++){
                    if (gameObjects[floorNum][j][k]!=null&&gameObjects[floorNum][j][k].type.equals("downstair")){
                        hero.z = floorNum;
                        hero.y = j;
                        hero.x = k;
                        hero.gameObjectLabel.setLocation(gameObjects[floorNum][j][k].gameObjectLabel.getLocation().x,
                                gameObjects[floorNum][j][k].gameObjectLabel.getLocation().y);
                    }
                }
            }

            //现在hero的坐标就是上一层的downstair的坐标，然而需要上下左右遍历一次，然后得到空地的坐标，把空地的坐标设置给hero
            if(hero.x-1>=0&&gameObjects[hero.z][hero.y][hero.x-1].type.equals("blank")){//上
                hero.x = hero.x-1;
                hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x ,hero.gameObjectLabel.getLocation().y-34);


            }else if(hero.x+1<=10&&gameObjects[hero.z][hero.y][hero.x+1].type.equals("blank")){//下

                hero.x = hero.x+1;
                hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x,hero.gameObjectLabel.getLocation().y+34);

            }else if(hero.y-1>=0&&gameObjects[hero.z][hero.y-1][hero.x].type.equals("blank")){//左
                hero.y = hero.y-1;
                hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34,
                        hero.gameObjectLabel.getLocation().y);
            }else{//右
                hero.y = hero.y+1;
                hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x+34,
                        hero.gameObjectLabel.getLocation().y);
            }

        }
        else {

            for(int j=0;j<11;j++){
                for(int k=0;k<11;k++){
                    if (null!=gameObjects[floorNum][j][k]&&gameObjects[floorNum][j][k].type.equals("upstair")){
                        hero.z = floorNum;
                        hero.y = j;
                        hero.x = k;
                        hero.gameObjectLabel.setLocation(gameObjects[floorNum][j][k].gameObjectLabel.getLocation().x,
                                gameObjects[floorNum][j][k].gameObjectLabel.getLocation().y);
                    }
                }
            }

            //现在hero的坐标就是上一层的downstair的坐标，然而需要上下左右遍历一次，然后得到空地的坐标，把空地的坐标设置给hero
            if(hero.x-1>=0&&gameObjects[hero.z][hero.y][hero.x-1].type.equals("blank")){//上
                hero.x = hero.x-1;
                hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x ,hero.gameObjectLabel.getLocation().y-34);


            }else if(hero.x+1<=10&&gameObjects[hero.z][hero.y][hero.x+1].type.equals("blank")){//下
                hero.x = hero.x+1;
                hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x,hero.gameObjectLabel.getLocation().y+34);

            }else if(hero.y-1>=0&&gameObjects[hero.z][hero.y-1][hero.x].type.equals("blank")){//左
                hero.y = hero.y-1;
                hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34,
                        hero.gameObjectLabel.getLocation().y);
            }else{//右
                hero.y = hero.y+1;
                hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x+34,
                        hero.gameObjectLabel.getLocation().y);
            }



        }
    }



    public void setBottom(){
        //设置bottom
        bottom.setLocation(200,-55);
        ImageIcon bottomIcon = new ImageIcon("src/imageResource/BlankBg.png");
        bottomIcon.setImage(bottomIcon.getImage().getScaledInstance(374,370,1));
        bottom.setIcon(bottomIcon);
        bottom.setSize(500,560);
        bottom.setLocation(199,-60);
        bottom.setVisible(true);

    }

    public void setBgLabel() {
        //只设置bgLabel
        bgLabel.setLocation(0,0);
        ImageIcon bgIcon = new ImageIcon("src/imageResource/GameBg.png");
        bgIcon.setImage(bgIcon.getImage().getScaledInstance(605,438,1));
        bgLabel.setIcon(bgIcon);
        bgLabel.setSize(520,520);
    }



    public void setAttributeLabels() {
        int[]yCoor = new int[]{-85,-55,-30,-5,25,50,90,125,155};

        for (int i=0;i<hero.getClass().getDeclaredFields().length-3;i++){
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

                if (monsterNameSet.contains(line)){
                    Monster monster = new Monster();
                    try{
                        Constructor<?> constructor = Class.forName("GameObject.Characters.Enemy." + StringUtils.capitalize(line)).getConstructor();
                        monster = (Monster) constructor.newInstance();
                        monster.type = line;
                        monster.x = k;
                        monster.y = j;
                        monster.z = i;
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    gameObjects[i][j][k] = monster;//todo

                }
                else if(line.contains("key")){
                    Key key = new Key();
                    try{
                        Constructor<?> constructor = Class.forName("GameObject.Matter.Item.Key." + StringUtils.capitalize(line)).getConstructor();
                        key = (Key) constructor.newInstance();
                        key.type = line;
                        key.x = k;
                        key.y = j;
                        key.z = i;
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    gameObjects[i][j][k] = key;//todo
                }
                else if(line.contains("bottle")){
                    Bottle bottle = new Bottle();
                    try{
                        Constructor<?> constructor = Class.forName("GameObject.Matter.Item.Bottle." + StringUtils.capitalize(line)).getConstructor();
                        bottle = (Bottle) constructor.newInstance();
                        bottle.type = line;
                        bottle.x = k;
                        bottle.y = j;
                        bottle.z = i;
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    gameObjects[i][j][k] = bottle;//todo
                }
                else if(line.contains("diamond")){
                    Diamond diamond = new Diamond();
                    try{
                        Constructor<?> constructor = Class.forName("GameObject.Matter.Item.Diamond." + StringUtils.capitalize(line)).getConstructor();
                        diamond = (Diamond) constructor.newInstance();
                        diamond.type = line;
                        diamond.x = k;
                        diamond.y = j;
                        diamond.z = i;
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    gameObjects[i][j][k] = diamond;//todo
                }
                else if(line.contains("firstgreedy")){
                    Firstgreedy firstgreedy = new Firstgreedy();
                    try{
                        Constructor<?> constructor = Class.forName("GameObject.Characters.SpecialChar." + StringUtils.capitalize(line)).getConstructor();
                        firstgreedy = (Firstgreedy) constructor.newInstance();
                        firstgreedy.type = line;
                        firstgreedy.x = k;
                        firstgreedy.y = j;
                        firstgreedy.z = i;
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    gameObjects[i][j][k] = firstgreedy;//todo
                }
                else if(line.contains("hero")){
                    GameObject gameObject = new GameObject();
                    gameObject.type = line;
                    gameObject.x = k;
                    gameObject.y = j;
                    gameObject.z = i;
                    gameObjects[i][j][k] = gameObject;//todo

                }
                else if(line.contains("sword")||line.contains("shield")){
                    Weapon weapon = new Weapon();
                    try{
                        Constructor<?> constructor = Class.forName("GameObject.Matter.Item.Weapon." + StringUtils.capitalize(line)).getConstructor();
                        weapon = (Weapon) constructor.newInstance();
                        weapon.type = line;
                        weapon.x = k;
                        weapon.y = j;
                        weapon.z = i;
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    gameObjects[i][j][k] = weapon;//todo
                }


                else {
                    GameObject gameObject = new GameObject();
                    gameObject.type = line;
                    gameObject.x = k;
                    gameObject.y = j;
                    gameObject.z = i;
                    gameObjects[i][j][k] = gameObject;//todo
                }


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





    }



    public void updateStatus() {
        for (int i=0;i<hero.getClass().getDeclaredFields().length-3;i++){
            try{
                attributeLabels[i].setText(""+hero.getClass().getDeclaredFields()[i].getInt(hero));
            }catch (Exception e){
                e.printStackTrace();
            }

        }


    }


    public void addComponent2Floor(){
        bottom.add(hero.gameObjectLabel);
        //todo
        for(int i=0;i<22;i++){//楼层
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(gameObjects[i][j][k]!=null){
                        bottom.add(gameObjects[i][j][k].gameObjectLabel);
                    }
                }
            }
        }

    }


    public void showFloor() {
        for(int i=0;i<22;i++){
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(gameObjects[i][j][k]!=null){
                        if(i==floorNum){
                            gameObjects[i][j][k].gameObjectLabel.setVisible(true);
                        }else{
                            gameObjects[i][j][k].gameObjectLabel.setVisible(false);
                        }
                    }
                }
            }
        }

        hero.gameObjectLabel.setVisible(true);

    }


    public void setCells() {
        for(int i=0;i<22;i++){
            for(int j=0;j<11;j++){
                for(int k=0;k<11;k++){
                    if (null!=gameObjects[i][j][k]){
                        JLabel gameObjectLabel = new JLabel();
                        gameObjectLabel.setSize(34,34);
                        gameObjectLabel.setLocation(34*j,94+34*k);

                        ImageIcon gameObjectIcon = new ImageIcon("src/imageResource/" +gameObjects[i][j][k].type+".png");
                        gameObjectIcon.setImage(gameObjectIcon.getImage().getScaledInstance(34,34,1));
                        gameObjectLabel.setIcon(gameObjectIcon);
                        if (gameObjects[i][j][k].type.equals("hero")){
                            hero.z = i;
                            hero.y = j;
                            hero.x = k;
                            hero.gameObjectLabel = gameObjectLabel;
                        }

                        gameObjects[i][j][k].gameObjectLabel=gameObjectLabel;

                    }
                    else {
                        continue;
                    }



                }
            }
        }

    }


    public void setGameFrame() {
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

    public void heroDoAction(KeyEvent e) {
        if(shopLabel.isVisible()==false){
            if (hero.handbook==true&&e.getKeyCode()==KeyEvent.VK_L){
                System.out.println("开始handbook");
                if (handbookLabel.isVisible()){
                    handbookLabel.setVisible(false);
                }else{
                    gamePanel.setLayout(null);
                    handbookLabel.setVisible(true);
                    handbookLabel.setOpaque(true);
                    handbookLabel.setLocation(300,30);
                    handbookLabel.setBackground(Color.BLACK);
                    handbookLabel.setSize(400,400);
                    setHandBook();
                }

            }



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
        }
//        hero.gameObjectLabel.setVisible(false);
        else {
            boolean exit = false;
            if(e.getKeyCode()==KeyEvent.VK_A){
                hero.coin = hero.coin-25;
                hero.attack = hero.attack+4;
                System.out.println("攻击");
            }
            else if(e.getKeyCode()==KeyEvent.VK_Q){
                exit = true;
                System.out.println("取消");
            }
            else if(e.getKeyCode()==KeyEvent.VK_S){
                hero.coin = hero.coin-25;
                hero.defence = hero.defence+4;
                System.out.println("防御");
            }
            else if(e.getKeyCode()==KeyEvent.VK_D){
                hero.coin = hero.coin-25;
                hero.life = hero.life+800;
                System.out.println("生命");
            }
            else{
                shopLabel.setVisible(true);
            }


            if (exit){
                shopLabel.setVisible(false);
            }

        }


//        hero.gameObjectLabel.setVisible(true);
        reLoadGameFrame();
    }

    private void setHandBook() {
        //设置圣光徽的内容
        //在圣光徽章界面上显示所有怪物的攻防记录
        Map<Class,Monster>monsterMap = new HashMap<>();
        //先删除旧有的component
        Component[] components = handbookLabel.getComponents();
        for (Component component : components) {
            handbookLabel.remove(component);
        }

        for(int j=0;j<11;j++){
            for(int k=0;k<11;k++){
                if (Monster.class.isAssignableFrom(gameObjects[floorNum][j][k].getClass())){
                    monsterMap.put(gameObjects[floorNum][j][k].getClass(), (Monster) gameObjects[floorNum][j][k]);
                }

            }
        }


        int num = 0;
        for (Class aClass : monsterMap.keySet()) {
            JLabel iconLabel = new JLabel();
            iconLabel.setIcon(monsterMap.get(aClass).gameObjectLabel.getIcon());
            iconLabel.setSize(34,34);
            iconLabel.setLocation(0,num*50);
            handbookLabel.add(iconLabel);


            JLabel nameLabel = new JLabel();
            nameLabel.setText(monsterMap.get(aClass).type);
            nameLabel.setBackground(Color.WHITE);
            nameLabel.setSize(68,17);
            nameLabel.setLocation(60,num*50);
            nameLabel.setVisible(true);
            nameLabel.setOpaque(true);
            handbookLabel.add(nameLabel);

            JLabel lifeLabel = new JLabel();
            lifeLabel.setText("生命 "+monsterMap.get(aClass).life);
            lifeLabel.setBackground(Color.WHITE);
            lifeLabel.setSize(68,17);
            lifeLabel.setLocation(60,num*50+17);
            lifeLabel.setVisible(true);
            lifeLabel.setOpaque(true);
            handbookLabel.add(lifeLabel);


            JLabel attackLabel = new JLabel();
            attackLabel.setText("攻击 "+monsterMap.get(aClass).attack);
            attackLabel.setBackground(Color.WHITE);
            attackLabel.setSize(68,17);
            attackLabel.setLocation(60+100,num*50);
            attackLabel.setVisible(true);
            attackLabel.setOpaque(true);
            handbookLabel.add(attackLabel);

            JLabel defenceLabel = new JLabel();
            defenceLabel.setText("防御 "+monsterMap.get(aClass).defence);
            defenceLabel.setBackground(Color.WHITE);
            defenceLabel.setSize(68,17);
            defenceLabel.setLocation(60+100,num*50+17);
            defenceLabel.setVisible(true);
            defenceLabel.setOpaque(true);
            handbookLabel.add(defenceLabel);



            JLabel CoinAndExpLabel = new JLabel();
            CoinAndExpLabel.setText("金｜经 "+monsterMap.get(aClass).coin+"|"+monsterMap.get(aClass).experience);
            CoinAndExpLabel.setBackground(Color.WHITE);
            CoinAndExpLabel.setSize(100,17);
            CoinAndExpLabel.setLocation(60+100*2,num*50);
            CoinAndExpLabel.setVisible(true);
            CoinAndExpLabel.setOpaque(true);
            handbookLabel.add(CoinAndExpLabel);

            JLabel damageLabel = new JLabel();
            damageLabel.setText("损失 "+"未定义");
            damageLabel.setBackground(Color.WHITE);
            damageLabel.setSize(100,17);
            damageLabel.setLocation(60+100*2,num*50+17);
            damageLabel.setVisible(true);
            damageLabel.setOpaque(true);
            handbookLabel.add(damageLabel);






            num = num+1;

        }





    }

    public void reLoadGameFrame(){
        //修改勇者的状态栏
        updateStatus();
        //重新显示整个界面
        showFloor();
        updateFloorLabel();
    }

    public void updateFloorLabel() {
        floorLabel.setText(""+floorNum);
    }


    public void heroGoRight() {
        LeftORRight = -1;
        hero.y = hero.y-LeftORRight;
        if(hero.y<=10){
//            heroMove(gameObjects[hero.z][hero.y][hero.x].type);
            heroMove();
        }else{
            hero.y = hero.y-1;
        }
        LeftORRight = 0;

    }

    public void heroGoLeft() {
        LeftORRight = 1;
        hero.y = hero.y-LeftORRight;
        if(hero.y>=0){
            heroMove();
        }else{
            hero.y = hero.y+1;
        }
        LeftORRight = 0;

    }


    public boolean fight(Monster monster) {
        Hero simulateHero = new Hero() ;//模拟的英雄的数据
        simulateHero.attack =  hero.attack;
        simulateHero.defence = hero.defence;
        simulateHero.life = hero.life;

        Monster simulateMonster = new Monster();
        simulateMonster.attack = monster.attack;
        simulateMonster.defence = monster.defence;
        simulateMonster.life = monster.life;

        if(simulateMonster.defence>=simulateHero.attack){
            return false;
        }else if(simulateHero.defence>=simulateMonster.attack){
            hero.coin = hero.coin+monster.coin;
            hero.experience = hero.experience+monster.experience;
            return true;
        }else {
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



    }

    public void heroGoDown() {
        UpORDown=-1;
        hero.x = hero.x-UpORDown;
        if(hero.x<=10){
            heroMove();
        }else{
            hero.x = hero.x-1;
        }
        UpORDown=0;

    }

    public void heroMove(){
        //destination就是目标，也就是黄门，史莱姆，墙，上下楼这些
        if(gameObjects[hero.z][hero.y][hero.x].type.equals("blank")){
            //空白格子，允许站上去
            //修改英雄位置，前进后退等，变成新位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-LeftORRight*34,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //修改英雄坐标上的位置
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";

        }
        else if(hero.yellowKey>=1&&gameObjects[hero.z][hero.y][hero.x].type.equals("yellowdoor")){
            System.out.println("踩到黄门");
            hero.yellowKey = hero.yellowKey-1;//黄钥匙数量-1
            //黄门的标签直接设置为空
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";

        }
        else if(hero.yellowKey>=1&&gameObjects[hero.z][hero.y][hero.x].type.equals("bluedoor")){
            System.out.println("踩到");
            hero.blueKey = hero.blueKey-1;//黄钥匙数量-1
            //黄门的标签直接设置为空
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";

        }
        else if (hero.redKey>=1&&gameObjects[hero.z][hero.y][hero.x].type.equals("reddoor")){
            System.out.println("踩到红门");
            hero.redKey = hero.redKey-1;//红钥匙数量-1
            //从bottom当中去除此红门
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y][hero.x+UpORDown].type="blank";

        }
        else if (gameObjects[hero.z][hero.y][hero.x].type.equals("upstair")){
            System.out.println("上楼");
            //楼层数+1
            floorNum = floorNum+1;
            //原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";//hero.x-1
            //修改英雄标签所在的位置，改为上一层的下楼所在的坐标
            SetHeroCoor(true);//此方法寻找上一层的downStairs的在floor上的坐标然后选取一个可以站立的位置赋值给hero
            //将英雄的现在的位置在floor里面设置值
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //修改英雄标签所在的位置,将其设置为新楼层的downstairs所在的位置
        }
        else if(gameObjects[hero.z][hero.y][hero.x].type.equals("fence")){
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
        }
        else if (gameObjects[hero.z][hero.y][hero.x].type.equals("downstair")){
            System.out.println("下楼");
            //楼层数-1
            floorNum = floorNum-1;
            //原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
            //修改英雄标签所在的位置，改为上一层的下楼所在的坐标
            SetHeroCoor(false);//此方法寻找上一层的downStairs的在floor上的坐标然后选取一个可以站立的位置赋值给hero
            //将英雄的现在的位置在floor里面设置值
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //修改英雄标签所在的位置,将其设置为新楼层的downstairs所在的位置
            //
            System.out.println("下楼");
        }
        else if (gameObjects[hero.z][hero.y][hero.x].type .equals("yellowkey")){
            System.out.println("得到黄钥匙");
            hero.yellowKey = hero.yellowKey+1;//
            //红钥匙的标签直接设置为空
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
        }
        else if (gameObjects[hero.z][hero.y][hero.x].type.equals("redkey")){
            System.out.println("得到红钥匙");
            hero.redKey = hero.redKey+1;//
            //红钥匙的标签直接设置为空
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
        }
        else if (gameObjects[hero.z][hero.y][hero.x].type.equals("bluekey")){
            System.out.println("得到蓝钥匙");
            hero.blueKey = hero.blueKey+1;//
            //红钥匙的标签直接设置为空
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
        }
        else if (gameObjects[hero.z][hero.y][hero.x].type.equals("bigkey")){
            System.out.println("得到大钥匙");
            hero.blueKey = hero.blueKey+1;//
            hero.redKey = hero.redKey+1;
            hero.yellowKey = hero.yellowKey+1;
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
        }
        else if (gameObjects[hero.z][hero.y][hero.x].type.equals("redbottle")){
            System.out.println("");
            hero.life = hero.life+((Redbottle)gameObjects[hero.z][hero.y][hero.x]).life;//
            //红钥匙的标签直接设置为空
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
        }
        else if (gameObjects[hero.z][hero.y][hero.x].type.equals("bluebottle")){
            System.out.println("");
            hero.life = hero.life+((Bluebottle)gameObjects[hero.z][hero.y][hero.x]).life;//
            //红钥匙的标签直接设置为空
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
        }

        else if (gameObjects[hero.z][hero.y][hero.x].type.equals("reddiamond")){
            System.out.println("");
            hero.attack = hero.attack+((Reddiamond)gameObjects[hero.z][hero.y][hero.x]).attack;//
            //红钥匙的标签直接设置为空
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
        }
        else if (gameObjects[hero.z][hero.y][hero.x].type.equals("bluediamond")){
            System.out.println("");
            hero.defence = hero.defence+((Bluediamond)gameObjects[hero.z][hero.y][hero.x]).defence;//
            //红钥匙的标签直接设置为空
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
        }
        else if (monsterNameSet.contains(gameObjects[hero.z][hero.y][hero.x].type)){
            System.out.println("遇到"+gameObjects[hero.z][hero.y][hero.x].type);
            //进行模拟战斗，如果能够打得过，就进行真战斗，然后设置位置等等，如果打不过，就直接退回原位
            boolean fightingResult = fight((Monster) gameObjects[hero.z][hero.y][hero.x]);
            if (fightingResult){//打得过
                System.out.println("打得过");
                //史莱姆的标签直接从bottom当中剔除
                bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
                //修改英雄标签所在的位置
                hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
                //英雄现在站立的地方变成英雄的坐标
                gameObjects[hero.z][hero.y][hero.x].type="hero";
                //原本站立的地方变成空地
                gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
            }else{//打不过
                System.out.println("打不过");
                hero.y = hero.y+LeftORRight;
                hero.x = hero.x+UpORDown;
            }
        }
        else if (gameObjects[hero.z][hero.y][hero.x].type.contains("greedy")){
            //遇到商店老板或者叫贪婪之神
            System.out.println("遇到商店老板");
            //显示交易的界面
            if(gameObjects[hero.z][hero.y][hero.x].type.contains("first")){
                gamePanel.setLayout(null);

                shopLabel.setSize(150,200);
                shopLabel.setLocation(430,120);
                shopLabel.setVisible(true);
                shopLabel.setOpaque(true);
                shopLabel.setBackground(Color.GRAY);

                JLabel title = new JLabel();
                title.setLayout(null);
                title.setSize(150,40);
                title.setLocation(0,0);
                title.setVisible(true);
                title.setOpaque(true);
                title.setBackground(Color.WHITE);
                title.setText("献祭就能更强");

                JLabel lifeImprove = new JLabel();
                lifeImprove.setLayout(null);
                lifeImprove.setSize(150,40);
                lifeImprove.setLocation(0,40);
                lifeImprove.setVisible(true);
                lifeImprove.setOpaque(true);

                lifeImprove.setBackground(Color.YELLOW);
                lifeImprove.setText("生命+800");

                JLabel attackImprove = new JLabel();
                attackImprove.setLayout(null);
                attackImprove.setSize(150,40);
                attackImprove.setLocation(0,80);
                attackImprove.setVisible(true);
                attackImprove.setOpaque(true);

                attackImprove.setBackground(Color.PINK);
                attackImprove.setText("攻击+800");

                JLabel defenceImprove = new JLabel();
                defenceImprove.setLayout(null);
                defenceImprove.setSize(150,40);
                defenceImprove.setLocation(0,120);
                defenceImprove.setVisible(true);
                defenceImprove.setOpaque(true);
                defenceImprove.setBackground(Color.ORANGE);
                defenceImprove.setText("防御+800");

                JLabel quit = new JLabel();
                quit.setLayout(null);
                quit.setSize(150,40);
                quit.setLocation(0,160);
                quit.setVisible(true);
                quit.setOpaque(true);

                quit.setBackground(Color.CYAN);
                quit.setText("取消");

                shopLabel.add(title);
                shopLabel.add(lifeImprove);
                shopLabel.add(attackImprove);
                shopLabel.add(defenceImprove);
                shopLabel.add(quit);


                hero.x = hero.x+UpORDown;
                hero.y = hero.y+LeftORRight;

            }



        }
        else if(gameObjects[hero.z][hero.y][hero.x].type.contains("sword")||gameObjects[hero.z][hero.y][hero.x].type.contains("shield")){
            System.out.println("得到"+gameObjects[hero.z][hero.y][hero.x].type);
            hero.defence = hero.defence+((Weapon)gameObjects[hero.z][hero.y][hero.x]).defence;//
            hero.attack = hero.attack+((Weapon)gameObjects[hero.z][hero.y][hero.x]).attack;//
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);

            //红钥匙的标签直接设置为空
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
        }
        else if (gameObjects[hero.z][hero.y][hero.x].type.equals("handbook")){
            System.out.println("得到"+"handbook");
            //红钥匙的标签直接设置为空
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
            hero.handbook = true;
        }
        else if (gameObjects[hero.z][hero.y][hero.x].type.equals("fly")){
            System.out.println("捡到飞行器！");
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
            hero.fly = true;
        }
        else{
            hero.x = hero.x+UpORDown;
            hero.y = hero.y+LeftORRight;
        }

    }

    public void heroGoUp() {
        UpORDown = 1;
        hero.x = hero.x-UpORDown;
        if(hero.x>=0){
            heroMove();
        }else{
            hero.x = hero.x+1;
        }
        UpORDown = 0;
    }



    public void setHeroStatus(){
        for(int i=0;i<hero.getClass().getDeclaredFields().length-4;i++){
            try{
                attributeLabels[i].setText(""+hero.getClass().getDeclaredFields()[i].getInt(hero));
            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }


    public void setFloorNum() {
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
