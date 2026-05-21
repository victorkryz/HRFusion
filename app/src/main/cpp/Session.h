#pragma once

#include "common.h"
#include <string>
#include <memory>
#include "Poco/Data/Session.h"

namespace Sqlt {
    using SessionSp = std::shared_ptr<Poco::Data::Session>;

    namespace Utils {
       SessionSp openSession(const std::string &strDbFile);
    }
}

std::string getDbFile(JNIEnv *env, jobject obj);
