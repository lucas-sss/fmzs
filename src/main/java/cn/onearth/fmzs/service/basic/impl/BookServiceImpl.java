package cn.onearth.fmzs.service.basic.impl;

import cn.onearth.fmzs.dao.BookMapper;
import cn.onearth.fmzs.model.pojo.Book;
import cn.onearth.fmzs.service.basic.BookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void saveBook(Book book) {
        bookMapper.saveBook(book);
    }

    @Override
    public void updateBook(Book book) {
        bookMapper.updateBook(book);
    }

}
