import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jarvismall.mapper.TbItemMapper;
import com.jarvismall.pojo.TbItem;
import com.jarvismall.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by JarvisDong on 2018/8/19.
 */
public class TestPageHelper {

    @Test
    public void testPageHelper() throws Exception{
        //配置分页条件
        PageHelper.startPage(1,10);
        //执行查询
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        TbItemMapper bean = applicationContext.getBean(TbItemMapper.class);
        TbItemExample example = new TbItemExample();
//        TbItemExample.Criteria criteria = example.createCriteria();
        List<TbItem> tbItems = bean.selectByExample(example);

        Page page = new Page(1,10);
        page.addAll(tbItems);
        System.out.print("count:"+page.getTotal()+" "+page.getPages()+" "+ tbItems.size());

    }
}
