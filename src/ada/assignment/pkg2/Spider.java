/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.assignment.pkg2;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Alex
 */
public class Spider {

    private Set<HTMLink> visitedPages;
    private Queue<HTMLink> pagesToVisit;
    private SpiderLeg spiderLeg;
    private final int MAX_DEPTH = 2;

    public Spider() {
        this.visitedPages = new HashSet();
        this.pagesToVisit = new LinkedList();
        this.spiderLeg = new SpiderLeg();
    }

    public void crawl(String url) {
        //change url to HTMLink to track depth
        HTMLink firstUrl = new HTMLink(url, 0);
        //add seed URL to pages to visit
        pagesToVisit.add(firstUrl);

        //crawl if there are still unvisited pages. 
        while (!pagesToVisit.isEmpty()) {
            
            System.out.println(pagesToVisit.size() + " link(s) left to crawl.");
            
            //get the url to visit from unvisited pages
            HTMLink urlToVisit = pagesToVisit.poll();
            
            //stop crawling when target depth is breached
            if(urlToVisit.getDepth() > MAX_DEPTH) {
                break;
            }

            //add url to visited pages
            if(!visitedPages.contains(urlToVisit)) {
                visitedPages.add(urlToVisit);
            } 

            //get all links from current url
            List<String> links = spiderLeg.getHyperLink(urlToVisit.getUrl());
            for (String link : links) {
                //if url has not been visited and is not empty then add it to the unvisited
               HTMLink linkToAdd = new HTMLink(link, (urlToVisit.getDepth() + 1));
                if (!visitedPages.contains(linkToAdd) && !link.isEmpty()) {
                    if (linkToAdd.getDepth() <= MAX_DEPTH) {
                        pagesToVisit.add(linkToAdd);
                    }
                }
            }            
        }
        //print out all visited/acquired URLs
        for (HTMLink links: visitedPages) {
            System.out.println(links);
        }
        
    }

    public static void main(String args[]) {
        Spider s = new Spider();

        s.crawl("https://www.youtube.com/");
    }

}
