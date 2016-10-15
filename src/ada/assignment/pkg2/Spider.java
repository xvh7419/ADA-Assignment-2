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
    private Queue<HTMLink> unvisitedPages;
    private SpiderLeg spiderLeg;
    private final int MAX_DEPTH = 2;

    public Spider() {
        this.visitedPages = new HashSet();
        this.unvisitedPages = new LinkedList();
        this.spiderLeg = new SpiderLeg();
    }

    public void crawl(String url) {
        //add the seed url
        HTMLink firstUrl = new HTMLink(url, 0);
        unvisitedPages.add(firstUrl);

        //crawl if there are still unvisited pages. Stop if the number of visits exceeds a number
        while (!unvisitedPages.isEmpty()) {
            
            //get the url to visit from unvisited pages
            HTMLink urlToVisit = unvisitedPages.poll();
            
            //finish program when max depth is reached
            if(urlToVisit.getDepth() > MAX_DEPTH) {
                return;
            }
            
            //Get and display content
            String content = spiderLeg.getTitle(urlToVisit.getUrl()) + "\n"
                    + urlToVisit.getUrl() + "\n"
                    + "Depth: " + urlToVisit.getDepth();

            System.out.println(content + "\n");

            //add url to visited pages
            visitedPages.add(urlToVisit);

            //get all links from current url
            List<String> links = spiderLeg.getHyperLink(urlToVisit.getUrl());
            for (String link : links) {
                //if url has not been visited then add it to the unvisited
                if (!visitedPages.contains(link) && !link.isEmpty()) {
                    HTMLink linkToAdd = new HTMLink(link, (urlToVisit.getDepth() + 1));
                    unvisitedPages.add(linkToAdd);
                }
            }
        }
    }

    public static void main(String args[]) {
        Spider s = new Spider();

        s.crawl("http://www.w3schools.com/html/default.asp");
    }

}
