package cn.onearth.fmzs.service.core.impl;

import cn.onearth.fmzs.dao.BookMapper;
import cn.onearth.fmzs.dao.PersonMapper;
import cn.onearth.fmzs.dao.TracerGroupMapper;
import cn.onearth.fmzs.dao.TracerTaskMapper;
import cn.onearth.fmzs.model.business.SectionContextDO;
import cn.onearth.fmzs.model.business.SectionsDrawerDO;
import cn.onearth.fmzs.model.pojo.TracerGroup;
import cn.onearth.fmzs.model.pojo.TracerTask;
import cn.onearth.fmzs.service.core.CoreService;
import cn.onearth.fmzs.service.taskjob.CollectSectionTask;
import cn.onearth.fmzs.spider.service.impl.N31xsTracerService;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by wliu on 2017/11/28 0028.
 */
@Service(value = "coreService")
public class CoreServiceImpl implements CoreService {


    @Autowired
    private N31xsTracerService n31xsTracerService;


    @Override
    public boolean addTrackTask(TracerTask tracerTask) {
        return false;
    }

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
        CollectSectionTask task = new CollectSectionTask(list, n31xsTracerService);

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
