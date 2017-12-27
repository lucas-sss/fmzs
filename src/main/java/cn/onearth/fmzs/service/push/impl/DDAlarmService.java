package cn.onearth.fmzs.service.push.impl;

import cn.onearth.fmzs.model.business.PushBean;
import cn.onearth.fmzs.service.push.AlarmService;
import cn.onearth.fmzs.spider.processor.SpiderProcessor;
import cn.onearth.fmzs.spider.req.Method;
import cn.onearth.fmzs.spider.req.Request;
import cn.onearth.fmzs.spider.resp.Response;
import cn.onearth.fmzs.spider.service.AbstractBasicTracer;
import cn.onearth.fmzs.spider.service.BasicTracer;
import cn.onearth.fmzs.utils.common.ConstantCacheUtil;
import cn.onearth.fmzs.utils.common.ConstantParam;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.net.HttpHeaders;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by wliu on 2017/11/28 0028.
 */
@Service(value = "ddAlarmService")
public class DDAlarmService implements AlarmService {

    private static final String DING_TALK_URL = "https://oapi.dingtalk.com/robot/send?access_token=f8acc59ccda54294690f6752a1d6256d19c013212f52b13bc937e28849d30753";


    @Autowired
    private SpiderProcessor processor;

    @Override
    public boolean noticeUser(String[] phones, PushBean pushBean) {
        String shareUrl = ConstantCacheUtil.getValue(ConstantParam.DING_TALK_PREFIX) + pushBean.getTocken();
        Request request = new Request(shareUrl, Method.post);
        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        StringBuilder param = new StringBuilder();
        String[] pp = new String[phones.length];

        for (int i = 0; i < phones.length; i++) {
            pp[i] = "\"" + phones[i] + "\"";
        }

        param.append("{\"msgtype\":\"text\",\"text\":{\"content\":\"").append(pushBean.getText()).append("\"},\"at\":{\"atMobiles\":[").append(StringUtils.join(pp, ","))
                .append("],\"isAtAll\":").append(false).append("}}");

        request.setEntity(new StringEntity(param.toString(), ContentType.APPLICATION_JSON));
        Response response = processor.sendRequest(request, true);
        JSONObject result = JSON.parseObject(response.getContent());
        String errcode = result.getString("errcode");
        if ("0".equals(errcode)){
            return true;
        }
        return false;
    }

    @Override
    public boolean shareLink(PushBean pushBean) {
        String shareUrl = ConstantCacheUtil.getValue(ConstantParam.DING_TALK_PREFIX) + pushBean.getTocken();
        Request request = new Request(shareUrl, Method.post);
        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        StringBuilder param = new StringBuilder();
        param.append("{\"msgtype\":\"link\",\"link\":{\"text\":\"").append(pushBean.getText()).append("\",\"title\":\"").append(pushBean.getTitle())
                .append("\",\"picUrl\":\"").append(pushBean.getPicLink()).append("\",\"messageUrl\":\"").append(pushBean.getLink()).append("\"}}");
        request.setEntity(new StringEntity(param.toString(), ContentType.APPLICATION_JSON));

        Response response = processor.sendRequest(request, true);
        if (response == null){
            int i = 0;
            while (i < 2){
                response = processor.sendRequest(request, true);
                i++;
            }
            return true;
        }
        JSONObject result = JSON.parseObject(response.getContent());
        String errcode = result.getString("errcode");
        if ("0".equals(errcode)){
            return true;
        }
        return false;
    }
}
