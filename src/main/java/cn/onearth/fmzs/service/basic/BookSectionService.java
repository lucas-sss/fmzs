package cn.onearth.fmzs.service.basic;

import cn.onearth.fmzs.model.pojo.BookSection;

import java.util.List;

/**
 * Created by wliu on 2017/11/18 0018.
 */
public interface BookSectionService {

    BookSection getBookSectionById(Integer id);

    List<BookSection> getAllSectionByBook(Integer bookId);

    BookSection getBookSectionByBookAndSite(Integer bookId, Integer site);

    void updateLocalPath(BookSection bookSection);

    void updateSectionById(BookSection bookSection);

    Integer getLocalLatestSection(Integer bookId);

    Integer saveBookSection(BookSection bookSection);
}
