package com.example.administrator.myapplication2;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016-6-30.
 */
public class Util
{
//    public static String getPrice (String price)
//    {
//        if (price.contains ("."))
//        {
//            String[] array = price.split ("\\.");
//            if (array[0].length () > 6)
//            {
//                price = array[0].substring (0,array[0].length () - 1);
//            }
//            else if (array[0].length () == 6)
//            {
//                double temp = Double.parseDouble (array[0]);
//                if (temp < 999999)
//                {
//                    if (array[1].length () > 3)
//                    {
//                        price = array[0] + "." + array[1].substring (0,3);
//                    }
//                }
//                else if (temp == 999999)
//                {
//                    price = "999999";
//                }
//            }
//            else
//            {
//                if (array[1].length () > 3)
//                {
//                    price = array[0] + "." + array[1].substring (0,3);
//                }
//            }
//        }
//        else
//        {
//            if (price.length () > 6)
//            {
//                price = price.substring (0,6);
//            }
//        }
//        return price;
//    }

    public static String getPrice (String price,double maxPrice,int radixPointLength)
    {
        String maxPriceStr = String.valueOf (maxPrice);
        String [] maxArray = maxPriceStr.split ("\\.");

        if (price.contains ("."))
        {
            String[] array = price.split ("\\.");
            if (array[0].length () > maxArray[0].length ())
            {
                price = array[0].substring (0,maxArray[0].length ());
            }
            else if (array[0].length () == maxArray[0].length ())
            {
                double temp = Double.parseDouble (array[0]);
                if (temp < maxPrice)
                {
                    if (array[1].length () > radixPointLength)
                    {
                        price = array[0] + "." + array[1].substring (0,radixPointLength);
                    }
                }
                else if (temp == maxPrice)
                {
                    price = maxPriceStr;
                }
            }
            else
            {
                if (array[1].length () > radixPointLength)
                {
                    price = array[0] + "." + array[1].substring (0,radixPointLength);
                }
            }
        }
        else
        {
            if (price.length () > maxArray[0].length ())
            {
                price = price.substring (0,maxArray[0].length ());
            }
        }
        return price;
    }

    public static double getDouble (String price)
    {
        double temp;

        try
        {
            if (TextUtils.isEmpty (price))
            {
                return 0;
            }
            temp = Double.parseDouble (price);
        }
        catch (Exception e)
        {
            return 0;
        }
        return temp;
    }

    public static String formatDouble3 (double price)
    {
        DecimalFormat format = new DecimalFormat ("0.000");
        return format.format (price);
    }

    public static double getFormatPrice (double price)
    {
        DecimalFormat format = new DecimalFormat ("0.000");
        String temp = format.format (price);
        return Double.parseDouble (temp);
    }

    public static boolean isDouble (String s)
    {
        boolean flag = true;
        try
        {
            Double.parseDouble (s);
        }
        catch (Exception e)
        {
            flag = false;
        }
        return flag;
    }
}
