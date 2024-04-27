// SampleJNI.c
#include <jni.h>	// JNI header provided by JDK
#include <stdio.h>	// C Standard IO Header
#include "SampleJNI.h"	// Generated header

// Implementation of the native method hola()
JNIEXPORT void JNICALL Java_SampleJNI_hola(JNIEnv *env, jobject thisObj) {
    printf("C code!\n");
    return;
}