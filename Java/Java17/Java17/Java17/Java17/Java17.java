// Java 17
// OpenJDK 17
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK17.0.2/jdk-17.0.2.jdk/Contents/Home/bin/":$PATH

// Java 17
// Microsoft Build of OpenJDK
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/MicrosoftBuildofOpenJDK/jdk-17.0.8.1+1/Contents/Home/bin/":$PATH

// % java --enable-preview --source 17 --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.foreign,jdk.incubator.vector Java17.java
// Or
// % javac -Xlint:preview --enable-preview --source 17 --add-modules=jdk.incubator.foreign,jdk.incubator.vector Java17.java
// % java --enable-preview --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.foreign,jdk.incubator.vector Java17

// Java 16
// OpenJDK 16
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK16.0.2/jdk-16.0.2.jdk/Contents/Home/bin/":$PATH


/*
# 1 # 306: Restore Always-Strict Floating-Point Semantics
# 2 # 356: Enhanced Pseudo-Random Number Generators
# 3 # 382: New macOS Rendering Pipeline
# 4 # 391: macOS/AArch64 Port
# 5 # 398: Deprecate the Applet API for Removal
# 6 # 403: Strongly Encapsulate JDK Internals
# 7 # 406: Pattern Matching for switch (Preview)
# 8 # 407: Remove RMI Activation
# 9 # 409: Sealed Classes
# 10 # 410: Remove the Experimental AOT and JIT Compiler
# 11 # 411: Deprecate the Security Manager for Removal
# 12 # 412: Foreign Function & Memory API (Incubator)
# 13 # 414: Vector API (Second Incubator)
# 14 # 415: Context-Specific Deserialization Filters
*/


// import static java.lang.System.out;
// import java.lang.System;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import java.util.Arrays;
import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.stream.IntStream;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.MemoryAccess;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.SegmentAllocator;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

class Java17 {
    public static void main(String[] args) throws Throwable { 
    // public static void main(String... args) throws Throwable { 
        var propertiesSystem = new SystemProperties();
        propertiesSystem.print();

	// # 1 # 306: Restore Always-Strict Floating-Point Semantics
        var floatingPointStrict = new StrictFloatingPoint();
	floatingPointStrict.print();

	// # 2 # 356: Enhanced Pseudo-Random Number Generators
	var pseudoRandomNumberGeneratorsEnhanced = new EnhancedPseudoRandomNumberGenerators();
	pseudoRandomNumberGeneratorsEnhanced.print();
	
	// # 3 # 382: New macOS Rendering Pipeline
        System.out.println("# 3 # 382: New macOS Rendering Pipeline");
        System.out.println("Java 2D internal rendering pipeline for macOS using the Apple Metal API as alternative to the existing pipeline, which uses the deprecated Apple OpenGL API.");
        System.out.println();

	// # 4 # 391: macOS/AArch64 Port
        System.out.println("# 4 # 391: macOS/AArch64 Port");
        System.out.println("JDK 17 ports to macOS/AArch64.");
        System.out.println();

	// # 5 # 398: Deprecate the Applet API for Removal
        System.out.println("# 5 # 398: Deprecate the Applet API for Removal");
        System.out.println("The Applet API was previously deprecated, though not for removal, by JEP 289 in Java 9.");
        System.out.println();

	// # 6 # 403: Strongly Encapsulate JDK Internals
        System.out.println("# 6 # 403: Strongly Encapsulate JDK Internals");
        System.out.println("% java --illegal-access=warn");
        System.out.println("OpenJDK 64-Bit Server VM warning: Ignoring option --illegal-access=warn; support was removed in 17.0");
        System.out.println("Except for critical internal APIs such as sun.misc.Unsafe, it will no longer be possible to relax the strong encapsulation of internal elements via a single command-line option, as was possible in JDK 9 through JDK 16.");
        System.out.println();

	// # 7 # 406: Pattern Matching for switch (Preview)
	// Java 17 Preview
        var switchPatternMatching = new PatternMatchingSwitch();
        switchPatternMatching.print();
	
	// # 8 # 407: Remove RMI Activation
        System.out.println("# 8 # 407: Remove RMI Activation");
        System.out.println("package java.rmi.activation removed, rest of package java.rmi.* preserved");
        System.out.println();

	// # 9 # 409: Sealed Classes
        var clientSealed = new SealedClient();
	clientSealed.print();
        
	// # 10 # 410: Remove the Experimental AOT and JIT Compiler
        System.out.println("# 10 # 410: Remove the Experimental AOT and JIT Compiler");
        System.out.println("Experimental java-based ahead-of-time (AOT) and just-in-time (JIT) compiler removed");
        System.out.println("Experimental java-level JVM compiler interface (JVMCI) retained to use externally-built versions of the compiler for JIT compilation");
        System.out.println();

	// # 11 # 411: Deprecate the Security Manager for Removal
        System.out.println("# 11 # 411: Deprecate the Security Manager for Removal");
        System.out.println("Security Manager dates from Java 1.0., deprecated for removal in concert with the legacy Applet API (JEP 398).");
        System.out.println();

	// # 12 # 412: Foreign Function & Memory API (Incubator)
        var functionMemoryAPIIncubatorForeign = new ForeignFunctionMemoryAPIIncubator();
        functionMemoryAPIIncubatorForeign.print();

	// # 13 # 414: Vector API (Second Incubator)
        var incubatorVectorAPISecond = new VectorAPISecondIncubator();
	incubatorVectorAPISecond.print();

	// # 14 # 415: Context-Specific Deserialization Filters
        var filtersContextSpecificDeserialization = new ContextSpecificDeserializationFilters();
	filtersContextSpecificDeserialization.print();
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


// # 1 # 306: Restore Always-Strict Floating-Point Semantics
// strictfp keyword as a non-access modifier for classes, non-abstract methods or interfaces
// warning: [strictfp] as of release 17, all floating-point expressions are evaluated strictly and 'strictfp' is not required
// strictfp interface StrictFloatingPointer {}
interface StrictFloatingPointer {}
// strictfp class StrictFloatingPoint implements StrictFloatingPointer {
class StrictFloatingPoint implements StrictFloatingPointer {
    // public strictfp void print() { 
    public void print() { 
        System.out.println("# 1 # 306: Restore Always-Strict Floating-Point Semantics");
        System.out.println("Java 17, all floating-point expressions are evaluated strictly and 'strictfp' is not required");
        System.out.println("This will restore the original floating-point semantics to the language and VM, matching the semantics before the introduction of strict and default floating-point modes in Java SE 1.2.");
        System.out.println();
    }
}


// # 2 # 356: Enhanced Pseudo-Random Number Generators
class EnhancedPseudoRandomNumberGenerators {
    public void print() { 
        // Java 17
 	RandomGeneratorFactory<RandomGenerator> factory = RandomGeneratorFactory.of("Random");
        
        System.out.println("# 2 # 356: Enhanced Pseudo-Random Number Generators");
	RandomGenerator generatorRandom = factory.create();
        // nextInt() returns a pseudorandomly chosen int value
	System.out.println(generatorRandom.nextInt());

        int size = 5;
        int lowerBoundInclusive = 1;
        int upperBoundExclusive = 10;

        System.out.println("# 2 # 356: Enhanced Pseudo-Random Number Generators: RandomGeneratorFactory");
        // ints() returns an effectively unlimited stream of pseudorandomly chosen int values
        IntStream streamInt = factory.create().ints(size, lowerBoundInclusive , upperBoundExclusive).sorted();
	streamInt.forEach(x -> System.out.printf("%d ", x));
        System.out.println("\n");
    }
}


// # 7 # 406: Pattern Matching for switch (Preview)
// Java 17 Preview
// cannot inherit from record
record TypeRecord() {} 

// record can be final // cannot be sealed or non-sealed
final record FinalRecord() {}

interface Build {}

// record can be final // cannot be sealed or non-sealed // can implement interface
final record FinalType() implements Build {}

record Vehicle(String realName, String nickname) {}

class PatternMatchingSwitch { 
    // record can be static member // only when nested in any type
    private static record StaticRecord(int number) {}   

    // record can implement interface // cannot extent record or class
    private record Aircraft(String realName, String nickname, int aircraftClassificationNumber) {}

    private void switchExpression(String greek) {	
	int number = switch (greek.toLowerCase()) {
   	    case "epsilon", "omicron", "upsilon" -> 7777;
	    case "lambda" -> 6666;		
	    case "alpha", "gamma", "delta", "theta", "kappa", "sigma", "omega" -> 5555;
	    case "beta", "zeta", "iota" -> 44444;
 	    case "eta", "rho", "tau", "phi", "chi", "psi" -> 3333;
	    case "mu", "nu", "xi", "pi" -> 2222;
	    default -> throw new IllegalArgumentException("Invalid Greek alphabet: " + greek);
        };
        System.out.println(number);
    }

    private String findRecord(Object instance) {	
	return switch (instance) {
   	    case TypeRecord recordType -> "Ït is a Record";
   	    case FinalRecord recordFinal -> "Ït is a Final Record";
	    case (StaticRecord recordStatic && recordStatic.number %2 == 0)-> "Ït is a Static Record Even Integer: %d".formatted(recordStatic.number);
	    case StaticRecord recordStatic  -> "Ït is a Static Record Odd Integer: %d".formatted(recordStatic.number);
   	    case Vehicle vhcl -> "Ït is a Vehicle";
   	    case Aircraft air -> "Ït is an Aircraft - Name: %s, Nickname: %s, Aircraft Classification Number: %d".formatted(air.realName, air.nickname, air.aircraftClassificationNumber);
	    case null -> "Ït is null"; // null in switch
	    default -> "Ït is an Object";
        };
    }

    public void print() {
	System.out.println("# 7 # 406: Pattern Matching for switch (Preview)");
	switchExpression("Alpha");

        var recordType = new TypeRecord();
        System.out.println(findRecord(recordType));
        
        var recordFinal = new FinalRecord();
        System.out.println(findRecord(recordFinal));
	
        System.out.println(StaticRecord.class);
        var recordStatic = new StaticRecord(1);
        System.out.println(findRecord(recordStatic));
	recordStatic = new StaticRecord(2);
        System.out.println(findRecord(recordStatic));
        var ran = new Random();
        recordStatic = new StaticRecord(ran.nextInt(100));
        System.out.println(findRecord(recordStatic));

        var vhcl = new Vehicle("Boeing", "Dreamliner");
        System.out.println(findRecord(vhcl));

        var air = new Aircraft("Boeing", "Dreamliner", 787);
        System.out.println(findRecord(air));
        System.out.println();
    }
}


// # 9 # 409: Sealed Classes: Classes can be sealed, non-sealed or final
/*
// only sealed class can permit class and interface(s) unlike non'-sealed or final class 

// sealed class must be extereded by sealed, non-sealed, or final class 

// non-sealed class must extend sealed class

// non-sealed class can be extended by non-sealed, final or class without modifier 

// final class cannot be extended and need not extend
*/
// sealed class must have subclasses
sealed class SealedClass {}

// subclass of sealed class must be sealed, non-sealed, or final
// non-sealed class must extend sealed class
non-sealed class NonSealedSubSealedClass extends SealedClass {}

// only sealed class can permit unlike non-sealed or final
sealed class SealedPermitter permits SealedPermitted, NonSealedPermitted, FinalPermitted {}
// sealed // non-sealed
sealed class SealedPermitted extends SealedPermitter {}
non-sealed class NonSealedSubSealedPermitted extends SealedPermitted {}
// non-sealed
non-sealed class NonSealedPermitted extends SealedPermitter {}
// final
final class FinalPermitted extends SealedPermitter {}

// final class cannot be extended and need not extend
// final class CannotBeExtendedAndNeedNotExtend {} 

// # 9 # 409: Sealed Classes: Sealed Interfaces: Interfaces can be sealed or non-sealed, however cannot be final
/*
// only sealed interface can permit interface(s) unlike non'-sealed interface 

// sealed interface must be extended by sealed or non-sealed interface 

// non-sealed interface must extend sealed interface 

// non-sealed interface can be extended by non-sealed or interface without modifier 

// interface cannot be final
*/
// sealed interface must have non-sealed subinterface
sealed interface SealedInterface {}

// subinterface of sealed interface must be sealed or non-sealed, however cannot be final
// non-sealed interface must extend sealed interface
non-sealed interface NonSealedSubSealedInterface extends SealedInterface {}

// only sealed interface can permit unlike non-sealed
// sealed interface can permit sealed, non-sealed or final class
sealed interface SealedInterfacePermitter permits SealedInterfacePermitted, NonSealedInterfacePermitted, SealedClassPermitted, NonSealedClassPermitted, FinalClassPermitted {}
// sealed // non-sealed
sealed interface SealedInterfacePermitted extends SealedInterfacePermitter {}
non-sealed interface NonSealedSubSealedInterfacePermitted extends SealedInterfacePermitted {}
// non-sealed
non-sealed interface NonSealedInterfacePermitted extends SealedInterfacePermitter {}
// sealed class // non-sealed class
sealed class SealedClassPermitted implements SealedInterfacePermitter {}
non-sealed class NonSealedClassPermitted extends SealedClassPermitted implements SealedInterfacePermitter {}
// final class
final class FinalClassPermitted implements SealedInterfacePermitter {}

// # 9 # 409: Sealed Classes
class SealedClient {
    public void print() {
        System.out.println("# 9 # 409: Sealed Classes: Classes can be sealed, non-sealed or final");
        System.out.println(String.format("class: %s", SealedPermitter.class));
        System.out.println(String.format("isSealed(): %s", SealedPermitter.class.isSealed()));
        System.out.println(String.format("getPermittedSubclasses(): %s", Arrays.toString(SealedPermitter.class.getPermittedSubclasses())));
        var permitterSealed = new SealedPermitter();
        System.out.println(String.format("class: %s", permitterSealed.getClass()));
        System.out.println(String.format("isSealed(): %s", permitterSealed.getClass().isSealed()));
        System.out.println(String.format("getPermittedSubclasses(): %s", Arrays.toString(permitterSealed.getClass().getPermittedSubclasses())));
     
        System.out.println("# 9 # 409: Sealed Classes: Sealed Interfaces: Interfaces can be sealed or non-sealed, however cannot be final");
        System.out.println(String.format("class: %s", SealedInterfacePermitter.class));
        System.out.println(String.format("isSealed(): %s", SealedInterfacePermitter.class.isSealed()));
        System.out.println(String.format("getPermittedSubclasses(): %s", Arrays.toString(SealedInterfacePermitter.class.getPermittedSubclasses())));
        SealedInterfacePermitter interfacePermitterSealed = new SealedClassPermitted();
        System.out.println(String.format("class: %s", interfacePermitterSealed.getClass()));
        System.out.println(String.format("isSealed(): %s", interfacePermitterSealed.getClass().isSealed()));
        System.out.println(String.format("getPermittedSubclasses(): %s", Arrays.toString(interfacePermitterSealed.getClass().getPermittedSubclasses())));
        System.out.println();
    }
}


// # 12 # 412: Foreign Function & Memory API (Incubator)
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
	System.out.println("# 12 # 412: Foreign Function & Memory API (Incubator)");
	radixsort();
        strlen("Hola!");
        qsort(5, 7, 9, 3, 4, 6, 1, 8, 2, 0);
	System.out.println();
    }
}


// # 13 # 414: Vector API (Second Incubator)
class VectorAPISecondIncubator {
    // Simple scalar computation over elements of arrays:
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

    // Hope to improve the performance of vector operations that accept masks on architectures that support masking in hardware.
    // If masks were more efficient then the above could be written more simply, without the scalar loop to process the tail elements, while still achieving optimal performance.
    private void vectorComputationFuture(float[] a, float[] b, float[] c) {
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
        System.out.println("# 13 # 414: Vector API (Second Incubator)");
        System.out.println("An API to express vector computations that reliably compile at runtime to optimal vector instructions on supported CPU architectures, thus achieving performance superior to equivalent scalar computations.");

	float[] alpha = { 1.2f, 2.3f };
        float[] beta = { 3.4f, 5.6f };
        float[] gamma = { 7.8f, 9.1f };

        System.out.println("Scalar Computation");
        scalarComputation(alpha, beta, gamma);

        System.out.println("Vector Computation");
	vectorComputation(alpha, beta, gamma);

        System.out.println("Hope to improve the performance of vector operations that accept masks on architectures that support masking in hardware.");
	vectorComputationFuture(alpha, beta, gamma);
        System.out.println();
    }
}


// # 14 # 415: Context-Specific Deserialization Filters
class Alphanumeric implements Serializable {
    private String alpha;
    private int beta;

    public Alphanumeric(String alpha, int beta) {
       this.alpha = alpha;
       this.beta = beta;
    }

    @Override
    public String toString() {
        return String.format("Alpha = %1$s, Beta = %2$d", alpha, beta);
    }
}

record Aircraft(String name, int model) implements Serializable {  
    @Override
    public String toString() {
        return String.format("Name = %1$s, Model = %2$d", name, model);
    }
}

enum Day implements Serializable {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
    THURSDAY, FRIDAY, SATURDAY 
}

enum Planet implements Serializable {
    MERCURY (3.303e+23, 2.4397e6),
    VENUS   (4.869e+24, 6.0518e6),
    EARTH   (5.976e+24, 6.37814e6),
    MARS    (6.421e+23, 3.3972e6),
    JUPITER (1.9e+27,   7.1492e7),
    SATURN  (5.688e+26, 6.0268e7),
    URANUS  (8.686e+25, 2.5559e7),
    NEPTUNE (1.024e+26, 2.4746e7);

    private final double mass;   // in kilograms
    private final double radius; // in meters
    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }
}

// # 14 # 415: Context-Specific Deserialization Filters
class ContextSpecificDeserializationFilters {
    public void print() throws IOException {  
        // Java 17 added allowFilter and rejectFilter to ObjectInputFilter
        System.out.println("# 14 # 415: Context-Specific Deserialization Filters: ObjectInputFilter: allowFilter");
        // Filter allow String.class
        ObjectInputFilter filterStringClass = ObjectInputFilter.allowFilter(
            x -> x.equals(String.class), ObjectInputFilter.Status.REJECTED
	);

        // Classes other than String.class have status: REJECTED
        // byte[] byteStream = toStream(1);
        byte[] byteStream = toStream("Hola!");

        InputStream streamInput = new ByteArrayInputStream(byteStream);
	// new ObjectInputStream // unreported exception IOException; must be caught or declared to be thrown
        ObjectInputStream inputStreamObject = new ObjectInputStream(streamInput);
        inputStreamObject.setObjectInputFilter(filterStringClass);
    
        try {
            // readObject // unreported exception IOException; must be caught or declared to be thrown
            Object instance = inputStreamObject.readObject();
            System.out.println("Read object: " + instance);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

	// Java 17 added allowFilter and rejectFilter to ObjectInputFilter
        System.out.println("# 14 # 415: Context-Specific Deserialization Filters: ObjectInputFilter: rejectFilter");
        // Filter reject Planet.class
        ObjectInputFilter filterPlanetClass = ObjectInputFilter.rejectFilter(
            Planet.class::isAssignableFrom,
            ObjectInputFilter.Status.UNDECIDED
	);

        // Planet.class has status: REJECTED
        // byteStream = toStream(Planet.SATURN);
        byteStream = toStream(Day.SATURDAY);
        streamInput = new ByteArrayInputStream(byteStream);
   	// new ObjectInputStream // unreported exception IOException; must be caught or declared to be thrown
        inputStreamObject = new ObjectInputStream(streamInput);

        inputStreamObject.setObjectInputFilter(filterPlanetClass);

        try {
	    // readObject // unreported exception IOException; must be caught or declared to be thrown
            Object instance = inputStreamObject.readObject();
            System.out.println("Read object: " + instance);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Pre-Java 17
	System.out.println("Pre-Java 17: ObjectInputFilter");
        // Filter reject Alphanumeric.class by passing argument false
        // ObjectInputFilter filterAlphanumericClass = filter(Alphanumeric.class, false);
        // Filter allow Alphanumeric.class by passing argument true
        ObjectInputFilter filterAlphanumericClass = filter(Alphanumeric.class, true);

        Alphanumeric numericAlpha = new Alphanumeric("A to Z", 1234567890);
        byteStream = toStream(numericAlpha);
        streamInput = new ByteArrayInputStream(byteStream);
	// new ObjectInputStream // unreported exception IOException; must be caught or declared to be thrown
        inputStreamObject = new ObjectInputStream(streamInput);

        inputStreamObject.setObjectInputFilter(filterAlphanumericClass);

        try {
	    // readObject // unreported exception IOException; must be caught or declared to be thrown
            Object instance = inputStreamObject.readObject();
            System.out.println("Read object: " + instance);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Pre-Java 17
        // Filter reject Aircraft.class by passing argument false
        // ObjectInputFilter filterAircraftClass = filter(Aircraft.class, false);
        // Filter allow Aircraft.class by passing argument true
        ObjectInputFilter filterAircraftClass = filter(Aircraft.class, true);

        Aircraft air = new Aircraft("Boeing", 787);
        byteStream = toStream(air);
        streamInput = new ByteArrayInputStream(byteStream);
	// new ObjectInputStream // unreported exception IOException; must be caught or declared to be thrown
        inputStreamObject = new ObjectInputStream(streamInput);

        inputStreamObject.setObjectInputFilter(filterAircraftClass);

        try {
	    // readObject // unreported exception IOException; must be caught or declared to be thrown
            Object instance = inputStreamObject.readObject();
            System.out.println("Read object: " + instance);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private byte[] toStream(Object instance) {
        ByteArrayOutputStream outputStreamByteArray = new ByteArrayOutputStream();
        try (ObjectOutputStream outputStreamObject = new ObjectOutputStream(outputStreamByteArray)) {
            outputStreamObject.writeObject(instance);
            return outputStreamByteArray.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    // Pre-Java 17
    private ObjectInputFilter filter(Class<?> wildcardParameter, boolean isAllowed) {
        return x -> {
            Class<?> wildcardLocal = x.serialClass();
            if (wildcardLocal != null) {
                if (isAllowed) {
                    return (wildcardParameter.isAssignableFrom(wildcardLocal))
                        ? ObjectInputFilter.Status.ALLOWED
                        : ObjectInputFilter.Status.REJECTED;
                }
                return (wildcardParameter.isAssignableFrom(wildcardLocal))
                        ? ObjectInputFilter.Status.REJECTED
                        : ObjectInputFilter.Status.ALLOWED;

            }
            return ObjectInputFilter.Status.UNDECIDED;
        };
    }
}


// Output
/*
WARNING: Using incubator modules: jdk.incubator.vector, jdk.incubator.foreign
warning: using incubating module(s): jdk.incubator.foreign,jdk.incubator.vector
Note: Java17.java uses preview features of Java SE 17.
Note: Recompile with -Xlint:preview for details.
1 warning
OS Name: Mac OS X
OS Version: 14.0
OS Architecture: aarch64
Java Version: 17.0.2

# 1 # 306: Restore Always-Strict Floating-Point Semantics
Java 17, all floating-point expressions are evaluated strictly and 'strictfp' is not required
This will restore the original floating-point semantics to the language and VM, matching the semantics before the introduction of strict and default floating-point modes in Java SE 1.2.

# 2 # 356: Enhanced Pseudo-Random Number Generators
-501438149
# 2 # 356: Enhanced Pseudo-Random Number Generators: RandomGeneratorFactory
2 5 6 7 9 

# 3 # 382: New macOS Rendering Pipeline
Java 2D internal rendering pipeline for macOS using the Apple Metal API as alternative to the existing pipeline, which uses the deprecated Apple OpenGL API.

# 4 # 391: macOS/AArch64 Port
JDK 17 ports to macOS/AArch64.

# 5 # 398: Deprecate the Applet API for Removal
The Applet API was previously deprecated, though not for removal, by JEP 289 in Java 9.

# 6 # 403: Strongly Encapsulate JDK Internals
% java --illegal-access=warn
OpenJDK 64-Bit Server VM warning: Ignoring option --illegal-access=warn; support was removed in 17.0
Except for critical internal APIs such as sun.misc.Unsafe, it will no longer be possible to relax the strong encapsulation of internal elements via a single command-line option, as was possible in JDK 9 through JDK 16.

# 7 # 406: Pattern Matching for switch (Preview)
5555
Ït is a Record
Ït is a Final Record
class PatternMatchingSwitch$StaticRecord
Ït is a Static Record Odd Integer: 1
Ït is a Static Record Even Integer: 2
Ït is a Static Record Even Integer: 90
Ït is a Vehicle
Ït is an Aircraft - Name: Boeing, Nickname: Dreamliner, Aircraft Classification Number: 787

# 8 # 407: Remove RMI Activation
package java.rmi.activation removed, rest of package java.rmi.* preserved

# 9 # 409: Sealed Classes: Classes can be sealed, non-sealed or final
class: class SealedPermitter
isSealed(): true
getPermittedSubclasses(): [class SealedPermitted, class NonSealedPermitted, class FinalPermitted]
class: class SealedPermitter
isSealed(): true
getPermittedSubclasses(): [class SealedPermitted, class NonSealedPermitted, class FinalPermitted]
# 9 # 409: Sealed Classes: Sealed Interfaces: Interfaces can be sealed or non-sealed, however cannot be final
class: interface SealedInterfacePermitter
isSealed(): true
getPermittedSubclasses(): [interface SealedInterfacePermitted, interface NonSealedInterfacePermitted, class SealedClassPermitted, class NonSealedClassPermitted, class FinalClassPermitted]
class: class SealedClassPermitted
isSealed(): true
getPermittedSubclasses(): [class NonSealedClassPermitted]

# 10 # 410: Remove the Experimental AOT and JIT Compiler
Experimental java-based ahead-of-time (AOT) and just-in-time (JIT) compiler removed
Experimental java-level JVM compiler interface (JVMCI) retained to use externally-built versions of the compiler for JIT compilation

# 11 # 411: Deprecate the Security Manager for Removal
Security Manager dates from Java 1.0., deprecated for removal in concert with the legacy Applet API (JEP 398).

# 12 # 412: Foreign Function & Memory API (Incubator)
radixsort input: [Delta, Gamma, Alpha, Beta]
radixsort output: [Alpha, Beta, Delta, Gamma]
strlen input: Hola!
strlen output: 5
qsort input: [5, 7, 9, 3, 4, 6, 1, 8, 2, 0]
qsort output: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

# 13 # 414: Vector API (Second Incubator)
An API to express vector computations that reliably compile at runtime to optimal vector instructions on supported CPU architectures, thus achieving performance superior to equivalent scalar computations.
Scalar Computation
[-13.0, -36.649998]
Vector Computation
[-13.0, -36.649998]
Hope to improve the performance of vector operations that accept masks on architectures that support masking in hardware.
[-13.0, -36.649998]

# 14 # 415: Context-Specific Deserialization Filters: ObjectInputFilter: allowFilter
Read object: Hola!
# 14 # 415: Context-Specific Deserialization Filters: ObjectInputFilter: rejectFilter
Read object: SATURDAY
Pre-Java 17: ObjectInputFilter
Read object: Alpha = A to Z, Beta = 1234567890
Read object: Name = Boeing, Model = 787
*/


/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
https://learn.microsoft.com/en-us/java/openjdk
*/