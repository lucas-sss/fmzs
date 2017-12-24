package cn.onearth.fmzs.dao;

import cn.onearth.fmzs.model.pojo.Bookrack;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wliu on 2017/12/18 0018.
 */
public interface PersonBookrackMapper {

    /**
     * 根据用户id查询出个人书架列表
     *
     * @param userId
     * @return
     */
    List<Bookrack> getBookrack(Integer userId);

    Bookrack getBookrackByUserAndBook(@Param("userId") Integer userId, @Param("bookId")Integer bookId);

    void addBookrack(Bookrack bookrack);

    void updateBookrack(Bookrack bookrack);
}
