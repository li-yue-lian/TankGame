package li.TankGame.version02;

import javax.swing.*;

/**
 * @author 李
 * @version 2.0
 */
public class TankGame02 extends JFrame {
    //定义一个MyPanel
    MyPanel mp = null;
    public static void main(String[] args) {
        TankGame02 tankGame02 = new TankGame02();
    }

    public TankGame02(){
        mp = new MyPanel();
        this.add(mp);//把面板（就是游戏的绘图区域）添加进来
        this.setSize(700,550);//设置大小
        this.addKeyListener(mp);//让JFrame监听mp的键盘事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击窗口的叉时停止运行
        this.setVisible(true);//设置显示
    }
}
