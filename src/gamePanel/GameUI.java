package gamePanel;
import GameObject.Characters.Enemy.*;
import GameObject.Characters.Hero;
import GameObject.Characters.SpecialChar.*;
import GameObject.GameObject;

import GameObject.Matter.Item.Bottle.Bluebottle;
import GameObject.Matter.Item.Bottle.Bottle;
import GameObject.Matter.Item.Bottle.Redbottle;
import GameObject.Matter.Item.Diamond.Bluediamond;
import GameObject.Matter.Item.Diamond.Diamond;
import GameObject.Matter.Item.Diamond.Reddiamond;
import GameObject.Matter.Item.Flyfeather;
import GameObject.Matter.Item.Key.Key;
import GameObject.Matter.Item.Weapon.Weapon;
import com.sun.xml.internal.ws.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class GameUI{

    int floorNum = 0;
    int simulateFloorNum;
    JLabel floorLabel = new JLabel();

    Map<String,Integer> cellMap = new HashMap<>();
    JLabel shopLabel = new JLabel();
    JLabel title = new JLabel();
    JLabel firstLine = new JLabel();
    JLabel secondLine = new JLabel();
    JLabel thirdLine = new JLabel();
    JLabel quit = new JLabel();

    String shopDirect = "";//表示商店在主角的什么方位


    JLabel flyLabel = new JLabel();
    JLabel flyLabelBottom = new JLabel();
    JLabel flyLabelTitle = new JLabel();
    JLabel handbookLabel = new JLabel();
    JFrame gameFrame = new JFrame();
    JLabel bottom = new JLabel();
    JPanel gamePanel = new JPanel();

    JLabel dialogLabel = new JLabel();

    JLabel gameOverLabel = new JLabel();

    JLabel bgLabel = new JLabel();
    GameObject [][][] gameObjects = new GameObject[22][11][11];
    Hero hero = new Hero();

    Set<String>monsterNameSet = new HashSet<>();

    JLabel[]attributeLabels = new JLabel[9];

    public int UpORDown =  0;
    public int LeftORRight = 0;


    public final int BLANK = 0;


    public void initMonsterNameSet(){
        //todo
        BufferedReader br = new BufferedReader(
                new InputStreamReader(this.getClass()
                        .getClassLoader()
                        .getResourceAsStream("gamePanel/MonsterName.txt")));
        String s = null;
        try{
            while((s=br.readLine())!=null){
                //在这里进行读取
                String line = s.toLowerCase();
                monsterNameSet.add(line);

            }


        }catch (Exception e){
            e.printStackTrace();
        }



    }

    public void writeMonsterNameFile(){
        //todo
        //这里打成jar包的情况下无法读取到
        Class[] classByPackage = ClassUtils.getClassByPackage("GameObject.Characters.Enemy");
        String classNameSum = "";
        File file = new File("src/gamePanel/MonsterName.txt");

        for (Class aClass : classByPackage) {
            String className = aClass.getName();
            if (className.equals("Monster")){
                continue;
            }
            className = className.replace("GameObject.Characters.Enemy.","");
            classNameSum = classNameSum+className+"\r\n";
            monsterNameSet.add(className.toLowerCase());
        }

        try {
            byte[]bytes = classNameSum.getBytes();
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public GameUI(){
        floorNum = 0;
        shopLabel.setVisible(false);
        gamePanel.add(shopLabel);
        shopLabel.add(title);
        shopLabel.add(firstLine);
        shopLabel.add(secondLine);
        shopLabel.add(thirdLine);
        shopLabel.add(quit);


        initMonsterNameSet();
        initCellMap();
        setFloor();//设置棋盘，[21][11][11]每一个点都放着东西，不是勇士就是墙就是敌人或者是道具
        JFrame.setDefaultLookAndFeelDecorated(true);
        gameFrame.setSize(820,600);
        setAttributeLabels();
        setGameFrame();//设置gameFrame，比如大小，高度，还有键盘的触发事件
        setBgLabel();
        setBottom();
        setFlyLabelBottom();
        setCells();//设置所有的棋盘上的格子
        addComponent2Floor();//将人物还有道具全部都加进bottom里面
        showFloor();//把所有的墙还有人物还有道具全部都显示出来
        setHeroStatus();//设置英雄的状态
        setFloorNum();//设置楼层功能表里面的楼层数



        dialogLabel.setVisible(false);
        gamePanel.add(dialogLabel);

        flyLabelTitle.setVisible(false);
        gamePanel.add(flyLabelTitle);
        gamePanel.add(flyLabel);
        gamePanel.add(flyLabelBottom);
        handbookLabel.setVisible(false);
        gamePanel.add(handbookLabel);

        gameOverLabel.setVisible(false);
        gamePanel.add(gameOverLabel);

        bgLabel.add(bottom);
        gamePanel.add(bgLabel);


        gameFrame.setContentPane(gamePanel);


    }

    private void setFlyLabelBottom() {
        flyLabelBottom.setLocation(320,100);

        flyLabelBottom.setVisible(false);
        flyLabelBottom.setBackground(Color.GRAY);
        flyLabelBottom.setSize(370,300);

    }

    public void initCellMap(){
        cellMap.put("blank",BLANK);
    }





    public void setBottom(){
        //设置bottom
        bottom.setLocation(200,-55);
        ImageIcon bottomIcon = new ImageIcon(Image.class.getResource("imageResource/BlankBg.png"));
        bottomIcon.setImage(bottomIcon.getImage().getScaledInstance(374,370,1));
        bottom.setIcon(bottomIcon);
        bottom.setSize(500,560);
        bottom.setLocation(199,-60);
        bottom.setVisible(true);

    }

    public void setBgLabel() {
        //只设置bgLabel
        bgLabel.setLocation(0,0);
        ImageIcon bgIcon = new ImageIcon(Image.class.getResource("imageResource/GameBg.png"));
        bgIcon.setImage(bgIcon.getImage().getScaledInstance(605,438,1));
        bgLabel.setIcon(bgIcon);
        bgLabel.setSize(520,520);
    }



    public void setAttributeLabels() {
        int[]yCoor = new int[]{-85,-55,-30,-5,25,50,90,125,155};

        for (int i=0;i<hero.getClass().getDeclaredFields().length;i++){
            if (hero.getClass().getDeclaredFields()[i].getType().isAssignableFrom(int.class)){
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
    }


    public void setFloor() {
        //从floor.txt当中读取数据
        int i=0;
        int j=0;
        int k=0;
        //原始的读取文件的方式
//        String path = "src/gamePanel/floor.txt";
//        File floorTxt = new File(path);
//        BufferedReader br = null;

        BufferedReader br = new BufferedReader(
                new InputStreamReader(this.getClass()
                        .getClassLoader()
                        .getResourceAsStream("gamePanel/floor.txt")));

        try{
//            br = new BufferedReader(new FileReader(floorTxt));
            String s = null;
            while((s=br.readLine())!=null){
                //在这里进行读取
                String line = s.toLowerCase();
                if(s.equals("")||s.startsWith("#")){
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

                    gameObjects[i][j][k] = key;
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

                    gameObjects[i][j][k] = bottle;
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

                    gameObjects[i][j][k] = diamond;
                }
                else if(line.contains("greedy")){
                    if (line.contains("first")){
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

                        gameObjects[i][j][k] = firstgreedy;
                    }
                    else if (line.contains("second")){
                        Secondgreedy secondgreedy = new Secondgreedy();
                        try{
                            Constructor<?> constructor = Class.forName("GameObject.Characters.SpecialChar." + StringUtils.capitalize(line)).getConstructor();
                            secondgreedy = (Secondgreedy) constructor.newInstance();
                            secondgreedy.type = line;
                            secondgreedy.x = k;
                            secondgreedy.y = j;
                            secondgreedy.z = i;
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        gameObjects[i][j][k] = secondgreedy;
                    }

                }
                else if(line.contains("hero")){
                    GameObject gameObject = new GameObject();
                    gameObject.type = line;
                    gameObject.x = k;
                    gameObject.y = j;
                    gameObject.z = i;
                    gameObjects[i][j][k] = gameObject;

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

                    gameObjects[i][j][k] = weapon;
                }
                else if(line.contains("celler")){
                    Celler celler = new Celler();
                    try{
                        Constructor<?> constructor = Class.forName("GameObject.Characters.SpecialChar." + StringUtils.capitalize(line)).getConstructor();
                        celler = (Celler) constructor.newInstance();
                        celler.type = line;
                        celler.x = k;
                        celler.y = j;
                        celler.z = i;
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    gameObjects[i][j][k] = celler;
                }
                else if(line.contains("oldman")){
                    Oldman oldman = new Oldman();
                    try{
                        Constructor<?> constructor = Class.forName("GameObject.Characters.SpecialChar." + StringUtils.capitalize(line)).getConstructor();
                        oldman = (Oldman) constructor.newInstance();
                        oldman.type = line;
                        oldman.x = k;
                        oldman.y = j;
                        oldman.z = i;
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    gameObjects[i][j][k] = oldman;
                }


                else {
                    GameObject gameObject = new GameObject();
                    gameObject.type = line;
                    gameObject.x = k;
                    gameObject.y = j;
                    gameObject.z = i;
                    gameObjects[i][j][k] = gameObject;
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
        for (int i=0;i<hero.getClass().getDeclaredFields().length-4;i++){
            if (hero.getClass().getDeclaredFields()[i].getType().isAssignableFrom(int.class)){
                try{
                    attributeLabels[i].setText(""+hero.getClass().getDeclaredFields()[i].getInt(hero));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }



        }


    }


    public void addComponent2Floor(){
        bottom.add(hero.gameObjectLabel);
        flyLabel.add(hero.simulateGameObjectLabel);
        for(int i=0;i<22;i++){//楼层
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(gameObjects[i][j][k]!=null&&!gameObjects[i][j][k].type.equals("blank")&&!gameObjects[i][j][k].type.equals("hero")){
                        bottom.add(gameObjects[i][j][k].gameObjectLabel);
                        flyLabel.add(gameObjects[i][j][k].simulateGameObjectLabel);
                    }
                }
            }
        }

    }

    public void showFlyFloor() {
        for(int i=0;i<22;i++){
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(gameObjects[i][j][k]!=null&&!gameObjects[i][j][k].type.equals("blank")&&!gameObjects[i][j][k].type.equals("hero")){
                        if(i==simulateFloorNum){
                            gameObjects[i][j][k].simulateGameObjectLabel.setVisible(true);
                        }else{
                            gameObjects[i][j][k].simulateGameObjectLabel.setVisible(false);
                        }
                    }
                }
            }
        }

        hero.simulateGameObjectLabel.setVisible(false);
    }

    public void showFloor() {
        for(int i=0;i<22;i++){
            for(int j=0;j<11;j++){//j==y
                for(int k=0;k<11;k++){//i==x
                    if(gameObjects[i][j][k]!=null&&gameObjects[i][j][k].type.equals("blank")==false&&gameObjects[i][j][k].type.equals("hero")==false){
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
                    if (null!=gameObjects[i][j][k]&&!gameObjects[i][j][k].type.equals("blank")){
                        addNewGameObject(i,j,k);
                    }
                }
            }
        }

    }

    private void addNewGameObject(int i,int j,int k) {

        if (gameObjects[i][j][k].type.equals("blank")){
            return;
        }
        JLabel gameObjectLabel = new JLabel();
        gameObjectLabel.setSize(34,34);
        gameObjectLabel.setLocation(34*j,94+34*k);

        System.out.println(gameObjects[i][j][k].type);
        ImageIcon gameObjectIcon = new ImageIcon(Image.class.getResource("imageResource/" +gameObjects[i][j][k].type+".png"));
        gameObjectIcon.setImage(gameObjectIcon.getImage().getScaledInstance(34,34,1));
        gameObjectLabel.setIcon(gameObjectIcon);



        ImageIcon simulateGameObjectIcon = new ImageIcon(Image.class.getResource("imageResource/" +gameObjects[i][j][k].type+".png"));
        JLabel simulateGameObjectLabel = new JLabel();
        simulateGameObjectLabel.setSize(24,24);
        simulateGameObjectLabel.setLocation(24*j,24*k);
        simulateGameObjectIcon.setImage(simulateGameObjectIcon.getImage().getScaledInstance(24,24,1));
        simulateGameObjectLabel.setIcon(simulateGameObjectIcon);


        if (gameObjects[i][j][k].type.equals("hero")){
            hero.z = i;
            hero.y = j;
            hero.x = k;
            hero.gameObjectLabel = gameObjectLabel;
            hero.simulateGameObjectLabel = simulateGameObjectLabel;
        }else {
            gameObjects[i][j][k].simulateGameObjectLabel = simulateGameObjectLabel;
            gameObjects[i][j][k].gameObjectLabel=gameObjectLabel;
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
        if (dialogLabel.isVisible()){
            if (e.getKeyCode()==KeyEvent.VK_ENTER||e.getKeyCode()==KeyEvent.VK_SPACE){
                dialogLabel.setVisible(false);
            }
        }
        else if (shopLabel.isVisible()){
            doShopping(e);
        }
        else if(handbookLabel.isVisible()){
            //如果手册开着
            if (e.getKeyCode()==KeyEvent.VK_L){
                handbookLabel.setVisible(false);
            }
        }
        else if(flyLabelBottom.isVisible()){
            //如果飞行器开着
            doFlying(e);
        }
        else{
            //一般状态

            heroDoMove(e);

            if (hero.handbook==true&&e.getKeyCode()==KeyEvent.VK_L){
                System.out.println("开始handbook");
                gamePanel.setLayout(null);
                handbookLabel.setVisible(true);
                handbookLabel.setOpaque(true);
                handbookLabel.setLocation(300,30);
                handbookLabel.setBackground(Color.BLACK);
                handbookLabel.setSize(400,400);
                setHandBook();

            }
            else if (hero.fly==true&&e.getKeyCode()==KeyEvent.VK_F){
                simulateFloorNum = floorNum;
                showFlyFloor();
                System.out.println("开始楼层跳跃");
                gamePanel.setLayout(null);
                flyLabelBottom.setVisible(true);
                flyLabelBottom.setOpaque(true);
                ImageIcon flyLabelIcon = new ImageIcon(Image.class.getResource("imageResource/BlankBg.png"));
                flyLabelIcon.setImage(flyLabelIcon.getImage().getScaledInstance(300,300,1));
                flyLabel.setIcon(flyLabelIcon);
                flyLabel.setLocation(330,120);
                flyLabel.setSize(264,264);
                flyLabel.setOpaque(true);
                flyLabel.setVisible(true);

                setFlyLabelTitle();

//                setFlyLabel();
            }

        }

        reLoadGameFrame();
    }




    public void setFlyLabelTitle(){
        flyLabelTitle.setText("第"+simulateFloorNum+"层");
        flyLabelTitle.setBackground(Color.WHITE);
        flyLabelTitle.setLocation(600,230);
        flyLabelTitle.setSize(90,30);
        flyLabelTitle.setOpaque(true);
        flyLabelTitle.setVisible(true);
    }





    private void doFlying(KeyEvent e) {
        //重新显示飞行的页面
        showFlyFloor();
        setFlyLabelTitle();
        if(e.getKeyCode()==KeyEvent.VK_UP){
            if (simulateFloorNum<=20){
                simulateFloorNum = simulateFloorNum + 1;
                showFlyFloor();
            }
            setFlyLabelTitle();
        }else if (e.getKeyCode()==KeyEvent.VK_DOWN){
            if (simulateFloorNum>=1){
                simulateFloorNum = simulateFloorNum - 1;
                showFlyFloor();
            }
            setFlyLabelTitle();
        }else if(e.getKeyCode()==KeyEvent.VK_F) {
            flyLabelBottom.setVisible(false);
            flyLabel.setVisible(false);
            flyLabelTitle.setVisible(false);
        }else if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if (floorNum<=simulateFloorNum){
                gameObjects[hero.z][hero.y][hero.x].type="blank";
                floorNum = simulateFloorNum;
                SetHeroCoor(true);
                gameObjects[hero.z][hero.y][hero.x].type="hero";

            }else {
                gameObjects[hero.z][hero.y][hero.x].type="blank";
                floorNum = simulateFloorNum;
                SetHeroCoor(false);
                gameObjects[hero.z][hero.y][hero.x].type="hero";
            }
            flyLabelBottom.setVisible(false);
            flyLabel.setVisible(false);
            flyLabelTitle.setVisible(false);
        }
        else {
            System.out.println("暂时不支持");

        }



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



    private void doShopping(KeyEvent e) {
        int shopX = hero.x;
        int shopY = hero.y;
        if (shopDirect.equals("UP")){
            shopX = shopX - 1;
        }
        else if(shopDirect.equals("DOWN")){
            shopX = shopX + 1;
        }
        else if(shopDirect.equals("LEFT")){
            shopY = shopY -1;
        }
        else if(shopDirect.equals("RIGHT")){
            shopY = shopY+1;
        }
        else{
            System.out.println("方位无变化");
        }
        if (floorNum==5){
            if (Oldman.class.isAssignableFrom(gameObjects[floorNum][shopY][shopX].getClass())){
                if(e.getKeyCode()==KeyEvent.VK_A){
                    hero.experience = hero.experience-30;
                    hero.attack = hero.attack+5;
                }
                else if(e.getKeyCode()==KeyEvent.VK_Q){
                    shopDirect = "NO";
                    title.setVisible(false);
                    firstLine.setVisible(false);
                    secondLine.setVisible(false);
                    thirdLine.setVisible(false);
                    shopLabel.setVisible(false);
                }
                else if(e.getKeyCode()==KeyEvent.VK_S){
                    hero.experience = hero.experience-30;
                    hero.defence = hero.defence+5;
                }
                else if(e.getKeyCode()==KeyEvent.VK_D){
                    hero.experience = hero.experience-100;
                    hero.level = hero.level+1;
                }
                else{
                    shopLabel.setVisible(true);
                }
            }
            else if(Celler.class.isAssignableFrom(gameObjects[floorNum][shopY][shopX].getClass())){
                if(e.getKeyCode()==KeyEvent.VK_A){
                    hero.coin = hero.coin-50;
                    hero.bluekey = hero.bluekey+1;
                }
                else if(e.getKeyCode()==KeyEvent.VK_Q){
                    shopLabel.setVisible(false);
                    System.out.println("取消");
                }
                else if(e.getKeyCode()==KeyEvent.VK_S){
                    hero.coin = hero.coin-100;
                    hero.redkey = hero.redkey+1;
                }
                else if(e.getKeyCode()==KeyEvent.VK_D){
                    hero.coin = hero.coin-10;
                    hero.yellowkey = hero.yellowkey+1;
                }
                else{
                    shopLabel.setVisible(true);
                }
            }

        }

        else if (floorNum==3){
            if(e.getKeyCode()==KeyEvent.VK_A){
                hero.coin = hero.coin-25;
                hero.attack = hero.attack+4;
                System.out.println("攻击");
            }
            else if(e.getKeyCode()==KeyEvent.VK_Q){
                shopDirect = "NO";
                title.setVisible(false);
                firstLine.setVisible(false);
                secondLine.setVisible(false);
                thirdLine.setVisible(false);
                shopLabel.setVisible(false);
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
        }

        else if (floorNum==11){
            if(e.getKeyCode()==KeyEvent.VK_A){
                hero.coin = hero.coin-100;
                hero.attack = hero.attack+20;
                System.out.println("攻击");
            }
            else if(e.getKeyCode()==KeyEvent.VK_Q){
                shopDirect = "NO";
                title.setVisible(false);
                firstLine.setVisible(false);
                secondLine.setVisible(false);
                thirdLine.setVisible(false);
                shopLabel.setVisible(false);
                System.out.println("取消");
            }
            else if(e.getKeyCode()==KeyEvent.VK_S){
                hero.coin = hero.coin-100;
                hero.defence = hero.defence+20;
                System.out.println("防御");
            }
            else if(e.getKeyCode()==KeyEvent.VK_D){
                hero.coin = hero.coin-100;
                hero.life = hero.life+4000;
                System.out.println("生命");
            }
            else{
                shopLabel.setVisible(true);
            }
        }

        else if (floorNum==12){
            if(e.getKeyCode()==KeyEvent.VK_A){
                hero.coin = hero.coin+30;
                hero.bluekey = hero.bluekey-1;
            }
            else if(e.getKeyCode()==KeyEvent.VK_Q){
                shopLabel.setVisible(false);
                System.out.println("取消");
            }
            else if(e.getKeyCode()==KeyEvent.VK_S){
                hero.coin = hero.coin+70;
                hero.redkey = hero.redkey-1;
            }
            else if(e.getKeyCode()==KeyEvent.VK_D){
                hero.coin = hero.coin+7;
                hero.yellowkey = hero.yellowkey-1;
            }
            else{
                shopLabel.setVisible(true);
            }
        }

        else if (floorNum==13){
            if(e.getKeyCode()==KeyEvent.VK_A){
                hero.experience = hero.experience-90;
                hero.attack = hero.attack+5;
            }
            else if(e.getKeyCode()==KeyEvent.VK_Q){
                shopDirect = "NO";
                title.setVisible(false);
                firstLine.setVisible(false);
                secondLine.setVisible(false);
                thirdLine.setVisible(false);
                shopLabel.setVisible(false);
            }
            else if(e.getKeyCode()==KeyEvent.VK_S){
                hero.experience = hero.experience-90;
                hero.defence = hero.defence+5;
            }
            else if(e.getKeyCode()==KeyEvent.VK_D){
                hero.experience = hero.experience-270;
                hero.level = hero.level+1;
            }
            else{
                shopLabel.setVisible(true);
            }

        }
        else {
            System.out.println("此地无商店");
        }
    }

    private void heroDoMove(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_UP){
            heroGoUp(e);
        }
        else if (e.getKeyCode()==KeyEvent.VK_DOWN){
            heroGoDown(e);
        }
        else if (e.getKeyCode()==KeyEvent.VK_LEFT){
            heroGoLeft(e);
        }
        else if (e.getKeyCode()==KeyEvent.VK_RIGHT){
            heroGoRight(e);
        }
        else{
            System.out.println("is not ready!");
        }


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
                    if (!gameObjects[floorNum][j][k].type.equals("blank")){
                        monsterMap.put(gameObjects[floorNum][j][k].getClass(), (Monster) gameObjects[floorNum][j][k]);
                    }
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
            int loss = calculateLoss(monsterMap.get(aClass));
            String lossStr = "";
            if (loss<0){
                lossStr = "无法战胜";
            }else{
                lossStr = ""+loss;
            }
            damageLabel.setText("损失 "+lossStr);
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


    public void heroGoRight(KeyEvent e) {
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

    public void heroGoLeft(KeyEvent e) {
        LeftORRight = 1;
        hero.y = hero.y-LeftORRight;
        if(hero.y>=0){
            heroMove();
        }else{
            hero.y = hero.y+1;
        }
        LeftORRight = 0;

    }


    public int calculateLoss(Monster monster){

        //获取simulateHero和simulateMonster的模拟数据
        Hero simulateHero = new Hero() ;//模拟的英雄的数据
        simulateHero.attack =  hero.attack;
        simulateHero.defence = hero.defence;
        simulateHero.life = hero.life;

        Monster simulateMonster = new Monster();
        simulateMonster.attack = monster.attack;
        simulateMonster.defence = monster.defence;
        simulateMonster.life = monster.life;

        if (Vampire.class.isAssignableFrom(monster.getClass())){
            if (Whitewarrior.class.isAssignableFrom(monster.getClass())){
                simulateHero.life = simulateHero.life*3/4;
            }else if(Soulwitch.class.isAssignableFrom(monster.getClass())){
                simulateHero.life = simulateHero.life*2/3;
            }
        }
        if (Fixeddamage.class.isAssignableFrom(monster.getClass())){
            if (Juniorwitch.class.isAssignableFrom(monster.getClass())){
                simulateHero.life = simulateHero.life-100;
            }else if(Seniorwitch.class.isAssignableFrom(monster.getClass())){
                simulateHero.life = simulateHero.life-300;
            }
        }




        if(simulateMonster.defence>=simulateHero.attack){
            return -1;
        }else if(simulateHero.defence>=simulateMonster.attack){
            hero.coin = hero.coin+monster.coin;
            hero.experience = hero.experience+monster.experience;
            return 0;
        }else {
            while (simulateMonster.life>0){//怪物的生命值不为0的时候
                //英雄砍怪物一刀
                simulateMonster.life = simulateMonster.life-(simulateHero.attack-simulateMonster.defence);
                if (simulateMonster.life<=0){
                    break;
                }
                //怪物砍英雄一刀
                simulateHero.life =simulateHero.life-(simulateMonster.attack-simulateHero.defence);

            }
            return hero.life - simulateHero.life;
        }



    }
    public boolean fight(Monster monster) {
        boolean result = false;
        int loss = calculateLoss(monster);
        if (loss<0){
            result = false;
        }else if (loss>=0){
            if(loss>=hero.life){
                result = false;
            }else {
                hero.life = hero.life-loss;
                hero.coin = hero.coin+monster.coin;
                hero.experience = hero.experience+monster.experience;
                result = true;
            }
        }
        return result;

    }

    public void heroGoDown(KeyEvent e) {
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
        else if(hero.yellowkey>=1&&gameObjects[hero.z][hero.y][hero.x].type.equals("yellowdoor")){
            System.out.println("踩到黄门");
            hero.yellowkey = hero.yellowkey-1;//黄钥匙数量-1
            //黄门的标签直接设置为空
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
            flyLabel.remove(gameObjects[hero.z][hero.y][hero.x].simulateGameObjectLabel);
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";

        }
        else if(hero.bluekey>=1&&gameObjects[hero.z][hero.y][hero.x].type.equals("bluedoor")){
            System.out.println("踩到");
            hero.bluekey = hero.bluekey-1;//黄钥匙数量-1
            //黄门的标签直接设置为空
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
            flyLabel.remove(gameObjects[hero.z][hero.y][hero.x].simulateGameObjectLabel);
            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";

        }
        else if (hero.redkey>=1&&gameObjects[hero.z][hero.y][hero.x].type.equals("reddoor")){
            System.out.println("踩到红门");
            hero.redkey = hero.redkey-1;//红钥匙数量-1
            //从bottom当中去除此红门
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
            flyLabel.remove(gameObjects[hero.z][hero.y][hero.x].simulateGameObjectLabel);
            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";

        }
        else if (gameObjects[hero.z][hero.y][hero.x].type.equals("upstair")){
            if (floorNum<20){
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
                if (floorNum==17){
                    //17层怪物都会变异升级
                    monsterMutate();


                }


            }else {
                //todo
                //上21楼，螟蛉魔王也会变异升级
                System.out.println("上第21层");
                //楼层数+1
                floorNum = floorNum+1;
                monsterMutate();

                //原本站立的地方变成空地
                gameObjects[20][5+LeftORRight][6+UpORDown].type="blank";//hero.x-1
                //修改英雄标签所在的位置，改为上一层的下楼所在的坐标
                hero.z = 21;
                hero.y = 5;
                hero.x = 5;

                //todo
                // 确定一下这个坐标
                hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown-34
                );


                //将英雄的现在的位置在floor里面设置值
                gameObjects[hero.z][hero.y][hero.x].type="hero";
                //修改英雄标签所在的位置,将其设置为新楼层的downstairs所在的位置
            }

        }
        else if(gameObjects[hero.z][hero.y][hero.x].type.equals("fence")){
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
            flyLabel.remove(gameObjects[hero.z][hero.y][hero.x].simulateGameObjectLabel);
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
        else if (gameObjects[hero.z][hero.y][hero.x].type.contains("key")&&!gameObjects[hero.z][hero.y][hero.x].type.contains("big")){
            System.out.println("得到"+gameObjects[hero.z][hero.y][hero.x].type);
            try {
                Field f1 = hero.getClass().getDeclaredField(gameObjects[hero.z][hero.y][hero.x].type);
                f1.set(hero, (Integer)f1.get(hero)+1);
            }catch (Exception e){
                e.printStackTrace();
            }
            //钥匙的标签直接设置为空
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
            flyLabel.remove(gameObjects[hero.z][hero.y][hero.x].simulateGameObjectLabel);

            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
        }


        else if (gameObjects[hero.z][hero.y][hero.x].type.equals("bigkey")){
            System.out.println("得到大钥匙");
            hero.bluekey = hero.bluekey+1;//
            hero.redkey = hero.redkey+1;
            hero.yellowkey= hero.yellowkey+1;
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
            flyLabel.remove(gameObjects[hero.z][hero.y][hero.x].simulateGameObjectLabel);

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
            flyLabel.remove(gameObjects[hero.z][hero.y][hero.x].simulateGameObjectLabel);
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
            flyLabel.remove(gameObjects[hero.z][hero.y][hero.x].simulateGameObjectLabel);
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
            flyLabel.remove(gameObjects[hero.z][hero.y][hero.x].simulateGameObjectLabel);
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
            flyLabel.remove(gameObjects[hero.z][hero.y][hero.x].simulateGameObjectLabel);
//                    yellowDoors[hero.z][hero.y][hero.x]=null;
            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
        }
        else if(Monster.class.isAssignableFrom(gameObjects[hero.z][hero.y][hero.x].getClass())){
            System.out.println("遇到"+gameObjects[hero.z][hero.y][hero.x].type);
            boolean fightingResult = fight((Monster) gameObjects[hero.z][hero.y][hero.x]);
            ;
            //进行模拟战斗，如果能够打得过，就进行真战斗，然后设置位置等等，如果打不过，就直接退回原位


            if (fightingResult){//打得过
                System.out.println("打得过");
                //史莱姆的标签直接从bottom当中剔除
                bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
                flyLabel.remove(gameObjects[hero.z][hero.y][hero.x].simulateGameObjectLabel);
                //修改英雄标签所在的位置
                hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
                //英雄现在站立的地方变成英雄的坐标
                gameObjects[hero.z][hero.y][hero.x].type="hero";
                //原本站立的地方变成空地
                gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
                if (Devilking.class.isAssignableFrom(gameObjects[hero.z][hero.y][hero.x].getClass())&&floorNum==21){
                    gamePanel.setLayout(null);
                    gameOverLabel.setSize(374,374);
                    gameOverLabel.setLocation(310,35);
                    gameOverLabel.setVisible(true);
                    gameOverLabel.setOpaque(true);
                    gameOverLabel.setBackground(Color.GRAY);
                    gameOverLabel.setText("<html><body>魔王被打败了，仙子也恢复了魔法，公主也被救出来了.<br>" +
                            "走出城外，一切都是那么的平常。国王迎接了他们，并把王位传给了勇者，<br>" +
                            "从此以后，他们过上了幸福快乐的生活<br>" +
                            "The end");
                    Font font = new Font("宋体",Font.PLAIN,20);
                    gameOverLabel.setFont(font);

                }
            }else{//打不过
                System.out.println("打不过");
                hero.y = hero.y+LeftORRight;
                hero.x = hero.x+UpORDown;
            }
        }


        else if(Shop.class.isAssignableFrom(gameObjects[hero.z][hero.y][hero.x].getClass())){
            //可以交易的
            if (UpORDown!=0){
                if (UpORDown<0){
                    shopDirect = "DOWN";
                }else {
                    shopDirect = "UP";
                }
            }else{
                if (LeftORRight<0){
                    shopDirect = "RIGHT";
                }else if (LeftORRight==0){
                    shopDirect = "NO";
                }else {
                    shopDirect = "LEFT";
                }

            }
            System.out.println("开始交易:"+gameObjects[hero.z][hero.y][hero.x].type);


            if (Greedy.class.isAssignableFrom(gameObjects[hero.z][hero.y][hero.x].getClass())){
                if (Firstgreedy.class.isAssignableFrom(gameObjects[hero.z][hero.y][hero.x].getClass())){
                    Firstgreedy firstgreedy = (Firstgreedy) gameObjects[hero.z][hero.y][hero.x];
                    title.setText(firstgreedy.title);
                    firstLine.setText(firstgreedy.firstLine);
                    secondLine.setText(firstgreedy.secondLine);
                    thirdLine.setText(firstgreedy.thirdLine);
                }else if(Secondgreedy.class.isAssignableFrom(gameObjects[hero.z][hero.y][hero.x].getClass())){
                    Secondgreedy secondgreedy = (Secondgreedy) gameObjects[hero.z][hero.y][hero.x];
                    title.setText(secondgreedy.title);
                    firstLine.setText(secondgreedy.firstLine);
                    secondLine.setText(secondgreedy.secondLine);
                    thirdLine.setText(secondgreedy.thirdLine);
                }else{
                    System.out.println("不存在该商店!");
                }
            }

            if (Oldman.class.isAssignableFrom(gameObjects[hero.z][hero.y][hero.x].getClass())){
                Oldman oldman = (Oldman) gameObjects[hero.z][hero.y][hero.x];



                if (floorNum==5){
                    oldman.attack = 30;
                    oldman.defence = 30;
                    oldman.experience = 100;
                    oldman.init();
                }
                if (floorNum==13){
                    oldman.attack = 70;
                    oldman.defence = 70;
                    oldman.experience = 270;
                    oldman.init();
                }
                title.setText(oldman.title);
                firstLine.setText(oldman.firstLine);
                secondLine.setText(oldman.secondLine);
                thirdLine.setText(oldman.thirdLine);
                if (floorNum==2){
                    //todo
                    //可能后续这里还需要加入和老人的对话，勇敢的孩子之类的过场话
                    hero.attack = hero.attack+10;
                    gameObjects[floorNum][hero.y][hero.x].type = "blank";
                    bottom.remove(gameObjects[floorNum][hero.y][hero.x].gameObjectLabel);
                    flyLabel.remove(gameObjects[floorNum][hero.y][hero.x].simulateGameObjectLabel);
                    gameObjects[floorNum][hero.y][hero.x].gameObjectLabel.setVisible(false);
                    hero.x = hero.x+UpORDown;
                    hero.y = hero.y+LeftORRight;
                    return;
                }
                if (floorNum==15){
                    //又是卖剑的老人
                    //todo
                    //可能后续这里还需要加入和老人的对话，勇敢的孩子之类的过场话
                    hero.attack = hero.attack+100;
                    gameObjects[floorNum][hero.y][hero.x].type = "blank";
                    bottom.remove(gameObjects[floorNum][hero.y][hero.x].gameObjectLabel);
                    flyLabel.remove(gameObjects[floorNum][hero.y][hero.x].simulateGameObjectLabel);
                    gameObjects[floorNum][hero.y][hero.x].gameObjectLabel.setVisible(false);
                    hero.x = hero.x+UpORDown;
                    hero.y = hero.y+LeftORRight;
                    return;
                }
            }

            if (Celler.class.isAssignableFrom(gameObjects[hero.z][hero.y][hero.x].getClass())){
                Celler celler = (Celler) gameObjects[hero.z][hero.y][hero.x];
                if (floorNum==12){
                    celler.init();
                }
                if (floorNum==5){
                    celler.init();
                }
                title.setText(celler.title);
                firstLine.setText(celler.firstLine);
                secondLine.setText(celler.secondLine);
                thirdLine.setText(celler.thirdLine);
                if (floorNum==2){
                    //todo
                    //可能后续这里还需要加入和老人的对话，勇敢的孩子之类的过场话
                    hero.defence = hero.defence+10;
                    gameObjects[floorNum][hero.y][hero.x].type = "blank";
                    bottom.remove(gameObjects[floorNum][hero.y][hero.x].gameObjectLabel);
                    flyLabel.remove(gameObjects[floorNum][hero.y][hero.x].simulateGameObjectLabel);
                    gameObjects[floorNum][hero.y][hero.x].gameObjectLabel.setVisible(false);
                    hero.x = hero.x+UpORDown;
                    hero.y = hero.y+LeftORRight;
                    return;
                }

                if (floorNum==15){
                    //todo
                    //可能后续这里还需要加入和老人的对话，勇敢的孩子之类的过场话
                    hero.defence = hero.defence+100;
                    gameObjects[floorNum][hero.y][hero.x].type = "blank";
                    bottom.remove(gameObjects[floorNum][hero.y][hero.x].gameObjectLabel);
                    flyLabel.remove(gameObjects[floorNum][hero.y][hero.x].simulateGameObjectLabel);

                    gameObjects[floorNum][hero.y][hero.x].gameObjectLabel.setVisible(false);
                    hero.x = hero.x+UpORDown;
                    hero.y = hero.y+LeftORRight;
                    return;
                }



            }


            gamePanel.setLayout(null);
            shopLabel.setSize(150,200);
            shopLabel.setLocation(430,120);
            shopLabel.setVisible(true);
            shopLabel.setOpaque(true);
            shopLabel.setBackground(Color.GRAY);

            title.setSize(150,40);
            title.setLocation(0,0);
            title.setVisible(true);
            title.setOpaque(true);
            title.setBackground(Color.WHITE);

            firstLine.setSize(150,40);
            firstLine.setLocation(0,40);
            firstLine.setVisible(true);
            firstLine.setOpaque(true);

            firstLine.setBackground(Color.YELLOW);

            secondLine.setSize(150,40);
            secondLine.setLocation(0,80);
            secondLine.setVisible(true);
            secondLine.setOpaque(true);

            secondLine.setBackground(Color.PINK);

            thirdLine.setSize(150,40);
            thirdLine.setLocation(0,120);
            thirdLine.setVisible(true);
            thirdLine.setOpaque(true);
            thirdLine.setBackground(Color.ORANGE);

            quit.setSize(150,40);
            quit.setLocation(0,160);
            quit.setVisible(true);
            quit.setOpaque(true);

            quit.setBackground(Color.CYAN);
            quit.setText("取消(q)");

            shopLabel.setLayout(null);

            shopLabel.setVisible(true);

            hero.x = hero.x+UpORDown;
            hero.y = hero.y+LeftORRight;


        }


        else if(gameObjects[hero.z][hero.y][hero.x].type.contains("sword")||gameObjects[hero.z][hero.y][hero.x].type.contains("shield")){
            System.out.println("得到"+gameObjects[hero.z][hero.y][hero.x].type);
            hero.defence = hero.defence+((Weapon)gameObjects[hero.z][hero.y][hero.x]).defence;//
            hero.attack = hero.attack+((Weapon)gameObjects[hero.z][hero.y][hero.x]).attack;//
            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
            flyLabel.remove(gameObjects[hero.z][hero.y][hero.x].simulateGameObjectLabel);

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
            flyLabel.remove(gameObjects[hero.z][hero.y][hero.x].simulateGameObjectLabel);
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
            flyLabel.remove(gameObjects[hero.z][hero.y][hero.x].simulateGameObjectLabel);
            //修改英雄标签所在的位置
            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);
            //英雄现在站立的地方变成英雄的坐标
            gameObjects[hero.z][hero.y][hero.x].type="hero";
            //英雄原本站立的地方变成空地
            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
            hero.fly = true;
        }
        else if (gameObjects[hero.z][hero.y][hero.x].type.equals("hook")){
            hero.hook = true;

            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
            flyLabel.remove(gameObjects[hero.z][hero.y][hero.x].simulateGameObjectLabel);

            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);

            gameObjects[hero.z][hero.y][hero.x].type="hero";

            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
        }

        else if (Flyfeather.class.isAssignableFrom(gameObjects[hero.z][hero.y][hero.x].getClass())){
            Flyfeather flyfeather = (Flyfeather) gameObjects[hero.z][hero.y][hero.x];
            hero.level = hero.level + flyfeather.level;

            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
            flyLabel.remove(gameObjects[hero.z][hero.y][hero.x].simulateGameObjectLabel);

            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);

            gameObjects[hero.z][hero.y][hero.x].type="hero";

            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";

        }
        else if (gameObjects[hero.z][hero.y][hero.x].type.equals("holywater")){
            hero.holywater = true;

            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
            flyLabel.remove(gameObjects[hero.z][hero.y][hero.x].simulateGameObjectLabel);

            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);

            gameObjects[hero.z][hero.y][hero.x].type="hero";

            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
        }
        else if (gameObjects[hero.z][hero.y][hero.x].type.equals("cross")){
            hero.cross = true;

            bottom.remove(gameObjects[hero.z][hero.y][hero.x].gameObjectLabel);
            flyLabel.remove(gameObjects[hero.z][hero.y][hero.x].simulateGameObjectLabel);

            hero.gameObjectLabel.setLocation(hero.gameObjectLabel.getLocation().x-34*LeftORRight,hero.gameObjectLabel.getLocation().y-34*UpORDown);

            gameObjects[hero.z][hero.y][hero.x].type="hero";

            gameObjects[hero.z][hero.y+LeftORRight][hero.x+UpORDown].type="blank";
        }

        else if (gameObjects[hero.z][hero.y][hero.x].type.equals("celestial")){
            System.out.println("仙子的对话");
            gamePanel.setLayout(null);
            dialogLabel.setVisible(true);
            dialogLabel.setOpaque(true);
            dialogLabel.setLocation(350,200);
            dialogLabel.setBackground(Color.WHITE);
            dialogLabel.setSize(300,50);
            if (hero.cross==false){
                dialogLabel.setText("勇者，去吧！去追寻你的梦想吧！");
                hero.x = hero.x+UpORDown;
                hero.y = hero.y+LeftORRight;
            }else {
                dialogLabel.setText("哇！十字架！现在就给你强化！");
                hero.life = hero.life*4/3;
                hero.attack = hero.attack*4/3;
                hero.defence = hero.defence*4/3;
                hero.x = hero.x+UpORDown;
                hero.y = hero.y+LeftORRight;
                gameObjects[20][5][6].type = "upstair";
                addNewGameObject(20,5,6);
                bottom.setLayout(null);
                bottom.add(gameObjects[20][5][6].gameObjectLabel);
                flyLabel.setLayout(null);
                flyLabel.add(gameObjects[20][5][6].simulateGameObjectLabel);
                gameObjects[20][5][6].gameObjectLabel.setVisible(true);
                gameObjects[20][5][6].simulateGameObjectLabel.setVisible(true);


                hero.cross = false;

            }



        }

        else if (gameObjects[hero.z][hero.y][hero.x].type.equals("thief")){
            System.out.println("小偷的对话");
            gamePanel.setLayout(null);
            dialogLabel.setVisible(true);
            dialogLabel.setOpaque(true);
            dialogLabel.setLocation(350,200);
            dialogLabel.setBackground(Color.WHITE);
            dialogLabel.setSize(300,50);
            if(hero.hook==false){
                if (gameObjects[2][1][6].type.equals("blank")){
                    dialogLabel.setText("星光圣镰没带来吗？");
                }else {
                    dialogLabel.setText("你救了我！我这就把2楼打通！");
                    gameObjects[2][1][6].type = "blank";
                    bottom.remove(gameObjects[2][1][6].gameObjectLabel);
                    flyLabel.remove(gameObjects[hero.z][hero.y][hero.x].simulateGameObjectLabel);
                    gameObjects[2][1][6].gameObjectLabel.setVisible(false);
                }
                hero.x = hero.x+UpORDown;
                hero.y = hero.y+LeftORRight;
            }else {
                //todo
                //设置对话，"太好了，我这就给你把公主那一层打通
                dialogLabel.setText("太好了，我这就去把公主那边打通");
                //然后把公主那一层的地方都改成blank
                gameObjects[18][5][8].type = "blank";
                bottom.remove(gameObjects[18][5][8].gameObjectLabel);
                flyLabel.remove(gameObjects[18][5][8].simulateGameObjectLabel);
                gameObjects[18][5][8].gameObjectLabel.setVisible(false);
                gameObjects[18][5][9].type = "blank";
                bottom.remove(gameObjects[18][5][9].gameObjectLabel);
                flyLabel.remove(gameObjects[18][5][9].simulateGameObjectLabel);
                gameObjects[18][5][9].gameObjectLabel.setVisible(false);
                //同时小偷消失
                gameObjects[4][5][0].type = "blank";
                bottom.remove(gameObjects[4][5][0].gameObjectLabel);
                flyLabel.remove(gameObjects[4][5][0].simulateGameObjectLabel);
                gameObjects[4][5][0].gameObjectLabel.setVisible(false);

                hero.x = hero.x+UpORDown;
                hero.y = hero.y+LeftORRight;

            }

        }



        else{
            hero.x = hero.x+UpORDown;
            hero.y = hero.y+LeftORRight;
        }





    }

    private void monsterMutate() {
        //怪物变异的方式
        if (floorNum==17){
            for(int i=0;i<22;i++){//楼层
                for(int j=0;j<11;j++){//j==y
                    for(int k=0;k<11;k++){//i==x
                        if(gameObjects[i][j][k]!=null){
                            if (Reddevilking.class.isAssignableFrom(gameObjects[i][j][k].getClass())){
                                Reddevilking reddevilking = (Reddevilking) gameObjects[i][j][k];
                                reddevilking.life = 20000;
                                reddevilking.attack = 1333;
                                reddevilking.defence = 1333;
                                reddevilking.coin = 100;
                                reddevilking.experience = 100;

                            }
                            else if (Soulwitch.class.isAssignableFrom(gameObjects[i][j][k].getClass())){
                                Soulwitch soulwitch = (Soulwitch) gameObjects[i][j][k];
                                soulwitch.life = 2000;
                                soulwitch.attack = 1106;
                                soulwitch.defence = 730;
                                soulwitch.coin = 106;
                                soulwitch.experience = 93;
                            }
                            else if (Soulsoldier.class.isAssignableFrom(gameObjects[i][j][k].getClass())){
                                Soulsoldier soulsoldier = (Soulsoldier) gameObjects[i][j][k];
                                soulsoldier.life = 1800;
                                soulsoldier.attack = 1306;
                                soulsoldier.defence = 1200;
                                soulsoldier.coin = 117;
                                soulsoldier.experience = 100;
                            }
                        }
                    }
                }
            }
        }
        else if (floorNum==21){
            for(int i=0;i<22;i++){//楼层
                for(int j=0;j<11;j++){//j==y
                    for(int k=0;k<11;k++){//i==x
                        if(gameObjects[i][j][k]!=null){
                            if (Devilking.class.isAssignableFrom(gameObjects[i][j][k].getClass())){
                                Devilking devilking = (Devilking) gameObjects[i][j][k];
                                devilking.life = 45000;
                                devilking.attack = 2550;
                                devilking.defence = 2250;
                                devilking.coin = 312;
                                devilking.experience = 275;
                            }
                        }
                    }
                }
            }
        }



    }

    public void heroGoUp(KeyEvent e) {
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
            if (hero.getClass().getDeclaredFields()[i].getType().isAssignableFrom(int.class)){
                try{
                    attributeLabels[i].setText(""+hero.getClass().getDeclaredFields()[i].getInt(hero));
                }catch (Exception e){
                    e.printStackTrace();
                }
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
