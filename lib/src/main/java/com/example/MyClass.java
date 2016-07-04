package com.example;

import java.text.DecimalFormat;

public class MyClass
{
    public static void main (String[] args)
    {
//        final User user = new User ();
//        user.setAge (12.3f);
//        user.setName ("qiu");
//
//        System.out.println (user.getAge () + "/" + user.getName ());

//        DecimalFormat format = new DecimalFormat("0.000");
//        System.out.println(format.format(123.1257));
//
//        String s = "123";
//        System.out.println (s.substring (0,s.length ()-1));
//        System.out.println (getPrice ("123.12"));
//        System.out.println (getPrice ("123.1234"));
//        System.out.println (getPrice ("123.1"));
//        System.out.println (getPrice ("999999.1235"));
//        System.out.println (getPrice ("1234567"));
//        System.out.println (getPrice ("999999.0"));
//        System.out.println (getPrice ("9999999"));
//        System.out.println (getPrice ("999999.123"));

//        System.out.println (Double.parseDouble ("123456.987"));
//        System.out.println ("1231".split (","));
//        System.out.println (getPrice3 ("."));
//        System.out.println (getPrice3 (".0"));
//        System.out.println (getPrice3 (".1"));
//        System.out.println (getPrice3 (".1234"));
//        System.out.println (getPrice3 ("0."));
//        System.out.println (getPrice3 ("0.0"));
//        System.out.println (getPrice3 ("0.1"));
//        System.out.println (getPrice3 ("1."));
//        System.out.println (getPrice3 ("1."));
//        System.out.println (getPrice3 ("1.0"));
//        System.out.println (getPrice3 ("1.1"));
//        System.out.println (getPrice3 ("1.234"));
//        System.out.println (getPrice3 ("1.2345"));
//        System.out.println (getPrice3 ("123456"));
//        System.out.println (getPrice3 ("1234567"));
//        System.out.println (getPrice3 ("123456."));
//        System.out.println (getPrice3 ("123456.0"));
//        System.out.println (getPrice3 ("123456.012"));
//        System.out.println (getPrice3 ("123456.0123"));
//        System.out.println (getPrice3 ("999999"));
//        System.out.println (getPrice3 ("999999."));
//        System.out.println (getPrice3 ("999999.0"));
//        System.out.println (getPrice3 ("999999.000"));
//        System.out.println (getPrice3 ("999999.001"));
//        System.out.println (getPrice3 ("999999.123"));
//        String res = "0.";
//        System.out.println ((res.length () - 1) - res.indexOf ("."));
//        DecimalFormat format = new DecimalFormat ("###,000");
//        System.out.println (format.format (123));
//        System.out.println (format.format (12345));
//        System.out.println (format.format (123456));
//        System.out.println (format.format (1234567));
//        System.out.println (getFormatAmountStr ("123,456",123456));
//        System.out.println (getFormatAmountStr ("1,234",123456));
//        System.out.println (getFormatAmountStr ("1,234,567",123456));
//        System.out.println (getFormatAmountStr ("1,234,578",123456));
//        System.out.println (getFormatAmountStr ("12,345",123456));
        System.out.println (Integer.parseInt (getFormatAmountStr ("123457",123456)));
//        System.out.println ("123".substring (1,"123".length ()));
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

    public static String getPrice (String price)
    {
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
                    if (array[1].length () > 3)
                    {
                        price = array[0] + "." + array[1].substring (0, 3);
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
                    price = array[0] + "." + array[1].substring (0, 3);
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
        DecimalFormat format = new DecimalFormat ("0.000");
        return format.format (Double.parseDouble (price));
    }

//    public String getPrice1 (String price)
//    {
//        Double
//    }

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
            DecimalFormat format = new DecimalFormat ("0.000");
            return format.format (Double.parseDouble (price));
        }
        return price;
    }
}
