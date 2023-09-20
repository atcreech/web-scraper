/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalProject;

/**
 * Abstract class representing a search on a website's search results page.
 * 
 * @author Alexander Creech
 */
public abstract class Search {
    
    /**
     *  the url for the website's search results, minus the search term
     */
    protected String url;
    
    /**
     * the url for the website's search results with the search term
     */
    protected String searchUrl;
    
    /**
     * Initializes the search and the url for the website's search results page.
     * 
     * @param url the url for the website's search results, minus the search term
     */
    public Search(String url) {
    
        this.url = url;
    
    }
    
    /**
     * Returns the url linking to the search results page of the website, excluding
     * the search term.
     * 
     * @return url for the website's search results without the search term
     */
    public String getUrl() {
    
        return url;
    
    }
    
    /**
     * Sets the url linking to the search results page of the website, excluding
     * the search term.
     * 
     * @param url the url for the website's search results, minus the search term
     */
    public void setUrl(String url) {
    
        this.url = url;
    
    }
    
    /**
     * Sets the url for finding search results.
     * 
     * @param searchUrl the url used to find search results
     */
    public void setSearchUrl(String searchUrl) {
    
        this.searchUrl = searchUrl;
    
    }
    
    /**
     * Returns the url for finding search results.
     * 
     * @return url for search page with search term
     */
    public String getSearchUrl() {
    
        return searchUrl;
    
    }
    
}
