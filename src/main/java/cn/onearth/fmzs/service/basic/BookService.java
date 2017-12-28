package cn.onearth.fmzs.service.basic;

import cn.onearth.fmzs.model.pojo.Book;
import cn.onearth.fmzs.model.pojo.BookSourceAdd;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by wliu on 2017/11/18 0018.
 */
public interface BookService {

    Book getBookById(Integer id);

    List<Book> getBookByNamelike(String name);

    List<Book> getAllBook();

    void saveBook(Book book);

    void updateBook(Book book);

    PageInfo<Book> getAllBookByPage(Integer pageNo, Integer pageSize);

    List<BookSourceAdd> getSourceAdds();

    int deleteSourceOffer(Integer[] id);

    int addSourceOffer(int[] ids);
}
