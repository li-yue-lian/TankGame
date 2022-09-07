package li.TankGame.version04;

/**
 * @author 李
 * @version 4.0
 */
public class Hero extends Tank {
    //定义一个shot对象，表示一个射击（线程）
    Shot shot = null;

    public Hero(int x, int y) {
        super(x, y);
    }

    //射击
    public void shotEnemyTank() {
        //创建Shot对象，根据当前Hero对象的坐标位置和方向来设置子弹的位置和方向
        switch (getDirect()) {//获取Hero对象的方向
            case 0://向上
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1://向右
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2://向下
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3://向左
                shot = new Shot(getX(), getY() + 20, 3);
                break;

        }
        //启动Shot线程
        new Thread(shot).start();
    }
}
