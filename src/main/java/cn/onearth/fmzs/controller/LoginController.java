package cn.onearth.fmzs.controller;

import cn.onearth.fmzs.model.pojo.Person;
import cn.onearth.fmzs.service.basic.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录controller
 *
 * Created by wliu on 2017/11/18 0018.
 */
@Controller
@RequestMapping(value = "user")
public class LoginController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(Model model, Person person, String remberLogin, HttpServletResponse response, HttpSession session){
        ModelAndView mv = new ModelAndView();
        Person findOne = personService.getPersonByNameAndPwd(person.getUserName(), person.getPassword());
        if (findOne == null){
            mv.addObject("userName",person.getUserName());
            mv.addObject("errorMsg","用户名或密码错误！");
            mv.setViewName("login");
            return mv;
        }
        session.setAttribute("user", findOne);
        response.addCookie(new Cookie("user", String.valueOf(findOne.getId())));

        mv.setViewName("redirect:/book/bookrack?user=" + findOne.getId());
        mv.addObject("person", findOne);
        return mv;
    }


    /**
     * 供ajax使用，没有登录统一返回-5
     *
     * @return
     */
    @RequestMapping(value = "/login/ajaxhandle")
    @ResponseBody
    public String ajaxhandle(){
        return "-5";
    }


    @RequestMapping(value = "/outlogin")
    public String outLogin(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.invalidate();
        return "redirect:/book/bookstorage";
    }
}
