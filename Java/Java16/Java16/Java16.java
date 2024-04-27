// Java 16
// OpenJDK 16
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK16.0.2/jdk-16.0.2.jdk/Contents/Home/bin/":$PATH

// Java 16
// Microsoft Build of OpenJDK
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/MicrosoftBuildofOpenJDK/jdk-16.0.2+7/Contents/Home/bin/":$PATH

// % java --enable-preview --source 16 --add-modules=jdk.incubator.foreign -Dforeign.restricted=permit Java16.java
// Or
// % javac -Xlint:preview --enable-preview --source 16 --add-modules=jdk.incubator.foreign Java16.java
// % java --enable-preview --add-modules=jdk.incubator.foreign -Dforeign.restricted=permit Java16


/*
338: Vector API (Incubator)
347: Enable C++14 Language Features
357: Migrate from Mercurial to Git
369: Migrate to GitHub
376: ZGC: Concurrent Thread-Stack Processing
380: Unix-Domain Socket Channels
386: Alpine Linux Port
387: Elastic Metaspace
388: Windows/AArch64 Port
# 1 # 389: Foreign Linker API (Incubator)
390: Warnings for Value-Based Classes
392: Packaging Tool
393: Foreign-Memory Access API (Third Incubator)
# 2 # 394: Pattern Matching for instanceof
# 3 # 395: Records
396: Strongly Encapsulate JDK Internals by Default
# 4 # 397: Sealed Classes (Second Preview)
*/


// import static java.lang.System.out;
// import java.lang.System;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.LibraryLookup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import java.nio.file.Path;
import java.util.Optional;

class Java16 {
    public static void main(String[] args) throws Throwable {
    // public static void main(String... args) throws Throwable { 
        var propertiesSystem = new SystemProperties();
        propertiesSystem.print();

	// # 1 # 389: Foreign Linker API (Incubator)
        var incubatorForeignLinkerAPI = new ForeignLinkerAPIIncubator();
	incubatorForeignLinkerAPI.print();

	// # 2 # 394: Pattern Matching for instanceof
	var instanceOfPatternMatching = new PatternMatchingInstanceOf();
	instanceOfPatternMatching.print();	

	// # 3 # 395: Records
	var clientRecords = new RecordsClient();
	clientRecords.print();

	// # 4 # 397: Sealed Classes (Second Preview)
	// Java 16 Preview
        var clientSealed = new SealedClient();
	clientSealed.print();
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


// # 1 # 389: Foreign Linker API (Incubator)
class ForeignLinkerAPIIncubator {
    public static void print() throws Throwable {
        System.out.println("# 1 # 389: Foreign Linker API (Incubator)");

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
	System.out.println();
    }
}


// # 2 # 394: Pattern Matching for instanceof
class PatternMatchingInstanceOf {
    public void print() {        
        System.out.println("# 2 # 394: Pattern Matching for instanceof");
       
        Object pattern = "^[a-zA-Z]*$";

	// Pre-Java 16 Preview
        if (pattern instanceof String) {
	    // String s = (String)pattern;
            System.out.println("pattern instanceof String");
            System.out.println("((String)pattern).length():" + ((String)pattern).length());
        }
        
        if (pattern instanceof String s) {
	    // error: pattern binding s may not be assigned
	    // s = "^[a-zA-Z0-9]*$";
            System.out.println("pattern instanceof String s");
            System.out.println("s.length():" + s.length());
        }
	System.out.println();
    }
}


// # 3 # 395: Records
record Records(int number, String text) { 
    // Public accessor method
    public int number() {
        //System.out.println("Number is " + number);
        return number;
    }

     // Public accessor method
    public String text() {
        //System.out.println("Text is " + text);
        return text;
    }

    // Static field
    static Character period;

    // Static initializer
    static {
        period = '.';
    }

    // No instance initializer
    // {
        // period = '.';
    // }

    // Static method
    public static Records append(String message) {
        return new Records(2, message + period);
    }
}

// # 3 # 395: Records
class RecordsClient {
    public void print() {        
        System.out.println("# 3 # 395: Records");

	var rec = new Records(1, "Final");
        System.out.println(rec.number());
        System.out.println(rec.text());
        System.out.println(Records.append("Final"));
        System.out.println();
    }
}


// # 4 # 397: Sealed Classes (Second Preview)
// Java 16 Preview

// sealed class must have subclasses
sealed class SealedType {}

// subclasses of sealed class must be sealed, non-sealed or final modifiers
non-sealed class SubSealedType extends SealedType {}

// sealed class can permit unlike final
sealed class SealedTypePermitter permits SubSealedTypePermitted, SubSealedTypePermittedHavingSubType {}

// sealed, non-sealed or final
non-sealed class SubSealedTypePermitted extends SealedTypePermitter {}
// final class SubSealedTypePermitted extends SealedTypePermitter {}
sealed class SubSealedTypePermittedHavingSubType extends SealedTypePermitter {}
non-sealed class SubSealedTypePermittedHavingSubTypeExtended extends SubSealedTypePermittedHavingSubType {}

// # 4 # 397: Sealed Classes (Second Preview)
// Java 16 Preview
class SealedClient {
    public void print() { 
        System.out.println("# 4 # 397: Sealed Classes (Second Preview)");
        var permitterSealedType = new SealedTypePermitter();
        System.out.println(permitterSealedType.getClass());
        System.out.println(permitterSealedType.getClass().isSealed());
        System.out.println();

        var permittedSubSealedType = new SubSealedTypePermitted();
        System.out.println(permittedSubSealedType.getClass());
        System.out.println(permittedSubSealedType.getClass().isSealed());
        System.out.println();

        var havingSubTypeSubSealedTypePermitted = new SubSealedTypePermittedHavingSubType();
        System.out.println(havingSubTypeSubSealedTypePermitted.getClass());
        System.out.println(havingSubTypeSubSealedTypePermitted.getClass().isSealed());
        System.out.println();

        var havingSubTypeExtendedSubSealedTypePermitted = new SubSealedTypePermittedHavingSubTypeExtended();
        System.out.println(havingSubTypeExtendedSubSealedTypePermitted.getClass());
        System.out.println(havingSubTypeExtendedSubSealedTypePermitted.getClass().isSealed());
    }
}


// msys64-mingw64.c
/*
#include <stdio.h>
#include <string.h>

void hola() {  
    printf("Hello, World!\n");
}

const char* h() {  
    return "Hello, World!\n";
}

int number() { 
    printf("number function call\n");
    return 1234567890;
}

void hi(char* c) {  
    printf("%s", c);
}

const char* hello_world(char* x, char* y) {  
   static char a[100], b[100];
   strcpy(a, x); 
   strcpy(b, y);
   return strcat(a, b);
}

const char* hello(char* b) {
   static char a[] = "Hello, ";
   return strcat(a, b);
}

int main() {
    h();
    printf("%d\n", number());
    printf("%s\n", hello_world("Hello, ", "World!"));
    printf("%s\n", hello("World!"));
}
*/


/*
// "_main"
gcc msys64-mingw64.c -o msys64-mingw64
./msys64-mingw64

gcc -c -fPIC msys64-mingw64.c

// Create shared .SO library
gcc -shared -o msys64-mingw64.so msys64-mingw64.o
*/


// Output
/*
WARNING: Using incubator modules: jdk.incubator.foreign
warning: using incubating module(s): jdk.incubator.foreign
Note: Java16.java uses preview language features.
Note: Recompile with -Xlint:preview for details.
1 warning
OS Name: Mac OS X
OS Version: 11.5
OS Architecture: x86_64
Java Version: 16.0.2

# 1 # 389: Foreign Linker API (Incubator)
Hello, World!

# 2 # 394: Pattern Matching for instanceof
pattern instanceof String
((String)pattern).length():11
pattern instanceof String s
s.length():11

# 3 # 395: Records
1
Final
Records[number=2, text=Final.]

# 4 # 397: Sealed Classes (Second Preview)
class SealedTypePermitter
true

class SubSealedTypePermitted
false

class SubSealedTypePermittedHavingSubType
true

class SubSealedTypePermittedHavingSubTypeExtended
false
*/


/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
https://learn.microsoft.com/en-us/java/openjdk
*/