#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_grblkotlin_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hola desde C++";
    return env->NewStringUTF(hello.c_str());
}
