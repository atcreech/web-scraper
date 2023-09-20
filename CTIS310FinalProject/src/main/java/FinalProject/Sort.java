/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalProject;

/**
 *
 * @author creec
 */
public class Sort {
    
    private EBaySearch ebaySearch;
    private CraigslistSearch craigslistSearch;
    private NeweggSearch neweggSearch;
    
    public Sort(EBaySearch ebaySearch, CraigslistSearch craigslistSearch, NeweggSearch neweggSearch) {
    
        this.ebaySearch = ebaySearch;
        this.craigslistSearch = craigslistSearch;
        this.neweggSearch = neweggSearch;
    
    }
    
}
