package shopping.list.csc340.project2;
import java.util.*;
import java.time.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ochaun Marshall
 */
public class statTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //(String s, LocalDate d, Double ip, StoreName sn, PurchaseCategory pc)
        Purchase a = new Purchase( "milk", LocalDate.of(2014,04,12), 4.13,StoreName.WALMART,PurchaseCategory.FOOD );
        Purchase s = new Purchase( "milk", LocalDate.of(2014,04,14), 4.0,StoreName.WALMART,PurchaseCategory.FOOD );
        Purchase d = new Purchase( "coffee", LocalDate.of(2014,04,7), 4.77,StoreName.HARRIS_TEETER,PurchaseCategory.FOOD );
        Purchase f = new Purchase( "coffee", LocalDate.of(2014,04,13), 4.13,StoreName.HARRIS_TEETER,PurchaseCategory.FOOD );
        Purchase j = new Purchase( "coffee", LocalDate.of(2014,04,20), 4.13,StoreName.HARRIS_TEETER,PurchaseCategory.FOOD );
        Purchase k = new Purchase( "coffee", LocalDate.of(2014,04,21), 4.0,StoreName.HARRIS_TEETER,PurchaseCategory.FOOD );
        Purchase l = new Purchase( "coffee", LocalDate.of(2014,04,5), 4.77,StoreName.AMAZON,PurchaseCategory.FOOD );
        Purchase p = new Purchase( "milk", LocalDate.of(2014,04,27), 4.25,StoreName.WALMART,PurchaseCategory.FOOD );
        ArrayList<Purchase> temp = new ArrayList<Purchase>(Arrays.asList(a,s,d,f,j,k,l,p));
        System.out.println(statGen.meanPrice(temp));
        System.out.println(statGen.modeItem(temp));
        System.out.println(statGen.modeCategory(temp));
        System.out.println(statGen.modeStore(temp));
        statGen.predict(temp, "milk");
        
    }
    
}
