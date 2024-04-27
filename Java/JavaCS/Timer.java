import java.time.format.DateTimeFormatter;
// import java.time.LocalDateTime;
import java.time.ZonedDateTime;

class Timer {
    public static void main(String[] args) /* throws InterruptedException */ { 
    // public static void main(String... args) {
 
        RunnableThread threadRunnable = new RunnableThread(); 
  
        try { 
            // 5 seconds
            Thread.sleep(5000); 
            threadRunnable.thread.interrupt();
            Thread.sleep(10); 
        } 
        catch (InterruptedException e) { 
             e.printStackTrace();
        }
 
        // System.out.println("Exiting the main Thread"); 
    } 
}

class RunnableThread implements Runnable { 

    Thread thread; 
  
    RunnableThread() { 
        thread = new Thread(this); 
        thread.start(); 
    } 
  
    public void run() { 
	String dateTimePattern = "EEEE, dd MMMM yyyy, z, h:mm:ss.SSS a";
	DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern(dateTimePattern);

        while (!Thread.interrupted()) {  
            // String ticks = formatterDateTime.format(LocalDateTime.now());   
            String ticks = formatterDateTime.format(ZonedDateTime.now());  
            System.out.print(ticks + "\r");
        } 

        // System.out.println("Thread stopped"); 
    } 
} 
  



