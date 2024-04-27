// Java 20
// OpenJDK 20
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK20.0.2/jdk-20.0.2.jdk/Contents/Home/bin/":$PATH

// % java --enable-preview --source 20 --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.concurrent,jdk.incubator.vector ScopedValuesIncubator.java
// Or
// % javac -Xlint:preview --enable-preview --source 20 -add-modules=jdk.incubator.concurrent,jdk.incubator.vector ScopedValuesIncubator.java
// % java --enable-preview --enable-native-access=ALL-UNNAMED -add-modules=jdk.incubator.concurrent,jdk.incubator.vector ScopedValuesIncubator

// Java 19
// OpenJDK 19
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK19.0.2/jdk-19.0.2.jdk/Contents/Home/bin/":$PATH


/*
# 429: Scoped Values (Incubator)
*/


class ScopedValuesIncubator {
    public static void main(String[] args) { 
    // public static void main(String... args) { 
        var propertiesSystem = new SystemProperties();
        propertiesSystem.print();

        System.out.println("# 429: Scoped Values (Incubator)");
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

# 429: Scoped Values (Incubator)
*/


/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
*/