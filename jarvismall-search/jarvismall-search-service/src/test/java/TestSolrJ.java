import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by JarvisDong on 2018/9/7.
 */
public class TestSolrJ {

    @Test
    public void testAddDocument() throws IOException, SolrServerException {
        //连接单机版
        SolrServer server = new HttpSolrServer("http://192.168.142.129:8080/solr/collection1");

        SolrInputDocument document = new SolrInputDocument();
        document.addField("item_title","测试商品1");
        document.addField("item_price",1200);
        document.addField("id","5677");
        server.add(document);
        //server.deleteXXX();
        server.commit();
    }
}
