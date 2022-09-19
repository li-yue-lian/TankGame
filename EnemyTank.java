package li.TankGame.version06;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {

    //在敌人坦克类使用Vector保存多个shot
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true;
    //增加一个成员，EnemyTank 可以得到敌人坦克成员的Vector，用于循环比较是否重叠
    Vector<EnemyTank> enemyTanks = new Vector<>();

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    //这里提供一个方法，可以将MyPanel的成员 Vector<EnemyTank> enemyTanks = new Vector<>();
    // 设置到Enemy 的成员enemyTank
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    /**
     * 编写方法，判断当前敌人坦克是否和enemyTanks中的其他坦克发生了重叠或碰撞
     * 思路：读取一个坦克的坐标，在前进之前先将该坦克坐标与当前所有坦克的坐标依次比较，
     * 如果到达某个坦克的边缘则不能再前进，则更改方向前进
     */
    public boolean isTouchEnemyTank() {
        //判断当前敌人坦克（this）方向
        switch (this.getDirect()) {
            case 0://向上
                //让当前的this敌人坦克和其他所有的敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //Vector 中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果敌人坦克是上/下方向
                        /**
                         * 情况1.this坦克向上，如果敌人的坦克是上/下方向
                         * 那么敌人坦克x的范围是[enemyTank.getX(),enemyTank.getX()+40]
                         * 敌人坦克y的范围是[enemyTank.getY(),enemyTank.getY()+60]
                         * */
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //this坦克的左上角的坐标(this.getX(),this.getY())
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;//如果进入左上角了该范围，说明发生了碰撞，返回true
                            }
                            //this坦克的右上角的坐标(this.getX()+40,this.getY())
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;//如果右上角进入了该范围，说明发生了碰撞，返回true
                            }

                        }

                        //如果敌人坦克是右/左方向
                        /**
                         * 情况2.this坦克向上，如果敌人的坦克是右/左方向
                         * 那么敌人坦克x的范围是[enemyTank.getX(),enemyTank.getX()+60]
                         * 敌人坦克y的范围是[enemyTank.getY(),enemyTank.getY()+40]
                         * */
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //this坦克的左上角的坐标(this.getX(),this.getY())
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;//如果进入左上角了该范围，说明发生了碰撞，返回true
                            }

                            //this坦克的右上角的坐标(this.getX()+40,this.getY())
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;//如果右上角进入了该范围，说明发生了碰撞，返回true
                            }

                        }
                    }
                }
                break;
            case 1://向右
                //让当前的this敌人坦克和其他所有的敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //Vector 中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果敌人坦克是上/下方向
                        /**
                         * 情况3.this坦克向右，如果敌人的坦克是上/下方向
                         * 那么敌人坦克x的范围是[enemyTank.getX(),enemyTank.getX()+40]
                         * 敌人坦克y的范围是[enemyTank.getY(),enemyTank.getY()+60]
                         * */
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //this坦克的右上角的坐标(this.getX()+60,this.getY())
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;//如果进入右上角了该范围，说明发生了碰撞，返回true
                            }
                            //this坦克的右下角的坐标(this.getX()+60,this.getY()+40)
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;//如果右下角进入了该范围，说明发生了碰撞，返回true
                            }

                        }

                        //如果敌人坦克是右/左方向
                        /**
                         * 情况4.this坦克向右，如果敌人的坦克是右/左方向
                         * 那么敌人坦克x的范围是[enemyTank.getX(),enemyTank.getX()+60]
                         * 敌人坦克y的范围是[enemyTank.getY(),enemyTank.getY()+40]
                         * */
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //this坦克的右上角的坐标(this.getX()+60,this.getY())
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;//如果进入右上角了该范围，说明发生了碰撞，返回true
                            }

                            //this坦克的右下角的坐标(this.getX()+60,this.getY()+40)
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;//如果右下角进入了该范围，说明发生了碰撞，返回true
                            }
                        }
                    }
                }
                break;
            case 2://向下
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //Vector 中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果敌人坦克是上/下方向
                        /**
                         * 情况5.this坦克向下，如果敌人的坦克是上/下方向
                         * 那么敌人坦克x的范围是[enemyTank.getX(),enemyTank.getX()+40]
                         *    敌人坦克y的范围是[enemyTank.getY(),enemyTank.getY()+60]
                         * */
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //this坦克的左下角的坐标(this.getX(),this.getY()+60)
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;//如果进入左下角了该范围，说明发生了碰撞，返回true
                            }
                            //this坦克的右下角的坐标(this.getX()+40,this.getY()+60)
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;//如果右下角进入了该范围，说明发生了碰撞，返回true
                            }

                        }

                        //如果敌人坦克是右/左方向
                        /**
                         * 情况6.this坦克向下，如果敌人的坦克是右/左方向
                         * 那么敌人坦克x的范围是[enemyTank.getX(),enemyTank.getX()+60]
                         * 敌人坦克y的范围是[enemyTank.getY(),enemyTank.getY()+40]
                         * */
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //this坦克的左下角的坐标(this.getX(),this.getY()+60)
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;//如果进入左下角了该范围，说明发生了碰撞，返回true
                            }

                            //this坦克的右下角的坐标(this.getX()+40,this.getY()+60)
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;//如果右下角进入了该范围，说明发生了碰撞，返回true
                            }
                        }
                    }
                }
                break;
            case 3://向左
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //Vector 中取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this) {
                        //如果敌人坦克是上/下方向
                        /**
                         * 情况7.this坦克向左，如果敌人的坦克是上/下方向
                         * 那么敌人坦克x的范围是[enemyTank.getX(),enemyTank.getX()+40]
                         *    敌人坦克y的范围是[enemyTank.getY(),enemyTank.getY()+60]
                         * */
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //this坦克的左上角的坐标(this.getX(),this.getY())
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;//如果进入左上角了该范围，说明发生了碰撞，返回true
                            }
                            //this坦克的左下角的坐标(this.getX(),this.getY()+40)
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;//如果左下角进入了该范围，说明发生了碰撞，返回true
                            }

                        }

                        //如果敌人坦克是右/左方向
                        /**
                         * 情况8.this坦克向左，如果敌人的坦克是右/左方向
                         * 那么敌人坦克x的范围是[enemyTank.getX(),enemyTank.getX()+60]
                         * 敌人坦克y的范围是[enemyTank.getY(),enemyTank.getY()+40]
                         * */
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //this坦克的左上角的坐标(this.getX(),this.getY())
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;//如果进入左下角了该范围，说明发生了碰撞，返回true
                            }

                            //this坦克的左下角的坐标(this.getX(),this.getY()+40)
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;//如果右下角进入了该范围，说明发生了碰撞，返回true
                            }
                        }
                    }
                }
                break;
        }
        return false;
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
                        if (getY() > 0 && !isTouchEnemyTank()) {
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
                        if (getX() + 60 < 700 && !isTouchEnemyTank()) {//700为面板宽度
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
                        if (getY() + 60 < 550 && !isTouchEnemyTank()) {//550为面板宽度
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
                        if (getX() > 0 && !isTouchEnemyTank()) {
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
