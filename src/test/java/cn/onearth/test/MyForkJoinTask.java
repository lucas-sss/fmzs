package cn.onearth.test;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * Created by wliu on 2017/11/29 0029.
 */
public class MyForkJoinTask  extends RecursiveAction {
    private final int spilSize = 2;
    private int start,end;
    public MyForkJoinTask (int start,int end)
    {
        this.start=start;
        this.end=end;
    }
    @Override
    protected void compute() {
        // TODO Auto-generated method stub
        int sum = 0;
        if((end-start)<2)
        {
            for(int i=start;i<end;i++)
            {
                sum+=i;
            }
            System.out.println(start + "----------" + end);
        }
        else
        {
            int middle = (start+end)/2;
            MyForkJoinTask firstTask = new MyForkJoinTask(start,middle);
            MyForkJoinTask secondTask = new MyForkJoinTask(middle+1,end);
            firstTask.fork();   //提交任务
            secondTask.fork();  //
//            Integer firstResult = firstTask.join();   //阻塞线程等待任务结果
//            Integer secondResult = secondTask.join();
//            sum = firstResult.intValue()+secondResult.intValue();
            System.out.println("middle:" + middle);
        }
//        return sum;
    }
}
