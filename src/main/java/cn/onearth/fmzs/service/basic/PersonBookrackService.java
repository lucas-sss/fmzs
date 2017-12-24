package cn.onearth.fmzs.service.basic;

import cn.onearth.fmzs.model.pojo.Bookrack;

import java.util.List;

/**
 * 个人书架service接口
 *
 * Created by wliu on 2017/12/18 0018.
 */
public interface PersonBookrackService {

    Bookrack getBookrackByUserAndBook(Integer userId, Integer bookId);

    List<Bookrack> getBookrack(Integer userId);

    void addBookrack(Bookrack bookrack);

    void updateBookrack(Bookrack bookrack);
}
