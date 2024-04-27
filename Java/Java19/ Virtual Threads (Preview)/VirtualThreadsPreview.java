// Java 19
// OpenJDK 19
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK19.0.2/jdk-19.0.2.jdk/Contents/Home/bin/":$PATH

// % java --enable-preview --source 19 --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.vector VirtualThreadsPreview.java
// Or
// % javac -Xlint:preview --enable-preview --source 19 --add-modules=jdk.incubator.vector VirtualThreadsPreview.java
// % java --enable-preview --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.vector VirtualThreadsPreview

// Java 18
// OpenJDK 18
// % export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK18.0.2/jdk-18.0.2.jdk/Contents/Home/bin/":$PATH


import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;

import java.util.ArrayList;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

import java.util.List;

class VirtualThreadsPreview {
    public static void main(String[] args) throws Throwable { 
        var propertiesSystem = new SystemProperties();
        propertiesSystem.print();
	var clientCallableTask = new CallableTaskClient();
	clientCallableTask.print();
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

class CallableTask implements Callable<Integer> {

    private final int number;

    public CallableTask(int number) {
        this.number = number;
    }

    @Override
    public Integer call() {

        // System.out.printf("Thread %s - Task %d waiting...%n", Thread.currentThread().getName(), number);

        try {
            Thread.sleep(1000);
            String dateTimePattern = "EEEE, dd MMMM yyyy, VV, z, O, h:mm:ss a";
            DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern(dateTimePattern);
            String ticks = formatterDateTime.format(ZonedDateTime.now());
            System.out.print(ticks + "\r");

        } catch (InterruptedException e) {
            System.out.printf("Thread %s - Task %d canceled.%n", Thread.currentThread().getName(), number);
            return -1;
        }

        // System.out.printf("Thread %s - Task %d finished.%n", Thread.currentThread().getName(), number);

        return ThreadLocalRandom.current().nextInt(100);
    }
}

class CallableTaskClient {

    public void print() throws InterruptedException, ExecutionException {

	// 1 Second
        // try (ExecutorService executorsThread = Executors.newVirtualThreadPerTaskExecutor()) {
 	// 1_000 / 100 = 10 Seconds
        try (ExecutorService executorsThread = Executors.newFixedThreadPool(100)) {
            List <CallableTask> tasks = new ArrayList<> ();
            for (int i = 0; i < 1_000; i++) {
                tasks.add(new CallableTask(i));
            }

            long time = System.currentTimeMillis();

            // invokeAll //unreported exception InterruptedException; must be caught or declared to be thrown
            List < Future < Integer >> futures = executorsThread.invokeAll(tasks);

            long sum = 0;
            for (Future <Integer> future: futures) {
                // get // unreported exception InterruptedException; must be caught or declared to be thrown
                // get // unreported exception ExecutionException; must be caught or declared to be thrown
                sum += future.get();
            }

            time = System.currentTimeMillis() - time;

            // System.out.println("sum = " + sum + "; time = " + time + " ms");
        }
    }
}


// Output
/*
WARNING: Using incubator modules: jdk.incubator.vector
OS Name: Mac OS X
OS Version: 13.5.2
OS Architecture: aarch64
Java Version: 19.0.2
*/


/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
*/