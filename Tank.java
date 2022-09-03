package li.TankGame.version02;

/**
 * @author 李
 * @version 2.0
 */
public class Tank {
    private int x;//坦克的横坐标
    private int y;//坦克的纵坐标
    private int direct;//坦克方向  0：向上  1：向右  2：向下  3：向左
    private int speed = 1;//坦克的速度,初始值为 1

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    //上下左右移动方法
    public void moveUp() {
        y -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void moveLeft() {
        x -= speed;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
