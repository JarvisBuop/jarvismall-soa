import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by JarvisDong on 2018/8/28.
 */
public class TestJedis {

    @Test
    public void testJedis() throws  Exception{
        Jedis jedis = new Jedis("192.168.142.129");
        jedis.set("jedis-test","jarvisdong");
        String result = jedis.get("jedis-test");
        System.out.print("r:"+result);
        jedis.close();
    }

    @Test
    public void testJedisPool() throws Exception{
        //单例;
        JedisPool jedisPool = new JedisPool("192.168.142.129");
        Jedis jedis = jedisPool.getResource();

        String result = jedis.get("jedis-test");
        System.out.print("r:"+result);
        jedis.close();
        jedisPool.close();
    }

    @Test
    public void testJedisCluster() throws Exception{
        Set<HostAndPort> set = new HashSet<>();
        set.add(new HostAndPort("192.168.142.129",7001));
        set.add(new HostAndPort("192.168.142.129",7002));
        set.add(new HostAndPort("192.168.142.129",7003));
        set.add(new HostAndPort("192.168.142.129",7004));
        set.add(new HostAndPort("192.168.142.129",7005));
        set.add(new HostAndPort("192.168.142.129",7006));
        JedisCluster cluster = new JedisCluster(set);

        cluster.set("jarvis","jarvis-test");

        String string = cluster.get("jarvis");
        System.out.print("r:"+string);

        cluster.close();
    }
}
