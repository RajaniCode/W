// Java 7
/*
%c character
%d decimal (integer) number (base 10)
%e exponential floating-point number
%f floating-point number
%i integer (base 10)
%o octal number (base 8)
%s String
%u unsigned decimal (integer) number
%x number in hexadecimal (base 16)
%t formats date/time
%% print a percent sign
\% print a percent sign
*/

import java.lang.System;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

// import java.util.ArrayList;
// import java.util.List;


interface Member {
    public static final int publicStaticFinalField = 1;
    String defaultPublicStaticFinalField = "Two";
}

// class Java7 implements Member {
class Java7 {
    public static void main(String[] args) {
	System.out.printf("Java Version: %s%n%n", System.getProperty("java.version"));

	System.out.printf("A field declared in interface is public static final: %s%n%n", Member.publicStaticFinalField);

	System.out.printf("A field declared in interface is by default implicitly public static final: %s%n%n", Member.defaultPublicStaticFinalField);

	Field[] declaredFields = Member.class.getDeclaredFields();
	// List<Field> staticFields = new ArrayList<Field>();
	for (Field field : declaredFields) {
            // field.getClass().getSimpleName()
             // field.getClass().getName()
            System.out.printf("Is %s static? %s %n%n", field.getName(), Modifier.isStatic(field.getModifiers()));
          
            System.out.printf("Access Modifier of %s: %s %n%n", field.getName(), Modifier.toString(field.getModifiers()));

	    /*
    	    if (Modifier.isStatic(field.getModifiers())) {
	        // staticFields.add(field);
            } else {
	        // System.out.printf("Access Modifier of %s: %s %n%n", field.getName(), Modifier.toString(field.getModifiers()));
            }
            */
        }
    }
}

/*
rajaniapple@Rajanis-MacBook-Pro Java7 % arch            
i386
rajaniapple@Rajanis-MacBook-Pro Java7 % java -version
java version "1.7.0_80"
Java(TM) SE Runtime Environment (build 1.7.0_80-b15)
Java HotSpot(TM) 64-Bit Server VM (build 24.80-b11, mixed mode)
rajaniapple@Rajanis-MacBook-Pro Java7 % javac Java7.java
rajaniapple@Rajanis-MacBook-Pro Java7 % java Java7  
*/

// Output
/*    
Java Version: 1.7.0_80

A field declared in interface is public static final: 1

A field declared in interface is by default implicitly public static final: Two

Is publicStaticFinalField static? true 

Access Modifier of publicStaticFinalField: public static final 

Is defaultPublicStaticFinalField static? true

Access Modifier of defaultPublicStaticFinalField: public static final 

*/

/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
*/