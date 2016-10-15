/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.assignment.pkg2;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Alex
 */
public class SpiderLeg {

    private Document doc;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0";

    public String getTitle(String url) {
        try {
            if (url == null || url.isEmpty()) {
                System.out.println(url + " cannot be read.");
                return "";
            }
            Connection conn = Jsoup.connect(url).userAgent(USER_AGENT).ignoreHttpErrors(true);
            org.jsoup.nodes.Document htmlDoc = conn.get();
            this.doc = htmlDoc;

            return htmlDoc.title();
        } catch (Exception e) {
            System.err.println("Exception: " + e);
        }
        return "";
    }

    public List<String> getHyperLink(String url) {
        ArrayList<String> links = new ArrayList();
        try {
            if (url == null || url.isEmpty()) {
                System.out.println(url + " cannot be read.");
                return links;
            }
            Connection conn = Jsoup.connect(url).userAgent(USER_AGENT).ignoreHttpErrors(true);
            org.jsoup.nodes.Document htmlDoc = conn.get();
            this.doc = htmlDoc;

            for (Element link : htmlDoc.select("a[href]")) {
                links.add(link.absUrl("href"));
            }

        } catch (Exception e) {
            System.err.println("Exception: " + e);
        }
        return links;
    }

    public List<String> getImages(String url) {
        ArrayList<String> images = new ArrayList();
        try {
            if (url == null || url.isEmpty()) {
                System.out.println(url + " cannot be read.");
                return images;
            }
            Connection conn = Jsoup.connect(url).userAgent(USER_AGENT).ignoreHttpErrors(true);
            org.jsoup.nodes.Document htmlDoc = conn.get();
            this.doc = htmlDoc;

            for (Element img : htmlDoc.select("img[src~=(?i)\\.(png|jpe?g|gif)]")) {
                images.add("Source: " + img.attr("src") + " Width: " + img.attr("width") + " Height: " + img.attr("height") + " Alt: " + img.attr("alt"));
            }

        } catch (Exception e) {
            System.err.println("Exception: " + e);
        }
        return images;
    }

    public String getMeta(String url) {
        String meta = "";
        try {
            if (url == null || url.isEmpty()) {
                System.out.println(url + " cannot be read.");
                return "";
            }
            Connection conn = Jsoup.connect(url).userAgent(USER_AGENT).ignoreHttpErrors(true);
            org.jsoup.nodes.Document htmlDoc = conn.get();
            this.doc = htmlDoc;

            String description = doc.select("meta[name=description]").attr("content");
            String keywords = doc.select("meta[name=keywords]").attr("content");

            meta = "Description: " + description + " Keywords: " + keywords;

        } catch (Exception e) {
            System.err.println("Exception: " + e);
        }
        return meta;
    }

    public static void main(String[] args) {
        SpiderLeg sl = new SpiderLeg();

        System.out.println(sl.getTitle("http://www.w3schools.com/"));

        for (String a : sl.getHyperLink("http://www.w3schools.com/")) {
            System.out.println(a);
        }

        for (String a : sl.getImages("http://www.w3schools.com/")) {
            System.out.println(a);
        }

        System.out.println(sl.getMeta("http://www.w3schools.com/"));
    }

}
