package Matter;

import javax.swing.*;

public class WallCell {
    public int x;//行
    public int y;//列
    public int z;//楼层数
    public JLabel wcLabel;


    public WallCell() {


    }

    public WallCell(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public WallCell(int x, int y, int z, JLabel wcLabel) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.wcLabel = wcLabel;
    }
}
