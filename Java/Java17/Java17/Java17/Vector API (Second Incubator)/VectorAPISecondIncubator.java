// Java 17
// OpenJDK 17
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK17.0.2/jdk-17.0.2.jdk/Contents/Home/bin/":$PATH

// Java 17
// Microsoft Build of OpenJDK
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/MicrosoftBuildofOpenJDK/jdk-17.0.8.1+1/Contents/Home/bin/":$PATH

// % java --enable-native-access=ALL-UNNAMED --add-modules jdk.incubator.vector VectorAPISecondIncubator.java
// Or
// % javac --add-modules jdk.incubator.vector VectorAPISecondIncubator.java
// % java --add-modules jdk.incubator.vector VectorAPISecondIncubator

// Java 16
// OpenJDK 16
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK16.0.2/jdk-16.0.2.jdk/Contents/Home/bin/":$PATH


/*
# 414: Vector API (Second Incubator)
*/

import java.util.Arrays;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

// java --enable-native-access=ALL-UNNAMED --add-modules jdk.incubator.vector VectorAPISecondIncubator.java
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
        System.out.println("# 414: Vector API (Second Incubator)");
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
    }
        
    // public static void main(String... args) {
    public static void main(String[] args) { 
        var propertiesSystem = new SystemProperties();
        propertiesSystem.print();

        var incubatorVectorAPISecond = new VectorAPISecondIncubator();
	incubatorVectorAPISecond.print();
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
1 warning
OS Name: Mac OS X
OS Version: 13.5.2
OS Architecture: aarch64
Java Version: 17.0.2

# 414: Vector API (Second Incubator)
An API to express vector computations that reliably compile at runtime to optimal vector instructions on supported CPU architectures, thus achieving performance superior to equivalent scalar computations.
Scalar Computation
[-13.0, -36.649998]
Vector Computation
[-13.0, -36.649998]
Hope to improve the performance of vector operations that accept masks on architectures that support masking in hardware.
[-13.0, -36.649998]
*/


/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
https://learn.microsoft.com/en-us/java/openjdk
*/