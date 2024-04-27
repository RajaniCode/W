#include <stdio.h>
#include <string.h>

void printMatrix(int **matrix, int width, int height) {
    for (int i = 0; i < width; ++i) {
        // printf(matrix[i], height);
        // std::cout << std::endl;
        printf("%p,  %d", matrix[i], height);
        printf("\n");
    }
    printf("\n");
}

void printArray(int *array, int width) {
    for (int j = 0; j < width; ++j) {
        //std::cout << array[j] << " ";
        printf("%d ", array[j]);
    }
    printf("\n");
}


int main() {

    int var = 4;
    int array[] = { 1, 2, 3, 4, 5};

    printArray(array, var);

    int *ptr = &var;
    int **matrix = &ptr;
    // int matrixArray[5][2] = { { 10, 11 }, { 12, 13 }, { 14, 15 }, { 16, 17 }, { 18, 19 } };;

    printMatrix(matrix, var, var);
}


/*
// "_main"
gcc sample.c -o sample
./sample

gcc -c -fPIC sample.c

gcc -shared -o sample.so sample.o
*/