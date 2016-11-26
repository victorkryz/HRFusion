/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

#ifndef HRFUSION_TOOLS_H
#define HRFUSION_TOOLS_H

#include "common.h"
#include "Poco/Format.h"
#include "Reflection.h"

std::string fromInt(int intFrom);
int fromString(const std::string& strFrom);
std::string fromJStr(JNIEnv* env, jstring jstr);

template <typename T> std::string toString(const T& src);

template <> inline std::string toString(const int& src) {
    return std::move(fromInt(src));
}
template <> inline std::string toString(const std::string& src) {
    return std::move(std::string(src));
}

void simulateException(JNIEnv* env, const std::string& msg, const char* exceptionClass = nullptr);


#endif //HRFUSION_TOOLS_H
