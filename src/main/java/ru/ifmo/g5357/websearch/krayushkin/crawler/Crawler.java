package ru.ifmo.g5357.websearch.krayushkin.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * User: allight
 * Date: 03.10.2014 10:44
 */

public class Crawler {
    private Queue<String> urlQueue;
    private Map<String,String> urlMap;
    private Whitelist whitelist;

    public Crawler() throws IOException {
        //todo: query with unical elem
        urlQueue = new PriorityQueue<>();
        urlMap = new HashMap<>();
        Files.createDirectory(Paths.get("./dir"));

    }

    public void addURL(String u){
        urlQueue.add(u);
    }

    public String getURL(String url) throws IOException {
        return Jsoup.connect(url).get().toString();
    }


    public void processQuery() throws IOException {
        while(!urlQueue.isEmpty()){
//todo: check for unical
            String url = urlQueue.peek();
            System.out.println(url);
            Document page = Jsoup.connect(url).get();
            parseURL(page);
            urlQueue.poll();
//todo: what do with old values?  Unic of pages?
            String path;
            if(!urlMap.containsKey(url)){
                urlMap.put(url, page.title());
                path = page.title();
            }
            else path = urlMap.get(url);
            Path dir = Paths.get("").toAbsolutePath();
// todo:file name collision
//          dir.
            Files.write(Paths.get("./" + path + ".txt"),page.toString().getBytes());
//            List<String> lines = Files.readAllLines(Paths.get("./duke.txt"), Charset.defaultCharset());
//            for (String line : lines) {
//            System.out.println("line read: " + line);




        }
    }

    public void parseURL(Document page) throws IOException {
//todo: get absolute urls
        Elements links = page.getElementsByTag("a");
        for (Element link : links) {
            String href;
            if(link.attr("href").matches("^/.*"))
                href = link.attr("abs:href");
            else href = link.attr("href");
//todo: is it really needed?
            if(!urlMap.containsKey(href) && !urlQueue.contains(href))
                urlQueue.add(href);
        }
    }


}

