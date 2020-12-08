//
// Created by 陈沛文 on 2020/11/28.
//

#ifndef MYAPPLICATION_IMAGEUTIL_H
#define MYAPPLICATION_IMAGEUTIL_H

#include <jni.h>
void i420torgba(const unsigned char *imgY,
                const int width,
                const int height,
                unsigned char *imgDst);

#endif //MYAPPLICATION_IMAGEUTIL_H
