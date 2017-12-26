package cn.onearth.fmzs.dao;

import cn.onearth.fmzs.model.pojo.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wliu on 2017/11/18 0018.
 */
public interface BookMapper {

    Book getBookById(Integer id);

    List<Book> getBookByNamelike(String name);

    List<Book> getBookByAuthorlike(String author);

    List<Book> getAllBook();


    void saveBook(Book book);

    void updateBook(Book book);

}
