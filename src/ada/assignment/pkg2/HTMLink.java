/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.assignment.pkg2;

import java.util.LinkedList;
import java.util.List;

/**
 * Class to associate a URL link with an int depth. Also stores parent URLs in
 * case of something.
 *
 * @author Alex
 */
public class HTMLink {

    private String url;
    private List<String> pastURLs;
    private int depth;

    //Default constructor
    public HTMLink(String url, int depth) {
        this.url = url;
        this.depth = depth;
        pastURLs = new LinkedList<>();
    }
    
    //Constructor to inherit parent URLS from a parent URL
    public HTMLink(String url, int depth, List<String> inheritedUrls, String parentUrl) {
        this.url = url;
        this.depth = depth;
        pastURLs = inheritedUrls;
        pastURLs.add(parentUrl);
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public int getDepth() {
        return depth;
    }
    public void setDepth(int depth) {
        this.depth = depth;
    }

    public List<String> getPastURLs() {
        return pastURLs;
    }
    public void setPastURLs(List<String> pastURLs) {
        this.pastURLs = pastURLs;
    }
    
    @Override
    public String toString() {
        return this.url + " Depth: " + this.depth;
    }
    
    public int compareTo(HTMLink other) {
        return this.url.compareTo(other.getUrl());
    }
    
    @Override
    public boolean equals(Object o){
        return this.url.equals(o);
    }
}
