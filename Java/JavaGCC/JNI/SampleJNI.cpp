// SampleJNI.cpp
#include <jni.h>	// JNI header provided by JDK
#include <iostream>    	// C++ standard IO header
#include "SampleJNI.h" 	// Generated header
using namespace std;

// Implementation of the native method hola()
JNIEXPORT void JNICALL Java_SampleJNI_hola(JNIEnv *env, jobject thisObj) {
    cout << "C++ code!" << endl;
    return;
}