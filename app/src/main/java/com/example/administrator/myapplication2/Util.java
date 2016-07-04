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

    /**
     * 根据当前价格，最大价格以及保留的小数位数，获得一个符合规则的数值字符串
     * @param price 当前价格
     * @param maxPrice 最大价格
     * @param radixPointLength 保留的小数位数
     * @return String
     */
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

    /**
     * 从一个字符串转换成double类型的数据
     * @param price
     * @return
     */
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

    /**
     * 获得一个字符串类型的，已经格式化过的数值（保留三位小数）
     * @param price
     * @return
     */
    public static String formatDouble3 (double price)
    {
        DecimalFormat format = new DecimalFormat ("0.000");
        return format.format (price);
    }

    /**
     * 获得一个格式化过的double类型的数值（保留三位小数）
     * @param price
     * @return
     */
    public static double getFormatDoublePrice (double price)
    {
        DecimalFormat format = new DecimalFormat ("0.000");
        String temp = format.format (price);
        return Double.parseDouble (temp);
    }

    /**
     * 判断一个字符串是否可以转换成double类型的数据
     * @param s
     * @return
     */
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

    public static boolean isInteger (String s)
    {
        boolean flag = true;
        try
        {
            Integer.parseInt (s);
        }
        catch (Exception e)
        {
            flag = false;
        }
        return flag;
    }

    /**
     * 得到一个以逗号分隔的数字，类似：123,456
     * @param amount
     * @return
     */
    public static String getAmountWithComma (int amount)
    {
        DecimalFormat format = new DecimalFormat ("###,###");
        return format.format (amount);
    }

    /**
     * 获得格式化过的数量（类似:123,456）
     * @param currentAmount
     * @param maxAmount
     * @return
     */
    public static String getFormatAmountStr (String currentAmount,int maxAmount)
    {
        String currentAmountStr = currentAmount.replace (",","");
        String maxAmountStr = String.valueOf (maxAmount);
        int currentAmountLength = currentAmountStr.length ();
        int maxAmountLength = maxAmountStr.length ();

        if (currentAmountLength > maxAmountLength)
        {
            currentAmountStr = currentAmountStr.substring (0,maxAmountLength);
            // 长度截断之后（当前价格的长度等于最大价格的长度），当前价格的值依旧有可能大于最大价格
            int tempAmount = Integer.parseInt (currentAmountStr);
            if (tempAmount >= maxAmount)
            {
                currentAmountStr = maxAmountStr;
            }
        }
        else if (currentAmountStr.length () == maxAmountStr.length ())
        {
            // 长度截断之后（当前价格的长度等于最大价格的长度），当前价格的值依旧有可能大于最大价格
            int tempAmount = Integer.parseInt (currentAmountStr);
            if (tempAmount >= maxAmount)
            {
                currentAmountStr = maxAmountStr;
            }
        }

        return getAmountWithComma (Integer.parseInt (currentAmountStr));
    }
}
