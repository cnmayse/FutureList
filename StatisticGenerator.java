package shopping.list.csc340.project2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * This is my/our own work. I/We have abided by UNCG's Academic integrity policy
 *
 * @author Charles Mayse
 */
/**
 * This class is used to generate statistics on sets of statistics
 */
public class StatisticGenerator {

    private double purchaseSum;
    
    /**
     * Gets the average price of a set of purchases. NOTE: if a purchase has a
     * null price, the null is treated as a zero, and the number of items to
     * divide by gets subtracted.
     *
     * @param purchaseList the set of purchases
     * @return the average price
     */
    public double meanPrice(ArrayList purchaseList) {
        Iterator iterator = purchaseList.iterator();
         purchaseSum = 0.0;
        int numPurchases = purchaseList.size();

        while (iterator.hasNext()) {
            Double currentItemPrice = ((Purchase) iterator.next()).getItemPrice();
            if (currentItemPrice != null) {
                purchaseSum += currentItemPrice;
            } else {
                --numPurchases;
            }
        }
        
        if(numPurchases == 0){
            return 0.0;
        }
        
        return (purchaseSum / numPurchases);
    }
    
    public double getPurchaseSum(){
        return this.purchaseSum;
    }

    /**
     * Gets the mode of the stores in a set of purchases. NOTE: if a purchase
     * has a null store name, that entry is skipped.
     *
     * TODO: this may need to be fixed for when two or more store entries occur
     * the same number of times, but are the maximum number of occurrences.
     *
     * @param purchaseList the set of purchases
     * @return the store name that occurs the most;
     */
    public StoreName modeStore(ArrayList purchaseList) {
        Iterator iterator1 = purchaseList.iterator();
        HashMap storeNames = new HashMap<StoreName, Integer>();

        //Make a hashmap keyed by store name, value of number of occurrences
        while (iterator1.hasNext()) {
            StoreName currentStoreName = ((Purchase) iterator1.next()).getStoreName();
            if (currentStoreName != null && !storeNames.containsKey(currentStoreName)) {
                storeNames.put(currentStoreName, 1);
            } else if (currentStoreName != null && storeNames.containsKey(currentStoreName)) {
                Integer newValue = (Integer) storeNames.get(currentStoreName);
                newValue = newValue + 1;
                storeNames.replace(currentStoreName, newValue);
            }
        }

        Set storeNamesSet = storeNames.entrySet();
        Iterator iterator2 = storeNamesSet.iterator();
        Map.Entry currentTop = null;
        
        if(iterator2.hasNext()){
            currentTop = (Map.Entry) iterator2.next();
        }else{
            return null;
        }

        //iterate through the hashmap and find the entry with the most 
        while (iterator2.hasNext()) {
            Map.Entry nextEntry = (Map.Entry) iterator2.next();
            if ((Integer) currentTop.getValue() > (Integer) nextEntry.getValue()) {
                continue;
            } else {
                currentTop = nextEntry;
            }
        }
        
        
        
        return (StoreName) currentTop.getKey();
    }

    /**
     * Gets the mode of the categories in a set of purchases. NOTE: if a
     * purchase has a category, that entry is skipped.
     *
     * TODO: this may need to be fixed for when two or more category entries
     * occur the same number of times, but are the maximum number of
     * occurrences.
     *
     * @param purchaseList the set of purchases
     * @return the category that occurs the most;
     */
    public PurchaseCategory modeCategory(ArrayList purchaseList) {
        Iterator iterator1 = purchaseList.iterator();
        HashMap categories = new HashMap<PurchaseCategory, Integer>();

        //Make a hashmap keyed by store name, value of number of occurrences
        while (iterator1.hasNext()) {
            PurchaseCategory currentCategory = ((Purchase) iterator1.next()).getPurchaseCategory();
            if (currentCategory != null && !categories.containsKey(currentCategory)) {
                categories.put(currentCategory, 1);
            } else if (currentCategory != null && categories.containsKey(currentCategory)) {
                Integer newValue = (Integer) categories.get(currentCategory);
                newValue = newValue + 1;
                categories.replace(currentCategory, newValue);
            }
        }

        Set categorySet = categories.entrySet();
        Iterator iterator2 = categorySet.iterator();
        Map.Entry currentTop = null;
        if(iterator2.hasNext()){
            currentTop = (Map.Entry) iterator2.next();
        }else{
            return null;
        }

        //iterate through the hashmap and find the entry with the most 
        while (iterator2.hasNext()) {
            Map.Entry nextEntry = (Map.Entry) iterator2.next();
            if ((Integer) currentTop.getValue() > (Integer) nextEntry.getValue()) {
                continue;
            } else {
                currentTop = nextEntry;
            }
        }
        
        
        return (PurchaseCategory) currentTop.getKey();
    }

    /**
     * Gets the mode of the items in a set of purchases.
     *
     * TODO: this may need to be fixed for when two or more item name entries occur
     * the same number of times, but are the maximum number of occurrences.
     *
     * @param purchaseList the set of purchases
     * @return the item that occurs the most;
     */
    public String modeItem(ArrayList purchaseList) {
        Iterator iterator1 = purchaseList.iterator();
        HashMap itemNames = new HashMap<String, Integer>();

        //Make a hashmap keyed by store name, value of number of occurrences
        while (iterator1.hasNext()) {
            String currentItemName = ((Purchase) iterator1.next()).getItemName();
            if (currentItemName != null && !itemNames.containsKey(currentItemName)) {
                itemNames.put(currentItemName, 1);
            } else if (currentItemName != null && itemNames.containsKey(currentItemName)) {
                Integer newValue = (Integer) itemNames.get(currentItemName);
                newValue = newValue + 1;
                itemNames.replace(currentItemName, newValue);
            }
        }

        Set storeNamesSet = itemNames.entrySet();
        Iterator iterator2 = storeNamesSet.iterator();

        Map.Entry currentTop = (Map.Entry) iterator2.next();

        //iterate through the hashmap and find the entry with the most 
        while (iterator2.hasNext()) {
            Map.Entry nextEntry = (Map.Entry) iterator2.next();
            if ((Integer) currentTop.getValue() > (Integer) nextEntry.getValue()) {
                continue;
            } else {
                currentTop = nextEntry;
            }
        }

        return (String)currentTop.getKey();
    }
    
    
}
