package cn.onearth.test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * Created by wliu on 2017/11/29 0029.
 */
public class ForkJoinTest {


    public static void main(String[] args) throws Exception {

        ForkJoinPool forkJoinPool = new ForkJoinPool();// 默认工作线程的个数为cpu的个数

        MyTask task = new MyTask(1, 216);

        forkJoinPool.submit(task);

        do {
            System.out.println("+++++++++++++");
            Thread.sleep(100);

        }while (!task.isDone());

        forkJoinPool.shutdown();


    }
}
