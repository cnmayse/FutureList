package shopping.list.csc340.project2;
import java.util.*;
import java.util.Map.Entry;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ochaun Marshall
 */
public class statGen {
    public static double meanPrice(ArrayList<Purchase> serries){
        
        //Purchase[] sarr = (Purchase)serries.toArray();
        double sum = 0;
        for (int i = 0; i < serries.size(); i++) {
            sum += serries.get(i).getItemPrice();
        }
        double mean = sum / serries.size();
        return mean;
    }
    
    public static String modeStore(ArrayList<Purchase> serries){
       throw new UnsupportedOperationException();
     //   Purchase[] sarr =serries.toArray();
        int max=1, count=0;
        StoreName temp;
       // List<String> theList = Arrays.asList(sarr);
        HashMap<Purchase,Integer> serhm =new HashMap<Purchase,Integer>();
        for (int i = 0; i < serries.size(); i++) {
            temp = serries.get(i).getStoreName();
            if (serhm.containsKey(temp)) {
                serhm.put(temp, serhm.get(temp)+1);
            }
            else{
                serhm.put(tempstr,1);
            }
            if (max < count){
                max = count;
            }
        }
        for (Entry<String, Integer> entry: serhm.entrySet()) {
            if (max == entry.getValue()){
                return entry.getKey();
            }
            
        }
        return null;
        
        
    }
    public static String modeCatagory(ArrayList<Purchase> serries){
        throw new UnsupportedOperationException();
    }
    public static String modeItem(ArrayList<Purchase> serries){
       // throw new UnsupportedOperationException();
        int max=0;
        String tempstr;
        List<String> theList = new ArrayList<String>();
        HashMap<String,Integer> serhm =new HashMap<String,Integer>();
        for (int i = 0; i < serries.size(); i++) {
            tempstr = serries.get(i).getItemName();
            if (serhm.containsKey(tempstr)) {
                serhm.put(tempstr, serhm.get(tempstr)+1);
            }
            else{
                serhm.put(tempstr,1);
            }
            if (max < count){
                max = count;
            }
        }
        for (Entry<String, Integer> entry: serhm.entrySet()) {
            if (max == entry.getValue()){
                return entry.getKey();
            }
        
        
        }    
        return "abc you fail";
    }
    public static ArrayList predict(ArrayList<Purchase> serries){
        /**
         * 1. Recives a list of arrays along with empty spaces for prediction in x days in advance 
         * 2. Turn those purchases into data points
         * 3. Feed those data points 
         */
        throw new UnsupportedOperationException();
    }
}
