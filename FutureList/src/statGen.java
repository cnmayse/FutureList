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
    public static double meanPrice(ArrayList serries){
        Purchase[] sarr = serries.toArray();
        double sum = 0;
        for (int i = 0; i < sarr.length; i++) {
            sum += sarr[i].getItemPrice();
        }
        double mean = sum / serries.size();
        return mean;
    }
    
    public static String modeStore(ArrayList serries){
        Purchase[] sarr =serries.toArray();
        int max=1, count=0;
        String tempstr;
        List<String> theList = Arrays.asList(sarr);
        HashMap<String,Integer> serhm =new HashMap<String,Integer>();
        for (int i = 0; i < sarr.length(); i++) {
            tempstr = sarr[i];
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
        return null;
    }
    public static String modeCatagory(ArrayList serries){
        throw new UnsupportedOperationException();
    }
    public static String modeItem(ArrayList serries){
        throw new UnsupportedOperationException();
    }
    public static ArrayList predict(ArrayList serries){
        throw new UnsupportedOperationException();
    }
}
