package cn.onearth.fmzs.dao;

import cn.onearth.fmzs.model.pojo.BookSection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wliu on 2017/11/18 0018.
 */
public interface BookSectionMapper {

    BookSection getBookSectionById(Integer id);

    List<BookSection> getAllSectionByBook(@Param("bookId")Integer bookId);

    BookSection getBookSectionByBookAndSite(@Param("bookId")Integer bookId, @Param("site") Integer site);

    void updateLocalPath(BookSection bookSection);

    void updateSectionById(BookSection bookSection);

    Integer getLocalLatestSection(Integer bookId);

    Integer saveBookSection(BookSection bookSection);

}
