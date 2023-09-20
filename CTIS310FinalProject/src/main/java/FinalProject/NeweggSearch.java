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
 * Represents a search for a specific term on Newegg. The results page is
 * scanned for specific information relating to title, price, image, and link
 * to item's individual page, and a NeweggItem is created for each item in the
 * search results using this information.
 * 
 * @author Alexander Creech
 */
public class NeweggSearch extends Search{
    
    private Document doc;
    private Elements titles;
    private Elements links;
    private Elements images;
    private Elements prices;
    private ArrayList<NeweggItem> items;
    private int maxResults;
    
    /**
     * Initializes the search by setting the object's url. Does not include
     * the search term, as that is set later.
     */
    public NeweggSearch() {
    
        super("https://www.newegg.com/p/pl?d=");
        maxResults = 1000;
    
    }
    
    /**
     *  Sets the search term for the object. Connects to each website, using
     * its url plus the search term to generate search results. Information from
     * results are grabbed and a NeweggItem is made for each search result using
     * the information grabbed.
     * 
     * @param searchTerm the term used to search each website
     */
    public void setSearchTerm(String searchTerm) {
    
        super.setSearchUrl(super.getUrl() + searchTerm);
        
        items = new ArrayList<NeweggItem>();
        
        try{
        
            doc = Jsoup.connect(super.getSearchUrl()).get();
            Elements doc1 = doc.getElementsByClass("list-wrap");
            
            if(doc1.isEmpty()) {    // If Newegg has no results for search
            
                NeweggItem item = new NeweggItem();
                item.setTitle("No Results Found on Newegg");
                item.setLink("None");
                item.setPrice("Try another search");
                item.setImageUrl("http://www.drodd.com/images15/red-x21.jpg");
                items.add(item);
                        
                return;
            
            }
            
            titles = doc1.get(0).getElementsByClass("item-title");
            links = doc1.get(0).getElementsByClass("item-title");
            images = doc1.get(0).getElementsByTag("img");   // img tag is used for images
            prices = doc1.get(0).getElementsByClass("price-current");
            
            for(int i = 0; i < Math.min(titles.size(), maxResults); i++) {  // Generates a NeweggItem for each search result
            
                NeweggItem item = new NeweggItem();
                item.setTitle(titles.get(i).text());
                item.setLink(links.get(i).attr("href"));    // href attribute is used for links
                item.setImageUrl(images.get(i).attr("src"));    // src attribute contains link to image
                
                if(prices.get(i).text().length() > 0) { // Sets price if price is on website, otherwise sets it to Price Not Available
                
                    item.setPrice(prices.get(i).text());
                    
                } else {
                
                    item.setPrice("Price Not Available");
                
                }
                
                items.add(item);    // Item is added to the list of items
            
            }
        
        } catch(IOException e) {
        
            e.printStackTrace();
        
        }
    
    }

    /**
     * Returns an ArrayList containing search results for the search term on
     * Newegg.
     * 
     * @return ArrayList of type NeweggItem
     */
    public ArrayList<NeweggItem> getItems() {
        
        return items;
        
    }
    
    public void setMaxResults(int maxResults) {
    
        this.maxResults = maxResults;
    
    }
    
}
