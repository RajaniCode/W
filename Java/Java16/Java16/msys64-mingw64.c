#include <stdio.h>

void hola() {  
    printf("Hello, World!\n");
}

/*
gcc -c -fPIC msys64-mingw64.c

gcc -shared -o msys64-mingw64.so msys64-mingw64.o
*/