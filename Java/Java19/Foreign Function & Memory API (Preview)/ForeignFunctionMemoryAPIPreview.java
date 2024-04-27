// Java 19
// OpenJDK 19
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK19.0.2/jdk-19.0.2.jdk/Contents/Home/bin/":$PATH

// % java --enable-preview --source 19 --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.vector ForeignFunctionMemoryAPIPreview.java
// Or
// % javac -Xlint:preview --enable-preview --source 19 --add-modules=jdk.incubator.vector ForeignFunctionMemoryAPIPreview.java
// % java --enable-preview --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.vector ForeignFunctionMemoryAPIPreview

// Java 18
// OpenJDK 18
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK18.0.2/jdk-18.0.2.jdk/Contents/Home/bin/":$PATH


/*
# 424: Foreign Function & Memory API (Preview)
*/


import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import java.util.Arrays;

class ForeignFunctionMemoryAPIPreview {
    private void radixsort() throws Throwable {

        // 1. Find foreign function on the C library path
        Linker linker = Linker.nativeLinker();
        SymbolLookup stdlib = linker.defaultLookup();
	// .get() // Or // .orElseThrow()
        MethodHandle radixSort = linker.downcallHandle(
            stdlib.lookup("radixsort").get(), 
    	    FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_CHAR));

        // 2. Allocate on-heap memory to store four strings
        String[] javaStrings   = { "Delta", "Gamma", "Alpha", "Beta" };
        System.out.println("radixsort input: " + Arrays.toString(javaStrings));

        // 3. Allocate off-heap memory to store four pointers
        SegmentAllocator allocator = SegmentAllocator.implicitAllocator();
        MemorySegment offHeap = allocator.allocateArray(ValueLayout.ADDRESS, javaStrings.length);

        // 4. Copy the strings from on-heap to off-heap
        for (int i = 0; i < javaStrings.length; i++) {
            // Allocate a string off-heap, then store a pointer to it
            MemorySegment cString = allocator.allocateUtf8String(javaStrings[i]);
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
        /*
	// Java 18 // static CLinker.systemCLinker() // Returns the C linker for the current platform.
	CLinker linker = CLinker.systemCLinker();
	MethodHandle strlenHandle = linker.downcallHandle(
    	    linker.lookup("strlen").get(),
    	    FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)
	);
        */
	// Java 19 Preview
	Linker linker = Linker.nativeLinker();
	MethodHandle strlenHandle = linker.downcallHandle(
    	    linker.defaultLookup().lookup("strlen").get(),
    	    FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)
	);

        // Or // Java 18
        // try(ResourceScope scopeResource = ResourceScope.newConfinedScope()) { // static ResourceScope.newConfinedScope() // Creates a new confined scope.
	    // Or // Java 18 // static SegmentAllocator.nativeAllocator(ResourceScope scope) // Returns a native allocator, associated with the provided scope.
            // SegmentAllocator allocatorSegment = SegmentAllocator.nativeAllocator(scopeResource);

        // Or // Java 19 Preview 
	// try (MemorySession sessionMemory = MemorySession.openConfined()) { // static MemorySession.openConfined() // Creates a closeable confined memory session.
    	    // Or // Java 19 Preview  // static SegmentAllocator.newNativeArena(MemorySession session) // Creates an unbounded arena-based allocator used to allocate native memory segments.
	    // SegmentAllocator allocatorSegment = SegmentAllocator.newNativeArena(sessionMemory);
      
	    // Java 18 // default MemorySegment allocateUtf8String(String str) // Converts a Java string into a UTF-8 encoded, null-terminated C string, storing the result into a memory segment.
            // MemorySegment segmentMemory = allocatorSegment.allocateUtf8String(text);
            // Or //
	    // Java 18 // static SegmentAllocator.implicitAllocator() // Returns a native allocator which allocates segments in independent implicit scopes.
            // MemorySegment segmentMemory = SegmentAllocator.implicitAllocator().allocateUtf8String(text);
   	    // Java 19 Preview // default MemorySegment allocateUtf8String(String str) // Converts a Java string into a UTF-8 encoded, null-terminated C string, storing the result into a memory segmen
	    // MemorySegment segmentMemory = allocatorSegment.allocateUtf8String(text);
	    // Or //
	    // Java 19 Preview // static SegmentAllocator.implicitAllocator() // Returns a native allocator which allocates segments in independent implicit scopes.
	    MemorySegment segmentMemory = SegmentAllocator.implicitAllocator().allocateUtf8String(text);

	    // Java 18
            // final Object invoke(Object... args) // Invokes the method handle, allowing any caller type descriptor, and optionally performing conversions on arguments and return values.       
	    // https://openjdk.org/jeps/419 // Erratum // long len = strlen.invoke(cString);  // 5 // cString // segmentMemory
            /*
	    MemorySegment str = implicitAllocator().allocateUtf8String("Hello");
            long len = strlen.invoke(cString);  // 5
            */
	    // invoke // unreported exception Throwable; must be caught or declared to be thrown
            // long length = (long) strlenHandle.invoke(segmentMemory); 
	    // Java 19 Preview
  	    // final Object invoke(Object... args) Invokes the method handle, allowing any caller type descriptor, and optionally performing conversions on arguments and return values.
            // https://openjdk.org/jeps/424// Erratum
	    /*
	    MemorySegment str = implicitAllocator().allocateUtf8String("Hello");
	    long len = strlen.invoke(cString);  // 5
	    */
	    // invoke // unreported exception Throwable; must be caught or declared to be thrown
	    long length = (long) strlenHandle.invoke(segmentMemory); 
            System.out.println("strlen output: " + length);
         // Or // }
    }

    // Java 18
    /*
    class Qsort {
        // Qsort: 1. Create a static method in Java that compares two long values, represented indirectly as MemoryAddress objects
    	static int qsortCompare(MemoryAddress memoryAddressX, MemoryAddress memoryAddressY) {
            return memoryAddressX.get(ValueLayout.JAVA_INT, 0) - memoryAddressY.get(ValueLayout.JAVA_INT, 0);
    	}
    }
    */

    // Java 19 Preview
    class Qsort {
        static int qsortCompare(MemoryAddress memoryAddressX, MemoryAddress memoryAddressY) {
        return Integer.compare(memoryAddressX.get(ValueLayout.JAVA_INT, 0), memoryAddressY.get(ValueLayout.JAVA_INT, 0));
        }
    }

    private void qsort(int... varargs) throws Throwable {
        System.out.println("qsort input: " + Arrays.toString(varargs));

        /*
        Consider the following function defined in the standard C library
        void qsort(void *base, size_t nmemb, size_t size, int (*compar)(const void *, const void *));
        */
        // To call qsort from Java, create a downcall method handle
        // Find foreign function on the C library path
	// Java 18
        /*
        // Java 18 // static CLinker.systemCLinker() // Returns the C linker for the current platform.
        CLinker linker = CLinker.systemCLinker();
  	MethodHandle qsortHandle = linker.downcallHandle(
    	    linker.lookup("qsort").get(),
    	    FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)
	);
        */
	// Java 19 Preview
	Linker linker = Linker.nativeLinker();
	MethodHandle qsortHandle = linker.downcallHandle(
    	    linker.defaultLookup().lookup("qsort").get(),
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
	// The FFM API therefore includes a SegmentAllocator abstraction, which defines useful operations to allocate and initialize memory segments. 
	// Segment allocators are obtained via factories in the SegmentAllocator interface. 
	// One such factory returns the implicit allocator, that is, an allocator which allocates native segments backed by a fresh implicit session. 
	// Other, more optimized allocators are also provided.
        */

	// Java 18 
        // try(ResourceScope scopeResource = ResourceScope.newConfinedScope()) { // static ResourceScope.newConfinedScope() // Creates a new confined scope.
	// Java 19 Preview 
	try (MemorySession sessionMemory = MemorySession.openConfined()) { // static MemorySession.openConfined() // Creates a closeable confined memory session.

	    // Java 18 // static SegmentAllocator.newNativeArena(ResourceScope scope) // Returns a native unbounded arena-based allocator, with predefined block size and maximum arena size, associated with the provided scope.
            // SegmentAllocator allocatorSegment = SegmentAllocator.newNativeArena(scopeResource);
	    // Java 19 Preview // static SegmentAllocator.newNativeArena(MemorySession session) // Creates an unbounded arena-based allocator used to allocate native memory segments.
    	    SegmentAllocator allocatorSegment = SegmentAllocator.newNativeArena(sessionMemory);

	    // Java 18
	    /*
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
	    */
	    // Java 19 Preview
	    // Qsort: 3. Create a function pointer using Linker::upcallStub
            // Describe the signature of the function pointer using a FunctionDescriptor
	    // MemorySegment // A memory segment models a contiguous region of memory
            // linker
            // MemorySegment upcallStub(MethodHandle target, FunctionDescriptor function, MemorySession session) // Creates a stub which can be passed to other foreign functions as a function pointer, with the given memory session.
            // static MemorySession.openImplicit() // Creates a non-closeable shared memory session, managed by a private Cleaner instance.
	    MemorySegment comparFunc = linker.upcallStub(comparHandle,
	        /* A Java description of a C function implemented by a Java method! */
	        FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS),
	        MemorySession.openImplicit()
	    );

	    // Qsort: 4. Have a memory address, comparFunc, which points to a stub that can be used to invoke the Java comparator function
            // And so have all that needed to invoke the qsort downcall handle
            // Java 18 // static SegmentAllocator.implicitAllocator() // Returns a native allocator which allocates segments in independent implicit scopes.
	    // MemorySegment allocateArray = SegmentAllocator.implicitAllocator().allocateArray(ValueLayout.JAVA_INT, varargs);
	    // Java 19 Preview // default MemorySegment allocateArray(ValueLayout.OfIntPREVIEW elementLayout, int... elements) Allocates a memory segment with the given layout and initializes it with the given int elements.
	    MemorySegment allocateArray = allocatorSegment.allocateArray(ValueLayout.JAVA_INT, varargs);

            // Java 18
            // final Object invoke(Object... args) // Invokes the method handle, allowing any caller type descriptor, and optionally performing conversions on arguments and return values.
 	    // invoke // unreported exception Throwable; must be caught or declared to be thrown
	    // qsortHandle.invoke(allocateArray, 10L, 4L, comparFunc);
	    // Java 19 Preview 
            // final Object invoke(Object... args) Invokes the method handle, allowing any caller type descriptor, and optionally performing conversions on arguments and return values.
            // invoke // error: unreported exception Throwable; must be caught or declared to be thrown
	    qsortHandle.invoke(allocateArray, 10L, ValueLayout.JAVA_INT.byteSize(), comparFunc);

            // Java 18
	    // https://openjdk.org/jeps/419 // Erratum // int[] sorted = array.toIntArray(); // [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
            // int[] toArray(ValueLayout.OfInt elementLayout) // Copy the contents of this memory segment into a fresh int array.
	    // int[] sortedArray = allocateArray.toArray(ValueLayout.JAVA_INT);
	    // Java 19 Preview
	    // https://openjdk.org/jeps/424// Erratum // int[] sorted = array.toIntArray(); // [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
	    // int[] toArray(ValueLayout.OfInt elementLayout) // Copy the contents of this memory segment into a new int array.
	    int[] sortedArray = allocateArray.toArray(ValueLayout.JAVA_INT); 

            System.out.println("qsort output: " + Arrays.toString(sortedArray));
        }
    }

    public void print() throws Throwable {
        System.out.println("# 424: Foreign Function & Memory API (Preview)");
	radixsort();
        strlen("Hola!");
        qsort(5, 7, 9, 3, 4, 6, 1, 8, 2, 0);
    }

    public static void main(String[] args) throws Throwable {
        var propertiesSystem = new SystemProperties();
        propertiesSystem.print();

	var previewForeignFunctionMemoryAPI = new ForeignFunctionMemoryAPIPreview();
        previewForeignFunctionMemoryAPI.print();
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
warning: using incubating module(s): jdk.incubator.vector
Note: ForeignFunctionMemoryAPIPreview.java uses preview features of Java SE 19.
Note: Recompile with -Xlint:preview for details.
1 warning
OS Name: Mac OS X
OS Version: 13.5.2
OS Architecture: aarch64
Java Version: 19.0.2

# 424: Foreign Function & Memory API (Preview)
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