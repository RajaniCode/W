// Java 18
// OpenJDK 18
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK18.0.2/jdk-18.0.2.jdk/Contents/Home/bin/":$PATH

// % java --enable-preview --source 18 --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.vector,jdk.incubator.foreign Java18.java
// Or
// % javac -Xlint:preview --enable-preview --source 18 --add-modules=jdk.incubator.vector,jdk.incubator.foreign Java18.java
// % java --enable-preview --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.vector,jdk.incubator.foreign Java18

// Java 17
// OpenJDK 17
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK17.0.2/jdk-17.0.2.jdk/Contents/Home/bin/":$PATH


/*
# 1 # 400: UTF-8 by Default
# 2 # 408: Simple Web Server
# 3 # 413: Code Snippets in Java API Documentation
# 4 # 416: Reimplement Core Reflection with Method Handles
# 5 # 417: Vector API (Third Incubator)
# 6 # 418: Internet-Address Resolution SPI
# 7 # 419: Foreign Function & Memory API (Second Incubator)
# 8 # 420: Pattern Matching for switch (Second Preview)
# 9 # 421: Deprecate Finalization for Removal
*/


// import static java.lang.System.out;
// import java.lang.System;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.SimpleFileServer;
import com.sun.net.httpserver.SimpleFileServer.OutputLevel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.ServiceLoader;
import java.util.stream.Stream;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.NativeSymbol;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.SegmentAllocator;
import jdk.incubator.foreign.ValueLayout;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

public class Java18 {
    public static void main(String[] args) throws Throwable { 
    // public static void main(String... args) throws Throwable { 
        var propertiesSystem = new SystemProperties();
        propertiesSystem.print();
       
	// # 1 # 400: UTF-8 by Default
        System.out.println("# 1 # 400: UTF-8 by Default");
        // # 1 # 400: UTF-8 by Default
	// java -Dfile.encoding=UTF-8 # Default
	// java -Dfile.encoding=COMPAT # Pre-Java 18
	// java -Dfile.encoding=ASCII Java18.java
        System.out.println("さようなら!"); // Sayonara!
        System.out.println();

	// # 2 # 408: Simple Web Server"
        System.out.println("# 2 # 408: Simple Web Server");
        System.out.println("% jwebserver");
        System.out.println("% jwebserver --port 8888");
        System.out.println("% jwebserver --port 8888 --directory /.");
        System.out.println("jwebserver is a wrapper that calls:");
        System.out.println("% java -m jdk.httpserver");
	// Paths // Or // FileSystems
	String absolutePath = Paths.get("").toAbsolutePath().toString();
	// String absolutePath = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
        // System.out.println(absolutePath);
        HttpServer server = SimpleFileServer.createFileServer(new InetSocketAddress(8000), Path.of(absolutePath), OutputLevel.INFO);
	System.out.println("server.start():");
	System.out.println("For 10 seconds");
	System.out.println("URL http://localhost:8000/");
	System.out.println("To stop the server, press Ctrl + C");
        server.start();
	System.out.println("server.stop(10)");
	server.stop(10);
        System.out.println();

	// # 3 # 413: Code Snippets in Java API Documentation
        var codeSnippetsInJavaAPIDocumentation = new Java18();
        codeSnippetsInJavaAPIDocumentation.codeSnippetInline("Reverse a string!", true);
        codeSnippetsInJavaAPIDocumentation.codeSnippetInlineHighlight("Reverse a string!", true);
        codeSnippetsInJavaAPIDocumentation.codeSnippetExternal("Reverse a string!", true);
	// Pre-Java 18
        codeSnippetsInJavaAPIDocumentation.code("Reverse a string!", true);
        System.out.println();

	// # 4 # 416: Reimplement Core Reflection with Method Handles
        System.out.println("# 4 # 416: Reimplement Core Reflection with Method Handles");
        System.out.println("java.lang.reflect.Method, Constructor, and Field reimplemented on top of java.lang.invoke method handles.");
        System.out.println("Making method handles the underlying mechanism for reflection will reduce the maintenance and development cost of both the java.lang.reflect and java.lang.invoke APIs.");
        System.out.println("No change to the java.lang.reflect API. This is solely an implementation change.");
        System.out.println("Enable the old implementation via -Djdk.reflect.useDirectMethodHandle=false");
        System.out.println();

	// # 5 # 417: Vector API (Third Incubator)
        var incubatorVectorAPIThird = new VectorAPIThirdIncubator();
	incubatorVectorAPIThird.print();

	// # 6 # 418: Internet-Address Resolution SPI
	var spiInternetAddressResolution = new InternetAddressResolutionSPI();
	spiInternetAddressResolution.print();

	// # 7 # 419: Foreign Function & Memory API (Second Incubator)
	var incubatorForeignFunctionMemoryAPISecond = new ForeignFunctionMemoryAPISecondIncubator();
        incubatorForeignFunctionMemoryAPISecond.print();

	// # 8 # 420: Pattern Matching for switch (Second Preview)
	// Java 18 Preview
        var previewPatternMatchingForSwitchSecond = new PatternMatchingForSwitchSecondPreview();
	previewPatternMatchingForSwitchSecond.print();

	// # 9 # 421: Deprecate Finalization for Removal
	var forRemovalDeprecateFinalization = new DeprecateFinalizationForRemoval();
	forRemovalDeprecateFinalization.print();
    }

    // # 3 # 413: Code Snippets in Java API Documentation // Inline
    // public class Java18. // public void codeSnippetInline
    // javadoc Java18.java 
    /**
    * Code snippet for {@code isTrue}:
    * {@snippet :
    * if (isTrue) {
    *     text = java.util.stream.Stream.of(text)
    *         .map(string -> new StringBuilder(string).reverse())
    *         .collect(java.util.stream.Collectors.joining());
    * }
    * }
    */
    public void codeSnippetInline(String text, boolean isTrue) {
        System.out.println("# 3 # 413: Code Snippets in Java API Documentation: Inline");
        if (isTrue) {
            text = java.util.stream.Stream.of(text)
                .map(string -> new StringBuilder(string).reverse())
    		.collect(java.util.stream.Collectors.joining());
        }
        System.out.println(text);
    }

    // # 3 # 413: Code Snippets in Java API Documentation // Inline // @highlight
    // public class Java18 // public void codeSnippetInlineHighlight
    // javadoc Java18.java 
    /**
    * Code snippet for {@code isTrue}:
    * {@snippet :
    * if (isTrue) {
    *     text = java.util.stream.Stream.of(text) // @highlight substring="Stream.of"
    *         .map(string -> new StringBuilder(string).reverse())
    *         .collect(java.util.stream.Collectors.joining());
    * }
    * }
    */
    public void codeSnippetInlineHighlight(String text, boolean isTrue) {
        System.out.println("# 3 # 413: Code Snippets in Java API Documentation: Inline: Highlight");
        if (isTrue) {
            text = java.util.stream.Stream.of(text)
                .map(string -> new StringBuilder(string).reverse())
    		.collect(java.util.stream.Collectors.joining());
        }
        System.out.println(text);
    }

    // # 3 # 413: Code Snippets in Java API Documentation // External
    // public class Java18 // public void codeSnippetExternal
    // NB
    // class External in External.java need not be public
    // javadoc Java18.java --snippet-path .
    // subdirectories
    // mkdir -p "output-files"
    // mkdir -p "snippet-files"
    // cp External.java snippet-files
    // javadoc Java18.java --source-path . --snippet-path "./snippet-files" -d "./output-files"
    // javadoc Java18.java --enable-preview --source 18 --add-modules=jdk.incubator.vector,jdk.incubator.foreign --source-path . --snippet-path "./snippet-files" -d "./output-files"
    /**
    * Code snippet for {@code isTrue}:
    * {@snippet file="External.java" region="sample"}
    */
    public void codeSnippetExternal(String text, boolean isTrue) {
	System.out.println("# 3 # 413: Code Snippets in Java API Documentation: External");
        if (isTrue) {
            text = java.util.stream.Stream.of(text)
                .map(string -> new StringBuilder(string).reverse())
    		.collect(java.util.stream.Collectors.joining());
        }
        System.out.println(text);
    }

    // Pre-Java 18
    // public class Java18 // public void code
    // javadoc Java18.java 
    /** 
    * <pre>{@code
    *     if (isTrue) {
    *         text = java.util.stream.Stream.of(text)
    *             .map(string -> new StringBuilder(string).reverse())
    *             .collect(java.util.stream.Collectors.joining());
    *     }
    * }</pre>
    */
    public void code(String text, boolean isTrue) {
        System.out.println("Pre-Java 18: @code");
        if (isTrue) {
            text = java.util.stream.Stream.of(text)
                .map(string -> new StringBuilder(string).reverse())
    		.collect(java.util.stream.Collectors.joining());
        }
        System.out.println(text);
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


// # 5 # 417: Vector API (Third Incubator)
class VectorAPIThirdIncubator {
    // Simple scalar computation over elements of arrays
    // Assume that the array arguments are of the same length
    private void scalarComputation(float[] a, float[] b, float[] c) {
        for (int i = 0; i < a.length; i++) {
            c[i] = (a[i] * a[i] + b[i] * b[i]) * -1.0f;
        }
        System.out.println(Arrays.toString(c));
    }
    
    // Equivalent vector computation, using the Vector API
    private static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

    private void vectorComputation(float[] a, float[] b, float[] c) {
        int i = 0;
        int upperBound = SPECIES.loopBound(a.length);
        for (; i < upperBound; i += SPECIES.length()) {
            // FloatVector va, vb, vc;
            var va = FloatVector.fromArray(SPECIES, a, i);
            var vb = FloatVector.fromArray(SPECIES, b, i);
            var vc = va.mul(va)
                .add(vb.mul(vb))
                .neg();
            vc.intoArray(c, i);
        }
        for (; i < a.length; i++) {
            c[i] = (a[i] * a[i] + b[i] * b[i]) * -1.0f;
        }
        System.out.println(Arrays.toString(c));
    }

    // On platforms supporting predicate registers, the above could be written more simply, without the scalar loop to process the tail elements, while still achieving optimal performance
    private void vectorComputationPredicateRegisters(float[] a, float[] b, float[] c) {
        for (int i = 0; i < a.length; i += SPECIES.length()) {
            // VectorMask<Float>  m;
            var m = SPECIES.indexInRange(i, a.length);
            // FloatVector va, vb, vc;
            var va = FloatVector.fromArray(SPECIES, a, i, m);
            var vb = FloatVector.fromArray(SPECIES, b, i, m);
            var vc = va.mul(va)
                .add(vb.mul(vb))
                .neg();
            vc.intoArray(c, i, m);
        }
        System.out.println(Arrays.toString(c));
    }

    public void print() {
        System.out.println("# 5 # 417: Vector API (Third Incubator)");
        
	float[] alpha = { 1.2f, 2.3f };
        float[] beta = { 3.4f, 5.6f };
        float[] gamma = { 7.8f, 9.1f };
        System.out.println("Support the ARM Scalar Vector Extension (SVE) platform.");

	System.out.println("Scalar Computation");
	scalarComputation(alpha, beta, gamma);

	System.out.println("Vector Computation");
        vectorComputation(alpha, beta, gamma);

        System.out.println("Support the ARM Scalar Vector Extension (SVE) platform.");
        System.out.println("Improve the performance of vector operations that accept masks on architectures that support masking in hardware.");
	vectorComputationPredicateRegisters(alpha, beta, gamma);
        System.out.println();
    }
}


// # 6 # 418: Internet-Address Resolution SPI
class InternetAddressResolutionSPI {
    public void print() throws UnknownHostException {
        System.out.println("# 6 # 418: Internet-Address Resolution SPI");
        System.out.println("A service-provider interface (SPI) for host name and address resolution, so that java.net.InetAddress can make use of resolvers other than the platform's built-in resolver.");
        System.out.println("The InetAddress API will use a service loader to locate a resolver provider. If no provider is found, the built-in implementation will be used as before.");
        // Not an alternative resolver to include in the JDK.
        // The JDK's built-in resolver will continue to be used by default.
	InetAddress[] addressInet = InetAddress.getAllByName("openjdk.java.net");
	System.out.println("Internet Addresses: " + Arrays.toString(addressInet));
	InetAddress address = InetAddress.getByName("openjdk.java.net");
	System.out.println("Internet Address: " + address);
        System.out.println();
    }
}


// # 7 # 419: Foreign Function & Memory API (Second Incubator)
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
	// Java 18 // static CLinker.systemCLinker() // Returns the C linker for the current platform.
	CLinker linker = CLinker.systemCLinker();
	MethodHandle strlenHandle = linker.downcallHandle(
    	    linker.lookup("strlen").get(),
    	    FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)
	);

        // Or // try(ResourceScope scopeResource = ResourceScope.newConfinedScope()) { // static ResourceScope.newConfinedScope() // Creates a new confined scope.
	    // Java 18 // static SegmentAllocator.nativeAllocator(ResourceScope scope) // Returns a native allocator, associated with the provided scope.
	    // Or // SegmentAllocator allocatorSegment = SegmentAllocator.nativeAllocator(scopeResource);
       
	    // Java 18 // default MemorySegment allocateUtf8String(String str) // Converts a Java string into a UTF-8 encoded, null-terminated C string, storing the result into a memory segment.
            // MemorySegment segmentMemory = allocatorSegment.allocateUtf8String(text);
            // Or //
	    // Java 18 // static SegmentAllocator.implicitAllocator() // Returns a native allocator which allocates segments in independent implicit scopes.
            MemorySegment segmentMemory = SegmentAllocator.implicitAllocator().allocateUtf8String(text);

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
            
	    // Java 18 // static SegmentAllocator.newNativeArena(ResourceScope scope) // Returns a native unbounded arena-based allocator, with predefined block size and maximum arena size, associated with the provided scope.
            SegmentAllocator allocatorSegment = SegmentAllocator.newNativeArena(scopeResource);

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
            // Java 18 // static SegmentAllocator.implicitAllocator() // Returns a native allocator which allocates segments in independent implicit scopes.
	    MemorySegment allocateArray = SegmentAllocator.implicitAllocator().allocateArray(ValueLayout.JAVA_INT, varargs);

            // Java 18
            // final Object invoke(Object... args) // Invokes the method handle, allowing any caller type descriptor, and optionally performing conversions on arguments and return values.
 	    // invoke // unreported exception Throwable; must be caught or declared to be thrown
	    qsortHandle.invoke(allocateArray, 10L, 4L, comparFunc);
	
            // Java 18
	    // https://openjdk.org/jeps/419 // Erratum // int[] sorted = array.toIntArray(); // [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
            // int[] toArray(ValueLayout.OfInt elementLayout) // Copy the contents of this memory segment into a fresh int array.
	    int[] sortedArray = allocateArray.toArray(ValueLayout.JAVA_INT);

            System.out.println("qsort output: " + Arrays.toString(sortedArray));
        }
    }

    public void print() throws Throwable {
        System.out.println("# 7 # 419: Foreign Function & Memory API (Second Incubator)");
	radixsort();
        strlen("Hola!");
        qsort(5, 7, 9, 3, 4, 6, 1, 8, 2, 0);
        System.out.println();
    }
}


// # 8 # 420: Pattern Matching for switch (Second Preview)
// Java 18 Preview
sealed interface Blueprint<T> permits Alpha, Beta {}

final class Alpha<T> implements Blueprint<String> {}

final class Beta<T> implements Blueprint<T> {}

class PatternMatchingForSwitchSecondPreview {
    public void print() {
	System.out.println("# 8 # 420: Pattern Matching for switch (Second Preview)");

	// Java 17 Preview // output: 7 
        // Java 18 Preview // error: case "Preview" label is dominated by a preceding case label 
        /*
	String text = "Preview";
	switch (text) {
  	    case String text && text.length() > 6 -> System.out.println(text.length());
  	    case "Preview" -> System.out.println("Java 17 Preview");
  	    default -> System.out.println("default");
	}
	*/

        // Java 17 Preview // error: the switch statement does not cover all possible input values
	// Java 18 Preview // output: Beta
        Blueprint<Integer> blueNumber = new Beta<Integer>();
        switch (blueNumber) {
            case Beta <Integer> b -> System.out.println("Beta");
        }

	// Java 17 Preview // error: the switch statement does not cover all possible input values
	// Java 18 Preview // output: Beta
        Blueprint<Object> blueObject = new Beta<Object>();
        switch (blueObject) {
            case Beta <Object> b -> System.out.println("Beta");
        }

	// Java 17 Preview // output: Not Beta
	// Java 18 Preview // output: Not Beta
        Blueprint<String> blueAlpha = new Alpha<String>();
        switch (blueAlpha) {
            case Beta<String> b -> System.out.println("Beta");
            default -> System.out.println("Not Beta");
        }
	System.out.println();
    }
}


// # 9 # 421: Deprecate Finalization for Removal
class DeprecateFinalizationForRemoval {
    public void print() throws IOException {
	System.out.println("# 9 # 421: Deprecate Finalization for Removal");
	System.out.println("""
	    Finalization remains enabled by default for now, but can be disabled to facilitate early testing. 
	    In a future release it will be disabled by default, and in a later release it will be removed. 
	    Maintainers of libraries and applications that rely upon finalization should consider migrating to other resource management techniques such as the try-with-resources statement and cleaners.""");

	// Paths // Or // FileSystems
	// String absolutePath = Paths.get("").toAbsolutePath().toString();
	String absolutePath = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
        // System.out.println(absolutePath);
	String inputFile = absolutePath + "/" + "External.java";
        String outputFile = absolutePath + "/" + "OutputExternal.java";

	FileInputStream  input  = null;
	FileOutputStream output = null;
	try {
	    // FileInputStream // unreported exception FileNotFoundException; must be caught or declared to be thrown
    	    input  = new FileInputStream(inputFile);

	    // new FileOutputStream // unreported exception FileNotFoundException; must be caught or declared to be thrown
    	    output = new FileOutputStream(outputFile);

    	    byte[] bytes = new byte[1024];
	    int length = 0;
	    while((length = input.read(bytes, 0, 1024)) > 0) {
    		output.write(bytes, 0, length);
	    }

	    // close // unreported exception IOException; must be caught or declared to be thrown
    	    output.close();  
	    output = null;

	    // close // unreported exception IOException; must be caught or declared to be thrown
    	    input.close();
	    input  = null;
	} finally { // Finalization remains enabled by default for now
    	    if (output != null) {
		// close // unreported exception IOException; must be caught or declared to be thrown
	        output.close();
	    }
	    if (input  != null) {
		// close // unreported exception IOException; must be caught or declared to be thrown
	        input.close();
	    }
	}
	
	System.out.println("""
	    Alternative techniques:
	    Java 7 introduced the try-with-resources statement
	    Java 9 introduced the cleaner API""");
    }
}


// External.java 
/*
// class need not be public
class External {
    public void method(String text, boolean isTrue) {
        // @start region="sample"
        if (isTrue) {
            text = java.util.stream.Stream.of(text)
                .map(string -> new StringBuilder(string).reverse())
    		.collect(java.util.stream.Collectors.joining());
        }
	// @end
        System.out.println(text);
    }
}
*/


// Output
/*
WARNING: Using incubator modules: jdk.incubator.vector, jdk.incubator.foreign
warning: using incubating module(s): jdk.incubator.foreign,jdk.incubator.vector
Note: Java18.java uses preview features of Java SE 18.
Note: Recompile with -Xlint:preview for details.
1 warning
OS Name: Mac OS X
OS Version: 14.0
OS Architecture: aarch64
Java Version: 18.0.2

# 1 # 400: UTF-8 by Default
さようなら!

# 2 # 408: Simple Web Server
% jwebserver
% jwebserver --port 8888
% jwebserver --port 8888 --directory /.
jwebserver is a wrapper that calls:
% java -m jdk.httpserver
server.start():
For 10 seconds
URL http://localhost:8000/
To stop the server, press Ctrl + C
server.stop(10)

# 3 # 413: Code Snippets in Java API Documentation: Inline
!gnirts a esreveR
# 3 # 413: Code Snippets in Java API Documentation: Inline: Highlight
!gnirts a esreveR
# 3 # 413: Code Snippets in Java API Documentation: External
!gnirts a esreveR
Pre-Java 18: @code
!gnirts a esreveR

# 4 # 416: Reimplement Core Reflection with Method Handles
java.lang.reflect.Method, Constructor, and Field reimplemented on top of java.lang.invoke method handles.
Making method handles the underlying mechanism for reflection will reduce the maintenance and development cost of both the java.lang.reflect and java.lang.invoke APIs.
No change to the java.lang.reflect API. This is solely an implementation change.
Enable the old implementation via -Djdk.reflect.useDirectMethodHandle=false

# 5 # 417: Vector API (Third Incubator)
Support the ARM Scalar Vector Extension (SVE) platform.
Scalar Computation
[-13.0, -36.649998]
Vector Computation
[-13.0, -36.649998]
Support the ARM Scalar Vector Extension (SVE) platform.
Improve the performance of vector operations that accept masks on architectures that support masking in hardware.
[-13.0, -36.649998]

# 6 # 418: Internet-Address Resolution SPI
A service-provider interface (SPI) for host name and address resolution, so that java.net.InetAddress can make use of resolvers other than the platform's built-in resolver.
The InetAddress API will use a service loader to locate a resolver provider. If no provider is found, the built-in implementation will be used as before.
Internet Addresses: [openjdk.java.net/23.58.30.141, openjdk.java.net/2600:140f:3:1187:0:0:0:2c21, openjdk.java.net/2600:140f:3:11ab:0:0:0:2c21]
Internet Address: openjdk.java.net/23.58.30.141

# 7 # 419: Foreign Function & Memory API (Second Incubator)
radixsort input: [Delta, Gamma, Alpha, Beta]
radixsort output: [Alpha, Beta, Delta, Gamma]
strlen input: Hola!
strlen output: 5
qsort input: [5, 7, 9, 3, 4, 6, 1, 8, 2, 0]
qsort output: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

# 8 # 420: Pattern Matching for switch (Second Preview)
Beta
Beta
Not Beta

# 9 # 421: Deprecate Finalization for Removal
Finalization remains enabled by default for now, but can be disabled to facilitate early testing.
In a future release it will be disabled by default, and in a later release it will be removed.
Maintainers of libraries and applications that rely upon finalization should consider migrating to other resource management techniques such as the try-with-resources statement and cleaners.
Alternative techniques:
Java 7 introduced the try-with-resources statement
Java 9 introduced the cleaner API
*/


/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
*/