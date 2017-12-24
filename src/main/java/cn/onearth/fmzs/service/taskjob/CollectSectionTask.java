package cn.onearth.fmzs.service.taskjob;

import cn.onearth.fmzs.model.business.SectionContextDO;
import cn.onearth.fmzs.spider.service.BasicTracer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * 爬取所有的章节
 *
 * Created by wliu on 2017/11/29 0029.
 */
public class CollectSectionTask extends RecursiveAction {

    //待爬取的章节
    private List<SectionContextDO> list;
    //爬虫处理器
    private BasicTracer tracer;

    public CollectSectionTask(List<SectionContextDO> list, BasicTracer tracer) {
        this.list = list;
        this.tracer = tracer;
    }

    @Override
    protected void compute() {

        int size = list.size();

        if (size > 2) {

            List<SectionContextDO> leftList = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                leftList.add(list.get(i));
            }
            List<SectionContextDO> rightList = new ArrayList<>();
            for (int i = 2; i < size; i++) {
                rightList.add(list.get(i));
            }
            CollectSectionTask leftTask = new CollectSectionTask(leftList, this.tracer);
            CollectSectionTask rightTask = new CollectSectionTask(rightList, this.tracer);

            invokeAll(leftTask, rightTask);
//            leftTask.fork();
//            rightTask.fork();
        } else {
            //直接进行爬取
            for (SectionContextDO section : list) {
                tracer.pullSectionContext(section);
            }
        }
    }

}
