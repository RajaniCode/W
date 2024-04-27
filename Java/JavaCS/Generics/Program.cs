// C# vs. Java Generic methods

// % csc Program.cs
// [
// % mcs Program.cs
// ]
// % mono Program.exe
class Program
{
    static void Main()
    {
        Program pro = new Program();
    	System.Console.WriteLine(pro.Function<int>(1));
    	System.Console.WriteLine(pro.Function<string>("Alpha"));
    }

    public void Method<T>(T t)
    {
        System.Console.WriteLine(t);
    }

    public T Function<T>(T t)
    {
        return t;
    }
}

// Java
// % java Program.java
/*
class Program {
    public static void main(String[] args) {
        Program pro = new Program();
        System.out.println(pro.<Integer>function(1));
        System.out.println(pro.<String >function("Alpha"));
    }
    
    public <T> void method(T t) {
        System.out.println(t);
    }
    
    public <T> T function(T t) {
        return t;
    }
}
*/