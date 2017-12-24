package cn.onearth.fmzs.controller;

import cn.onearth.fmzs.model.pojo.Person;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


@Controller
@RequestMapping("/")
public class PageContoller {

	@RequestMapping("/")
	public ModelAndView rootPath(){
		return new ModelAndView("forward:/book/bookstorage");
	}
	
	@RequestMapping("page/{pageName}")
	public ModelAndView getPage(@PathVariable String pageName){
		
		return new ModelAndView(pageName);
	}

	@RequestMapping(value = "page/{bookid}/{sectionid}")
	public ModelAndView toSection(@PathVariable("bookid") String bookid,@PathVariable("sectionid") String sectionid, HttpSession session){

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/book/bookstorage");
		if (!bookid.matches("\\d+") || !sectionid.matches("\\d+")){
			//返回首页
			return mv;
		}
		//记录每个人的读书进度
		Person user = (Person)session.getAttribute("user");
		if (user != null){

			ServletContext servletContext = session.getServletContext();
			String realPath = servletContext.getRealPath("/WEB-INF/html/section");

			File section = new File(realPath + "/" + bookid + "/" + sectionid + ".html");

			if (!section.exists()){
				return mv;
			}

			ConcurrentHashMap<Integer, HashMap<Integer, Integer>> concurrentHashMap = (ConcurrentHashMap)servletContext.getAttribute("readSiteMap");
			HashMap<Integer, Integer> map;
			if (concurrentHashMap.containsKey(user.getId())){
				map = concurrentHashMap.get(user.getId());
			}else {
				map = new HashMap<>();
				concurrentHashMap.put(user.getId(), map);
			}
			map.put(Integer.valueOf(bookid), Integer.valueOf(sectionid));
		}

		String viewName = "section/" + bookid + "/" + sectionid;
		mv.setViewName(viewName);
		return mv;
	}
}
