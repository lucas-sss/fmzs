package cn.onearth.fmzs.service.basic.impl;

import cn.onearth.fmzs.dao.PersonBookrackMapper;
import cn.onearth.fmzs.model.pojo.Bookrack;
import cn.onearth.fmzs.service.basic.PersonBookrackService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 个人书架service
 *
 * Created by wliu on 2017/12/18 0018.
 */
@Service(value = "personBookrackService")
public class PersonBookrackServiceImpl implements PersonBookrackService {

    @Autowired
    private PersonBookrackMapper personBookrackMapper;

    @Override
    public Bookrack getBookrackByUserAndBook(@Param("userId") Integer userId, @Param("bookId")Integer bookId) {
        return personBookrackMapper.getBookrackByUserAndBook(userId,bookId);
    }

    @Override
    public List<Bookrack> getBookrack(Integer userId) {
        return personBookrackMapper.getBookrack(userId);
    }

    @Override
    public void addBookrack(Bookrack bookrack) {
        personBookrackMapper.addBookrack(bookrack);
    }

    @Override
    public void updateBookrack(Bookrack bookrack) {
        personBookrackMapper.updateBookrack(bookrack);
    }
}
