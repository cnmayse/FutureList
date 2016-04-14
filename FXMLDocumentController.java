/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shopping.list.csc340.project2;

import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Matthew Yengle
 */
public class FXMLDocumentController implements Initializable {
    /**
     * fx:id variable names
     */    
    @FXML
    private TextField AddItemName1,  AddItemName2,  AddItemName3,  AddItemName4, AddPrice1, AddPrice2, AddPrice3,AddPrice4, QueryItemName; 
    @FXML
    private ComboBox AddStoreName1, AddStoreName2,AddStoreName3, AddStoreName4, AddStoreNameAll, AddCategory1, AddCategory2, AddCategory3, AddCategory4, AddCategoryAll, QueryStoreName, QueryCategory;
    @FXML
    private DatePicker AddCalendar, QueryStartDate, QueryEndDate;  
    @FXML
    private VBox AddDateList, AddItemNameList, AddPriceList, AddStoreNameList, AddCategoryList, QueryItemNameList, QueryDateList, QueryPriceList, QueryStoreNameList, QueryCategoryList, QueryDeleteList;
    @FXML
    private Label AddMessageDate, Day1Date, Day1Text, Day2Date, Day2Text, Day3Date, Day3Text, Day4Date, Day4Text, Day5Date, Day5Text, Day6Date, Day6Text, Day7Date, Day7Text, MeanPriceLabel, ModeStoreLabel, TotalPriceLabel, ModeCategoryLabel, ModeItemLabel, TimeFrameLabel, AddPurchasesDateLabel;
    @FXML
    private Pane AddPurchasePane, CreateListPane, HomePane; 
        
    /**
     * Global variables
     */
    private TextField[] AddItemNameArray, AddPriceArray;
    private ComboBox[] AddStoreNameArray, AddCategoryArray;    
    private LocalDate newDate, todayDate;    
    private Purchase newPurchase;
    private PurchaseData purchaseData;
    private PurchaseCategory category;
    private StoreName storeName;
    private ArrayList<Purchase> queryResult;
    private ArrayList<Purchase> todayPurchases;
    private StatisticGenerator sGen;
    private NumberFormat currencyFormat;
    
    /**
     * This method handles when the "Add Items" button is clicked 
     */
    @FXML
    private void handleAddButtonAction(ActionEvent event) {  
        /**
         * Create an array for each type of field for easier access
         */
        AddItemNameArray = new TextField[]{AddItemName1, AddItemName2, AddItemName3, AddItemName4};
        AddPriceArray = new TextField[]{AddPrice1, AddPrice2, AddPrice3, AddPrice4};
        AddStoreNameArray = new ComboBox[]{AddStoreName1, AddStoreName2, AddStoreName3, AddStoreName4};
        AddCategoryArray = new ComboBox[]{AddCategory1, AddCategory2, AddCategory3, AddCategory4};        
        
        /**
         * A purchase needs to have a Item Name at minumum
         * If the purchase does not have an Item Name, continue the loop
         * 
         * A null String can not parse to an int, if else statement is used to detect a null Price value
         */
        for(int i =0; i <4; i++){            
            if(AddItemNameArray[i].getText().equals("") || AddItemNameArray[i] == null){
                continue;
            }
            if(AddStoreNameArray[i].getValue() != null && AddStoreNameArray[i].getValue().toString().equals("")){
                AddStoreNameArray[i].setValue(null);
            }
            if(AddCategoryArray[i].getValue() != null && AddCategoryArray[i].getValue().toString().equals("")){
                AddCategoryArray[i].setValue(null);
            }
            
            //TODO
            //Replace all non numbers or non-decimal points with empty string in Price textfield                  
            
            addNewPurchase(AddItemNameArray[i].getText(), AddPriceArray[i].getText().equals("")? null : Double.parseDouble(AddPriceArray[i].getText()), (StoreName) AddStoreNameArray[i].getValue(), (PurchaseCategory)AddCategoryArray[i].getValue());               
                     
        }        
        /**
         * Populate the entries for the List of Purchases made today and clear the parameters
         */
        AddNewTodayPurchaseList();
        clearFields();
     
    }
    /**
     * Method to create a list based on the users parameters
     * If no value has been set for a parameter, the parameter is sent as null
     * 
     * Clears previous query results in the list
     */
    @FXML
    private void handleGenerateListButtonAction(ActionEvent event){
        queryResult = new ArrayList();
        sGen = new StatisticGenerator();        
        Double totalPrice = 0.0;
        LocalDate startDate;
        LocalDate endDate;
        
        if(QueryStoreName.getValue() != null && QueryStoreName.getValue().toString().equals("")){
                QueryStoreName.setValue(null);
        }
        if(QueryCategory.getValue() != null && QueryCategory.getValue().toString().equals("")){
                QueryCategory.setValue(null);        
        }
        if(QueryItemName.getText() != null && QueryItemName.getText().toString().equals("")){
                QueryItemName.setText(null); 
        }       
        
        queryResult  = purchaseData.query(QueryStartDate.getValue(), QueryEndDate.getValue(), 
                QueryItemName.getText(), 
                null, (StoreName) QueryStoreName.getValue(),
                (PurchaseCategory) QueryCategory.getValue());
        
        if(queryResult.size() > 0){
            //Sort the results based on date
            mergeSortQuery(queryResult);
            
            /**
             * Output info to the user, if applicable
             */
            
            //Find the time span of the query
            startDate = queryResult.get(0).getPurchaseDate();
            endDate = queryResult.get(queryResult.size()-1).getPurchaseDate();
            Period period = endDate.until(startDate.plusDays(1));

            TimeFrameLabel.setText("Time Frame: " + period.getDays() + " day(s)");
            MeanPriceLabel.setText("Mean Price of Purchases: " + currencyFormat.format(sGen.meanPrice(queryResult)));
            //If there are no store names in result query, use empty string in display
            ModeStoreLabel.setText("Most Frequent Store: " + (sGen.modeStore(queryResult) == null? "" : sGen.modeStore(queryResult)));
            //If there are no categories in result query, use empty string in display
            ModeCategoryLabel.setText("Most Frequent Category: "  + (sGen.modeCategory(queryResult) == null? "" : sGen.modeCategory(queryResult)));
            ModeItemLabel.setText("Most Frequent Item: " + sGen.modeItem(queryResult));                   
            TotalPriceLabel.setText("Total Amount Spent: " + currencyFormat.format(sGen.getPurchaseSum()));
        }
        QueryItemNameList.getChildren().clear();
        QueryDateList.getChildren().clear();
        QueryPriceList.getChildren().clear();
        QueryStoreNameList.getChildren().clear();
        QueryCategoryList.getChildren().clear();
        QueryDeleteList.getChildren().clear();
        populateQueryList();        
    }
    
    /**
     * If used, the fifth drop-down box for StoreName and Category will set 
     * all four  drop-down values for their respective parameter to the fifth's value
     */
    @FXML
    private void handleStoreNameAllAction(ActionEvent event){
        AddStoreName1.setValue(AddStoreNameAll.getValue());
        AddStoreName2.setValue(AddStoreNameAll.getValue());
        AddStoreName3.setValue(AddStoreNameAll.getValue());
        AddStoreName4.setValue(AddStoreNameAll.getValue());
    }    
    @FXML
    private void handleCategoryAllAction(ActionEvent event){
        AddCategory1.setValue(AddCategoryAll.getValue());
        AddCategory2.setValue(AddCategoryAll.getValue());
        AddCategory3.setValue(AddCategoryAll.getValue());
        AddCategory4.setValue(AddCategoryAll.getValue());
    }
    
    /**
     * Changes the date, based on their choice in the calender, that the purchases are made on
     * Clear the list for the purchases made for old date
     */
    @FXML
    private void handleAddChangeDateAction(ActionEvent event){
        newDate = AddCalendar.getValue();
        AddMessageDate.setText("Add New Items for " + newDate.getMonth() + " " +  newDate.getDayOfMonth() + ", " + newDate.getYear());
        AddPurchasesDateLabel.setText("All Purchases for " + newDate.getMonth() + " " +  newDate.getDayOfMonth() + ", " + newDate.getYear());
        AddDateList.getChildren().clear();
        AddItemNameList.getChildren().clear();
        AddPriceList.getChildren().clear();
        AddStoreNameList.getChildren().clear();
        AddCategoryList.getChildren().clear();
        
        todayPurchases = purchaseData.query(newDate.minusDays(1), newDate.plusDays(1), null, null, null,null);
        TodayPurchasesList();
    }
    /**
     * Takes the user to the add new purchase screen
     */
    @FXML
    private void handleAddPurchaseMenuAction(ActionEvent event){
        AddPurchasePane.setVisible(true);
        CreateListPane.setVisible(false);
        HomePane.setVisible(false);
    }
    /**
     * Takes the user to the create list screen
     */
    @FXML
    private void handleCreateListMenuAction(ActionEvent event){
        AddPurchasePane.setVisible(false);
        CreateListPane.setVisible(true);
        HomePane.setVisible(false);
    }
    /**
     * Closes the application
     */
    @FXML
    private void handleCloseMenuAction(ActionEvent event){
        Stage stage = (Stage) AddPurchasePane.getScene().getWindow();
        stage.hide();        
    }
    @FXML
    private void handleDeleteSelectedButtonAction(ActionEvent evnet){
        getDeletedPurchases();
    }
    
    /**
     * Starting point for the class
     * Sets the current date
     * Initalizes the values for Store Name and Category
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {                              
        newDate = LocalDate.now();       
        todayDate = LocalDate.now();    
        purchaseData = new PurchaseData();
        currencyFormat = NumberFormat.getCurrencyInstance();
        todayPurchases = purchaseData.query(newDate, newDate, null, null, null, null);
        TodayPurchasesList();
        UpcomingPurchases();        
        AddMessageDate.setText("Add New Items for " + newDate.getMonth() + " " +  newDate.getDayOfMonth() + ", " + newDate.getYear());    
        AddPurchasesDateLabel.setText("All Purchases for " + newDate.getMonth() + " " +  newDate.getDayOfMonth() + ", " + newDate.getYear());
        initAddItems();     
    }    
    /**
     * Method to control the list of upcoming purchases
     */
    public void UpcomingPurchases(){
        Day1Date.setText(todayDate.getMonth().toString() + " " + Integer.toString(todayDate.getDayOfMonth()) +  ", " + Integer.toString(todayDate.getYear()) + " (Today)");
        Day1Text.setText("Milk\nBread\nBananas\nGas");
       
        todayDate = todayDate.plusDays(1);        
        Day2Date.setText(todayDate.getMonth().toString() + " " + Integer.toString(todayDate.getDayOfMonth()) +  ", " + Integer.toString(todayDate.getYear()));        
        Day2Text.setText("Chicken\nBroccoli\nOranges\nT-Shirt");
        
        todayDate = todayDate.plusDays(1);        
        Day3Date.setText(todayDate.getMonth().toString() + " " + Integer.toString(todayDate.getDayOfMonth()) +  ", " + Integer.toString(todayDate.getYear()));
        Day3Text.setText("");
        
        todayDate = todayDate.plusDays(1);        
        Day4Date.setText(todayDate.getMonth().toString() + " " + Integer.toString(todayDate.getDayOfMonth()) +  ", " + Integer.toString(todayDate.getYear()));
        Day4Text.setText("Car Payment\nEggs\nApples");
        
        todayDate = todayDate.plusDays(1);        
        Day5Date.setText(todayDate.getMonth().toString() + " " + Integer.toString(todayDate.getDayOfMonth()) +  ", " + Integer.toString(todayDate.getYear()));
        Day5Text.setText("Steak\nSpaghetti\nRed Sauce");
        
        todayDate = todayDate.plusDays(1);        
        Day6Date.setText(todayDate.getMonth().toString() + " " + Integer.toString(todayDate.getDayOfMonth()) +  ", " + Integer.toString(todayDate.getYear()));
        Day6Text.setText("Candy");
        
        todayDate = todayDate.plusDays(1);        
        Day7Date.setText(todayDate.getMonth().toString() + " " + Integer.toString(todayDate.getDayOfMonth()) +  ", " + Integer.toString(todayDate.getYear()));
        todayDate = todayDate.now();
        Day7Text.setText("Gas\nMilk");
    }
    
    /**
     * Initializes the values for Store Name and Category
     */
    public void initAddItems(){       
        AddCategory1.getItems().addAll("", category.AUTOMOTIVE, category.CLOTHING, category.FOOD, category.ETC_DAILY, category.ETC_WEEKLY, category.ETC_MONTHLY, category.ETC_YEARLY, category.HOLIDAY,
        category.LEISURE, category.TRAVEL);
        AddCategory2.getItems().addAll("", category.AUTOMOTIVE, category.CLOTHING, category.FOOD, category.ETC_DAILY, category.ETC_WEEKLY, category.ETC_MONTHLY, category.ETC_YEARLY, category.HOLIDAY,
        category.LEISURE, category.TRAVEL);
        AddCategory3.getItems().addAll("", category.AUTOMOTIVE, category.CLOTHING, category.FOOD, category.ETC_DAILY, category.ETC_WEEKLY, category.ETC_MONTHLY, category.ETC_YEARLY, category.HOLIDAY,
        category.LEISURE, category.TRAVEL);
        AddCategory4.getItems().addAll("", category.AUTOMOTIVE, category.CLOTHING, category.FOOD, category.ETC_DAILY, category.ETC_WEEKLY, category.ETC_MONTHLY, category.ETC_YEARLY, category.HOLIDAY,
        category.LEISURE, category.TRAVEL);
        AddCategoryAll.getItems().addAll("", category.AUTOMOTIVE, category.CLOTHING, category.FOOD, category.ETC_DAILY, category.ETC_WEEKLY, category.ETC_MONTHLY, category.ETC_YEARLY, category.HOLIDAY,
        category.LEISURE, category.TRAVEL);
        QueryCategory.getItems().addAll("", category.AUTOMOTIVE, category.CLOTHING, category.FOOD, category.ETC_DAILY, category.ETC_WEEKLY, category.ETC_MONTHLY, category.ETC_YEARLY, category.HOLIDAY,
        category.LEISURE, category.TRAVEL);
        
        AddStoreName1.getItems().addAll("", storeName.AMAZON, storeName.HARRIS_TEETER, storeName.SHEETZ, storeName.TARGET, storeName.WALMART);
        AddStoreName2.getItems().addAll("", storeName.AMAZON, storeName.HARRIS_TEETER, storeName.SHEETZ, storeName.TARGET, storeName.WALMART);
        AddStoreName3.getItems().addAll("", storeName.AMAZON, storeName.HARRIS_TEETER, storeName.SHEETZ, storeName.TARGET, storeName.WALMART);
        AddStoreName4.getItems().addAll("", storeName.AMAZON, storeName.HARRIS_TEETER, storeName.SHEETZ, storeName.TARGET, storeName.WALMART);
        AddStoreNameAll.getItems().addAll("", storeName.AMAZON, storeName.HARRIS_TEETER, storeName.SHEETZ, storeName.TARGET, storeName.WALMART);        
        QueryStoreName.getItems().addAll("", storeName.AMAZON, storeName.HARRIS_TEETER, storeName.SHEETZ, storeName.TARGET, storeName.WALMART);
    }
    
    /**
     * Method to add a new Purchase
     * 
     * @param name
     * @param price
     * @param store
     * @param cat 
     */
    public void addNewPurchase(String name, Double price, StoreName store, PurchaseCategory cat){           
        newPurchase = new Purchase(name, newDate, price, store, cat);
        purchaseData.addPurchase(newPurchase);
    }
    
    private void TodayPurchasesList(){
        AddDateList.getChildren().clear();
        AddItemNameList.getChildren().clear();
        AddPriceList.getChildren().clear();
        AddStoreNameList.getChildren().clear();
        AddCategoryList.getChildren().clear();
        
        for(int i =0; i < todayPurchases.size(); i ++){
            AddDateList.getChildren().add(new Label(todayPurchases.get(i).getPurchaseDate().toString()));
            AddItemNameList.getChildren().add(new Label(todayPurchases.get(i).getItemName().toString()));            
            if(todayPurchases.get(i).getItemPrice() == null){
                AddPriceList.getChildren().add(new Label(""));
            }else{
                AddPriceList.getChildren().add(new Label(todayPurchases.get(i).getItemPrice().toString()));
            }
            
            if(todayPurchases.get(i).getStoreName() == null){
                AddStoreNameList.getChildren().add(new Label(""));  
            }else{
                AddStoreNameList.getChildren().add(new Label(todayPurchases.get(i).getStoreName().toString()));                
            }
            
            if(todayPurchases.get(i).getPurchaseCategory() == null){
                AddCategoryList.getChildren().add(new Label(""));
            }else{
                AddCategoryList.getChildren().add(new Label(todayPurchases.get(i).getPurchaseCategory().toString()));                
            }
        }
    }
    
    /**
     * Updates the list that keep track of the purchases made today in a top down fashion
     */
    private void AddNewTodayPurchaseList() {
        VBox tempBox = new VBox();
        
        for(int i = 0; i < 4; i++){
            /**
             * If the purchase does not have a name, do not add to list
             */
            if(AddItemNameArray[i].getText().equals(""))
                continue;
                      
            tempBox.getChildren().addAll(AddDateList.getChildren());
            AddDateList.getChildren().clear();
            AddDateList.getChildren().addAll(new Label(newDate.toString()));             
            AddDateList.getChildren().addAll(tempBox.getChildren());        
            tempBox.getChildren().clear();
                          
            tempBox.getChildren().addAll(AddItemNameList.getChildren());
            AddItemNameList.getChildren().clear();
            AddItemNameList.getChildren().addAll(new Label(AddItemNameArray[i].getText()));
            AddItemNameList.getChildren().addAll(tempBox.getChildren());        
            tempBox.getChildren().clear();
            
            
            tempBox.getChildren().addAll(AddPriceList.getChildren());
            AddPriceList.getChildren().clear();
            if(AddPriceArray[i].getText() == null){
                AddPriceList.getChildren().addAll(new Label(""));
                
            }else{
                AddPriceList.getChildren().addAll(new Label(currencyFormat.format(Double.parseDouble(AddPriceArray[i].getText()))));
            }
            AddPriceList.getChildren().addAll(tempBox.getChildren());        
            tempBox.getChildren().clear();         
           
            tempBox.getChildren().addAll(AddStoreNameList.getChildren());
            AddStoreNameList.getChildren().clear();
            if(AddStoreNameArray[i] == (null) || AddStoreNameArray[i].getValue() == (null)){   
                 AddStoreNameList.getChildren().addAll(new Label(""));      
            }else{
                 AddStoreNameList.getChildren().addAll(new Label(AddStoreNameArray[i].getValue().toString()));
            }
            AddStoreNameList.getChildren().addAll(tempBox.getChildren());        
            tempBox.getChildren().clear();   
            
            tempBox.getChildren().addAll(AddCategoryList.getChildren());
            AddCategoryList.getChildren().clear();            
            if(AddCategoryArray[i] == (null) || AddCategoryArray[i].getValue() == (null)){      
                 AddCategoryList.getChildren().addAll(new Label(""));      
            }else{
                AddCategoryList.getChildren().addAll(new Label(AddCategoryArray[i].getValue().toString()));
            }
            AddCategoryList.getChildren().addAll(tempBox.getChildren());        
            tempBox.getChildren().clear();           
        }   
    }
    
    /**
     * Clears all the parameters when the Add Items button has been clicked
     */
    private void clearFields(){
        for(int i =0; i < 4; i++){
            AddItemNameArray[i].setText("");
            AddPriceArray[i].setText("");
            AddStoreNameArray[i].setValue(null);
            AddCategoryArray[i].setValue(null);
        }
        AddStoreNameAll.setValue(null);
        AddCategoryAll.setValue(null);
    }
    
    /**
     * Merge sort to sort the results of the query by date
     * Most recent date appears first in the list
     */
    private ArrayList<Purchase> mergeSortQuery(ArrayList<Purchase> list){
        ArrayList<Purchase> left = new ArrayList<Purchase>();
        ArrayList<Purchase> right = new ArrayList<Purchase>();
        int center;
        
        if(list.size() <= 1)
            return list;
        else{
            center = list.size()/2;
            for(int i =0; i < center; i ++){
                left.add(list.get(i));
            }
            for(int i=center; i <list.size(); i++){
                right.add(list.get(i));
            }
            left = mergeSortQuery(left);
            right = mergeSortQuery(right);
            
            mergeQuery(left, right, list);        
        }
        return list;  
    }
    
    private void mergeQuery(ArrayList<Purchase> left, ArrayList<Purchase> right, ArrayList<Purchase> list){
        int leftIndex = 0;
        int rightIndex = 0;
        int listIndex = 0;
        
        while(leftIndex < left.size() && rightIndex < right.size()){
            if((left.get(leftIndex).getPurchaseDate()).isAfter(right.get(rightIndex).getPurchaseDate())){
                list.set(listIndex, left.get(leftIndex));
                leftIndex++;
            }
            else{
                list.set(listIndex, right.get(rightIndex));
                rightIndex++;
            }
            listIndex++;
        }
        if(leftIndex >= left.size()){
            for(int i = rightIndex; i < right.size(); i++){
                list.set(listIndex, right.get(i));
                listIndex++;
            }
        }
        else{
            for(int i = leftIndex; i < left.size(); i++){
                list.set(listIndex, left.get(i));
                listIndex++;
            }
        }
    }
    
    /**
     * Populates the list for the create a list query
     */
    private void populateQueryList() {
                
        QueryDeleteList.getChildren().clear();
        QueryDateList.getChildren().clear();
        QueryItemNameList.getChildren().clear();
        QueryPriceList.getChildren().clear();
        QueryStoreNameList.getChildren().clear();
        QueryCategoryList.getChildren().clear();
        
        for(int i =0; i < queryResult.size(); i ++){
            QueryDeleteList.getChildren().add(new CheckBox());
            QueryDateList.getChildren().add(new Label(queryResult.get(i).getPurchaseDate().toString()));
            QueryItemNameList.getChildren().add(new Label(queryResult.get(i).getItemName().toString()));            
            if(queryResult.get(i).getItemPrice() == null){
                QueryPriceList.getChildren().add(new Label(""));
            }else{
                QueryPriceList.getChildren().add(new Label((currencyFormat.format(queryResult.get(i).getItemPrice()).toString())));
            }
            
            if(queryResult.get(i).getStoreName() == null){
                QueryStoreNameList.getChildren().add(new Label(""));  
            }else{
                QueryStoreNameList.getChildren().add(new Label(queryResult.get(i).getStoreName().toString()));                
            }
            
            if(queryResult.get(i).getPurchaseCategory() == null){
                QueryCategoryList.getChildren().add(new Label(""));
            }else{
                QueryCategoryList.getChildren().add(new Label(queryResult.get(i).getPurchaseCategory().toString()));                
            }
        }
    }
    
    private void getDeletedPurchases(){
        List<Node> deletePurchases = new ArrayList<Node>();
        ObservableList<Node> observableList = FXCollections.observableList(deletePurchases);
        observableList.addAll(QueryDeleteList.getChildren());            
        
        for(int i =0; i < observableList.size(); i++){
            if(((CheckBox)observableList.get(i)).isSelected()){
                purchaseData.deletePurchase((Purchase)queryResult.get(i));
                queryResult.remove(i);
                queryResult.add(i, null);
            }
        }
        
        while(queryResult.contains(null)){
            queryResult.remove(null);
        }
        
        populateQueryList();
    }
}
