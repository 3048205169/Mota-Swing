package gamePanel;
import Floor.Floor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameUI{
         List<Floor> floors;
    public GameUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame gameFrame = new JFrame();
        gameFrame.setVisible(true);
        gameFrame.setSize(800,600);
        JPanel gamePanel = new JPanel();
        gamePanel.setLocation(0,0);
        gamePanel.setSize(800,600);
        floors = setFloor();                        
        Floor.showFloor(floors.get(0));
        prepareBG(gamePanel);

        gameFrame.setContentPane(gamePanel);



    }

    private void setInitData(JLabel bgLabel) {
        //现在先放置一些假的数据，到时候再使用勇者的数据放进去
        setLevel(bgLabel);
        setLife(bgLabel);
        setAttack(bgLabel);
        setDefence(bgLabel);
        setCoin(bgLabel);
        setExperience(bgLabel);


    }

    private void setExperience(JLabel bgLabel) {
        JLabel attackLabel = new JLabel();
        attackLabel.setLocation(100,35);
        attackLabel.setSize(200,300);
        attackLabel.setVisible(true);
        attackLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        attackLabel.setForeground(Color.WHITE);
        attackLabel.setText("20000");
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
        attackLabel.setText("1000");
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
        attackLabel.setText("200");
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
        attackLabel.setText("20");
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
        attackLabel.setText("10");
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
        levelLabel.setText("3");
        levelLabel.setVisible(true);
        bgLabel.add(levelLabel);
    }

    private ArrayList<Floor> setFloor() {
        ArrayList<Floor>resultFloor = new ArrayList<Floor>();
        Floor floor1 = new Floor();
        floor1.floorNum = 1;
        resultFloor.add(floor1);

        return resultFloor;
    }

    private void prepareBG(JPanel gamePanel) {
        //设置背景图片，但是不设置人物等等
        //这个设置为了各个楼层都可以使用的
        JLabel bgLabel = new JLabel();
        bgLabel.setLocation(0,0);
        ImageIcon bgIcon = new ImageIcon("src/imageResource/GameBg.png");
        bgIcon.setImage(bgIcon.getImage().getScaledInstance(600,400,1));
        bgLabel.setIcon(bgIcon);
        bgLabel.setSize(500,500);
        bgLabel.setBackground(Color.RED);
        setInitData(bgLabel);
        gamePanel.add(bgLabel);
    }

//    public static void main(String[] args) {
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                createAndShowGUI();
//            }
//        });
//    }


}
