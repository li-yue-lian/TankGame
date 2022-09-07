package li.TankGame.version03;

import javax.swing.*;

/**
 * @author 李
 * @version 3.0
 */
public class TankGame03 extends JFrame {
    //定义一个MyPanel
    MyPanel mp = null;
    public static void main(String[] args) {
        TankGame03 tankGame03 = new TankGame03();
    }

    public TankGame03(){
        mp = new MyPanel();
        //将mp放入到Thread，并启动
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//把面板（就是游戏的绘图区域）添加进来
        this.setSize(700,550);//设置大小
        this.addKeyListener(mp);//让JFrame监听mp的键盘事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击窗口的叉时停止运行
        this.setVisible(true);//设置显示
    }
}
