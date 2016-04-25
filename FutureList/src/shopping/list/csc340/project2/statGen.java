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
public class statGen {

    public static double meanPrice(ArrayList<Purchase> serries) {

        //Purchase[] sarr = (Purchase)serries.toArray();
        double sum = 0;
        for (int i = 0; i < serries.size(); i++) {
            sum += serries.get(i).getItemPrice();
        }
        double mean = sum / serries.size();
        return mean;
    }

    public static StoreName modeStore(ArrayList<Purchase> serries) {
        // throw new UnsupportedOperationException();
        int max = 0;
        StoreName temp;
        List<StoreName> theList = new ArrayList<StoreName>();
        Map<StoreName, Integer> serhm = new HashMap<StoreName, Integer>();
        for (int i = 0; i < serries.size(); i++) {
            temp = serries.get(i).getStoreName();
            if (serhm.containsKey(temp)) {
                serhm.put(temp, serhm.get(temp) + 1);
            } else {
                serhm.put(temp, 1);
            }
            if (serhm.get(temp) > max) {
                max = serhm.get(temp);
                theList.clear();
                theList.add(temp);
            } else if (serhm.get(temp) == max) {
                theList.add(temp);
            }
        }
        return theList.get(0);
    }

    public static PurchaseCategory modeCategory(ArrayList<Purchase> serries) {
        int max = 0;
        PurchaseCategory temp;
        List<PurchaseCategory> theList = new ArrayList<PurchaseCategory>();
        Map<PurchaseCategory, Integer> serhm = new HashMap<PurchaseCategory, Integer>();
        for (int i = 0; i < serries.size(); i++) {
            temp = serries.get(i).getPurchaseCategory();
            if (serhm.containsKey(temp)) {
                serhm.put(temp, serhm.get(temp) + 1);
            } else {
                serhm.put(temp, 1);
            }
            if (serhm.get(temp) > max) {
                max = serhm.get(temp);
                theList.clear();
                theList.add(temp);
            } else if (serhm.get(temp) == max) {
                theList.add(temp);
            }
        }
        return theList.get(0);
    }

    public static String modeItem(ArrayList<Purchase> serries) {
        //throw new UnsupportedOperationException();
        int max = 0;
        String tempstr;
        List<String> theList = new ArrayList<String>();
        Map<String, Integer> serhm = new HashMap<String, Integer>();
        for (int i = 0; i < serries.size(); i++) {
            tempstr = serries.get(i).getItemName();
            if (serhm.containsKey(tempstr)) {
                serhm.put(tempstr, serhm.get(tempstr) + 1);
            } else {
                serhm.put(tempstr, 1);
            }
            if (serhm.get(tempstr) > max) {
                max = serhm.get(tempstr);
                theList.clear();
                theList.add(tempstr);
            } else if (serhm.get(tempstr) == max) {
                theList.add(tempstr);
            }
        }
        return theList.get(0);
    }

    public static ArrayList<Purchase> predict(ArrayList<Purchase> series, String itemname) {
        /**
         * 1. Traverses through to calculate the average distance between days
         * someone purchases an item 2. Creates an arraylist of future dates
         * with the average interval two to 3 times in advaced with the price
         */
        LocalDate baseDate;
        Integer baseInt;
        List<LocalDate> dateSet = new ArrayList<LocalDate>();
        List<Integer> timeInt = new ArrayList<Integer>();
        for (int i = 0; i < series.size(); i++) {
            if (series.get(i).getItemName().equalsIgnoreCase(itemname)) {
                dateSet.add(series.get(i).getPurchaseDate());
            }
        }
        baseDate = Collections.min(dateSet);
        baseInt = baseDate.getDayOfYear();
        for (int i = 0; i < dateSet.size(); i++) {
            timeInt.add((Integer) dateSet.get(i).getDayOfYear() - baseInt);
            baseInt = dateSet.get(i).getDayOfYear();

        }
        int timeMean = sum(timeInt) / timeInt.size();
        series.clear();
        for (int i = 1; i < 4; i++) {
            long solution = timeMean * i;
            series.add(new Purchase(itemname, Collections.max(dateSet).plusDays(solution), 0.0, StoreName.AMAZON, PurchaseCategory.ETC_WEEKLY));
        }
        for (int i = 0; i < series.size(); i++) {
            System.out.println(series.get(i).getPurchaseDate());
        }
        return series;
    }

    private static int sum(List<Integer> bunch) {
        int sum = 0;
        for (int i = 0; i < bunch.size(); i++) {
            sum += bunch.get(i);
        }
        return sum;
    }

    public static ArrayList<Purchase> predict(ArrayList<Purchase> series, String itemname, PurchaseCategory freq) {
        LocalDate recentDate;
        List<LocalDate> dateSet = new ArrayList<LocalDate>();
        List<Integer> timeInt = new ArrayList<Integer>();
        switch (freq) {
            case ETC_YEARLY:
                for (int i = 0; i < series.size(); i++) {
                    if (series.get(i).getItemName().equalsIgnoreCase(itemname)) {
                        dateSet.add(series.get(i).getPurchaseDate());
                    }
                }
                recentDate = Collections.max(dateSet);
                series.clear();
                for (int i = 1; i < 4; i++) {
                    series.add(new Purchase(itemname, Collections.max(dateSet).plusYears(i), 0.0, StoreName.AMAZON, PurchaseCategory.ETC_YEARLY));
                }
                break;
            case ETC_MONTHLY:
                for (int i = 0; i < series.size(); i++) {
                    if (series.get(i).getItemName().equalsIgnoreCase(itemname)) {
                        dateSet.add(series.get(i).getPurchaseDate());
                    }
                }
                recentDate = Collections.max(dateSet);
                series.clear();
                for (int i = 1; i < 4; i++) {
                    series.add(new Purchase(itemname, Collections.max(dateSet).plusMonths(i), 0.0, StoreName.AMAZON, PurchaseCategory.ETC_MONTHLY));
                }
                break;
            case ETC_WEEKLY:
                for (int i = 0; i < series.size(); i++) {
                    if (series.get(i).getItemName().equalsIgnoreCase(itemname)) {
                        dateSet.add(series.get(i).getPurchaseDate());
                    }
                }
                recentDate = Collections.max(dateSet);
                series.clear();
                for (int i = 1; i < 4; i++) {
                    series.add(new Purchase(itemname, Collections.max(dateSet).plusWeeks(i), 0.0, StoreName.AMAZON, PurchaseCategory.ETC_WEEKLY));
                }
                break;
            case ETC_DAILY:
                for (int i = 0; i < series.size(); i++) {
                    if (series.get(i).getItemName().equalsIgnoreCase(itemname)) {
                        dateSet.add(series.get(i).getPurchaseDate());
                    }
                }
                recentDate = Collections.max(dateSet);
                series.clear();
                for (int i = 1; i < 4; i++) {
                    series.add(new Purchase(itemname, Collections.max(dateSet).plusDays(i), 0.0, StoreName.AMAZON, PurchaseCategory.ETC_DAILY));
                }
                break;
            default:
                break;
        }
        return series;

    }

}
