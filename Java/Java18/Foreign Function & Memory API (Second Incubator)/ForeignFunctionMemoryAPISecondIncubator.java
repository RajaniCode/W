// Java 18
// OpenJDK 18
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK18.0.2/jdk-18.0.2.jdk/Contents/Home/bin/":$PATH

// % java -Djava.library.path=. --enable-native-access=ALL-UNNAMED --add-modules jdk.incubator.foreign ForeignFunctionMemoryAPISecondIncubator.java
// Or
// % javac --add-modules jdk.incubator.foreign ForeignFunctionMemoryAPISecondIncubator.java
// % java -Djava.library.path=. --enable-native-access=ALL-UNNAMED --add-modules jdk.incubator.foreign ForeignFunctionMemoryAPISecondIncubator

// Java 17
// OpenJDK 17
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK17.0.2/jdk-17.0.2.jdk/Contents/Home/bin/":$PATH


/*
# 419: Foreign Function & Memory API (Second Incubator)
*/


import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import java.util.Arrays;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.NativeSymbol;
// Java 17
// import jdk.incubator.foreign.MemoryAccess;
import jdk.incubator.foreign.MemoryAddress;
// import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.SegmentAllocator;
import jdk.incubator.foreign.ValueLayout;

class ForeignFunctionMemoryAPISecondIncubator {
   private void radixsort() throws Throwable {

        // 1. Find foreign function on the C library path
        CLinker linker = CLinker.systemCLinker();
	// .get() // Or // .orElseThrow()
        MethodHandle radixSort = linker.downcallHandle(
            linker.lookup("radixsort").get(),
            FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_CHAR));

        // 2. Allocate on-heap memory to store four strings
        String[] javaStrings   = { "Delta", "Gamma", "Alpha", "Beta" };
        System.out.println("radixsort input: " + Arrays.toString(javaStrings));

        // 3. Allocate off-heap memory to store four pointers
	// https://openjdk.org/jeps/419 // Erratum // error: cannot find symbol ofSequence
        /*
	MemorySegment offHeap  = MemorySegment.allocateNative(
                             MemoryLayout.ofSequence(javaStrings.length,
                                                     ValueLayout.ADDRESS), ...);        
	*/

        SegmentAllocator allocator = SegmentAllocator.implicitAllocator();
        MemorySegment offHeap = allocator.allocateArray(ValueLayout.ADDRESS, javaStrings.length);

        // 4. Copy the strings from on-heap to off-heap
        for (int i = 0; i < javaStrings.length; i++) {
            // Allocate a string off-heap, then store a pointer to it
            MemorySegment cString = SegmentAllocator.implicitAllocator().allocateUtf8String(javaStrings[i]);
            offHeap.setAtIndex(ValueLayout.ADDRESS, i, cString);
        }

        // 5. Sort the off-heap data by calling the foreign function
	// invoke // unreported exception Throwable; must be caught or declared to be thrown
        radixSort.invoke(offHeap, javaStrings.length, MemoryAddress.NULL, '\0');

        // 6. Copy the (reordered) strings from off-heap to on-heap
        for (int i = 0; i < javaStrings.length; i++) {
            MemoryAddress cStringPtr = offHeap.getAtIndex(ValueLayout.ADDRESS, i);
            javaStrings[i] = cStringPtr.getUtf8String(0);
        }

        System.out.println("radixsort output: " + Arrays.toString(javaStrings));
    }

    private void strlen(String text) throws Throwable {
	System.out.println("strlen input: " + text);

        /*
        To downcall from Java to the strlen function defined in the standard C library
        size_t strlen(const char *s);
        */
        // A downcall method handle that exposes strlen can be obtained as follows
	// Java 17 // static CLinker.getInstance() // Returns the C linker for the current platform.
        /*
	MethodHandle strlenHandle  = CLinker.getInstance().downcallHandle(
    	    CLinker.systemLookup().lookup("strlen").get(),
    	    MethodType.methodType(long.class, MemoryAddress.class),
    	    FunctionDescriptor.of(CLinker.C_LONG, CLinker.C_POINTER)
	);
        */
	// Java 18 // static CLinker.systemCLinker() // Returns the C linker for the current platform.
	CLinker linker = CLinker.systemCLinker();
	MethodHandle strlenHandle = linker.downcallHandle(
    	    linker.lookup("strlen").get(),
    	    FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)
	);

        // Or // try(ResourceScope scopeResource = ResourceScope.newConfinedScope()) { // static ResourceScope.newConfinedScope() // Creates a new confined scope.
	    // Java 17 // static SegmentAllocator.arenaAllocator(ResourceScope scope) // Returns a native unbounded arena-based allocator.
            // SegmentAllocator allocatorSegment = SegmentAllocator.arenaAllocator(scopeResource);
	    // Java 18 // static SegmentAllocator.nativeAllocator(ResourceScope scope) // Returns a native allocator, associated with the provided scope.
	    // Or // SegmentAllocator allocatorSegment = SegmentAllocator.nativeAllocator(scopeResource);

            // Java 17
            /*
	    // Java 17 // static MemorySegment toCString(String str, SegmentAllocator allocator) // Converts a Java string into a UTF-8 encoded, null-terminated C string, storing the result into a native memory segment allocated using the provided allocator.
            // MemorySegment segmentMemory = CLinker.toCString(text, allocatorSegment);
            // Or
            // Java 17 // static MemorySegment toCString(String str, ResourceScope scope) // Converts a Java string into a UTF-8 encoded, null-terminated C string, storing the result into a native memory segment associated with the provided resource scope.      
            MemorySegment segmentMemory = CLinker.toCString(text, ResourceScope.newImplicitScope());
            */         
	    // Java 18 // default MemorySegment allocateUtf8String(String str) // Converts a Java string into a UTF-8 encoded, null-terminated C string, storing the result into a memory segment.
            // MemorySegment segmentMemory = allocatorSegment.allocateUtf8String(text);
            // Or //
	    // Java 18 // static SegmentAllocator.implicitAllocator() // Returns a native allocator which allocates segments in independent implicit scopes.
            MemorySegment segmentMemory = SegmentAllocator.implicitAllocator().allocateUtf8String(text);

	    // Java 17 // final Object invokeExact(Object... args) // Invokes the method handle, allowing any caller type descriptor, but requiring an exact type match.
            // long length = (long) strlenHandle.invokeExact(segmentMemory.address());	   
	    // Java 18
	    // https://openjdk.org/jeps/419 // Erratum // long len = strlen.invoke(cString);  // 5 // cString // segmentMemory
            /*
	    MemorySegment str = implicitAllocator().allocateUtf8String("Hello");
            long len = strlen.invoke(cString);  // 5
            */
            // final Object invoke(Object... args) // Invokes the method handle, allowing any caller type descriptor, and optionally performing conversions on arguments and return values.
            // invoke // unreported exception Throwable; must be caught or declared to be thrown
	    long length = (long) strlenHandle.invoke(segmentMemory); 
            System.out.println("strlen output: " + length);
        // Or //  }
    }


    // Java 17
    /*
    class Qsort {
        // Qsort: 1. Create a static method in Java that compares two long values, represented indirectly as MemoryAddress objects
        static int qsortCompare(MemoryAddress memoryAddressX, MemoryAddress memoryAddressY) {
            int x = MemoryAccess.getIntAtOffset(MemorySegment.globalNativeSegment(), memoryAddressX.toRawLongValue());
            int y = MemoryAccess.getIntAtOffset(MemorySegment.globalNativeSegment(), memoryAddressY.toRawLongValue());
            return x - y;
        }
    }
    */

    class Qsort {
        // Qsort: 1. Create a static method in Java that compares two long values, represented indirectly as MemoryAddress objects
    	static int qsortCompare(MemoryAddress memoryAddressX, MemoryAddress memoryAddressY) {
            return memoryAddressX.get(ValueLayout.JAVA_INT, 0) - memoryAddressY.get(ValueLayout.JAVA_INT, 0);
    	}
    }

    private void qsort(int... varargs) throws Throwable {
        System.out.println("qsort input: " + Arrays.toString(varargs));

        /*
        Consider the following function defined in the standard C library
        void qsort(void *base, size_t nmemb, size_t size, int (*compar)(const void *, const void *));
        */
        // To call qsort from Java, create a downcall method handle:
        // Find foreign function on the C library path
	// Java 17 // CLinker.getInstance() // Returns the C linker for the current platform.
        /*
        MethodHandle qsortHandle = CLinker.getInstance().downcallHandle(
            CLinker.systemLookup().lookup("qsort").get(),
            MethodType.methodType(void.class, MemoryAddress.class, long.class, long.class, MemoryAddress.class),
            FunctionDescriptor.ofVoid(CLinker.C_POINTER, CLinker.C_LONG, CLinker.C_LONG, CLinker.C_POINTER)
        );
        */
        // Java 18 // static CLinker.systemCLinker() // Returns the C linker for the current platform.
        CLinker linker = CLinker.systemCLinker();
  	MethodHandle qsortHandle = linker.downcallHandle(
    	    linker.lookup("qsort").get(),
    	    FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)
	);

        // Qsort: 2. Create a method handle pointing to the Java comparator method
 	// findStatic // unreported exception NoSuchMethodException; must be caught or declared to be thrown
	// findStatic // unreported exception IllegalAccessException; must be caught or declared to be thrown
        MethodHandle comparHandle = MethodHandles.lookup().findStatic(
	    Qsort.class, 
	    "qsortCompare",
	    MethodType.methodType(
	        int.class,
	        MemoryAddress.class,
		MemoryAddress.class
	    )
	);

	/*
	// Segment allocators
	// Memory allocation can often be a bottleneck when clients use off-heap memory.
	// The FFM API includes a SegmentAllocator abstraction, which defines useful operations to allocate and initialize memory segments. 
        // Segment allocators are obtained via factories in the SegmentAllocator interface.
        */

	// Java 18
        try(ResourceScope scopeResource = ResourceScope.newConfinedScope()) { // static ResourceScope.newConfinedScope() // Creates a new confined scope.
	    
	    // Java 17 // static SegmentAllocator.arenaAllocator(ResourceScope scope) // Returns a native unbounded arena-based allocator.
 	    // SegmentAllocator allocatorSegment = SegmentAllocator.arenaAllocator(scopeResource);
	    // Java 18 // static SegmentAllocator.newNativeArena(ResourceScope scope) // Returns a native unbounded arena-based allocator, with predefined block size and maximum arena size, associated with the provided scope.
            SegmentAllocator allocatorSegment = SegmentAllocator.newNativeArena(scopeResource);

	    // Java 17
	    /*
	    // Qsort: 3. Create a function pointer using CLinker::upcallStub
      	    // Java 17 // Describe the signature of the function pointer using layouts in the CLinker class
            // Java 17 // static CLinker.getInstance() // Returns the C linker for the current platform.
            MemoryAddress comparFunc = CLinker.getInstance().upcallStub(
                comparHandle,
                FunctionDescriptor.of(CLinker.C_INT, CLinker.C_POINTER, CLinker.C_POINTER),
                scopeResource
            );
	    */
	
	    // Java 18
	    // Qsort: 3. Create a function pointer using CLinker::upcallStub
            // Describe the signature of the function pointer using a FunctionDescriptor
	    // NativeSymbol // A native symbol models a reference to a location (typically the entry point of a function) in a native library.
            // linker
            // MemoryAddress upcallStub(MethodHandle target, FunctionDescriptor function, ResourceScope scope) // Allocates a native stub with given scope which can be passed to other foreign functions (as a function pointer); calling such a function pointer from native code will result in the execution of the provided method handle.
            // static ResourceScope.newImplicitScope() // Creates a new shared scope, managed by a private Cleaner instance.	   
	    NativeSymbol comparFunc = linker.upcallStub(
	 	comparHandle,
              	// A Java description of a C function implemented by a Java method! //
        	FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS), 
		ResourceScope.newImplicitScope()
     	    );

	    // Qsort: 4. Have a memory address, comparFunc, which points to a stub that can be used to invoke the Java comparator function
            // And so have all that needed to invoke the qsort downcall handle
	    // Java 17 // allocatorSegment
 	    // MemorySegment allocateArray = allocatorSegment.allocateArray(CLinker.C_INT, varargs);
            // Java 18 // static SegmentAllocator.implicitAllocator() // Returns a native allocator which allocates segments in independent implicit scopes.
	    MemorySegment allocateArray = SegmentAllocator.implicitAllocator().allocateArray(ValueLayout.JAVA_INT, varargs);

	    // Java 17 // final Object invokeExact(Object... args) // Invokes the method handle, allowing any caller type descriptor, but requiring an exact type match.
	    // qsortHandle.invokeExact(allocateArray.address(), (long)varargs.length, 4L, comparFunc);
            // Java 18
            // final Object invoke(Object... args) // Invokes the method handle, allowing any caller type descriptor, and optionally performing conversions on arguments and return values.
 	    // invoke // unreported exception Throwable; must be caught or declared to be thrown
	    qsortHandle.invoke(allocateArray, 10L, 4L, comparFunc);
	
	    // Java 17 // int[] toIntArray() Copy the contents of this memory segment into a fresh int array.
            // int[] sortedArray = allocateArray.toIntArray();
            // Java 18
	    // https://openjdk.org/jeps/419 // Erratum // int[] sorted = array.toIntArray(); // [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
            // int[] toArray(ValueLayout.OfInt elementLayout) // Copy the contents of this memory segment into a fresh int array.
	    int[] sortedArray = allocateArray.toArray(ValueLayout.JAVA_INT);

            System.out.println("qsort output: " + Arrays.toString(sortedArray));
        }
    }

    public void print() throws Throwable {
        System.out.println("# 419: Foreign Function & Memory API (Second Incubator)");
	radixsort();
        strlen("Hola!");
        qsort(5, 7, 9, 3, 4, 6, 1, 8, 2, 0);
    }

    public static void main(String[] args) throws Throwable {
        var propertiesSystem = new SystemProperties();
        propertiesSystem.print();

	var incubatorForeignFunctionMemoryAPISecond = new ForeignFunctionMemoryAPISecondIncubator();
        incubatorForeignFunctionMemoryAPISecond.print();
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
OS Version: 13.5.2
OS Architecture: aarch64
Java Version: 18.0.2

# 419: Foreign Function & Memory API (Second Incubator)
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
https://learn.microsoft.com/en-us/java/openjdk
*/