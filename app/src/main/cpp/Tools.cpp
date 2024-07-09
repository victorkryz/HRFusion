/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

#include "common.h"
#include "Session.h"
#include "Tools.h"
#include "Poco/File.h"
#include "Poco/Data/SQLite/Connector.h"
#include "Poco/Data/SQLite/SQLiteException.h"

std::string fromInt(int intFrom){
    return Poco::format("%i", intFrom);
}

int fromString(const std::string& strFrom)
{
    const std::string str(Poco::trim(strFrom));
    return Poco::NumberParser::parse(str);
}

std::string fromJStr(JNIEnv* env, jstring jstr)
{
    std::string str("");
    if ( nullptr != jstr )
    {
        const char* p = env->GetStringUTFChars(jstr, nullptr);
        if ( nullptr != p )
        {
            str.assign(p);
            env->ReleaseStringUTFChars(jstr, p);
        }
    }
    return str;
}


void simulateException(JNIEnv* env, const std::string& msg, const char* exceptionClass)
{
    static const char* __baseExceptionName = "java/lang/Exception";
    const char* className = (exceptionClass != nullptr) ? exceptionClass : __baseExceptionName;
    jclass clazz = env->FindClass(className);
    if ( nullptr == clazz )
        clazz = env->FindClass(__baseExceptionName);

    env->ThrowNew(clazz, msg.c_str());
}

