#include <jni.h>


JNIEXPORT jdouble JNICALL Java_ra48_12014_pnrs1_rtrk_taskmanager_NativeCalc_calculatePercentage
  (JNIEnv *env, jobject obj, jint finished , jint total)
{
    if(total<0)
        return 0.01;
    return (double)finished / total * 100;

}
