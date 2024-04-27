// Java 20
// OpenJDK 20
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK20.0.2/jdk-20.0.2.jdk/Contents/Home/bin/":$PATH

// % java --enable-preview --source 20 --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.concurrent,jdk.incubator.vector ForeignFunctionMemoryAPISecondPreview.java
// Or
// % javac -Xlint:preview --enable-preview --source 20 --add-modules=jdk.incubator.concurrent,jdk.incubator.vector ForeignFunctionMemoryAPISecondPreview.java
// % java --enable-preview --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.concurrent,jdk.incubator.vector ForeignFunctionMemoryAPISecondPreview

// Java 19
// OpenJDK 19
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK19.0.2/jdk-19.0.2.jdk/Contents/Home/bin/":$PATH


/*
# 434: Foreign Function & Memory API (Second Preview)
*/


import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;

// import java.lang.foreign.MemoryAddress;

// import java.lang.foreign.MemoryLayout; //

import java.lang.foreign.MemorySegment;

// import java.lang.foreign.MemorySession;

import java.lang.foreign.SegmentAllocator; //

import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import java.util.Arrays;

class ForeignFunctionMemoryAPISecondPreview {
    private void radixsort() throws Throwable {

	// 1. Find foreign function on the C library path
	Linker linkerNative = Linker.nativeLinker();
	SymbolLookup lookuppSymbol = linkerNative.defaultLookup();
	// .get() // Or // .orElseThrow()
        MethodHandle radixsortHandle = linkerNative.downcallHandle(
            // lookuppSymbol.lookup("radixsort").get(), 
            lookuppSymbol.find("radixsort").orElseThrow(), 
    	    FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_CHAR));

        // 2. Allocate on-heap memory to store four strings
        String[] javaStrings   = { "Delta", "Gamma", "Alpha", "Beta" };
        System.out.println("radixsort input: " + Arrays.toString(javaStrings));
	
	// 3. Use try-with-resources to manage the lifetime of off-heap memory
	try (Arena offHeap = Arena.openConfined()) { 	

	    // 4. Allocate a region of off-heap memory to store four pointers
    	    MemorySegment pointers = offHeap.allocateArray(ValueLayout.ADDRESS, javaStrings.length);

	    // 5. Copy the strings from on-heap to off-heap
    	    for (int i = 0; i < javaStrings.length; i++) {
        	MemorySegment cString = offHeap.allocateUtf8String(javaStrings[i]);
        	pointers.setAtIndex(ValueLayout.ADDRESS, i, cString);
    	    }

	    // 6. Sort the off-heap data by calling the foreign function
	    // invoke // unreported exception Throwable; must be caught or declared to be thrown
    	    radixsortHandle.invoke(pointers, javaStrings.length, MemorySegment.NULL, '\0');

	    // 7. Copy the (reordered) strings from off-heap to on-heap
    	    for (int i = 0; i < javaStrings.length; i++) {
        	MemorySegment cString = pointers.getAtIndex(ValueLayout.ADDRESS, i);
                // Java 20 Preview
		cString = MemorySegment.ofAddress(cString.address(), Long.MAX_VALUE);
                // Java 21 Preview
		// cString = cString.reinterpret(Long.MAX_VALUE);
       	        javaStrings[i] = cString.getUtf8String(0);
    	    }
    	}

	// 8. All off-heap memory is deallocated here
        System.out.println("radixsort output: " + Arrays.toString(javaStrings));
    }

    private void strlen(String text) throws Throwable {
        
	System.out.println("strlen input: " + text);

        try (Arena arena = Arena.openConfined()) {
            
            // 1. Allocate off-heap memory, and
            // 2. Dereference off-heap memory
            MemorySegment segmentMemory = arena.allocateUtf8String(text);
        
            // 3. Link and call C function
        
            // 3a. Obtain an instance of the native linker
            Linker linkerNative = Linker.nativeLinker();
        
            // 3b. Locate the address of the C function
            SymbolLookup lookupSymbol = linkerNative.defaultLookup();
            MemorySegment segmentMemoryLookup = lookupSymbol.find("strlen").get();
        
            // 3c. Create a description of the native function signature
            FunctionDescriptor descriptorFunction =
                FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS);
            
            // 3d. Create a downcall handle for the C function    
            MethodHandle strlenHandle = linkerNative.downcallHandle(segmentMemoryLookup, descriptorFunction);

            // 3e. Call the C function directly from Java
	    // invokeExact // unreported exception Throwable; must be caught or declared to be thrown
            System.out.println("strlen output: " + (long)strlenHandle.invokeExact(segmentMemory));
        } 
    }

    private void strlenJava19Preview20(String text) throws Throwable {
	System.out.println("Java 19 Preview vs. Java 20 Preview");
	System.out.println("strlen input: " + text);

	// Java 19 Preview // lookup
        // Java 20 Preview // find
	Linker linkerNative = Linker.nativeLinker();
	MethodHandle strlenHandle = linkerNative.downcallHandle(
    	    linkerNative.defaultLookup().find("strlen").get(),
    	    FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)
	);

        // Java 19 Preview 
	// try (MemorySession sessionMemory = MemorySession.openConfined()) { 
	// Java 20 Preview  
	try (Arena arena = Arena.openConfined()) {

    	    // Java 19 Preview
	    // SegmentAllocator allocatorSegment = SegmentAllocator.newNativeArena(sessionMemory);
            // Java 20 Preview      
            // Or // SegmentAllocator allocatorSegment = SegmentAllocator.slicingAllocator(arena.allocate(1024));

   	    // Java 19 Preview // Java 20 Preview
	    // Or // MemorySegment segmentMemory = allocatorSegment.allocateUtf8String(text);
	    // Java 20 Preview
	    MemorySegment segmentMemory = arena.allocateUtf8String(text); // Or //

	    // invoke // unreported exception Throwable; must be caught or declared to be thrown
	    long length = (long) strlenHandle.invoke(segmentMemory); 
            System.out.println("strlen output: " + length);
         }
    }
        
    class Qsort {
        static int qsortCompare(MemorySegment addr1, MemorySegment addr2) {
            return addr1.get(ValueLayout.JAVA_INT, 0) - addr2.get(ValueLayout.JAVA_INT, 0);
        }
    }
    
    // Obtain instance of native linker
    private final Linker nativeLinker = Linker.nativeLinker();
    
    // Create downcall handle for qsort
    private MethodHandle qsortHandle = nativeLinker.downcallHandle(
        nativeLinker.defaultLookup().find("qsort").get(),
        FunctionDescriptor.ofVoid(
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_LONG,
            ValueLayout.JAVA_LONG,
            ValueLayout.ADDRESS));

    // A Java description of a C function implemented by a Java method
    private final FunctionDescriptor qsortCompareDesc = FunctionDescriptor.of(
        ValueLayout.JAVA_INT,
        ValueLayout.ADDRESS.asUnbounded(),
        ValueLayout.ADDRESS.asUnbounded());

    // Create method handle for qsortCompare
    private final MethodHandle compareHandle;

    {
        try {
	    // findStatic // unreported exception NoSuchMethodException; must be caught or declared to be thrown
	    // findStatic // unreported exception IllegalAccessException; must be caught or declared to be thrown
            compareHandle = MethodHandles.lookup().findStatic(
                Qsort.class,
                "qsortCompare",
                qsortCompareDesc.toMethodType());
        } catch (Exception e) {
            throw new AssertionError(
                "Problem creating method handle compareHandle", e);
        }
    }    
    
    private void qsort(int... unsortedArray) throws Throwable {
        System.out.println("qsort input: " + Arrays.toString(unsortedArray));

        int[] sorted = null;        
        
        try (Arena arena = Arena.openConfined()) {                    
        
            // Allocate off-heap memory and store unsortedArray in it                
            MemorySegment array = arena.allocateArray(
                                          ValueLayout.JAVA_INT,
                                          unsortedArray);        
        
            // Create function pointer for qsortCompare
            MemorySegment compareFunc = nativeLinker.upcallStub(
                compareHandle,
                qsortCompareDesc,
                arena.scope());
                    
            // Call qsort
	    // invoke // unreported exception Throwable; must be caught or declared to be thrown      
            qsortHandle.invoke(array, (long)unsortedArray.length,
                ValueLayout.JAVA_INT.byteSize(), compareFunc);
            
            // Access off-heap memory
            sorted = array.toArray(ValueLayout.JAVA_INT);                
        }

        System.out.println("qsort output: " + Arrays.toString(sorted));
    }        

    private void print() throws Throwable {
        System.out.println("# 434: Foreign Function & Memory API (Second Preview)");
	radixsort();
        strlen("Hola!");
	strlenJava19Preview20("Hola!");
        qsort(5, 7, 9, 3, 4, 6, 1, 8, 2, 0);
    }

    public static void main(String[] args) throws Throwable {
        var propertiesSystem = new SystemProperties();
        propertiesSystem.print();

	var previewForeignFunctionMemoryAPISecond = new ForeignFunctionMemoryAPISecondPreview();
        previewForeignFunctionMemoryAPISecond.print();
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
Note: ForeignFunctionMemoryAPISecondPreview.java uses preview features of Java SE 20.
Note: Recompile with -Xlint:preview for details.
OS Name: Mac OS X
OS Version: 14.0
OS Architecture: aarch64
Java Version: 20.0.2

# 434: Foreign Function & Memory API (Second Preview)
radixsort input: [Delta, Gamma, Alpha, Beta]
radixsort output: [Alpha, Beta, Delta, Gamma]
strlen input: Hola!
strlen output: 5
Java 19 Preview vs. Java 20 Preview
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