// Java 21
// OpenJDK 21
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK21/jdk-21.jdk/Contents/Home/bin/":$PATH

// Java 21
// Microsoft Build of OpenJDK
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/MicrosoftBuildofOpenJDK/jdk-21+35/Contents/Home/bin/":$PATH

// % java --enable-preview --source 21 --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.vector ForeignFunctionMemoryAPIThirdPreview.java
// Or
// % javac -Xlint:preview --enable-preview --source 21 --add-modules=jdk.incubator.vector ForeignFunctionMemoryAPIThirdPreview.java
// % java --enable-preview --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.vector ForeignFunctionMemoryAPIThirdPreview

// Java 20
// OpenJDK 20
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK20.0.2/jdk-20.0.2.jdk/Contents/Home/bin/":$PATH


/*
# 442: Foreign Function & Memory API (Third Preview)
*/


import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;

// import java.lang.foreign.MemoryAddress;

import java.lang.foreign.MemorySegment;

// import java.lang.foreign.MemorySession;

import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import java.util.Arrays;

class ForeignFunctionMemoryAPIThirdPreview {
    private void radixsort() throws Throwable {

	// 1. Find foreign function on the C library path
	Linker linkerNative = Linker.nativeLinker();
	SymbolLookup lookupSymbol = linkerNative.defaultLookup();
	// .get() // Or // .orElseThrow()
        MethodHandle radixsortHandle = linkerNative.downcallHandle(
            // lookupSymbol.lookup("radixsort").get(), 
            lookupSymbol.find("radixsort").orElseThrow(), 
    	    FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_CHAR));

        // 2. Allocate on-heap memory to store four strings
        String[] javaStrings   = { "Delta", "Gamma", "Alpha", "Beta" };
        System.out.println("radixsort input: " + Arrays.toString(javaStrings));
	
	// 3. Use try-with-resources to manage the lifetime of off-heap memory
	// Java 20 Preview
	// try (Arena offHeap = Arena.openConfined()) {
	// Java 21 Preview
 	try (Arena offHeap = Arena.ofConfined()) {   	

	    // 4. Allocate a region of off-heap memory to store four pointers
    	    MemorySegment pointers = offHeap.allocateArray(ValueLayout.ADDRESS, javaStrings.length);
    	    
	    // 5. Copy the strings from on-heap to off-heap
    	    for (int i = 0; i < javaStrings.length; i++) {
        	MemorySegment cString = offHeap.allocateUtf8String(javaStrings[i]);
        	pointers.setAtIndex(ValueLayout.ADDRESS, i, cString);
                // System.out.println(cString.getUtf8String(0));    
                // System.out.println(cString);   
    	    }
    	
	    // 6. Sort the off-heap data by calling the foreign function
	    // invoke // unreported exception Throwable; must be caught or declared to be thrown
    	    radixsortHandle.invoke(pointers, javaStrings.length, MemorySegment.NULL, '\0');
    	
	    // 7. Copy the (reordered) strings from off-heap to on-heap     
    	    for (int i = 0; i < javaStrings.length; i++) {
        	MemorySegment cString = pointers.getAtIndex(ValueLayout.ADDRESS, i);
                // Java 20 Preview
		// Java 21 Preview // error: method ofAddress in interface MemorySegment cannot be applied to given types
                // cString = MemorySegment.ofAddress(cString.address(), Long.MAX_VALUE); 
                // Java 21 Preview
		cString = cString.reinterpret(Long.MAX_VALUE);
        	javaStrings[i] = cString.getUtf8String(0);
    	    }   
    	}

	// 8. All off-heap memory is deallocated here
        System.out.println("radixsort output: " + Arrays.toString(javaStrings));
    }

    private void strlen(String text) throws Throwable {
        
	System.out.println("strlen input: " + text);
	
	// Java 20 Preview
        // try (Arena arena = Arena.openConfined()) {
	// Java 21 Preview
        try (Arena arena = Arena.ofConfined()) {
            
            // Allocate off-heap memory and
            // copy the argument, a Java string, into off-heap memory
            MemorySegment segmentMemory = arena.allocateUtf8String(text);
        
            // Link and call the C function strlen
        
            // Obtain an instance of the native linker
            Linker linkerNative = Linker.nativeLinker();
        
            // Locate the address of the C function signature
            SymbolLookup lookupSymbol = linkerNative.defaultLookup();
            MemorySegment segmentMemoryLookup = lookupSymbol.find("strlen").get();
        
            // Create a description of the C function
            FunctionDescriptor descriptorFunction =
                FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS);
            
            // Create a downcall handle for the C function    
            MethodHandle strlenHandle = linkerNative.downcallHandle(segmentMemoryLookup, descriptorFunction);

            // Call the C function directly from Java
	    // invokeExact // unreported exception Throwable; must be caught or declared to be thrown
	    System.out.println("strlen output: " + (long)strlenHandle.invokeExact(segmentMemory));
        } 
    }
     
    class Qsort {
        static int qsortCompare(MemorySegment elem1, MemorySegment elem2) {
            return Integer.compare(elem1.get(ValueLayout.JAVA_INT, 0), elem2.get(ValueLayout.JAVA_INT, 0));
        }
    }
    
    // Obtain instance of native linker
    private final Linker nativeLinker = Linker.nativeLinker();
    
    private void qsort(int... unsortedArray) throws Throwable {
        System.out.println("qsort input: " + Arrays.toString(unsortedArray));
        
        int[] sorted = null;
        
        // Create downcall handle for qsort
        MethodHandle qsortHandle = nativeLinker.downcallHandle(
            nativeLinker.defaultLookup().find("qsort").get(),
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS,
                                      ValueLayout.JAVA_LONG,
                                      ValueLayout.JAVA_LONG,
                                      ValueLayout.ADDRESS));
        
        // Create method handle for qsortCompare
	// findStatic // unreported exception NoSuchMethodException; must be caught or declared to be thrown
	// findStatic // unreported exception IllegalAccessException; must be caught or declared to be thrown
        MethodHandle comparHandle = MethodHandles.lookup()
            .findStatic(Qsort.class,
                        "qsortCompare",
                        MethodType.methodType(int.class,
                                              MemorySegment.class,
                                              MemorySegment.class));
                                              
        // Create a Java description of a C function implemented by a Java method
        FunctionDescriptor qsortCompareDesc = FunctionDescriptor.of(
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS.withTargetLayout(ValueLayout.JAVA_INT),
            ValueLayout.ADDRESS.withTargetLayout(ValueLayout.JAVA_INT));


        // Create function pointer for qsortCompare
        MemorySegment compareFunc = nativeLinker.upcallStub(comparHandle,
                                                      qsortCompareDesc,
                                                      Arena.ofAuto());        
        
        try (Arena arena = Arena.ofConfined()) {                    
        
            // Allocate off-heap memory and store unsortedArray in it                
            MemorySegment array = arena.allocateArray(ValueLayout.JAVA_INT,
                                                      unsortedArray);        
                    
            // Call qsort
	    // invoke // unreported exception Throwable; must be caught or declared to be thrown     
            qsortHandle.invoke(array,
                        (long)unsortedArray.length,
                        ValueLayout.JAVA_INT.byteSize(),
                        compareFunc);
            
            // Access off-heap memory
            sorted = array.toArray(ValueLayout.JAVA_INT);              
        }

        System.out.println("qsort output: " + Arrays.toString(sorted));
    }        

    public void print() throws Throwable {
        System.out.println("# 442: Foreign Function & Memory API (Third Preview)");
	radixsort();
        strlen("Hola!");
        qsort(5, 7, 9, 3, 4, 6, 1, 8, 2, 0);
    }

    public static void main(String[] args) throws Throwable {
        var propertiesSystem = new SystemProperties();
        propertiesSystem.print();

	var previewForeignFunctionMemoryAPIThird = new ForeignFunctionMemoryAPIThirdPreview();
        previewForeignFunctionMemoryAPIThird.print();
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
Note: ForeignFunctionMemoryAPIThirdPreview.java uses preview features of Java SE 21.
Note: Recompile with -Xlint:preview for details.
OS Name: Mac OS X
OS Version: 14.0
OS Architecture: aarch64
Java Version: 21

# 442: Foreign Function & Memory API (Third Preview)
radixsort input: [Delta, Gamma, Alpha, Beta]
radixsort output: [Alpha, Beta, Delta, Gamma]
strlen input: Hola!
strlen output: 5
qsort input: [5, 7, 9, 3, 4, 6, 1, 8, 2, 0]
qsort output: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
*/


/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
*/