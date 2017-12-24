package cn.onearth.fmzs.viewresolver;

import org.springframework.web.servlet.view.InternalResourceView;

import java.io.File;
import java.util.Locale;

/**
 * html视图解析器
 *
 * Created by wliu on 2017/12/9 0009.
 */
public class HtmlInternalResourceView extends InternalResourceView {

    @Override
    public boolean checkResource(Locale locale){
        System.out.println(this.getServletContext().getRealPath("/") + getUrl());
        File file = new File(this.getServletContext().getRealPath("/") + getUrl());

        return file.exists();
    }
}
