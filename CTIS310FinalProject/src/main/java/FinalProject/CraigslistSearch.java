/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalProject;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Represents a search for a specific term on Craigslist. The results page is
 * scanned for specific information relating to title, price, image, and link
 * to item's individual page, and a CraigslistItem is created for each item in the
 * search results using this information.
 * 
 * @author Alexander Creech
 */
public class CraigslistSearch extends Search{
    
    private Document doc;
    private Elements titles;
    private Elements links;
    private Elements images;
    private Elements prices;
    private ArrayList<CraigslistItem> items;
    private int maxResults;
    
    /**
     * Initializes the search by setting the object's url. Does not include
     * the search term, as that is set later.
     */
    public CraigslistSearch() {
    
        super("https://greensboro.craigslist.org/search/sss?query=");
        maxResults = 1000;
    
    }
    
    /**
     *  Sets the search term for the object. Connects to each website, using
     * its url plus the search term to generate search results. Information from
     * results are grabbed and a CraigslistItem is made for each search result using
     * the information grabbed.
     * 
     * @param searchTerm the term used to search each website
     */
    public void setSearchTerm(String searchTerm) {
    
        super.setSearchUrl(super.getUrl() + searchTerm + "&sort=rel&purveyor-input=all");
        
        items = new ArrayList<CraigslistItem>();
        
        try{
        
            doc = Jsoup.connect(super.getSearchUrl()).get();
            titles = doc.getElementsByClass("result-title");
            links = doc.getElementsByClass("result-title");
            prices = doc.getElementsByClass("result-price");
            
            if(titles.isEmpty()) {  // If no search results are found
            
                CraigslistItem item = new CraigslistItem();
                item.setTitle("No Results Found on Craigslist");
                item.setLink("None");
                item.setPrice("Try another search");
                item.setImageUrl("http://www.drodd.com/images15/red-x21.jpg");
                items.add(item);
                
                return;
            
            }
            
            for(int i = 0; i < Math.min(titles.size(), maxResults); i++) {  // Generates a CraigslistItem for each search result
            
                CraigslistItem item = new CraigslistItem();
                item.setTitle(titles.get(i).text());
                item.setLink(links.get(i).attr("href"));    // Gets all elements with a href attribute, which is used for links
                Document doc1 = Jsoup.connect(links.get(i).attr("href")).get();
                
                if(doc1.getElementsByClass("swipe").size() > 0) {   // If there is an image
                
                    images = doc1.getElementsByClass("swipe").get(0).getElementsByAttribute("src");
                    item.setImageUrl(images.get(0).attr("src"));    // src attribute contains link to image
                    
                } else {    // If there is no image
                
                    item.setImageUrl("https://i2.wp.com/www.coachcarson.com/wp-content/uploads/2017/05/Craigslist-Peace-Sign-Logo.jpg?ssl=1");  // Backup image
                
                }
                
                item.setPrice(prices.get(i).text());
                items.add(item);
            
            }
        
        } catch(IOException e) {
        
            e.printStackTrace();
        
        }
    
    }

    /**
     * Returns an ArrayList containing search results for the search term on
     * Craigslist.
     * 
     * @return ArrayList of type CraigslistItem
     */
    public ArrayList<CraigslistItem> getItems() {
        
        return items;
        
    }
    
    public void setMaxResults(int maxResults) {
    
        this.maxResults = maxResults;
    
    }
    
}
