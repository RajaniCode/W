// Java 20
// OpenJDK 20
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK20.0.2/jdk-20.0.2.jdk/Contents/Home/bin/":$PATH
// % java --version


// helloworld.h
/*
#ifndef helloworld_h
#define helloworld_h

extern void helloworld(void);

#endif */ // /* helloworld_h */

/*
// helloworld.c
#include <stdio.h>

#include "helloworld.h"

void helloworld(void) {
    printf("Hello World!\n");
}
*/

// Mac Gatekeeper # Allow Anyway
/*
/Users/rajaniapple/Desktop/Working/Java/Java-GCC/jextract/jextract-20/bin/jextract
*/

// cc -shared -o libhelloworld.dylib helloworld.c
// /Users/rajaniapple/Desktop/Working/Java/Java-GCC/jextract/jextract-20/bin/jextract --source -t org.hello -lhelloworld helloworld.h
// javac --enable-preview --source=20 org/hello/*.java
// java --enable-native-access=ALL-UNNAMED --enable-preview --source=20 HelloWorld.java
import static org.hello.helloworld_h.*;

public class HelloWorld {
    public static void main(String[] args) {
        helloworld();
    }
}