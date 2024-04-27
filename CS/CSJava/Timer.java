import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

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
	String dateTimePattern = "EEEE, dd MMMM yyyy, h:mm:ss.SSS a";
	DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern(dateTimePattern);

        while (!Thread.interrupted()) {  
            String tick = formatterDateTime.format(LocalDateTime.now());   
            System.out.print(tick + "\r");
        } 

        // System.out.println("Thread stopped"); 
    } 
} 
  



