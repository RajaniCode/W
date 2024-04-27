// Java 20
// OpenJDK 20
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK20.0.2/jdk-20.0.2.jdk/Contents/Home/bin/":$PATH

// % java --enable-preview --source 20 --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.concurrent,jdk.incubator.vector Java20.java
// Or
// % javac -Xlint:preview --enable-preview --source 20 -add-modules=jdk.incubator.concurrent,jdk.incubator.vector Java20.java
// % java --enable-preview --enable-native-access=ALL-UNNAMED -add-modules=jdk.incubator.concurrent,jdk.incubator.vector Java20

// Java 19
// OpenJDK 19
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK19.0.2/jdk-19.0.2.jdk/Contents/Home/bin/":$PATH


/*
# 1 # 429: Scoped Values (Incubator)
# 2 # 432: Record Patterns (Second Preview)
# 3 # 433: Pattern Matching for switch (Fourth Preview)
# 4 # 434: Foreign Function & Memory API (Second Preview)
# 5 # 436: Virtual Threads (Second Preview)
# 6 # 437: Structured Concurrency (Second Incubator)
# 7 # 438: Vector API (Fifth Incubator)
*/


// import static java.lang.System.out;
// import java.lang.System;

class Java20 {
    public static void main(String[] args) { 
    // public static void main(String... args) { 
        var propertiesSystem = new SystemProperties();
        propertiesSystem.print();

	// # 1 # 429: Scoped Values (Incubator)

	// # 2 # 432: Record Patterns (Second Preview)

	// # 3 # 433: Pattern Matching for switch (Fourth Preview)

	// # 4 # 434: Foreign Function & Memory API (Second Preview)

	// # 5 # 436: Virtual Threads (Second Preview)

	// # 6 # 437: Structured Concurrency (Second Incubator)

	// # 7 # 438: Vector API (Fifth Incubator)
    }
}

class SystemProperties {
    public void print() {
        System.out.println(String.format("OS Name: %s", System.getProperty("os.name")));
        System.out.println(String.format("OS Version: %s", System.getProperty("os.version")));
        System.out.println(String.format("OS Architecture: %s", System.getProperty("os.arch")));
        System.out.println(String.format("Java Version: %s", System.getProperty("java.version")));
        // System.getProperties().list(System.out);
        System.out.println();
    }
}


// Output
/*
WARNING: Using incubator modules: jdk.incubator.vector, jdk.incubator.concurrent
OS Name: Mac OS X
OS Version: 14.0
OS Architecture: aarch64
Java Version: 20.0.2
*/


/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
*/