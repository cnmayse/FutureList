package shopping.list.csc340.project2;
/**
 * This class is used to represent a purchase.
 * 
 * I/We have abided by UNCG's academic integrity policy. This is my/our own 
 * work.
 * @author Charles Mayse.
 * @version Purchase v1.0.0
 */

import java.time.LocalDate;
import java.util.Date;

public class Purchase {
    //Fields
     private String itemName;
     private LocalDate purchaseDate;
     private Double itemPrice;
     private StoreName storeName;
     private PurchaseCategory purchaseCategory;
     
     //Constructors
     //NOTE: only the full field constructor will be implemented to enforce
     //instatiating purchase objects with non-null fields. However, it is 
     //up to the discretion of the calling code to pass null values.
     
     /**
      * Full field constructor.
      * @param s the item name (string)
      * @param d the purchase date (Date)
      * @param ip the item price (double)
      * @param sn the store name (StoreName)
      * @param pc the purchase category (PurchaseCategory)
      */
     public Purchase(String s, LocalDate d, Double ip, StoreName sn, PurchaseCategory pc){
         this.itemName = s;
         this.purchaseDate = d;
         this.itemPrice = ip;
         this.storeName = sn;
         this.purchaseCategory = pc;
     }
     
     //Getters and setters
     
     public String getItemName(){
         return this.itemName;
     }
     
     public LocalDate getPurchaseDate(){
         return this.purchaseDate;
     }
     
     public double getItemPrice(){
         return this.itemPrice;
     }
     
     public StoreName getStoreName(){
         return this.storeName;
     }
     
     public PurchaseCategory getPurchaseCategory(){
         return this.purchaseCategory;
     }
     
     public void setItemName(String name){
         this.itemName = name;
     }
     
     public void setPurchaseDate(LocalDate date){
         this.purchaseDate = date;
     }
     
     public void setItemPrice(double price){
         this.itemPrice = price;
     }
     
     public void setStoreName(StoreName store){
         this.storeName = store;
     }
     
     public void setPurchaseCategory(PurchaseCategory category){
         this.purchaseCategory = category;
     }
}
