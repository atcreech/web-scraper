package FinalProject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii; 
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


/**
 * JavaFX App
 * <p>
 * The heart of the program. This class handles the window, containers within the window,
 * and placing the search results into those containers.
 */
public class App extends Application {

    TabPane tabPane = new TabPane();
    int totalCount;
    int maxResultLength;
    int rows;
    final int COUNT_PER_ROW = 4;
    EBaySearch ebaySearch = new EBaySearch();
    CraigslistSearch craigslistSearch = new CraigslistSearch();
    NeweggSearch neweggSearch = new NeweggSearch();
    Tab searchTab;
    TextField searchBox = new TextField();
    int ebayIndex;
    int craigslistIndex;
    int neweggIndex;
    Hyperlink resultTitle;
    Text resultPrice;
    ImageView resultImage;
    Tab mainTab = new Tab("Main Page");
    Hyperlink currentLink;
    CheckBox quickSearchBox = new CheckBox();
    ScrollPane scrollPane = new ScrollPane();
    Font priceFont = Font.font("Courier", 14);
    Font linkFont = Font.font(13);
    Scene scene;
    
    /**
     * Creates the window, initializes components, and creates the initial
     * tab for the first search.
     * 
     * @param stage the window
     */
    @Override
    public void start(Stage stage) {
        
        Group root = new Group();
        
        // scrollPane is used to allow for scrolling through results
        scrollPane.setPannable(true);
        scrollPane.setVmax(5000);
        scrollPane.setVmin(0);
        scrollPane.setVvalue(0);
        scrollPane.setFitToHeight(false);
        
        
        scene = new Scene(root, 1000, 800);
        //scrollPane size is bound to the size of the scene
        scrollPane.prefWidthProperty().bind(scene.widthProperty());
        scrollPane.prefHeightProperty().bind(scene.heightProperty());
        
        mainTab.setClosable(false); // mainTab can't be closed
        VBox mainSearch = new VBox();
        mainSearch.setAlignment(Pos.CENTER);
        mainSearch.setSpacing(5);
        mainSearch.setMinHeight(800);
        mainSearch.setPrefHeight(800);
        
        Text prompt = new Text("Search for an Item");
        Font searchFont = Font.font("Courier", FontWeight.BOLD, FontPosture.REGULAR, 40);
        prompt.setFont(searchFont);
        
        HBox origBox = new HBox();
        origBox.setAlignment(Pos.TOP_CENTER);
        
        // searchBox is used to get the search term
        searchBox.setOnAction(this::processSearch);
        searchBox.setPrefWidth(400);
        searchBox.setAlignment(Pos.CENTER);
        
        origBox.getChildren().add(searchBox);
        
        HBox quickBox = new HBox();
        quickBox.setAlignment(Pos.TOP_CENTER);
        quickBox.setPrefHeight(100);
        
        // Quick Search sacrifices some results to get a faster loading time
        quickSearchBox.setText("Quick Search (Fewer Search Results)");
        quickSearchBox.setSelected(false);
        quickSearchBox.setOnAction(this::processQuickSearch);
        
        quickBox.getChildren().add(quickSearchBox);
        
        mainSearch.getChildren().addAll(prompt, origBox, quickBox);
        mainTab.setContent(mainSearch);
        
        tabPane.getTabs().add(mainTab);
        tabPane.prefWidthProperty().bind(scene.widthProperty());
        
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setContent(tabPane);
        
        root.getChildren().add(scrollPane);
        
        stage.setTitle("CTIS 310 Final Project");
        stage.setScene(scene);
        stage.show();
        
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch();
        
    }
    
    /**
     * Returns the total number of results gathered from EBay, Craigslist, and
     * Newegg.
     * 
     * @param ebaySearch the Object containing the search results from EBay
     * @param craigslistSearch the Object containing the search results from Craigslist
     * @param neweggSearch the Object containing the search results from Newegg
     * @return total number of search results (includes all three websites)
     */
    public int getTotalCount(EBaySearch ebaySearch, CraigslistSearch craigslistSearch, NeweggSearch neweggSearch) {
    
        int count = 0;
        
        for(int i = 0; i < ebaySearch.getItems().size(); i++) { // For each item in ebaySearch
        
            count++;
        
        }
        
        for(int i = 0; i < craigslistSearch.getItems().size(); i++) {   // For each item in craigslistSearch
        
            count++;
        
        }
        
        for(int i = 0; i < neweggSearch.getItems().size(); i++) {   // For each item in neweggSearch
        
            count++;
        
        }
        
        return count;
    
    }
    
    /**
     * Returns the total number of rows needed to display all search results, given
     * a specific number of results per row.
     * 
     * @param totalCount total number of results
     * @param countPerRow number of results per row
     * @return int representing number of rows
     */
    public int getRowCount(int totalCount, int countPerRow) {
    
        return (totalCount / countPerRow) + 1;
    
    }
    
    /**
     * Processes a search, linked to the search box. This method first removes
     * any tabs currently open, then creates a new tab with the search results
     * for the search term. The search term is taken from the search box.
     * 
     * @param event
     */
    public void processSearch(ActionEvent event) {
    
        searchTab = new Tab("Search");
        
        // Setting the search term for each site
        ebaySearch.setSearchTerm(searchBox.getText());
        craigslistSearch.setSearchTerm(searchBox.getText());
        neweggSearch.setSearchTerm(searchBox.getText());
        
        totalCount = getTotalCount(ebaySearch, craigslistSearch, neweggSearch);
        rows = getRowCount(totalCount, COUNT_PER_ROW);
        
        tabPane.getTabs().clear();  // Clears all current tabs
        
        VBox mainBox = new VBox();
        mainBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));    // White background
        mainBox.setSpacing(10);
        mainBox.setAlignment(Pos.TOP_CENTER);
        mainBox.getChildren().add(searchBox);
        mainBox.getChildren().add(quickSearchBox);
        
        // Indexes used for iterating through the items
        ebayIndex = 0;
        craigslistIndex = 0;
        neweggIndex = 0;
        
        for(int i = 0; i < rows; i++) { // For each row
        
            HBox row = new HBox();  // HBox represents a row
            row.setSpacing(50);
            row.setAlignment(Pos.TOP_CENTER);
            
            for(int j = 0; j < COUNT_PER_ROW; j++) {    // For each item in the row
            
                VBox resultBox = new VBox();    // VBox is the individual result in a row
                resultBox.setSpacing(2);
                resultBox.setMaxWidth(100);
                
                // Boolean conditions decide which result should be added at which time
                // Order is: EBay, Craigslist, Newegg, then repeat
                boolean ebayCondition = ebayIndex < ebaySearch.getItems().size() && 
                        ebayIndex <= craigslistIndex && ebayIndex <= neweggIndex;
                
                boolean ebayCondition1 = ebayIndex < ebaySearch.getItems().size() && ebayIndex <= craigslistIndex &&
                        neweggIndex == neweggSearch.getItems().size();
                
                boolean ebayCondition2 = ebayIndex < ebaySearch.getItems().size() &&
                        ebayIndex <= neweggIndex && craigslistIndex == craigslistSearch.getItems().size();
                
                boolean ebayBool = ebayCondition || ebayCondition1 || ebayCondition2;
                
                boolean craigslistCondition = craigslistIndex < craigslistSearch.getItems().size() && craigslistIndex <= neweggIndex;
                
                boolean craigslistCondition1 = craigslistIndex < craigslistSearch.getItems().size() && 
                        neweggIndex == neweggSearch.getItems().size();
                
                boolean craigslistBool = craigslistCondition || craigslistCondition1;
                
                if(ebayBool) {  // Adds the information for an EBay result
                
                    resultTitle = new Hyperlink(ebaySearch.getItems().get(ebayIndex).getTitle());
                    resultTitle.setAlignment(Pos.CENTER);
                    resultPrice = new Text(ebaySearch.getItems().get(ebayIndex).getPrice());
                    resultPrice.setFont(priceFont);
                    resultImage = ebaySearch.getItems().get(ebayIndex).getImageView();
                    
                    resultTitle.setWrapText(true);
                    resultTitle.maxWidth(100);
                    resultTitle.setFont(linkFont);
                    int linkIndex = ebayIndex;
                    resultTitle.setOnAction(new EventHandler<ActionEvent>() {   // Title is set to open a new tab with a browser, link is item's link
                    
                        public void handle(ActionEvent e) {
                            
                            if(!(ebaySearch.getItems().get(linkIndex).getHyperlink().getText().equalsIgnoreCase("None"))) {
                                
                                final WebView browser = new WebView();
                                browser.prefHeightProperty().bind(scene.heightProperty());
                                final WebEngine webEngine = browser.getEngine();
                            
                                Tab browserTab = new Tab();
                                browserTab.setText("EBay");
                            
                                VBox browserBox = new VBox();
        
                                webEngine.load(ebaySearch.getItems().get(linkIndex).getHyperlink().getText());
        
                                browserBox.getChildren().add(browser);
                                browserTab.setContent(browserBox);
        
                                tabPane.getTabs().add(browserTab);
                            
                            }
                        
                        }
                    
                    });
                    
                    resultPrice.setWrappingWidth(100);
                    
                    resultBox.getChildren().addAll(resultImage, resultTitle, resultPrice);
                    
                    ebayIndex++;
                
                } else if(craigslistBool) { // Adds the information for a Craigslist result
                
                    resultTitle = new Hyperlink(craigslistSearch.getItems().get(craigslistIndex).getTitle());
                    resultTitle.setAlignment(Pos.CENTER);
                    resultPrice = new Text(craigslistSearch.getItems().get(craigslistIndex).getPrice());
                    resultPrice.setFont(priceFont);
                    resultImage = craigslistSearch.getItems().get(craigslistIndex).getImageView();
                    
                    resultTitle.setWrapText(true);
                    resultTitle.maxWidth(100);
                    resultTitle.setFont(linkFont);
                    int linkIndex = craigslistIndex;
                    resultTitle.setOnAction(new EventHandler<ActionEvent>() {   // Title is set to open a new tab with a browser, link is item's link
                    
                        public void handle(ActionEvent e) {
                        
                            if(!(craigslistSearch.getItems().get(linkIndex).getHyperlink().getText().equalsIgnoreCase("None"))) {
                            
                                final WebView browser = new WebView();
                                browser.prefHeightProperty().bind(scene.heightProperty());
                                final WebEngine webEngine = browser.getEngine();
                            
                                Tab browserTab = new Tab();
                                browserTab.setText("Craigslist");
                            
                                VBox browserBox = new VBox();
        
                                webEngine.load(craigslistSearch.getItems().get(linkIndex).getHyperlink().getText());
        
                                browserBox.getChildren().add(browser);
                                browserTab.setContent(browserBox);
        
                                tabPane.getTabs().add(browserTab);
                                
                            }
                        
                        }
                        
                    });
                    
                    resultPrice.setWrappingWidth(100);
                    
                    resultBox.getChildren().addAll(resultImage, resultTitle, resultPrice);
                    
                    craigslistIndex++;
                
                } else if(neweggIndex < neweggSearch.getItems().size()) {   // Adds the information for a Newegg result
                
                    resultTitle = new Hyperlink(neweggSearch.getItems().get(neweggIndex).getTitle());
                    resultTitle.setAlignment(Pos.CENTER);
                    resultPrice = new Text(neweggSearch.getItems().get(neweggIndex).getPrice());
                    resultPrice.setFont(priceFont);
                    resultImage = neweggSearch.getItems().get(neweggIndex).getImageView();
                    
                    resultTitle.setWrapText(true);
                    resultTitle.maxWidth(100);
                    resultTitle.setFont(linkFont);
                    int linkIndex = neweggIndex;
                    resultTitle.setOnAction(new EventHandler<ActionEvent>() {   // Title is set to open a new tab with a browser, link is item's link
                    
                        public void handle(ActionEvent e) {
                        
                            if(!(neweggSearch.getItems().get(linkIndex).getHyperlink().getText().equalsIgnoreCase("None"))) {
                            
                                final WebView browser = new WebView();
                                browser.prefHeightProperty().bind(scene.heightProperty());
                                final WebEngine webEngine = browser.getEngine();
                            
                                Tab browserTab = new Tab();
                                browserTab.setText("Newegg");
                            
                                VBox browserBox = new VBox();
        
                                webEngine.load(neweggSearch.getItems().get(linkIndex).getHyperlink().getText());
        
                                browserBox.getChildren().add(browser);
                                browserTab.setContent(browserBox);
        
                                tabPane.getTabs().add(browserTab);
                                
                            }
                        
                        }
                        
                    });
                    
                    resultPrice.setWrappingWidth(100);
                    
                    resultBox.getChildren().addAll(resultImage, resultTitle, resultPrice);
                    
                    neweggIndex++;
                
                }
                
                row.getChildren().add(resultBox);
            
            }
            
            mainBox.getChildren().add(row);
            
        }
        
        searchTab.setContent(mainBox);
        tabPane.getTabs().add(searchTab);
    
    }
    
    /**
     * Sets up the quick search by setting the max number of results grabbed by the
     * search on each website.
     * 
     * @param event
     */
    public void processQuickSearch(ActionEvent event) {
    
        if(quickSearchBox.isSelected()) {   // If checked
        
            ebaySearch.setMaxResults(20);   // Only grabs the first 20 results
            craigslistSearch.setMaxResults(15); //Only grabs the first 15 results
            neweggSearch.setMaxResults(20);     // Only grabs the first 20 results
        
        } else {    // If unchecked, everything is set back to normal
        
            ebaySearch.setMaxResults(1000);
            craigslistSearch.setMaxResults(1000);
            neweggSearch.setMaxResults(1000);
        }
    
    }

}