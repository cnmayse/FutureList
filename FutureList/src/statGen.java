import java.util.*;
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
        double sum = 0;
        for (int i = 0; i < serries.size(); i++) {
            sum += serries.get(i).getitemPrice();
        }
        double mean = sum / serries.size();
        return mean;
    }
    
    public static String modeStore(ArrayList serries){
       throw new UnsupportedOperationException();
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
