// Java 19
// OpenJDK 19
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK19.0.2/jdk-19.0.2.jdk/Contents/Home/bin/":$PATH

// % java --enable-preview --source 19 --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.concurrent,jdk.incubator.vector StructuredConcurrencyIncubator.java
// Or
// % javac -Xlint:preview --enable-preview --source 19 --add-modules=jdk.incubator.vector,jdk.incubator.concurrent StructuredConcurrencyIncubator.java
// % java --enable-preview --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.concurrent,jdk.incubator.vector StructuredConcurrencyIncubator

// Java 18
// OpenJDK 18
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK18.0.2/jdk-18.0.2.jdk/Contents/Home/bin/":$PATH


/*
# 428: Structured Concurrency (Incubator)
*/


import java.time.Duration;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import jdk.incubator.concurrent.StructuredTaskScope;

class StructuredConcurrencyIncubator {

    private String texter() throws InterruptedException {
	// Pre-Java 19 // error: no suitable method found for sleep(Duration)
        Thread.sleep(Duration.ofSeconds(1));
        return "JEP";
    }

    private Integer ringer() throws InterruptedException {
	// Pre-Java 19 // error: no suitable method found for sleep(Duration)
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

            // Here, both forks have succeeded, so compose their results
            return new Phone(text.resultNow(), number.resultNow());
        }
    }

    public void print() throws Throwable {
        System.out.println("# 428: Structured Concurrency (Incubator)");
	System.out.println("UnStructured Concurrency API: " + handleUnStructureAPI());
	System.out.println("Structured Concurrency API: " + handleStructureAPI());
    }

    public static void main(String[] args) throws Throwable { 
    // public static void main(String... args) { 
        var propertiesSystem = new SystemProperties();
        propertiesSystem.print();
        
	var incubatorStructuredConcurrency = new StructuredConcurrencyIncubator();
	incubatorStructuredConcurrency.print();
    }
}

record Phone(String sms, int callerID) {}

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
warning: using incubating module(s): jdk.incubator.concurrent,jdk.incubator.vector
1 warning
OS Name: Mac OS X
OS Version: 13.5.2
OS Architecture: aarch64
Java Version: 19.0.2

# 428: Structured Concurrency (Incubator)
UnStructured Concurrency API: Phone[sms=JEP, callerID=428]
Structured Concurrency API: Phone[sms=JEP, callerID=428]
*/


/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
*/