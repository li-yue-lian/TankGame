package li.TankGame.version01;

import javax.swing.*;
import java.awt.*;

/**
 * @author 李
 * @version 1.0
 * 坦克大战的绘图区域
 */
public class MyPanel extends JPanel {
    //定义我的坦克
    Hero hero = null;

    public MyPanel() {
        hero = new Hero(100, 100);//初始化自己的坦克
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 700, 550);//填充矩形，默认为黑色

        //画出坦克-封装方法
        drawTank(hero.getX(),hero.getY(),g,0,0);

    }


    /** 编写方法，画出坦克
     * @param x      坦克的左上角横坐标
     * @param y      坦克的左上角纵坐标
     * @param g      画笔
     * @param direct 坦克方向（上下左右）
     * @param type   坦克的类型（我方，敌方）
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {

        //根据不同类型的坦克设置不同的颜色
        switch (type) {
            case 0://我方坦克
                g.setColor(Color.cyan);//设置我方坦克颜色
                break;
            case 1://敌方坦克
                g.setColor(Color.yellow);//设敌方坦克颜色
                break;
        }

        //根据坦克坐标方向，来绘制坦克
        switch (direct) {
            case 0:
                g.fill3DRect(x, y, 10, 60,false);//画出坦克左边的轮子
                g.fill3DRect(x+30, y, 10, 60,false);//画出坦克右边的轮子
                g.fill3DRect(x+10,y+10,20,40,false);//画出坦克主体
                g.fillOval(x+10,y+20,20,20);//画出坦克舱体
                g.drawLine(x+20,y-5,x+20,y+30);//画出炮管
                break;
            default:
                System.out.println("暂时没有处理");

        }


    }

}
