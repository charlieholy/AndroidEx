//
// Created by 陈沛文 on 2020/11/28.
//
#include <jni.h>
#include <string>
#include <android/native_window.h>
#include <android/native_window_jni.h>
void drawColor(JNIEnv *env,jobject obj,jobject surface, jint colorARGB)
{
    int alpha = colorARGB >> 24 & 0xFF;
    int red = colorARGB >> 16 & 0xFF;
    int green = colorARGB >> 8 & 0xFF;
    int blue = colorARGB & 0xFF;
    int colorABGR = alpha << 24 | (blue << 16) | (green << 8) | red;
    ANativeWindow *window = ANativeWindow_fromSurface(env,surface);
    int32_t result = ANativeWindow_setBuffersGeometry(window,640,640,AHARDWAREBUFFER_FORMAT_R8G8B8A8_UNORM);
    if (result < 0)
    {
        ANativeWindow_release(window);
        window = nullptr;
        return;
    }
    ANativeWindow_acquire(window);
    ANativeWindow_Buffer buffer;
    if (ANativeWindow_lock(window,&buffer, nullptr) < 0)
    {
        ANativeWindow_release(window);
        window = nullptr;
        return;
    }
    auto *line = (uint32_t *) buffer.bits;
    for (int y = 0; y < buffer.height; ++y)
    {
        for (int x = 0; x < buffer.width; ++x)
        {
            line[x] = colorABGR;
        }
        line += buffer.stride;
    }
    if (ANativeWindow_unlockAndPost(window) < 0)
    {
        return;
    }
    ANativeWindow_release(window);
}
