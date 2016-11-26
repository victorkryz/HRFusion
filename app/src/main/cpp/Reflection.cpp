/**
 * HRFusion
 *
 * @author Victor Kryzhanivskyi
 */

//& @formatter:off

#include "common.h"
#include "Reflection.h"

using namespace Entities;

/**
 * ReflectorBase class implementation
 */

jclass ReflectorBase::getEntClass()
{
    if (nullptr == class_)
    {
        ClassReflector classReflector(env_);
        class_ = classReflector.getClass(getEntClassName());
    }
    return class_;
}

template <typename T>
jobject ReflectorBase::createInstanceByHrItemConstructor(const Entities::HrItem<T>& item)
{
    const auto clazz = getEntClass();
    auto constr = jniCall<jmethodID>(env_, [&]()->jmethodID
                            {return env_->GetMethodID(clazz, constrMethodName,
                                    "(Ljava/lang/String;Ljava/lang/String;)V");});
    auto jstrId = jniCall<jstring>(env_, [&]()->jstring
                            {return env_->NewStringUTF(toString<T>(item.id).c_str());});
    auto jstrName = jniCall<jstring>(env_, [&]()->jstring
                            {return env_->NewStringUTF(item.name.c_str());});
    return jniCall<jobject>(env_, [&]()->jobject
                            {return env_->NewObject(clazz, constr, jstrId, jstrName);});
}


/**
 * Reflector<HrItem<ST>> class implementation
 */
template <typename ST>
jobject Reflector<HrItem<ST>>::reflectEntity(const HrItem<ST>& ent) {
    return createInstanceByHrItemConstructor(ent);
}

template <typename ST>
jobjectArray Reflector<HrItem<ST>>::reflectEntities(const HrItems<ST>& items)
{
    ArrayBuilder<HrItem<ST>> rgBuilder(*this);
    return rgBuilder.buildArray(items);
}

/**
 * Reflector<Location> class implementation
 */
jobject Reflector<Location>::reflectEntity(const Location& item)
{
        const auto clazz = getEntClass();

        auto constr = jniCall<jmethodID>(env_, [&]()->jmethodID
                            {return env_->GetMethodID(clazz, constrMethodName,
                                                   "(Ljava/lang/String;)V");});
        auto jstrId = jniCall<jstring>(env_, [&]()->jstring
                            {return env_->NewStringUTF(toString<int>(item.id).c_str());});
        auto obj = jniCall<jobject>(env_, [&]()->jobject
                            {return env_->NewObject(clazz, constr, jstrId);});

        FieldAggregator<std::string> f(env_, clazz, obj);
        f.setField("name", item.name);
        f.setField("city", item.city);
        f.setField("stateProvince", item.stateProvince);
        f.setField("streetAddress", item.streetAddress);
        f.setField("postalCode", item.postalCode);
        f.setField("countryId", item.countryId);

        return obj;
}

jobjectArray Reflector<Location>::reflectEntities(const Locations& items)
{
    ArrayBuilder<Location> rgBuilder(*this);
    return rgBuilder.buildArray(items);
}


/**
 * Reflector<Department> class implementation
 */
jobject Reflector<Department>::reflectEntity(const Department& item)
{
    auto obj = createInstanceByHrItemConstructor(item);
    const auto clazz = getEntClass();

    FieldAggregator<std::string> f(env_, clazz, obj);
    f.setField("managerId", toString<int>(item.mngrId));

    return obj;
}

jobjectArray Reflector<Department>::reflectEntities(const Departments& items)
{
    ArrayBuilder<Department> rgBuilder(*this);
    return rgBuilder.buildArray(items);
}

/**
 * Reflector<Employee> class implementation
 */
jobject Reflector<Employee>::reflectEntity(const Entities::Employee& item)
{
    auto obj = createInstanceByHrItemConstructor(item);
    const auto clazz = getEntClass();

    FieldAggregator<std::string> fs(env_, clazz, obj);
    fs.setField("lastName", item.lastName);
    fs.setField("phoneNumber", item.phoneNumber);
    fs.setField("email", item.email);
    fs.setField("hireDate", item.hireDate);
    fs.setField("jobId", item.jobId);
    fs.setField("jobTitle", item.jobTitle);

    FieldAggregator<float> ff(env_, clazz, obj);
    ff.setField("comissionPct", item.comissionPct);

    FieldAggregator<double> fd(env_, clazz, obj);
    fd.setField("salary", item.salary);

    FieldAggregator<bool> fb(env_, clazz, obj);
    fb.setField("isMngr", item.isMngr);

    return obj;
}

jobjectArray Reflector<Employee>::reflectEntities(const Employees& items)
{
    ArrayBuilder<Employee> rgBuilder(*this);
    return rgBuilder.buildArray(items);
}



/**
 * Reflector<Employee> class implementation
 */
jobject Reflector<JobStage>::reflectEntity(const Entities::JobStage& item)
{
    auto obj = createInstanceByHrItemConstructor(item);
    const auto clazz = getEntClass();

    FieldAggregator<double> fd(env_, clazz, obj);
    fd.setField("minSalary", item.minSalary);
    fd.setField("maxSalary", item.maxSalary);

    FieldAggregator<std::string> fs(env_, clazz, obj);

    std::string strDepartmentId(fromInt(item.departmentId));

    fs.setField("departmentId", strDepartmentId);
    fs.setField("departmentName", item.departmentName);
    fs.setField("startDate", item.startDate);
    fs.setField("endDate", item.endDate);

    return obj;
}

jobjectArray Reflector<JobStage>::reflectEntities(const JobStages& items)
{
    ArrayBuilder<JobStage> rgBuilder(*this);
    return rgBuilder.buildArray(items);
}


/**
 * ArrayBuilder<T> class implementation
 */
template <typename T>
jobjectArray ArrayBuilder<T>::buildArray(const entities_t& items)
{
    const auto clazz = reflector_.getEntClass();
    auto initElem = reflector_.reflectEntity(T());
    auto env = reflector_.getEnv();
    auto jArr = env->NewObjectArray(items.size(), clazz, initElem);
    fillElements(jArr, items);
    return jArr;
}

template <typename T>
void ArrayBuilder<T>::fillElements(jobjectArray jArr, const entities_t& items)
{
    auto env = reflector_.getEnv();
    jsize index(0);
    for (auto item : items)
    {
        jobject obj = reflector_.reflectEntity(item);
        jniCall<bool>(env, [&]()->bool
                            {env->SetObjectArrayElement(jArr, index++, obj);
                            return true;});
    }
}


// explicit instantiation:
template class  Reflector<HrItem<int>>;
template class  Reflector<HrItem<std::string>>;






