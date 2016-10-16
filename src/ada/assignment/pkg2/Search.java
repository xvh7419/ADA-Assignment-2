/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.assignment.pkg2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
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
    private Queue<HTMLink> pagesToVisit;
    private Set<String> savedUrls;
    
    public Search() {
        spiderleg = new SpiderLeg();
        this.visitedPages = new HashSet();
        this.pagesToVisit = new LinkedList();
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
        pagesToVisit.add(firstUrl);
        
        //crawl if there are still unvisited pages. 
        while (!pagesToVisit.isEmpty()) {
            System.out.println(pagesToVisit.size() + " link(s) left to crawl.");
            
            //get the url to visit from unvisited pages
            HTMLink urlToVisit = pagesToVisit.poll();
            
            //stop crawling when target depth is breached
            if(urlToVisit.getDepth() > depth) {
                System.out.println("Depth of " + depth + " exceeded.");
                break;
            }
            
            //get meta from URL
            String meta = spiderleg.getMeta(urlToVisit.getUrl()).toString();
            
            //check if meta was acquired or not.
            String keywords = "";
            if (!meta.isEmpty()) {
                //get substring of keywords from meta, 
                keywords = meta.substring(meta.indexOf("Keywords: ") + 9, meta.length());
            } 
            //if the keywords contain the keyword the user specified then add the url to the set of saved URLS
            if (keywords.toLowerCase().contains(keyword.toLowerCase())) {
                savedUrls.add(urlToVisit.getUrl());
            }
            
            //add url to visited pages
            visitedPages.add(urlToVisit);
     
            //get all links from current url
            List<String> links = spiderleg.getHyperLink(urlToVisit.getUrl());
            for (String link : links) {                
                //if url has not been visited and is not empty then add it to the unvisited
                HTMLink linkToAdd = new HTMLink(link, (urlToVisit.getDepth() + 1));
                if (!visitedPages.contains(linkToAdd) && !link.isEmpty()) {
                    
                    if (linkToAdd.getDepth() <= depth) {
                        pagesToVisit.add(linkToAdd);
                    }          
                }
            }           
        } 
        
        //Write urls to file and console
        try {
            File linkFile = new File("Links with Meta Keyword - " + keyword + ".txt");
            PrintStream out = new PrintStream(new FileOutputStream(linkFile));
            for (String links : savedUrls) {
                out.println(links);
                System.out.println(links);
            }
            
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
