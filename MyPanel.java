package li.TankGame.version03;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * @author 李
 * @version 3.0
 * 坦克大战的绘图区域
 */
//为了监听键盘事件，要实现 KeyListener

//为了让Panel不停地重绘子弹，需要将MyPanel实现Runnable，
// 当做一个线程使用（因为需要不停地重绘子弹的图案）
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    Hero hero = null;

    //定义敌方坦克，放入到Vector集合中
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemyTankNum = 3;//初始化坦克数量

    public MyPanel() {
        hero = new Hero(100, 100);//初始化自己的坦克
        //hero.setSpeed(5); //改变坦克的速度

        //初始化敌人的坦克
        for (int i = 0; i < enemyTankNum; i++) {
            //创建一个敌人的坦克
            EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
            //初始化敌人坦克方向向下
            enemyTank.setDirect(2);
            //将设置好的的敌人坦克放入到集合中
            enemyTanks.add(enemyTank);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 700, 550);//填充矩形，默认为黑色

        //画出自己的坦克-封装方法
        drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);

        //画出hero发射的子弹
        //如果子弹对象不为空，并且处于存活状态（即没有超出边界）
        if (hero.shot != null && hero.shot.isLive) {
            System.out.println("子弹被绘制");
            g.draw3DRect(hero.shot.x, hero.shot.y, 2, 2, false);
        }

        //画出敌人的坦克,遍历Vector
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
        }
    }

    /**
     * 编写方法，画出坦克
     *
     * @param x      坦克的左上角横坐标
     * @param y      坦克的左上角纵坐标
     * @param g      画笔
     * @param direct 坦克方向（上下左右）
     * @param type   坦克的类型（我方，敌方）
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {

        //根据不同类型的坦克设置不同的颜色
        switch (type) {
            case 0://敌方坦克
                g.setColor(Color.cyan);//设置我方坦克颜色
                break;
            case 1://我方坦克
                g.setColor(Color.yellow);//设敌方坦克颜色
                break;
        }
        //根据坦克坐标方向，来绘制对应方向的坦克
        //direct表示方向（0：向上  1：向右  2：向下  3：向左）
        switch (direct) {
            case 0://表示向上
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克主体
                g.fillOval(x + 10, y + 20, 20, 20);//画出坦克舱体
                g.drawLine(x + 20, y + 30, x + 20, y);//画出炮管
                break;
            case 1://表示向右
                //注意：以坦克左上角的(x,y)坐标为参考画图案
                g.fill3DRect(x, y, 60, 10, false);//画出坦克上边的轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克下边的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克方形主体
                g.fillOval(x + 20, y + 10, 20, 20);//画出坦克舱体
                g.drawLine(x + 30, y + 20, x + 60, y + 20);//画出炮管
                break;
            case 2://表示向下
                //向下只需要将向上的炮筒的位置改变即可
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克主体
                g.fillOval(x + 10, y + 20, 20, 20);//画出坦克舱体
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//画出炮管
                break;
            case 3://表示向左
                //向左只需要将向右的炮筒的位置改变即可
                g.fill3DRect(x, y, 60, 10, false);//画出坦克上边的轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克下边的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克方形主体
                g.fillOval(x + 20, y + 10, 20, 20);//画出坦克舱体
                g.drawLine(x + 30, y + 20, x, y + 20);//画出炮管
                break;
            default:
                System.out.println("暂时没有处理");
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //控制方向--处理 WSAD 键按下的情况
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {//按下W键-向上
            //改变坦克的方向
            hero.setDirect(0);
            //修改坦克的坐标
            hero.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {//按下D键-向右
            hero.setDirect(1);
            hero.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {//按下S键-向下
            hero.setDirect(2);
            hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//按下A键-向左
            hero.setDirect(3);
            hero.moveLeft();
        }

        //如果用户按下j键，就发射子弹
        if (e.getKeyCode() == KeyEvent.VK_J) {
            hero.shotEnemyTank();
        }

        //让面板重绘
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {//每个100毫秒，重绘区域,刷新绘图区域，子弹就移动
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
        }
    }
}
