package cn.onearth.fmzs.service.taskjob;

import cn.onearth.fmzs.model.pojo.BookSection;
import cn.onearth.fmzs.model.pojo.Bookrack;
import cn.onearth.fmzs.service.basic.BookSectionService;
import cn.onearth.fmzs.service.basic.PersonBookrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * Created by wliu on 2017/12/19 0019.
 * @author wliu
 */
@Component(value = "remberReadSiteTaskJob")
public class RemberReadSiteTaskJob implements ServletContextAware {

    @Autowired
    private PersonBookrackService personBookrackService;

    @Autowired
    private BookSectionService bookSectionService;

    private ServletContext servletContext;


    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void  recordReadsite(){
        System.out.println("----------------------------------------------------------------------------" +
                "---------------------------------------------------------------------------------------------" +
                "-----------------------------------------------------------------------------------------");
        ConcurrentHashMap<Integer, HashMap<Integer, Integer>> conMap = (ConcurrentHashMap)servletContext.getAttribute("readSiteMap");

        if (!conMap.isEmpty()){
            Set<Map.Entry<Integer, HashMap<Integer, Integer>>> entries = conMap.entrySet();
            for (Map.Entry<Integer, HashMap<Integer, Integer>> entry : entries) {
                Integer userId = entry.getKey();
                /**
                 * key是书籍id，value是章节id
                 */
                HashMap<Integer, Integer> readInfo = entry.getValue();
                Iterator<Integer> iterator = readInfo.keySet().iterator();
                Integer bookId = iterator.next();
                BookSection findSection = bookSectionService.getBookSectionByBookAndSite(bookId, readInfo.get(bookId));
                //对阅读进度进行更新
                Bookrack findBookrack = personBookrackService.getBookrackByUserAndBook(userId, bookId);
                if (findSection != null && findBookrack != null){
                    findBookrack.setReadProgress(findSection.getSectionName());

                    findBookrack.setReadPath("/page/" + bookId + "/" + readInfo.get(bookId));
                    personBookrackService.updateBookrack(findBookrack);
                }
            }
        }
    }

}
