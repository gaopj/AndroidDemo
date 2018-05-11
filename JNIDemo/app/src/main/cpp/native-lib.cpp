#include <jni.h>
#include <string>


extern "C"
JNIEXPORT jstring
JNICALL
Java_com_gpj_jnidemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello2 from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_gpj_jnidemo_MainActivity_getDouble(JNIEnv *env, jclass type, jint input) {

    return 2 * input;

}