package cn.onearth.fmzs.service.basic.impl;

import cn.onearth.fmzs.dao.PersonMapper;
import cn.onearth.fmzs.model.pojo.Person;
import cn.onearth.fmzs.service.basic.PersonService;
import com.github.abel533.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wliu on 2017/11/19 0019.
 */
@Service(value = "personService")
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonMapper personMapper;


    @Override
    public Person getPersonById(Integer id) {
        return personMapper.selectByPrimaryKey(id);
    }

    @Override
    public Person getPersonByName(String name) {
        Example example = new Example(Person.class);
        example.createCriteria().andEqualTo("userName", name);
        List<Person> list = personMapper.selectByExample(example);
        return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public Person getPersonByNameAndPwd(String name, String pwd) {
        Example example = new Example(Person.class);
        example.createCriteria().andEqualTo("userName", name).andEqualTo("password", pwd);
        List<Person> persons = personMapper.selectByExample(example);
        return persons.size() > 0 ? persons.get(0) : null;
    }
}
