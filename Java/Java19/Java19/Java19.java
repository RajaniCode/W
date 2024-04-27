// Java 19
// OpenJDK 19
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK19.0.2/jdk-19.0.2.jdk/Contents/Home/bin/":$PATH

// % java --enable-preview --source 19 --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.concurrent,jdk.incubator.vector Java19.java
// Or
// % javac -Xlint:preview --enable-preview --source 19 --add-modules=jdk.incubator.concurrent,jdk.incubator.vector Java19.java
// % java --enable-preview --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.concurrent,jdk.incubator.vector Java19

// Java 18
// OpenJDK 18
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK18.0.2/jdk-18.0.2.jdk/Contents/Home/bin/":$PATH


/*
# 1 # 405: Record Patterns (Preview)
# 2 # 422: Linux/RISC-V Port
# 3 # 424: Foreign Function & Memory API (Preview)
# 4 # 425: Virtual Threads (Preview)
# 5 # 426: Vector API (Fourth Incubator)
# 6 # 427: Pattern Matching for switch (Third Preview)
# 7 # 428: Structured Concurrency (Incubator)
*/


// import static java.lang.System.out;
// import java.lang.System;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;

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

import java.util.ArrayList;
import java.util.Arrays;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import java.util.List;
import java.util.stream.IntStream;

import jdk.incubator.concurrent.StructuredTaskScope;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

class Java19 {
    public static void main(String[] args) throws Throwable { 
    // public static void main(String... args) throws Throwable { 
        var propertiesSystem = new SystemProperties();
        propertiesSystem.print();
	
	// # 1 # 405: Record Patterns (Preview)
	var previewRecordPatterns = new RecordPatternsPreview();
	previewRecordPatterns.print();

	// # 2 # 422: Linux/RISC-V Port
        System.out.println("# 2 # 422: Linux/RISC-V Port");
	System.out.println("Port the JDK to Linux/RISC-V.");
	System.out.println("RISC-V is a free and open-source RISC instruction set architecture (ISA) designed originally at the University of California, Berkeley, and now developed collaboratively under the sponsorship of RISC-V International.");
	System.out.println();

	// # 3 # 424: Foreign Function & Memory API (Preview)
	var previewForeignFunctionMemoryAPI = new ForeignFunctionMemoryAPIPreview();
        previewForeignFunctionMemoryAPI.print();

	// # 4 # 425: Virtual Threads (Preview)
	System.out.println("# 4 # 425: Virtual Threads (Preview)");
	System.out.println("Virtual threads are lightweight threads that dramatically reduce the effort of writing, maintaining, and observing high-throughput concurrent applications.");
    	String dateTimePattern = "EEEE, dd MMMM yyyy, VV, z, O, h:mm:ss a";
	DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern(dateTimePattern);
 	// 1 Second
  	try (ExecutorService executorsVirtualThread = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10_000).forEach(i -> {
          	executorsVirtualThread.submit(() -> {
                    // Java 19
              	    Thread.sleep(Duration.ofSeconds(1));
 	      	    String ticks = formatterDateTime.format(ZonedDateTime.now());  
              	    System.out.print(ticks + "\r");
              	    return i;
          	});
      	    });
  	}
	// 10_000 / 20 = 500 Seconds = 8 Minutes and 33 Seconds
	// 200 / 20 = 10 Seconds
 	try (ExecutorService executorsFixedThread = Executors.newFixedThreadPool(20)) {
      	    // IntStream.range(0, 10_000).forEach(i -> {
      	    IntStream.range(0, 200).forEach(i -> {
          	executorsFixedThread.submit(() -> {
                    // Java 19
              	    Thread.sleep(Duration.ofSeconds(1));
 	      	    String ticks = formatterDateTime.format(ZonedDateTime.now());  
              	    System.out.print(ticks + "\r");
              	    return i;
          	});
      	    });
        }
        System.out.println("\n");

	// # 5 # 426: Vector API (Fourth Incubator)
        var incubatorVectorAPIFourth = new VectorAPIFourthIncubator();
	incubatorVectorAPIFourth.print();

	// # 6 # 427: Pattern Matching for switch (Third Preview)
	var previewPatternMatchingForSwitchThird = new PatternMatchingForSwitchThirdPreview();
	previewPatternMatchingForSwitchThird.print();

	// # 7 # 428: Structured Concurrency (Incubator)
	var incubatorStructuredConcurrency = new StructuredConcurrencyIncubator();
	incubatorStructuredConcurrency.print();
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


// # 1 # 405: Record Patterns (Preview)
record Point(int x, int y) {}

enum Color { RED, GREEN, BLUE }

record ColoredPoint(Point p, Color c) {}

record Rectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) {}

record Box<T>(T t) {}

class A {}

class B extends A {}

sealed interface I permits C, D {}

final class C implements I {}

final class D implements I {}

record Pair<T>(T x, T y) {}

record RecordPatternsPreview() {
    public void print() {
        System.out.println("# 1 # 405: Record Patterns (Preview)");
	System.out.println("""
            Record patterns to deconstruct record values. 
            Record patterns and type patterns can be nested to enable a powerful, declarative, and composable form of data navigation and processing.""");

	Object o = new Point(1, 2);

        // Pre-Java 19
        if (o instanceof Point) {
	    System.out.println(String.format("%s instanceof Point %b", o, (o instanceof Point)));
    	}

    	if (o instanceof Point(int x, int y)) {
	    System.out.println(String.format("%s instanceof Point(int x, int y) %b", o, true));
	}
	
	Rectangle r = new Rectangle(new ColoredPoint(new Point(0, 128), Color.GREEN), new ColoredPoint(new Point(0, 255), Color.BLUE));

	if (r instanceof Rectangle(ColoredPoint ul, ColoredPoint lr)) {
            System.out.println(ul.c());
            System.out.println(lr.c());
    	}

	if (r instanceof Rectangle(ColoredPoint(Point p, Color c), ColoredPoint lr)) {
	    System.out.println(p);
	    System.out.println(c);
	    System.out.println(lr);
	    System.out.println(lr.c());
    	}

	if (r instanceof Rectangle(ColoredPoint(Point(var x, var y), var c), var lr)) {
            System.out.println("Upper-left corner: " + x);
    	}

	@SuppressWarnings("unchecked")
	Box<Object> bo = new <String>Box("Box Object");
	if (bo instanceof Box<Object>(String s)) {
            System.out.println("String " + s);
    	}
	
	// https://openjdk.org/jeps/405 // Erratum
	// error: incompatible types: Box<Object> cannot be converted to Box<String>
	/*
	if (bo instanceof Box<String>(var s)) {
            System.out.println("String " + s);
    	}
	*/

	/*
	// error: raw deconstruction patterns are not allowed
	if (bo instanceof Box(var s)) {
            System.out.println("I'm a box");
    	}
        */

	@SuppressWarnings("unchecked")
	Box b = new Box("Box Object");
 
       	// error: raw deconstruction patterns are not allowed
	/*
	if (b instanceof Box(var t)) {
            System.out.println("I'm a box");
    	}
	*/

	@SuppressWarnings("unchecked")
	Pair<A> p1 = new <A>Pair(new A(), new A());
	@SuppressWarnings("unchecked")
	Pair<I> p2 = new <I>Pair(new C(), new C());

	
	// error: the switch statement does not cover all possible input values
	/*
	switch (p1) {
      	    case Pair<A>(A a1, B b1) -> System.out.println("I'm a Pair<A> of A and B");
            case Pair<A>(B b2, A a2) -> System.out.println("I'm a Pair<A> of B and A");
	}
    	*/

	switch (p2) {
    	    case Pair<I>(I i, C c) -> System.out.println("I'm a Pair<I> of I and C");
    	    case Pair<I>(I i, D d) -> System.out.println("I'm a Pair<I> of I and D");
	}

	switch (p2) {
    	    case Pair<I>(C c, I i) -> System.out.println("I'm a Pair<I> of C and I");
    	    case Pair<I>(D d, C c) -> System.out.println("I'm a Pair<I> of D and C");
    	    case Pair<I>(D d1, D d2) -> System.out.println("I'm a Pair<I> of D and D");
	}
	
	// error: the switch statement does not cover all possible input values
	/*
	switch (p2) {
    	    case Pair<I>(C fst, D snd) -> System.out.println("I'm a Pair<I> of C and D");
    	    case Pair<I>(D fst, C snd) -> System.out.println("I'm a Pair<I> of D and C");
    	    case Pair<I>(I fst, C snd) -> System.out.println("I'm a Pair<I> of I and C");
	}
	*/
        System.out.println();
    }
}


// # 3 # 424: Foreign Function & Memory API (Preview)
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
	// Java 19 Preview
	Linker linker = Linker.nativeLinker();
	MethodHandle strlenHandle = linker.downcallHandle(
    	    linker.defaultLookup().lookup("strlen").get(),
    	    FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)
	);

        // Or // Java 19 Preview 
	// try (MemorySession sessionMemory = MemorySession.openConfined()) { // static MemorySession.openConfined() // Creates a closeable confined memory session.
    	    // Or // Java 19 Preview  // static SegmentAllocator.newNativeArena(MemorySession session) // Creates an unbounded arena-based allocator used to allocate native memory segments.
	    // SegmentAllocator allocatorSegment = SegmentAllocator.newNativeArena(sessionMemory);
      
   	    // Java 19 Preview // default MemorySegment allocateUtf8String(String str) // Converts a Java string into a UTF-8 encoded, null-terminated C string, storing the result into a memory segmen
	    // MemorySegment segmentMemory = allocatorSegment.allocateUtf8String(text);
	    // Or //
	    // Java 19 Preview // static SegmentAllocator.implicitAllocator() // Returns a native allocator which allocates segments in independent implicit scopes.
	    MemorySegment segmentMemory = SegmentAllocator.implicitAllocator().allocateUtf8String(text);

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

	// Java 19 Preview 
	try (MemorySession sessionMemory = MemorySession.openConfined()) { // static MemorySession.openConfined() // Creates a closeable confined memory session.

	    // Java 19 Preview // static SegmentAllocator.newNativeArena(MemorySession session) // Creates an unbounded arena-based allocator used to allocate native memory segments.
    	    SegmentAllocator allocatorSegment = SegmentAllocator.newNativeArena(sessionMemory);

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
	    // Java 19 Preview // default MemorySegment allocateArray(ValueLayout.OfIntPREVIEW elementLayout, int... elements) Allocates a memory segment with the given layout and initializes it with the given int elements.
	    MemorySegment allocateArray = allocatorSegment.allocateArray(ValueLayout.JAVA_INT, varargs);

	    // Java 19 Preview 
            // final Object invoke(Object... args) Invokes the method handle, allowing any caller type descriptor, and optionally performing conversions on arguments and return values.
            // invoke // error: unreported exception Throwable; must be caught or declared to be thrown
	    qsortHandle.invoke(allocateArray, 10L, ValueLayout.JAVA_INT.byteSize(), comparFunc);

	    // Java 19 Preview
	    // https://openjdk.org/jeps/424// Erratum // int[] sorted = array.toIntArray(); // [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
	    // int[] toArray(ValueLayout.OfInt elementLayout) // Copy the contents of this memory segment into a new int array.
	    int[] sortedArray = allocateArray.toArray(ValueLayout.JAVA_INT); 

            System.out.println("qsort output: " + Arrays.toString(sortedArray));
        }
    }

    public void print() throws Throwable {
        System.out.println("# 3 # 424: Foreign Function & Memory API (Preview)");
	radixsort();
        strlen("Hola!");
        qsort(5, 7, 9, 3, 4, 6, 1, 8, 2, 0);
	System.out.println();
    }
}


// # 5 # 426: Vector API (Fourth Incubator)
class VectorAPIFourthIncubator {
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
        System.out.println("# 5 # 426: Vector API (Fourth Incubator)");
        
	float[] alpha = { 1.2f, 2.3f };
        float[] beta = { 3.4f, 5.6f };
        float[] gamma = { 7.8f, 9.1f };
        System.out.println("Support the ARM Scalar Vector Extension (SVE) platform.");

	System.out.println("Scalar Computation");
	scalarComputation(alpha, beta, gamma);

	System.out.println("Vector Computation");
        vectorComputation(alpha, beta, gamma);

        System.out.println("An API to express vector computations that reliably compile at runtime to optimal vector instructions on supported CPU architectures, thus achieving performance superior to equivalent scalar computations.");
	vectorComputationPredicateRegisters(alpha, beta, gamma);

	System.out.println();
    }
}


// # 6 # 427: Pattern Matching for switch (Third Preview)
class Shapes {}

class Rectangles extends Shapes {}

class Triangles extends Shapes { int calculateArea() { return 1000; } }

// # 6 # 427: Pattern Matching for switch (Third Preview)
class PatternMatchingForSwitchThirdPreview {
    public void print() {
        System.out.println("# 6 # 427: Pattern Matching for switch (Third Preview)");

	Object obj = "1234567890";
        // Pre-Java 19 Preview Only
	/*
	switch (obj) {
            // Java 19 Preview // error: illegal start of expression // &&
            // Java 18 Preview // length > 5
  	    case String s && s.length() > 5 -> System.out.println("length > 5");
  	    case String s -> System.out.println("length <= 5");
  	    case Integer i -> System.out.println(i);
  	    default -> {}
	}
        */

	// Java 19 Preview
	// when
	switch (obj) {
  	    case String s when s.length() > 5 -> System.out.println("length > 5");
  	    case String s -> System.out.println("length <= 5");
  	    case Integer i -> System.out.println(i);
  	    default -> {}
	}

	Shapes s = new Shapes();
        switch (s) {
            case null:
                break;
            case Triangles t:
                if (t.calculateArea() > 100) {
                    System.out.println("Large triangle");
                    break;
                }
            default:
                System.out.println("A shape, possibly a small triangle.");
        }

	// Java 19 Preview
	// when
	switch (s) {
            case null -> 
                { break; }
            case Triangles t
                when t.calculateArea() > 100 -> System.out.println("Large triangle");
            default -> System.out.println("A shape, possibly a small triangle");
        }

	s = new Triangles();
	// when
	switch (s) {
            case null -> 
                { break; }
            case Triangles t
                when t.calculateArea() > 100 -> System.out.println("Large triangle");
            default -> System.out.println("A shape, possibly a small triangle");
        }

	s = null;
    	switch (s) {
            case null -> 
            { break; }
            case Triangles t
        	when t.calculateArea() > 100 -> System.out.println("Large triangle");
            case Triangles t -> System.out.println("Small triangle");
            default -> System.out.println("Non-triangle");
        }

	System.out.println();
    }
}


// # 7 # 428: Structured Concurrency (Incubator)
record Phone(String sms, int callerID) {}

// # 7 # 428: Structured Concurrency (Incubator)
class StructuredConcurrencyIncubator {

    private String texter() throws InterruptedException {
	// Java 19
        Thread.sleep(Duration.ofSeconds(1));
        return "JEP";
    }

    private Integer ringer() throws InterruptedException {
	// Java 19
        Thread.sleep(Duration.ofSeconds(1));
        return 428;
    }

    private Phone handleUnStructureAPI() throws ExecutionException, InterruptedException {
	// Pre-Java 19 // error: incompatible types: try-with-resources not applicable to variable type
        try (ExecutorService executorsFixedThread = Executors.newFixedThreadPool(10)) {
            Future<String> text = executorsFixedThread.submit(this::texter);
            Future<Integer> number = executorsFixedThread.submit(this::ringer);
            String message = text.get(); // Join message
            int id = number.get(); // Join id
            return new Phone(message, id);
        }
    }

    private Phone handleStructureAPI() throws ExecutionException, InterruptedException {
        try (StructuredTaskScope.ShutdownOnFailure taskScope = new StructuredTaskScope.ShutdownOnFailure()) {
	   
            Future<String> text = taskScope.fork(this::texter);
            Future<Integer> number = taskScope.fork(this::ringer);

            taskScope.join(); // Join both forks
            taskScope.throwIfFailed(); // And propagate errors

            return new Phone(text.resultNow(), number.resultNow());
        }
    }

    public void print() throws Throwable {
        System.out.println("# 7 # 428: Structured Concurrency (Incubator)");
	System.out.println("UnStructured Concurrency API: " + handleUnStructureAPI());
	System.out.println("Structured Concurrency API: " + handleStructureAPI());
    }
}


// Output
/*
WARNING: Using incubator modules: jdk.incubator.vector, jdk.incubator.concurrent
warning: using incubating module(s): jdk.incubator.concurrent,jdk.incubator.vector
Note: Java19.java uses preview features of Java SE 19.
Note: Recompile with -Xlint:preview for details.
1 warning
OS Name: Mac OS X
OS Version: 14.0
OS Architecture: aarch64
Java Version: 19.0.2

# 1 # 405: Record Patterns (Preview)
Record patterns to deconstruct record values.
Record patterns and type patterns can be nested to enable a powerful, declarative, and composable form of data navigation and processing.
Point[x=1, y=2] instanceof Point true
Point[x=1, y=2] instanceof Point(int x, int y) true
GREEN
BLUE
Point[x=0, y=128]
GREEN
ColoredPoint[p=Point[x=0, y=255], c=BLUE]
BLUE
Upper-left corner: 0
String Box Object
I'm a Pair<I> of I and C
I'm a Pair<I> of C and I

# 2 # 422: Linux/RISC-V Port
Port the JDK to Linux/RISC-V.
RISC-V is a free and open-source RISC instruction set architecture (ISA) designed originally at the University of California, Berkeley, and now developed collaboratively under the sponsorship of RISC-V International.

# 3 # 424: Foreign Function & Memory API (Preview)
radixsort input: [Delta, Gamma, Alpha, Beta]
radixsort output: [Alpha, Beta, Delta, Gamma]
strlen input: Hola!
strlen output: 5
qsort input: [5, 7, 9, 3, 4, 6, 1, 8, 2, 0]
qsort output: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

# 4 # 425: Virtual Threads (Preview)
Virtual threads are lightweight threads that dramatically reduce the effort of writing, maintaining, and observing high-throughput concurrent applications.
Wednesday, 04 October 2023, Asia/Kolkata, IST, GMT+5:30, 3:16:37 PM

# 5 # 426: Vector API (Fourth Incubator)
Support the ARM Scalar Vector Extension (SVE) platform.
Scalar Computation
[-13.0, -36.649998]
Vector Computation
[-13.0, -36.649998]
An API to express vector computations that reliably compile at runtime to optimal vector instructions on supported CPU architectures, thus achieving performance superior to equivalent scalar computations.
[-13.0, -36.649998]

# 6 # 427: Pattern Matching for switch (Third Preview)
length > 5
A shape, possibly a small triangle.
A shape, possibly a small triangle
Large triangle

# 7 # 428: Structured Concurrency (Incubator)
UnStructured Concurrency API: Phone[sms=JEP, callerID=428]
Structured Concurrency API: Phone[sms=JEP, callerID=428]
*/


/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
*/