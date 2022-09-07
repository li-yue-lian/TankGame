package li.TankGame.version04;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {

    //在敌人坦克类使用Vector保存多个Shot
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true;

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {
        while (true) {
            //根据坦克的方法来继续移动
            switch (getDirect()) {
                case 0://上
                    //让坦克保持一个方向走30步
                    for (int i = 0; i < 100; i++) {
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
                    //让坦克保持一个方向走30步
                    for (int i = 0; i < 100; i++) {
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
                    for (int i = 0; i < 100; i++) {
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
                    for (int i = 0; i < 100; i++) {
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
