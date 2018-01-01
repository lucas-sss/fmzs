package cn.onearth.fmzs.service.basic.impl;

import cn.onearth.fmzs.dao.BookMapper;
import cn.onearth.fmzs.dao.BookSourceAddMapper;
import cn.onearth.fmzs.model.pojo.Book;
import cn.onearth.fmzs.model.pojo.BookSourceAdd;
import cn.onearth.fmzs.model.pojo.TracerTask;
import cn.onearth.fmzs.service.basic.BookService;
import cn.onearth.fmzs.service.basic.TracerService;
import cn.onearth.fmzs.service.taskjob.TracerTaskJob;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
    @Autowired
    private TracerTaskJob tracerTaskJob;
    @Autowired
    private TracerService tracerService;

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
        for (int id : ids) {
            //1、把推荐书籍添加进书库
            BookSourceAdd bookSourceAdd = bookSourceAddMapper.selectByPrimaryKey(id);
            Book book = new Book();
            if (bookSourceAdd != null){
                book.setName(bookSourceAdd.getName());
                book.setRootPath(bookSourceAdd.getRootSource());
                book.setAuthor(bookSourceAdd.getAuthor());
                book.setStatus(String.valueOf(bookSourceAdd.getStatus()));
                bookMapper.saveBook(book);
            }
            //2、添加追书任务
            TracerTask tracerTask = new TracerTask();
            tracerTask.setBookId(book.getId());
            Date date = Calendar.getInstance().getTime();
            tracerTask.setCreateTime(date);
            tracerTask.setModifyTime(date);
            tracerService.saveTracerTask(tracerTask);

            //3、新添加书籍要把所有章节爬取入库


        }



        return 0;
    }

    @Override
    public int addSource(BookSourceAdd bookSourceAdd) {
        if (bookSourceAdd.getCreateTime() == null){
            bookSourceAdd.setCreateTime(new Date());
            bookSourceAdd.setModifyTime(bookSourceAdd.getCreateTime());
        }
        return bookSourceAddMapper.insert(bookSourceAdd);
    }

    @Override
    public BookSourceAdd getOfferSource(String name, String author) {
        Example example = new Example(BookSourceAdd.class);
        example.createCriteria().andEqualTo("name", name).andEqualTo("author", author);
        List<BookSourceAdd> list = bookSourceAddMapper.selectByExample(example);
        return list.size() > 0 ? list.get(0) : null;
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
