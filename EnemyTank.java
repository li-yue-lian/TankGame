package li.TankGame.version05;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {

    //在敌人坦克类使用Vector保存多个shot
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true;

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {
        while (true) {

            //这我们先判断当前的坦克是否存活
            // 在判断shots.size<3是否真,为真，说明当前的3颗子弹已经消亡了,
            // 就创建一颗子弹，放到shots集合中，并启动线程
            if (isLive && (shots.size() < 3)) {//可以通过控制数字来修改敌人坦克一次发射几颗子弹
                Shot s = null;
                //判断坦克的方创建对应的子弹
                switch (getDirect()) {
                    case 0://向上
                        s = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1://向右
                        s = new Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2://向下
                        s = new Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3://向左
                        s = new Shot(getX(), getY() + 20, 3);
                        break;
                }
                shots.add(s);
                new Thread(s).start();
            }
            //根据坦克的方法来继续移动
            switch (getDirect()) {
                case 0://上
                    //让坦克保持一个方向走50步
                    for (int i = 0; i < 50; i++) {
                        if (getY() > 0) {
                            moveUp();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1://右
                    //让坦克保持一个方向走50步
                    for (int i = 0; i < 50; i++) {
                        if (getX() + 60 < 700) {//700为面板宽度
                            moveRight();//走一步
                        }
                        try {
                            Thread.sleep(50);//每走一步就休眠50毫秒
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2://下
                    for (int i = 0; i < 50; i++) {
                        if (getY() + 60 < 550) {//550为面板宽度
                            moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3://左
                    for (int i = 0; i < 50; i++) {
                        if (getX() > 0) {
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
            //随机地改变坦克的方向 0-3
            setDirect((int) (Math.random() * 4));//[0,4)的取整
            //如果被击中了，就退出线程
            if (!isLive) {
                break;//退出线程
            }
        }
    }
}
