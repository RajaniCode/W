// Java class, interface, record, enum, sealed, non-sealed, final, permitted subclasses, primitive, public, static
/*
public class Program {
    public static void main(String args[]) {
       Program pro = new Program();
       pro.print();
    }
    
    public void print() {
        Class cls = null;

        // cls = C.class;
        /*
        isPublic: false
        isStatic: false // false if nested without static modifier 
        isFinal: false
        isSealed: false
        isPrimitive: false
        getPermittedSubclasses: null
        */
        
        // cls = SC.class;
        /*
        isPublic: false
        isStatic: false // false if nested without static modifier 
        isFinal: false
        isSealed: true
        isPrimitive: false
        getPermittedSubclasses: [class NSC]
        */

        // cls = NSC.class;
        /*
        isPublic: false
        isStatic: false // false if nested without static modifier 
        isFinal: false
        isSealed: false
        isPrimitive: false
        getPermittedSubclasses: null
        */
        
        // cls = FC.class;
        /*
        isPublic: false
        isStatic: false // false if nested without static modifier 
        isFinal: true
        isSealed: false
        isPrimitive: false
        getPermittedSubclasses: null
        */

        // cls = I.class;
        /*
        isPublic: false
        isStatic: false // true if nested even without static modifier 
        isFinal: false // interface cannot be final
        isSealed: false
        isPrimitive: false
        getPermittedSubclasses: null
        */
        
        // cls = SI.class;
        /*
        isPublic: false
        isStatic: false // true if nested even without static modifier 
        isFinal: false // interface cannot be final
        isSealed: true
        isPrimitive: false
        getPermittedSubclasses: [interface NSI, class FC]
        */
        
        // cls = NSI.class;
        /*
        isPublic: false
        isStatic: false // true if nested even without static modifier 
        isFinal: false // interface cannot be final
        isSealed: false
        isPrimitive: false
        getPermittedSubclasses: null
        */
        
        // cls = R.class;
        /*
        isPublic: false
        isStatic: false // true if nested even without static modifier 
        isFinal: true
        isSealed: false
        isPrimitive: false
        getPermittedSubclasses: null
        */

        // cls = FR.class;
        /*
        isPublic: false
        isStatic: false // true if nested even without static modifier 
        isFinal: true
        isSealed: falsee
        isPrimitive: false
        getPermittedSubclasses: null
        */     

        cls = E.class;
        /*
        isPublic: false
        isStatic: false // true if nested even without static modifier 
        isFinal: true
        isSealed: false
        isPrimitive: false
        getPermittedSubclasses: null
        */     
        
        int modifier = cls.getModifiers();
        
        System.out.println(String.format("isPublic: %b", java.lang.reflect.Modifier.isPublic(modifier)));
        System.out.println(String.format("isStatic: %b", java.lang.reflect.Modifier.isStatic(modifier)));
        System.out.println(String.format("isFinal: %b", java.lang.reflect.Modifier.isFinal(modifier)));
        System.out.println(String.format("isSealed: %b", cls.isSealed()));
        System.out.println(String.format("isPrimitive: %b", cls.isPrimitive()));
        System.out.println(String.format("getPermittedSubclasses: %s", java.util.Arrays.toString(cls.getPermittedSubclasses())));
    }
    
    /*
    class C {}
    // sealed class SC {}
    // Or
    sealed class SC permits NSC {}
    non-sealed class NSC extends SC {}
    final class FC extends NSC implements SI {}

    interface I {}
    // sealed interface SI {}
    // Or
    sealed interface SI permits NSI, FC {}
    non-sealed interface NSI extends SI {}

    record R() {}
    final record FR() {}
    enum E {}
    */
}

class C {}
// sealed class SC {}
// Or
sealed class SC permits NSC {}
non-sealed class NSC extends SC {}
final class FC extends NSC implements SI {}

interface I {}
// sealed interface SI {}
// Or
sealed interface SI permits NSI, FC {}
non-sealed interface NSI extends SI {}

record R() {}
final record FR() {}
enum E {}
*/