#include <jni.h>
#include <string>
#include "color.h"
#include "ImageUtil.h"

extern "C" JNIEXPORT jstring

JNICALL
Java_com_example_chenpeiwen_myapplication_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_chenpeiwen_myapplication_MainActivity_drawColor(
    JNIEnv *env,
    jobject thiz,
    jobject surface,
    jint color)
{
   drawColor(env,thiz,surface,color);
}

JNIEXPORT void JNICALL
Java_com_example_chenpeiwen_myapplication_MainActivity_yuv420p2rgba(
    JNIEnv *env,
    jclass type,
    jbyteArray yuv420p_,
    jint width,
    jint height,
    jbyteArray rgba_)
{
    jbyte *yuv420p = env->GetByteArrayElements(yuv420p_, NULL);
    jbyte *rgba = env->GetByteArrayElements(rgba_, NULL);

    i420torgba(reinterpret_cast<const unsigned char *>(yuv420p), width, height, reinterpret_cast<unsigned char *>(rgba));

    env->ReleaseByteArrayElements(yuv420p_, yuv420p, 0);
    env->ReleaseByteArrayElements(rgba_, rgba, 0);
}
