/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

#ifndef HRFUSION_COMMON_H
#define HRFUSION_COMMON_H


#include <jni.h>
#include <android/log.h>
#include <string>
#include <vector>
#include <functional>

#define LOG_TAG "HRFusion"
#define ALOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#if DEBUG
#define ALOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__)
#else
#define ALOGV(...)
#endif

template<typename F = std::function<void(bool bEnclosed)>>
struct scope_t
{
    scope_t(F f) : f_(std::move(f)){
    }
    scope_t(scope_t&&) = delete;
    scope_t(const scope_t&) = delete;
    scope_t& operator=(scope_t&&) = delete;

    void enclose() {
        bEnclosed = true;
    }
    ~scope_t() noexcept {
        f_(bEnclosed);
    }

    F f_;
    bool bEnclosed = false;
};

#endif //HRFUSION_COMMON_H
