#include <stdio.h>
#include <string.h>

void hola() {  
    printf("Hello, World!\n");
}

const char* h() {  
    return "Hello, World!\n";
}

int number() { 
    printf("number function call\n");
    return 1234567890;
}

void hi(char* c) {  
    printf("%s", c);
}

const char* hello_world(char* x, char* y) {  
   static char a[100], b[100];
   strcpy(a, x); 
   strcpy(b, y);
   return strcat(a, b);
}

const char* hello(char* b) {
   static char a[] = "Hello, ";
   return strcat(a, b);
}

int main() {
    h();
    printf("%d\n", number());
    printf("%s\n", hello_world("Hello, ", "World!"));
    printf("%s\n", hello("World!"));
}


/*
// arch64 // Microsoft Build of OpenJDK
// "_main"
gcc msys64-mingw64.c -o msys64-mingw64
./msys64-mingw64

gcc -c -fPIC msys64-mingw64.c

// Create shared .SO library
gcc -shared -o msys64-mingw64.so msys64-mingw64.o
*/