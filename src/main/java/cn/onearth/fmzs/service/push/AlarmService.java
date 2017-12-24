package cn.onearth.fmzs.service.push;

import cn.onearth.fmzs.model.business.PushBean;

/**
 * Created by wliu on 2017/11/28 0028.
 */
public interface AlarmService {

    boolean noticeUser(String[] phone, PushBean pushBean);

    boolean shareLink(PushBean pushBean);
}
