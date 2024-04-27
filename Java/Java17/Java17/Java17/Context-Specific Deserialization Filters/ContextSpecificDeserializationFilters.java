// Java 17
// Java 17
// OpenJDK 17
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK17.0.2/jdk-17.0.2.jdk/Contents/Home/bin/":$PATH

// Java 17
// Microsoft Build of OpenJDK
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/MicrosoftBuildofOpenJDK/jdk-17.0.8.1+1/Contents/Home/bin/":$PATH

// % java ContextSpecificDeserializationFilters.java 
// Or
// % javac ContextSpecificDeserializationFilters.java 
// % java ContextSpecificDeserializationFilters

// Java 16
// OpenJDK 16
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK16.0.2/jdk-16.0.2.jdk/Contents/Home/bin/":$PATH


/*
# 415: Context-Specific Deserialization Filters
*/ 


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.function.BinaryOperator;

class ContextSpecificDeserializationFilters {
    public void print() throws IOException {  
        // Java 17 added allowFilter and rejectFilter to ObjectInputFilter
        System.out.println("# 415: Context-Specific Deserialization Filters: ObjectInputFilter: allowFilter");
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
        System.out.println("# 415: Context-Specific Deserialization Filters: ObjectInputFilter: rejectFilter");
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

    public static void main(String[] args) throws IOException {
        var propertiesSystem = new SystemProperties();
        propertiesSystem.print();
	
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


// Output
/*
OS Name: Mac OS X
OS Version: 13.5.2
OS Architecture: aarch64
Java Version: 17.0.2

# 415: Context-Specific Deserialization Filters: ObjectInputFilter: allowFilter
Read object: Hola!
# 415: Context-Specific Deserialization Filters: ObjectInputFilter: rejectFilter
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