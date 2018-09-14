import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;

/**
 * Created by JarvisDong on 2018/9/14.
 */
public class TestActiveMq {

    @Test
    public void testReceiverMq() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        System.in.read();
    }
}
