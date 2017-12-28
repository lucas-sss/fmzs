package cn.onearth.fmzs.controller;

import cn.onearth.fmzs.model.pojo.Book;
import cn.onearth.fmzs.model.pojo.BookSourceAdd;
import cn.onearth.fmzs.service.basic.BookService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 后台控制
 *
 * @author liuwei 1215946336@qq.com
 * @version 1.0
 * @date 2017/12/27 0027
 */
@Controller
@RequestMapping(value = "/console")
public class ConsoleController {

    private static final Logger LOGGER = Logger.getLogger(ConsoleController.class.getName());


    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/book/list")
    @ResponseBody
    public void getAllbook(HttpServletResponse response) throws IOException {
        List<Book> list = bookService.getAllBook();

        response.setContentType("application/json;charset=UTF-8");
        JSONArray jsonArray = new JSONArray(new ArrayList<>(list));
        response.getWriter().write(jsonArray.toJSONString());
    }


    @RequestMapping(value = "/book/sourceOffer")
    @ResponseBody
    public void getSourceAdds(HttpServletResponse response) throws IOException {
        List<BookSourceAdd> list = bookService.getSourceAdds();

        response.setContentType("application/json;charset=UTF-8");
        JSONArray jsonArray = new JSONArray(new ArrayList<>(list));
        response.getWriter().write(jsonArray.toJSONString());
    }

    @RequestMapping(value = "/book/sourceDel", method = RequestMethod.POST)
    @ResponseBody
    public void deleteSource(String idStr, HttpServletResponse response) throws IOException {

        Integer[] ids = null;
        if (StringUtils.contains(idStr, ",")) {
            String[] split = idStr.split(",");
            ids = new Integer[split.length];
            for (int i = 0; i < split.length; i++) {
                ids[i] = Integer.valueOf(split[i]);
            }
        } else {
            ids = new Integer[1];
            ids[0] = Integer.valueOf(idStr);
        }

        int code = 0;
        String msg = "";
        try {
            int i = bookService.deleteSourceOffer(ids);
            if (i <= 0) {
                code = -1;
                msg = "删除失败";
            }
        } catch (Exception e) {
            LOGGER.info("删除推荐书源出错");
            code = -5;
            msg = "删除推荐书源出现异常";
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("msg", msg);

        response.setContentType("application/json;charset=UTF-8");
        JSONObject object = new JSONObject(map);
        response.getWriter().write(object.toJSONString());
    }


    @RequestMapping(value = "/book/sourceOfferAdd", method = RequestMethod.POST)
    @ResponseBody
    public void addSource(String idStr, HttpServletResponse response) throws IOException {


        int[] ids = null;
        if (StringUtils.contains(idStr, ",")) {
            String[] split = idStr.split(",");
            ids = Arrays.asList(split).stream().mapToInt(x -> Integer.valueOf(x)).toArray();
        } else {
            ids = new int[1];
            ids[0] = Integer.valueOf(idStr);
        }


    }

}
