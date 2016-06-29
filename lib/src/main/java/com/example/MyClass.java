package com.example;

public class MyClass
{
    public static void main (String [] args)
    {
        final User user = new User ();
        user.setAge (12.3f);
        user.setName ("qiu");

        System.out.println (user.getAge () + "/" + user.getName ());
    }
}
