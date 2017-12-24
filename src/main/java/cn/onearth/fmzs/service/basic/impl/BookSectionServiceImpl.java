package cn.onearth.fmzs.service.basic.impl;

import cn.onearth.fmzs.dao.BookSectionMapper;
import cn.onearth.fmzs.model.pojo.BookSection;
import cn.onearth.fmzs.service.basic.BookSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 章节service
 * <p>
 * Created by wliu on 2017/11/19 0019.
 */
@Service(value = "bookSectionService")
class BookSectionServiceImpl implements BookSectionService {

    @Autowired
    private BookSectionMapper bookSectionMapper;


    @Override
    public BookSection getBookSectionById(Integer id) {
        return bookSectionMapper.getBookSectionById(id);
    }

    @Override
    public List<BookSection> getAllSectionByBook(Integer bookId) {
        return bookSectionMapper.getAllSectionByBook(bookId);
    }

    @Override
    public BookSection getBookSectionByBookAndSite(Integer bookId, Integer site) {
        return bookSectionMapper.getBookSectionByBookAndSite(bookId, site);
    }

    @Override
    public void updateLocalPath(BookSection bookSection) {
        bookSectionMapper.updateLocalPath(bookSection);
    }

    @Override
    public void updateSectionById(BookSection bookSection) {
        bookSectionMapper.updateSectionById(bookSection);
    }

    @Override
    public Integer getLocalLatestSection(Integer bookId) {
        Integer localLatestSection = bookSectionMapper.getLocalLatestSection(bookId);
        return localLatestSection;
    }

    @Override
    public Integer saveBookSection(BookSection bookSection) {
        Integer id = bookSectionMapper.saveBookSection(bookSection);
        return id;
    }
}
