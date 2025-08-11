#include "jni.h"
#include <string>

std::string getData(int x) {
    std::string app_secret = "Null";
    if (x == 1)app_secret = "123456789";
    if (x == 2)app_secret = "abcdefg";
    return app_secret;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_timeplannerapp_MainActivity_getApiKey(
        JNIEnv *env,
        jobject thiz,
        jint id
) {
    std::string app_secret = getData(id);
    return env->NewStringUTF(app_secret.c_str());
}