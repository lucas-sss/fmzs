package cn.onearth.test;

import java.util.concurrent.RecursiveAction;

/**
 * Created by wliu on 2017/11/29 0029.
 */
public class MyTask extends RecursiveAction {

    private int count;

    private int i;

    public MyTask(int count, int i) {
        this.count = count;
        this.i = i;
    }

    @Override
    protected void compute() {

        if (i > 10) {
            count++;
            int num = i - 10;
            MyTask left = new MyTask(count, 10);
            MyTask rigth = new MyTask(count, num);
            System.out.println( "第" + count + "次-----num=" + num);
            invokeAll(left, rigth);
//            left.fork();
//            rigth.fork();

        }else {
            System.out.println("第" + count + "次-----i=" + i);
        }

    }
}
