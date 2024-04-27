package com.sample;

import java.util.UUID;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Hello, World!");
        App instance = new App();
        System.out.println("Unique ID : " + instance.getUUID("123e4567-e89b-12d3-a456-556642440000"));
    }

    public String getUUID(String universallyUniqueIdentifier) 
    {
        String uid = UUID.fromString​(universallyUniqueIdentifier).toString();
        return uid;
    }
}