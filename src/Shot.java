package li.TankGame.version07;

/**
 * @author 李
 * @version 7.0
 * 射击子弹
 */
public class Shot implements Runnable {
    int x; // 记录子弹 x 坐标
    int y; // 记录子弹 y 坐标
    int direct = 0; // 子弹方向
    int speed = 2; // 子弹速度
    boolean isLive = true;//记录子弹（线程）存活状态

    //构造器
    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {//射击
        while (true) {
            //休眠50 毫秒
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据方向来改变(x,y)坐标
            switch (direct) {
                case 0://上
                    y -= speed;
                    break;
                case 1://右
                    x += speed;
                    break;
                case 2://下
                    y += speed;
                    break;
                case 3://左
                    x -= speed;
                    break;
            }
            //测试。输出坐标
            //System.out.println("x=" + x + "y=" + y);
            //如果子弹移动到面板边界时，就应该销毁（把启动的子弹的线程销毁）
            //当子弹碰到敌人坦克时，也应该结束线程
            if (!(x >= 0 && x <= 700 && y <= 550 && y >= 0 && isLive)) {//如果不符合条件
                System.out.println("子弹线程退出...");
                isLive = false;
                break;//线程退出
            }
        }
    }
}
