package com.example.administrator.myapplication2;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016-6-30.
 */
public class Util
{
    public static String getPrice (String price)
    {
        if (price.contains ("."))
        {
            String[] array = price.split ("\\.");
            if (array[0].length () > 6)
            {
                price = array[0].substring (0,array[0].length () - 1);
            }
            else if (array[0].length () == 6)
            {
                double temp = Double.parseDouble (array[0]);
                if (temp < 999999)
                {
                    if (array[1].length () > 3)
                    {
                        price = array[0] + "." + array[1].substring (0,3);
                    }
                }
                else if (temp == 999999)
                {
                    price = "999999";
                }
            }
            else
            {
                if (array[1].length () > 3)
                {
                    price = array[0] + "." + array[1].substring (0,3);
                }
            }
        }
        else
        {
            if (price.length () > 6)
            {
                price = price.substring (0,6);
            }
        }
        return price;
//        DecimalFormat format = new DecimalFormat ("0.000");
//        return format.format (Double.parseDouble (price));
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

    public static String getPrice3 (String price)
    {
        if (null == price || price.length () == 0)
        {
            price = "0";
        }
        else
        {
            price = price.replace (",", "");
            if (price.startsWith ("."))
            {
                price = "0" + price;
            }
            if (price.contains ("."))
            {
                String[] array = price.split ("\\.");
                if (array[0].length () > 6)
                {
                    price = array[0].substring (0, array[0].length () - 1);
                }
                else if (array[0].length () == 6)
                {
                    double temp = Double.parseDouble (array[0]);
                    if (temp < 999999)
                    {
                        if (array.length == 2)
                        {
                            if (array[1].length () > 3)
                            {
                                price = array[0] + "." + array[1].substring (0, 3);
                            }
                        }
                        else
                        {
                            price = array[0];
                        }
                    }
                    else if (temp == 999999)
                    {
                        price = "999999";
                    }
                }
                else
                {
                    if (array.length == 2)
                    {
                        if (array[1].length () > 3)
                        {
                            price = array[0] + "." + array[1].substring (0, 3);
                        }
                    }
                    else
                    {
                        price = array[0];
                    }
                }
            }
            else
            {
                if (price.length () > 6)
                {
                    price = price.substring (0, price.length () - 1);
                }
            }

            if (!price.contains ("."))
            {
                DecimalFormat format = new DecimalFormat ("0.000");
                price = format.format (Double.parseDouble (price));
            }
        }
        return price;
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
