package li.TankGame.version07;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
 * @author 李
 * @version 7.0
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

    //定义一个存放Node对象的Vector，用于恢复敌人坦克的坐标和方向
    Vector<Node> nodes = new Vector<>();

    //定义一个Vector，用于存放炸弹
    //说明：当子弹击中坦克时就加入一个Bomb对象到bombs集合里
    Vector<Bomb> bombs = new Vector<>();

    int enemyTankNum = 3;//初始化坦克数量

    //定义三张炸弹图片，用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public MyPanel(String key) {
        //先判断记录文件是否存在，如果存在就正常执行，若不存在，就提示只能开新新游戏，将 key 置为 1
        File file = new File(Recorder.getRecordFile());
        if (file.exists()) {
            nodes = Recorder.getNodesAndEnemyTankRec();
        } else {
            System.out.println("没有存档记录，只能开启新游戏！");
            key = "1";
        }

        //将MyPanel对象的enemyTanks 设置给Recorder的enemyTanks
        Recorder.setEnemyTanks(enemyTanks);

        hero = new Hero(400, 200);//初始化自己的坦克
        //hero.setSpeed(5); //改变坦克的速度

        switch (key) {
            case "1":  //开新游戏
                Recorder.setAllEnemyTankNum(0);//重设击毁敌方坦克数目
                //初始化敌人的坦克
                for (int i = 0; i < enemyTankNum; i++) {
                    //创建一个敌人的坦克
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    //将enemyTanks集合设置给 enemyTank
                    enemyTank.setEnemyTanks(enemyTanks);
                    //初始化敌人坦克方向向下
                    enemyTank.setDirect(2);
                    //启动敌人坦克线程,让他动起来
                    new Thread(enemyTank).start();
                    //给该enemyTank加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //将该子弹加入到enemyTank的Vector集合中
                    enemyTank.shots.add(shot);
                    //启动 shot对象
                    new Thread(shot).start();
                    //将设置好的的敌人坦克放入到集合中
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2": //继续上局游戏
                //初始化敌人的坦克
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    //创建一个敌人的坦克
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    //将enemyTanks集合设置给 enemyTank
                    enemyTank.setEnemyTanks(enemyTanks);
                    //初始化敌人坦克方向向下
                    enemyTank.setDirect(node.getDirect());
                    //启动敌人坦克线程,让他动起来
                    new Thread(enemyTank).start();
                    //给该enemyTank加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //将该子弹加入到enemyTank的Vector集合中
                    enemyTank.shots.add(shot);
                    //启动 shot对象
                    new Thread(shot).start();
                    //将设置好的的敌人坦克放入到集合中
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("输入有误");
        }

        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb1.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb2.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb3.png"));

        //这里播放指定的音乐
        new AePlayWave("src\\111.wav").start();
    }

    /**
     * 编写方法，显示我方击毁敌方坦克的信息
     */
    public void showInfo(Graphics g) {
        //画出 玩家的总成绩
        g.setColor(Color.BLACK);//设置画笔颜色
        Font font = new Font("宋体", Font.BOLD, 20);
        g.setFont(font);
        g.drawString("您累计击毁敌方坦克", 730, 30);//画出“您累计击毁敌方坦克”的字样
        drawTank(760, 55, g, 0, 0);//画一个敌方坦克图案
        g.setColor(Color.BLACK);//重新设置画笔颜色
        g.drawString(Recorder.getAllEnemyTankNum() + "", 850, 95);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 700, 550);//填充矩形，默认为黑色
        showInfo(g);

        //画出自己的坦克-封装方法
        if (hero != null && hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }

        //画出hero发射的子弹
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isLive) { //如果子弹对象不为空，并且处于存活状态（isLive=true）
                System.out.println("hero的子弹被绘制");
                g.draw3DRect(hero.shots.get(i).x, hero.shots.get(i).y, 2, 2, false);
            } else {//如果该shot对象已经无效，就从shot集合中删除
                hero.shots.remove(shot);
            }
        }

        //如果bombs集合中有炸弹对象，就画出来
        for (int i = 0; i < bombs.size(); i++) {
            try {
                Thread.sleep(50);//先休眠50毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据当前bomb对象的life的只去画出对应的图片
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            //让炸弹的生命值减少
            bomb.lifeDown();
            //如果bomb.life==0,就从bombs集合中删除这个对象
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }

        }

        //画出敌人的坦克,遍历Vector
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive) {//当敌人坦克是活的，才将其画出
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                //画出enemyTank所有子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //enemyTank.shots.size()是当前的enemy对象的shots集合的大小
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //绘制子弹
                    if (shot.isLive) {//shot.isLive==true
                        g.draw3DRect(shot.x, shot.y, 2, 2, false);
                    } else {
                        //从Vector移除子弹shot对象
                        enemyTank.shots.remove(shot);
                    }
                }
            }
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

    public void hitEnemyTank() {
        //判断是否击中敌人坦克
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);//shot为当前的子弹对象
            if (shot != null && shot.isLive) {//如果当前我的子弹不为空并且子弹还存活
                //就遍历敌人所有的坦克
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }

    public void hitHeroTank() {
        //遍历敌人所有的坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出当前的敌人坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //取出当enemyTank所有子弹
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                //取出一颗子弹
                Shot shot = enemyTank.shots.get(j);
                //判断该子弹是否击中我们的坦克
                if (hero.isLive && shot.isLive) {
                    hitTank(shot, hero);
                }
            }
        }
    }

    /*判断子弹是否击中坦克*/
    public void hitTank(Shot s, Tank enemyTank) {
        //判断s击中坦克
        switch (enemyTank.getDirect()) {
            case 0://敌人坦克向上（向上和向下的击中范围一样）
            case 2://敌人坦克向下
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 40
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //当子弹击中敌人后，将enemyTank从Vector中删除
                    enemyTanks.remove(enemyTank);
                    //当我方击毁一个敌人坦克的时候，就allEnemyTankNum++
                    //因为这里的enemyTank类型是Tank，所以要先进行判断击毁的是否是EnemyTank类型的
                    if (enemyTank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }
                    //创建一个Bomb对象，加入到bombs集合中
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1://右
            case 3://左
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 60
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 40) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //当子弹击中敌人后，将enemyTank从Vector中删除
                    enemyTanks.remove(enemyTank);
                    //当我方击毁一个敌人坦克的时候，就allEnemyTankNum++
                    //因为这里的enemyTank类型是Tank，所以要先进行判断击毁的是否是EnemyTank类型的
                    if (enemyTank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }
                    //创建一个Bomb对象，加入到bombs集合中
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
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
            if (hero.getY() > 0) {//向上
                hero.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {//按下D键-向右
            hero.setDirect(1);
            if (hero.getX() + 60 < 750) {//向右
                hero.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {//按下S键-向下
            hero.setDirect(2);
            if (hero.getY() + 60 < 550) {//向下
                hero.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//按下A键-向左
            hero.setDirect(3);
            if (hero.getX() > 0) {//向左
                hero.moveLeft();
            }
        }
        //如果用户按下j键，hero就发射子弹
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

            hitEnemyTank();
            hitHeroTank();

            this.repaint();
        }
    }
}
