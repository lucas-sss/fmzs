package cn.onearth.fmzs.service.core.impl;

import cn.onearth.fmzs.model.business.SectionContextDO;
import cn.onearth.fmzs.model.business.SectionsDrawerDO;
import cn.onearth.fmzs.model.pojo.TracerTask;
import cn.onearth.fmzs.service.core.CoreService;
import cn.onearth.fmzs.service.taskjob.CollectSectionTask;
import cn.onearth.fmzs.spider.service.impl.N31xsTracerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;

/**
 *
 *
 * Created by wliu on 2017/11/28 0028.
 */
@Service(value = "coreService")
public class CoreServiceImpl implements CoreService {


    @Override
    public boolean addTrackTask(TracerTask tracerTask) {
        return false;
    }

    /**
     * 使用fockjoin进行批量抓取
     *
     * @param sectionsDrawer
     * @return
     */
    @Override
    public List<SectionContextDO> collectNewBook(SectionsDrawerDO sectionsDrawer){

        TreeSet<SectionContextDO> sections = sectionsDrawer.getSections();

        List<SectionContextDO> list = new ArrayList<>(sections.size());
        for (SectionContextDO sectionContextDO : sections) {
            list.add(sectionContextDO);
        }

        /*for (SectionContextDO sectionContextDO : list) {
            n31xsTracerService.pullSectionContext(sectionContextDO);
        }
        return list;*/
        CollectSectionTask task = new CollectSectionTask(list, null);

        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(task);
        pool.shutdown();

        do {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }while (!task.isDone());

        return list;
    }

}
