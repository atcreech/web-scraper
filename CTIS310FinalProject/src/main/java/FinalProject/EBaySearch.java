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
 * Represents a search for a specific term on EBay. The results page is
 * scanned for specific information relating to title, price, image, and link
 * to item's individual page, and a EBayItem is created for each item in the
 * search results using this information.
 * 
 * @author Alexander Creech
 */
public class EBaySearch extends Search{
    
    private Document doc;
    private Elements titles;
    private Elements links;
    private Elements images;
    private Elements prices;
    private ArrayList<EBayItem> items;
    private int maxResults;
    
    /**
     * Initializes the search by setting the object's url. Does not include
     * the search term, as that is set later.
     */
    public EBaySearch() {
    
        super("https://www.ebay.com/sch/i.html?_from=R40&_trksid=p2380057.m570.l1313&_nkw=");
        maxResults = 1000;
    
    }
    
    /**
     *  Sets the search term for the object. Connects to each website, using
     * its url plus the search term to generate search results. Information from
     * results are grabbed and a EBayItem is made for each search result using
     * the information grabbed.
     * 
     * @param searchTerm the term used to search each website
     */
    public void setSearchTerm(String searchTerm) {
    
        super.setSearchUrl(super.getUrl() + searchTerm + "&_sacat=0");
        
        items = new ArrayList<EBayItem>();
        
        try{
        
            doc = Jsoup.connect(super.getSearchUrl()).get();
            titles = doc.getElementsByClass("s-item__title");
            links = doc.getElementsByClass("s-item__link");
            images = doc.getElementsByClass("s-item__image-img");
            prices = doc.getElementsByClass("s-item__price");
            
            if(titles.isEmpty()) {  // If there are no search results on EBay
            
                EBayItem item = new EBayItem();
                item.setTitle("No Results Found on EBay");
                item.setLink("None");
                item.setPrice("Try another search");
                item.setImageUrl("http://www.drodd.com/images15/red-x21.jpg");
                items.add(item);
                
                return;
            
            }
            
            for(int i = 0; i < Math.min(titles.size(), maxResults); i++) {  // Generates an EBayItem for search results
            
                EBayItem item = new EBayItem();
                item.setTitle(titles.get(i).text());
                item.setLink(links.get(i).attr("href"));    // href attribute is used for links
                item.setImageUrl(images.get(i).attr("src"));    // src attribute contains link for image source
                item.setPrice(prices.get(i).text());
                items.add(item);
            
            }
        
        } catch(IOException e) {
        
            e.printStackTrace();
        
        }
    
    }

    /**
     * Returns an ArrayList containing search results for the search term on
     * EBay.
     * 
     * @return ArrayList of type EBayItem
     */
    public ArrayList<EBayItem> getItems() {
        
        return items;
        
    }
    
    public void setMaxResults(int maxResults) {
    
        this.maxResults = maxResults;
    
    }
    
}
