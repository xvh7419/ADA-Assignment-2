/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.assignment.pkg2;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Alex
 */
public class Search {
    private Scanner keyboard = new Scanner(System.in);
    private SpiderLeg spiderleg;
    private Set<HTMLink> visitedPages;
    private Queue<HTMLink> unvisitedPages;
    private Set<String> savedUrls;
    
    public Search() {
        spiderleg = new SpiderLeg();
        this.visitedPages = new HashSet();
        this.unvisitedPages = new LinkedList();
        savedUrls = new HashSet<>();
    }
    
    public void search() {
        //query user for a url
        System.out.println("Please specify a seed URL: ");
        String url = keyboard.nextLine();
        
        //query user for a keyword
        System.out.println("Please specify a keyword: ");
        String keyword = keyboard.nextLine();
        
        //query a user for depth to search to
        System.out.println("Up to what depth would you like to search for?");
        int depth = Integer.parseInt(keyboard.nextLine());
        
        spider(url, keyword, depth);
    }
    
    public Set<String> spider(String url, String keyword, int depth) {
        //change url to HTMLink to track depth
        HTMLink firstUrl = new HTMLink(url, 0);
        //add seed URL to pages to visit
        unvisitedPages.add(firstUrl);
        
        //crawl if there are still unvisited pages. 
        while (!unvisitedPages.isEmpty()) {
            //get the url to visit from unvisited pages
            HTMLink urlToVisit = unvisitedPages.poll();
            
            //stop crawling when target depth is breached
            if(urlToVisit.getDepth() > depth) {
                break;
            }
            
            //get meta from URL
            String meta = spiderleg.getMeta(urlToVisit.getUrl()).toString();
            //get substring of keywords from meta
            String keywords = meta.substring(meta.indexOf("Keywords: ") + 3, meta.length());
            
            //if the keywords contain the keyword the user specified then add the url to the set of saved URLS
            if (keywords.toLowerCase().contains(keyword.toLowerCase())) {
                savedUrls.add(urlToVisit.getUrl());
                System.out.println(urlToVisit.getUrl());
                System.out.println(keywords);
            }
            
            //add url to visited pages
            if(!visitedPages.contains(urlToVisit)) {
                visitedPages.add(urlToVisit);
            } 
            
            //get all links from current url
            List<String> links = spiderleg.getHyperLink(urlToVisit.getUrl());
            for (String link : links) {                
                //if url has not been visited and is not empty then add it to the unvisited
                if (!visitedPages.contains(link) && !link.isEmpty()) {
                    HTMLink linkToAdd = new HTMLink(link, (urlToVisit.getDepth() + 1));
                    unvisitedPages.add(linkToAdd);
                }
            }           
        } 
        
        //Write urls to file and console
        try {
            PrintWriter out = new PrintWriter(keyword + "Keyword Search.txt");
            out.println(savedUrls);
            
            System.out.println(savedUrls);
        } catch (Exception e) {
            System.err.println(e);
        }
        
        
        return savedUrls;
    }
    
    public static void main (String args[]) {
        Search s = new Search();
        s.search();
    }
}
