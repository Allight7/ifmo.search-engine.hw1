package ru.ifmo.g5357.websearch.krayushkin.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * User: allight
 * Date: 03.10.2014 10:44
 */

public class Crawler {
    private LinkedHashSet<String> urlToProcess;
    private HashMap<String, String> urlMap;  //<url,title>
    private int bwMode;                     // mode: 0 - common, 1 - bList, 2 - wList
    private HashSet<String> bwList;
    //todo: robots.txt
    //todo: add log
    //todo: implement bwList

    public Crawler(int mode) throws IOException {   // mode: 0 - common, 1 - bList, 2 - wList
        urlToProcess = new LinkedHashSet<>();
        urlMap = new HashMap<>();
        if (mode == 1 || mode == 2) {
            bwMode = mode;
            bwList = new HashSet<>();
        } else bwMode = 0;

        if(!Files.exists(Paths.get("./dir")))
            Files.createDirectory(Paths.get("./dir"));
    }

    public void addUrl(String s) {
        urlToProcess.add(s);
    }

    public void addAllUrl(Collection<String> u) {
        urlToProcess.addAll(u);
    }

    public String getURL(String url) throws IOException {
        return Jsoup.connect(url).get().toString();
    }


    public void processQueue() throws IOException {                 //todo: check for unical
        int i = 0;                                                                             //todo: debugLine
        while (!urlToProcess.isEmpty()) {
            Iterator<String> it = urlToProcess.iterator();          //todo: maybe create it outside @while
            String url = it.next();
            System.out.println("--> STARTED: \"" + url + "\"");                                //todo: debugLine
            Document page = Jsoup.connect(url).get();
            parseURL(page);                             //get all url


            //todo: temp solution:
            urlMap.put(url, page.title());
            urlToProcess.remove(url);
            System.out.println("--x COMPLETE: \"" + url + "\"");                                //todo: debugLine
            if(i == 100)                                                                          //todo: debugLine
                return;                                                                         //todo: debugLine
            i++;                                                                                //todo: debugLine

//            String path;                                //name for new file
//            if (!urlMap.containsKey(url)) {                         //todo: ??? what do with old values?  Unic of pages?
//                urlMap.put(url, page.title());                      //todo: check how good @title works
//                path = page.title();
//            } else path = urlMap.get(url);             //do not change
//
//            Path dir = Paths.get("").toAbsolutePath();
//            //todo:file name collision
////          dir.
//            Files.write(Paths.get("./" + path + ".txt"), page.toString().getBytes());
////            List<String> lines = Files.readAllLines(Paths.get("./duke.txt"), Charset.defaultCharset());
////            for (String line : lines) {
////            System.out.println("line read: " + line);


        }
    }

    public void parseURL(Document page) throws IOException {
//todo: get absolute urls
        Elements links = page.getElementsByTag("a");
        for (Element link : links) {
            String href;
            if (link.attr("href").matches("^/.*"))
                href = link.attr("abs:href");
            else href = link.attr("href");
//todo: is it really needed?
            if (!urlMap.containsKey(href) && !urlToProcess.contains(href)) {
                urlToProcess.add(href);
                System.out.println("--+ ADDED: \"" + href + "\"");                                //todo: debugLine
            }
        }
    }


}

