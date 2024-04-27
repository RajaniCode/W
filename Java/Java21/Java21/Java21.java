// Java 21
// OpenJDK 21
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK21/jdk-21.jdk/Contents/Home/bin/":$PATH

// Java 21
// Microsoft Build of OpenJDK
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/MicrosoftBuildofOpenJDK/jdk-21+35/Contents/Home/bin/":$PATH

// % java --enable-preview --source 21 --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.vector Java21.java
// Or
// % javac -Xlint:preview --enable-preview --source 21 --add-modules=jdk.incubator.vector Java21.java
// % java --enable-preview --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.vector Java21

// Java 20
// OpenJDK 20
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK20.0.2/jdk-20.0.2.jdk/Contents/Home/bin/":$PATH


/*
# 1 # 430: String Templates (Preview)
# 2 # 431: Sequenced Collections
# 3 # 439: Generational ZGC
# 4 # 440: Record Patterns
# 5 # 441: Pattern Matching for switch
# 6 # 442: Foreign Function & Memory API (Third Preview)
# 7 # 443: Unnamed Patterns and Variables (Preview)
# 8 # 444: Virtual Threads
# 9 # 445: Unnamed Classes and Instance Main Methods (Preview)
# 10 # 446: Scoped Values (Preview)
# 11 # 448: Vector API (Sixth Incubator)
# 12 # 449: Deprecate the Windows 32-bit x86 Port for Removal
# 13 # 451: Prepare to Disallow the Dynamic Loading of Agents
# 14 # 452: Key Encapsulation Mechanism API
# 15 # 453: Structured Concurrency (Preview)
*/


// import static java.lang.System.out;
// import java.lang.System;

class Java21 {
    public static void main(String[] args) { 
    // public static void main(String... args) { 
        var propertiesSystem = new SystemProperties();
        propertiesSystem.print();

	// # 1 # 430: String Templates (Preview)

	// # 2 # 431: Sequenced Collections

	// # 3 # 439: Generational ZGC

	// # 4 # 440: Record Patterns

	// # 5 # 441: Pattern Matching for switch

	// # 6 # 442: Foreign Function & Memory API (Third Preview)

	// # 7 # 443: Unnamed Patterns and Variables (Preview)

	// # 8 # 444: Virtual Threads

	// # 9 # 445: Unnamed Classes and Instance Main Methods (Preview)

	// # 10 # 446: Scoped Values (Preview)

	// # 11 # 448: Vector API (Sixth Incubator)

	// # 12 # 449: Deprecate the Windows 32-bit x86 Port for Removal

	// # 13 # 451: Prepare to Disallow the Dynamic Loading of Agents

	// # 14 # 452: Key Encapsulation Mechanism API

	// # 15 # 453: Structured Concurrency (Preview)
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
WARNING: Using incubator modules: jdk.incubator.vector
OS Name: Mac OS X
OS Version: 14.0
OS Architecture: aarch64
Java Version: 21
*/


/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
*/