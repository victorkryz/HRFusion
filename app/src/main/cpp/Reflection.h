/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

//& @formatter:off

#ifndef HRFUSION_REFLECTION_H
#define HRFUSION_REFLECTION_H

#include "common.h"
#include "Entities.h"
#include "Tools.h"

class ReflectionException : public std::runtime_error
{
public:
    ReflectionException(const std::string& msg) : std::runtime_error(msg) {
    }
};

template<typename T>
inline T jniCall(JNIEnv* env, std::function<T(void)> fnc, std::string outerFuncName = __func__ )
{
    T res = fnc();
    jboolean bExcept = env->ExceptionCheck();
    if ( bExcept )
    {
        env->ExceptionDescribe();
        env->ExceptionClear();

        const std::string strMsg(Poco::format("%s (raised by function: %s)",
                                              std::string("Reflection exception"), outerFuncName));
        throw ReflectionException(strMsg);
    }

    return std::move(res);
}

class ClassReflector
{
    public:
        ClassReflector(JNIEnv* env) : env_(env) {
        }

        jclass getClass(const char* className) {
            return getClassByName(className);
        }

    private:
        jclass getClassByName(const char* clazzName){
            return env_->FindClass(clazzName);
        }

    private:
        JNIEnv* env_;
};


class FieldAggregatorBase
{
    public:
        FieldAggregatorBase(JNIEnv* env, jclass clazz, jobject jobj, const char* sign = nullptr) :
                            env_(env), class_(clazz), jobj_(jobj), sign_(sign){
         };

        jfieldID getFieldId(const char *fieldName ) {
            return jniCall<jfieldID>(env_, [&]()->jfieldID
            {return env_->GetFieldID(class_, fieldName, sign_);});
        }

    protected:
        JNIEnv* env_;
        jclass class_;
        jobject jobj_;
        const char* sign_;
};

template <typename T>
class FieldAggregator : public FieldAggregatorBase
{
    public:
    FieldAggregator(JNIEnv* env, jclass clazz, jobject jobj) :
                        FieldAggregatorBase(env, clazz, jobj) {
    }

    void setField(const char *fieldName, const T &value);
    T getField(const char *fieldName);

    protected:
    jfieldID getFieldId(const char *fieldName);
};

template <>
class FieldAggregator<std::string> : public FieldAggregatorBase
{
    using _Base = FieldAggregatorBase;

    public:
        FieldAggregator(JNIEnv* env, jclass clazz, jobject jobj) :
                FieldAggregatorBase(env, clazz, jobj, "Ljava/lang/String;") {
        }

        void setField(const char *fieldName, const std::string &value)
        {
            auto jstrValue = jniCall<jstring>(env_, [&]()->jstring {
                                return env_->NewStringUTF(value.c_str());});
            jniCall<bool>(env_, [&]()->bool {
                                env_->SetObjectField(jobj_, getFieldId(fieldName), jstrValue);
                                return true;});
        }
};


template <>
class FieldAggregator<int> : public FieldAggregatorBase
{
    using _Base = FieldAggregatorBase;

    public:
        FieldAggregator(JNIEnv* env, jclass clazz, jobject jobj) :
                FieldAggregatorBase(env, clazz, jobj, "I") {
        }

        void setField(const char *fieldName, const int &value)
        {
            jniCall<bool>(env_, [&]()->bool {
                                env_->SetIntField(jobj_, getFieldId(fieldName), value);
                                return true;});
        }
};


template <>
class FieldAggregator<float> : public FieldAggregatorBase
{
    using _Base = FieldAggregatorBase;

    public:
        FieldAggregator(JNIEnv* env, jclass clazz, jobject jobj) :
                FieldAggregatorBase(env, clazz, jobj, "F") {
        }

        void setField(const char *fieldName, const float& value) {
            jniCall<bool>(env_, [&]()->bool {
                                env_->SetFloatField(jobj_, getFieldId(fieldName), value);
                                return true;});
        }
};


template <>
class FieldAggregator<double> : public FieldAggregatorBase
{
    using _Base = FieldAggregatorBase;

    public:
        FieldAggregator(JNIEnv* env, jclass clazz, jobject jobj) :
                FieldAggregatorBase(env, clazz, jobj, "D") {
        }

        void setField(const char *fieldName, const double& value) {
            jniCall<bool>(env_, [&]()->bool {
                    env_->SetDoubleField(jobj_, getFieldId(fieldName), value);
                    return true;});
        }
};


template <>
class FieldAggregator<bool> : public FieldAggregatorBase
{
    using _Base = FieldAggregatorBase;

    public:
        FieldAggregator(JNIEnv* env, jclass clazz, jobject jobj) :
                FieldAggregatorBase(env, clazz, jobj, "Z") {
        }

        void setField(const char *fieldName, const bool& value) {
            jniCall<bool>(env_, [&]()->bool {
                    env_->SetBooleanField(jobj_, getFieldId(fieldName), value);
                    return true;});
        }
};

class ReflectorBase
{
    public:
        ReflectorBase(JNIEnv *env) : env_(env) {
        }

        JNIEnv* getEnv() {
            return env_;
        }

        jclass getEntClass();
        virtual const char* getEntClassName()  = 0;

    protected:
        template <typename T>
        jobject createInstanceByHrItemConstructor(const Entities::HrItem<T>& item);

    protected:
        const char* constrMethodName = "<init>";
        JNIEnv* env_;
        jclass class_ = nullptr;
};


template <typename T>
class  Reflector : public ReflectorBase
{
    public:
        Reflector(JNIEnv *env) : ReflectorBase(env) {
        }
        jobject reflectEntity(const T &ent);
};


template <typename T>
class ArrayBuilder
{
    using entities_t = std::vector<T>;

    public:
        ArrayBuilder(Reflector<T>& reflector) : reflector_(reflector) {
        }

        jobjectArray buildArray(const entities_t& items);
        void fillElements(jobjectArray jArr, const entities_t& items);

    protected:
        Reflector<T>& reflector_;
};


template <typename ST>
class  Reflector<Entities::HrItem<ST>> : public ReflectorBase
{
    public:
        Reflector(JNIEnv *env) : ReflectorBase(env){
        }

        jobject reflectEntity(const Entities::HrItem<ST>& ent);
        jobjectArray reflectEntities(const Entities::HrItems<ST>& items);

        const char* getEntClassName() override {
            return "victor/kryz/hrfusion/hrdb/HrItem";
        }
};


template <>
class  Reflector<Entities::Location> : public ReflectorBase
{
    public:
        Reflector(JNIEnv *env) : ReflectorBase(env){
        }

        jobject reflectEntity(const Entities::Location& item);
        jobjectArray reflectEntities(const Entities::Locations& items);

        const char* getEntClassName() override {
            return "victor/kryz/hrfusion/hrdb/Location";
        }
};

template <>
class  Reflector<Entities::Department> : public ReflectorBase
{
    public:
        Reflector(JNIEnv *env) : ReflectorBase(env){
        }

        jobject reflectEntity(const Entities::Department& item);
        jobjectArray reflectEntities(const Entities::Departments& items);

        const char* getEntClassName() override {
            return "victor/kryz/hrfusion/hrdb/Department";
        }
};


template <>
class  Reflector<Entities::Employee> : public ReflectorBase
{
    public:
        Reflector(JNIEnv *env) : ReflectorBase(env){
        }

        jobject reflectEntity(const Entities::Employee& item);
        jobjectArray reflectEntities(const Entities::Employees& items);

        const char* getEntClassName() override {
            return "victor/kryz/hrfusion/hrdb/Employee";
        }
};


template <>
class  Reflector<Entities::JobStage> : public ReflectorBase
{
    public:
        Reflector(JNIEnv *env) : ReflectorBase(env){
        }

        jobject reflectEntity(const Entities::JobStage& item);
        jobjectArray reflectEntities(const Entities::JobStages& items);

        const char* getEntClassName() override {
            return "victor/kryz/hrfusion/hrdb/JobStage";
        }
};



//& @formatter:on

#endif //HRFUSION_REFLECTION_H
