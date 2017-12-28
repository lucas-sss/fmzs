package cn.onearth.fmzs.service.basic.impl;

import cn.onearth.fmzs.dao.BookMapper;
import cn.onearth.fmzs.dao.BookSourceAddMapper;
import cn.onearth.fmzs.model.pojo.Book;
import cn.onearth.fmzs.model.pojo.BookSourceAdd;
import cn.onearth.fmzs.service.basic.BookService;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 书籍本身和用户书籍关系维护服务
 * <p>
 * Created by wliu on 2017/11/18 0018.
 */
@Service(value = "bookService")
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookSourceAddMapper bookSourceAddMapper;

    @Override
    public Book getBookById(Integer id) {
        return bookMapper.getBookById(id);
    }

    @Override
    public List<Book> getBookByNamelike(String name) {
        return bookMapper.getBookByNamelike(name);
    }

    @Override
    public List<Book> getAllBook() {
        return bookMapper.getAllBook();
    }


    @Override
    public PageInfo<Book> getAllBookByPage(Integer pageNo, Integer pageSize) {

        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = Integer.valueOf("12");
        }

        System.out.println();

        PageHelper.startPage(pageNo, pageSize);
        List<Book> allBook = bookMapper.getAllBook();
        PageInfo<Book> pageInfo = new PageInfo<Book>(allBook);
        return pageInfo;
    }

    @Override
    public List<BookSourceAdd> getSourceAdds() {
        List<BookSourceAdd> select = bookSourceAddMapper.select(null);
        return select;
    }

    @Override
    public int deleteSourceOffer(Integer[] ids) {
        Example exmple = new Example(BookSourceAdd.class);
        exmple.createCriteria().andIn("id", Arrays.asList(ids));
        return bookSourceAddMapper.deleteByExample(exmple);
    }

    @Override
    public int addSourceOffer(int[] ids) {

        /**
         * 有很大一部分逻辑
         */
        //1、吧推荐书籍添加进书库
        //2、添加追书任务
        //3、新添加书籍要把所有章节爬取入库


        return 0;
    }

    @Override
    public void saveBook(Book book) {
        bookMapper.saveBook(book);
    }

    @Override
    public void updateBook(Book book) {
        bookMapper.updateBook(book);
    }

}
