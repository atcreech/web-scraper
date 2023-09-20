/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalProject;

import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents an individual search result on EBay. The name, price, image, and link to
 * the item are used to create an object with all the necessary information to
 * represent its counterpart on EBay.
 * 
 * @author Alexander Creech
 */
public class EBayItem {
    
    private String title;
    private String link;
    private String imageUrl;
    private String price;
    private ImageView imageView;
    private Image image;
    private Hyperlink hyperlink;
    
    /**
    * Initializes the Hyperlink for the item
    */
    public EBayItem() {
        
        hyperlink = new Hyperlink();
        
    }

    /**
     * Returns the title of the item.
     * 
     * @return the name of the item on EBay
     */
    public String getTitle() {
        
        return title;
        
    }

    /**
     * Sets the title of the item.
     * 
     * @param title the name of the item on EBay
     */
    public void setTitle(String title) {
        
        this.title = title;
        
    }

    /**
     * Returns the url for the item's individual page on EBay.
     * 
     * @return the url representing the item's page on EBay
     */
    public String getLink() {
        
        return link;
        
    }

    /**
     * Sets the url for the item's individual page on EBay. The link parameter
     * is then used to create a hyperlink.
     * 
     * @param link the url representing the item's page on EBay
     */
    public void setLink(String link) {
        
        this.link = link;
        hyperlink.setText(link);
        
    }

    /**
     * Returns the url for the location of the item's image on EBay.
     * 
     * @return  the url of the item's image
     */
    public String getImageUrl() {
        
        return imageUrl;
        
    }

    /**
     * Sets the url for the item's Image, then makes an ImageView object
     * to display the image. All images are set to be 150 pixels wide.
     * 
     * @param imageUrl the url representing the location of the item's image on EBay
     */
    public void setImageUrl(String imageUrl) {
        
        this.imageUrl = imageUrl;
        image = new Image(imageUrl);
        imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(150);
        
    }

    /**
     * Returns the price of the item on EBay.
     * 
     * @return price of the item.
     */
    public String getPrice() {
        
        return price;
        
    }

    /**
     * Sets the price of the item.
     * 
     * @param price cost of the item on EBay
     */
    public void setPrice(String price) {
        
        this.price = price;
        
    }

    /**
     * Returns the ImageView object for the item, containing its Image.
     * 
     * @return ImageView object for the item
     */
    public ImageView getImageView() {
        
        return imageView;
        
    }

    /**
     * Returns the Hyperlink that links to the item's specific page on EBay.
     * 
     * @return the Hyperlink of the item
     */
    public Hyperlink getHyperlink() {
        
        return hyperlink;
        
    }
    
}
