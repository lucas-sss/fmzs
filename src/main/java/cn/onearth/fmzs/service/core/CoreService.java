package cn.onearth.fmzs.service.core;

import cn.onearth.fmzs.model.business.SectionContextDO;
import cn.onearth.fmzs.model.business.SectionsDrawerDO;
import cn.onearth.fmzs.model.pojo.TracerTask;

import java.util.List;

/**
 * Created by wliu on 2017/11/28 0028.
 */
public interface CoreService {

    /**
     * 添加追书任务
     *
     * @param tracerTask
     * @return
     */
    boolean addTrackTask(TracerTask tracerTask);

    /**
     * 采集新书的所有章节，并同步到本地数据库
     *
     * @param sectionsDrawer
     */
    List<SectionContextDO> collectNewBook(SectionsDrawerDO sectionsDrawer);
}
