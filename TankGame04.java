package li.TankGame.version04;

import javax.swing.*;

/**
 * @author 李
 * @version 4.0
 */
public class TankGame04 extends JFrame {
    //定义一个MyPanel
    MyPanel mp = null;
    public static void main(String[] args) {
        TankGame04 tankGame04 = new TankGame04();
    }

    public TankGame04(){
        mp = new MyPanel();
        //将mp放入到Thread，并启动
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//把面板（就是游戏的绘图区域）添加进来
        this.setSize(750,600);//设置大小
        this.addKeyListener(mp);//让JFrame监听mp的键盘事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击窗口的叉时停止运行
        this.setVisible(true);//设置显示
    }
}
