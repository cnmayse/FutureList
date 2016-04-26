package ShoppingListPackage;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Abstraction for holding and querying for purchases.
 *
 * I have abided by UNCG's academic integrity policy. This is my own work.
 *
 * @author Charles Mayse
 * @version PurchaseData v1.0.0
 */
public class PurchaseData {

    /**
     * Summary of data structure: each field of a purchase object.toString()
     * will be used as a key in individual hashmaps. The key will be associated
     * with a purchase object.
     */
    private HashMap itemNameHashMap;
    private HashMap dateHashMap;
    private HashMap itemPriceHashMap;
    private HashMap storeNameHashMap;
    private HashMap categoryHashMap;

    /**
     * This is the actual hashmap that contains all of the purchases, keyed by
     * object.toString() associated with the actual object. This is so that we
     * can query about different fields about a purchase
     */
    private HashMap purchaseHashMap;
    
  
    /**
     * This main() will primarily be used for testing purposes. Delete if 
     * necessary
     */
   /*   public static void main(String[] args) {
        
        Purchase p1 = new Purchase("Tshirt", LocalDate.now(), 4.35, StoreName.WALMART, PurchaseCategory.CLOTHING);
        
        PurchaseData purchaseData = new PurchaseData();
        
        purchaseData.addPurchase(p1);
        
        Purchase p2 = new Purchase("Water", LocalDate.now(), 1.29, StoreName.HARRIS_TEETER, PurchaseCategory.FOOD);
        
        purchaseData.addPurchase(p2);
        
        Purchase p3 = new Purchase("Tires", LocalDate.now(), 649.00, StoreName.WALMART, PurchaseCategory.FOOD);
        
        purchaseData.addPurchase(p3);
        
        System.out.println("Number of items in purchase data");
        
        ArrayList result = purchaseData.query(LocalDate.of(2012, Month.MARCH, 1), null, null, null, null, null);
        
        System.out.println(result.size());
        
        System.out.println("Items in purchase data" + "\n");
        
        
        for(int i = 0; i<result.size(); i++){
            String line = "";
           line+=(((Purchase)result.get(i)).getItemName()) + ",";
           line+=(((Purchase)result.get(i)).getItemPrice()) + ",";
           line+=(((Purchase)result.get(i)).getPurchaseDate() + ",");
           line+=(((Purchase)result.get(i)).getStoreName() + ",");
           line+=(((Purchase)result.get(i)).getPurchaseCategory() + "\n");
            System.out.print(line);
        }
        
        result = purchaseData.query(LocalDate.of(2012, Month.MARCH, 1), null, null, null, StoreName.HARRIS_TEETER, null);
        
        System.out.println("\nItems from Harris Teeter");
        
        for(int i = 0; i<result.size(); i++){
            String line = "";
           line+=(((Purchase)result.get(i)).getItemName()) + ",";
           line+=(((Purchase)result.get(i)).getItemPrice()) + ",";
           line+=(((Purchase)result.get(i)).getPurchaseDate() + ",");
           line+=(((Purchase)result.get(i)).getStoreName() + ",");
           line+=(((Purchase)result.get(i)).getPurchaseCategory() + "\n");
            System.out.print(line);
        }
        
        System.out.println("\nPurchase toStrings()");
        System.out.println("p1 " + p1.toString());
        System.out.println("p2 " + p2.toString());
        System.out.println("p3 " + p3.toString());
        
        
    }*/

    //Default constructor
    public PurchaseData() {
        itemNameHashMap = new HashMap<String, ArrayList>();
        dateHashMap = new HashMap<LocalDate, ArrayList>();
        itemPriceHashMap = new HashMap<Double, ArrayList>();
        storeNameHashMap = new HashMap<StoreName, ArrayList>();
        categoryHashMap = new HashMap<PurchaseCategory, ArrayList>();
        purchaseHashMap = new HashMap<String, Purchase>();
    }

    /**
     * Adds a purchase to the data collection.
     *
     * @param p The purchase to add
     */
    public void addPurchase(Purchase p) {

        //Hash purchase names
        if (p.getItemName() != null && itemNameHashMap.containsKey(p.getItemName())) {
            ((ArrayList) (itemNameHashMap.get(p.getItemName()))).add(p.toString());
        } else if (p.getItemName() != null) {
            itemNameHashMap.put(p.getItemName(), new ArrayList<Purchase>());
            ((ArrayList) (itemNameHashMap.get(p.getItemName()))).add(p.toString());
        }

        //Hash purchase dates
        if (p.getPurchaseDate() != null && dateHashMap.containsKey(p.getPurchaseDate())) {
            ((ArrayList) (dateHashMap.get(p.getPurchaseDate()))).add(p.toString());
        } else if (p.getPurchaseDate() != null) {
            dateHashMap.put(p.getPurchaseDate(), new ArrayList<Purchase>());
            ((ArrayList) (dateHashMap.get(p.getPurchaseDate()))).add(p.toString());
        }

        //Hash item prices
        if (itemPriceHashMap.containsKey(p.getItemPrice())) {
            ((ArrayList) (itemPriceHashMap.get(p.getItemPrice()))).add(p.toString());
        } else {
            itemPriceHashMap.put(p.getItemPrice(), new ArrayList<Purchase>());
            ((ArrayList) (itemPriceHashMap.get(p.getItemPrice()))).add(p.toString());
        }

        //Hash store names
        if (p.getStoreName() != null && storeNameHashMap.containsKey(p.getStoreName())) {
            ((ArrayList) (storeNameHashMap.get(p.getStoreName()))).add(p.toString());
        } else if (p.getStoreName() != null) {
            storeNameHashMap.put(p.getStoreName(), new ArrayList<Purchase>());
            ((ArrayList) (storeNameHashMap.get(p.getStoreName()))).add(p.toString());
        }

        //Hash categories
        if (p.getPurchaseCategory() != null && categoryHashMap.containsKey(p.getPurchaseCategory())) {
            ((ArrayList) (categoryHashMap.get(p.getPurchaseCategory()))).add(p.toString());
        } else if (p.getItemName() != null) {
            categoryHashMap.put(p.getPurchaseCategory(), new ArrayList<Purchase>());
            ((ArrayList) (categoryHashMap.get(p.getPurchaseCategory()))).add(p.toString());
        }

        //Hash the purchase objects
        purchaseHashMap.put(p.toString(), p);
    }

    /**
     * Queries the data collection based on inputs from the gui.
     * If return is of 0 size, then there aren't any results
     *
     * @param startBound Staring date
     * @param endBound Ending date
     * @param qName Item Name
     * @param price item price
     * @param qStore Store Name
     * @param qCategory Category
     * @return the list of purchases that fulfill the query requirements
     */
    public ArrayList query(LocalDate startBound, LocalDate endBound, String qName, Double price, StoreName qStore, PurchaseCategory qCategory) {
        ArrayList queryResults = new ArrayList<Purchase>();
        
        /**
         * If no parameters specified, return all purchases
         */
        if(startBound == null && endBound == null && qName == null && price == null && qStore == null && qCategory == null){
            queryResults.addAll((purchaseHashMap.values()));
            return queryResults;
        }
        
        
        //Date subquery
        //TODO: THE PROBLEM RESIDES IN THE FACT THAT WITHIN A BOUND, EACH PROSPECTIVE DATE MUST ADD, NOT REPLACE
        //<editor-fold>
        //ArrayList to hold all of the keys for the purchaseHashMap
        ArrayList datePurchaseKeyList = new ArrayList<>();
        //startBound and endBound sub queries, does not execute if both bounds are not defined
        if (startBound != null || endBound != null) {

            //get the entry set of the dateHashMap
            Set dateEntrySet = dateHashMap.entrySet();
            //iterator to iterate through the entrySet
            Iterator iterator = dateEntrySet.iterator();

            //Iterate through the set
            while (iterator.hasNext()) {
                Map.Entry dateEntry = (Map.Entry) iterator.next();

                //Get the date
                LocalDate dateKey = (LocalDate) dateEntry.getKey();

                //perform the start and end bound query, add matches 
                if (startBound != null && endBound != null) {
                    if ((dateKey.isAfter(startBound) || dateKey.isEqual(startBound)) && (dateKey.isEqual(endBound) || dateKey.isBefore(endBound))) {
                        datePurchaseKeyList.addAll((ArrayList) dateEntry.getValue());
                        continue;
                    }
                } //perform the start bound only query
                else if (startBound != null && endBound == null) {
                    if (dateKey.isAfter(startBound) || dateKey.isEqual(startBound)) {
                        datePurchaseKeyList.addAll((ArrayList) dateEntry.getValue());
                        continue;
                    }
                } //perform the end bound only query{
                else if (endBound != null && startBound == null) {
                    if (dateKey.isBefore(endBound) || dateKey.isEqual(endBound)) {
                        datePurchaseKeyList.addAll((ArrayList) dateEntry.getValue());
                        continue;
                    }
                }
            }

        }
        //</editor-fold>

        //Name subquery
        //<editor-fold>
        //Array list to hold the keys for purchaseHashMap
        ArrayList itemNameKeyList = null;
        //name subquery, does not execute if the parameter is not defined
        if (qName != null) {
            if (itemNameHashMap.containsKey(qName)) {
                System.out.println("CONTAINS");
                itemNameKeyList = (ArrayList) itemNameHashMap.get(qName);
            }
        }
        //</editor-fold>

        //Price subquery
        //TODO: Maybe this might need to be a bounded search?
        //<editor-fold>
        ArrayList priceKeyList = null;
        //price subquery, does not execute if the parameter is not defined
        if (price != null) {
            if (itemPriceHashMap.containsKey(price)) {
                priceKeyList = (ArrayList) itemPriceHashMap.get(price);
            }
        }
        //</editor-fold>

        //Store name subquery
        //<editor-fold>
        ArrayList storeKeyList = null;
        //price subquery, does not execute if the parameter is not defined
        if (qStore != null) {
            if (storeNameHashMap.containsKey(qStore)) {
                storeKeyList = (ArrayList) storeNameHashMap.get(qStore);
            }
        }
        //</editor-fold>

        //Category subquery
        //<editor-fold>
        ArrayList categoryKeyList = null;
        //category subquery, does not execute if the parameter is not defined
        if (qCategory != null) {
            if (categoryHashMap.containsKey(qCategory)) {
                categoryKeyList = (ArrayList) categoryHashMap.get(qCategory);
            }
        }
        //</editor-fold>

        //After all of the subqueries, they all must be compared to see
        //all of the matching purchase keys
        //ArrayList to hold all of the subqueries that are not null
        ArrayList purchaseKeySubList = new ArrayList<ArrayList>();
        //ArrayList to hold all of the purchase keys that match all subqueries
        ArrayList purchaseKeyList = new ArrayList<String>();

        //start by find the smallest length list
        if (datePurchaseKeyList != null) {
            purchaseKeySubList.add(datePurchaseKeyList);
        }
        if (itemNameKeyList != null) {
            purchaseKeySubList.add(itemNameKeyList);
        }
        if (priceKeyList != null) {
            purchaseKeySubList.add(priceKeyList);
        }
        if (categoryKeyList != null) {
            purchaseKeySubList.add(categoryKeyList);
        }
        if (storeKeyList != null) {
            purchaseKeySubList.add(storeKeyList);
        }       

        //Comparator class for ArrayList sort
        class KeyListComparator implements Comparator<ArrayList> {

            @Override
            public int compare(ArrayList o1, ArrayList o2) {
                if (o1.size() > o2.size()) {
                    return 1;
                } else {
                    return (-1);
                }
            }

        }
        //the sorted list, descending
        purchaseKeySubList.sort(new KeyListComparator());
        
        //get the purchaseKeySublist size
        int purchaseKeySubListSize = purchaseKeySubList.size();
        
        //The max number of results is the number of items in the smallest list
        int maxNumberOfResults;
        if(purchaseKeySubList.size() == 0 ){
            maxNumberOfResults = 0;
        }else{
            maxNumberOfResults  = ((ArrayList)purchaseKeySubList.get(0)).size();
        }
        //iterate through each element in the smallest list
        for(int i = 0; i<maxNumberOfResults; i++){
            //get the current purchase key that is being searched for
            String currentPurchaseKey = (String)((ArrayList)purchaseKeySubList.get(0)).get(i);
            
            boolean matchFound = true;
            
            //if one of the lists does not contain the key, then it doesn't fulfill the query
            for(int k = 1; k<purchaseKeySubListSize; k++){
                if(!((ArrayList)(purchaseKeySubList.get(k))).contains(currentPurchaseKey))
                    matchFound = false;
            }
            
            //if there is a key that all of the lists have, then add it to the purchaseKeyList
            if(matchFound)
                purchaseKeyList.add(currentPurchaseKey);
        }
            
        //Get all of the purchases that match the keys purchaseKeyList
        for(int j = 0; j<purchaseKeyList.size(); j++){
            queryResults.add((Purchase)purchaseHashMap.get((String)purchaseKeyList.get(j)));
        }

        return queryResults;
    }
    
    /**
     * Deletes a purchase from the record
     * @param p the purchase to delete
     */
    public void deletePurchase(Purchase p){
        String key = p.toString();
        ArrayList check;
        if(purchaseHashMap.containsKey(key)){
            purchaseHashMap.remove(key);
        }
        
        if(itemNameHashMap.containsKey(p.getItemName())){            
            check = (ArrayList)itemNameHashMap.get(p.getItemName());
            if(check.contains(p.toString())){
                check.remove(p.toString());
               ((ArrayList)itemNameHashMap.get(p.getItemName())).remove(p.toString());
               if(check.size() == 0){
                    itemNameHashMap.remove(p.getItemName());                   
               }
            }
        }
        
        if(p.getPurchaseDate() != null && dateHashMap.containsKey(p.getPurchaseDate())){
            check = (ArrayList)dateHashMap.get(p.getPurchaseDate());
            if(check.contains(p.toString())){
                check.remove(p.toString());
               ((ArrayList)dateHashMap.get(p.getPurchaseDate())).remove(p.toString());
               if(check.size() == 0){
                    dateHashMap.remove(p.getItemName());                   
               }
            }
        }
        
        if(p.getItemPrice() != null && itemPriceHashMap.containsKey(p.getItemPrice())){
            check = (ArrayList)itemPriceHashMap.get(p.getItemPrice());
            if(check.contains(p.toString())){
                check.remove(p.toString());
               ((ArrayList)itemPriceHashMap.get(p.getItemPrice())).remove(p.toString());
               if(check.size() == 0){
                    itemPriceHashMap.remove(p.getItemPrice());                   
               }
            }
        }
        
       if(p.getStoreName() != null && storeNameHashMap.containsKey(p.getStoreName())){
            check = (ArrayList)storeNameHashMap.get(p.getStoreName());
            if(check.contains(p.toString())){
                check.remove(p.toString());
               ((ArrayList)storeNameHashMap.get(p.getStoreName())).remove(p.toString());
               if(check.size() == 0){
                    storeNameHashMap.remove(p.getStoreName());                   
               }
            }
        }
        
         if(p.getPurchaseCategory() != null && purchaseHashMap.containsKey(p.getPurchaseCategory())){
            check = (ArrayList)categoryHashMap.get(p.getPurchaseCategory());
            if(check.contains(p.toString())){
                check.remove(p.toString());
               ((ArrayList)categoryHashMap.get(p.getPurchaseCategory())).remove(p.toString());
               if(check.size() == 0){
                    categoryHashMap.remove(p.getPurchaseCategory());                   
               }
            }
        }
         
    }
    
    /**
     * Edits a purchase in a record, basically a swap.
     * @param op the original purchase record
     * @param np the new purchase record
     */
    public void editPurchase(Purchase op, Purchase np){
        deletePurchase(op);
        addPurchase(np);
    }
    
    public ArrayList<String> getPurchaseItemName(){
        ArrayList<String> arr = new ArrayList<String>();
        arr.addAll(itemNameHashMap.keySet());
        return arr;
    }
}
