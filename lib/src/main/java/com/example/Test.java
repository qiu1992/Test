package com.example;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016-7-21.
 */
public class Test
{
    public static void main (String [] args)
    {
//        System.out.println (Math.round (0));
//        System.out.println (Math.round (0.01));
//        System.out.println (Math.round (0.1));
//        System.out.println (Math.round (0.5));
//        System.out.println (Math.round (0.6));
//        System.out.println (Math.round (0.7));
//        System.out.println (Math.round (0.55));
//        System.out.println (Math.round (0.99));
//        System.out.println (new BigDecimal("0.55").setScale(0, BigDecimal.ROUND_HALF_UP));
        System.out.println (getFormatNumberStr (0));
        System.out.println (getFormatNumberStr (0.01));
        System.out.println (getFormatNumberStr (0.5));
        System.out.println (getFormatNumberStr (0.55));
        System.out.println (getFormatNumberStr (0.99));
    }

    public static String getFormatNumberStr (double number)
    {
        String res = "0";
        if (number >= 0 && number < 1000)
        {
            if (number >=0 && number < 1)
            {
                res = String.valueOf (Math.round (number));
            }
            else
            {
                if (((int) number) == number)
                {
                    res = String.valueOf ((int) number);
                }
                else
                {
                    res = formatDouble3 (number);
                }
            }
        }
        else if (number >= 1000 && number < 1000000)// K
        {
            res = getFormatNumberStr (number, 1000) + "K";
        }
        else if (number >= 1000000 && number < 1000000000)// M
        {
            res = getFormatNumberStr (number, 1000000) + "M";
        }
        else if (number >= 1000000000)// B
        {
            res = getFormatNumberStr (number, 1000000000) + "B";
        }
        return res;
    }

    private static String getFormatNumberStr (double number, int rate)
    {
        String res;
        if (number % rate == 0)
        {
            res = String.valueOf ((int) number / rate);
        }
        else
        {
            res = formatDouble3 (number / (rate * 1.0));
        }
        return res;
    }

    /**
     * 获得一个字符串类型的，已经格式化过的数值（保留三位小数）
     *
     * @param price
     * @return
     */
    public static String formatDouble3 (double price)
    {
        DecimalFormat format = new DecimalFormat ("0.000");
        return format.format (price);
    }
}
