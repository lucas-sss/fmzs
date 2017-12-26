package cn.onearth.fmzs.controller;

import cn.onearth.fmzs.Utils.common.ConstantCacheUtil;
import cn.onearth.fmzs.Utils.common.ConstantParam;
import cn.onearth.fmzs.Utils.pagination.Pagination;
import cn.onearth.fmzs.model.pojo.Book;
import cn.onearth.fmzs.model.pojo.BookSection;
import cn.onearth.fmzs.model.pojo.Bookrack;
import cn.onearth.fmzs.model.pojo.Person;
import cn.onearth.fmzs.service.basic.BookSectionService;
import cn.onearth.fmzs.service.basic.BookService;
import cn.onearth.fmzs.service.basic.PersonBookrackService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wliu on 2017/12/8 0008.
 */
@Controller
@RequestMapping(value = "book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookSectionService bookSectionService;

    @Autowired
    private PersonBookrackService personBookrackService;


    @RequestMapping(value = "/search")
    @ResponseBody
    public void search(String bookName, HttpServletResponse response) throws IOException {
        if (bookName == null) {
            System.out.println("----------------");
        }
        try {
            bookName = URLDecoder.decode(bookName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            //TODO
        }
        System.out.println(bookName);
        /**
         * 这里只进行完全匹配,后期会进行solr全文模糊检索
         */
        List<Book> list = bookService.getBookByNamelike(bookName);


        response.setContentType("application/json;charset=UTF-8");
        JSONArray jsonArray = new JSONArray(new ArrayList<>(list));
        response.getWriter().write(jsonArray.toJSONString());
    }


    /**
     * 进入我的书架，如果没有登录会在前面拦截器拦截掉
     *
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "/bookrack")
    public String bookrack(String user, Model model, HttpServletRequest request) {

        /*if (StringUtils.isEmpty(user)) {
            return "redirect:/page/login";
        }
        if (!user.matches("\\d+")) {

        }
        int userId = Integer.valueOf(user);*/

        Person person = (Person) request.getSession().getAttribute("user");
        if (null == person || person.getId() == null) {
            return "redirect:/page/login";
        }
        List<Bookrack> bookracks = personBookrackService.getBookrack(person.getId());

        if (bookracks.size() > 0) {
            for (Bookrack bookrack : bookracks) {
                Book book = bookService.getBookById(bookrack.getBookId());
                bookrack.setBook(book);
            }
        }
        if (bookracks.size() < Integer.valueOf(ConstantCacheUtil.getValue(ConstantParam.BOOK_STORAGE_PAGE_SIZE))) {
            model.addAttribute("size", bookracks.size());
        }
        model.addAttribute("bookracks", bookracks);
        model.addAttribute("user", person);
        return "bookrack";
    }

    @RequestMapping(value = "/bookstorage")
    public String bookStorage(String pageNo, Model model) {
        if (pageNo == null || !pageNo.matches("\\d+")) {
            return "forward:/book/bookstorage/1";
        }
        return "forward:/book/bookstorage/" + pageNo;
    }

    @RequestMapping(value = "/bookstorage/{pageno}")
    public String bookStoragePage(@PathVariable("pageno") String pageNo, Model model, HttpServletRequest request) {
        String join = "--";
        Person person = (Person) request.getSession().getAttribute("user");
        if (null != person) {
            List<Bookrack> bookracks = personBookrackService.getBookrack(person.getId());
            if (bookracks.size() > 0) {
                String[] bookIds = new String[bookracks.size()];
                int i = 0;
                for (Bookrack bookrack : bookracks) {
                    bookIds[i] = bookrack.getBookId().toString();
                    i++;
                }
                join = StringUtils.join(bookIds, ",");
            }
        }
        model.addAttribute("bookIds", join);
        int page = Integer.valueOf(pageNo);
        PageInfo<Book> pageInfo = bookService.getAllBookByPage(page, null);
        List<Book> list = pageInfo.getList();
        if (list.size() < Integer.valueOf(ConstantCacheUtil.getValue(ConstantParam.BOOK_STORAGE_PAGE_SIZE))) {
            model.addAttribute("size", list.size());
        }
        model.addAttribute("bookList", list);
        Pagination pagination = new Pagination();

        pagination.setTotalCount((int) pageInfo.getTotal());
        pagination.setPageNo(page);
        pagination.setPageSize(14);
        pagination.pageView("/book/bookstorage", null);
        model.addAttribute("pagination", pagination);
        return "index";
    }


    /**
     * @param bookId
     * @param model
     * @return
     */
    @RequestMapping(value = "/catalog/{bookid}")
    public String bookCatalog(@PathVariable("bookid") String bookId, Model model) {

        if (bookId == null || !bookId.matches("\\d+")) {
            return "index";
        }
        Integer id = Integer.valueOf(bookId);
        Book book = bookService.getBookById(id);
        if (book != null) {
            List<BookSection> allSectionByBook = bookSectionService.getAllSectionByBook(id);
            model.addAttribute("bookName", book.getName());
            model.addAttribute("sectionList", allSectionByBook);
            return "catalog";
        }
        return "index";
    }


    /**
     * 下一章节
     * current: 102_655
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/section/next/{current}")
    public String nextSection(@PathVariable("current") String current, Model model, HttpServletRequest request) {

        if (current != null && current.matches("\\d+_\\d+")) {
            ServletContext servletContext = request.getSession().getServletContext();

            String[] split = current.split("_");
            Integer boodId = Integer.valueOf(split[0]);
            Integer sectionSite = Integer.valueOf(split[1]);
            sectionSite += 1;
            String realPath = servletContext.getRealPath("/WEB-INF");
            File file = new File(realPath + "/html/section/" + boodId + "/" + sectionSite + ".html");
            if (file.exists()) {
                return "redirect:/page/" + boodId + "/" + sectionSite;
            }
        }
        /**
         * 下一步转向操作错误页面
         */
        return "redirect:/";
    }

    /**
     * 上一章节
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/section/last/{current}")
    public String lastSection(@PathVariable("current") String current, Model model, HttpServletRequest request) {

        if (current != null && current.matches("\\d+_\\d+")) {
            ServletContext servletContext = request.getSession().getServletContext();

            String[] split = current.split("_");
            Integer boodId = Integer.valueOf(split[0]);
            Integer sectionSite = Integer.valueOf(split[1]);
            sectionSite -= 1;
            String realPath = servletContext.getRealPath("/WEB-INF");
            File file = new File(realPath + "/html/section/" + boodId + "/" + sectionSite + ".html");
            if (file.exists()) {
                return "redirect:/page/" + boodId + "/" + sectionSite;
            }
        }

        /**
         * 下一步转向操作错误页面
         */
        return "redirect:/";
    }


    /**
     * 章节目录
     *
     * @param model
     * @param bookid
     * @return
     */
    @RequestMapping(value = "/sectionList/{bookid}")
    public String sectionList(Model model, @PathVariable("bookid") String bookid) {

        return "index";
    }

    @RequestMapping(value = "/markSite/{sectionid}")
    public String markSite(Model model, @PathVariable("sectionid") String sectionid) {

        return "index";
    }

}
