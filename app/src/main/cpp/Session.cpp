/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

#include <Poco/File.h>
#include <Poco/Data/RecordSet.h>
#include "common.h"
#include "Session.h"
#include "Poco/Data/SQLite/Connector.h"
#include "Poco/Data/SQLite/SQLiteException.h"
#include "Tools.h"

namespace Sqlt
{
    using SessionSp = std::shared_ptr<Poco::Data::Session>;

    namespace Utils
    {
        SessionSp openSession(const std::string& strDbFile)
        {
            using Poco::Data::Session;
            using Poco::Data::SQLite::InvalidSQLStatementException;
            using namespace Poco::Data::Keywords;

            Poco::Data::SQLite::Connector::registerConnector();

            SessionSp spSession(std::make_shared<Session>("SQLite", strDbFile));;
            return std::move(spSession);
        }
    }
}

jfieldID getDbFileField(JNIEnv *env, jobject obj)
{
    jclass c = jniCall<jclass>(env, [&]()->jclass
                        {return env->GetObjectClass(obj);});
    return jniCall<jfieldID>(env, [&]()->jfieldID
                        {return env->GetFieldID(c,"strDbFile", "Ljava/lang/String;");});
}

std::string getDbFile(JNIEnv *env, jobject obj)
{
    jstring jstr = jniCall<jstring>(env, [&]()->jstring
        {return (jstring)env->GetObjectField(obj, getDbFileField(env, obj));});

    return std::move(fromJStr(env, jstr));
}

