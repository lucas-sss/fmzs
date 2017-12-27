package cn.onearth.fmzs.model.business;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by wliu on 2017/11/28 0028.
 */
@Data
@AllArgsConstructor
public class PushBean {

    private String title;

    private String text;

    private String link;

    private String picLink;

    /**
     * 对应推送群
     */
    private String tocken;


    public PushBean() {
    }

    public PushBean(String title, String text, String link) {
        this.title = title;
        this.text = text;
        this.link = link;
    }

    public PushBean(String text) {
        this.text = text;
    }
}
