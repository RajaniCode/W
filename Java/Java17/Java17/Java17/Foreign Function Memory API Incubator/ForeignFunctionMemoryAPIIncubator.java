// Java 17
// OpenJDK 17
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK17.0.2/jdk-17.0.2.jdk/Contents/Home/bin/":$PATH

// Java 17
// Microsoft Build of OpenJDK
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/MicrosoftBuildofOpenJDK/jdk-17.0.8.1+1/Contents/Home/bin/":$PATH

// % java --enable-native-access=ALL-UNNAMED --add-modules jdk.incubator.foreign ForeignFunctionMemoryAPIIncubator.java
// Or
// % javac --add-modules jdk.incubator.foreign ForeignFunctionMemoryAPIIncubator.java
// % java --enable-native-access=ALL-UNNAMED --add-modules jdk.incubator.foreign ForeignFunctionMemoryAPIIncubator

// Java 16
// OpenJDK 16
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK16.0.2/jdk-16.0.2.jdk/Contents/Home/bin/":$PATH


/*
# 412: Foreign Function & Memory API (Incubator)
*/


import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.MemoryAccess;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.SegmentAllocator;

class ForeignFunctionMemoryAPIIncubator {

   private void radixsort() throws Throwable {

	// 1. Find foreign function on the C library path
	MethodHandle radixsortHandle = CLinker.getInstance().downcallHandle(
                             CLinker.systemLookup().lookup("radixsort").get(),
    	    MethodType.methodType(void.class, MemoryAddress.class, int.class, MemoryAddress.class, int.class),
    	    FunctionDescriptor.ofVoid(CLinker.C_POINTER, CLinker.C_INT, CLinker.C_POINTER, CLinker.C_INT));


        // 2. Allocate on-heap memory to store four strings
        String[] javaStrings   = { "Delta", "Gamma", "Alpha", "Beta" };
        System.out.println("radixsort input: " + Arrays.toString(javaStrings));

        // 3. Allocate off-heap memory to store four pointers
	// https://openjdk.org/jeps/412 // Erratum // error: cannot find symbol ofSequence
	/*
	MemorySegment offHeap  = MemorySegment.allocateNative(
                             MemoryLayout.ofSequence(javaStrings.length,
                                                     CLinker.C_POINTER), ...);
        */

	// Or // try (ResourceScope scopeResource = ResourceScope.newConfinedScope()) {
	    // Or // SegmentAllocator allocatorSegment = SegmentAllocator.arenaAllocator(scopeResource);
	    // Or //
	    SegmentAllocator allocatorSegment = SegmentAllocator.arenaAllocator(ResourceScope.newImplicitScope()); 
            MemorySegment offHeap = allocatorSegment.allocateArray(CLinker.C_POINTER, javaStrings.length);

	    // 4. Copy the strings from on-heap to off-heap
	    for (int i = 0; i < javaStrings.length; i++) {
    	        // Allocate a string off-heap, then store a pointer to it
    	        MemorySegment cString = CLinker.toCString(javaStrings[i], ResourceScope.newImplicitScope());
    	        MemoryAccess.setAddressAtIndex(offHeap, i, cString.address());
	    }

            // 5. Sort the off-heap data by calling the foreign function
	    // invoke // unreported exception Throwable; must be caught or declared to be thrown
            radixsortHandle.invoke(offHeap.address(), javaStrings.length, MemoryAddress.NULL, '\0');

            // 6. Copy the (reordered) strings from off-heap to on-heap
	    for (int i = 0; i < javaStrings.length; i++) {
	    	MemoryAddress cStringPtr = MemoryAccess.getAddressAtIndex(offHeap, i);
	        // https://openjdk.org/jeps/412 // Erratum // error: cannot find symbol toJavaStringRestricted
	    	// javaStrings[i] = CLinker.toJavaStringRestricted(cStringPtr);
	    	javaStrings[i] = CLinker.toJavaString(cStringPtr);
	    }
        // Or // }
        System.out.println("radixsort output: " + Arrays.toString(javaStrings));
    }

    private void strlen(String text) throws Throwable {
        System.out.println("strlen input: " + text);

        /*
        To downcall from Java to the strlen function defined in the standard C library
        size_t strlen(const char *s);
        */
        // A downcall method handle that exposes strlen can be obtained as follows
	MethodHandle strlenHandle  = CLinker.getInstance().downcallHandle(
    	    CLinker.systemLookup().lookup("strlen").get(),
    	    MethodType.methodType(long.class, MemoryAddress.class),
    	    FunctionDescriptor.of(CLinker.C_LONG, CLinker.C_POINTER)
	);

        // Or // try(ResourceScope scopeResource = ResourceScope.newConfinedScope()) {
            // Or // SegmentAllocator allocatorSegment = SegmentAllocator.arenaAllocator(scopeResource);

            // MemorySegment segmentMemory = CLinker.toCString(text, allocatorSegment);
	    // Or //
            MemorySegment segmentMemory = CLinker.toCString(text, ResourceScope.newImplicitScope());

	    // invokeExact // unreported exception Throwable; must be caught or declared to be thrown
            long length = (long) strlenHandle.invokeExact(segmentMemory.address());

            System.out.println("strlen output: " + length);
        // Or // }
    }

    class Qsort {
        // Qsort: 1. Create a static method in Java that compares two long values, represented indirectly as MemoryAddress objects
        static int qsortCompare(MemoryAddress memoryAddressX, MemoryAddress memoryAddressY) {
            int x = MemoryAccess.getIntAtOffset(MemorySegment.globalNativeSegment(), memoryAddressX.toRawLongValue());
            int y = MemoryAccess.getIntAtOffset(MemorySegment.globalNativeSegment(), memoryAddressY.toRawLongValue());
            return x - y;
        }
    }

    private void qsort(int... varargs) throws Throwable {
        System.out.println("qsort input: " + Arrays.toString(varargs));

        /*
        Consider the following function defined in the standard C library
        void qsort(void *base, size_t nmemb, size_t size, nt (*compar)(const void *, const void *));
        */
        // To call qsort from Java, create a downcall method handle
        // Find foreign function on the C library path
        MethodHandle qsortHandle = CLinker.getInstance().downcallHandle(
            CLinker.systemLookup().lookup("qsort").get(),
            MethodType.methodType(void.class, MemoryAddress.class, long.class, long.class, MemoryAddress.class),
            FunctionDescriptor.ofVoid(CLinker.C_POINTER, CLinker.C_LONG, CLinker.C_LONG, CLinker.C_POINTER)
        );

        // Qsort: 2. Create a method handle pointing to the Java comparator method
        // findStatic // unreported exception NoSuchMethodException; must be caught or declared to be thrown
        // findStatic // unreported exception IllegalAccessException; must be caught or declared to be thrown
        MethodHandle comparHandle = MethodHandles.lookup()
            .findStatic(Qsort.class, "qsortCompare",
                MethodType.methodType(int.class, MemoryAddress.class, MemoryAddress.class));

	/*
	// Segment allocators
	// Memory allocation can often be a bottleneck when clients use off-heap memory.
	// The FFM API includes a SegmentAllocator abstraction, which defines useful operations to allocate and initialize memory segments. 
        // Segment allocators are obtained via factories in the SegmentAllocator interface.
        */
        try(ResourceScope scopeResource = ResourceScope.newConfinedScope()) {
            SegmentAllocator allocatorSegment = SegmentAllocator.arenaAllocator(scopeResource);

	    // Qsort: 3. Create a function pointer using CLinker::upcallStub
            // Describe the signature of the function pointer using layouts in the CLinker class
            MemoryAddress comparFunc = CLinker.getInstance().upcallStub(
                comparHandle,
                FunctionDescriptor.of(CLinker.C_INT, CLinker.C_POINTER, CLinker.C_POINTER),
                scopeResource
            );

	    // Qsort: 4. Have a memory address, comparFunc, which points to a stub that can be used to invoke the Java comparator function
            // And so have all that needed to invoke the qsort downcall handle
            MemorySegment allocateArray = allocatorSegment.allocateArray(CLinker.C_INT, varargs);

	    // invokeExact // unreported exception Throwable; must be caught or declared to be thrown
            qsortHandle.invokeExact(allocateArray.address(), (long)varargs.length, 4L, comparFunc);

            int[] sortedArray = allocateArray.toIntArray();

            System.out.println("qsort output: " + Arrays.toString(sortedArray));
        }
    }

    public void print() throws Throwable {
        System.out.println("# 412: Foreign Function & Memory API (Incubator)");
	radixsort();
        strlen("Hola!");
        qsort(5, 7, 9, 3, 4, 6, 1, 8, 2, 0);
    }

    public static void main(String[] args) throws Throwable {
        var propertiesSystem = new SystemProperties();
        propertiesSystem.print();

	var functionMemoryAPIIncubatorForeign = new ForeignFunctionMemoryAPIIncubator();
        functionMemoryAPIIncubatorForeign.print();
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
OS Version: 14.0
OS Architecture: aarch64
Java Version: 17.0.2

# 412: Foreign Function & Memory API (Incubator)
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