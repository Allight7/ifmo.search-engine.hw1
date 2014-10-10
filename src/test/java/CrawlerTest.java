import ru.ifmo.g5357.websearch.krayushkin.crawler.Crawler;

import java.io.IOException;
import java.net.URL;

/**
 * User: allight
 * Date: 03.10.2014 11:18
 */
public class CrawlerTest {
    public static void main(String[] args) throws IOException {
        Crawler c = new Crawler();
        URL u = new URL("http://vk.com");
//        System.out.println(c.getURL(u.toString()));
        c.addURL(u.toString());
        c.processQuery();


    }
}
