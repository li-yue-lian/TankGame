package li.TankGame.version06;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @author 李
 * @version 6.0
 */
public class TankGame06 extends JFrame {
    //定义一个MyPanel
    MyPanel mp = null;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        TankGame06 tankGame06 = new TankGame06();
    }

    public TankGame06() {
        System.out.println("请输入选择：\n" + "1：新游戏 2：继续上局");
        String key = scanner.next();
        mp = new MyPanel(key);
        //将mp放入到Thread，并启动
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//把面板（就是游戏的绘图区域）添加进来
        this.setSize(950, 600);//设置大小
        this.addKeyListener(mp);//让JFrame监听mp的键盘事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击窗口的叉时停止运行
        this.setVisible(true);//设置显示

        //在JFrame中增加相应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });

    }
}
