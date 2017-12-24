package cn.onearth.fmzs.service.basic;

import cn.onearth.fmzs.model.pojo.Person;

/**
 * Created by wliu on 2017/11/18 0018.
 */
public interface PersonService {

    Person getPersonById(Integer id);

    Person getPersonByName(String name);

    Person getPersonByNameAndPwd(String name, String pwd);
}
