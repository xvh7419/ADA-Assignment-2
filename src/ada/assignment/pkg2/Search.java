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
        System.out.println("Please specify a seed URL: ");
        String url = keyboard.nextLine();
        
        System.out.println("Please specify a keyword: ");
        String keyword = keyboard.nextLine();
        
        System.out.println("Up to what depth would you like to search for?");
        int depth = Integer.parseInt(keyboard.nextLine());
        
        spider(url, keyword, depth);
    }
    
    public Set<String> spider(String url, String keyword, int depth) {
        HTMLink firstUrl = new HTMLink(url, 0);
        unvisitedPages.add(firstUrl);
        
        while (!unvisitedPages.isEmpty()) {
            HTMLink urlToVisit = unvisitedPages.poll();
            
            if(urlToVisit.getDepth() > depth) {
                break;
            }
            
            String meta = spiderleg.getMeta(urlToVisit.getUrl()).toString();
            
            if (meta.toLowerCase().contains(keyword.toLowerCase())) {
                savedUrls.add(urlToVisit.getUrl());
                System.out.println(urlToVisit.getUrl());
            }
            
            List<String> links = spiderleg.getHyperLink(urlToVisit.getUrl());
            for (String link : links) {                
                if (!visitedPages.contains(link) && !link.isEmpty()) {
                    HTMLink linkToAdd = new HTMLink(link, (urlToVisit.getDepth() + 1));
                    unvisitedPages.add(linkToAdd);
                }
            }
            visitedPages.add(urlToVisit);
        } 
        
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
