package li.TankGame.version01;

import javax.swing.*;

/**
 * @author 李
 * @version 1.0
 */
public class TankGame01 extends JFrame {
    //定义一个MyPanel
    MyPanel mp = null;
    public static void main(String[] args) {
        TankGame01 tankGame01 = new TankGame01();
    }

    public TankGame01(){
        mp = new MyPanel();
        this.add(mp);//把面板（就是游戏的绘图区域）添加进来
        this.setSize(700,550);//设置大小
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//点击窗口的叉时停止运行
        this.setVisible(true);//设置显示
    }
}
