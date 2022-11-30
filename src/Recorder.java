package li.TankGame.version07;

import java.io.*;
import java.util.Vector;

/**
 * @author 李
 * @version 7.0
 * 该类用于记录相关信息，和文件交互
 */
public class Recorder {
    //定义变量，记录我方击毁敌人坦克数
    private static int allEnemyTankNum = 0;

    //定义IO对象，用于写入/写出到文件中
    private static BufferedWriter bw = null;//输出处理流
    private static BufferedReader br = null;//输入处理流

    //把记录文件保存到 src 下
    //private static String recordFile = "d:\\myRecord.txt";//记录文件的路径
    private static String recordFile = "src\\myRecord.txt";//记录文件的路径

    //定义Vector，指向MyPanel对象的敌人坦克的Vector
    private static Vector<EnemyTank> enemyTanks = null;

    //定义一个Node的Vector对象，用于保存敌人的信息node
    private static Vector<Node> nodes = new Vector<>();

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    //返回记录文件的路径
    public static String getRecordFile() {
        return recordFile;
    }

    //增加一个方法，用于读取recordFile，恢复相关信息
    //该方法在点击继续上局的时候调用
    public static Vector<Node> getNodesAndEnemyTankRec() {
        try {
            br = new BufferedReader(new FileReader(recordFile));
            //读取上局击毁坦克数量
            allEnemyTankNum = Integer.parseInt(br.readLine());
            //循环读取文件，生成nodes集合
            String line = "";
            while ((line = br.readLine()) != null) {
                //用空格将每一行的数据分割，分割完的字符数组里面存储了一组敌方坦克的x,y,direct
                String[] xyd = line.split(" ");
                //将字符串转为int类型，赋值给node对象
                Node node = new Node(Integer.parseInt(xyd[0]),
                        Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                //将该node对象放到nodes集合中
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return nodes;
    }


    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    //当我方击毁一辆敌人坦克时，就应该allEnemyTankNum++
    public static void addAllEnemyTankNum() {
        Recorder.allEnemyTankNum++;
    }

    //增加一个方法，当游戏退出时，将allEnemyTankNum保存到myRecord.txt文件中
    //对keepRecord进行升级，保存敌人坦克的坐标和方向

    public static void keepRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "\n");
            //遍历敌人坦克的Vector，根据情况保存即可
            for (int i = 0; i < enemyTanks.size(); i++) {
                //取出敌人坦克
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive) {//虽然被击中的坦克对象已经被删除了，但是还是建议判断一下
                    //保存该enemyTank信息
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                    //写入到文件中
                    bw.write(record + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
