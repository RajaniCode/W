package com.sample;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

import jdk.incubator.foreign.*;

/*
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.CLinker.VaList.Builder;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.LibraryLookup;
import jdk.incubator.foreign.MemoryAddress;
*/

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Throwable
    {
        String userDir = System.getProperty("user.dir");

        String fileName = "hello.so";

      	String dirFile = userDir + "/" + fileName;

     	System.out.println(dirFile);

     	Path path = Path.of(dirFile);

      	LibraryLookup libraryLookup = LibraryLookup.ofPath(path);

      	Optional<LibraryLookup.Symbol> optionalSymbol = libraryLookup.lookup("hello");
      	if (optionalSymbol.isPresent()) {

          LibraryLookup.Symbol symbol = optionalSymbol.get();

          FunctionDescriptor functionDescriptor = FunctionDescriptor.ofVoid();

          MethodType methodType = MethodType.methodType(Void.TYPE);

          MethodHandle methodHandle = CLinker.getInstance().downcallHandle(
                  symbol.address(),
                  methodType,
                  functionDescriptor);
          methodHandle.invokeExact();
      	}
	
	System.out.println("Hello, World!");
        App instance = new App();
        System.out.println("Unique ID : " + instance.getUUID("123e4567-e89b-12d3-a456-556642440000"));
    }

    public String getUUID(String universallyUniqueIdentifier) 
    {
        String uid = UUID.fromString​(universallyUniqueIdentifier).toString();
        return uid;
    }
}