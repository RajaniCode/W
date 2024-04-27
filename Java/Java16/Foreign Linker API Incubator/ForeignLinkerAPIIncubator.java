// Java 16
// OpenJDK 16
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK16.0.2/jdk-16.0.2.jdk/Contents/Home/bin/":$PATH

// Java 16
// Microsoft Build of OpenJDK
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/MicrosoftBuildofOpenJDK/jdk-16.0.2+7/Contents/Home/bin/":$PATH

// % java --add-modules jdk.incubator.foreign -Dforeign.restricted=permit ForeignLinkerAPIIncubator.java
// Or
// % javac --add-modules jdk.incubator.foreign ForeignLinkerAPIIncubator.java
// % java --add-modules jdk.incubator.foreign -Dforeign.restricted=permit ForeignLinkerAPIIncubator


/*
# 389: Foreign Linker API (Incubator)
*/


import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.LibraryLookup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import java.nio.file.Path;
import java.util.Optional;

class ForeignLinkerAPIIncubator {
    public static void main(String[] args) throws Throwable {
        var propertiesSystem = new SystemProperties();
        propertiesSystem.print();

        System.out.println("# 389: Foreign Linker API (Incubator)");

        String userDir = System.getProperty("user.dir");

        String fileName = "msys64-mingw64.so";

        String dirFile = userDir + "/" + fileName;

        // System.out.println(dirFile);

        Path pathOf = Path.of(dirFile);

        LibraryLookup libraryLookup = LibraryLookup.ofPath(pathOf);

        Optional < LibraryLookup.Symbol > optionalLibraryLookupSymbol = libraryLookup.lookup("hola");

        if (optionalLibraryLookupSymbol.isPresent()) {

            LibraryLookup.Symbol libraryLookupSymbol = optionalLibraryLookupSymbol.get();

            FunctionDescriptor descriptorFunction = FunctionDescriptor.ofVoid();

            // return type, parameter type(s)
            MethodType typeMethod = MethodType.methodType(Void.TYPE);

            MethodHandle handleMethod = CLinker.getInstance().downcallHandle(
                libraryLookupSymbol.address(),
                typeMethod,
                descriptorFunction
            );

	    // unreported exception Throwable; must be caught or declared to be thrown
            handleMethod.invokeExact();
        }
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
WARNING: Using incubator modules: jdk.incubator.foreign
warning: using incubating module(s): jdk.incubator.foreign
1 warning
OS Name: Mac OS X
OS Version: 11.5
OS Architecture: x86_64
Java Version: 16.0.2

# 389: Foreign Linker API (Incubator)
Hello, World!
*/


/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
https://learn.microsoft.com/en-us/java/openjdk
*/